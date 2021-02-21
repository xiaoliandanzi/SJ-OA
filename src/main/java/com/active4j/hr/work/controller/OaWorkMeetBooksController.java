package com.active4j.hr.work.controller;

import com.active4j.hr.base.controller.BaseController;
import com.active4j.hr.common.constant.GlobalConstant;
import com.active4j.hr.core.beanutil.MyBeanUtils;
import com.active4j.hr.core.model.AjaxJson;
import com.active4j.hr.core.query.QueryUtils;
import com.active4j.hr.core.shiro.ShiroUtils;
import com.active4j.hr.core.util.DateUtils;
import com.active4j.hr.core.util.ListUtils;
import com.active4j.hr.core.util.ResponseUtil;
import com.active4j.hr.core.web.tag.model.DataGrid;
import com.active4j.hr.system.model.SysUserModel;
import com.active4j.hr.system.service.SysUserService;
import com.active4j.hr.system.util.MessageUtils;
import com.active4j.hr.work.entity.OaWorkMeetRoomBooksEntity;
import com.active4j.hr.work.entity.OaWorkMeetRoomEntity;
import com.active4j.hr.work.service.OaWorkMeetRoomBooksService;
import com.active4j.hr.work.service.OaWorkMeetRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @title OaWorkMeetBooksController.java
 * @description 
		  会议预定
 * @time  2020年4月6日 上午10:39:48
 * @author xfzhang
 * @version 1.0
*/
@Controller
@RequestMapping("/oa/work/meetRoomBooks")
@Slf4j
public class OaWorkMeetBooksController extends BaseController {

	
	@Autowired
	private OaWorkMeetRoomBooksService oaWorkMeetRoomBooksService;
	@Autowired
	private OaWorkMeetRoomService oaWorkMeetRoomService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 会议室预定列表
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		
		ModelAndView view = new ModelAndView("oa/work/meetroom/meetroombookslist");
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", ListUtils.listToReplaceStr(lstRooms, "name", "id"));
		
		String nowStrDate = DateUtils.date2Str(DateUtils.SDF_YYYY_MM_DD);
		view.addObject("nowStrDate", nowStrDate);
		
