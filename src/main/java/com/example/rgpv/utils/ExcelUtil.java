package com.example.rgpv.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    private static String excelPath = "C:\\Users\\vivek\\Testing\\ResultXtractor\\ResultXtractor\\src\\test\\resources\\rolls.xlsx";

    public static void writeResult(String sheetName, String rollNo, String name, String semester, String cgpa) throws IOException {
        File file = new File(excelPath);

        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        fis.close();

        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Enrollment No");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Course");
            header.createCell(3).setCellValue("Semester");
            header.createCell(4).setCellValue("SGPA");
            header.createCell(5).setCellValue("CGPA");
        }

        boolean updated = false;
        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(0).getStringCellValue().equals(rollNo)) {
                row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(name);
                row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("MCA 2 Year");
                row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(semester);
                row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(cgpa);
                row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(cgpa);
                updated = true;
                break;
            }
        }

        // if roll no not found → add new row
        if (!updated) {
            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(rollNo);
            row.createCell(1).setCellValue(cgpa);
        }

        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
