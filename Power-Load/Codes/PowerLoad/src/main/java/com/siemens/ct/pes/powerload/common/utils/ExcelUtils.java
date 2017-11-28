/**
 * 
 */
package com.siemens.ct.pes.powerload.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel utilities
 * 
 * @author Hao Liu
 *
 */
public class ExcelUtils {

    /**
     * Level 1 header
     */
    public static final String[] LV1HEADER = new String[] { "区域名称", "变压器名称", "变压器类型", "电压等级", "容量", "轻载时间", "重载时间",
            "过载时间", "最高负荷", "负载率曲线数据", };

    /**
     * Level 2 header
     */
    public static final String[] LV2HEADER = new String[] { "0:00", "0:15", "0:30", "0:45", "1:00", "1:15", "1:30",
            "1:45", "2:00", "2:15", "2:30", "2:45", "3:00", "3:15", "3:30", "3:45", "4:00", "4:15", "4:30", "4:45",
            "5:00", "5:15", "5:30", "5:45", "6:00", "6:15", "6:30", "6:45", "7:00", "7:15", "7:30", "7:45", "8:00",
            "8:15", "8:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00",
            "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00",
            "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00",
            "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45", "21:00",
            "21:15", "21:30", "21:45", "22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45" };

    /**
     * Get level 1 header positions for merge
     * 
     * <li>start_row, end_row, start_col, end_col(start with 0)
     * 
     * @return
     */
    public static String[] getLv1HeaderPosition() {
        return new String[] { "0,1,0,0", "0,1,1,1", "0,1,2,2", "0,1,3,3", "0,1,4,4", "0,1,5,5", "0,1,6,6", "0,1,7,7",
                "0,1,8,8", "0,0,9,107", };
    }

    /**
     * Get level2 header positions for merge
     * 
     * <li>start_row, end_row, start_col, end_col(start with 0)
     * 
     * @return
     */
    public static String[] getLv2HeaderPosition() {
        return new String[] { "1,1,12,12", "1,1,13,13", "1,1,14,14", "1,1,15,15", "1,1,16,16", "1,1,17,17", "1,1,18,18",
                "1,1,19,19", "1,1,20,20", "1,1,21,21", "1,1,22,22", "1,1,23,23", "1,1,24,24", "1,1,25,25", "1,1,26,26",
                "1,1,27,27", "1,1,28,28", "1,1,29,29", "1,1,30,30", "1,1,31,31", "1,1,32,32", "1,1,33,33", "1,1,34,34",
                "1,1,35,35", "1,1,36,36", "1,1,37,37", "1,1,38,38", "1,1,39,39", "1,1,40,40", "1,1,41,41", "1,1,42,42",
                "1,1,43,43", "1,1,44,44", "1,1,45,45", "1,1,46,46", "1,1,47,47", "1,1,48,48", "1,1,49,49", "1,1,50,50",
                "1,1,51,51", "1,1,52,52", "1,1,53,53", "1,1,54,54", "1,1,55,55", "1,1,56,56", "1,1,57,57", "1,1,58,58",
                "1,1,59,59", "1,1,60,60", "1,1,61,61", "1,1,62,62", "1,1,63,63", "1,1,64,64", "1,1,65,65", "1,1,66,66",
                "1,1,67,67", "1,1,68,68", "1,1,69,69", "1,1,70,70", "1,1,71,71", "1,1,72,72", "1,1,73,73", "1,1,74,74",
                "1,1,75,75", "1,1,76,76", "1,1,77,77", "1,1,78,78", "1,1,79,79", "1,1,80,80", "1,1,81,81", "1,1,82,82",
                "1,1,83,83", "1,1,84,84", "1,1,85,85", "1,1,86,86", "1,1,87,87", "1,1,88,88", "1,1,89,89", "1,1,90,90",
                "1,1,91,91", "1,1,92,92", "1,1,93,93", "1,1,94,94", "1,1,95,95", "1,1,96,96", "1,1,97,97", "1,1,98,98",
                "1,1,99,99", "1,1,100,100", "1,1,101,101", "1,1,102,102", "1,1,103,103", "1,1,104,104", "1,1,105,105",
                "1,1,106,106", "1,1,107,107" };
    }

    /**
     * Create excel work book by area power load data
     * 
     * @param areaNameMap
     * @param attrMap
     * @param tranPowerLoad
     * @param lv1Header
     * @param lv1HeaderPos
     * @param lv2Header
     * @param lv2HeaderPos
     * @throws Exception
     */
    public static HSSFWorkbook createExcelWorkBook(final Map<Integer, String> areaNameMap,
            final Map<Integer, Map<Integer, List<String>>> attrMap, final Map<Integer, List<String>> tranPowerLoad,
            final String[] lv1Header, final String[] lv1HeaderPos, final String[] lv2Header,
            final String[] lv2HeaderPos) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        // Column header style - font
        HSSFFont fontHeader = workbook.createFont();
        fontHeader.setFontName("宋体");
        fontHeader.setFontHeightInPoints((short) 12);
        fontHeader.setBold(true);

