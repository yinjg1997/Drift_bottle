package cn.edu.zust.cms.controller;



import cn.edu.zust.cms.entity.Banner;
import cn.edu.zust.cms.service.BannerService;
import cn.edu.zust.common.result.ResultMode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前台系统，banner展示
 * </p>
 *
 */
@Api(tags = "前台系统banner展示API")
@RestController
@RequestMapping("/cmsservice/frontbanner")
public class FrontBannerController {

    @Autowired
    private BannerService bannerService;

    //查询出最近更新的四个banner（若不足4个，查询所有）
    @ApiOperation(value = "查询出最近更新的四个banner（若不足4个，查询所有）")
    @GetMapping("getBanners")
    public ResultMode getBanners(){
        List<Banner> list = bannerService.getBanners();
        HashMap<Object, List<Banner>> map = new HashMap<>();
        map.put("list", list);
        return ResultMode.ok(map);
    }

}
