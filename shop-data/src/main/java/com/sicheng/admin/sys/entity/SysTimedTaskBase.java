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
package com.sicheng.admin.sys.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

/**
 * 管理定时任务 Entity 父类
 *
 * @author 张加利
 * @version 2017-02-08
 */
public class SysTimedTaskBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long sttId;                     // 主键id
    private String taskName;                // 任务名称
    private String taskExplain;             // 任务说明
    private String taskTime;                // 执行时间（表达式）
    private String timeExplain;             // 执行时间说明
    private String status;                  // 状态(1启用，0禁用)
    @JsonIgnore
    private String bak1;                    // 备用字段1
    @JsonIgnore
    private String bak2;                    // 备用字段2
    @JsonIgnore
    private String bak3;                    // 备用字段3
    @JsonIgnore
    private String bak4;                    // 备用字段4
    @JsonIgnore
    private String bak5;                    // 备用字段5
    private Integer timedTaskNum;           // 编号

    public SysTimedTaskBase() {
        super();
    }

    public SysTimedTaskBase(Long id) {
        super(id);
        this.sttId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return sttId;
    }

    /**
     * 描述: 设置ID
     *
     * @param id
     * @see com.sicheng.common.persistence.BaseEntity#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
        this.sttId = id;
    }

    /**
     * getter sttId(主键id)
     */
    public Long getSttId() {
        return sttId;
    }

    /**
     * setter sttId(主键id)
     */
    public void setSttId(Long sttId) {
        this.sttId = sttId;
    }

    /**
     * getter taskName(任务名称)
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * setter taskName(任务名称)
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * getter taskExplain(任务说明)
     */
    public String getTaskExplain() {
        return taskExplain;
    }

    /**
     * setter taskExplain(任务说明)
     */
    public void setTaskExplain(String taskExplain) {
        this.taskExplain = taskExplain;
    }

    /**
     * getter taskTime(执行时间（表达式）)
     */
    public String getTaskTime() {
        return taskTime;
    }

    /**
     * setter taskTime(执行时间（表达式）)
     */
    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    /**
     * getter timeExplain(执行时间说明)
     */
    public String getTimeExplain() {
        return timeExplain;
    }

    /**
     * setter timeExplain(执行时间说明)
     */
    public void setTimeExplain(String timeExplain) {
        this.timeExplain = timeExplain;
    }

    /**
     * getter status(状态(1启用，0禁用))
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(状态(1启用，0禁用))
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter bak1(备用字段)
     */
    public String getBak1() {
        return bak1;
    }

    /**
     * setter bak1(备用字段)
     */
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    /**
     * getter bak2(备用字段)
     */
    public String getBak2() {
        return bak2;
    }

    /**
     * setter bak2(备用字段)
     */
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    /**
     * getter bak3(备用字段)
     */
    public String getBak3() {
        return bak3;
    }

    /**
     * setter bak3(备用字段)
     */
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }

    /**
     * getter bak4(备用字段)
     */
    public String getBak4() {
        return bak4;
    }

    /**
     * setter bak4(备用字段)
     */
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }

    /**
     * getter bak5(备用字段)
     */
    public String getBak5() {
        return bak5;
    }

    /**
     * setter bak5(备用字段)
     */
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }

    /**
     * getter timedTaskNum(编号)
     */
    public Integer getTimedTaskNum() {
        return timedTaskNum;
    }

    /**
     * setter timedTaskNum(编号)
     */
    public void setTimedTaskNum(Integer timedTaskNum) {
        this.timedTaskNum = timedTaskNum;
    }


}