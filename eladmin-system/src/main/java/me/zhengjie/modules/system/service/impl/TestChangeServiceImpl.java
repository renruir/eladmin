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
import me.zhengjie.modules.system.domain.TestChange;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TestChangeRepository;
import me.zhengjie.modules.system.service.TestChangeService;
import me.zhengjie.modules.system.service.dto.TestChangeDto;
import me.zhengjie.modules.system.service.dto.TestChangeQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TestChangeMapper;
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
 * @date 2021-03-25
 **/
@Service
@RequiredArgsConstructor
public class TestChangeServiceImpl implements TestChangeService {

    private final TestChangeRepository testChangeRepository;
    private final TestChangeMapper testChangeMapper;

    @Override
    public Map<String,Object> queryAll(TestChangeQueryCriteria criteria, Pageable pageable){
        Page<TestChange> page = testChangeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(testChangeMapper::toDto));
    }

    @Override
    public List<TestChangeDto> queryAll(TestChangeQueryCriteria criteria){
        return testChangeMapper.toDto(testChangeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public TestChangeDto findById(Long id) {
        TestChange testChange = testChangeRepository.findById(id).orElseGet(TestChange::new);
        ValidationUtil.isNull(testChange.getId(),"TestChange","id",id);
        return testChangeMapper.toDto(testChange);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestChangeDto create(TestChange resources) {
        return testChangeMapper.toDto(testChangeRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestChangeDto> createList(List<TestChange> resources) {
        List<TestChangeDto> testChangeDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (TestChange testChange : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            testChange.setId(snowflake.nextId());
            testChangeDtos.add(testChangeMapper.toDto(testChangeRepository.save(testChange)));
        }
        return testChangeDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TestChange resources) {
        TestChange testChange = testChangeRepository.findById(resources.getId()).orElseGet(TestChange::new);
        ValidationUtil.isNull( testChange.getId(),"TestChange","id",resources.getId());
        testChange.copy(resources);
        testChangeRepository.save(testChange);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            testChangeRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TestChangeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestChangeDto testChange : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" username",  testChange.getUsername());
            map.put(" station",  testChange.getStation());
            map.put(" deviceId",  testChange.getDeviceId());
            map.put(" ssid",  testChange.getSsid());
            map.put(" currentChange",  testChange.getCurrentChange());
            map.put(" connectStyle",  testChange.getConnectStyle());
            map.put(" fenjieValue",  testChange.getFenjieValue());
            map.put(" fenjieBit",  testChange.getFenjieBit());
            map.put(" caChange",  testChange.getCaChange());
            map.put(" caError",  testChange.getCaError());
            map.put(" caAngle",  testChange.getCaAngle());
            map.put(" bcChange",  testChange.getBcChange());
            map.put(" bcError",  testChange.getBcError());
            map.put(" bcAngle",  testChange.getBcAngle());
            map.put(" abChange",  testChange.getAbChange());
            map.put(" abError",  testChange.getAbError());
            map.put(" abAngle",  testChange.getAbAngle());
            map.put(" inspectTime",  testChange.getInspectTime());
            map.put(" sysState",  testChange.getSysState());
            map.put(" saveTime",  testChange.getSaveTime());
            map.put(" other1",  testChange.getOther1());
            map.put(" other2",  testChange.getOther2());
            map.put(" other3",  testChange.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}