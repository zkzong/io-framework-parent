package io.code.example.consumer.oss;

import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileResult;
import io.code.framework.core.entity.ApiResponse;
import io.code.framework.core.entity.ApiResponseUtil;
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
    public ApiResponse<PutObjectResult> putObject(String key, File file) {
        PutObjectResult putObjectResult = ossService.putObject(key, file);
        return ApiResponseUtil.success(putObjectResult);
    }

    @PostMapping("/putObjectRequest")
    public ApiResponse<PutObjectResult> putObjectRequest(String key, File file) {
        PutObjectResult putObjectResult = ossService.putObjectRequest(key, file);
        return ApiResponseUtil.success(putObjectResult);
    }

    @PostMapping("/uploadFile")
    public ApiResponse<UploadFileResult> uploadFile(String key, File file) {
        UploadFileResult uploadFileResult = null;
        try {
            uploadFileResult = ossService.uploadFile(key, file);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return ApiResponseUtil.success(uploadFileResult);
    }

    @PostMapping("/getObject")
    public ApiResponse<OSSObject> getObject(String key) {
        OSSObject ossObject = ossService.getObject(key);
        return ApiResponseUtil.success(ossObject);
    }

    @PostMapping("/downloadFile")
    public ApiResponse<DownloadFileResult> downloadFile(String key) {
        DownloadFileResult downloadFileResult = null;
        try {
            downloadFileResult = ossService.downloadFile(key);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return ApiResponseUtil.success(downloadFileResult);
    }

    @PostMapping("/generatePresignedUrl")
    public ApiResponse<URL> generatePresignedUrl(String key, Date date) {
        URL url = ossService.generatePresignedUrl(key, date);
        return ApiResponseUtil.success(url);
    }

    @PostMapping("/generatePresignedUrlcontentType")
    public ApiResponse<URL> generatePresignedUrlcontentType(String key, Date date) {
        URL url = ossService.generatePresignedUrlcontentType(key, date);
        return ApiResponseUtil.success(url);
    }

}
