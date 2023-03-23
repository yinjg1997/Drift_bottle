package cn.edu.zust.edu.controller.front;

import cn.edu.zust.common.jwt.JwtUtils;
import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.edu.client.UcenterClient;
import cn.edu.zust.edu.entity.Comment;
import cn.edu.zust.edu.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/frontComment/")
@Slf4j
public class FrontCommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public ResultMode index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            String courseId) {
        Page<Comment> pageParam = new Page<>(page, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(pageParam,wrapper);
        List<Comment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        HashMap<String, Object> res = new HashMap<>();
        res.put("res", map);
        return ResultMode.ok(res);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("auth/save")
    public ResultMode save(@RequestBody Comment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        log.info("memberId: {}", memberId);
        if(StringUtils.isEmpty(memberId)) {
            return ResultMode.error(ResponseCode.USER_NOT_LOGGED_IN);
        }
        comment.setMemberId(memberId);
        log.info("comment: {}", comment);
        ResultMode<HashMap<String, String>> ucenterInfo = ucenterClient.getMemberInfoForComment(memberId);
        HashMap<String, String> data = ucenterInfo.getData();

        comment.setNickname(data.get("nickName"));
        comment.setAvatar(data.get("avatar"));
        comment.setGmtCreate(LocalDateTime.now());
        comment.setGmtModified(LocalDateTime.now());
        commentService.save(comment);
        return ResultMode.ok();
    }
}
