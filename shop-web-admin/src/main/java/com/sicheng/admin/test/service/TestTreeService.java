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

import com.sicheng.admin.test.dao.TestTreeDao;
import com.sicheng.admin.test.entity.TestTree;
import com.sicheng.common.service.TreeService;
import com.sicheng.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 树结构生成Service
 *
 * @author zhaolei
 * @version 2015-04-06
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TestTreeService extends TreeService<TestTreeDao, TestTree> {

    public TestTree get(Long id) {
        return get1(id);
    }

    public List<TestTree> findList(TestTree testTree) {
        if (StringUtils.isNotBlank(testTree.getParentIds())) {
            testTree.setParentIds("," + testTree.getParentIds() + ",");
        }
        return findList1(testTree);
    }

    @Transactional(readOnly = false)
    public void save(TestTree testTree) {
        super.save(testTree);
    }

    @Transactional(readOnly = false)
    public void delete(TestTree testTree) {
        delete1(testTree);
    }

    @Transactional(readOnly = false)
    public void delete1(TestTree testTree) {
        dao.delete(testTree);
    }

    @Transactional(readOnly = false)
    public TestTree get1(Long id) {
        return dao.get(id);
    }

    public List<TestTree> findList1(TestTree testTree) {
        return dao.findList(testTree);
    }

}