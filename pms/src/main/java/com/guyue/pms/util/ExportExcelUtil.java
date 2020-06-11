package com.guyue.pms.util;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.guyue.pms.bean.ExcelData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import javax.servlet.http.HttpServletResponse;

public class ExportExcelUtil {
    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }



    public static void exportExcel(ExcelData data, OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);

            wb.write(out);
        } finally {
            wb.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {

        int rowIndex = 0;

        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex,data.getCols(),data.getCol());
        autoSizeColumns(sheet, data.getTitles().size() + 1);

    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;

        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192)));
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<String>> rows, int rowIndex,List<Integer> cols,Integer col) {
        // 单元格合并
       /* List<Integer> cols  = Arrays.asList(0,1);
        Integer col = 0 ; //合并的参考列*/
        String tmp = rows.get(0).get(col);
        List<int[]> row = new ArrayList<>();
        int [] subrow = new int[2];
        subrow[0] = rowIndex;


        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0)));

        for (List<String> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (String cellData : rowData) {

                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    if(colIndex==col&&!cellData.equals(tmp)){
                        subrow[1] = rowIndex-1;
                        row.add(subrow);
                        tmp = cellData;
                        subrow = new int[2];
                        subrow[0] = rowIndex;
                    }
                    cell.setCellValue(cellData);
                } else {
                    cell.setCellValue("");
                }

                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        subrow[1] = rowIndex-1;
        row.add(subrow);
        row.stream().forEach(it->System.out.println(it[0]+"---"+it[1]));
        // 合并
        for(int[] arr : row){
            for(int c : cols){

                // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
                // 行和列都是从0开始计数，且起始结束都会合并
                // 这里是合并excel中日期的两行为一行
                if(arr[1]-arr[0]>0){
                    CellRangeAddress region = new CellRangeAddress(arr[0], arr[1], c, c);
                    sheet.addMergedRegion(region);
                }
            }
        }








        return rowIndex;
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {

        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(BorderSide.TOP, color);
        style.setBorderColor(BorderSide.LEFT, color);
        style.setBorderColor(BorderSide.RIGHT, color);
        style.setBorderColor(BorderSide.BOTTOM, color);
    }

    public static <T>   List<List<String>> ObjtoList(List<T> list) {
        List<List<String>>  vs = new ArrayList<>();
        if(list!=null&&list.size()>0){
            Class c = list.get(0).getClass();
            Field[] fields = c.getDeclaredFields();
            List<String> fs = new ArrayList<>();
            Arrays.stream(fields).forEach(item->{fs.add(item.getName());
                item.setAccessible(true);
            });
            list.forEach(item->{
                List<String> fv = new ArrayList<>();
                Arrays.stream(fields).forEach(it->{
                    if(!it.getName().equals("isfinish")&&!it.getName().equals("fid")){
                        String v = "";
                        try {
                            v=it.get(item)==null?"":it.get(item).toString();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        fv.add(v);
                    }

                });
                vs.add(fv);
            });
        }
        return vs;
    }
}
