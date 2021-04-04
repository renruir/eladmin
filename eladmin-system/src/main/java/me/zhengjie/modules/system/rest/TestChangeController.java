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
import me.zhengjie.modules.system.domain.TestChange;
import me.zhengjie.modules.system.service.TestChangeService;
import me.zhengjie.modules.system.service.dto.TestChangeDto;
import me.zhengjie.modules.system.service.dto.TestChangeQueryCriteria;
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
 * @author renrui
 * @website https://el-admin.vip
 * @date 2021-03-25
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "管理")
@RequestMapping("/api/testChange")
public class TestChangeController {
    private static final Logger logger = LoggerFactory.getLogger(TestChangeController.class);
    private final TestChangeService testChangeService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('testChange:list')")
    public void download(HttpServletResponse response, TestChangeQueryCriteria criteria) throws IOException {
        testChangeService.download(testChangeService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询")
    @ApiOperation("查询")
//    @PreAuthorize("@el.check('testChange:list')")
    public ResponseEntity<Object> query(TestChangeQueryCriteria criteria, Pageable pageable) {
        logger.info("pageable: ", pageable);
        return new ResponseEntity<>(testChangeService.queryAll(criteria, pageable), HttpStatus.OK);
    }

//    @PostMapping
//    @ApiOperation("新增")
//    @PreAuthorize("@el.check('testChange:add')")
//    public ResponseEntity<Object> create(@Validated @RequestBody TestChange resources){
//        return new ResponseEntity<>(testChangeService.create(resources),HttpStatus.CREATED);
//    }

    @PostMapping
    @Log("新增")
    @ApiOperation("新增")
//    @PreAuthorize("@el.check('testChangeDatas:add')")
    @RequestMapping(value = "/add")
    @AnonymousAccess
    public ResponseEntity<Object> create(@Validated @RequestBody List<TestChange> resources) {
        return new ResponseEntity<>(testChangeService.createList(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("")
    @PreAuthorize("@el.check('testChange:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TestChange resources) {
        testChangeService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("")
    @PreAuthorize("@el.check('testChange:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        testChangeService.deleteAll(ids);
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
        TestChangeQueryCriteria testChangeQueryCriteria = new TestChangeQueryCriteria();
        testChangeQueryCriteria.setStation(stationName);
        List<TestChangeDto> testChanges = testChangeService.queryAll(testChangeQueryCriteria);
        return new ResponseEntity<>(testChanges, HttpStatus.OK);
    }
}