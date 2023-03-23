package cn.edu.zust;

import cn.edu.zust.edu.entity.Admin;
import cn.edu.zust.edu.entity.EduTeacher;
import cn.edu.zust.edu.entity.query.TeacherQuery;
import cn.edu.zust.edu.entity.vo.AdminVo;
import cn.edu.zust.edu.service.EduTeacherService;
import cn.edu.zust.edu.service.impl.AdminServiceImpl;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EduServiceTests {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private AdminServiceImpl adminService;


    @Test
    void getByName() {
        // String encrypt = MD5.encrypt("123123");
        // Admin admin = new Admin();
        // admin.setId(DevHelper.getUUID());
        // admin.setName("admin1");
        // admin.setPassword(encrypt);
        // admin.setGmtCreate(LocalDateTime.now());
        // admin.setGmtModified(LocalDateTime.now());
        // boolean b = adminService.save(admin);
        // System.out.println(b);

        AdminVo admin2 = new AdminVo();
        admin2.setName("admin");
        Admin admin1 = adminService.getByName(admin2);
        System.out.println(admin1);

    }
    @Test
    void updateById() {
        EduTeacher instance = EduTeacher.getInstance();
        instance.setName("changed");
        teacherService.updateById(instance);
    }
    @Test
    void saveTeacher() {
        EduTeacher instance = EduTeacher.getInstance();
        teacherService.saveTeacher(instance);
    }
    @Test
    void context() {
        TeacherQuery teacherQuery = new TeacherQuery("王", 1, "2019-01-01 10:10:10", "2023-12-01 10:10:10");
        PageInfo<EduTeacher> teacherPageInfo = teacherService.selectTeacherByQuery(1, 5, null);
        System.out.println(teacherPageInfo.getTotal());
        PageInfo<EduTeacher> teacherPageInfo1 = teacherService.selectTeacherByQuery(1, 5, teacherQuery);
        System.out.println(teacherPageInfo1.getList());
    }

    @Test
    void context2() {
        BatisUtils.getMybatisBoot("grain","root","admin",
                "edu_teacher");
    }


}
