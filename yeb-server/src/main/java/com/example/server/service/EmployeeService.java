package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Employee;
import com.example.server.pojo.RespPageBean;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 获取所有员工（分页）
     * @param page
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, LocalDate[] beginDateScope);
}
