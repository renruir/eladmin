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

import me.zhengjie.modules.system.domain.StationDevicesInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.StationDevicesInfoRepository;
import me.zhengjie.modules.system.service.StationDevicesInfoService;
import me.zhengjie.modules.system.service.dto.StationDevicesInfoDto;
import me.zhengjie.modules.system.service.dto.StationDevicesInfoQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.StationDevicesInfoMapper;
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
 * @date 2021-03-27
 **/
@Service
@RequiredArgsConstructor
public class StationDevicesInfoServiceImpl implements StationDevicesInfoService {

    private final StationDevicesInfoRepository stationDevicesInfoRepository;
    private final StationDevicesInfoMapper stationDevicesInfoMapper;

    @Override
    public Map<String,Object> queryAll(StationDevicesInfoQueryCriteria criteria, Pageable pageable){
        Page<StationDevicesInfo> page = stationDevicesInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stationDevicesInfoMapper::toDto));
    }

    @Override
    public List<StationDevicesInfoDto> queryAll(StationDevicesInfoQueryCriteria criteria){
        return stationDevicesInfoMapper.toDto(stationDevicesInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public StationDevicesInfoDto findById(Long id) {
        StationDevicesInfo stationDevicesInfo = stationDevicesInfoRepository.findById(id).orElseGet(StationDevicesInfo::new);
        ValidationUtil.isNull(stationDevicesInfo.getId(),"StationDevicesInfo","id",id);
        return stationDevicesInfoMapper.toDto(stationDevicesInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StationDevicesInfoDto create(StationDevicesInfo resources) {
        return stationDevicesInfoMapper.toDto(stationDevicesInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StationDevicesInfo resources) {
        StationDevicesInfo stationDevicesInfo = stationDevicesInfoRepository.findById(resources.getId()).orElseGet(StationDevicesInfo::new);
        ValidationUtil.isNull( stationDevicesInfo.getId(),"StationDevicesInfo","id",resources.getId());
        stationDevicesInfo.copy(resources);
        stationDevicesInfoRepository.save(stationDevicesInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            stationDevicesInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StationDevicesInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StationDevicesInfoDto stationDevicesInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" stationIndex",  stationDevicesInfo.getStationIndex());
            map.put(" stationName",  stationDevicesInfo.getStationName());
            map.put(" deviceId",  stationDevicesInfo.getDeviceId());
            map.put(" deviceName",  stationDevicesInfo.getDeviceName());
            map.put(" attachInfo",  stationDevicesInfo.getAttachInfo());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

}