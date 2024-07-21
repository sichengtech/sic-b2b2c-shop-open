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
package com.sicheng.admin.sys.service;

import com.sicheng.admin.sys.dao.MenuDao;
import com.sicheng.admin.sys.entity.Menu;
import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.common.persistence.BaseEntity;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单Service
 *
 * @author fxx
 * @version 2017-02-3
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class MenuService extends CrudService<MenuDao, Menu> {

    //请在这里编写业务逻辑

    //TreeService父类中N个树结构的常用方法，已全部继承下来，可直接使用。

    //父父类中20个单表操作的常用方法，已全部继承下来，可直接使用。

    //注意：把多条业务sql包在一个事务中

    @Autowired
    private MenuDao menuDao;

    /**
     * 取用户可见的菜单
     * 超级管理员可取所有菜单
     * 其它用户可取自己可见的菜单
     *
     * @return
     */
    public List<Menu> findAllMenu() {
        return UserUtils.getMenuList();
    }

    /**
     * 根据菜单编号获取菜单
     *
     * @author fxx
     */
    public Menu findByMenuNum(String menuNum) {
        Wrapper wrapper = new Wrapper();
        wrapper.and("menu_num = ", menuNum);
        wrapper.and("del_flag = ", BaseEntity.DEL_FLAG_NORMAL);
        return this.selectOne(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMenu(Menu menu) {

        // 获取父节点实体
        //menu.setParent(this.getMenu(menu.getParent().getId()));
        menu.setParent(this.selectById(menu.getParent().getId()));

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();

        // 设置新的父节点串
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

        // 保存或更新实体
        if (menu.getId() == null) {
            menu.preInsert(UserUtils.getUser());
            menuDao.insert(menu);
        } else {
            menu.preUpdate(UserUtils.getUser());
            menuDao.updateById(menu);
        }

        // 更新子节点 parentIds
        Menu m = new Menu();
        m.setParentIds("," + menu.getId() + ",");//这里是like查询，表中的值格式是"0,1,95,115,126,"这样的，所以"%,95,%"可以Like查询
        List<Menu> list = menuDao.selectByWhere(null, new Wrapper(m));

        for (Menu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            menuDao.updateByIdSelective(e);
        }
    }

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return 受影响的行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        //级联删除，删除父节点的同时，删除所有子节点
        return dao.deleteCascade(id);
    }

}
