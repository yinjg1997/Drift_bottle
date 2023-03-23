package cn.edu.zust.oss.controller;


import cn.edu.zust.common.exception.BusinessException;
import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import cn.edu.zust.oss.service.FileService;
import cn.edu.zust.oss.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/ossservice")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @return 图片地址
     *
     * 上传文件使用 @RequestPart, swagger3.0 才能识别上传文件
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("file/upload")
    public ResultMode upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestPart("file") MultipartFile file,

            @ApiParam(name = "host", value = "文件上传路径", required = false)
            @RequestParam(value = "host", required = false) String host) {


        //调用阿里云OSS上传的程序
        String uploadUrl = fileService.upload(file);
        if (uploadUrl==null)
            throw new BusinessException(ResponseCode.UPLOAD_ERROR);
        HashMap<String, String> map = new HashMap<>();
        map.put("url", uploadUrl);
        //返回r对象
        return ResultMode.ok(map);
    }
}
