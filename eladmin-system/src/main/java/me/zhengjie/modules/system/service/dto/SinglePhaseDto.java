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
* @date 2021-04-13
**/
@Data
public class SinglePhaseDto implements Serializable {

    private Long id;

    private String deviceId;

    private String username;

    private String station;

    private String ssid;

    private String inspectStyle;

    private String ptValueChange;

    private String inspectName;

    private String ix;

    private String irp;

    private String ir1p;

    private String ir3p;

    private String ir5p;

    private String ir7p;

    private String uH;

    private String ic1p;

    private String fi;

    private String p1;

    private String f;

    private String uL;

    private Timestamp inspectTime;

    private Timestamp saveTime;

    private String synState;

    private String other1;

    private String other2;

    private String other3;
}