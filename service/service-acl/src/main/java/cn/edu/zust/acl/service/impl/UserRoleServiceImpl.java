package cn.edu.zust.acl.service.impl;

import cn.edu.zust.acl.entity.User;
import cn.edu.zust.acl.entity.UserRole;
import cn.edu.zust.acl.mapper.UserRoleMapper;
import cn.edu.zust.acl.service.PermissionService;
import cn.edu.zust.acl.service.UserRoleService;
import cn.edu.zust.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     // 从数据库中取出用户信息
    //     User user = userService.selectByUsername(username);
    //
    //     // 判断用户是否存在
    //     if (null == user){
    //         //throw new UsernameNotFoundException("用户名不存在！");
    //     }
    //     // 返回UserDetails实现类
    //     com.luozongyao.security.entity.User curUser = new com.luozongyao.security.entity.User();
    //     BeanUtils.copyProperties(user,curUser);
    //
    //     List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
    //     SecurityUser securityUser = new SecurityUser(curUser);
    //     securityUser.setPermissionValueList(authorities);
    //     return securityUser;
    // }
}
