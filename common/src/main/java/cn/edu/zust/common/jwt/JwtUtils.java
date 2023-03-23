package cn.edu.zust.common.jwt;


import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.result.ResponseCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JwtUtils里面主要就三样东西:
 *          1. createToken方法 签发token 或者说 返回加密的token
 *          2. getClaim()方法 用来解析token,获得 荷载 信息
 *          3. verify()方法 校验token是否正确
 * 具体还需要什么功能可以自己根据这三样的需求创建方法使用,
 * 如:
 * 创建根据载荷claim创建token的方法,
 * 创建根据token获取用户信息
 * 创建验证token是否过期
 * Created by Jianguo.Yin on 2021-06-23.
 */
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    /**
     * 设置存储token的Header名称常量
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * Token前缀常量,
     * 这个有啥用呢? 暂时没想到
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * token过期时间
     * 过期时间 24 小时, 此处过期时间是以毫秒为单位，所以乘以1000
     */
    public static final long EXPIRE_TIME = 60 * 60 * 24 * 1000;
    /**
     * token 密钥
     */
    public static final String SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 当验证过密码正确后 可以调用此方法生成令牌, 之后每次请求都要带这个令牌, 令牌的验证统一交给拦截器或过滤器
     * 生成Token
     * @return java.lang.String 返回加密的Token
     */
    public static String createToken(String id, String nickname) {
        try {
            // 附带account帐号信息
            return JWT.create()
                    // .withIssuer("CSDN Blog") // 发行者
                    // .withSubject("userid") // 用户身份标识
                    // .withAudience("CSDN User") // 用户单位
                    // .withJWTId("001") // 分配JWT的ID
                    .withIssuedAt(new Date()) // 签名时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 定义好的过期时间,此处过期时间是以毫秒为单位，所以乘以1000
                    .withClaim("id", id) // 在公有域中添加字段
                    .withClaim("nickname", nickname) // 在公有域中添加字段
                    .sign(Algorithm.HMAC256(SECRET)); // 使用某种加密进行签发
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new BusinessException(ResponseCode.ERROR);
        }
    }

    /**
     * 获得Token中的公有域信息, 它无需secret解密也能获得
     * getClaim方法就是获得token创建时用withClaim创建的 公有域 中的信息
     * @param token
     * @param claim claim为 域 的key
     * @return java.lang.String
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            LOGGER.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new BusinessException(ResponseCode.ERROR);
        }
    }

    /**
     * 校验token是否有效;
     * 这里自动验证token是否过期
     * @param token Token
     * @return boolean 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtUtils.SECRET);
            // 用加密算法加密获得所谓的 验证器
            JWTVerifier verifier = JWT.require(algorithm) // 验证算法
                    // .withIssuer("签发人") // 验证签发人, 如果在创建token时用 withIssuer() 创建了该 签发域 的话, 这里就可以进行验证
                    .acceptExpiresAt(8) // 表示您将接受8秒前已过期的令牌。考虑到网络滞后，这个值应该在几秒钟内。
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException|JWTDecodeException e) {
            LOGGER.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new BusinessException(99999, "token验证有问题");
        }
    }

    /**
     *  业务代码
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        LOGGER.info("会话id---" + request.getSession().getId());

        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) return "";

        return getClaim(jwtToken, "id");
    }
}
