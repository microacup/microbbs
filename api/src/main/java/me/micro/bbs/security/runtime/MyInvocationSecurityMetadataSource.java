package me.micro.bbs.security.runtime;

import me.micro.bbs.security.Permission;
import me.micro.bbs.security.support.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    Logger log = Logger.getLogger(MyInvocationSecurityMetadataSource.class);

    @Autowired
    private PermissionService permissionService;

    private HashMap<String, Collection<ConfigAttribute>> map = null;


    /**
     * 根据路径获取访问权限的集合接口
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        List<ConfigAttribute> attrs = new ArrayList<>();
        try {
            List<Permission> permissions = permissionService.findAll();
            for (Permission p : permissions) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(p.getUrl());
                if (matcher.matches(request)) {
                    attrs.add(new SecurityConfig(p.getCode()));
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return attrs;
    }

    /**
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null; // XXX 全都支持，这里就不写了。
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
