package com.example.server.controller;


import com.example.server.pojo.Employee;
import com.example.server.pojo.RespPageBean;
import com.example.server.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "4") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(page,size,employee,beginDateScope);
    }
}
