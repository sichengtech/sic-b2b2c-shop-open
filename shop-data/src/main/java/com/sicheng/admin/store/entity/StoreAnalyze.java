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
package com.sicheng.admin.store.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 店铺统计表 Entity 子类，请把你的业务代码写在这里
 *
 * @author 贺东泽
 * @version 2019-11-18
 */
public class StoreAnalyze extends StoreAnalyzeBase<StoreAnalyze> {

    private static final long serialVersionUID = 1L;

    public StoreAnalyze() {
        super();
    }

    public StoreAnalyze(Long id) {
        super(id);
    }

    //对于实体类的扩展代码，请写在这里
    private String starLevel;

    /**
     * 计算店铺星级的css样式
     * 拼接CSS的class name：star1 ~ star5 供模板页面使用
     *
     * @return
     */
    public String getStarLevel() {
        if (starLevel != null){
            return starLevel;
        }else if (starLevel == null && this.getProductScore() != null && this.getLogisticsScore() != null && this.getServiceAttitudeScore() != null) {
            Double level = new Double(this.getProductScore()) + new Double(this.getLogisticsScore()) + new Double(this.getServiceAttitudeScore());
            long avg = Math.round( level / 3); //计算一个平均分，再四舍五入取整数，4.4=4， 4.5=5 。
            starLevel = "star" + avg;
            return starLevel;
        }else{
            return "star5"; //默认值
        }
    }

}