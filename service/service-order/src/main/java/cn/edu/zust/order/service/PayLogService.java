package cn.edu.zust.order.service;

import cn.edu.zust.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-07-05
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);

    Map createWXPayCode(String orderNo);
}
