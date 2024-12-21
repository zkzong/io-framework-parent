package io.code.framework.fastexcel;

import cn.hutool.core.convert.Convert;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.write.metadata.WriteSheet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel读写工具类
 *
 * @Author: zongz
 * @Date: 2024-12-21
 */
public class ExcelUtil {

    /**
     * 读取Excel（多个sheet可以用同一个实体类解析）
     *
     * @param excelInputStream
     * @param fileName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcel(InputStream excelInputStream, String fileName, Class<T> clazz) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = getReader(excelInputStream, fileName, clazz, excelListener);
        if (excelReader == null) {
            return new ArrayList<>();
        }
        List<ReadSheet> readSheetList = excelReader.excelExecutor().sheetList();
        for (ReadSheet readSheet : readSheetList) {
            excelReader.read(readSheet);
        }
        excelReader.finish();
        return Convert.toList(clazz, excelListener.getDataList());
    }

    /**
     * 导出Excel(一个sheet)
     *
     * @param response  HttpServletResponse
     * @param list      数据list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的sheet名
     * @param clazz     实体类
     */
    public static <T> void writeExcel(HttpServletResponse response, List<T> list, String fileName, String sheetName, Class<T> clazz) {

        OutputStream outputStream = getOutputStream(response, fileName);
        ExcelWriter excelWriter = EasyExcel.write(outputStream, clazz).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }


    /**
     * 导出时生成OutputStream
     */
    private static OutputStream getOutputStream(HttpServletResponse response, String fileName) {
        //创建本地文件
        String filePath = fileName + ".xlsx";
        File file = new File(filePath);
        try {
            if (!file.exists() || file.isDirectory()) {
                file.createNewFile();
            }
            fileName = new String(filePath.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回ExcelReader
     *
     * @param inputStream   文件流
     * @param filename      文件名
     * @param clazz         实体类
     * @param excelListener
     */
    private static <T> ExcelReader getReader(InputStream inputStream, String filename, Class<T> clazz, ExcelListener excelListener) {
        try {
            if (filename == null ||
                    (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
                return null;
            }
            ExcelReader excelReader = EasyExcel.read(inputStream, clazz, excelListener).build();
            inputStream.close();
            return excelReader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
