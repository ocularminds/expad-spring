
package com.ocularminds.expad.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExcelFile extends ArrayList {
    
    static final Logger LOG = LoggerFactory.getLogger(ExcelFile.class);
    
    public ExcelFile(String fileName) throws IOException, Exception {
        super(Arrays.asList(ExcelFile.read(fileName).split("\n")));
    }

    public static String read(String f) throws IOException, Exception {
        LOG.info(" Accepting upload file name " + f);
        Workbook workbook = Workbook.getWorkbook((File)new File(f));
        Sheet[] sheets = workbook.getSheets();
        LOG.info("total sheets - " + sheets.length);
        return ExcelFile.performReading(sheets);
    }

    private static String performReading(Sheet[] sheets) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (sheets.length > 0) {
            LOG.info("[xlUtil]: TOTAL RECORDS FOUND IS:" + sheets[0].getRows() + " rows");
            LOG.info("[xlUtil]: TOTAL COLUMNS FOUND IS:" + sheets[0].getColumns() + " cells");
            for (int index = 0; index < sheets.length; ++index) {
                int rows = sheets[index].getRows();
                LOG.info("[xlUtil]: Sheet:" + index + " with " + rows + " rows.");
                for (int x = 0; x < rows; ++x) {
                    String cellTest;
                    Cell[] cell = sheets[index].getRow(x);
                    String string = cellTest = cell.length > 0 ? cell[0].getContents() : null;
                    if (cellTest == null || cellTest.equalsIgnoreCase("") && cellTest.equalsIgnoreCase("0")) continue;
                    sb.append(ExcelFile.readCellContents(cell));
                    if (x >= rows - 1) continue;
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private static String readCellContents(Cell[] cell) throws Exception {
        StringBuilder sb = new StringBuilder();
        String column0 = "";
        int size = cell.length;
        boolean isCorrect = true;
        for (int x = 0; x < cell.length; ++x) {
            try {
                column0 = cell[0].getContents();
                column0 = size < 2 ? "" : cell[1].getContents();
            }
            catch (Exception ex) {
                isCorrect = false;
            }
            if (!isCorrect || column0.equals("")) continue;
            sb.append(cell[x].getContents().replaceAll(",", "-"));
            if (x >= cell.length - 1) continue;
            sb.append(Constants.COMMA);
        }
        return sb.toString();
    }

    public static void write(String fileName, String text) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            out.print(text);
        }
    }

    public static List readContentToArrayList(String fileName) throws IOException, Exception {
        return Arrays.asList(ExcelFile.read(fileName).split("\n"));
    }

    public void write(String fileName) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (int i = 0; i < this.size(); ++i) {
                out.println(this.get(i));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String file = ExcelFile.read("FBN_Micro-finance2.xls");
        ExcelFile.write("exceltest.txt", file);
        ExcelFile contents = new ExcelFile("FBN_Micro-finance2.xls");
        contents.write("exceltest2.txt");
        for (int x = 0; x < contents.size(); ++x) {
            LOG.info(contents.get(x).toString());
        }
    }
}

