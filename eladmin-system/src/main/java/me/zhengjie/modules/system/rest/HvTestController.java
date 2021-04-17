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
import me.zhengjie.modules.system.domain.HvTest;
import me.zhengjie.modules.system.service.HvTestService;
import me.zhengjie.modules.system.service.dto.HvTestDto;
import me.zhengjie.modules.system.service.dto.HvTestQueryCriteria;
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
@Api(tags = "高压试验变压器管理")
@RequestMapping("/api/hvTest")
public class HvTestController {

    private final HvTestService hvTestService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('hvTest:list')")
    public void download(HttpServletResponse response, HvTestQueryCriteria criteria) throws IOException {
        hvTestService.download(hvTestService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询高压试验变压器")
    @ApiOperation("查询高压试验变压器")
    @PreAuthorize("@el.check('hvTest:list')")
    public ResponseEntity<Object> query(HvTestQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(hvTestService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("query_by_station")
    @RequestMapping(value = "/query_by_station")
    public ResponseEntity<Object> queryByStation(@RequestParam String stationName) throws Exception {
        if (stationName == null || "".equals(stationName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HvTestQueryCriteria hvTestQueryCriteria = new HvTestQueryCriteria();
        hvTestQueryCriteria.setStation(stationName);
        List<HvTestDto> hvTestDtos = hvTestService.queryAll(hvTestQueryCriteria);
        return new ResponseEntity<>(hvTestDtos, HttpStatus.OK);
    }

    @PostMapping
    @Log("新增高压试验变压器")
    @ApiOperation("新增高压试验变压器")
    @PreAuthorize("@el.check('hvTest:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody HvTest resources){
        return new ResponseEntity<>(hvTestService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改高压试验变压器")
    @ApiOperation("修改高压试验变压器")
    @PreAuthorize("@el.check('hvTest:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HvTest resources){
        hvTestService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除高压试验变压器")
    @ApiOperation("删除高压试验变压器")
    @PreAuthorize("@el.check('hvTest:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        hvTestService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}