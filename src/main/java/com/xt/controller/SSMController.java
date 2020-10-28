package com.xt.controller;

import com.xt.entity.Emp;
import com.xt.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SSMController {

    @Autowired
    EmpMapper empDao;

    @RequestMapping("/test")
    public String test(Model model){
        System.out.println("test");
        Emp emp = empDao.selectByEmpno(7369);
        System.out.println(emp);
        model.addAttribute("emp", emp.getEname());
        return "success";
    }
}