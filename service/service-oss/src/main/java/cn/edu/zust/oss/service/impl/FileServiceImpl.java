package cn.edu.zust.oss.service.impl;

import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.oss.service.FileService;
import cn.edu.zust.oss.utils.ConstantPropertiesUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;

import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;


@Service
@Slf4j
public class FileServiceImpl implements FileService {



    private static final String[] TYPESTR = {".png",".jpg",".bmp",".gif",".jpeg"};

    @Override
    public String upload(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        OSS ossClient = null;
        String url = null;

        try {
            // 创建OSS实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            boolean flag = false;
            //判断文件格式
            for(String type : TYPESTR){
                if(StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                log.error("上传的图片格式不正确");
                return null;
            }
            //判断文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image != null){
                System.err.println(String.valueOf(image.getHeight()));
                System.err.println(String.valueOf(image.getWidth()));
            } else{
                log.error("上传文件时发生错误,文件内容不正确");
                return null;
            }


            //获取文件名称
            String fileName = file.getOriginalFilename();
            //文件名字： lijin.shuai.jpg
            fileName = fileName.substring(fileName.lastIndexOf("."));
            //1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //2 把文件按照日期进行分类, 获取当前日期 2019/11/12
            Calendar cal = Calendar.getInstance();
            String yearMonthDay = cal.get(Calendar.YEAR) + "/"
                    + (cal.get(Calendar.MONTH) + 1) + "/"
                    + cal.get(Calendar.DATE);
            //拼接  2019/11/12/ewtqr313401.jpg
            fileName = yearMonthDay+"/"+fileName;

            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName , inputStream);

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://for-mustang.oss-cn-hangzhou.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20220614170537.png
            url = "https://"+bucketName+"."+endpoint+"/"+fileName;
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return url;
    }
}
