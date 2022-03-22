package com.example.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author qxy
 * @Date 2022/3/22 18:45
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    private Long total;
    private List<?> data;
}
