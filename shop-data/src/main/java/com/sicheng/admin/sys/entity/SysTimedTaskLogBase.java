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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicheng.common.persistence.DataEntity;

import java.util.Date;

/**
 * 查看定时任务的日志 Entity 父类
 *
 * @author 张加利
 * @version 2017-02-08
 */
public class SysTimedTaskLogBase<T> extends DataEntity<T> {

    private static final long serialVersionUID = 1L;
    private Long sttlId;                    // 主键
    private Long sttId;                     // 定时任务id
    private Date startTime;                 // 执行开始时间
    private Date endTime;                   // 执行结束时间
    private String result;                  // 执行结果（1成功 0失败）
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
    private String status;                  // 执行状态（1完成 0执行中）
    private Date beginStartTime;            // 开始 执行开始时间
    private Date endStartTime;              // 结束 执行开始时间
    private Date beginEndTime;              // 开始 执行结束时间
    private Date endEndTime;                // 结束 执行结束时间

    public SysTimedTaskLogBase() {
        super();
    }

    public SysTimedTaskLogBase(Long id) {
        super(id);
        this.sttlId = id;
    }

    /**
     * 描述: 获取ID
     *
     * @return
     * @see com.sicheng.common.persistence.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return sttlId;
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
        this.sttlId = id;
    }

    /**
     * getter sttlId(主键)
     */
    public Long getSttlId() {
        return sttlId;
    }

    /**
     * setter sttlId(主键)
     */
    public void setSttlId(Long sttlId) {
        this.sttlId = sttlId;
    }

    /**
     * getter sttId(定时任务id)
     */
    public Long getSttId() {
        return sttId;
    }

    /**
     * setter sttId(定时任务id)
     */
    public void setSttId(Long sttId) {
        this.sttId = sttId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter startTime(执行开始时间)
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * setter startTime(执行开始时间)
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * getter endTime(执行结束时间)
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * setter endTime(执行结束时间)
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * getter result(执行结果（1成功 0失败）)
     */
    public String getResult() {
        return result;
    }

    /**
     * setter result(执行结果（1成功 0失败）)
     */
    public void setResult(String result) {
        this.result = result;
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
     * getter status(执行状态（1完成 0执行中）)
     */
    public String getStatus() {
        return status;
    }

    /**
     * setter status(执行状态（1完成 0执行中）)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getter startTime(执行开始时间)
     */
    public Date getBeginStartTime() {
        return beginStartTime;
    }

    /**
     * setter startTime(执行开始时间)
     */
    public void setBeginStartTime(Date beginStartTime) {
        this.beginStartTime = beginStartTime;
    }

    /**
     * getter startTime(执行开始时间)
     */
    public Date getEndStartTime() {
        return endStartTime;
    }

    /**
     * setter startTime(执行开始时间)
     */
    public void setEndStartTime(Date endStartTime) {
        this.endStartTime = endStartTime;
    }

    /**
     * getter endTime(执行结束时间)
     */
    public Date getBeginEndTime() {
        return beginEndTime;
    }

    /**
     * setter endTime(执行结束时间)
     */
    public void setBeginEndTime(Date beginEndTime) {
        this.beginEndTime = beginEndTime;
    }

    /**
     * getter endTime(执行结束时间)
     */
    public Date getEndEndTime() {
        return endEndTime;
    }

    /**
     * setter endTime(执行结束时间)
     */
    public void setEndEndTime(Date endEndTime) {
        this.endEndTime = endEndTime;
    }

}