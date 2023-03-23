package cn.edu.zust.ucenter.controller;



import cn.edu.zust.common.jwt.JwtUtils;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.common.vo.UcenterMemberVo;
import cn.edu.zust.ucenter.entity.Member;
import cn.edu.zust.ucenter.entity.vo.RegisterVo;
import cn.edu.zust.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 */
@Api(tags = "用户中心API")
@RestController
@RequestMapping("/ucenterservice/member")
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    //注册会员
    @ApiOperation(value = "注册会员")
    @PostMapping("register")
    public ResultMode register(@ApiParam(name = "registerVo", value = "注册会员信息") @RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return ResultMode.ok();
    }

    //会员登录
    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public ResultMode login(@ApiParam(name = "loginInfo", value = "登录会员信息") @RequestBody Member loginInfo, HttpServletResponse response){
        //返回jwt生成的token字符串
        String token = memberService.login(loginInfo);

        //服务端设置一个cookie，在响应头里面设置cookie，通知客户端保存这个cookie
//        response.setHeader("Set-Cookie","k1=v1; Domain=localhost; Path=/; HttpOnly");
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        log.info("token: {}", token);
        return ResultMode.ok(map);
    }

    //根据token获取会员信息
    @ApiOperation(value = "根据token获取会员信息")
    @GetMapping("getMemberInfo")
    public ResultMode getMemberInfo(HttpServletRequest request){
        //从token里面拿到会员id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //根据会员id查询会员信息
        Member member = memberService.getById(memberId);
        HashMap<String, Member> map = new HashMap<>();
        map.put("memberInfo", member);
        return ResultMode.ok(map);
    }



    //根据 id 获取会员信息
    @ApiOperation(value = "根据 user id 获取会员信息")
    @PostMapping("getMemberInfoById")
    public UcenterMemberVo getMemberInfoForComment(String id){
        //根据会员id查询会员信息
        Member member = memberService.getById(id);
        UcenterMemberVo memberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member, memberVo);
        return memberVo;
    }


}
