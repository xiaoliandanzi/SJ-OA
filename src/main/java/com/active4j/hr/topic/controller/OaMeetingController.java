package com.active4j.hr.topic.controller;


import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
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
import com.active4j.hr.topic.entity.OaNotificationform;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaMeetingService;
import com.active4j.hr.topic.service.OaNotificationformService;
import com.active4j.hr.topic.service.OaTopicService;
import com.active4j.hr.topic.until.DeptLeaderRole;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private OaNotificationformService notificationformService;

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
     * list视图
     *
     * @return
     */
    @RequestMapping(value = "mylist")
    public ModelAndView mylist() {

        return new ModelAndView("topic/mymeeting");
    }

    /**
     * 我的会议
     *
     * @param
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "mytablelist")
    public void mytablelist(OaMeeting oaMeeting, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        ActiveUser user = ShiroUtils.getSessionUser();
        String id = user.getId();
        QueryWrapper<OaNotificationform> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nameid", id);
        List<OaNotificationform> list = notificationformService.list(queryWrapper);
        List<String> huiyilist = new ArrayList<>();
        for (OaNotificationform oano : list) {
            huiyilist.add(oano.getHuiyiid());
        }
        String idza = "";
        for (int i = 0; i < huiyilist.size(); i++) {
            if (i == 0) {
                idza = huiyilist.get(i);
            } else {
                idza = idza + "," + huiyilist.get(i);
            }
        }
        String str[] = idza.split(",");
        QueryWrapper<OaMeeting> queryWrappers = new QueryWrapper<>();
        queryWrappers.in("ID", str);
        if ("".equals(oaMeeting.getMeetingName())) {
            oaMeeting.setMeetingName(null);
        }
        if ("".equals(oaMeeting.getMeetingTime())) {
            oaMeeting.setMeetingTime(null);
        }
        if ("".equals(oaMeeting.getMeetingType())) {
            oaMeeting.setMeetingType(null);
        }
        //名称模糊查询
        if (!StringUtil.isEmpty(oaMeeting.getMeetingName())) {
            queryWrappers.like("Meeting_Name", oaMeeting.getMeetingName());
            oaMeeting.setMeetingName(null);
        }
        //日期查询
        if (!StringUtil.isEmpty(oaMeeting.getMeetingTime())) {
            queryWrappers.eq("Meeting_Time", oaMeeting.getMeetingTime());
            oaMeeting.setMeetingTime(null);
        }
        //会议查询
        if (!StringUtil.isEmpty(oaMeeting.getMeetingType())&&"书记会".equals(oaMeeting.getMeetingType())) {
            queryWrappers.eq("Meeting_type", "1");
            oaMeeting.setMeetingTime(null);
        }
        //会议查询
        if (!StringUtil.isEmpty(oaMeeting.getMeetingType())&&"主任会".equals(oaMeeting.getMeetingType())) {
            queryWrappers.eq("Meeting_type", "2");
            oaMeeting.setMeetingTime(null);
        }
        //会议查询
        if (!StringUtil.isEmpty(oaMeeting.getMeetingType())&&"工委会".equals(oaMeeting.getMeetingType())) {
            queryWrappers.eq("Meeting_type", "3");
            oaMeeting.setMeetingTime(null);
        }
        IPage<OaMeeting> page = meetingService.page(new Page<OaMeeting>(dataGrid.getPage(), dataGrid.getRows()), queryWrappers);
        ResponseUtil.writeJson(response, dataGrid, page);
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
        queryWrapper.orderByDesc("Meeting_Time");
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
                QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ROLE_NAME","主要领导");
                SysRoleEntity  role =roleService.list(queryWrapper).get(0);
                json.setObj( userList("", role.getId()));
            } else if ("2".equals(oaMeeting.getCanHuitype())) {
                QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.like("ROLE_NAME","主管领导");
                List<SysRoleEntity> list= roleService.list(queryWrapper);
                List<SysUserEntity> users = new ArrayList<>();
                for(SysRoleEntity sys: list){
                    QueryWrapper<SysUserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
                    userRoleEntityQueryWrapper.eq("ROLE_ID", sys.getId());
                    List<SysUserRoleEntity> URList = userRoleService.list(userRoleEntityQueryWrapper);
                    if (URList.size() > 0)
                        URList.forEach(URDao -> {
                            SysUserEntity user = userService.getById(URDao.getUserId());
                            users.add(user);
                        });
                }
                json.setObj(users);
            } else if ("3".equals(oaMeeting.getCanHuitype())) {
                QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.like("ROLE_NAME","主管领导");
                List<SysRoleEntity> list= roleService.list(queryWrapper);
                List<SysRoleEntity> listks=new ArrayList<>();
                List<SysUserEntity> users = new ArrayList<>();
                for(SysRoleEntity sys: list){
                    QueryWrapper<SysRoleEntity> queryWrapperks = new QueryWrapper<>();
                    queryWrapperks.like("PARENT_ID",sys.getId());
                    List<SysRoleEntity> listkss= roleService.list(queryWrapperks);
                    for (SysRoleEntity l:listkss){
                        listks.add(l);
                    }
                }
                for(SysRoleEntity sys: listks){
                    QueryWrapper<SysUserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
                    userRoleEntityQueryWrapper.eq("ROLE_ID", sys.getId());
                    List<SysUserRoleEntity> URList = userRoleService.list(userRoleEntityQueryWrapper);
                    if (URList.size() > 0)
                        URList.forEach(URDao -> {
                            SysUserEntity user = userService.getById(URDao.getUserId());
                            users.add(user);
                        });
                }
                json.setObj(users);
            }
            else if ("4".equals(oaMeeting.getCanHuitype())) {
                QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.like("ROLE_NAME","主管领导");
                List<SysRoleEntity> list= roleService.list(queryWrapper);
                List<SysRoleEntity> listks=new ArrayList<>();
                List<SysRoleEntity> listky=new ArrayList<>();
                List<SysUserEntity> users = new ArrayList<>();
                for(SysRoleEntity sys: list){
                    QueryWrapper<SysRoleEntity> queryWrapperks = new QueryWrapper<>();
                    queryWrapperks.like("PARENT_ID",sys.getId());
                    List<SysRoleEntity> listkss= roleService.list(queryWrapperks);
                    for (SysRoleEntity l:listkss){
                        listks.add(l);
                    }
                }
                for(SysRoleEntity sys: listks){
                    QueryWrapper<SysRoleEntity> queryWrapperky = new QueryWrapper<>();
                    queryWrapperky.like("PARENT_ID",sys.getId());
                    List<SysRoleEntity> listkyy= roleService.list(queryWrapperky);
                    for (SysRoleEntity l:listkyy){
                        listky.add(l);
                    }
                }
                for(SysRoleEntity sys: listky){
                    QueryWrapper<SysUserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
                    userRoleEntityQueryWrapper.eq("ROLE_ID", sys.getId());
                    List<SysUserRoleEntity> URList = userRoleService.list(userRoleEntityQueryWrapper);
                    if (URList.size() > 0)
                        URList.forEach(URDao -> {
                            SysUserEntity user = userService.getById(URDao.getUserId());
                            users.add(user);
                        });
                }
                json.setObj(users);
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
        modelAndView.addObject("depid", userEntity.getDeptId());
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
        QueryWrapper<SysRoleEntity> queryWrapperA = new QueryWrapper<>();
        queryWrapperA.eq("ROLE_NAME","主要领导");
        SysRoleEntity  role =roleService.list(queryWrapperA).get(0);
        modelAndView.addObject("dataList", userList("", role.getId()));
        List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
        modelAndView.addObject("roomList", lstRooms);
        return modelAndView;
    }

    /**
     * 我的会议
     * meeting/saveOrUpdateView
     *
     * @param oaMeeting
     * @return
     */
    @RequestMapping(value = "myyitihuiyiview")
    public ModelAndView myyitihuiyiview(OaMeeting oaMeeting) {
       ModelAndView modelAndView = new ModelAndView("topic/huiyitimeeting");
        if (StringUtil.isEmpty(oaMeeting.getId())){
            oaMeeting = new OaMeeting();
        } else {
            oaMeeting = meetingService.getById(oaMeeting.getId());
        }
        modelAndView.addObject("oaMeeting",oaMeeting);
        if("1".equals(oaMeeting.getMeetingType())){
            modelAndView.addObject("meetingType","书记会");
        }
        else   if("2".equals(oaMeeting.getMeetingType())){
            modelAndView.addObject("meetingType","主任会");
        }
        else  if("3".equals(oaMeeting.getMeetingType())){
            modelAndView.addObject("meetingType","工委会");
        }
        //会议室
        List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
        modelAndView.addObject("roomList", lstRooms);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                getRequestAttributes()).getRequest();
        HttpSession session=request.getSession();//创建session对象
        String  ids=oaMeeting.getIssueId();
        session.setAttribute("chakanhuiyiid", ids);
        return modelAndView;
    }
    /**
     * 表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "tablechakanyiti")
    public void tablechakanyiti(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        HttpSession session=request.getSession();//创建session对象
        String ids= (String) session.getAttribute("chakanhuiyiid");
        queryWrapper.in("ID",ids);
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
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
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("ids");
        String[] strArray = ids.split(",");
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",strArray);
        queryWrapper.eq("STATE_ID",4);
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
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("meid");
        String[] strArray = ids.split(",");
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",strArray);
        queryWrapper.eq("STATE_ID",4);
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
    public AjaxJson save(OaMeeting oaMeeting,HttpServletRequest request) throws ParseException {
        System.err.println(oaMeeting);
        AjaxJson ajaxJson = new AjaxJson();
        try {
        oaMeeting.setCreateTime(new Date());
        HttpSession session = request.getSession();
        String ids =  (String)session.getAttribute("ids");
        oaMeeting.setIssueId(ids.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  date =format.parse(oaMeeting.getMeetingTime());
        Date  date1 =format.parse(oaMeeting.getMeetingendTime());
        int compareTo =date.compareTo(new Date());
        int compareTo2 =date1.compareTo(new Date());
        if(compareTo==-1){
            oaMeeting.setStateId("未开始");
        }else if(compareTo==1){
            oaMeeting.setStateId("进行中");
        }
        if (compareTo2==-1){
            oaMeeting.setStateId("已结束");
            String AA=oaMeeting.getIssueId();
            String  strs[] =AA.split(",");
            //议题列表的数据
            QueryWrapper<OaTopic> queryWrappers = new QueryWrapper<>();
            queryWrappers.in("id",strs);
            List<OaTopic> oalist=topicService.list(queryWrappers);
            for(OaTopic oa:oalist){
                oa.setIsHistory(1);
                topicService.saveOrUpdate(oa);
            }
        }
        String  condata=oaMeeting.getConferee();
        List<String> conlist= Arrays.asList(condata.split(","));
        List<String>  coniddata=new ArrayList<>();
        for (int i=0; i<conlist.size();i++){
            QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("REAL_NAME",conlist.get(i));
            SysUserEntity user = userService.list(queryWrapper).get(0);
            coniddata.add(user.getId());
        }
            String   idza="";
            for(int i=0; i<coniddata.size(); i++){
                if(i==0){
                    idza=coniddata.get(i);
                }else{
                    idza=idza+","+coniddata.get(i);
                }
            }
            oaMeeting.setConfereeid(idza);
            meetingService.saveOrUpdate(oaMeeting);
            for(int i=0; i<coniddata.size(); i++){
                OaNotificationform oano =new OaNotificationform();
                SysUserEntity USER=userService.getById(coniddata.get(i));
                SysDeptEntity dept=deptService.getById(USER.getDeptId());
                oano.setDepid(USER.getDeptId());
                oano.setDepname(dept.getName());
                oano.setStatus("0");
                oano.setNameid(coniddata.get(i));
                oano.setName(USER.getRealName());
                oano.setHuiyiid(oaMeeting.getId());
                oano.setCreateTime(new Date());
                oano.setHuiyihome(oaMeeting.getMeetingId());
                oano.setHuiyidate(oaMeeting.getMeetingTime());
                notificationformService.saveOrUpdate(oano);

            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增议题会议失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 表格数据
     *
     * @param oaTopic
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "tableAll")
    public void tableAll(OaTopic oaTopic, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        if ("".equals(oaTopic.getTopicName())) {
            oaTopic.setTopicName(null);
        }
        QueryWrapper<OaTopic> queryWrapper = new QueryWrapper<>();
        oaTopic.setStateId(4);
        queryWrapper.setEntity(oaTopic);
        //名称模糊查询
        if (!StringUtil.isEmpty(oaTopic.getTopicName())) {
            queryWrapper.like("TOPIC_NAME", oaTopic.getTopicName());
            oaTopic.setTopicName(null);
        }
        //处理时间查询
        Map<String, String[]> paramsMap = request.getParameterMap();
        if (null != paramsMap) {
            String[] beginValue = paramsMap.get("creatTime_begin");
            if (null != beginValue && beginValue.length > 0) {
                if (StringUtils.isNotEmpty(beginValue[0].trim())) {
                    queryWrapper.ge("CREAT_TIME", beginValue[0].trim());
                }
            }
            String[] endValue = paramsMap.get("creatTime_end");
            if (null != endValue && endValue.length > 0) {
                if (StringUtils.isNotEmpty(endValue[0].trim())) {
                    queryWrapper.le("CREAT_TIME", endValue[0].trim());
                }
            }
        }
        if ("".equals(oaTopic.getOpinion())) {
            oaTopic.setOpinion(null);
        }
        if ("工委会".equals(oaTopic.getOpinion())) {
            queryWrapper.eq("IS_WORKING_COMMITTEE","true");
        }
        if ("主任会".equals(oaTopic.getOpinion())) {
            queryWrapper.eq("IS_DIRECTOR","true");
        }
        queryWrapper.orderByDesc("CREAT_TIME");
        IPage<OaTopic> page = topicService.page(new Page<OaTopic>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);
        ResponseUtil.writeJson(response, dataGrid, page);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date  date =format.parse(oaMeeting.getMeetingTime());
            Date  date1 =format.parse(oaMeeting.getMeetingendTime());
            int compareTo =date.compareTo(new Date());
            int compareTo2 =date1.compareTo(new Date());
            if(compareTo==-1){
                oaMeeting.setStateId("未开始");
            }else if(compareTo==1){
                oaMeeting.setStateId("进行中");
            }
            if (compareTo2==-1){
                oaMeeting.setStateId("已结束");
                String AA=oaMeeting.getIssueId();
                String  strs[] =AA.split(",");
                //议题列表的数据
                QueryWrapper<OaTopic> queryWrappers = new QueryWrapper<>();
                queryWrappers.in("id",strs);
                List<OaTopic> oalist=topicService.list(queryWrappers);
                for(OaTopic oa:oalist){
                    oa.setIsHistory(1);
                    topicService.saveOrUpdate(oa);
                }
            }
            if(oaMeeting.getConferee()==null||oaMeeting.getConferee()==""){
                oaMeeting.setConfereeid("");
                oaMeeting.setConferee("");
                meetingService.saveOrUpdate(oaMeeting);
                //查询通知表
                QueryWrapper<OaNotificationform> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("huiyiid",oaMeeting.getId());
                List<OaNotificationform> list =notificationformService.list(queryWrapper);
                for(OaNotificationform oanot:list){
                        QueryWrapper<OaNotificationform> queryRole = new QueryWrapper<OaNotificationform>();
                        queryRole.eq("huiyiid", oaMeeting.getId());
                        notificationformService.remove(queryRole);
                }
            }else {
                String condata = oaMeeting.getConferee();
                List<String> conlist = Arrays.asList(condata.split(","));
                List<String> coniddata = new ArrayList<>();
                for (int i = 0; i < conlist.size(); i++) {
                    QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("REAL_NAME", conlist.get(i));
                    SysUserEntity user = userService.list(queryWrapper).get(0);
                    coniddata.add(user.getId());
                }
                String idza = "";
                for (int i = 0; i < coniddata.size(); i++) {
                    if (i == 0) {
                        idza = coniddata.get(i);
                    } else {
                        idza = idza + "," + coniddata.get(i);
                    }
                }
                oaMeeting.setConfereeid(idza);
                meetingService.saveOrUpdate(oaMeeting);
                //查询通知表
                QueryWrapper<OaNotificationform> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("huiyiid", oaMeeting.getId());
                List<OaNotificationform> list = notificationformService.list(queryWrapper);
                for (OaNotificationform oanot : list) {
                    if (!coniddata.contains(oanot.getNameid())) {
                        //先删除角色
                        QueryWrapper<OaNotificationform> queryRole = new QueryWrapper<OaNotificationform>();
                        queryRole.eq("huiyiid", oaMeeting.getId());
                        queryRole.eq("nameid", oanot.getNameid());
                        notificationformService.remove(queryRole);
                    }
                }
                for (int i = 0; i < coniddata.size(); i++) {
                    //查询通知表
                    QueryWrapper<OaNotificationform> queryWrappers = new QueryWrapper<>();
                    queryWrappers.eq("huiyiid", oaMeeting.getId()).eq("nameid", coniddata.get(i));
                    List<OaNotificationform> oa = notificationformService.list(queryWrappers);
                    if (oa.size() <= 0) {
                        SysUserEntity USER=userService.getById(coniddata.get(i));
                        SysDeptEntity dept=deptService.getById(USER.getDeptId());
                        OaNotificationform oano = new OaNotificationform();
                        oano.setDepid(USER.getDeptId());
                        oano.setDepname(dept.getName());
                        oano.setStatus("0");
                        oano.setNameid(coniddata.get(i));
                        oano.setName(USER.getRealName());
                        oano.setHuiyiid(oaMeeting.getId());
                        oano.setCreateTime(new Date());
                        oano.setHuiyihome(oaMeeting.getMeetingId());
                        oano.setHuiyidate(oaMeeting.getMeetingTime());
                        notificationformService.saveOrUpdate(oano);
                    }
                }
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改议题会议失败");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    @RequestMapping(value = "printTopic")
    public ModelAndView printTopic(OaMeeting oaMeeting) throws ParseException {
        //创建人
       ModelAndView modelAndView = new ModelAndView("topic/meetingprint");
          modelAndView.addObject("meetingId",oaMeeting.getMeetingId());
          modelAndView.addObject("meetingName",oaMeeting.getMeetingName());
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          DateFormat sdd = new SimpleDateFormat("yyyy年MM月dd日");
          Date date =sdf.parse(oaMeeting.getMeetingTime());
          String datetime=sdd.format(date);
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date); //放入
          int hours= calendar.get(Calendar.HOUR_OF_DAY); //时（24小时制）
          String weekday=getWeekOfDate(date);
          String getDuringDay=getDuringDay(hours);
          int hour= calendar.get(Calendar.HOUR_OF_DAY); //时（24小时制）
          String  time=datetime+weekday+getDuringDay+":"+hour+":00";
          QueryWrapper<OaTopic> queryWrapper= new QueryWrapper<>();
          queryWrapper.eq("id", oaMeeting.getIssueId());
          OaTopic oatop=topicService.list(queryWrapper).get(0);
          modelAndView.addObject("oaTopic", oatop);
          modelAndView.addObject("time", time);
          return modelAndView;
    }
    @RequestMapping(value = "printTopicAll")
    public ModelAndView printTopicAll(OaMeeting oaMeeting) throws ParseException {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/meetingprintAll");
        modelAndView.addObject("meetingId",oaMeeting.getMeetingId());
        modelAndView.addObject("meetingName",oaMeeting.getMeetingName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat sdd = new SimpleDateFormat("yyyy年MM月dd日");
        Date date =sdf.parse(oaMeeting.getMeetingTime());
        String datetime=sdd.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); //放入
        int hours= calendar.get(Calendar.HOUR_OF_DAY); //时（24小时制）
        String weekday=getWeekOfDate(date);
        String getDuringDay=getDuringDay(hours);
        int hour= calendar.get(Calendar.HOUR_OF_DAY); //时（24小时制）
        String  time=datetime+weekday+getDuringDay+":"+hour+":00";
        QueryWrapper<OaTopic> queryWrapper= new QueryWrapper<>();
        String  str[]=oaMeeting.getIssueId().split(",");
        queryWrapper.in("id",str);
        List<OaTopic> list=topicService.list(queryWrapper);
        modelAndView.addObject("oaTopiclist", list);
        modelAndView.addObject("time", time);
        modelAndView.addObject("row_count", 2);
        return modelAndView;
    }
    @RequestMapping(value = "printqd")
    public ModelAndView printqd(OaMeeting oaMeeting) throws ParseException {
        //创建人
        ModelAndView modelAndView = new ModelAndView("topic/meetingprintqd");
        String str[] =oaMeeting.getConferee().split(",");
        modelAndView.addObject("strlist",str);
        return modelAndView;
    }
    /**
     * 根据日期获得星期的方法
     * @param date
     * @return
     * @author zhangsq
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        //String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }
    /**
     * 根据小时判断是否为上午、中午、下午
     * @param hour
     * @return
     * @author zhangsq
     */
    public static String getDuringDay(int hour){
        if (hour >= 7 && hour < 11) {
            return "上午";
        }if (hour >= 11 && hour <= 13) {
            return "正午";
        }if (hour >= 14 && hour <= 18) {
            return "下午";
        }
        return null;
    }

}

