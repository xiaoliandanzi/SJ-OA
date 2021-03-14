package com.active4j.hr.officalSeal.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/24/23:16
 * @Description:
 */
@Data
public class OaBookSealDomain implements Serializable {


    private static final long serialVersionUID = 8978434220646870255L;

    private String id;

    private String title;

    private String start;

    private String end;
}
