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
import me.zhengjie.modules.system.domain.SinglePhase;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.SinglePhaseRepository;
import me.zhengjie.modules.system.service.SinglePhaseService;
import me.zhengjie.modules.system.service.dto.SinglePhaseDto;
import me.zhengjie.modules.system.service.dto.SinglePhaseQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.SinglePhaseMapper;
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
* @date 2021-04-13
**/
@Service
@RequiredArgsConstructor
public class SinglePhaseServiceImpl implements SinglePhaseService {

    private final SinglePhaseRepository singlePhaseRepository;
    private final SinglePhaseMapper singlePhaseMapper;

    @Override
    public Map<String,Object> queryAll(SinglePhaseQueryCriteria criteria, Pageable pageable){
        Page<SinglePhase> page = singlePhaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(singlePhaseMapper::toDto));
    }

    @Override
    public List<SinglePhaseDto> queryAll(SinglePhaseQueryCriteria criteria){
        return singlePhaseMapper.toDto(singlePhaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SinglePhaseDto findById(Long id) {
        SinglePhase singlePhase = singlePhaseRepository.findById(id).orElseGet(SinglePhase::new);
        ValidationUtil.isNull(singlePhase.getId(),"SinglePhase","id",id);
        return singlePhaseMapper.toDto(singlePhase);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SinglePhaseDto create(SinglePhase resources) {
        return singlePhaseMapper.toDto(singlePhaseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SinglePhaseDto> createList(List<SinglePhase> resources) {
        List<SinglePhaseDto> singlePhaseDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (SinglePhase singlePhase : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            singlePhase.setId(snowflake.nextId());
            singlePhaseDtos.add(singlePhaseMapper.toDto(singlePhaseRepository.save(singlePhase)));
        }
        return singlePhaseDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SinglePhase resources) {
        SinglePhase singlePhase = singlePhaseRepository.findById(resources.getId()).orElseGet(SinglePhase::new);
        ValidationUtil.isNull( singlePhase.getId(),"SinglePhase","id",resources.getId());
        singlePhase.copy(resources);
        singlePhaseRepository.save(singlePhase);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            singlePhaseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SinglePhaseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SinglePhaseDto singlePhase : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" deviceId",  singlePhase.getDeviceId());
            map.put(" username",  singlePhase.getUsername());
            map.put(" station",  singlePhase.getStation());
            map.put(" ssid",  singlePhase.getSsid());
            map.put(" inspectStyle",  singlePhase.getInspectStyle());
            map.put(" ptValueChange",  singlePhase.getPtValueChange());
            map.put(" inspectName",  singlePhase.getInspectName());
            map.put(" ix",  singlePhase.getIx());
            map.put(" irp",  singlePhase.getIrp());
            map.put(" ir1p",  singlePhase.getIr1p());
            map.put(" ir3p",  singlePhase.getIr3p());
            map.put(" ir5p",  singlePhase.getIr5p());
            map.put(" ir7p",  singlePhase.getIr7p());
            map.put(" uH",  singlePhase.getUH());
            map.put(" ic1p",  singlePhase.getIc1p());
            map.put(" fi",  singlePhase.getFi());
            map.put(" p1",  singlePhase.getP1());
            map.put(" f",  singlePhase.getF());
            map.put(" uL",  singlePhase.getUL());
            map.put(" inspectTime",  singlePhase.getInspectTime());
            map.put(" saveTime",  singlePhase.getSaveTime());
            map.put(" synState",  singlePhase.getSynState());
            map.put(" other1",  singlePhase.getOther1());
            map.put(" other2",  singlePhase.getOther2());
            map.put(" other3",  singlePhase.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}