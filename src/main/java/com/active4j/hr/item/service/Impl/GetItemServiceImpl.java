package com.active4j.hr.item.service.Impl;

import com.active4j.hr.item.dao.GetItemDao;
import com.active4j.hr.item.entity.GetItemEntity;
import com.active4j.hr.item.service.GetItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jinxin
 * @Date: 2020/11/20/16:27
 * @Description:
 */
@Service("getItemServiceImpl")
@Transactional
public class GetItemServiceImpl extends ServiceImpl<GetItemDao, GetItemEntity>
        implements GetItemService {
}
