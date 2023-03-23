package cn.edu.zust.acl.mapper;

import cn.edu.zust.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("<script>" +
            "select " +
            "        permission_value " +
            "        from acl_permission " +
            "        where type = 2 " +
            "        and is_deleted = 0" +
            "</script>")
    List<String> selectAllPermissionValue();

    @Select("<script>" +
            "select " +
            "        p.permission_value " +
            "        from acl_user_role ur " +
            "        inner join acl_role_permission rp on rp.role_id = ur.role_id " +
            "        inner join acl_permission p on p.id = rp.permission_id " +
            "        where ur.user_id = #{userId} " +
            "        and p.type = 2 " +
            "        and ur.is_deleted = 0 " +
            "        and rp.is_deleted = 0 " +
            "        and p.is_deleted = 0" +
            "</script>")
    List<String> selectPermissionValueByUserId(String id);

    @Select("<script> " +
            "select" +
            "        p.id,p.pid,p.name,p.type,p.permission_value,path,p.component," +
            "        p.icon,p.status,p.is_deleted,p.gmt_create,p.gmt_modified " +
            "        from acl_user_role ur" +
            "        inner join acl_role_permission rp on rp.role_id = ur.role_id" +
            "        inner join acl_permission p on p.id = rp.permission_id" +
            "        where ur.user_id = #{userId}" +
            "        and ur.is_deleted = 0" +
            "        and rp.is_deleted = 0" +
            "        and p.is_deleted = 0" +
            "</script>")
    List<Permission> selectPermissionByUserId(String userId);
}