        // Column header style - cell
        HSSFCellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setBorderBottom(BorderStyle.THIN);
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);

        styleHeader.setFont(fontHeader);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setLocked(true);

        // Column content style - font
        HSSFFont fontContent = workbook.createFont();
        fontContent.setFontName("宋体");
        fontContent.setFontHeightInPoints((short) 12);
        HSSFCellStyle styleContent = workbook.createCellStyle();
        styleContent.setBorderBottom(BorderStyle.THIN);
        styleContent.setBorderLeft(BorderStyle.THIN);
        styleContent.setBorderTop(BorderStyle.THIN);
        styleContent.setBorderRight(BorderStyle.THIN);
        styleContent.setFont(fontContent);
        styleContent.setWrapText(true);
        styleContent.setVerticalAlignment(VerticalAlignment.CENTER);

        for (Entry<Integer, String> entry : areaNameMap.entrySet()) {
            HSSFSheet sheet = workbook.createSheet(entry.getValue());

            fillHSSSFSheetHeader(sheet, styleHeader, lv1Header, lv1HeaderPos, lv2Header);

            int rowIndex = 2;

            for (Entry<Integer, List<String>> attrEntry : attrMap.get(entry.getKey()).entrySet()) {
                addTranDataToSheet(sheet, styleContent, rowIndex, attrEntry.getValue(),
                        tranPowerLoad.get(attrEntry.getKey()));
                rowIndex++;
            }
        }

        return workbook;
    }

    /**
     * Fill sheet header
     * 
     * @param sheet
     * @param style
     * @param lv1Header
     * @param lv1HeaderPos
     * @param lv2Header
     */
    private static void fillHSSSFSheetHeader(final HSSFSheet sheet, final HSSFCellStyle style, final String[] lv1Header,
            final String[] lv1HeaderPos, final String[] lv2Header) {
        // Set column width
        sheet.setColumnWidth(0, 3600);
        sheet.setColumnWidth(1, 3600);
        sheet.setColumnWidth(2, 3600);
        sheet.setColumnWidth(3, 2800);
        sheet.setColumnWidth(4, 3600);
        sheet.setColumnWidth(5, 3600);
        sheet.setColumnWidth(6, 3600);
        sheet.setColumnWidth(7, 3600);
        sheet.setDefaultRowHeight((short) 360);

        // First row header
        HSSFRow row = sheet.createRow(0);

        for (int i = 0; i < 10; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(lv1Header[i]);
            cell.setCellStyle(style);
        }

        // Merge cell
        for (int i = 0; i < lv1HeaderPos.length; i++) {
            String[] temp = lv1HeaderPos[i].split(",");

            int startRow = Integer.parseInt(temp[0]);
            int endRow = Integer.parseInt(temp[1]);
            int startCol = Integer.parseInt(temp[2]);
            int endCol = Integer.parseInt(temp[3]);

            sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
        }

        // Second row header
        row = sheet.createRow(1);

        for (int i = 0; i < lv1Header.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);

            if (i > 8 && i < 104) {
                for (int j = 0; j < lv2Header.length; j++) {
                    cell = row.createCell(j + 9);
                    cell.setCellValue(lv2Header[j]);
                    cell.setCellStyle(style);
                }
            }
        }
    }

    /**
     * Add transformer's attributes and power load data to sheet
     * 
     * @param sheet
     * @param style
     * @param rowIndex
     * @param tranAttrs
     * @param powerLoads
     */
    private static void addTranDataToSheet(final HSSFSheet sheet, final HSSFCellStyle style, final int rowIndex,
            final List<String> tranAttrs, final List<String> powerLoads) {
        HSSFRow row = sheet.createRow(rowIndex);

        for (int i = 0; i < 105; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellType(CellType.STRING);

            String attr;

            if (i < 9) {
                attr = tranAttrs.get(i);
            } else {
                if (powerLoads == null) {
                    attr = CommonDefine.EMPTY;
                } else {
                    attr = powerLoads.get(i - 9);
                }
            }

            cell.setCellValue(attr);
        }
    }

    /**
     * Export work book to file
     * 
     * @param workbook
     */
    public static void exportToFile(final HSSFWorkbook workbook, final String filePath) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        File file = new File(filePath);
        file.createNewFile();

        OutputStream os = new FileOutputStream(file);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        byte[] b = new byte[1024];

        while ((bais.read(b)) > 0) {
            os.write(b);
        }

        bais.close();
        os.flush();
        os.close();
    }
}