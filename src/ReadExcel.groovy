import java.text.SimpleDateFormat

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class ReadExcel {

    private static String getCellVal(XSSFCell cell) {
        if (cell == null){
            return 'null'
        }else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
            return ""
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_ERROR) {
            return cell.getErrorCellValue();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else {
            return cell.getStringCellValue();
        }
    }

    void readExcel(String filename) {
        if (filename==null || filename=='') return;
        def outfile = new File(filename.replace('.xlsx','.txt'));
        def fp = outfile.newPrintWriter();
        println filename
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filename));

        (0..<wb.numberOfSheets).each{
            XSSFSheet sheet = wb.getSheetAt(it)
            int rows = sheet.physicalNumberOfRows
            println " sheet:\"" + wb.getSheetName(it) + "\" has " + rows + " row(s)."
            (0..<rows).each{ r ->
                XSSFRow row = sheet.getRow(r)
                if (row != null) {
                    int cells = row.physicalNumberOfCells
//                    println "\nROW " + row.rowNum + " has " + cells + " cell(s)."
                    println String.format("insert into alipay_logistics_mapping(logistics_company,alipay_logistics_company,alipay_logistics_code) values('%s','%s','%s');",getCellVal(row.getCell(4)),getCellVal(row.getCell(6)),getCellVal(row.getCell(7)));
//                    (0..<cells).each{ c ->
//                        XSSFCell cell = row.getCell(c)
//                    }
                }
            }
        }
        fp.flush();
        fp.close();
    }

    static void main(args) {
        def filename = "C:\\Users\\user\\Desktop\\物流.xlsx";
        def app = new ReadExcel();
        app.readExcel(filename);
    }
}
