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

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.system.domain.Insulation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.InsulationRepository;
import me.zhengjie.modules.system.service.InsulationService;
import me.zhengjie.modules.system.service.dto.InsulationDto;
import me.zhengjie.modules.system.service.dto.InsulationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.InsulationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
* @date 2021-04-12
**/
@Service
@RequiredArgsConstructor
public class InsulationServiceImpl implements InsulationService {

    private final InsulationRepository insulationRepository;
    private final InsulationMapper insulationMapper;

    @Override
    public Map<String,Object> queryAll(InsulationQueryCriteria criteria, Pageable pageable){
        Page<Insulation> page = insulationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(insulationMapper::toDto));
    }

    @Override
    public List<InsulationDto> queryAll(InsulationQueryCriteria criteria){
        return insulationMapper.toDto(insulationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public InsulationDto findById(Long id) {
        Insulation insulation = insulationRepository.findById(id).orElseGet(Insulation::new);
        ValidationUtil.isNull(insulation.getId(),"Insulation","id",id);
        return insulationMapper.toDto(insulation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsulationDto create(Insulation resources) {
        return insulationMapper.toDto(insulationRepository.save(resources));
    }

    @Override
    public List<InsulationDto> createList(List<Insulation> resources) {
        List<InsulationDto> insulationDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (Insulation hvTest : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            hvTest.setId(snowflake.nextId());
            insulationDtos.add(insulationMapper.toDto(insulationRepository.save(hvTest)));
        }
        return insulationDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Insulation resources) {
        Insulation insulation = insulationRepository.findById(resources.getId()).orElseGet(Insulation::new);
        ValidationUtil.isNull( insulation.getId(),"Insulation","id",resources.getId());
        insulation.copy(resources);
        insulationRepository.save(insulation);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            insulationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<InsulationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (InsulationDto insulation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  insulation.getUsername());
            map.put(" deviceId",  insulation.getDeviceId());
            map.put(" station",  insulation.getStation());
            map.put(" ssid",  insulation.getSsid());
            map.put(" settingVoltage",  insulation.getSettingVoltage());
            map.put(" settingCurrent",  insulation.getSettingCurrent());
            map.put(" settingTime",  insulation.getSettingTime());
            map.put(" area1",  insulation.getArea1());
            map.put(" area2",  insulation.getArea2());
            map.put(" area3",  insulation.getArea3());
            map.put(" area4",  insulation.getArea4());
            map.put(" area5",  insulation.getArea5());
            map.put(" area6",  insulation.getArea6());
            map.put(" inspectTime",  insulation.getInspectTime());
            map.put(" saveTime",  insulation.getSaveTime());
            map.put(" synState",  insulation.getSynState());
            map.put(" other1",  insulation.getOther1());
            map.put(" other2",  insulation.getOther2());
            map.put(" other3",  insulation.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}