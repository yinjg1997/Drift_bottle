package cn.edu.zust.acl.controller;


import cn.edu.zust.acl.entity.Permission;
import cn.edu.zust.acl.service.PermissionService;
import cn.edu.zust.common.result.ResultMode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ResultMode indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        HashMap<String, List<Permission>> map = new HashMap<>();
        map.put("children", list);

        return ResultMode.ok(map);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public ResultMode remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return ResultMode.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public ResultMode doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return ResultMode.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public ResultMode toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        HashMap<String, List<Permission>> map = new HashMap<>();
        map.put("children", list);
        return ResultMode.ok(map);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public ResultMode save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResultMode.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public ResultMode updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return ResultMode.ok();
    }
}

