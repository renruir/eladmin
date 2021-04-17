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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.SinglePhase;
import me.zhengjie.modules.system.service.SinglePhaseService;
import me.zhengjie.modules.system.service.dto.SinglePhaseDto;
import me.zhengjie.modules.system.service.dto.SinglePhaseQueryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author renrui
* @date 2021-04-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "管理")
@RequestMapping("/api/singlePhase")
public class SinglePhaseController {

    private static final Logger logger = LoggerFactory.getLogger(SinglePhaseController.class);
    private final SinglePhaseService singlePhaseService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('singlePhase:list')")
    public void download(HttpServletResponse response, SinglePhaseQueryCriteria criteria) throws IOException {
        singlePhaseService.download(singlePhaseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询")
    @ApiOperation("查询")
    @PreAuthorize("@el.check('singlePhase:list')")
    public ResponseEntity<Object> query(SinglePhaseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(singlePhaseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增")
    @ApiOperation("新增")
    @PreAuthorize("@el.check('singlePhase:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SinglePhase resources){
        return new ResponseEntity<>(singlePhaseService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改")
    @ApiOperation("修改")
    @PreAuthorize("@el.check('singlePhase:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SinglePhase resources){
        singlePhaseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation("删除")
    @PreAuthorize("@el.check('singlePhase:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        singlePhaseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("query_by_station")
    @RequestMapping(value = "/query_by_station")
    public ResponseEntity<Object> queryByStation(@RequestParam String stationName) throws Exception {
        if (stationName == null || "".equals(stationName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info("station: " + stationName);
        SinglePhaseQueryCriteria singlePhaseQueryCriteria = new SinglePhaseQueryCriteria();
        singlePhaseQueryCriteria.setStation(stationName);
        List<SinglePhaseDto> singlePhaseDtos = singlePhaseService.queryAll(singlePhaseQueryCriteria);
        return new ResponseEntity<>(singlePhaseDtos, HttpStatus.OK);
    }
}