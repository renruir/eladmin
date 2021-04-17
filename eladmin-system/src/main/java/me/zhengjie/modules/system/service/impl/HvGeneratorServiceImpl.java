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
import me.zhengjie.modules.system.domain.HvGenerator;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.HvGeneratorRepository;
import me.zhengjie.modules.system.service.HvGeneratorService;
import me.zhengjie.modules.system.service.dto.HvGeneratorDto;
import me.zhengjie.modules.system.service.dto.HvGeneratorQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.HvGeneratorMapper;
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
public class HvGeneratorServiceImpl implements HvGeneratorService {

    private final HvGeneratorRepository hvGeneratorRepository;
    private final HvGeneratorMapper hvGeneratorMapper;

    @Override
    public Map<String,Object> queryAll(HvGeneratorQueryCriteria criteria, Pageable pageable){
        Page<HvGenerator> page = hvGeneratorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(hvGeneratorMapper::toDto));
    }

    @Override
    public List<HvGeneratorDto> queryAll(HvGeneratorQueryCriteria criteria){
        return hvGeneratorMapper.toDto(hvGeneratorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public HvGeneratorDto findById(Long id) {
        HvGenerator hvGenerator = hvGeneratorRepository.findById(id).orElseGet(HvGenerator::new);
        ValidationUtil.isNull(hvGenerator.getId(),"HvGenerator","id",id);
        return hvGeneratorMapper.toDto(hvGenerator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HvGeneratorDto create(HvGenerator resources) {
        return hvGeneratorMapper.toDto(hvGeneratorRepository.save(resources));
    }

    @Override
    public List<HvGeneratorDto> createList(List<HvGenerator> resources) {
        List<HvGeneratorDto> hvGeneratorDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (HvGenerator hvGenerator : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            hvGenerator.setId(snowflake.nextId());
            hvGeneratorDtos.add(hvGeneratorMapper.toDto(hvGeneratorRepository.save(hvGenerator)));
        }
        return hvGeneratorDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HvGenerator resources) {
        HvGenerator hvGenerator = hvGeneratorRepository.findById(resources.getId()).orElseGet(HvGenerator::new);
        ValidationUtil.isNull( hvGenerator.getId(),"HvGenerator","id",resources.getId());
        hvGenerator.copy(resources);
        hvGeneratorRepository.save(hvGenerator);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            hvGeneratorRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<HvGeneratorDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HvGeneratorDto hvGenerator : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  hvGenerator.getUsername());
            map.put(" deviceId",  hvGenerator.getDeviceId());
            map.put(" station",  hvGenerator.getStation());
            map.put(" ssid",  hvGenerator.getSsid());
            map.put(" mFrequency",  hvGenerator.getMFrequency());
            map.put(" mTime",  hvGenerator.getMTime());
            map.put(" mVoltage",  hvGenerator.getMVoltage());
            map.put(" mCurrent",  hvGenerator.getMCurrent());
            map.put(" inspectTime",  hvGenerator.getInspectTime());
            map.put(" saveTime",  hvGenerator.getSaveTime());
            map.put(" synState",  hvGenerator.getSynState());
            map.put(" other1",  hvGenerator.getOther1());
            map.put(" other2",  hvGenerator.getOther2());
            map.put(" other3",  hvGenerator.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}