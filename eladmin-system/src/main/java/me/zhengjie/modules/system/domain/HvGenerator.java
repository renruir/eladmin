/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author renrui
* @date 2021-04-13
**/
@Entity
@Data
@Table(name="inspect_hv_generator")
public class HvGenerator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "username")
    @ApiModelProperty(value = "username")
    private String username;

    @Column(name = "device_id")
    @ApiModelProperty(value = "deviceId")
    private String deviceId;

    @Column(name = "station")
    @ApiModelProperty(value = "station")
    private String station;

    @Column(name = "ssid")
    @ApiModelProperty(value = "ssid")
    private String ssid;

    @Column(name = "m_frequency")
    @ApiModelProperty(value = "mFrequency")
    private String mFrequency;

    @Column(name = "m_time")
    @ApiModelProperty(value = "mTime")
    private String mTime;

    @Column(name = "m_voltage")
    @ApiModelProperty(value = "mVoltage")
    private String mVoltage;

    @Column(name = "m_current")
    @ApiModelProperty(value = "mCurrent")
    private String mCurrent;

    @Column(name = "inspect_time")
    @ApiModelProperty(value = "inspectTime")
    private Timestamp inspectTime;

    @CreatedDate
    @Column(name = "save_time")
    @ApiModelProperty(value = "saveTime")
    private Timestamp saveTime;

    @Column(name = "syn_state")
    @ApiModelProperty(value = "synState")
    private String synState;

    @Column(name = "other1")
    @ApiModelProperty(value = "other1")
    private String other1;

    @Column(name = "other2")
    @ApiModelProperty(value = "other2")
    private String other2;

    @Column(name = "other3")
    @ApiModelProperty(value = "other3")
    private String other3;

    public void copy(HvGenerator source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}