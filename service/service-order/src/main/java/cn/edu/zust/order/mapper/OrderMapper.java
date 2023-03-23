package cn.edu.zust.order.mapper;

import cn.edu.zust.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
