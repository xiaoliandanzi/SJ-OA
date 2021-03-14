package com.active4j.hr.system.service;

import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysFunctionEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.model.SysUserModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUserEntity> {

    /**
     * @return SysDeptEntity
     * @description 根据用户ID获取用户所在部门 注意是部门
     * @author xfzhang
     * @time 2020年4月8日 下午9:03:57
     */
    public SysDeptEntity getUserDepart(String userId);

    /**
     * @return List<String>
     * @description 获取当前用户的下属用户ID
     * @author xfzhang
     * @time 2020年4月3日 下午4:22:41
     */
    public List<String> getUnderUserIds(String userId);

    /**
     * 根据用户名取得用户
     *
     * @param userName
     * @return
     */
    public SysUserEntity getUserByUseName(String userName);


    /**
     * 根据真实姓名取得用户
     *
     * @param userName
     * @return
     */
    public SysUserEntity getUserByRealName(String userName);

    /**
     * @return List<SysUserEntity>
     * @description 根据部门获取用户
     * @author xfzhang
     * @time 2020年4月7日 上午10:52:54
     */
    public List<SysUserEntity> findUsersByDept(SysDeptEntity sysDeptEntity);


    /**
     * 根据用户信息  组装shiro session用户
     *
     * @param user
     * @return
     */
    public ActiveUser getActiveUserByUser(SysUserEntity user);

    /**
     * @return List<SysMenuEntity>
     * @description 根据用户ID 获取用户所有菜单
     * @params userId 用户ID
     * @author guyp
     * @time 2020年1月3日 下午1:18:08
     */
    public List<SysFunctionEntity> findMenuByUserId(String userId);


    /**
     * @return void
     * @description 用户的保存
     * @author xfzhang
     * @time 2020年1月28日 下午10:19:49
     */
    public void saveUser(SysUserEntity user, String[] roleIds);

    /**
     * @return List<SysRoleEntity>
     * @description 根据用户ID 获取用户所属角色集合
     * @params userId  用户ID
     * @author xfzhang
     * @time 2020年1月28日 下午11:40:01
     */
    public List<SysRoleEntity> getUserRoleByUserId(String userId);

    /**
     * @return void
     * @description 编辑用户
     * @author xfzhang
     * @time 2020年1月29日 上午12:26:43
     */
    public void saveOrUpdateUser(SysUserEntity user, String[] roleIds);

    /**
     * @return SysUserModel
     * @description 根据id查询用户个人资料
     * @params
     * @author guyp
     * @time 2020年2月8日 下午12:36:47
     */
    public List<SysUserModel> getInfoByUserId(String userId);

    /**
     * @return void
     * @description 删除关联的用户信息，然后删除用户
     * @params
     * @author guyp
     * @time 2020年2月8日 下午4:25:56
     */
    public void delete(String userId);

    /**
     * 获取真实姓名
     *
     * @param id
     * @return
     */
    String findNameById(String id);
}
