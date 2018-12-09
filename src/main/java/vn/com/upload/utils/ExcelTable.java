package vn.com.upload.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author NhanVT3
 *
 */
public class ExcelTable extends DataTable {

   private static final Logger logger = LoggerFactory.getLogger(ExcelTable.class);

   private ExcelTable() {

   }

   public static DataTable load(byte[] bytes) {
      ExcelTable table = new ExcelTable();
      table.load(new ByteArrayInputStream(bytes));
      return table;
   }

   public static DataTable load(Supplier<InputStream> inputStreamReader) {
      ExcelTable table = new ExcelTable();
      return table.load(inputStreamReader.get());
   }

   private DataTable load(InputStream fis) {

      DataTable result = this;

      try {

         XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

         XSSFSheet mySheet = myWorkBook.getSheetAt(0);
         Iterator<Row> rowIterator = mySheet.iterator();

         Map<Integer, String> columns = new HashMap<>();

         int rowIndex = 0;

         while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            DataRow dataRow = result.newRow();

            if (rowIndex == 0) {
               Iterator<Cell> cellIterator = row.cellIterator();
               int columnIndex = 0;

               while (cellIterator.hasNext()) {
                  Cell cell = cellIterator.next();

                  // skip hidden headers
                  if (cell.getCellStyle().getHidden()) {
                     continue;
                  }

                  String value;
                  try {
                     value = cell.getStringCellValue();
                  }
                  catch (IllegalStateException ex) {
                     try {
                        value = "" + cell.getNumericCellValue();
                     }
                     catch (IllegalStateException ex2) {
                        value = "INVALID_HEADER";
                     }
                  }

                  if (value == null) {
                     columnIndex++;
                     continue;
                  }

                  value = value.trim();
                  if (!StringUtils.isEmpty(value)) {
                     logger.info("column: {}", value);
                     columns.put(columnIndex, value);
                  }

                  columnIndex++;
               }

               for(String column : columns.values()) {
                  result.columns().add(column);
               }

            }

            else {
               ArrayList<Integer> columnKeys = new ArrayList<Integer>(columns.keySet());
               int numColumns = columnKeys.size();

               for (int i=0; i<numColumns; i++) {
                  int columnIndex = columnKeys.get(i);
                  Cell cell = row.getCell(columnIndex);
                  String value = "";

                  if (cell == null) {
                     value = "";
                  }
                  else {

                     try {
                        value = cell.getStringCellValue();
                        logger.info("#" + (i + 1) + ": cell value (string): {}", value);

                     } catch (IllegalStateException ex) {
                        try {
                           value = "" + (int) cell.getNumericCellValue();

                        } catch (IllegalStateException ex2) {
                           value = "";
                        }
                     }
                  }

                  // final check for null values
                  if (value == null) {
                     value = "";
                  }

                  dataRow.cell(columns.get(columnIndex), value);
               }

               result.rows().add(dataRow);
            }

            rowIndex++;
         }

      }
      catch (IOException e) {
         logger.error("Failed to read the excel file", e);
      }

      return result;

   }
}
