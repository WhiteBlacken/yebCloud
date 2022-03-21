package com.example.server.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 * @Author qxy
 * @Date 2022/3/20 21:54
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "统一返回对象",description = "")
public class RespBean {
    @ApiModelProperty(value = "状态码")
    private long code;
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private Object obj;

    public static RespBean success(String message) {
        return new RespBean(200, message, null);
    }

    public static RespBean success(String message, Object obj) {
        return new RespBean(200, message, obj);
    }

    public static RespBean error(String message) {
        return new RespBean(500, message, null);
    }

    public static RespBean error(String message, Object obj) {
        return new RespBean(500, message, obj);
    }
}