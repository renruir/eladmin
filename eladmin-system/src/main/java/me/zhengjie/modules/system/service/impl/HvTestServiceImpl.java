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
import me.zhengjie.modules.system.domain.HvTest;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.HvTestRepository;
import me.zhengjie.modules.system.service.HvTestService;
import me.zhengjie.modules.system.service.dto.HvTestDto;
import me.zhengjie.modules.system.service.dto.HvTestQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.HvTestMapper;
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
public class HvTestServiceImpl implements HvTestService {

    private final HvTestRepository hvTestRepository;
    private final HvTestMapper hvTestMapper;

    @Override
    public Map<String,Object> queryAll(HvTestQueryCriteria criteria, Pageable pageable){
        Page<HvTest> page = hvTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(hvTestMapper::toDto));
    }

    @Override
    public List<HvTestDto> queryAll(HvTestQueryCriteria criteria){
        return hvTestMapper.toDto(hvTestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public HvTestDto findById(Long id) {
        HvTest hvTest = hvTestRepository.findById(id).orElseGet(HvTest::new);
        ValidationUtil.isNull(hvTest.getId(),"HvTest","id",id);
        return hvTestMapper.toDto(hvTest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HvTestDto create(HvTest resources) {
        return hvTestMapper.toDto(hvTestRepository.save(resources));
    }

    @Override
    public List<HvTestDto> createList(List<HvTest> resources) {
        List<HvTestDto> hvTestDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (HvTest hvTest : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            hvTest.setId(snowflake.nextId());
            hvTestDtos.add(hvTestMapper.toDto(hvTestRepository.save(hvTest)));
        }
        return hvTestDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HvTest resources) {
        HvTest hvTest = hvTestRepository.findById(resources.getId()).orElseGet(HvTest::new);
        ValidationUtil.isNull( hvTest.getId(),"HvTest","id",resources.getId());
        hvTest.copy(resources);
        hvTestRepository.save(hvTest);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            hvTestRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<HvTestDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HvTestDto hvTest : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  hvTest.getUsername());
            map.put(" deviceId",  hvTest.getDeviceId());
            map.put(" station",  hvTest.getStation());
            map.put(" ssid",  hvTest.getSsid());
            map.put(" number",  hvTest.getNumber());
            map.put(" mVoltage",  hvTest.getMVoltage());
            map.put(" lvCurrent",  hvTest.getLvCurrent());
            map.put(" hvCurrent",  hvTest.getHvCurrent());
            map.put(" mDuration",  hvTest.getMDuration());
            map.put(" mResult",  hvTest.getMResult());
            map.put(" inspectTime",  hvTest.getInspectTime());
            map.put(" saveTime",  hvTest.getSaveTime());
            map.put(" synstate",  hvTest.getSynstate());
            map.put(" other1",  hvTest.getOther1());
            map.put(" other2",  hvTest.getOther2());
            map.put(" other3",  hvTest.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}