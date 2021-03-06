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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author renrui
* @date 2021-04-12
**/
@Data
public class InsulationDto implements Serializable {

    private Long id;

    private String username;

    private String deviceId;

    private String station;

    private String ssid;

    private String settingVoltage;

    private String settingCurrent;

    private String settingTime;

    private String area1;

    private String area2;

    private String area3;

    private String area4;

    private String area5;

    private String area6;

    private Timestamp inspectTime;

    private Timestamp saveTime;

    private String synState;

    private String other1;

    private String other2;

    private String other3;
}