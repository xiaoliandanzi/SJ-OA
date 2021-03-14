package com.active4j.hr.car.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/11/22 下午8:29
 */
@Data
public class OaBookCarDomain implements Serializable {
    private static final long serialVersionUID = -4413515129895330593L;

    private String id;

    private String title;

    private String start;

    private String end;
}
