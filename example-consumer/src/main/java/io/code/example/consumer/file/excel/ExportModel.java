package io.code.example.consumer.file.excel;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * 导出模型
 *
 * @Author: zongz
 * @Date: 2024-12-21
 */
@ContentRowHeight(20)
@HeadRowHeight(25)
@ColumnWidth(25)
@Data
public class ExportModel {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "性别", index = 1)
    private String sex;

    @ExcelProperty(value = "年龄", index = 2)
    private Integer age;

}
