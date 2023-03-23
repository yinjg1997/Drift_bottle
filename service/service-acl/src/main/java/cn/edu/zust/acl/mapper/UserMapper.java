package cn.edu.zust.acl.mapper;

import cn.edu.zust.acl.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
