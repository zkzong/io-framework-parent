package io.code.example.consumer.file.excel;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 导入模型
 *
 * @Author: zongz
 * @Date: 2024-12-21
 */
@Data
public class ImportModel {

    @ExcelProperty(index = 0)
    private String date;

    @ExcelProperty(index = 1)
    private String author;

    @ExcelProperty(index = 2)
    private String book;

}
