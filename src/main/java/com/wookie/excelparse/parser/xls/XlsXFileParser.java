package com.wookie.excelparse.parser.xls;

import com.wookie.excelparse.model.GooglePlayRes;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class XlsXFileParser {


    public void writeXLSXFile(Collection toWriteInFile, String fileName) throws IOException {

        String sheetName = "Sheet1";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        Field[] allFields = toWriteInFile.getClass().getDeclaredFields();

        //iterating r number of rows
//        int i = 1;
//        for (GooglePlayRes entry : googlePlayRes)
//        {
//            XSSFRow row = sheet.createRow(i);
//
//            XSSFCell cell = row.createCell(1);
//            cell.setCellValue(entry.getName());
//            cell = row.createCell(2);
//            cell.setCellValue(entry.getResult());
//
//            i++;
//        }
//
//        FileOutputStream fileOut = new FileOutputStream(fileName);
//
//        //write this workbook to an Outputstream.
//        wb.write(fileOut);
//        fileOut.flush();
//        fileOut.close();
    }

}
