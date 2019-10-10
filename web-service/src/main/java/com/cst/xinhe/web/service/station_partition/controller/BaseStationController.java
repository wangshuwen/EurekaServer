package com.cst.xinhe.web.service.station_partition.controller;

import com.cst.xinhe.base.controller.impl.BaseController;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeWebException;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.common.utils.string.StringUtils;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.dao.updateIp.StationIpPortMapper;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.updateIp.StationIpPort;
import com.cst.xinhe.persistence.vo.req.BaseStationBindingStandardVO;
import com.cst.xinhe.persistence.vo.resp.BaseStationPositionVO;
import com.cst.xinhe.web.service.feign.client.StationMonitorServerClient;
import com.cst.xinhe.web.service.feign.client.TerminalMonitorClient;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.cst.xinhe.web.service.staff_group_terminal.service.TerminalService;
import com.cst.xinhe.web.service.station_partition.service.BaseStationService;
import com.cst.xinhe.web.service.station_partition.service.OfflineStationService;
import com.cst.xinhe.web.service.station_partition.service.PartitionService;
import com.cst.xinhe.web.service.system.service.LevelSettingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @ClassName BaseStationController
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/26 16:04
 * @Vserion v0.0.1
 */
@RestController
@RequestMapping("station/")
@Api(value = "BaseStationController", tags = {"基站基础信息操作"})
public class BaseStationController extends BaseController {


    @Resource
    private StationIpPortMapper stationIpPortMapper;
    @Resource
    private TerminalMonitorClient terminalMonitorClient;

    @Resource
    private BaseStationService baseStationService;

    @Resource
    private PartitionService partitionService;

    @Resource
    private LevelSettingService levelSettingService;

    @Resource
    private OfflineStationService offlineStationService;

    @Resource
    private TerminalUpdateIpMapper terminalUpdateIpMapper;

    @Resource
    private TerminalService terminalService;

    @Resource
    private StationMonitorServerClient stationMonitorServerClient;

//    @Resource
//    private StaffOrganizationService staffOrganizationService;

