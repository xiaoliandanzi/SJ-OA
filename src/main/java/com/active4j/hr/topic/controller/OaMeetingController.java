package com.active4j.hr.topic.controller;


import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.util.StringUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.entity.SysDeptEntity;
import com.active4j.hr.system.entity.SysRoleEntity;
import com.active4j.hr.system.entity.SysUserEntity;
import com.active4j.hr.system.entity.SysUserRoleEntity;
import com.active4j.hr.system.model.ActiveUser;
import com.active4j.hr.system.service.SysDeptService;
import com.active4j.hr.system.service.SysRoleService;
import com.active4j.hr.system.service.SysUserRoleService;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaMeetingService;
import com.active4j.hr.topic.service.OaTopicService;
import com.active4j.hr.topic.until.DeptLeaderRole;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weizihao
 * @since 2021-01-04
 */
@RestController
@RequestMapping("/meeting")
public class OaMeetingController {

    @Autowired
    private OaMeetingService meetingService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private OaTopicService topicService;

    @Autowired
    private OaWorkMeetRoomService oaWorkMeetRoomService;
    @Autowired
    private SysDeptService deptService;
    /**
     * list视图
     *
     * @return
     */
    @RequestMapping(value = "list")
    public ModelAndView meetingList() {
        return new ModelAndView("topic/meeting");
    }


