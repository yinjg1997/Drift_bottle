package cn.edu.zust.cms;

import cn.edu.zust.cms.entity.Banner;
import cn.edu.zust.cms.service.BannerService;
import cn.edu.zust.common.utils.DevHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestCMS {
    @Autowired
    private BannerService bannerService;

    @Test
    void insertData() {
        Banner banner = Banner.getInstance();
        for (int i = 0; i <20; i++) {
            String uuid = DevHelper.getUUID();
            banner.setId(uuid);
            banner.setTitle(banner.getTitle() + i);
            bannerService.save(banner);
        }

    }


}
