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
import me.zhengjie.modules.system.domain.DchvWithstandVoltage;
import me.zhengjie.modules.system.service.DchvWithstandVoltageService;
import me.zhengjie.modules.system.service.dto.DchvWithstandVoltageDto;
import me.zhengjie.modules.system.service.dto.DchvWithstandVoltageQueryCriteria;
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
* @date 2021-04-14
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "直流高压发生器(耐压测试)管理")
@RequestMapping("/api/dchvWithstandVoltage")
public class DchvWithstandVoltageController {

    private final DchvWithstandVoltageService dchvWithstandVoltageService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dchvWithstandVoltage:list')")
    public void download(HttpServletResponse response, DchvWithstandVoltageQueryCriteria criteria) throws IOException {
        dchvWithstandVoltageService.download(dchvWithstandVoltageService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询直流高压发生器(耐压测试)")
    @ApiOperation("查询直流高压发生器(耐压测试)")
    @PreAuthorize("@el.check('dchvWithstandVoltage:list')")
    public ResponseEntity<Object> query(DchvWithstandVoltageQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(dchvWithstandVoltageService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("query_by_station")
    @RequestMapping(value = "/query_by_station")
    public ResponseEntity<Object> queryByStation(@RequestParam String stationName) throws Exception {
        if (stationName == null || "".equals(stationName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DchvWithstandVoltageQueryCriteria dchvWithstandVoltageQueryCriteria = new DchvWithstandVoltageQueryCriteria();
        dchvWithstandVoltageQueryCriteria.setStation(stationName);
        List<DchvWithstandVoltageDto> dchvWithstandVoltageDtos = dchvWithstandVoltageService.queryAll(dchvWithstandVoltageQueryCriteria);
        return new ResponseEntity<>(dchvWithstandVoltageDtos, HttpStatus.OK);
    }

    @PostMapping
    @Log("新增直流高压发生器(耐压测试)")
    @ApiOperation("新增直流高压发生器(耐压测试)")
    @PreAuthorize("@el.check('dchvWithstandVoltage:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody DchvWithstandVoltage resources){
        return new ResponseEntity<>(dchvWithstandVoltageService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改直流高压发生器(耐压测试)")
    @ApiOperation("修改直流高压发生器(耐压测试)")
    @PreAuthorize("@el.check('dchvWithstandVoltage:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody DchvWithstandVoltage resources){
        dchvWithstandVoltageService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除直流高压发生器(耐压测试)")
    @ApiOperation("删除直流高压发生器(耐压测试)")
    @PreAuthorize("@el.check('dchvWithstandVoltage:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        dchvWithstandVoltageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}