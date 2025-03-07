package io.code.example.consumer.file.excel;

import io.code.framework.fastexcel.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zongz
 * @Date: 2024-12-21
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @PostMapping(value = "/import")
    public List<ImportModel> read(@RequestParam("excel") MultipartFile excel) throws IOException {
        return ExcelUtil.readExcel(excel.getInputStream(), excel.getOriginalFilename(), ImportModel.class);
    }

    @GetMapping(value = "/export")
    public void writeExcel(HttpServletResponse response) {
        List<ExportModel> list = getList();
        String fileName = "Excel导出测试";
        String sheetName = "sheet1";
        ExcelUtil.writeExcel(response, list, fileName, sheetName, ExportModel.class);
    }

    private List<ExportModel> getList() {
        List<ExportModel> modelList = new ArrayList<>();
        ExportModel firstModel = new ExportModel();
        firstModel.setName("李明");
        firstModel.setSex("男");
        firstModel.setAge(20);
        modelList.add(firstModel);
        ExportModel secondModel = new ExportModel();
        secondModel.setName("珍妮");
        secondModel.setSex("女");
        secondModel.setAge(19);
        modelList.add(secondModel);
        return modelList;
    }
}
