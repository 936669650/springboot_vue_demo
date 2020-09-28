package com.hzy.controller;

import com.hzy.exception.PhoneException;
import com.hzy.service.AddressService;
import com.hzy.utils.ResultVoUtil;
import com.hzy.valida.AddressValida;
import com.hzy.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public ResultVo list(){
        return ResultVoUtil.success(addressService.findAll());
    }

    @PostMapping("/create")
    public ResultVo create(@Valid @RequestBody AddressValida address, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(address);
        return ResultVoUtil.success(null);
    }

    @PutMapping("/update")
    public ResultVo update(@Valid @RequestBody AddressValida address,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(address);
        return ResultVoUtil.success(null);
    }
}
