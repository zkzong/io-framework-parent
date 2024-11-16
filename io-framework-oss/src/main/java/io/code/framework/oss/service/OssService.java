package io.code.framework.oss.service;

import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileResult;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * @Author: zongz
 * @Date: 2024/11/13
 */
public interface OssService {

    PutObjectResult putObject(String key, File file);

    PutObjectResult putObjectRequest(String key, File file);

    UploadFileResult uploadFile(String key, File file) throws Throwable;

    OSSObject getObject(String key);

    DownloadFileResult downloadFile(String key) throws Throwable;

    URL generatePresignedUrl(String key, Date date);

    URL generatePresignedUrlcontentType(String key, Date date);
}
