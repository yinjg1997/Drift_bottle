package cn.edu.zust.security.security;

import cn.edu.zust.common.jwt.JwtUtils;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.common.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登出业务逻辑类
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {

            //清空当前用户缓存中的权限数据
            String userName = JwtUtils.getClaim(token, "nickname");
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, ResultMode.ok());
    }

}
