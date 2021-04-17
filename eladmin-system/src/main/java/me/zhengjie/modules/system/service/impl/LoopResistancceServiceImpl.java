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
import me.zhengjie.modules.system.domain.LoopResistancce;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.LoopResistancceRepository;
import me.zhengjie.modules.system.service.LoopResistancceService;
import me.zhengjie.modules.system.service.dto.LoopResistancceDto;
import me.zhengjie.modules.system.service.dto.LoopResistancceQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.LoopResistancceMapper;
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
public class LoopResistancceServiceImpl implements LoopResistancceService {

    private final LoopResistancceRepository loopResistancceRepository;
    private final LoopResistancceMapper loopResistancceMapper;

    @Override
    public Map<String,Object> queryAll(LoopResistancceQueryCriteria criteria, Pageable pageable){
        Page<LoopResistancce> page = loopResistancceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(loopResistancceMapper::toDto));
    }

    @Override
    public List<LoopResistancceDto> queryAll(LoopResistancceQueryCriteria criteria){
        return loopResistancceMapper.toDto(loopResistancceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public LoopResistancceDto findById(Long id) {
        LoopResistancce loopResistancce = loopResistancceRepository.findById(id).orElseGet(LoopResistancce::new);
        ValidationUtil.isNull(loopResistancce.getId(),"LoopResistancce","id",id);
        return loopResistancceMapper.toDto(loopResistancce);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoopResistancceDto create(LoopResistancce resources) {
        return loopResistancceMapper.toDto(loopResistancceRepository.save(resources));
    }

    @Override
    public List<LoopResistancceDto> createList(List<LoopResistancce> resources) {
        List<LoopResistancceDto> loopResistancceDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (LoopResistancce loopResistancce : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            loopResistancce.setId(snowflake.nextId());
            loopResistancceDtos.add(loopResistancceMapper.toDto(loopResistancceRepository.save(loopResistancce)));
        }
        return loopResistancceDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LoopResistancce resources) {
        LoopResistancce loopResistancce = loopResistancceRepository.findById(resources.getId()).orElseGet(LoopResistancce::new);
        ValidationUtil.isNull( loopResistancce.getId(),"LoopResistancce","id",resources.getId());
        loopResistancce.copy(resources);
        loopResistancceRepository.save(loopResistancce);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            loopResistancceRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<LoopResistancceDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LoopResistancceDto loopResistancce : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  loopResistancce.getUsername());
            map.put(" deviceId",  loopResistancce.getDeviceId());
            map.put(" station",  loopResistancce.getStation());
            map.put(" ssid",  loopResistancce.getSsid());
            map.put(" current",  loopResistancce.getCurrent());
            map.put(" resistance",  loopResistancce.getResistance());
            map.put(" inspectTime",  loopResistancce.getInspectTime());
            map.put(" saveTime",  loopResistancce.getSaveTime());
            map.put(" synState",  loopResistancce.getSynState());
            map.put(" other1",  loopResistancce.getOther1());
            map.put(" other2",  loopResistancce.getOther2());
            map.put(" other3",  loopResistancce.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}