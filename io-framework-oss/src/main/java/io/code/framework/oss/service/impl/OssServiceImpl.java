package io.code.framework.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.ResponseHeaderOverrides;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import io.code.framework.oss.config.OssProperties;
import io.code.framework.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * @Author: zongz
 * @Date: 2024/11/13
 */
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssProperties ossProperties;

    @Override
    public PutObjectResult putObject(String key, File file) {
        PutObjectResult putObjectResult = ossClient.putObject(ossProperties.getBucketName(), key, file);
        return putObjectResult;
    }

    @Override
    public PutObjectResult putObjectRequest(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), key, file);
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        return putObjectResult;
    }

    @Override
    public UploadFileResult uploadFile(String key, File file) throws Throwable {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(ossProperties.getBucketName(), key);
        uploadFileRequest.setUploadFile(file.getAbsolutePath());
        UploadFileResult uploadFileResult = ossClient.uploadFile(uploadFileRequest);
        return uploadFileResult;
    }

    @Override
    public OSSObject getObject(String key) {
        OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), key);
        return ossObject;
    }

    @Override
    public DownloadFileResult downloadFile(String key) throws Throwable {
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(ossProperties.getBucketName(), key);
        downloadFileRequest.setDownloadFile("a.txt");
        DownloadFileResult downloadFileResult = ossClient.downloadFile(downloadFileRequest);
        return downloadFileResult;
    }

    @Override
    public URL generatePresignedUrl(String key, Date date) {
        URL url = ossClient.generatePresignedUrl(ossProperties.getBucketName(), key, date);
        return url;
    }

    @Override
    public URL generatePresignedUrlcontentType(String key, Date date) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(ossProperties.getBucketName(), key);
        generatePresignedUrlRequest.setExpiration(date);
        ResponseHeaderOverrides responseHeaderOverrides = new ResponseHeaderOverrides();
        String contentType = MediaTypeFactory.getMediaType(key).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        // 当需要在浏览器里直接下载时，设置content-type为application/octet-stream，浏览器会直接下载文件，而不是打开文件
        responseHeaderOverrides.setContentType(contentType.toUpperCase());
        generatePresignedUrlRequest.setResponseHeaders(responseHeaderOverrides);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url;
    }

}
