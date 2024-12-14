package io.code.framework.example.controller.file;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

/**
 * @Author: zongz
 * @Date: 2024-12-14
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /***
     * todo 文件乱码
     * 通过流式处理方式，能够显著优化导出性能
     */
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                URLEncoder.encode("导出数据_" + System.currentTimeMillis() + ".csv", StandardCharsets.UTF_8));
        StreamingResponseBody stream = outputStream -> {
            String header = "ID,名称,描述\n";
            outputStream.write(header.getBytes(StandardCharsets.UTF_8));
            IntStream.range(1, 10001).forEach(i -> {
                try {
                    String row = i + ",名称" + i + ",描述" + i + "\n";
                    outputStream.write(row.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            outputStream.flush();
        };

        try (OutputStream os = response.getOutputStream()) {
            stream.writeTo(os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
