package com.xiaoshu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaoshu.entity.MeetingSign;
import com.xiaoshu.po.DtoMeetingSign;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel 解析工具
 *
 * @author XiaoW 2018-5-10
 * pom.xml 依赖
 *
 * 	<dependency>
 *	    <groupId>org.apache.poi</groupId>
 *	    <artifactId>poi</artifactId>
 *	    <version>3.16</version>
 *	</dependency>
 *
 * 	<!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
 *	<dependency>
 *	    <groupId>org.apache.poi</groupId>
 *	    <artifactId>poi-ooxml</artifactId>
 *	    <version>3.16</version>
 *	</dependency>
 *
 *	<!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml-schemas -->
 *	<dependency>
 *	    <groupId>org.apache.poi</groupId>
 *	    <artifactId>poi-ooxml-schemas</artifactId>
 *	    <version>3.16</version>
 *	</dependency>
 *
 *	<!-- https://mvnrepository.com/artifact/org.apache.xmlbeans/xmlbeans -->
 *	<dependency>
 *	    <groupId>org.apache.xmlbeans</groupId>
 *	    <artifactId>xmlbeans</artifactId>
 *	    <version>2.6.0</version>
 *	</dependency>
 *
 */
public class ExcelReader {


    private String filePath;
    private String sheetName;
    private Workbook workBook;
    private Sheet sheet;
    private List<String> columnHeaderList;
    private List<List<String>> listData;
    private List<Map<String, String>> mapData;
    private boolean flag;


    public ExcelReader(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        this.flag = false;
        this.load();
    }


    private void load() {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            workBook = WorkbookFactory.create(inStream);
            sheet = workBook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }


    private void getSheetData() {
        listData = new ArrayList<List<String>>();
        mapData = new ArrayList<Map<String, String>>();
        columnHeaderList = new ArrayList<String>();
        int numOfRows = sheet.getLastRowNum() + 1;
        for (int i = 0; i < numOfRows; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> map = new HashMap<String, String>();
            List<String> list = new ArrayList<String>();
            if (row != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (i == 0) {
                        columnHeaderList.add(getCellValue(cell));
                    } else {
                        map.put(columnHeaderList.get(j), this.getCellValue(cell));
                    }
                    list.add(this.getCellValue(cell));
                }
            }
            if (i > 0) {
                mapData.add(map);
            }
            listData.add(list);
        }
        flag = true;
    }


    public String getCellData(int row, int col) {
        if (row <= 0 || col <= 0) {
            return null;
        }
        if (!flag) {
            this.getSheetData();
        }
        if (listData.size() >= row && listData.get(row - 1).size() >= col) {
            return listData.get(row - 1).get(col - 1);
        } else {
            return null;
        }
    }


    public String getCellData(int row, String headerName) {
        if (row <= 0) {
            return null;
        }
        if (!flag) {
            this.getSheetData();
        }
        if (mapData.size() >= row && mapData.get(row - 1).containsKey(headerName)) {
            return mapData.get(row - 1).get(headerName);
        } else {
            return null;
        }
    }


    /**
     * 获取标题
     * @param eh
     * @param maxX
     * @return
     */
    public List<String> getTitleList(ExcelReader eh, int maxX) {
        List<String> result = new ArrayList<String>();
        for (int i = 1; i <= maxX; i++) {
            result.add(eh.getCellData(1, i));
        }
        return result;
    }


    /**
     * 获取单行对象
     * @param className
     * @param eh
     * @param y
     * @param titles
     * @return
     */
    public Object getObject(String className, ExcelReader eh, int y, List<String> titles) throws Exception {
        Object bean = Class.forName(className).newInstance();
        int length = titles.size();
        for (int x = 0; x < length; x++) {
            try {
                Field field = bean.getClass().getDeclaredField(titles.get(x));
                field.setAccessible(true);
                field.set(bean, eh.getCellData(y, x+1));
            } catch (Exception e) {
                //System.out.println("没有对应的方法：" + e);
            }
        }
        return bean;
    }


    /**
     * 获取Excel数据列表
     * @param clazz
     * @param eh
     * @param x  每行有多少列数据
     * @param y  整个sheet有多少行数据
     * @param titles
     * @return
     */
    public List<Object> getDataList(Class<?> clazz, ExcelReader eh, int x, int y, List<String> titles) {
        List<Object> result = new ArrayList<Object>();
        String className = clazz.getName();
        try {
            for (int i = 2; i <=y; i++) {
                Object object = eh.getObject(className, eh, i, titles);
                result.add(object);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }


    /**
     * 读取Excel数据并转化为传入的类型对象集合
     * @param clazz 类型
     * @param filePath 文件路径，绝对 "G:\\3715b6fcfbb6418fb4ed2269be48b48b.xlsx"
     * @param tableName 表1:"Sheet1" , 表2:"Sheet2"
     * @param x 表的列数
     * @param y 表的行数
     * @param maxX 表的最多标题
     */
    public static List<Object> getListExcelObject(Class<?> clazz,String filePath,String tableName,int x,int y,int maxX){
        ExcelReader eh = new ExcelReader(filePath, tableName);
        List<String> titles = eh.getTitleList(eh, maxX);
        List<Object> list = eh.getDataList(clazz, eh, x, y, titles);
        return list;
    }

    // 测试
    public static void main(String[] args) {
        try {
            List<Object> userList = ExcelReader.getListExcelObject(DtoMeetingSign.class, "G:\\test1.xlsx", "Sheet1", 7, 4, 7);
            for (Object object : userList) {
                System.out.println(object);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}  