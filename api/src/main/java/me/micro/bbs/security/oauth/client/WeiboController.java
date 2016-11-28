package me.micro.bbs.security.oauth.client;

import me.micro.bbs.enums.Channel;
import me.micro.bbs.enums.ClientType;
import me.micro.bbs.file.ShortUUID;
import me.micro.bbs.security.social.SocialToken;
import me.micro.bbs.user.User;
import me.micro.bbs.user.UserForm;
import me.micro.bbs.user.UserSocial;
import me.micro.bbs.user.support.UserService;
import me.micro.bbs.user.support.UserSocialRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Oauth2账号登录
 *
 * Created by microacup on 2016/11/24.
 */
@Controller
@RequestMapping("/oauth/weibo")
public class WeiboController {
    private static final String authorize_url = "https://api.weibo.com/oauth2/authorize";
    private static final String access_token_url = "https://api.weibo.com/oauth2/access_token";
    private static final String user_show = "https://api.weibo.com/2/users/show.json?uid=%s";
    private static final String STATE_LOGIN = "login";

    @Value("${oauth2.weibo.clientId}")
    private String clientId;

    @Value("${oauth2.weibo.clientSecret}")
    private String clientSecret;

    @Value("${site.server}")
    private String server;

    @Autowired
    private UserSocialRepository userSocialRepository;

    @Autowired
    private UserService userService;

    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        try {
            OAuthClientRequest oauthResponse = OAuthClientRequest
                    .authorizationLocation(authorize_url)
                    .setResponseType(OAuth.OAUTH_CODE)
                    .setClientId(clientId)
                    .setRedirectURI(server + "/oauth/weibo/callback")
                    .setState(STATE_LOGIN)
                    .buildQueryMessage();
            WebUtils.setSessionAttribute(request, "state", STATE_LOGIN);
            return "redirect:"+oauthResponse.getLocationUri();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }

        return "redirect:/500";
    }

    @GetMapping("/callback")
    public String callback(HttpServletRequest request, Model model) {
        OAuthAuthzResponse oauthAuthzResponse = null;
        try {
            oauthAuthzResponse = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            String state = oauthAuthzResponse.getState();
            String code = oauthAuthzResponse.getCode();

            if (illegal(state, request)) {
                model.addAttribute("message", "非法入口");
                return "site/500";
            }

            OAuthClientRequest oauthClientRequest = OAuthClientRequest
                    .tokenLocation(access_token_url)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRedirectURI(server + "/oauth/weibo/callback")
                    .setCode(code)
                    .buildQueryMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            //获取access token
            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(oauthClientRequest, OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();
            // String refreshToken= oAuthResponse.getRefreshToken();
            // Long expiresIn = oAuthResponse.getExpiresIn();
            String body = oAuthResponse.getBody();
            JSONObject jsonObject = new JSONObject(body);
            String uid = jsonObject.getString("uid");

            // 根据UID查找是否已经登录过
            UserSocial userSocial = userSocialRepository.findBySourceAndOpenid(getName(), uid);
            if (userSocial != null) {
                // 根据state判断是登录还是绑定
                if (STATE_LOGIN.equals(state)) {
                    User user = userSocial.getUser();
                    return login(request, user);
                } else {
                    // TODO 绑定
                }

            } else {
                //获得资源服务
                OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(String.format(user_show, uid)).setAccessToken(accessToken).buildQueryMessage();
                OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
                String resBody = resourceResponse.getBody();
                // 解析这个用户
                JSONObject userInfo = new JSONObject(resBody);

                // 根据state判断是登录还是绑定
                if (STATE_LOGIN.equals(state)) {
                    UserForm userForm = toUserForm(userInfo);
                    User user = userService.newUserAndUserSocial(userForm);
                    return login(request, user);
                } else {
                    // TODO 绑定
                }
            }

            return "redirect:/500";
        } catch (OAuthSystemException ex) {
            return  "redirect:/404";
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }

        return "redirect:/404";
    }

    private UserForm toUserForm(JSONObject userInfo) {
        UserForm userForm = new UserForm();
        userForm.setOpenid(userInfo.getString("idstr"));
        userForm.setClient(ClientType.WEB);
        userForm.setGender(userInfo.getString("gender").equals("m") ? 1: 2);
        userForm.setChannel(Channel.OPEN);
        userForm.setInfo(userInfo.getString("description"));
        userForm.setName(userInfo.getString("name"));
        userForm.setUsername(ShortUUID.uuid());
        userForm.setNick(userInfo.getString("screen_name"));
        userForm.setOpenNickname(userInfo.getString("screen_name"));
        userForm.setSource(getName());

        return userForm;
    }


    // state是否非法
    private boolean illegal(String state, HttpServletRequest request) {
        return StringUtils.isBlank(state) || !state.equals(WebUtils.getSessionAttribute(request, "state"));
    }

    private String login(HttpServletRequest request, User user) {
        // 第三方登录
        SocialToken socialToken = new SocialToken(user);
        socialToken.setDetails(authenticationDetailsSource.buildDetails(request));
        Authentication authenticate = authenticationManager.authenticate(socialToken);
        if (authenticate  != null && authenticate.isAuthenticated()) {
            return "redirect:/";
        }

        return "redirect:/login";
    }


    String getName() {
        return "wb";
    }


}
