package cn.edu.zust.edu.service.impl;

import cn.edu.zust.edu.entity.vo.AdminVo;
import cn.edu.zust.edu.service.AdminService;
import cn.edu.zust.edu.entity.Admin;
import cn.edu.zust.edu.mapper.AdminMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-26
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin getByName(AdminVo adminVo) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        if (!StringUtils.isEmpty(adminVo.getName())) {
            queryWrapper.eq("name", adminVo.getName());
            return baseMapper.selectOne(queryWrapper);
        } else {
            return null;
        }
    }
}
