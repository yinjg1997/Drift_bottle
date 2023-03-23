package cn.edu.zust.edu.mapper;

import cn.edu.zust.edu.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评价表 Mapper 接口
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-04
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
