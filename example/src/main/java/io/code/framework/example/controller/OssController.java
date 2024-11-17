package io.code.framework.example.controller;

import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileResult;
import io.code.framework.core.resp.Result;
import io.code.framework.core.resp.ResultUtil;
import io.code.framework.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * OssController
 *
 * @Author: zong
 * @Date: 2021/9/3
 */
@RestController
@RequestMapping("/oss")
@Slf4j
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/putObject")
    public Result<PutObjectResult> putObject(String key, File file) {
        PutObjectResult putObjectResult = ossService.putObject(key, file);
        return ResultUtil.success(putObjectResult);
    }

    @PostMapping("/putObjectRequest")
    public Result<PutObjectResult> putObjectRequest(String key, File file) {
        PutObjectResult putObjectResult = ossService.putObjectRequest(key, file);
        return ResultUtil.success(putObjectResult);
    }

    @PostMapping("/uploadFile")
    public Result<UploadFileResult> uploadFile(String key, File file) {
        UploadFileResult uploadFileResult = null;
        try {
            uploadFileResult = ossService.uploadFile(key, file);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return ResultUtil.success(uploadFileResult);
    }

    @PostMapping("/getObject")
    public Result<OSSObject> getObject(String key) {
        OSSObject ossObject = ossService.getObject(key);
        return ResultUtil.success(ossObject);
    }

    @PostMapping("/downloadFile")
    public Result<DownloadFileResult> downloadFile(String key) {
        DownloadFileResult downloadFileResult = null;
        try {
            downloadFileResult = ossService.downloadFile(key);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return ResultUtil.success(downloadFileResult);
    }

    @PostMapping("/generatePresignedUrl")
    public Result<URL> generatePresignedUrl(String key, Date date) {
        URL url = ossService.generatePresignedUrl(key, date);
        return ResultUtil.success(url);
    }

    @PostMapping("/generatePresignedUrlcontentType")
    public Result<URL> generatePresignedUrlcontentType(String key, Date date) {
        URL url = ossService.generatePresignedUrlcontentType(key, date);
        return ResultUtil.success(url);
    }

}
