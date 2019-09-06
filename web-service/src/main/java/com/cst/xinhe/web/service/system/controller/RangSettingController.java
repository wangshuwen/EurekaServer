package com.cst.xinhe.web.service.system.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.constant.ConstantUrl;
import com.cst.xinhe.common.netty.utils.WriteFileUtil;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.warn_level.LevelData;
import com.cst.xinhe.web.service.system.service.RangSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lifeng
 * @Description:
 * @Date 2019/05/06/10:11
 */
@RestController
@RequestMapping("rangSetting/")
@Api(value = "RangSettingController", tags = {"铃声设置表"})
public class RangSettingController {

    @Resource
    private RangSettingService rangSettingService;



    @PostMapping("upload")
    @ApiOperation(value = "上传铃声", notes = "上传铃声")
    public String insert (@RequestParam("file") MultipartFile[] file, /*@RequestParam("type") Integer type,
                          @RequestParam("name") String name,*/ @RequestParam("url") String url/*, @RequestParam("userId") Integer userId*/
    ) {
        if(file!=null) {
            //保存文件
            StringBuffer sb = new StringBuffer(ConstantUrl.rangBasePath);
            //文件夹名
            sb.append(url);
            //文件名
           /* String filename = file[0].getOriginalFilename();
            sb.append(File.separator).append(filename);*/

            File file1 = new File(sb.toString());
            //创建文件
            try {
                byte[] bytes = file[0].getBytes();
                file1.createNewFile();
                WriteFileUtil.writeByteToFile(sb.toString(),bytes,0,bytes.length,false);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //保存到数据库
           /* RangSetting rangSetting = new RangSetting();
            rangSetting.setStatus(0);
            rangSetting.setType(type);
            rangSetting.setName(name);
            rangSetting.setUserId(userId);
            rangSetting.setUrl(url+filename);
            rangSettingService.add(rangSetting);*/
            return ResultUtil.jsonToStringSuccess();
        }


       return  ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


    @GetMapping("findRangByType")
    @ApiOperation(value = "根据铃声类型查找铃声", notes = "获取铃声根据类型")
    public String findRangByType(@RequestParam(required = false) Integer type) {
        List<RangSetting> list= rangSettingService.findRangByType(type);
        return null!=list && !list.isEmpty()?ResultUtil.jsonToStringSuccess(list):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("findRangByLevelDataId")
    @ApiOperation(value = "获取所有等级对应选中的铃声", notes = "获取所有等级对应的铃声")
    public String findRangByLevelDataId(@RequestParam(required = false) Integer levelId) {
        List<LevelData> list= rangSettingService.findRangByLevelDataId(levelId);
        return null != list && !list.isEmpty() ?ResultUtil.jsonToStringSuccess(list): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }




    @PutMapping("updateStatusById")
    @ApiOperation(value = "根据id，设置此类型铃声中此铃声被选中", notes = "修改铃声选中状态")
    public String updateStatusById(@RequestParam Integer[] ids) {
       Integer result=rangSettingService.updateStatusById(ids);
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @PutMapping("restoreDefaultRang")
    @ApiOperation(value = "恢复默认设置（默认选择第一条）", notes = "恢复默认设置")
    public String restoreDefaultRang() {
        Integer result=rangSettingService.restoreDefaultRang();
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


    @PutMapping("updateRangIdByLevelId")
    @ApiOperation(value = "根据气体警报等级id和铃声id，绑定警报选择的铃声", notes = "保存设置")
    public String updateRangIdByLevelId(@RequestParam String jsonStr) {
        Integer result=rangSettingService.updateRangIdByLevelId(jsonStr);
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }





    @PostMapping("addRang")
    @ApiOperation(value = "新增铃声", notes = "新增铃声")
    public String addRang(@RequestBody RangSetting rangSetting) {
        Integer result=rangSettingService.add(rangSetting);
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @DeleteMapping("deleteRang")
    @ApiOperation(value = "删除铃声", notes = "删除铃声")
    public String deleteRang(@RequestParam Integer id) {
        Integer result=rangSettingService.delete(id);
        return result >0 ? ResultUtil.jsonToStringSuccess(result) : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @PutMapping("updateRang")
    @ApiOperation(value = "更新铃声", notes = "更新铃声")
    public String updateRang(@RequestBody RangSetting rangSetting) {
        Integer result=rangSettingService.update(rangSetting);
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

}
