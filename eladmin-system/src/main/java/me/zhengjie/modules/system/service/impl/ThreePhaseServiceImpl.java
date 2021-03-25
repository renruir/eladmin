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

import me.zhengjie.modules.system.domain.TestChange;
import me.zhengjie.modules.system.domain.ThreePhase;
import me.zhengjie.modules.system.service.dto.TestChangeDto;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.ThreePhaseRepository;
import me.zhengjie.modules.system.service.ThreePhaseService;
import me.zhengjie.modules.system.service.dto.ThreePhaseDto;
import me.zhengjie.modules.system.service.dto.ThreePhaseQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.ThreePhaseMapper;
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
 * @date 2021-03-25
 **/
@Service
@RequiredArgsConstructor
public class ThreePhaseServiceImpl implements ThreePhaseService {

    private final ThreePhaseRepository threePhaseRepository;
    private final ThreePhaseMapper threePhaseMapper;

    @Override
    public Map<String,Object> queryAll(ThreePhaseQueryCriteria criteria, Pageable pageable){
        Page<ThreePhase> page = threePhaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(threePhaseMapper::toDto));
    }

    @Override
    public List<ThreePhaseDto> queryAll(ThreePhaseQueryCriteria criteria){
        return threePhaseMapper.toDto(threePhaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ThreePhaseDto findById(Long id) {
        ThreePhase threePhase = threePhaseRepository.findById(id).orElseGet(ThreePhase::new);
        ValidationUtil.isNull(threePhase.getId(),"ThreePhase","id",id);
        return threePhaseMapper.toDto(threePhase);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ThreePhaseDto create(ThreePhase resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        return threePhaseMapper.toDto(threePhaseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ThreePhaseDto> createList(List<ThreePhase> resources) {
        List<ThreePhaseDto> threePhaseDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (ThreePhase threePhase : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            threePhase.setId(snowflake.nextId());
            threePhaseDtos.add(threePhaseMapper.toDto(threePhaseRepository.save(threePhase)));
        }
        return threePhaseDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ThreePhase resources) {
        ThreePhase threePhase = threePhaseRepository.findById(resources.getId()).orElseGet(ThreePhase::new);
        ValidationUtil.isNull( threePhase.getId(),"ThreePhase","id",resources.getId());
        threePhase.copy(resources);
        threePhaseRepository.save(threePhase);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            threePhaseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ThreePhaseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ThreePhaseDto threePhase : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  threePhase.getUsername());
            map.put(" station",  threePhase.getStation());
            map.put(" deviceId",  threePhase.getDeviceId());
            map.put(" ssid",  threePhase.getSsid());
            map.put(" inspectStyle",  threePhase.getInspectStyle());
            map.put(" ptValueChange",  threePhase.getPtValueChange());
            map.put(" inspectName",  threePhase.getInspectName());
            map.put(" aIx",  threePhase.getAIx());
            map.put(" aIrp",  threePhase.getAIrp());
            map.put(" aIr1p",  threePhase.getAIr1p());
            map.put(" aIr3p",  threePhase.getAIr3p());
            map.put(" aIr5p",  threePhase.getAIr5p());
            map.put(" aIr7p",  threePhase.getAIr7p());
            map.put(" aUh",  threePhase.getAUh());
            map.put(" aIc1p",  threePhase.getAIc1p());
            map.put(" aFi",  threePhase.getAFi());
            map.put(" aP1",  threePhase.getAP1());
            map.put(" aF",  threePhase.getAF());
            map.put(" aUl",  threePhase.getAUl());
            map.put(" bIx",  threePhase.getBIx());
            map.put(" bIrp",  threePhase.getBIrp());
            map.put(" bIr1p",  threePhase.getBIr1p());
            map.put(" bIr3p",  threePhase.getBIr3p());
            map.put(" bIr5p",  threePhase.getBIr5p());
            map.put(" bIr7p",  threePhase.getBIr7p());
            map.put(" bUh",  threePhase.getBUh());
            map.put(" bIc1p",  threePhase.getBIc1p());
            map.put(" bFi",  threePhase.getBFi());
            map.put(" bP1",  threePhase.getBP1());
            map.put(" bF",  threePhase.getBF());
            map.put(" bUl",  threePhase.getBUl());
            map.put(" cIx",  threePhase.getCIx());
            map.put(" cIrp",  threePhase.getCIrp());
            map.put(" cIr1p",  threePhase.getCIr1p());
            map.put(" cIr3p",  threePhase.getCIr3p());
            map.put(" cIr5p",  threePhase.getCIr5p());
            map.put(" cIr7p",  threePhase.getCIr7p());
            map.put(" cUh",  threePhase.getCUh());
            map.put(" cIc1p",  threePhase.getCIc1p());
            map.put(" cFi",  threePhase.getCFi());
            map.put(" cP1",  threePhase.getCP1());
            map.put(" cF",  threePhase.getCF());
            map.put(" cUl",  threePhase.getCUl());
            map.put(" inspectTime",  threePhase.getInspectTime());
            map.put(" synState",  threePhase.getSynState());
            map.put(" saveTime",  threePhase.getSaveTime());
            map.put(" other1",  threePhase.getOther1());
            map.put(" other2",  threePhase.getOther2());
            map.put(" other3",  threePhase.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}