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
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
 * @website https://el-admin.vip
 * @description /
 * @author renrui
 * @date 2021-03-25
 **/
@Data
public class ThreePhaseDto implements Serializable {

    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    private String username;

    private String station;

    private String deviceId;

    private String ssid;

    private String inspectStyle;

    private String ptValueChange;

    private String inspectName;

    private String aIx;

    private String aIrp;

    private String aIr1p;

    private String aIr3p;

    private String aIr5p;

    private String aIr7p;

    private String aUh;

    private String aIc1p;

    private String aFi;

    private String aP1;

    private String aF;

    private String aUl;

    private String bIx;

    private String bIrp;

    private String bIr1p;

    private String bIr3p;

    private String bIr5p;

    private String bIr7p;

    private String bUh;

    private String bIc1p;

    private String bFi;

    private String bP1;

    private String bF;

    private String bUl;

    private String cIx;

    private String cIrp;

    private String cIr1p;

    private String cIr3p;

    private String cIr5p;

    private String cIr7p;

    private String cUh;

    private String cIc1p;

    private String cFi;

    private String cP1;

    private String cF;

    private String cUl;

    private Timestamp inspectTime;

    private String synState;

    private Timestamp saveTime;

    private String other1;

    private String other2;

    private String other3;
}