package cn.edu.zust.edu.controller;



import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.common.utils.MD5;
import cn.edu.zust.edu.entity.vo.AdminVo;
import cn.edu.zust.edu.service.AdminService;
import cn.edu.zust.edu.entity.Admin;

import cn.edu.zust.common.jwt.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-26
 */
@Api(tags = "管理员管理")
@RestController
@RequestMapping("/eduservice/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 登录验证
     * @return
     */
    @ApiOperation(value = "登录验证")
    @PostMapping("login")
    public ResultMode login(@RequestBody AdminVo adminVo){
        log.info("Name{}",adminVo.getName());
        Admin adminTem = adminService.getByName(adminVo);
        if (adminTem == null) {
            throw new BusinessException(ResponseCode.USER_NOT_EXIST);
        }
        if (!Objects.equals(MD5.encrypt(adminVo.getPassword()), adminTem.getPassword())) {
            throw new BusinessException(ResponseCode.USER_LOGIN_ERROR);
        }
        String token = JwtUtils.createToken(adminTem.getId(), adminTem.getName());
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return ResultMode.ok(ResponseCode.SUCCESS, map);
    }

    /**
     * 用户信息
     * @param token
     * @return
     */
    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    public ResultMode info(String token){
        JwtUtils.verify(token);

        String id = JwtUtils.getClaim(token, "id");
        Admin admin = adminService.getById(id);

        HashMap<String, String> map = new HashMap<>();
        map.put("name", admin.getName());

        map.put("avatar", admin.getAvatar());
        return ResultMode.ok(ResponseCode.SUCCESS,map);
    }

    /**
     * 用户信息
     * @param token
     * @return
     */
    @ApiOperation(value = "用户信息")
    @PostMapping("logout")
    public ResultMode logout(String token){
        return ResultMode.ok(ResponseCode.SUCCESS,null);
    }
}

