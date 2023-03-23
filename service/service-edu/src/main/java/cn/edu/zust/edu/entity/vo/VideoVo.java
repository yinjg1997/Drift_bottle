package cn.edu.zust.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value="课程小节", description="课程小节")
@Data
public class VideoVo {

    @ApiModelProperty(value = "课程小节ID")
    private String id;

    @ApiModelProperty(value = "课程小节名称")
    private String title;

    @ApiModelProperty(value = "云端视频ID")
    private String videoSourceId;//云端视频ID

}
