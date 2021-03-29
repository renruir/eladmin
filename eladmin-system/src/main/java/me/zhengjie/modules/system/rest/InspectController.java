package me.zhengjie.modules.system.rest;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.modules.system.domain.StationDevicesInfo;
import me.zhengjie.modules.system.domain.TestChange;
import me.zhengjie.modules.system.domain.ThreePhase;
import me.zhengjie.modules.system.service.StationDevicesInfoService;
import me.zhengjie.modules.system.service.TestChangeService;
import me.zhengjie.modules.system.service.ThreePhaseService;
import me.zhengjie.modules.system.service.dto.StationDevicesInfoDto;
import me.zhengjie.modules.system.service.dto.StationDevicesInfoQueryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author renrui
 * @date 2021-03-22 22:14
 */

@RequestMapping("/api/inspect")
@RestController
@RequiredArgsConstructor
public class InspectController {
    private static final Logger logger = LoggerFactory.getLogger(InspectController.class);
    private final StationDevicesInfoService stationDevicesInfoService;
    private final ThreePhaseService threePhaseService;
    private final TestChangeService testChangeService;

    @ApiOperation("add")
    @RequestMapping(value = "/add")
    @AnonymousAccess
    public ResponseEntity<Object> test(@RequestBody String resouces, @RequestParam String index) throws Exception {
        logger.info("user: " + resouces);
        if (index == null || "".equals(index) || !index.matches("[0-9]+")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        switch (Integer.parseInt(index)) {
            case 0:
                List<TestChange> testChanges = JSON.parseArray(resouces, TestChange.class);
                StationDevicesInfoQueryCriteria testChangeCriteria = new StationDevicesInfoQueryCriteria();
                for (TestChange testChange : testChanges) {
                    testChangeCriteria.setDeviceId(testChange.getDeviceId());
                    StationDevicesInfoDto devicesInfoDto = stationDevicesInfoService.queryAll(testChangeCriteria).get(0);
                    if(devicesInfoDto != null){
                        testChange.setStation(devicesInfoDto.getStationName());
                    }
                }
                return new ResponseEntity<>(testChangeService.createList(testChanges), HttpStatus.CREATED);
            case 1:
                List<ThreePhase> threePhases = JSON.parseArray(resouces, ThreePhase.class);
                StationDevicesInfoQueryCriteria threePhaseCriteria = new StationDevicesInfoQueryCriteria();
                for(ThreePhase threePhase:threePhases){
                    threePhaseCriteria.setDeviceId(threePhase.getDeviceId());
                    StationDevicesInfoDto devicesInfoDto = stationDevicesInfoService.queryAll(threePhaseCriteria).get(0);
                    if(devicesInfoDto != null){
                        threePhase.setStation(devicesInfoDto.getStationName());
                    }

                }
                return new ResponseEntity<>(threePhaseService.createList(threePhases), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