    /**
     * 表格数据
     *
     * @param oaMeeting
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "table")
    public void topicTable(OaMeeting oaMeeting, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        System.err.println(user);
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(oaMeeting);
        IPage<OaMeeting> page = meetingService.page(new Page<OaMeeting>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }

    /**
     *  根据参会人员选择数据
     *
     * @param
     * @param req
     * @return
     */
    @RequestMapping("/groupBycanhui")
    @ResponseBody
    public AjaxJson groupBycanhui(HttpServletRequest req, OaMeeting  oaMeeting) {
        //创建人
        SysUserEntity userEntity = getUser();
        AjaxJson json = new AjaxJson();
        try {
            if ("1".equals(oaMeeting.getCanHuitype())) {
                //提议领导 查询主要领导
                json.setObj( userList("", "1e3124100e45ed3e9ec99bf3e35be2c0"));
            } else if ("2".equals(oaMeeting.getCanHuitype())) {
                //主管领导
                DeptLeaderRole deptLeaderRole = new DeptLeaderRole();
                String leaderRole = deptLeaderRole.getRoleForDept().get(userEntity.getDeptId());
                SysRoleEntity roleEntity = roleService.getById(leaderRole);
                json.setObj(userList("", roleEntity.getParentId()));
            } else if ("3".equals(oaMeeting.getCanHuitype())) {
                //科室负责人
                DeptLeaderRole deptLeaderRole = new DeptLeaderRole();
                String leaderRole = deptLeaderRole.getRoleForDept().get(userEntity.getDeptId());
                json.setObj(userList("", leaderRole));
            }
        }catch(Exception e) {
            json.setSuccess(false);
            json.setMsg("保存用户错误");
            e.printStackTrace();
        }
         return   json;
    }
    /**
     * 增改视图
     * meeting/saveOrUpdateView
     *
     * @param oaMeeting
     * @return
     */
    @RequestMapping(value = "saveOrUpdateView")
    public ModelAndView saveOrUpdateOa(OaMeeting oaMeeting) {
        //创建人
        SysUserEntity userEntity = getUser();
        String ids=oaMeeting.getIds();
        ModelAndView modelAndView = new ModelAndView("topic/meetingform");
        if (StringUtil.isEmpty(oaMeeting.getId())){
            oaMeeting = new OaMeeting();
            //oaTopic.setId(UUID.randomUUID().toString());
        } else {
            oaMeeting = meetingService.getById(oaMeeting.getId());
        }

        oaMeeting.setIds(ids);
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DEPT_ID", userEntity.getDeptId());
        List<SysUserEntity> users = userService.list(queryWrapper);
        modelAndView.addObject("oaMeeting", oaMeeting);
        //
        SysDeptEntity dept = deptService.getById(userEntity.getDeptId());
        modelAndView.addObject("deptName", dept.getName());
        //所属角色
        String roleId = "";
        String roleName = "";
        List<SysRoleEntity> roles = sysUserService.getUserRoleByUserId(oaMeeting.getId());
        if(roles.size() > 0) {
            for (SysRoleEntity r : roles) {
                roleId += r.getId() + ",";
                roleName += r.getRoleName() + ",";
            }
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                        getRequestAttributes()).getRequest();
        HttpSession session=request.getSession();//创建session对象
        session.setAttribute("ids", ids);
        session.setAttribute("ids1", ids);
        String[] strArray = ids.split(",");
        modelAndView.addObject("roleId", roleId);
        modelAndView.addObject("roleName", roleName);
        //议题列表的数据
        QueryWrapper<OaTopic> queryWrappers = new QueryWrapper<>();
        queryWrappers.notIn("id",strArray);
        List<OaTopic> oalist=topicService.list(queryWrappers);
        modelAndView.addObject("oalist", oalist);
            //提议领导 查询主要领导
            modelAndView.addObject("dataList", userList("", "1e3124100e45ed3e9ec99bf3e35be2c0"));
        List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
        modelAndView.addObject("roomList", lstRooms);
        return modelAndView;
    }
    /**
     * 查看会议数据
     */
    @RequestMapping(value = "lookView")
    public ModelAndView lookView(OaMeeting oaMeeting) {
        String id=oaMeeting.getId();
        OaMeeting   oameeting=meetingService.getById(id);
        ModelAndView modelAndView = new ModelAndView("topic/meetinglookform");
        modelAndView.addObject("depname", oameeting.getDeptName());
        modelAndView.addObject("registrantName", oameeting.getRegistrantName());
        modelAndView.addObject("meetingTime", oameeting.getMeetingTime());
        modelAndView.addObject("meetingId", oameeting.getMeetingId());
        modelAndView.addObject("meetingendTime", oameeting.getMeetingendTime());
        modelAndView.addObject("oaMeeting", oameeting);
        if("1".equals(oameeting.getMeetingType())){
            modelAndView.addObject("meetingType","书记会");
        }
        else   if("2".equals(oameeting.getMeetingType())){
            modelAndView.addObject("meetingType","主任会");
        }
        else  if("3".equals(oameeting.getMeetingType())){
            modelAndView.addObject("meetingType","工委会");
        }
        modelAndView.addObject("meetingName", oameeting.getMeetingName());
        modelAndView.addObject("memo", oameeting.getMemo());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest();
        HttpSession session=request.getSession();//创建session对象
        String  ids=oameeting.getIssueId();
        session.setAttribute("meid", ids);
        return modelAndView;
    }
    /**
     * 修改会议数据
     */
    @RequestMapping(value = "editView")
    public ModelAndView editView(OaMeeting oaMeeting) {
        String id=oaMeeting.getId();
        //创建人
        SysUserEntity userEntity = getUser();
        OaMeeting   oameeting=meetingService.getById(id);
        ModelAndView modelAndView = new ModelAndView("topic/meetingformedit");
        modelAndView.addObject("id", oameeting.getId());
        modelAndView.addObject("depname", oameeting.getDeptName());
        modelAndView.addObject("registrantName", oameeting.getRegistrantName());
        modelAndView.addObject("meetingTime", oameeting.getMeetingTime());
        modelAndView.addObject("meetingendTime", oameeting.getMeetingendTime());
        modelAndView.addObject("meetingId", oameeting.getMeetingId());
        modelAndView.addObject("oaMeeting", oameeting);
        modelAndView.addObject("conferee", oameeting.getConferee());
        List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
        modelAndView.addObject("roomList", lstRooms);
        if("1".equals(oameeting.getMeetingType())){
            modelAndView.addObject("meetingType","书记会");
        }
        else   if("2".equals(oameeting.getMeetingType())){
            modelAndView.addObject("meetingType","主任会");
        }
        else  if("3".equals(oameeting.getMeetingType())){
            modelAndView.addObject("meetingType","工委会");
        }
        modelAndView.addObject("meetingName", oameeting.getMeetingName());
        modelAndView.addObject("canhuipeo", oameeting.getCanhuipeo());
        modelAndView.addObject("memo", oameeting.getMemo());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest();
        HttpSession session=request.getSession();//创建session对象
        String  ids=oameeting.getIssueId();
    /*    if("1".equals(oameeting.getCanhuipeo())){
            modelAndView.addObject("canhuipeo","主要领导");
        }else
        if("2".equals(oameeting.getCanhuipeo())){
            modelAndView.addObject("canhuipeo","主管领导");
        }else
        if("3".equals(oameeting.getCanhuipeo())){
            modelAndView.addObject("canhuipeo","科室负责人");
        }else
        if("4".equals(oameeting.getCanhuipeo())){
            modelAndView.addObject("canhuipeo","科员");
        }*/
        session.setAttribute("bianid", ids);
        session.setAttribute("bianid1", ids);
        String  strArray[]=ids.split(",");
        //议题列表的数据
        QueryWrapper<OaTopic> queryWrappers = new QueryWrapper<>();
        queryWrappers.notIn("id",strArray);
        List<OaTopic> oalist=topicService.list(queryWrappers);
        modelAndView.addObject("oalist", oalist);
        //提议领导 查询主要领导
        DeptLeaderRole deptLeaderRole = new DeptLeaderRole();
        String leaderRole = deptLeaderRole.getRoleForDept().get(userEntity.getDeptId());
        SysRoleEntity roleEntity = roleService.getById(leaderRole);
        modelAndView.addObject("dataList",userListS(userEntity.getDeptId()));
        return modelAndView;
    }
    /**
     * roleID 换 users
     *
     * @return
     */
    private List<SysUserEntity> userList(String deptId, String roleId) {
        QueryWrapper<SysUserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
        userRoleEntityQueryWrapper.eq("ROLE_ID", roleId);
        List<SysUserRoleEntity> URList = userRoleService.list(userRoleEntityQueryWrapper);
        List<SysUserEntity> users = new ArrayList<>();
        if (URList.size() > 0)
            URList.forEach(URDao -> {
                SysUserEntity user = userService.getById(URDao.getUserId());
                users.add(user);
            });
        return users;
    }
    /**
     * roleID 换 users
     *
     * @return
     */
    private List<SysUserEntity> userListS(String deptId) {
        QueryWrapper<SysUserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        List<SysUserEntity> URList = userService.list(userEntityQueryWrapper);
        return URList;
    }

