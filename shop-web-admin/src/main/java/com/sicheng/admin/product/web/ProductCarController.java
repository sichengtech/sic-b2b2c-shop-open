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
package com.sicheng.admin.product.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sicheng.admin.product.entity.ProductCar;
import com.sicheng.admin.product.service.ProductCarService;

import com.sicheng.common.config.Global;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.utils.FileUtils;
import com.sicheng.common.utils.Pinyin4j;
import com.sicheng.common.utils.StringUtils;
import com.sicheng.common.web.BaseController;
import com.sicheng.common.web.R;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车系车型Controller
 *
 * @author cl
 * @version 2017-06-12
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productCar")
public class ProductCarController extends BaseController {

    @Autowired
    private ProductCarService productCarService;



    /**
     * 菜单高亮
     *
     * @param model
     */
    @ModelAttribute
    public void get(Model model) {
        String menu3 = "020140";//请修改为正确的三级菜单编号
        //手动控制菜单高亮,90%的情况无须手动控制。注意：本方法已淘汰，请使用 menuHighLighting(Long menuId) 方法来替代。
        super.menuHighLighting(menu3);
    }

    /**
     * 列表页面
     *
     * @param productCar
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCar:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductCar productCar, HttpServletRequest request, HttpServletResponse response, Model model) {
        String name = R.get("name");
        Wrapper wrapper = new Wrapper();
        if (StringUtils.isNotBlank(name)) {
            wrapper.and("a.name like", "%" + name + "%");
        } else {
            if (productCar.getParent() == null || productCar.getParent().getCarId() == null) {
                ProductCar car = new ProductCar();
                car.setCarId(0L);
                productCar.setParent(car);
            }
            wrapper.orderBy("a.sort asc");
            wrapper.setEntity(productCar);
        }
        wrapper.orderBy("a.first_letter asc");
        List<ProductCar> list = productCarService.selectByWhere(wrapper);
        model.addAttribute("name", name);
        model.addAttribute("list", list);
        return "admin/product/productCarList";
    }

    /**
     * 进入编辑,新增页面
     *
     * @param productCar
     * @param model
     * @return
     */
    @RequiresPermissions("product:productCar:edit")
    @RequestMapping(value = "form")
    public String form(ProductCar productCar, Model model) {
        ProductCar entity = null;
        if (productCar.getCarId() != null) {
            entity = productCarService.selectById(productCar.getCarId());
        }
        if (entity == null) {
            entity = productCar;
        }
        if (entity.getParent() != null && entity.getParent().getCarId() != null) {
            entity.setParent(productCarService.selectById(entity.getParent().getCarId()));
            // 获取排序号，最末节点排序号+30
            if (entity.getParent() != null && entity.getParent().getCarId() != null) {
                if (entity.getCarId() == null) {
                    ProductCar productCarChild = new ProductCar();
                    productCarChild.setParent(new ProductCar(entity.getParent().getCarId()));
                }
            }
        }
        model.addAttribute("productCar", entity);
        return "admin/product/productCarForm";
    }

    /**
     * 保存
     *
     * @param productCar
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCar:edit")
    @RequestMapping(value = "save")
    public String save(ProductCar productCar, Model model, RedirectAttributes redirectAttributes) {
        //表单验证
        List<String> errorList = validate(productCar, model);
        if (errorList.size() > 0) {
            errorList.add(0, "数据验证失败：");
            addMessage(model, errorList.toArray(new String[]{}));
            return form(productCar, model);//回显错误提示
        }
        Pinyin4j pinyin4j = new Pinyin4j();
        try {
            productCar.setFirstLetter(pinyin4j.toPinYinUppercaseInitials(productCar.getName()));
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("转换车系车型拼音首字母出错", e);
        }
        productCarService.save(productCar);
        addMessage(redirectAttributes, "保存成功");
        return "redirect:" + adminPath + "/product/productCar/list.do?repage";
    }

    /**
     * 删除
     *
     * @param productCar
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("product:productCar:drop")
    @RequestMapping(value = "delete")
    public String delete(ProductCar productCar, RedirectAttributes redirectAttributes) {
        productCarService.delete(productCar.getCarId());
        addMessage(redirectAttributes, "删除车系车型成功");
        return "redirect:" + adminPath + "/product/productCar/list.do?repage";
    }

    /**
     * 加载选择上级树的数据
     * 为页面上的“选择树”组件提供json数据，都是“树”结构的数据
     *
     * @param extId 排除的ID
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<ProductCar> list = productCarService.selectByWhere(new Wrapper(new ProductCar()));
        for (int i = 0; i < list.size(); i++) {
            ProductCar e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 进入导入车系车型页
     */
    @RequiresPermissions("product:productCar:import")
    @RequestMapping(value = "importExcel")
    public String importExcel(Model model) {
        String fail = R.get("fail");//导入失败条数
        String success = R.get("success");//导入成功条数
        model.addAttribute("fail", fail);
        model.addAttribute("success", success);
        return "admin/product/productCarImport";
    }

