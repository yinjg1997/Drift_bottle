package cn.edu.zust.ucenter.service.impl;


import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.jwt.JwtUtils;
import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.utils.MD5;
import cn.edu.zust.ucenter.entity.Member;
import cn.edu.zust.ucenter.entity.vo.RegisterVo;
import cn.edu.zust.ucenter.mapper.MemberMapper;
import cn.edu.zust.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;//spring boot底层整合了redis，自动配置类中通过了一定的条件然后向spring ioc容器里面注册了redis模板组件

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册会员的相关数据
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //注册必选项不能为空，否则注册失败
        if (StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"注册失败，请您检查注册必选项是否为空(⊙︿⊙)");
        }

        //判断手机号有无被注册
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"注册失败，此手机号已被注册(⊙︿⊙)");
        }

        //判断验证码是否正确(*￣︶￣)
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {//验证码匹配错误或已失效
            throw new BusinessException(ResponseCode.ERROR.getCode(),"注册失败，验证码匹配错误或已失效，请您重新注册(⊙︿⊙)");
        }

        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);//新注册会员，默认不禁用
        member.setAvatar("https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/2020/04/19/80791cf03b5845d28a8383904198c4e4file.png");//新注册会员，默认头像
        member.setGmtCreate(LocalDateTime.now());
        member.setGmtModified(LocalDateTime.now());
//        this.save(member);
//        save(member);
        //将注册会员的相关数据存到数据库
        baseMapper.insert(member);
    }

    @Override
    public String login(Member member) {
        //获取登录会员的手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //判断手机号是否为空
        if (StringUtils.isEmpty(mobile)) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"登录失败，手机号不能为空(⊙︿⊙)");
        }

        //判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"登录失败，密码不能为空(⊙︿⊙)");
        }

        //判断手机号有无被注册
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"登录失败，此手机号未被注册(⊙︿⊙)");
        }

        //判断密码
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"登录失败，密码不匹配(⊙︿⊙)");
        }

        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new BusinessException(ResponseCode.ERROR.getCode(),"登录失败，您被禁用了(⊙︿⊙)");
        }

        //为了实现单点登录，登录成功后需要返回jwt生成的token字符串
        String jwtToken = JwtUtils.createToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public Member getMemberByOpenId(String openId) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

}
