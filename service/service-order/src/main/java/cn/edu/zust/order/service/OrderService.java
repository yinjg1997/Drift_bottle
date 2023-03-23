package cn.edu.zust.order.service;

import cn.edu.zust.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberIdByJwtToken);

}
