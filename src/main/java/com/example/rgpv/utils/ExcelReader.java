package com.example.rgpv.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public static List<String> readRollNumbers(String path) throws IOException {
        List<String> rolls = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();
                Cell cell = row.getCell(0); // column A
                if (cell != null) {
                    cell.setCellType(CellType.STRING);
                    String roll = cell.getStringCellValue().trim();
                    if (!roll.isEmpty()) {
                        rolls.add(roll);
                    }
                }
            }
        }
        return rolls;
    }
}
