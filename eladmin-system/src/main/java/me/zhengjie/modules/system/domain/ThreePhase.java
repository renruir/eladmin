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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @website https://el-admin.vip
 * @description /
 * @author renrui
 * @date 2021-03-25
 **/
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name="inspect_three_phase")
public class ThreePhase implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "username")
    @ApiModelProperty(value = "username")
    private String username;

    @Column(name = "station")
    @ApiModelProperty(value = "station")
    private String station;

    @Column(name = "device_id")
    @ApiModelProperty(value = "deviceId")
    private String deviceId;

    @Column(name = "ssid")
    @ApiModelProperty(value = "ssid")
    private String ssid;

    @Column(name = "inspect_style")
    @ApiModelProperty(value = "inspectStyle")
    private String inspectStyle;

    @Column(name = "pt_value_change")
    @ApiModelProperty(value = "ptValueChange")
    private String ptValueChange;

    @Column(name = "inspect_name")
    @ApiModelProperty(value = "inspectName")
    private String inspectName;

    @Column(name = "a_ix")
    @ApiModelProperty(value = "aIx")
    private String aIx;

    @Column(name = "a_irp")
    @ApiModelProperty(value = "aIrp")
    private String aIrp;

    @Column(name = "a_ir1p")
    @ApiModelProperty(value = "aIr1p")
    private String aIr1p;

    @Column(name = "a_ir3p")
    @ApiModelProperty(value = "aIr3p")
    private String aIr3p;

    @Column(name = "a_ir5p")
    @ApiModelProperty(value = "aIr5p")
    private String aIr5p;

    @Column(name = "a_ir7p")
    @ApiModelProperty(value = "aIr7p")
    private String aIr7p;

    @Column(name = "a_uH")
    @ApiModelProperty(value = "aUh")
    private String aUh;

    @Column(name = "a_ic1p")
    @ApiModelProperty(value = "aIc1p")
    private String aIc1p;

    @Column(name = "a_fi")
    @ApiModelProperty(value = "aFi")
    private String aFi;

    @Column(name = "a_p1")
    @ApiModelProperty(value = "aP1")
    private String aP1;

    @Column(name = "a_f")
    @ApiModelProperty(value = "aF")
    private String aF;

    @Column(name = "a_uL")
    @ApiModelProperty(value = "aUl")
    private String aUl;

    @Column(name = "b_ix")
    @ApiModelProperty(value = "bIx")
    private String bIx;

    @Column(name = "b_irp")
    @ApiModelProperty(value = "bIrp")
    private String bIrp;

    @Column(name = "b_ir1p")
    @ApiModelProperty(value = "bIr1p")
    private String bIr1p;

    @Column(name = "b_ir3p")
    @ApiModelProperty(value = "bIr3p")
    private String bIr3p;

    @Column(name = "b_ir5p")
    @ApiModelProperty(value = "bIr5p")
    private String bIr5p;

    @Column(name = "b_ir7p")
    @ApiModelProperty(value = "bIr7p")
    private String bIr7p;

    @Column(name = "b_uH")
    @ApiModelProperty(value = "bUh")
    private String bUh;

    @Column(name = "b_ic1p")
    @ApiModelProperty(value = "bIc1p")
    private String bIc1p;

    @Column(name = "b_fi")
    @ApiModelProperty(value = "bFi")
    private String bFi;

    @Column(name = "b_p1")
    @ApiModelProperty(value = "bP1")
    private String bP1;

    @Column(name = "b_f")
    @ApiModelProperty(value = "bF")
    private String bF;

    @Column(name = "b_uL")
    @ApiModelProperty(value = "bUl")
    private String bUl;

    @Column(name = "c_ix")
    @ApiModelProperty(value = "cIx")
    private String cIx;

    @Column(name = "c_irp")
    @ApiModelProperty(value = "cIrp")
    private String cIrp;

    @Column(name = "c_ir1p")
    @ApiModelProperty(value = "cIr1p")
    private String cIr1p;

    @Column(name = "c_ir3p")
    @ApiModelProperty(value = "cIr3p")
    private String cIr3p;

    @Column(name = "c_ir5p")
    @ApiModelProperty(value = "cIr5p")
    private String cIr5p;

    @Column(name = "c_ir7p")
    @ApiModelProperty(value = "cIr7p")
    private String cIr7p;

    @Column(name = "c_uH")
    @ApiModelProperty(value = "cUh")
    private String cUh;

    @Column(name = "c_ic1p")
    @ApiModelProperty(value = "cIc1p")
    private String cIc1p;

    @Column(name = "c_fi")
    @ApiModelProperty(value = "cFi")
    private String cFi;

    @Column(name = "c_p1")
    @ApiModelProperty(value = "cP1")
    private String cP1;

    @Column(name = "c_f")
    @ApiModelProperty(value = "cF")
    private String cF;

    @Column(name = "c_uL")
    @ApiModelProperty(value = "cUl")
    private String cUl;

    @Column(name = "inspect_time")
    @ApiModelProperty(value = "inspectTime")
    private Timestamp inspectTime;

    @Column(name = "syn_state")
    @ApiModelProperty(value = "synState")
    private String synState;

    @CreatedDate
    @Column(name = "save_time")
    @ApiModelProperty(value = "saveTime")
    private Timestamp saveTime;

    @Column(name = "other1")
    @ApiModelProperty(value = "other1")
    private String other1;

    @Column(name = "other2")
    @ApiModelProperty(value = "other2")
    private String other2;

    @Column(name = "other3")
    @ApiModelProperty(value = "other3")
    private String other3;

    public void copy(ThreePhase source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}