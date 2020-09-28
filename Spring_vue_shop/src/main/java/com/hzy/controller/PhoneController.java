package com.hzy.controller;

import com.hzy.service.PhoneService;
import com.hzy.utils.ResultVoUtil;
import com.hzy.vo.DataVo;
import com.hzy.vo.PhoneInfoVo;
import com.hzy.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/index")
    public DataVo index(){
        return phoneService.findDataVo();
    }

    @GetMapping("/findByCategroyType/{categoryType}")
    public List<PhoneInfoVo> findByCategroyType(@PathVariable("categoryType") Integer categoryType){
        return phoneService.findPhoneInfoVoBycateGoryType(categoryType);
    }

    @GetMapping("findSpecsByPhoneId/{phoneId}")
    public ResultVo findSpecsByPhoneId(@PathVariable("phoneId") Integer phoneId){
        return ResultVoUtil.success(phoneService.findSpecsByPhoneId(phoneId));
    }

}
