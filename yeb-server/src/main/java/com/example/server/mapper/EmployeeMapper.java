package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qxy
 * @since 2022-03-20
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 获取所有员工（分页）
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    //在sql语句执行之前去获取，并改为分页查询的sql语句
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);
}
