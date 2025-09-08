package com.example.rgpv.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    private static String inputPath = "C:\\Users\\vivek\\Testing\\ResultXtractor\\ResultXtractor\\src\\test\\resources\\rolls.xlsx";
    private static String outputPath = "C:\\Users\\vivek\\Testing\\ResultXtractor\\ResultXtractor\\src\\main\\resources\\testData\\results.xlsx";

    //read
    public static Object[][] getRollNumbers(String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(inputPath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        Object[][] data = new Object[rowCount - 1][1];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue();
        }
        workbook.close();
        fis.close();
        return data;
    }

    //write
    public static void writeResult(String rollNo, String cgpa) throws IOException {
        Workbook workbook;
        Sheet sheet;
        File file = new File(outputPath);

        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            fis.close();
            sheet = workbook.getSheet("Results");
            if (sheet == null) {
                sheet = workbook.createSheet("Results");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Roll No");
                header.createCell(1).setCellValue("CGPA");
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Results");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Roll No");
            header.createCell(1).setCellValue("CGPA");
        }

        int lastRow = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRow + 1);
        row.createCell(0).setCellValue(rollNo);
        row.createCell(1).setCellValue(cgpa);

        FileOutputStream fos = new FileOutputStream(outputPath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
