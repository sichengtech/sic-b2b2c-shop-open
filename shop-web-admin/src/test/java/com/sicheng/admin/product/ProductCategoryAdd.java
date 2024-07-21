/**
 * 本作品使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权。
 * Copyright (c) 2016 SiCheng.Net
 * This software is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.admin.product;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: 读取Excel，把产品分类数据导入到产品分类表中</p>
 * <p>描述: </p>
 * <p>公司: 思程科技 www.sicheng.net</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath*:spring-context*.xml"})
public class ProductCategoryAdd {
    //	@Autowired
//	private ProductCategoryService productCategoryService;
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private Sheet sheet;
    private Row row;
    public Workbook book = null;

    /**
     * 读取Excel表格表头的内容
     *
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle() {
//		try {
//			fs = new POIFSFileSystem(is);
//			wb = new HSSFWorkbook(fs);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        sheet = book.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            // title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     *
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent() {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
//		try {
//			fs = new POIFSFileSystem(is);
//			wb = new HSSFWorkbook(fs);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        sheet = book.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
//		// 正文内容应该从第二行开始,第一行为表头的标题
//		List<ProductCategory> list=new ArrayList<ProductCategory>();
//		list=productCategoryService.findList(new ProductCategory());
//		boolean flag=true;//重复插入控制
//		for (int i = 1; i <= rowNum; i++) {
//			row = sheet.getRow(i);
//			int j = 0;
//			while (j < colNum) {
//				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
//				// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
//				// str += getStringCellValue(row.getCell((short) j)).trim() +
//				// "-";
//				if(j>=1&&j<=3){
//					ProductCategory cate=new ProductCategory();
//					ProductCategory pcate=new ProductCategory();
//					if(j==1){
//						pcate.setId("0");
//					}else if(j==2){
//						pcate.setName(getCellFormatValue(row.getCell((short) 1)).trim());
//						pcate.setCateLevel("1");
//						pcate=productCategoryService.getByNameAndLevel(pcate);
//						cate.setParent(pcate);
//					}else if(j==3){
//						pcate.setName(getCellFormatValue(row.getCell((short) 2)).trim());
//						pcate.setCateLevel("2");
//						pcate=productCategoryService.getByNameAndLevel(pcate);
//						cate.setParent(pcate);
//					}
//					cate.setName(getCellFormatValue(row.getCell((short) j)).trim());
//					System.out.print(cate.getName());
//					cate.setCateLevel(j+"");
//					cate.setRemarks(getCellFormatValue(row.getCell((short) 0)).trim());
//					for(ProductCategory c:list){
//						if(cate.getName().equals(c.getName())&&cate.getCateLevel().equals(c.getCateLevel())){
//							flag=false;
//						}
//					}
//					if(flag&&cate.getName()!=null&&!"".equals(cate.getName())){
//						productCategoryService.save(cate);
//						list.add(cate);
//					}else{
//						flag=true;
//					}
//				}
//				
//				str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
//				j++;
//			}
//			content.put(i, str);
//			str = "";
//		}
        return content;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();

                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    //主运行方法
    //@Test
    public void main() {
        // 对读取Excel表格标题测试
//			InputStream is = new FileInputStream("C:/Users/73281/Desktop/HCXC/原材料/原材料/分类表电子整理2014-07-20.xlsx");
        String filePath = "C:/Users/73281/Desktop/HCXC/原材料/原材料/最终产品分类7-23.xls";
        try {
            book = new XSSFWorkbook(filePath);
        } catch (Exception ex) {
            try {
                book = new HSSFWorkbook(new FileInputStream(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//		ProductCategoryController excelReader = new ProductCategoryController("C:/Users/73281/Desktop/HCXC/原材料/原材料/分类表电子整理2014-07-20.xlsx");
        String[] title = readExcelTitle();
        System.out.println("获得Excel表格的标题:");
        for (String s : title) {
            System.out.println(s + " ");
        }

        // 对读取Excel表格内容测试
//			InputStream is2 = new FileInputStream("C:/Users/73281/Desktop/HCXC/原材料/原材料/分类表电子整理2014-07-20.xlsx");
        Map<Integer, String> map = readExcelContent();
        System.out.println("获得Excel表格的内容:");
        for (int i = 1; i <= map.size(); i++) {
            System.out.println(map.get(i));
        }
    }

    /**
     * TODO(这里用一句话描述这个方法的作用)
     */
    @Test
    public void test_01() {

    }
}
