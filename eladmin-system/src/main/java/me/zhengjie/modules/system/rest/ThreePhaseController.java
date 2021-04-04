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

import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.ThreePhase;
import me.zhengjie.modules.system.service.ThreePhaseService;
import me.zhengjie.modules.system.service.dto.ThreePhaseDto;
import me.zhengjie.modules.system.service.dto.ThreePhaseQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * @website https://el-admin.vip
 * @author renrui
 * @date 2021-03-25
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "管理")
@RequestMapping("/api/threePhase")
public class ThreePhaseController {

    private final ThreePhaseService threePhaseService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('threePhase:list')")
    public void download(HttpServletResponse response, ThreePhaseQueryCriteria criteria) throws IOException {
        threePhaseService.download(threePhaseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询")
    @ApiOperation("查询")
//    @PreAuthorize("@el.check('threePhase:list')")
    public ResponseEntity<Object> query(ThreePhaseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(threePhaseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

//    @PostMapping
//    @Log("新增")
//    @ApiOperation("新增")
//    @PreAuthorize("@el.check('threePhase:add')")
//    public ResponseEntity<Object> create(@Validated @RequestBody ThreePhase resources){
//        return new ResponseEntity<>(threePhaseService.create(resources),HttpStatus.CREATED);
//    }

    @PostMapping
    @Log("新增")
    @ApiOperation("新增")
//    @PreAuthorize("@el.check('testChangeDatas:add')")
    @RequestMapping(value = "/add")
    @AnonymousAccess
    public ResponseEntity<Object> create(@Validated @RequestBody List<ThreePhase> resources) {
        return new ResponseEntity<>(threePhaseService.createList(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改")
    @ApiOperation("修改")
    @PreAuthorize("@el.check('threePhase:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ThreePhase resources){
        threePhaseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation("删除")
    @PreAuthorize("@el.check('threePhase:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        threePhaseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("query_by_station")
    @RequestMapping(value = "/query_by_station")
    public ResponseEntity<Object> queryByStation(@RequestParam String stationName) throws Exception {
        if (stationName == null || "".equals(stationName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ThreePhaseQueryCriteria threePhaseQueryCriteria = new ThreePhaseQueryCriteria();
        threePhaseQueryCriteria.setStation(stationName);
        return new ResponseEntity<>(threePhaseService.queryAll(threePhaseQueryCriteria), HttpStatus.OK);
    }
}