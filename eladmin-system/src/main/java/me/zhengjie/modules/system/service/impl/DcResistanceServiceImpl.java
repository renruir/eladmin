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
import me.zhengjie.modules.system.domain.DcResistance;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DcResistanceRepository;
import me.zhengjie.modules.system.service.DcResistanceService;
import me.zhengjie.modules.system.service.dto.DcResistanceDto;
import me.zhengjie.modules.system.service.dto.DcResistanceQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DcResistanceMapper;
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
* @date 2021-04-14
**/
@Service
@RequiredArgsConstructor
public class DcResistanceServiceImpl implements DcResistanceService {

    private final DcResistanceRepository dcResistanceRepository;
    private final DcResistanceMapper dcResistanceMapper;

    @Override
    public Map<String,Object> queryAll(DcResistanceQueryCriteria criteria, Pageable pageable){
        Page<DcResistance> page = dcResistanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dcResistanceMapper::toDto));
    }

    @Override
    public List<DcResistanceDto> queryAll(DcResistanceQueryCriteria criteria){
        return dcResistanceMapper.toDto(dcResistanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DcResistanceDto findById(Long id) {
        DcResistance dcResistance = dcResistanceRepository.findById(id).orElseGet(DcResistance::new);
        ValidationUtil.isNull(dcResistance.getId(),"DcResistance","id",id);
        return dcResistanceMapper.toDto(dcResistance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DcResistanceDto create(DcResistance resources) {
        return dcResistanceMapper.toDto(dcResistanceRepository.save(resources));
    }

    @Override
    public List<DcResistanceDto> createList(List<DcResistance> resources) {
        List<DcResistanceDto> dcResistanceDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (DcResistance dcResistance : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            dcResistance.setId(snowflake.nextId());
            dcResistanceDtos.add(dcResistanceMapper.toDto(dcResistanceRepository.save(dcResistance)));
        }
        return dcResistanceDtos;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DcResistance resources) {
        DcResistance dcResistance = dcResistanceRepository.findById(resources.getId()).orElseGet(DcResistance::new);
        ValidationUtil.isNull( dcResistance.getId(),"DcResistance","id",resources.getId());
        dcResistance.copy(resources);
        dcResistanceRepository.save(dcResistance);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            dcResistanceRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DcResistanceDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DcResistanceDto dcResistance : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  dcResistance.getUsername());
            map.put(" deviceId",  dcResistance.getDeviceId());
            map.put(" station",  dcResistance.getStation());
            map.put(" ssid",  dcResistance.getSsid());
            map.put(" mResistance",  dcResistance.getMResistance());
            map.put(" eResistance",  dcResistance.getEResistance());
            map.put(" fenjiePosition",  dcResistance.getFenjiePosition());
            map.put(" mPhase",  dcResistance.getMPhase());
            map.put(" inspectTime",  dcResistance.getInspectTime());
            map.put(" saveTime",  dcResistance.getSaveTime());
            map.put(" synState",  dcResistance.getSynState());
            map.put(" other1",  dcResistance.getOther1());
            map.put(" other2",  dcResistance.getOther2());
            map.put(" other3",  dcResistance.getOther3());
            map.put(" current",  dcResistance.getCurrent());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}