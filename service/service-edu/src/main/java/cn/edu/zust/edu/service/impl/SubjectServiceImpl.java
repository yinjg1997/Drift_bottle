package cn.edu.zust.edu.service.impl;

import cn.edu.zust.edu.entity.Subject;
import cn.edu.zust.edu.entity.vo.OneSubject;
import cn.edu.zust.edu.entity.vo.TwoSubject;
import cn.edu.zust.edu.mapper.SubjectMapper;
import cn.edu.zust.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author JianGuo Yin
 * @since 2022-06-28
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public List<String> importExcel(MultipartFile file) {

        //存储错误信息集合
        List<String> meg = new ArrayList<>();
        try {
            //1、获取文件流
            InputStream inputStream = file.getInputStream();
            //2、根据流创建workBook
            Workbook workbook = new HSSFWorkbook(inputStream);
            //3、获取sheet.getSheetAt(0)
            Sheet sheet = workbook.getSheetAt(0);
            //4、根据sheet获取行数
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum <= 1){
                meg.add("请填写数据");
                return meg;
            }
            //5、遍历行
            for (int rowNum = 1; rowNum < lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(0);
                //6、获取每一行的第一列：一级分类
                if(cell == null ){
                    meg.add("第" + rowNum + "行第1列为空");
                    continue;
                }
                String cellValue = cell.getStringCellValue();
                if(StringUtils.isEmpty(cellValue)){
                    meg.add("第" + rowNum + "行第1列为数据空");
                    continue;
                }

                //7、判断列是否存在，存在获取的数据
                Subject subject = this.selectSubjectByName(cellValue);
                String pid = "";
                //8、把这一列中的数据（一级分类）保存到数据库中
                if(subject == null){
                    //9、在保存之前判断此一级分类是否存在，如果在就不再添加；如果不存在就保存数据
                    Subject su = new Subject();
                    su.setTitle(cellValue);
                    su.setParentId("0");
                    su.setSort(0);
                    baseMapper.insert(su);
                    pid = su.getId();
                } else {
                    pid = subject.getId();
                }

                //10、再获取每一行的第二列
                Cell cell_1 = row.getCell(1);
                //11、获取第二列中的数据（二级分类）
                if(cell_1 == null){
                    meg.add("第" + rowNum + "行第2列为空");
                    continue;
                }
                String stringCellValue = cell_1.getStringCellValue();
                if(StringUtils.isEmpty(stringCellValue)){
                    meg.add("第" + rowNum + "行第2列为数据空");
                    continue;
                }
                //12、判断此一级分类中是否存在此二级分类
                Subject subject_1 = this.selectSubjectByNameAndParentId(stringCellValue,pid);
                //13、如果此一级分类中有此二级分类：不保存
                if(subject_1 == null){
                    Subject su = new Subject();
                    su.setTitle(stringCellValue);
                    su.setParentId(pid);
                    su.setSort(0);
                    baseMapper.insert(su);
                }
                //14、如果没有则保存
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return meg;
    }

    @Override
    public List<OneSubject> getTree() {
        //1、创建一个集合存放OneSubject
        List<OneSubject> oneSubjects = new ArrayList<>();
        //2、获取一级分类的列表
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        List<Subject> SubjectsList = baseMapper.selectList(wrapper);
        //3、遍历一级分类的列表
        for (Subject subject : SubjectsList) {
            //4、把一级分类对象复制到OneObject
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);
            //5、根据每一个一级分类获取二级分类的列表
            QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", oneSubject.getId());
            List<Subject> Subjects = baseMapper.selectList(queryWrapper);
            //6、遍历二级分类列表
            for (Subject su: Subjects) {
                //7、把二级分类对象复制到TwoObject
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(su, twoSubject);
                //8、把TwoObject添加到OneObject的children集合属性中
                oneSubject.getChildren().add(twoSubject);
            }
            //9、把OneSubject添加到集合中
            oneSubjects.add(oneSubject);
        }

        return oneSubjects;
    }

    @Override
    public boolean deleteById(String id) {
        //根据ID查询数据库中是否存在此ID为ParentId（二级分类）
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Subject> subjects = baseMapper.selectList(wrapper);
        if(subjects.size() != 0){
            return false;
        }
        int i = baseMapper.deleteById(id);
        return i == 1;
    }


    @Override
    public boolean saveLevelOne(Subject subject) {

        Subject subjectLevelOne = this.selectSubjectByName(subject.getTitle());

        if(subjectLevelOne == null){
            return super.save(subject);
        }

        return false;
    }

    @Override
    public boolean saveLevelTwo(Subject subject) {
        //判断此一级分类中是否存在此二级分类的title
        Subject sub = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if(sub != null){
            //存在
            return false;
        }
        int insert = baseMapper.insert(subject);
        return insert == 1;
    }


    /**
     * 根据课程分类的名字和父类ID查询分类是否存在
     * @param stringCellValue
     * @param pid
     * @return
     */
    private Subject selectSubjectByNameAndParentId(String stringCellValue, String pid) {
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title", stringCellValue);
        subjectQueryWrapper.eq("parent_id", pid);
        Subject subject = baseMapper.selectOne(subjectQueryWrapper);
        return subject;
    }

    /**
     * 根据课程分类的名字查询分类是否存在
     * @param cellValue
     * @return
     */
    private Subject selectSubjectByName(String cellValue) {
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title", cellValue);
        subjectQueryWrapper.eq("parent_id", 0);
        Subject subject = baseMapper.selectOne(subjectQueryWrapper);
        return subject;
    }
}