    /**
     * 执行车系车型保存
     *
     * @param excel              上传车系车型excel文件
     * @param ststus             1.初始导入，2.清空车系车型,再导入
     * @param model
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @RequiresPermissions("product:productCar:import")
    @RequestMapping(value = "saveExcel")
    public String saveExcel(@RequestParam("excel") MultipartFile excel, String status,
                            Model model, RedirectAttributes redirectAttributes) {
        if ("2".equals(status)) {//清空车系车型,再导入
            Wrapper wrapper = new Wrapper();
            wrapper.and("1=", 1);
            productCarService.deleteByWhere(wrapper);
        }
        if (excel == null) {
            model.addAttribute("message", "请选择文件");
            return "admin/product/productCarImport";
        }
        if (excel.getSize() > 5242880) {
            model.addAttribute("message", "上传文件超过了5M");
            return "admin/product/productCarImport";
        }
        if (!FileUtils.isExcel(excel.getOriginalFilename())) {
            model.addAttribute("message", "文件格式不正确");
            return "admin/product/productCarImport";
        }

        // 判断是不是03版本的excel
        boolean is03Excel = excel.getOriginalFilename().matches("^.+\\.(?i)(xls)$");
        // 读取工作薄
        Workbook workbook = null;
        try {
            InputStream inputStream = excel.getInputStream();
            workbook = is03Excel ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("e1：", e);
        } catch (IOException e) {
            logger.error("e1：", e);
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet.getPhysicalNumberOfRows() <= 1) {
            model.addAttribute("message", "Excel格式与模板格式不一致！");
            return "admin/product/productCarImport";
        }
        //将Excel的数据导入数据库中
        Pinyin4j pinyin4j = new Pinyin4j();
        int fail = 0;//导入失败行数
        int success = 0;//导入成功行数
        Map<String, ProductCar> map1 = new HashMap<String, ProductCar>();//1级分类
        Map<String, Map<String, ProductCar>> map2 = new HashMap<String, Map<String, ProductCar>>();//2级分类(键为上级名字)
        Map<String, Map<String, ProductCar>> map3 = new HashMap<String, Map<String, ProductCar>>();//3级分类(键为上级名字)
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // 读取单元格
            Row row = sheet.getRow(i);
            int colNum = row.getPhysicalNumberOfCells();
            for (int j = 0; j < colNum; j++) {
                if (j >= 0 && j <= 3) {
                    ProductCar productCarParent = new ProductCar();
                    ProductCar productCar = new ProductCar();
                    String name = getCellFormatValue(row.getCell((short) j));
                    if (StringUtils.isNotBlank(name)) {
                        productCar.setName(name);
                        try {
                            productCar.setFirstLetter(pinyin4j.toPinYinUppercaseInitials(productCar.getName()));
                        } catch (BadHanyuPinyinOutputFormatCombination e) {
                            logger.error("转换车系车型拼音首字母出错", e);
                        }
                        if (j == 0) {
                            //1级
                            //获取当前name在是否存在
                            ProductCar car = map1.get(name);
                            if (car == null) {
                                //不存在
                                productCarParent.setCarId(0L);
                                productCar.setParent(productCarParent);
                                productCar.setParentIds("0,");
                                productCarService.insertSelective(productCar);
                                map1.put(name, productCar);
                                success++;
                            }
                        }
                        if (j == 1) {
                            //2级
                            //获取当前name在1级是否存在
                            String nameParent = getCellFormatValue(row.getCell((short) j - 1));
                            if (StringUtils.isNotBlank(nameParent)) {
                                ProductCar carParent = map1.get(nameParent);
                                if (carParent != null) {
                                    Map<String, ProductCar> carMapParent = map2.get(carParent.getName());
                                    if (carMapParent == null || (carMapParent != null && carMapParent.get(name) == null)) {
                                        if (carMapParent == null) {
                                            carMapParent = new HashMap<String, ProductCar>();
                                        }
                                        productCar.setParent(carParent);
                                        productCar.setParentIds(carParent.getParentIds() + carParent.getCarId() + ",");
                                        productCarService.insertSelective(productCar);
                                        carMapParent.put(name, productCar);
                                        map2.put(nameParent, carMapParent);
                                        success++;
                                    }
                                }
                            }
                        }
                        if (j == 2) {
                            //3级
                            //获取当前name在2级是否存在
                            String nameParentParent = getCellFormatValue(row.getCell((short) j - 2));
                            String nameParent = getCellFormatValue(row.getCell((short) j - 1));
                            if (StringUtils.isNotBlank(nameParentParent) && StringUtils.isNotBlank(nameParent)) {
                                Map<String, ProductCar> carMapParent = map2.get(nameParentParent);
                                if (carMapParent != null && carMapParent.get(name) == null) {
                                    ProductCar carParent = carMapParent.get(nameParent);
                                    productCar.setParent(carParent);
                                    productCar.setParentIds(carParent.getParentIds() + carParent.getCarId() + ",");
                                    productCarService.insertSelective(productCar);
                                    carMapParent.put(name, productCar);
                                    map3.put(nameParent, carMapParent);
                                    success++;
                                }
                            }
                        }
                        if (j == 3) {
                            //4级
                            //获取当前name在3级是否存在
                            String nameParentParent = getCellFormatValue(row.getCell((short) j - 2));
                            String nameParent = getCellFormatValue(row.getCell((short) j - 1));
                            if (StringUtils.isNotBlank(nameParentParent) && StringUtils.isNotBlank(nameParent)) {
                                Map<String, ProductCar> carMapParent = map3.get(nameParentParent);
                                if (carMapParent != null && carMapParent.get(name) == null) {
                                    ProductCar carParent = carMapParent.get(nameParent);
                                    productCar.setParent(carParent);
                                    productCar.setParentIds(carParent.getParentIds() + carParent.getCarId() + ",");
                                    productCarService.insertSelective(productCar);
                                    carMapParent.put(name, productCar);
                                    map3.put(nameParent, carMapParent);
                                    success++;
                                }
                            }
                        }
                    }
                }
            }
        }
        addMessage(redirectAttributes, "成功导入车系车型");
        return "redirect:" + Global.getAdminPath() + "/product/productCar/importExcel.do?success=" + success + "&fail=" + fail;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (!(cell == null || "".equals(cell))) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellvalue = cell.getStringCellValue();
        }
        if (StringUtils.isNotBlank(cellvalue)) {
            cellvalue = cellvalue.trim();
        }
        return cellvalue;
    }

    /**
     * 表单验证
     *
     * @param productCar 实体对象
     * @param model
     * @return List<String> 错误提示信息
     */
    private List<String> validate(ProductCar productCar, Model model) {
        List<String> errorList = new ArrayList<String>();
        if (StringUtils.isBlank(R.get("name"))) {
            errorList.add("名字不能为空");
        }
        if (StringUtils.isNotBlank(R.get("name")) && R.get("name").length() > 64) {
            errorList.add("名字最大长度不能超过64字符");
        }
        Long parentId = productCar.getParentId();
        if (parentId != 0 && parentId != null) {
            ProductCar pc = productCarService.selectById(parentId);
            if (pc == null) {
                errorList.add("车系车型为空");
            } else {
                Long level = pc.getLevel();
                if (level == 4) {
                    errorList.add("车系车型最多为4级");
                }
            }
        }
        return errorList;
    }
}