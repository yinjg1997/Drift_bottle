package cn.edu.zust.edu.service.impl;

import cn.edu.zust.edu.entity.Comment;
import cn.edu.zust.edu.mapper.CommentMapper;
import cn.edu.zust.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评价表 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
