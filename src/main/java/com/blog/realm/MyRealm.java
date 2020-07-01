package com.blog.realm;

import com.blog.entity.Blogger;
//import com.blog.service.BloggerService;
import javax.annotation.Resource;

import com.blog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class MyRealm
  extends AuthorizingRealm
{
  @Resource
  private BloggerService bloggerService;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
  {
      return null;
  }

    /**
     * 登陆验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
    throws AuthenticationException
  {
      String userName = (String)token.getPrincipal();
      Blogger blogger = this.bloggerService.getByUserName(userName);
      if (blogger != null)
    {
        /*
        将登陆对象存入session
         */
        SecurityUtils.getSubject().getSession().setAttribute("blogger", blogger);
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(blogger.getUserName(), blogger.getPassword(), "xx");
        return authcInfo;
    }
      return null;
  }
}

