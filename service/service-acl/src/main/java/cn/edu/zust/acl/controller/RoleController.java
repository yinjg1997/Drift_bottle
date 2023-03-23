package cn.edu.zust.acl.controller;


import cn.edu.zust.acl.entity.Role;
import cn.edu.zust.acl.service.RoleService;
import cn.edu.zust.common.result.ResultMode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@RestController
@RequestMapping("/admin/acl/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public ResultMode index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestParam(name = "role", required = false) Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        // if(!StringUtils.isEmpty(role.getRoleName())) {
        //     wrapper.like("role_name",role.getRoleName());
        // }
        // log.info("roleName:{}", role.getRoleName());
        roleService.page(pageParam,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pageParam.getTotal());
        map.put("item", pageParam.getRecords());
        return ResultMode.ok(map);
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public ResultMode get(@PathVariable String id) {
        Role role = roleService.getById(id);
        HashMap<String, Role> map = new HashMap<>();
        map.put("item", role);
        return ResultMode.ok(map);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public ResultMode save(@RequestBody Role role) {
        roleService.save(role);
        return ResultMode.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public ResultMode updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return ResultMode.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public ResultMode remove(@PathVariable String id) {
        roleService.removeById(id);
        return ResultMode.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public ResultMode batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return ResultMode.ok();
    }
}

