package com.zx.base.common;

import com.zx.lib.utils.DateUtil;
import com.zx.lib.utils.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wanght
 * @version 2017/10/20
 */
public class ExcelUtil {
    /**
     * 2003- 版本的excel
     */
    private final static String EXCEL_2003L = ".xls";
    /**
     * 2007+ 版本的excel
     */
    private final static String EXCEL_2007U = ".xlsx";

    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     *
     * @param in,fileName
     * @return
     */
    public List<List<Object>> getBankListByExcel(InputStream in, String fileName, Integer sheetNum) throws Exception {
        List<List<Object>> list = null;

        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        if (null == sheetNum) {
            //遍历Excel中所有的sheet
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }

                //遍历当前sheet中的所有行
                for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if (row == null || row.getFirstCellNum() == j) {
                        continue;
                    }

                    //遍历所有的列
                    List<Object> li = new ArrayList<Object>();
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        li.add(this.getCellValue(cell));
                    }
                    list.add(li);
                }
            }
        } else {
            sheet = work.getSheetAt(sheetNum);
            if (sheet == null) {
                return null;
            }

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }

                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    if (null != cell) {
                        li.add(this.getCellValue(cell));
                    }
                }
                list.add(li);
            }
        }
        work.close();
        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_2003L.equals(fileType)) {
            //2003-
            wb = new HSSFWorkbook(inStr);
        } else if (EXCEL_2007U.equals(fileType)) {
            //2007+
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public Object getCellValue(Cell cell) {
        Object value = null;
        //格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        //格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String[] columnNames) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        //f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
       /* cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);*/

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        /*cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);*/
        //设置列名
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    /**
     * 单元格样式池：1标题 2列名 3文本内容 4数值内容
     *
     * @param workbook
     * @param cellType
     * @return
     */
    private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, int cellType) {
        //全局样式
        HSSFFont font = workbook.createFont();//字体
        font.setFontName("Microsoft YaHei");
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle style = workbook.createCellStyle();//样式
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setWrapText(true);

        //1.标题样式
        if (cellType == 1) {
            font.setFontHeightInPoints((short) 16);//增大字体
//            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
        }
        //2.列名样式
        else if (cellType == 2) {
            font.setBold(true);
            style.setFont(font);
            style.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);//参考：https://blog.csdn.net/qq_38025219/article/details/82760471
        }
        //3.内容样式：文本
        else if (cellType == 3) {
            //...
        }
        //4.内容样式：数值
        else if (cellType == 3) {
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        }
        return style;
    }

    /**
     * 数据库结果集导出为Excel对象
     *
     * @param sheetName
     * @param list
     * @return
     */
    public static HSSFWorkbook createWorkBook(String sheetName, List<LinkedHashMap<String, Object>> list, Boolean autoSize) throws Exception {
        //1.创建excel工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        //2.创建sheet页并命名
        Sheet sheet = wb.createSheet(sheetName);
        //3.遍历数据汇总列名（部分属性为NULL时不会在map中，所以要汇总）
        List<String> columns = new ArrayList<>();
        for (LinkedHashMap<String, Object> item : list) {
            for (String key : item.keySet()) {
                if (!columns.contains(key))
                    columns.add(key);
            }
        }
        //4.设置列宽
        // for (int i = 0; i < columns.size(); i++) {
        //     sheet.setColumnWidth((short) i, (short) 4000);
        // }
        //5.创建第一行：标题行
        Row rowTitle = sheet.createRow((short) 0);
        rowTitle.setHeightInPoints(20);
        rowTitle.setHeight((short) 80);
        //标题样式
        CellStyle styleTitle = getCellStyle(wb, 1);
        //创建单元格（第一个含标题）
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellStyle(styleTitle);
            if (i == 0) {
                cell.setCellValue(sheetName);
            }
        }
        if (columns.size() == 0) {
            return wb;
        }
        //合并
        CellRangeAddress regionTitle = new CellRangeAddress(0, 0, (short) 0, (short) (columns.size() - 1));
        sheet.addMergedRegion(regionTitle);
        //6.创建列名行
        Row rowHead = sheet.createRow((short) 1);
        CellStyle styleHead = getCellStyle(wb, 2);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = rowHead.createCell(i);
            cell.setCellStyle(styleHead);
            cell.setCellValue(columns.get(i));
        }
        //7.数据行
        CellStyle styleText = getCellStyle(wb, 3);
        CellStyle styleNumber = getCellStyle(wb, 4);
        for (short i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i + 2);//标题与列名占2行
            for (short j = 0; j < columns.size(); j++) {
                Cell cell = row.createCell(j);
                String data = list.get(i).get(columns.get(j)) + "";
                // if ("序号".equals(columns.get(j))) {
                //     data = i + 1 + "";
                // } else {
                //     //根据单元格的值设置单元格不同样式
                //     data = list.get(i).get(columns.get(j)) + "";
                // }
                data = data.trim();
                try {
                    Double.parseDouble(data);
                    //避免Excel中数值型数据达到12位时，将以科学计数法的方式显示
                    if (data.length() <= 11) {
                        cell.setCellStyle(styleNumber);
                        cell.setCellValue(Double.parseDouble(data));
                    } else {
                        cell.setCellStyle(styleText);
                        cell.setCellValue(data);
                    }
                } catch (Exception e) {
                    cell.setCellStyle(styleText);
                    cell.setCellValue(data);
                }
            }
        }
        //8.设置列宽
        for (int i = 0; i < columns.size(); i++) {
            if (autoSize) {
                sheet.autoSizeColumn((short) i);
                if (sheet.getColumnWidth(i) > 15000) {
                    sheet.setColumnWidth((short) i, (short) 15000);
                }
            } else {
                sheet.setColumnWidth((short) i, (short) 4000);
            }
        }

        return wb;
    }


    /**
     * Bean集合导出Excel对象
     *
     * @param sheetName 表名
     * @param list      数据集合
     * @param fileds    字段过滤字典，不指定默认按字段名填充
     * @return
     */
    public static <T> HSSFWorkbook createWorkBook(String sheetName, List<T> list, LinkedHashMap<String, String> fileds) throws Exception {
        return createWorkBook(sheetName, getLinkedHashMapMapList(list, fileds), false);
    }

    /**
     * Bean集合导出Excel对象
     *
     * @param sheetName 表名
     * @param list      数据集合
     * @param fileds    字段过滤字典，不指定默认按字段名填充
     * @param autoSize  列宽自适应
     * @return
     */
    public static <T> HSSFWorkbook createWorkBook(String sheetName, List<T> list, LinkedHashMap<String, String> fileds, Boolean autoSize) throws Exception {
        return createWorkBook(sheetName, getLinkedHashMapMapList(list, fileds), autoSize);
    }

    /**
     * Bean集合转化为数据库结果集
     *
     * @param list   数据列表
     * @param fileds 导出字典：key是属性名称 value是属性导出名称
     * @param <T>    数据类型
     * @return
     * @throws Exception
     */
    public static <T> List<LinkedHashMap<String, Object>> getLinkedHashMapMapList(List<T> list, LinkedHashMap<String, String> fileds) throws Exception {
        //0.最终返回的数据库结果集
        List<LinkedHashMap<String, Object>> resList = new ArrayList<>();
        //1.遍历数据列表，提取数据元素的导出值
        int index = 0;
        for (T item : list) {
            index++;
            //1.1 单个数据项的结果集
            LinkedHashMap<String, Object> resItem = new LinkedHashMap<>();
            //1.2 单个数据项的属性及函数
            Method[] itemMethods = item.getClass().getDeclaredMethods();
            Field[] itemFileds = item.getClass().getDeclaredFields();
            //1.3 如果未指定导出字典，默认导出该数据项的全部属性
            boolean isEmpty = (fileds == null || fileds.size() <= 0);
            if (fileds == null || fileds.size() <= 0) {
                fileds = new LinkedHashMap<>();
                for (Field f : itemFileds) {
                    fileds.put(f.getName(), f.getName());
                }
            }
            //1.4 开始按照导出字典导出
            Iterator iter = fileds.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                //要导出的属性名
                String filedName = entry.getKey() + "";
                //要导出的属性显示名
                String filedOutName = entry.getValue() + "";
                //找到属性值
                Object filedValue;
                if ("_index".equals(filedName)) {
                    filedValue = index + "";
                } else if ("_team_adviser".equals(filedName)) {
                    Object team = getFieldValue(item, "team");
                    Object adviser = getFieldValue(item, "adviser");
                    filedValue = (team != null && StringUtil.isNotEmpty(team.toString())) ? team + " " + adviser : adviser;
                } else {
                    filedValue = getFieldValue(item, filedName);
                    if (filedValue == null) {
                        filedValue = "";
                    }
                }
                //保存
                resItem.put(filedOutName, filedValue);
            }
            resList.add(resItem);
        }
        return resList;
    }

    /**
     * 根据表达式获取对象值
     *
     * @param obj      对象
     * @param fieldStr 取值表达式，支持多级子对象提取,如 order.logs.title
     * @return
     * @throws Exception
     */
    private static Object getFieldValue(Object obj, String fieldStr) throws Exception {
        if (obj == null)
            return null;
        Method[] itemMethods = obj.getClass().getDeclaredMethods();
        Field[] itemFileds = obj.getClass().getDeclaredFields();
        Object result = null;
        //组合字段AAA.BB.CC，递归获取属性值
        if (fieldStr.contains(".")) {
            String[] ary = fieldStr.split("\\.");
            String subfiled = ary[0];//AAA
            String subOther = fieldStr.substring(fieldStr.indexOf('.') + 1);//BBB.CC
            for (Method method : itemMethods) {
                if (method.getName().equalsIgnoreCase("get" + subfiled)) {
                    Object subObj = method.invoke(obj);
                    return getFieldValue(subObj, subOther);
                }
            }
        }
        //基本字段，直接反射获取属性值
        else {
            for (Field filed : itemFileds) {
                String fname = filed.getName();
                if (fname.equalsIgnoreCase(fieldStr)) {
                    //获取字段值：private无法获取，转而调用POJO函数
                    for (Method method : itemMethods) {
                        if (method.getName().equalsIgnoreCase("get" + fname)) {
                            result = method.invoke(obj);
                            //部分数据类型转换为字符串后无法格式化，如日期，此处进行转换
                            if (filed.getGenericType().toString().contains(".Date") && result != null) {
                                try {
                                    result = DateUtil.toDateString((Date) result, "yyyy-MM-dd HH:mm:ss");
                                } catch (Exception ex) {
                                    result = null;
                                }
                            }
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }
}