		return view;
	}
	
	/**
	 * 查询数据
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping("/datagrid")
	public void datagrid(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 拼接查询条件
		QueryWrapper<OaWorkMeetRoomBooksEntity> queryWrapper = QueryUtils.installQueryWrapper(oaWorkMeetRoomBooksEntity, request.getParameterMap(), dataGrid);
		// 执行查询
		IPage<OaWorkMeetRoomBooksEntity> lstResult = oaWorkMeetRoomBooksService.page(new Page<OaWorkMeetRoomBooksEntity>(dataGrid.getPage(), dataGrid.getRows()), queryWrapper);

		// 输出结果
		ResponseUtil.writeJson(response, dataGrid, lstResult);
	}
	
	/**
	 * 跳转到新增编辑页面
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/addorupdate")
	public ModelAndView addorupdate(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("oa/work/meetroom/meetroombooks");
		
		//查询可用的会议室
		List<OaWorkMeetRoomEntity> lstRooms = oaWorkMeetRoomService.findNormalMeetRoom();
		view.addObject("lstRooms", lstRooms);

		String userId = ShiroUtils.getSessionUserId();
		SysUserModel userModel = sysUserService.getInfoByUserId(userId).get(0);
		view.addObject("userName", userModel.getRealName());
		view.addObject("dept", userModel.getDeptName());

		if(StringUtils.isNotEmpty(oaWorkMeetRoomBooksEntity.getId())) {
			oaWorkMeetRoomBooksEntity = oaWorkMeetRoomBooksService.getById(oaWorkMeetRoomBooksEntity.getId());
			view.addObject("meet", oaWorkMeetRoomBooksEntity);
		}
		
		return view;
	}
	
	
	/**
	 * 保存方法
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxJson save(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, String meetRoomId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			
			//预定人赋值
			String userId = ShiroUtils.getSessionUserId();
			oaWorkMeetRoomBooksEntity.setUserName(ShiroUtils.getSessionUser().getRealName());
			oaWorkMeetRoomBooksEntity.setUserId(userId);
			oaWorkMeetRoomBooksEntity.setDept(sysUserService.getInfoByUserId(userId).get(0).getDeptName());
			
			if(null == oaWorkMeetRoomBooksEntity.getBookDate()) {
				j.setSuccess(false);
				j.setMsg("预定日期不能为空");
				return j;
			}

			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = df.format(new Date());
			Date now = df.parse(currentDate);
			if(oaWorkMeetRoomBooksEntity.getBookDate().compareTo(now) == -1){
				j.setSuccess(false);
				j.setMsg("预定日期日期不能在当前日期之前");
				return j;
			}

			if(null == oaWorkMeetRoomBooksEntity.getStartDate() || null == oaWorkMeetRoomBooksEntity.getEndDate()) {
				j.setSuccess(false);
				j.setMsg("请填写完整的预定时间");
				return j;
			}
			if (!oaWorkMeetRoomBooksEntity.getEndDate().after(oaWorkMeetRoomBooksEntity.getStartDate())){
				j.setSuccess(false);
				j.setMsg("请填写正确的预定时间");
				return j;
			}
			//日期赋值
			oaWorkMeetRoomBooksEntity.setStrBookDate(DateUtils.date2Str(oaWorkMeetRoomBooksEntity.getBookDate(), DateUtils.SDF_YYYY_MM_DD));
			
			//时间的校验，预定的会议室，时间不能重合
			List<OaWorkMeetRoomBooksEntity> lstRooms = oaWorkMeetRoomBooksService.findMeetBooks(meetRoomId, oaWorkMeetRoomBooksEntity.getStrBookDate());
			boolean isValid = true;
			if(null != lstRooms && lstRooms.size() > 0) {
				for(OaWorkMeetRoomBooksEntity bookRoom : lstRooms) {
					if (!oaWorkMeetRoomBooksEntity.getStartDate().after(bookRoom.getStartDate())
							&& oaWorkMeetRoomBooksEntity.getEndDate().after(bookRoom.getStartDate())){
						isValid = false;
						break;
					}
					if (!oaWorkMeetRoomBooksEntity.getEndDate().before(bookRoom.getEndDate())
							&& oaWorkMeetRoomBooksEntity.getStartDate().before(bookRoom.getEndDate())){
						isValid = false;
						break;
					}
					if (!oaWorkMeetRoomBooksEntity.getStartDate().before(bookRoom.getStartDate())
							&& !oaWorkMeetRoomBooksEntity.getEndDate().after(bookRoom.getEndDate())) {
						isValid = false;
						break;
					}
				}
			}
			if (!isValid) {
				j.setSuccess(false);
				j.setMsg("当前会议室已经被预定");
				return j;
			}

			
			if(StringUtils.isEmpty(oaWorkMeetRoomBooksEntity.getId())) {
				if(StringUtils.isNotEmpty(meetRoomId)) {
					OaWorkMeetRoomEntity room = oaWorkMeetRoomService.getById(meetRoomId);
					if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_WORK_MEET_ROOM_STATUS_STOP)) {
						j.setSuccess(false);
						j.setMsg("会议室不可用");
						return j;
					}
					oaWorkMeetRoomBooksEntity.setMeetRoomId(meetRoomId);
				}
				
				//新增方法
				oaWorkMeetRoomBooksService.save(oaWorkMeetRoomBooksEntity);
				
				
			}else {
				//编辑方法
				OaWorkMeetRoomBooksEntity tmp = oaWorkMeetRoomBooksService.getById(oaWorkMeetRoomBooksEntity.getId());
				MyBeanUtils.copyBeanNotNull2Bean(oaWorkMeetRoomBooksEntity, tmp);
				
				if(StringUtils.isNotEmpty(meetRoomId)) {
					OaWorkMeetRoomEntity room = oaWorkMeetRoomService.getById(meetRoomId);
					if(StringUtils.equals(room.getStatus(), GlobalConstant.OA_WORK_MEET_ROOM_STATUS_STOP)) {
						j.setSuccess(false);
						j.setMsg("会议室不可用");
						return j;
					}
					tmp.setMeetRoomId(meetRoomId);
				}
				oaWorkMeetRoomBooksService.saveOrUpdate(tmp);
				
			}
			sendMessage2Attendee(oaWorkMeetRoomBooksEntity.getAttendee(),
					oaWorkMeetRoomBooksEntity.getUserName(),
					oaWorkMeetRoomBooksEntity.getBookDate(),
					oaWorkMeetRoomBooksEntity.getStartDate(),
					oaWorkMeetRoomBooksEntity.getEndDate(),
					oaWorkMeetRoomBooksEntity.getMeetRoomId());
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("保存会议室预定信息失败，错误信息:{}", e);
		}
		return j;
	}

	private void sendMessage2Attendee(String attendees, String userName, Date bookDate,
		Date startTime, Date endTime, String roomId){
		SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
		try {
			if(attendees.isEmpty()) {
				return;
			}
			String[] attendeeList = attendees.split(",");
			for (int i = 0; i < attendeeList.length; i++) {
				String content = String.format("您好，%s 邀请您在 %s 的 %s 至 %s 于%s会议室参加会议",
						userName, formatterDate.format(bookDate),
						formatterTime.format(startTime), formatterTime.format(endTime),
						oaWorkMeetRoomService.getById(roomId).getName());
				MessageUtils.SendSysMessage(sysUserService.getUserByRealName(attendeeList[i]).getId(), content);
			}
		} catch (Exception ex) {
			log.error("发送会议信息失败，错误信息:{}", ex);
		}
	}
	
	/**
	 * 删除
	 * @param oaWorkMeetRoomBooksEntity
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxJson delete(OaWorkMeetRoomBooksEntity oaWorkMeetRoomBooksEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			if(StringUtils.isNotEmpty(oaWorkMeetRoomBooksEntity.getId())) {
				oaWorkMeetRoomBooksEntity = oaWorkMeetRoomBooksService.getById(oaWorkMeetRoomBooksEntity.getId());
				
				//不是自己预订的会议室不能删除
				if(!StringUtils.equals(oaWorkMeetRoomBooksEntity.getUserId(), ShiroUtils.getSessionUserId())) {
					j.setSuccess(false);
					j.setMsg("不是自己预订的会议室不能删除");
					return j;
				}
				oaWorkMeetRoomBooksService.removeById(oaWorkMeetRoomBooksEntity.getId());
			}
		}catch(Exception e) {
			j.setSuccess(false);
			j.setMsg(GlobalConstant.Err_Msg_All);
			log.error("删除会议室预定信息失败，错误信息:{}", e);
		}
		
		return j;
	}
}
