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

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.SneakyThrows;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.StationDevicesInfo;
import me.zhengjie.modules.system.service.StationDevicesInfoService;
import me.zhengjie.modules.system.service.dto.StationDevicesInfoQueryCriteria;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * @website https://el-admin.vip
 * @author renrui
 * @date 2021-03-27
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "管理")
@RequestMapping("/api/stationDevicesInfo")
public class StationDevicesInfoController {
    private static final Logger logger = LoggerFactory.getLogger(StationDevicesInfoController.class);

    private final StationDevicesInfoService stationDevicesInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('stationDevicesInfo:list')")
    public void download(HttpServletResponse response, StationDevicesInfoQueryCriteria criteria) throws IOException {
        stationDevicesInfoService.download(stationDevicesInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询")
    @ApiOperation("查询")
    @PreAuthorize("@el.check('stationDevicesInfo:list')")
    public ResponseEntity<Object> query(StationDevicesInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(stationDevicesInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增")
    @ApiOperation("新增")
    @PreAuthorize("@el.check('stationDevicesInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StationDevicesInfo resources){
        return new ResponseEntity<>(stationDevicesInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改")
    @ApiOperation("修改")
    @PreAuthorize("@el.check('stationDevicesInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StationDevicesInfo resources){
        stationDevicesInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除")
    @ApiOperation("删除")
    @PreAuthorize("@el.check('stationDevicesInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        stationDevicesInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @AnonymousAccess
    @SneakyThrows
    @Log("导入excel")
    @RequestMapping(value = "/importBatch")
    @PreAuthorize("@el.check('stationDevicesInfo:add')")
    public ResponseEntity<Object> importBatch( @RequestParam("file") MultipartFile file){
        logger.info("importBatch");
        String msg = "";
        if (file != null) {
            ImportParams importParams = new ImportParams();
            importParams.setHeadRows(1);
            importParams.setTitleRows(0);
            importParams.setImportFields(StationDevicesInfo.field);
            logger.info("file: "+file.getInputStream());
            try {
                List<StationDevicesInfo> stationDevicesInfoList = ExcelImportUtil.importExcel(file.getInputStream(), StationDevicesInfo.class, importParams);
                for (StationDevicesInfo stationDevicesInfo : stationDevicesInfoList) {
                    logger.info("从Excel导入数据到数据库的详细为 ：{}", stationDevicesInfo.toString());
                    stationDevicesInfoService.create(stationDevicesInfo);
                }
                msg = "共导入了" + stationDevicesInfoList.size() + "条记录";
                logger.info(msg);
                return new ResponseEntity<>(stationDevicesInfoList.size(), HttpStatus.OK);
            }  catch (Exception e1) {
                msg = "导入失败：" + e1;
                logger.error(msg);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}