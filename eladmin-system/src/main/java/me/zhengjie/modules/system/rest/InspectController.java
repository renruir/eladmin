package me.zhengjie.modules.system.rest;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.service.*;
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
    private final TestChangeService testChangeService;
    private final ThreePhaseService threePhaseService;
    private final SinglePhaseService singlePhaseService;
    private final DcResistanceService dcResistanceService;
    private final LoopResistancceService loopResistancceService;
    private final HvGeneratorService hvGeneratorService;
    private final DchvWithstandVoltageService dchvWithstandVoltageService;
    private final DcHvArresterService dcHvArresterService;
    private final InsulationService insulationService;
    private final HvTestService hvTestService;

    @ApiOperation("add")
    @RequestMapping(value = "/add")
    @AnonymousAccess
    public ResponseEntity<Object> syncData(@RequestBody String resouces, @RequestParam String index) throws Exception {
        logger.info("user: " + resouces);
        if (index == null || "".equals(index) || !index.matches("[0-9]+")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        switch (Integer.parseInt(index)) {
            case 0:
                List<TestChange> testChanges = JSON.parseArray(resouces, TestChange.class);
                if (testChanges == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria testChangeCriteria = new StationDevicesInfoQueryCriteria();
                for (TestChange testChange : testChanges) {
                    testChangeCriteria.setDeviceId(testChange.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(testChangeCriteria);
                    if (devicesInfoDtos != null&& devicesInfoDtos.size() > 1) {
                        testChange.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(testChangeService.createList(testChanges), HttpStatus.CREATED);
            case 1:
                List<ThreePhase> threePhases = JSON.parseArray(resouces, ThreePhase.class);
                if (threePhases == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria threePhaseCriteria = new StationDevicesInfoQueryCriteria();
                for (ThreePhase threePhase : threePhases) {
                    threePhaseCriteria.setDeviceId(threePhase.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(threePhaseCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        threePhase.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(threePhaseService.createList(threePhases), HttpStatus.CREATED);
            case 2:
                List<SinglePhase> singlePhases = JSON.parseArray(resouces, SinglePhase.class);
                if (singlePhases == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria singlePhaseCriteria = new StationDevicesInfoQueryCriteria();
                for (SinglePhase singlePhase : singlePhases) {
                    singlePhaseCriteria.setDeviceId(singlePhase.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(singlePhaseCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        singlePhase.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(singlePhaseService.createList(singlePhases), HttpStatus.CREATED);
            case 3:
                List<DcResistance> dcResistances = JSON.parseArray(resouces, DcResistance.class);
                if (dcResistances == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria dcResistancesCriteria = new StationDevicesInfoQueryCriteria();
                for (DcResistance dcResistance : dcResistances) {
                    dcResistancesCriteria.setDeviceId(dcResistance.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(dcResistancesCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        dcResistance.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(dcResistanceService.createList(dcResistances), HttpStatus.CREATED);
            case 4:
                List<LoopResistancce> loopResistancces = JSON.parseArray(resouces, LoopResistancce.class);
                if (loopResistancces == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria loopResistanccesCriteria = new StationDevicesInfoQueryCriteria();
                for (LoopResistancce loopResistancce : loopResistancces) {
                    loopResistanccesCriteria.setDeviceId(loopResistancce.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(loopResistanccesCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        loopResistancce.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(loopResistancceService.createList(loopResistancces), HttpStatus.CREATED);
            case 5:
                List<Insulation> insulations = JSON.parseArray(resouces, Insulation.class);
                if (insulations == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria insulationCriteria = new StationDevicesInfoQueryCriteria();
                for (Insulation insulation : insulations) {
                    insulationCriteria.setDeviceId(insulation.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(insulationCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        insulation.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(insulationService.createList(insulations), HttpStatus.CREATED);
            case 6:
                List<HvGenerator> hvGenerators = JSON.parseArray(resouces, HvGenerator.class);
                if (hvGenerators == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria hvGeneratorsCriteria = new StationDevicesInfoQueryCriteria();
                for (HvGenerator hvGenerator : hvGenerators) {
                    hvGeneratorsCriteria.setDeviceId(hvGenerator.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(hvGeneratorsCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        hvGenerator.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(hvGeneratorService.createList(hvGenerators), HttpStatus.CREATED);
            case 7:
                List<DchvWithstandVoltage> dchvWithstandVoltages = JSON.parseArray(resouces, DchvWithstandVoltage.class);
                if (dchvWithstandVoltages == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria dchvWithstandVoltagesCriteria = new StationDevicesInfoQueryCriteria();
                for (DchvWithstandVoltage dchvWithstandVoltage : dchvWithstandVoltages) {
                    dchvWithstandVoltagesCriteria.setDeviceId(dchvWithstandVoltage.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(dchvWithstandVoltagesCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        dchvWithstandVoltage.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(dchvWithstandVoltageService.createList(dchvWithstandVoltages), HttpStatus.CREATED);
            case 8:
                List<DcHvArrester> dcHvArresters = JSON.parseArray(resouces, DcHvArrester.class);
                if (dcHvArresters == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria dcHvArrestersCriteria = new StationDevicesInfoQueryCriteria();
                for (DcHvArrester dcHvArrester : dcHvArresters) {
                    dcHvArrestersCriteria.setDeviceId(dcHvArrester.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(dcHvArrestersCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        dcHvArrester.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(dcHvArresterService.createList(dcHvArresters), HttpStatus.CREATED);
            case 9:
                List<HvTest> hvTests = JSON.parseArray(resouces, HvTest.class);
                if (hvTests == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                StationDevicesInfoQueryCriteria hvtestCriteria = new StationDevicesInfoQueryCriteria();
                for (HvTest hvTest : hvTests) {
                    hvtestCriteria.setDeviceId(hvTest.getDeviceId());
                    List<StationDevicesInfoDto> devicesInfoDtos = stationDevicesInfoService.queryAll(hvtestCriteria);
                    if (devicesInfoDtos != null && devicesInfoDtos.size() > 1) {
                        hvTest.setStation(devicesInfoDtos.get(0).getStationName());
                    }
                }
                return new ResponseEntity<>(hvTestService.createList(hvTests), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
