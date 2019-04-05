package com.ccsu.server.entity.vo;

import lombok.Data;

/**
 * @author hangs.zhang
 * @date 2018/8/27
 * *********************
 * function:
 */
@Data
public class BaseRes<T> {

    private Integer code;

    private String message;

    private T data;

}
