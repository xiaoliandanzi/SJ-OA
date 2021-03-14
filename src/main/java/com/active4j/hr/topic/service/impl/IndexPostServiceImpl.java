package com.active4j.hr.topic.service.impl;

import com.active4j.hr.topic.dao.IndexPostMapper;
import com.active4j.hr.topic.service.IndexPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexPostServiceImpl implements IndexPostService {
    @Resource
    private IndexPostMapper indexPostMapper;

    @Override
    public List<String> getIndexImg() {
        return this.indexPostMapper.getIndexImg();
    }
}
