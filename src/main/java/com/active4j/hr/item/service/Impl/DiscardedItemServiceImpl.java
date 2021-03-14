package com.active4j.hr.item.service.Impl;

import com.active4j.hr.item.dao.DiscardedItemDao;
import com.active4j.hr.item.entity.DiscardedItemEntity;
import com.active4j.hr.item.service.DiscardedItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xfzhang
 * @version 1.0
 * @date 2020/12/3 上午12:31
 */
@Service("discardedItemService")
@Transactional
public class DiscardedItemServiceImpl extends ServiceImpl<DiscardedItemDao, DiscardedItemEntity>
        implements DiscardedItemService {
}
