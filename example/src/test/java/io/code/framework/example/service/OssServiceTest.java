package io.code.framework.example.service;

import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.net.URL;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OssServiceTest {

    @Autowired
    private OssService ossService;

    private File file;

    @BeforeEach
    public void setUp() {
        String path = this.getClass().getClassLoader().getResource(".").getPath();
        file = new File(path + "/baidu.png");
    }

    @Test
    public void putObject_ValidFile_ReturnsNotNull() {
        PutObjectResult result = ossService.putObject("dev/testing/", file);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    public void uploadFile_ValidFile_ReturnsNotNull() {
        UploadFileResult result = null;
        try {
            result = ossService.uploadFile("test.txt", file);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        assertNotNull(result);
    }

    @Test
    public void getObject_ValidKey_ReturnsNotNull() {
        OSSObject result = ossService.getObject("test.txt");
        assertNotNull(result);
    }

    @Test
    public void downloadFile_ValidKey_ReturnsNotNull() {
        DownloadFileResult result = null;
        try {
            result = ossService.downloadFile("test.txt");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        assertNotNull(result);
    }

    @Test
    public void generatePresignedUrl_ValidKeyAndDate_ReturnsNotNull() {
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url = ossService.generatePresignedUrl("test.txt", expiration);
        assertNotNull(url);
    }

    @Test
    public void generatePresignedUrlContentType_ValidKeyAndDate_ReturnsNotNull() {
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        URL url = ossService.generatePresignedUrlcontentType("test.txt", expiration);
        assertNotNull(url);
    }
}
