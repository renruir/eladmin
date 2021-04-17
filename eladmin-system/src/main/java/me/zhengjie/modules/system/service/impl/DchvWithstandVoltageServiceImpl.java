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
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.DchvWithstandVoltage;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DchvWithstandVoltageRepository;
import me.zhengjie.modules.system.service.DchvWithstandVoltageService;
import me.zhengjie.modules.system.service.dto.DchvWithstandVoltageDto;
import me.zhengjie.modules.system.service.dto.DchvWithstandVoltageQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DchvWithstandVoltageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author renrui
* @date 2021-04-14
**/
@Service
@RequiredArgsConstructor
public class DchvWithstandVoltageServiceImpl implements DchvWithstandVoltageService {

    private final DchvWithstandVoltageRepository dchvWithstandVoltageRepository;
    private final DchvWithstandVoltageMapper dchvWithstandVoltageMapper;

    @Override
    public Map<String,Object> queryAll(DchvWithstandVoltageQueryCriteria criteria, Pageable pageable){
        Page<DchvWithstandVoltage> page = dchvWithstandVoltageRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dchvWithstandVoltageMapper::toDto));
    }

    @Override
    public List<DchvWithstandVoltageDto> queryAll(DchvWithstandVoltageQueryCriteria criteria){
        return dchvWithstandVoltageMapper.toDto(dchvWithstandVoltageRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DchvWithstandVoltageDto findById(Long id) {
        DchvWithstandVoltage dchvWithstandVoltage = dchvWithstandVoltageRepository.findById(id).orElseGet(DchvWithstandVoltage::new);
        ValidationUtil.isNull(dchvWithstandVoltage.getId(),"DchvWithstandVoltage","id",id);
        return dchvWithstandVoltageMapper.toDto(dchvWithstandVoltage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DchvWithstandVoltageDto create(DchvWithstandVoltage resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return dchvWithstandVoltageMapper.toDto(dchvWithstandVoltageRepository.save(resources));
    }

    @Override
    public List<DchvWithstandVoltageDto> createList(List<DchvWithstandVoltage> resources) {
        List<DchvWithstandVoltageDto> dchvWithstandVoltageDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (DchvWithstandVoltage dchvWithstandVoltage : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            dchvWithstandVoltage.setId(snowflake.nextId());
            dchvWithstandVoltageDtos.add(dchvWithstandVoltageMapper.toDto(dchvWithstandVoltageRepository.save(dchvWithstandVoltage)));
        }
        return dchvWithstandVoltageDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DchvWithstandVoltage resources) {
        DchvWithstandVoltage dchvWithstandVoltage = dchvWithstandVoltageRepository.findById(resources.getId()).orElseGet(DchvWithstandVoltage::new);
        ValidationUtil.isNull( dchvWithstandVoltage.getId(),"DchvWithstandVoltage","id",resources.getId());
        dchvWithstandVoltage.copy(resources);
        dchvWithstandVoltageRepository.save(dchvWithstandVoltage);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            dchvWithstandVoltageRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DchvWithstandVoltageDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DchvWithstandVoltageDto dchvWithstandVoltage : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  dchvWithstandVoltage.getUsername());
            map.put(" deviceId",  dchvWithstandVoltage.getDeviceId());
            map.put(" station",  dchvWithstandVoltage.getStation());
            map.put(" ssid",  dchvWithstandVoltage.getSsid());
            map.put(" mProject",  dchvWithstandVoltage.getMProject());
            map.put(" mVoltage",  dchvWithstandVoltage.getMVoltage());
            map.put(" mCurrent",  dchvWithstandVoltage.getMCurrent());
            map.put(" mTime",  dchvWithstandVoltage.getMTime());
            map.put(" inspectTime",  dchvWithstandVoltage.getInspectTime());
            map.put(" saveTime",  dchvWithstandVoltage.getSaveTime());
            map.put(" synState",  dchvWithstandVoltage.getSynState());
            map.put(" other1",  dchvWithstandVoltage.getOther1());
            map.put(" other2",  dchvWithstandVoltage.getOther2());
            map.put(" other3",  dchvWithstandVoltage.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}