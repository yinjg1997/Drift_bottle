package cn.edu.zust.ucenter.service;


import cn.edu.zust.ucenter.entity.Member;
import cn.edu.zust.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *

 */
public interface MemberService extends IService<Member> {

    /**
     * 注册会员
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 会员登录
     * @return
     * @param member
     */
    String login(Member member);

    /**
     * 根据调用微信提供的接口返回的openId去找会员信息
     * @param openId 唯一标识一个微信账号
     * @return
     */
    Member getMemberByOpenId(String openId);

}
