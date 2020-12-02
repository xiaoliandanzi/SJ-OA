package com.active4j.hr.item.service.Impl;

import com.active4j.hr.item.dao.BorrowedItemDao;
import com.active4j.hr.item.entity.BorrowedItemEntity;
import com.active4j.hr.item.service.BorrowedItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:28
 * @Description:
 */
@Service("borrowedItemService")
@Transactional
public class BorrowedItemServiceImpl extends ServiceImpl<BorrowedItemDao, BorrowedItemEntity>
        implements BorrowedItemService {
}
