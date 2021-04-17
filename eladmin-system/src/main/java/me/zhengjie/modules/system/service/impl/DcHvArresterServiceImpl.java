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
import me.zhengjie.modules.system.domain.DcHvArrester;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.DcHvArresterRepository;
import me.zhengjie.modules.system.service.DcHvArresterService;
import me.zhengjie.modules.system.service.dto.DcHvArresterDto;
import me.zhengjie.modules.system.service.dto.DcHvArresterQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.DcHvArresterMapper;
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
 * @author renrui
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-04-14
 **/
@Service
@RequiredArgsConstructor
public class DcHvArresterServiceImpl implements DcHvArresterService {

    private final DcHvArresterRepository dcHvArresterRepository;
    private final DcHvArresterMapper dcHvArresterMapper;

    @Override
    public Map<String, Object> queryAll(DcHvArresterQueryCriteria criteria, Pageable pageable) {
        Page<DcHvArrester> page = dcHvArresterRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(dcHvArresterMapper::toDto));
    }

    @Override
    public List<DcHvArresterDto> queryAll(DcHvArresterQueryCriteria criteria) {
        return dcHvArresterMapper.toDto(dcHvArresterRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public DcHvArresterDto findById(Long id) {
        DcHvArrester dcHvArrester = dcHvArresterRepository.findById(id).orElseGet(DcHvArrester::new);
        ValidationUtil.isNull(dcHvArrester.getId(), "DcHvArrester", "id", id);
        return dcHvArresterMapper.toDto(dcHvArrester);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DcHvArresterDto create(DcHvArrester resources) {
        return dcHvArresterMapper.toDto(dcHvArresterRepository.save(resources));
    }

    @Override
    public List<DcHvArresterDto> createList(List<DcHvArrester> resources) {
        List<DcHvArresterDto> dcHvArresterDtos = new ArrayList<>();
        if (resources == null) {
            return null;
        }
        for (DcHvArrester dcHvArrester : resources) {
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            dcHvArrester.setId(snowflake.nextId());
            dcHvArresterDtos.add(dcHvArresterMapper.toDto(dcHvArresterRepository.save(dcHvArrester)));
        }
        return dcHvArresterDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DcHvArrester resources) {
        DcHvArrester dcHvArrester = dcHvArresterRepository.findById(resources.getId()).orElseGet(DcHvArrester::new);
        ValidationUtil.isNull(dcHvArrester.getId(), "DcHvArrester", "id", resources.getId());
        dcHvArrester.copy(resources);
        dcHvArresterRepository.save(dcHvArrester);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            dcHvArresterRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DcHvArresterDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DcHvArresterDto dcHvArrester : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(" username", dcHvArrester.getUsername());
            map.put(" deviceId", dcHvArrester.getDeviceId());
            map.put(" station", dcHvArrester.getStation());
            map.put(" ssid", dcHvArrester.getSsid());
            map.put(" mProject", dcHvArrester.getMProject());
            map.put(" mVoltage", dcHvArrester.getMVoltage());
            map.put(" mResidual", dcHvArrester.getMResidual());
            map.put(" inspectTime", dcHvArrester.getInspectTime());
            map.put(" saveTime", dcHvArrester.getSaveTime());
            map.put(" synState", dcHvArrester.getSynState());
            map.put(" other1", dcHvArrester.getOther1());
            map.put(" other2", dcHvArrester.getOther2());
            map.put(" other3", dcHvArrester.getOther3());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}