    /**
     * 重新操作session
     *
     * @return
     */
    @RequestMapping(value = "setTopid")
    private void setTopid(OaTopic oaTopic,HttpServletRequest request, HttpServletResponse response) {
        String iddata=oaTopic.getIds();
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("ids");
        String ids1 =  (String)session.getAttribute("ids1");
        String trues =  (String)session.getAttribute("true");
        if(trues==null){
            String  data ="";
            if(iddata==null||iddata==""){
                data=ids1;
            }else{
                data=iddata+","+ids1;
            }
            session.setAttribute("ids",data);
        }else{
            String  data ="";
            if(iddata==null||iddata==""){
                data=ids;
            }else{
                data=iddata+","+ids;
            }
            session.setAttribute("ids",data);
        }

    }
    /**
     * 页面删除
     *
     * @return
     */
    @RequestMapping(value = "tablesdel")
    private AjaxJson tablesdel(OaTopic oaTopic,HttpServletRequest request, HttpServletResponse response) {
        String id=oaTopic.getId();
        AjaxJson j = new AjaxJson();
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("ids");
        List<String> list = Arrays.asList(ids.split(","));
        List<String> newlist = new ArrayList<>();
       try{
           for(int i=0; i<list.size(); i++){
               if(!(id).equals(list.get(i))){
                   newlist.add(list.get(i));
               }
           }
           System.out.println(newlist.toString());
           String   idza="";
           for(int i=0; i<newlist.size(); i++){
               if(i==0){
                   idza=newlist.get(i);
               }else{
                   idza=idza+","+newlist.get(i);
               }
           }
           System.out.println(idza);
           session.setAttribute("ids",idza);
           j.setSuccess(true);
           session.setAttribute("true","OK");
           j.setMsg("删除成功");
           return j;
       }catch (Exception e){
           j.setSuccess(false);
           j.setMsg("删除用户错误");
           e.printStackTrace();
           return j;
       }
    }
    /**
     * 编辑页面删除
     *
     * @return
     */
    @RequestMapping(value = "bjtablesdel")
    private AjaxJson bjtablesdel(OaTopic oaTopic,HttpServletRequest request, HttpServletResponse response) {
        String id=oaTopic.getId();
        AjaxJson j = new AjaxJson();
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("bianid");
        List<String> list = Arrays.asList(ids.split(","));
        List<String> newlist = new ArrayList<>();
        try{
            for(int i=0; i<list.size(); i++){
                if(!(id).equals(list.get(i))){
                    newlist.add(list.get(i));
                }
            }
            System.out.println(newlist.toString());
            String   idza="";
            for(int i=0; i<newlist.size(); i++){
                if(i==0){
                    idza=newlist.get(i);
                }else{
                    idza=idza+","+newlist.get(i);
                }
            }
            System.out.println(idza);
            session.setAttribute("bianid",idza);
            j.setSuccess(true);
            session.setAttribute("trues","OK");
            j.setMsg("删除成功");
            return j;
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg("删除用户错误");
            e.printStackTrace();
            return j;
        }
    }
    /**
     * 重新操作session
     *
     * @return
     */
    @RequestMapping(value = "seteditopid")
    private void seteditopid(OaTopic oaTopic,HttpServletRequest request, HttpServletResponse response) {
        String iddata=oaTopic.getIds();
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("bianid");
        String ids1 =  (String)session.getAttribute("bianid1");
        String trues =  (String)session.getAttribute("trues");
        if(trues==null){
            String  data ="";
            if(iddata==null||iddata==""){
                data=ids1;
            }else{
                data=iddata+","+ids1;
            }
            session.setAttribute("bianid",data);
        }else{
            String  data ="";
            if(iddata==null||iddata==""){
                data=ids;
            }else{
                data=iddata+","+ids;
            }
            session.setAttribute("bianid",data);
        }
    }
    /**
     * 表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "tables")
    public void topicTable(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        if ("".equals(oaTopic.getTopicName())) {
            oaTopic.setTopicName(null);
        }
        HttpSession session = request.getSession();

        String ids =  (String)session.getAttribute("ids");
        String[] strArray = ids.split(",");
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",strArray);
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }
    /**
     * 查看表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "tablechakans")
    public void tablechakans(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        if ("".equals(oaTopic.getTopicName())) {
            oaTopic.setTopicName(null);
        }
        HttpSession session = request.getSession();

        String ids =  (String)session.getAttribute("meid");
        String[] strArray = ids.split(",");
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",strArray);
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }
    /**
     * 编辑表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "tablebianji")
    public void tablebianji(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        if ("".equals(oaTopic.getTopicName())) {
            oaTopic.setTopicName(null);
        }
        HttpSession session = request.getSession();

        String ids =  (String)session.getAttribute("bianid");
        String[] strArray = ids.split(",");
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",strArray);
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
    }
    /**
     * 得到用户
     *
     * @return
     */
    private SysUserEntity getUser() {
        ActiveUser activeUser = ShiroUtils.getSessionUser();
        return userService.getById(activeUser.getId());
    }
    /**
     * 追溯上级
     *
     * @param userEntity
     * @return
     */
    private List<SysUserEntity> leaderList(SysUserEntity userEntity) {
        return null;
    }


    /**
     * 新增议题会议
     *
     * @param oaMeeting
     * @return meeting/save
     */
    @RequestMapping(value = "save")
    public AjaxJson save(OaMeeting oaMeeting,HttpServletRequest request) {
        System.err.println(oaMeeting);
        AjaxJson ajaxJson = new AjaxJson();
        oaMeeting.setCreateTime(new Date());
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("ids");
        oaMeeting.setIssueId(ids.toString());
        oaMeeting.setStateId("未开始");
        try {
            meetingService.saveOrUpdate(oaMeeting);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增议题会议失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }
    /**
     * 编辑议题会议
     *
     * @param oaMeeting
     * @return meeting/edit
     */
    @RequestMapping(value = "edit")
    public AjaxJson edit(OaMeeting oaMeeting,HttpServletRequest request) {
        System.err.println(oaMeeting);
        AjaxJson ajaxJson = new AjaxJson();
        oaMeeting.setModifyTime(new Date());
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("bianid");
        oaMeeting.setIssueId(ids.toString());
        try {
            meetingService.saveOrUpdate(oaMeeting);
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改议题会议失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }
}

