/**
 * SiC B2B2C Shop 使用 木兰公共许可证,第2版（Mulan PubL v2） 开源协议，请遵守相关条款，或者联系sicheng.net获取商用授权书。
 * Copyright (c) 2016 SiCheng.Net
 * SiC B2B2C Shop is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */
package com.sicheng.admin.test.service;

import com.sicheng.admin.sys.utils.UserUtils;
import com.sicheng.admin.test.dao.TestDataChildDao;
import com.sicheng.admin.test.dao.TestDataMainDao;
import com.sicheng.admin.test.entity.TestDataChild;
import com.sicheng.admin.test.entity.TestDataMain;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 主子表生成Service
 *
 * @author zhaolei
 * @version 2015-04-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TestDataMainService extends CrudService<TestDataMainDao, TestDataMain> {

    @Autowired
    private TestDataChildDao testDataChildDao;

    public TestDataMain get(Long id) {
        TestDataMain testDataMain = get1(id);
        testDataMain.setTestDataChildList(testDataChildDao.findList(new TestDataChild(testDataMain)));
        return testDataMain;
    }

    public List<TestDataMain> findList(TestDataMain testDataMain) {
        return findList1(testDataMain);
    }

    public Page<TestDataMain> findPage(Page<TestDataMain> page, TestDataMain testDataMain) {
        return findPage1(page, testDataMain);
    }

    @Transactional(readOnly = false)
    public void save(TestDataMain testDataMain) {
        save1(testDataMain);
        for (TestDataChild testDataChild : testDataMain.getTestDataChildList()) {
            if (testDataChild.getId() == null) {
                continue;
            }
            if (TestDataChild.DEL_FLAG_NORMAL.equals(testDataChild.getDelFlag())) {
                if (testDataChild.getId() == null) {
                    testDataChild.setTestDataMain(testDataMain);
                    testDataChild.preInsert(UserUtils.getUser());
                    testDataChildDao.insert(testDataChild);
                } else {
                    testDataChild.preUpdate(UserUtils.getUser());
                    testDataChildDao.update(testDataChild);
                }
            } else {
                testDataChildDao.delete(testDataChild);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(TestDataMain testDataMain) {
        delete1(testDataMain);
        testDataChildDao.delete(new TestDataChild(testDataMain));
    }

    public Page<TestDataMain> findPage1(Page<TestDataMain> page, TestDataMain testDataMain) {
        Wrapper w=new Wrapper(testDataMain);
        w.orderBy("a.id DESC");
        List list=dao.selectByWhere(page,w);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public void delete1(TestDataMain testDataMain) {
        dao.delete(testDataMain);
    }

    @Transactional(readOnly = false)
    public TestDataMain get1(Long id) {
        return dao.get(id);
    }

    @Transactional(readOnly = false)
    public void save1(TestDataMain testDataMain) {
        if (testDataMain.getIsNewRecord()) {
            testDataMain.preInsert(UserUtils.getUser());
            dao.insert(testDataMain);
        } else {
            testDataMain.preUpdate(UserUtils.getUser());
            dao.update(testDataMain);
        }
    }

    public List<TestDataMain> findList1(TestDataMain testDataMain) {
        return dao.findList(testDataMain);
    }

}