    @GetMapping("findStationByNum")
    @ApiOperation(value = "获取基站信息", notes = "根据Num查找基站")
    public String findStationByNum(@RequestParam("stationId") int stationId) {
        BaseStation station = baseStationService.findBaseStationByNum(stationId);
        return station != null ? ResultUtil.jsonToStringSuccess(station) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @GetMapping("findStationById")
    @ApiOperation(value = "获取基站信息", notes = "根据id查找基站")
    public String findStationById(@RequestParam("stationId") int stationId) {
        BaseStation station = baseStationService.findBaseStationById(stationId);
        return station != null ? ResultUtil.jsonToStringSuccess(station) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("findStationInfoByParams")
    @ApiOperation(value = "获取基站的信息通过多参数查询", notes = "params为多个参数")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.util.Date", name = "startTime", value = "开始时间"),
            @ApiImplicitParam(dataType = "java.util.Date", name = "endTime", value = "结束时间"),
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "zoneId", value = "分站ID"),
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "type", value = "基站类型")
    })
    public String findStationInfoByParams(@RequestParam(required = false) Map<String, Object> params,
                                          @RequestParam(name = "limit", defaultValue = "12", required = false) Integer pageSize,
                                          @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage) {
        //获取zoneId下面的所有子节点的zoneId
        Object t_zoneId = params.get("zoneId");
        Integer zoneId;
        if (null == t_zoneId || "".equals(t_zoneId)){
            zoneId = partitionService.findRootPartition();
        }else {
            zoneId =  Integer.parseInt((String)t_zoneId);
        }
            List<Integer> zoneIds = partitionService.getSonIdsById(zoneId);
            params.put("zoneIds", zoneIds);

        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
            try {
                if (null != startTime && !"".equals(startTime) && !"0".equals(startTime)){
                    params.put("startTime", DateConvert.convertStringToDate(startTime,10));
                }else{
                    params.put("startTime",null);
                }

                if (null != endTime && !"".equals(endTime) && !"0".equals(endTime)){
                    params.put("endTime",DateConvert.convertStringToDate(endTime,10));
                }else{
                    params.put("endTime",null);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        Page page = baseStationService.findBaseStationInfoByParams(startPage, pageSize, params);
        List<Map<String, Object>> result = page.getResult();

        Map<Integer, Integer> standardIdList = new HashMap<>();

        for (Map<String, Object> map : result) {
            //拼接区域管理名称
            standardIdList.put((Integer)map.get("baseStationNum"),(Integer) map.get("standardId"));
        }

        Map<Integer, String> map1 = levelSettingService.getStandardNameByStandardIds(standardIdList);
        if(map1!=null&&map1.size()>0){
            for (Map<String, Object> map : result) {
                map.put("zoneName", partitionService.geParentNamesById((Integer) map.get("zoneId")));

                map.put("standardName", map1.get(map.get("baseStationNum")));
            }
        }


//        if (null != standardId) {
////                Map<String, Object> map1 = levelSettingService.getStandardNameByStandardId(standardId);
//            Map<String, Object> map1 = systemServiceClient.getStandardNameByStandardId(standardId);
//            if (null != map1)
//                map.put("standardName", map1.get("standardName"));
//        }
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ?ResultUtil.jsonToStringSuccess(pageInfo):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @PostMapping("add")
    @ApiOperation(value = "录入基站信息", notes = ".0")
    public String addBaseStation(@RequestBody BaseStation station) {
        station.setBaseStationName("基站"+station.getBaseStationNum());

        station.setType(0);
        station.setCreateTime(new Date());
        Integer baseStationNum = station.getBaseStationNum();
        boolean flag = false;
        int result = 0;
        if (baseStationNum != null) {
            //检测该基站是否更新ip，不存在则插入更新ip表
            boolean exist = terminalUpdateIpMapper.checkStationIdIsNotExist(baseStationNum);
            if(!exist){
                StationIpPort stationIpPort = new StationIpPort();
                stationIpPort.setStationId(baseStationNum);
                stationIpPortMapper.insert(stationIpPort);
            }

            flag = baseStationService.checkStationExists(baseStationNum);
            if (!flag) {
                result = baseStationService.addBaseStation(station);
            } else {
                return ResultUtil.jsonToStringError(ResultEnum.BASE_STATION_NUM_EXIST);
            }
        }
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.ADD_STATION_FAIL);
    }

    @GetMapping("find/{begin},{end}")
    @ApiOperation(value = "查询基站信息", notes = "根据开始和结束时间获取基站的基本信息，如果为空则显示全部")
    public String findStationInfoByTime(@PathVariable(name = "begin") String begin
            , @PathVariable(name = "end") String end
            , @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage
            , @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        PageInfo<BaseStation> pageInfo = null;
        if (!StringUtils.isEmpty(begin) && !StringUtils.isEmpty(end)) {
            pageInfo = baseStationService.findBaseStationInfo(DateConvert.convertStampToDateString(begin, 10), DateConvert.convertStampToDateString(end, 10), startPage, pageSize);
        }
        if (StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)) {
            pageInfo = baseStationService.findAllBaseStationInfo(startPage, pageSize);
        }
        if (StringUtils.isEmpty(begin) && !StringUtils.isEmpty(end)) {
            pageInfo = baseStationService.findBaseStationInfoByEnd(DateConvert.convertStampToDateString(end, 10), startPage, pageSize);
        }
        if (!StringUtils.isEmpty(begin) && StringUtils.isEmpty(end)) {
            pageInfo = baseStationService.findBaseStationInfoByStart(DateConvert.convertStampToDateString(begin, 10), startPage, pageSize);
        }
        return ResultUtil.jsonToStringSuccess(pageInfo);
    }

    @PutMapping("update")
    @ApiOperation(value = "更新基站信息", notes = "通过基站的ID ，更新基站的基本信息")
    public String updateStationInfo(@RequestBody BaseStation station) {
        station.setUpdateTime(new Date());
        ResponseData responseData = new ResponseData();
        RequestData requestData = new RequestData();
        // 封装数据
        requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
        requestData.setStationId(station.getBaseStationNum());
        requestData.setLength(38);
        requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_CONTROL);
        Integer stationId = station.getBaseStationNum();
        Map<String, Object> result = terminalService.selectStationIpByStationId(stationId);
//        Map<String, Object> result = staffGroupTerminalServiceClient.selectStationIpByStationId(stationId);
        if (result == null || result.isEmpty()){
            throw new RuntimeWebException(ErrorCode.FIND_STATION_IP_FAIL);
        }
      /*  if (!result.containsKey("station_ip")){
            throw new RuntimeWebException(ErrorCode.FIND_STATION_IP_FAIL);
        }
        requestData.setStationIp(result.get("station_ip").toString());*/
        if (result.containsKey("station_ip")){
            requestData.setStationIp(result.get("station_ip").toString());
        }

        requestData.setSequenceId(terminalMonitorClient.getSequenceId());
        responseData.setCustomMsg(requestData);
//        BaseStation baseStation = new BaseStation();
        station.setRemark("0");
        station.setBaseStationNum(station.getBaseStationNum());
        baseStationService.updateStationByNumSelective(station);
        try {
//            SingletonStationClient.getSingletonStationClient().sendCmd(responseData);
            stationMonitorServerClient.sendCmd(responseData);

        } catch (Exception e) {
//            if (e instanceof NullPointerException)
            throw new RuntimeWebException(ErrorCode.STATION_OFFLINE_ERROR);
        }

        return baseStationService.updateStationInfo(station) == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.UPDATE_INFO_ERROR);
    }


    @PutMapping("bindingMap")
    @ApiOperation(value = "在地图中绑定基站信息", notes = "通过基站的ID ，XYZ坐标信息绑定位置")
    public String bindingStationInfoByPosition(@RequestParam(name = "baseStationId") Integer baseStationId,
                                               @RequestParam(name = "x") double x,
                                               @RequestParam(name = "y") double y,
                                               @RequestParam(name = "z") double z) {
        BaseStation station = new BaseStation();
        station.setBaseStationId(baseStationId);
        station.setPositionX(x);
        station.setPositionY(y);
        station.setPositionZ(z);
        station.setStatus(1);
        station.setUpdateTime(new Date());
        return baseStationService.updateStationInfo(station) == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.UPDATE_INFO_ERROR);
    }

    @PutMapping("removeBaseStationFromMap")
    @ApiOperation(value = "在地图中移除已经绑定的基站信息", notes = "通过基站的ID ，取消绑定位置，不做实际的基站信息的删除")
    public String bindingStationInfoByBaseStationId(@RequestParam("baseStationId") Integer baseStationId) {
        BaseStation station = new BaseStation();
        station.setBaseStationId(baseStationId);
        station.setPositionX(null);
        station.setPositionY(null);
        station.setPositionZ(null);
        station.setStatus(0);
        station.setUpdateTime(new Date());
        return baseStationService.updateStationInfoOfRemove(station) == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.UPDATE_INFO_ERROR);
    }

    @ApiOperation(value = "删除基站信息", notes = "通过ID删除")
    @DeleteMapping("delete/{id}")
    public String deleteStation(@PathVariable("id") Integer id) {
        return baseStationService.deleteStationById(id) == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.DELETE_STATION_ERROR);
    }

    @Transactional
    @DeleteMapping("delete")
    @ApiOperation(value = "批量删除基站信息", notes = "参数为id的数组，以逗号形式拼接")
    public String deleteStationByIds(@RequestParam("ids") Integer[] ids) {
        int len = ids.length;
        return baseStationService.deleteStationByIds(ids) == len ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.DELETE_STATION_ERROR);
    }

    @GetMapping("findInUsedStation")
    @ApiOperation(value = "获取已被使用的基站信息", notes = "用户地图展示")
    public String getInUsedBaseStation() {
        List<BaseStationPositionVO> list = baseStationService.findBaseStationPositionInfo();
        return list.size() > 0 ? ResultUtil.jsonToStringSuccess(list) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @GetMapping("findNotUsedStation")
    @ApiOperation(value = "获取未被使用的基站信息", notes = "用于用户向地图插入地图展示")
    public String getNotUsedBaseStation() {
        List<BaseStationPositionVO> list = baseStationService.findBaseStationPositionInfoNotUsed();
        return list.size() > 0 ? ResultUtil.jsonToStringSuccess(list) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("findStationByAreaId")
    public String getStationByAreaId(@RequestParam(name = "page", defaultValue = "0", required = false) Integer startPage,
                                     @RequestParam(name = "limit", defaultValue = "12", required = false) Integer pageSize,
                                     @RequestParam(name = "areaId", required = false) Integer areaId) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        PageHelper.startPage(startPage, pageSize);
//        zone_name as zoneName,zone.zone_id as zoneId,base_station.area_id as
//        areaId,area_name as areaName

//        base_station_id as baseStationId, base_station_num as baseStationNum, base_station_ip as baseStationIp,
//        software_version as softwareVersion, hardware_version as hardwareVersion,
//        power_supply as powerSupply, create_time as createTime, remark as remark, update_time updateTime, position_x as
//        positionX, position_y as positionY, position_z as positionZ,
//        channel as channel, emissive_power as emissivePower, antenna_gain as antennaGain, encryption as encryption, SSID
//        as ssid
//        , status as status, subnet_mask as subnetMask, base_station_name as baseStationName
        List<BaseStation> baseStations = baseStationService.findBaseStationByZoneId(areaId);
        List<Map<String, Object>> stationList = new ArrayList<>();
        HashMap<String, Object> map = null;
        if (baseStations != null && baseStations.size() > 0) {
            for (BaseStation item : baseStations) {
                map = new HashMap<>();
                map.put("baseStationNum", item.getBaseStationNum());
                map.put("baseStationIp", item.getBaseStationIp());
                map.put("ssid", item.getSsid());
                map.put("subnetMask", item.getSubnetMask());
                map.put("softwareVersion", item.getSoftwareVersion());
                map.put("hardwareVersion", item.getHardwareVersion());
                map.put("channel", item.getChannel());
                map.put("encryption", item.getEncryption());
                map.put("antennaGain", item.getAntennaGain());
                map.put("emissivePower", item.getEmissivePower());
                StringBuffer sb = new StringBuffer();
                sb.append("(");
                sb.append((Math.round(item.getPositionX()) / 100.0)).append(",");
                sb.append((Math.round(item.getPositionY()) / 100.0));
                sb.append(")");
                System.out.println(sb.toString());
                map.put("positionInfo", sb.toString());
                map.put("powerSupply", item.getPowerSupply());
                list.add(map);
            }
        }
        PageInfo info = new PageInfo<>(list);
        return ResultUtil.jsonToStringSuccess(info);
    }

    @ApiOperation(value = "根据基站的ID绑定气体标准", notes = "基站的ID为数组，标准Id")
    @PostMapping("baseStationBindingStandard")
    public String baseStationBindingStandard(@RequestParam(name = "baseStationIds") Integer[] baseStationIds, @RequestParam("standardId") Integer standardId) {
        BaseStationBindingStandardVO baseStationBindingStandardVO = new BaseStationBindingStandardVO();
        baseStationBindingStandardVO.setBaseStationIds(baseStationIds);
        baseStationBindingStandardVO.setStandardId(standardId);
        boolean result = baseStationService.bindingLevelStandard(baseStationBindingStandardVO);
        return result ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.BINDING_GAS_LEVEL_FAIL);
    }

    @ApiOperation(value = "获取所有的基站的名称和ID")
    @GetMapping("getAllBaseStationNameAndId")
    public String getAllStationInfo(){
        List<Map<String, Object>> mapList = baseStationService.findAllStation();
        return mapList.size() > 0 ? ResultUtil.jsonToStringSuccess(mapList): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @ApiOperation(value = "根基基站的ID 更新基站的TYPE 设置为是否为井口基站")
    @PutMapping("updateStationTypeByStationNum")
    public String updateStationTypeByStationNum(@RequestParam(name = "stationNum")Integer stationNum, @RequestParam(name = "type", required = true) Integer type){
        if (null == stationNum || type == null){
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }
        //检测是否该基站已被设定
        boolean flag = baseStationService.checkBindStatus(stationNum, type);
        if (flag){
            return ResultUtil.jsonToStringError(ResultEnum.STATION_CANT_SETTING_TWO_STATUS);
        }
        int result = baseStationService.updateStationType(stationNum, type);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.BINDING_STATION_MOUTH_FAIL);
    }

    @GetMapping("checkStationNumExist")
    @ApiOperation(value = "检测基站Id是否存在", notes = "检测ID如果存在返回成功200，否在显示提示信息")
    public String checkStationNumExist(@RequestParam("stationNum") Integer stationNum){
        boolean f = baseStationService.checkStationExists(stationNum);
        return f? ResultUtil.jsonToStringError(ResultEnum.STATION_IS_EXISTS): ResultUtil.jsonToStringSuccess();
    }

    @ApiOperation(value = "对基站的上传频率做出配置")
    @PutMapping("settingFrequency")
    public String uploadFrequency(@RequestParam("stationIds") Integer[] stationIds,@RequestParam("frequency") Integer frequency){
        int result = baseStationService.modifyBaseStationFrequency(stationIds,frequency);
        return stationIds.length == result ?ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.SETTING_STATION_UPLOAD_FREQUENCY_FAIL);
    }


    @ApiOperation(value = "分页查询掉线基站")
    @GetMapping("getOfflineStation")
    public String findOfflineStation(@RequestParam(name = "limit", defaultValue = "20", required = false) Integer pageSize,
                                     @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage,
                                     @RequestParam(name = "offlineTimeStart", required = false) String offlineTimeStart,
                                     @RequestParam(name = "offlineTimeEnd", required = false) String offlineTimeEnd,
                                     @RequestParam(name = "stationId", required = false)Integer stationId,
                                     @RequestParam(name = "partitionId", required = false)Integer partitionId){
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", offlineTimeStart);
        params.put("endTime", offlineTimeEnd);
        params.put("stationId", stationId);
        params.put("partitionId", partitionId);

        PageInfo pageInfo = offlineStationService.findOfflineStation(params, startPage, pageSize);
        if (pageInfo.getSize() > 0 ){
            List<Map<String, Object>> mapList = pageInfo.getList();
            for (Map<String ,Object> item: mapList){
                Integer zoneId = (Integer)item.get("zoneId");
                String partitionName = partitionService.geParentNamesById(zoneId);
                item.put("partitionName", partitionName);
            }
            return ResultUtil.jsonToStringSuccess(pageInfo);
        }
            return ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);

    }

    @ApiOperation(value = "获取掉线的基站数量")
    @GetMapping("getOfflineStationCount")
    public String getOfflineStationCount(){
        int count = offlineStationService.getOfflineStationCount();
        return ResultUtil.jsonToStringSuccess(count);
    }

    @ApiOperation("移除掉线的基站")
    @DeleteMapping("removeOfflineStation")
    public String removeOfflineStation(@RequestParam("stationId")Integer stationId){
        int result = offlineStationService.removeOfflineStation(stationId);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }
}
