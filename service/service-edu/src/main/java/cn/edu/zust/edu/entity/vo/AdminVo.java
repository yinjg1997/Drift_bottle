package cn.edu.zust.edu.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_admin")
@ApiModel(value="Admin对象", description="管理员")
public class AdminVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员名称")
    private String name;

    @ApiModelProperty(value = "管理员密码")
    private String password;

}
