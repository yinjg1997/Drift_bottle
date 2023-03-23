package cn.edu.zust.edu.service;

import cn.edu.zust.edu.entity.vo.AdminVo;
import cn.edu.zust.edu.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-26
 */
public interface AdminService extends IService<Admin> {

    Admin getByName(AdminVo adminVo);
}
