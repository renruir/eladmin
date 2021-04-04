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
import me.zhengjie.modules.system.domain.StationList;
import me.zhengjie.modules.system.service.StationListService;
import me.zhengjie.modules.system.service.dto.StationListQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author renrui
* @date 2021-03-30
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "管理")
@RequestMapping("/api/stationList")
public class StationListController {

    private final StationListService stationListService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('stationList:list')")
    public void download(HttpServletResponse response, StationListQueryCriteria criteria) throws IOException {
        stationListService.download(stationListService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询")
    @ApiOperation("查询")
//    @PreAuthorize("@el.check('stationList:list')")
    public ResponseEntity<Object> query(StationListQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(stationListService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增")
    @ApiOperation("新增")
    @PreAuthorize("@el.check('stationList:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StationList resources){
        return new ResponseEntity<>(stationListService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改")
    @ApiOperation("修改")
    @PreAuthorize("@el.check('stationList:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StationList resources){
        stationListService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation("删除")
    @PreAuthorize("@el.check('stationList:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        stationListService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}