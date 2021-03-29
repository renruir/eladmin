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

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @website https://el-admin.vip
 * @description /
 * @author renrui
 * @date 2021-03-27
 **/
@Entity
@Data
@Table(name="station_devices_info")
public class StationDevicesInfo implements Serializable {

    public static final String[] field = new String[]{"站点编号", "站点名称", "设备ID", "设备名称", "备注"};
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    @ExcelIgnore
    private Long id;

    @Column(name = "station_index")
    @ApiModelProperty(value = "stationIndex")
    @Excel(name = "站点编号")
    private String stationIndex;

    @Column(name = "station_name")
    @ApiModelProperty(value = "stationName")
    @Excel(name = "站点名称")
    private String stationName;

    @Column(name = "device_id")
    @ApiModelProperty(value = "deviceId")
    @Excel(name = "设备ID")
    private String deviceId;

    @Column(name = "device_name")
    @ApiModelProperty(value = "deviceName")
    @Excel(name = "设备名称")
    private String deviceName;

    @Column(name = "attach_info")
    @ApiModelProperty(value = "attachInfo")
    @Excel(name = "备注")
    private String attachInfo;

    public void copy(StationDevicesInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}