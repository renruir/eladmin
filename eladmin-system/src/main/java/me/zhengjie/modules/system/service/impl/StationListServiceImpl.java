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

import me.zhengjie.modules.system.domain.StationList;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.StationListRepository;
import me.zhengjie.modules.system.service.StationListService;
import me.zhengjie.modules.system.service.dto.StationListDto;
import me.zhengjie.modules.system.service.dto.StationListQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.StationListMapper;
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
* @date 2021-03-30
**/
@Service
@RequiredArgsConstructor
public class StationListServiceImpl implements StationListService {

    private final StationListRepository stationListRepository;
    private final StationListMapper stationListMapper;

    @Override
    public Map<String,Object> queryAll(StationListQueryCriteria criteria, Pageable pageable){
        Page<StationList> page = stationListRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stationListMapper::toDto));
    }

    @Override
    public List<StationListDto> queryAll(StationListQueryCriteria criteria){
        return stationListMapper.toDto(stationListRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StationListDto findById(Long id) {
        StationList stationList = stationListRepository.findById(id).orElseGet(StationList::new);
        ValidationUtil.isNull(stationList.getId(),"StationList","id",id);
        return stationListMapper.toDto(stationList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StationListDto create(StationList resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return stationListMapper.toDto(stationListRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StationList resources) {
        StationList stationList = stationListRepository.findById(resources.getId()).orElseGet(StationList::new);
        ValidationUtil.isNull( stationList.getId(),"StationList","id",resources.getId());
        stationList.copy(resources);
        stationListRepository.save(stationList);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            stationListRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StationListDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StationListDto stationList : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" stationIndex",  stationList.getStationIndex());
            map.put(" stationName",  stationList.getStationName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}