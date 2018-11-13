package com.wookie.excelparse.parser;

import com.wookie.excelparse.model.Bloacklauncher;
import com.wookie.excelparse.model.GooglePlayRes;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ReadWriteExcelFile {

    private Map<String, Bloacklauncher> parsed = new LinkedHashMap<>();

    public void readXLSFile() throws IOException
    {
        InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

        HSSFSheet sheet=wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();

        while (rows.hasNext())
        {
            row=(HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext())
            {
                cell=(HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
                {
                    System.out.print(cell.getStringCellValue()+" ");
                }
                else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                {
                    System.out.print(cell.getNumericCellValue()+" ");
                }
                else
                {
                    //U Can Handel Boolean, Formula, Errors
                }
            }
            System.out.println();
        }

    }

    public void writeXLSFile() throws IOException {

        String excelFileName = "C:/Test.xls";//name of excel file

        String sheetName = "Sheet1";//name of sheet

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName) ;

        //iterating r number of rows
        for (int r=0;r < 5; r++ )
        {
            HSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c=0;c < 5; c++ )
            {
                HSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell "+r+" "+c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public void readXLSXFile(String filePath) throws IOException
    {
        InputStream ExcelFileToRead = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

        XSSFWorkbook test = new XSSFWorkbook();

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();

        int rowCount = 1;
//        while (rows.hasNext())
        while(rowCount <= 69)
        {
            row=(XSSFRow) rows.next();
            Iterator cells = row.cellIterator();


            cell=(XSSFCell) cells.next();
            String type = cell.getStringCellValue();

            while (cells.hasNext())
            {
                cell=(XSSFCell) cells.next();

                Bloacklauncher temp;
//                System.out.println("aaaa: " + cell.getStringCellValue());
                if(parsed.get(cell.getStringCellValue()) != null) {
                    temp = parsed.get(cell.getStringCellValue().toLowerCase());
                } else {
                    temp = new Bloacklauncher();
                }

                temp.setName(cell.getStringCellValue());
                temp.setType(type);
                if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    if (cell.getCellComment() != null) {
                        temp.setAppleLink(cell.getCellComment().getString().getString());
                    }
                    if(cell.getCellFormula().contains("play.google.com")) {
                        temp.setGoogleLink(cell.getCellFormula());
                    }
                    temp.setAppleUrl(cell.getCellFormula());
                    temp.setGoogleUrl(cell.getCellFormula());

                }

                parsed.put(temp.getName().toLowerCase(), temp);
            }

            rowCount++;
        }

    }

    public void writeXLSXFile(List<GooglePlayRes> googlePlayRes, String fileName) throws IOException {

        String excelFileName = fileName;//name of excel file

        String sheetName = "Sheet1";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName) ;

        //iterating r number of rows
        int i = 1;
//        for (Map.Entry<String, Bloacklauncher> entry : parsed.entrySet())
        for (GooglePlayRes entry : googlePlayRes)
        {
            XSSFRow row = sheet.createRow(i);

            XSSFCell cell = row.createCell(1);
            cell.setCellValue(entry.getName());
            cell = row.createCell(2);
            cell.setCellValue(entry.getResult());

//            XSSFCell cell = row.createCell(1);
//            cell.setCellValue(entry.getValue().getName());
//            cell = row.createCell(2);
//            cell.setCellValue(entry.getValue().getType());
//            cell = row.createCell(3);
//            cell.setCellValue(entry.getValue().getAppleUrl());
//            cell = row.createCell(4);
//            cell.setCellValue(entry.getValue().getGoogleUrl());
//            cell = row.createCell(5);
//            cell.setCellValue(entry.getValue().getAppleLink());
//            cell = row.createCell(6);
//            cell.setCellValue(entry.getValue().getGoogleLink());

            i++;
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public Map<String, Bloacklauncher> getParsed() {
        return parsed;
    }

    public void setParsed(Map<String, Bloacklauncher> parsed) {
        this.parsed = parsed;
    }
}
