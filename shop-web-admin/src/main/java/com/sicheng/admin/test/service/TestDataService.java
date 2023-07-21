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
import com.sicheng.admin.test.dao.TestDataDao;
import com.sicheng.admin.test.entity.TestData;
import com.sicheng.common.persistence.Page;
import com.sicheng.common.persistence.wrapper.Wrapper;
import com.sicheng.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 单表生成Service
 *
 * @author 赵磊
 * @version 2016-10-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TestDataService extends CrudService<TestDataDao, TestData> {

    public TestData selectById(Long id) {
        return super.selectById(id);
    }

    public List<TestData> selectByWhere(TestData testData) {
        return super.selectByWhere(new Wrapper(testData));
    }

    public Page<TestData> selectByWhere(Page<TestData> page, TestData testData) {
        return super.selectByWhere(page, new Wrapper(testData));
    }

    @Transactional(readOnly = false)
    public int saveOrUpdate(TestData testData) {
        if (testData.getIsNewRecord()) {
            testData.preInsert(UserUtils.getUser());
            //插入,只把非空的值插入到对应的字段
            return insertSelective(testData);
        } else {
            testData.preUpdate(UserUtils.getUser());
            //根据主键更新记录,只把非空的值更到对应的字段
            return updateByIdSelective(testData);
        }
    }

    @Transactional(readOnly = false)
    public int deleteById(Long id) {
        return super.deleteById(id);
    }

}