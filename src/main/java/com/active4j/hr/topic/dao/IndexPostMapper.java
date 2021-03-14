package com.active4j.hr.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface IndexPostMapper extends BaseMapper {
    List<String> getIndexImg();
}
