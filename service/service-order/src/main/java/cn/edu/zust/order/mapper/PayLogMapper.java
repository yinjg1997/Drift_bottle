package cn.edu.zust.order.mapper;

import cn.edu.zust.order.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

}
