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
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name="inspect_test_change")
public class TestChange implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "current_change")
    @ApiModelProperty(value = "currentChange")
    private String currentChange;

    @Column(name = "connect_style")
    @ApiModelProperty(value = "connectStyle")
    private String connectStyle;

    @Column(name = "fenjie_value")
    @ApiModelProperty(value = "fenjieValue")
    private String fenjieValue;

    @Column(name = "fenjie_bit")
    @ApiModelProperty(value = "fenjieBit")
    private String fenjieBit;

    @Column(name = "ca_change")
    @ApiModelProperty(value = "caChange")
    private String caChange;

    @Column(name = "ca_error")
    @ApiModelProperty(value = "caError")
    private String caError;

    @Column(name = "ca_angle")
    @ApiModelProperty(value = "caAngle")
    private String caAngle;

    @Column(name = "bc_change")
    @ApiModelProperty(value = "bcChange")
    private String bcChange;

    @Column(name = "bc_error")
    @ApiModelProperty(value = "bcError")
    private String bcError;

    @Column(name = "bc_angle")
    @ApiModelProperty(value = "bcAngle")
    private String bcAngle;

    @Column(name = "ab_change")
    @ApiModelProperty(value = "abChange")
    private String abChange;

    @Column(name = "ab_error")
    @ApiModelProperty(value = "abError")
    private String abError;

    @Column(name = "ab_angle")
    @ApiModelProperty(value = "abAngle")
    private String abAngle;

    @Column(name = "inspect_time")
    @ApiModelProperty(value = "inspectTime")
    private Timestamp inspectTime;

    @Column(name = "sys_state")
    @ApiModelProperty(value = "sysState")
    private String sysState;

    @CreatedDate
    @Column(name = "save_time")
    @ApiModelProperty(value = "saveTime")
    private Timestamp saveTime;

    @Column(name = "other_1")
    @ApiModelProperty(value = "other1")
    private String other1;

    @Column(name = "other_2")
    @ApiModelProperty(value = "other2")
    private String other2;

    @Column(name = "other_3")
    @ApiModelProperty(value = "other3")
    private String other3;

    public void copy(TestChange source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}