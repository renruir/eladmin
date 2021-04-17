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
import me.zhengjie.modules.system.domain.HvGenerator;
import me.zhengjie.modules.system.service.HvGeneratorService;
import me.zhengjie.modules.system.service.dto.HvGeneratorDto;
import me.zhengjie.modules.system.service.dto.HvGeneratorQueryCriteria;
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
@Api(tags = "超低频高压发生器管理")
@RequestMapping("/api/hvGenerator")
public class HvGeneratorController {

    private final HvGeneratorService hvGeneratorService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('hvGenerator:list')")
    public void download(HttpServletResponse response, HvGeneratorQueryCriteria criteria) throws IOException {
        hvGeneratorService.download(hvGeneratorService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询超低频高压发生器")
    @ApiOperation("查询超低频高压发生器")
    @PreAuthorize("@el.check('hvGenerator:list')")
    public ResponseEntity<Object> query(HvGeneratorQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(hvGeneratorService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增超低频高压发生器")
    @ApiOperation("新增超低频高压发生器")
    @PreAuthorize("@el.check('hvGenerator:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody HvGenerator resources){
        return new ResponseEntity<>(hvGeneratorService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改超低频高压发生器")
    @ApiOperation("修改超低频高压发生器")
    @PreAuthorize("@el.check('hvGenerator:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HvGenerator resources){
        hvGeneratorService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除超低频高压发生器")
    @ApiOperation("删除超低频高压发生器")
    @PreAuthorize("@el.check('hvGenerator:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        hvGeneratorService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("query_by_station")
    @RequestMapping(value = "/query_by_station")
    public ResponseEntity<Object> queryByStation(@RequestParam String stationName) throws Exception {
        if (stationName == null || "".equals(stationName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        HvGeneratorQueryCriteria hvGeneratorQueryCriteria = new HvGeneratorQueryCriteria();
        hvGeneratorQueryCriteria.setStation(stationName);
        List<HvGeneratorDto> hvGeneratorDtos = hvGeneratorService.queryAll(hvGeneratorQueryCriteria);
        return new ResponseEntity<>(hvGeneratorDtos, HttpStatus.OK);
    }
}