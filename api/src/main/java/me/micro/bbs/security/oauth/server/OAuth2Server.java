package me.micro.bbs.security.oauth.server;

import me.micro.bbs.user.User;
import me.micro.bbs.user.support.UserService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Authorization Code 授权码模式
 *
 * Created by microacup on 2016/11/28.
 */
@Controller
@RequestMapping("/oauth2")
public class OAuth2Server {
    private static Logger logger = LoggerFactory.getLogger(OAuth2Server.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2Service oAuth2Service;

    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @RequestMapping(value = "/authorize")
    public String authorize(HttpServletRequest request, Model model) {
        try {
            //构建OAuth请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (!ResponseType.CODE.toString().equals(responseType)) {
                throw OAuthProblemException.error("response_type不正确");
            }

            String clientId = oauthRequest.getClientId();
            // 验证clientId是否正确
            if (!validateOAuth2AppKey(clientId)) {
                OAuthResponse oauthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).setError(OAuthError.CodeResponse.ACCESS_DENIED).setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT).buildJSONMessage();
                logger.error("oauthRequest.getRedirectURI() : " + oauthRequest.getRedirectURI() + " oauthResponse.getBody() : " + oauthResponse.getBody());
                model.addAttribute("message", oauthResponse.getBody());
                return "/oauth2/500";
            }

            String redirectURI = oauthRequest.getRedirectURI();
            // 验证redirectURI
            if (!validateRedirectUri(redirectURI)) {
                OAuthResponse oauthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED).setError(OAuthError.CodeResponse.ACCESS_DENIED).setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT).buildJSONMessage();
                logger.error("oauthRequest.getRedirectURI() : " + oauthRequest.getRedirectURI() + " oauthResponse.getBody() : " + oauthResponse.getBody());
                model.addAttribute("message", oauthResponse.getBody());
                return "/oauth2/500";
            }

            // 查询app信息
            String appName = getAppInfo();
            model.addAttribute("appName",appName);
            model.addAttribute("response_type",oauthRequest.getResponseType());
            model.addAttribute("client_id",oauthRequest.getClientId());
            model.addAttribute("redirect_uri",oauthRequest.getRedirectURI());
            model.addAttribute("scope",oauthRequest.getScopes());
            model.addAttribute("state", oauthRequest.getState());

            // 用户是否登录
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof User && authentication.isAuthenticated()) {
                return authorize(request, redirectURI, authentication);
            } else {
                return "oauth2/login";
            }
        } catch (OAuthProblemException ex) {
            ex.printStackTrace();
        } catch (OAuthSystemException e) {
            model.addAttribute("message", e.getMessage());
            e.printStackTrace();
        }

        return "site/500";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String redirectURI = request.getParameter(OAuth.OAUTH_REDIRECT_URI);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        Authentication authentication = authenticationManager.authenticate(authRequest);
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            StringBuilder sb = new StringBuilder();
            sb.append("/oauth2/authorize").append("?")
                    .append(OAuth.OAUTH_CLIENT_ID).append("=").append(request.getParameter(OAuth.OAUTH_CLIENT_ID)).append("&")
                    .append(OAuth.OAUTH_RESPONSE_TYPE).append("=").append(request.getParameter(OAuth.OAUTH_RESPONSE_TYPE)).append("&")
                    .append(OAuth.OAUTH_SCOPE).append("=").append(request.getParameter(OAuth.OAUTH_SCOPE)).append("&")
                    .append(OAuth.OAUTH_STATE).append("=").append(request.getParameter(OAuth.OAUTH_STATE)).append("&")
                    .append(OAuth.OAUTH_REDIRECT_URI).append("=").append(request.getParameter(OAuth.OAUTH_REDIRECT_URI));
            return "redirect:" + sb.toString();
        }

        return "oauth2/login";
    }


    private String authorize(HttpServletRequest request, String redirectURI, Authentication authentication) throws OAuthSystemException {
        User principal = (User) authentication.getPrincipal();
        if (hasAuthorized(principal)) {
            // 生成code
            String code = new OAuthIssuerImpl(new MD5Generator()).authorizationCode();

            // 把授权码存入
            oAuth2Service.addCode(code, principal.getUsername());

            // 构建oauth2授权返回信息
            OAuthResponse oauthResponse = OAuthASResponse
                    .authorizationResponse(request, HttpServletResponse.SC_FOUND)
                    .setCode(code)
                    .location(redirectURI)
                    .buildQueryMessage();
            return "redirect:" + oauthResponse.getLocationUri();
        } else {
            return "/oauth2/authorize";
        }
    }

    /**
     * TODO 用户第一次授权scope
     *
     * @param request
     * @return
     */
    @PostMapping("/authorize")
    public String authorize(HttpServletRequest request) {


        return "site/500";
    }


    // TODO 验证redirectURI
    private boolean validateRedirectUri(String redirectURI) {

        return true;
    }

    private boolean hasAuthorized(User principal) {
        // TODO  查询用户授权信息，决定是否需要询问用户授权指定资源,or直接返回授权码
        return true;
    }


    private boolean validateOAuth2AppKey(String clientId) {
        List<String> clientIds = new ArrayList<String>() {{
            add("55833365");
            add("54477898");
            add("25863969");
            add("92447476");
        }};
        return clientIds.contains(clientId);
    }

    // TODO app Info
    private String getAppInfo() {
        return "Test APP";
    }

}

