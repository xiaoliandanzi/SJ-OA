package com.active4j.hr.topic.until;

import com.active4j.hr.topic.entity.OaMeeting;
import com.active4j.hr.topic.entity.OaTopic;
import com.active4j.hr.topic.service.OaMeetingService;
import com.active4j.hr.topic.service.OaTopicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@EnableTransactionManagement
public class SaticScheduleTask {
    public  final OaMeetingService meetingService;
    public  final OaTopicService topicService;


    public SaticScheduleTask(OaMeetingService meetingService, OaTopicService topicService) {
        this.meetingService = meetingService;
        this.topicService=topicService;
    }


    //3.添加定时任务
    @Scheduled(cron = "0 */1 * * * ?")
    //@Scheduled(fixedRate=5000)
    private void updatestatus() throws ParseException {
        //查询会议表
        QueryWrapper<OaMeeting> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("STATE_ID","已结束");
        List<OaMeeting> list =meetingService.list(queryWrapper);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OaMeeting oaMeeting:list){
            Date  date =format.parse(oaMeeting.getMeetingTime());
            Date date1 =format.parse(oaMeeting.getMeetingendTime());
            int compareTo =date.compareTo(new Date());
            int compareTo2 =date1.compareTo(new Date());
            if(compareTo==-1){
                oaMeeting.setStateId("进行中");
            }else if(compareTo==1){
                oaMeeting.setStateId("未开始");
            }
            if (compareTo2==-1){
                oaMeeting.setStateId("已结束");
                String ids=oaMeeting.getIssueId();
                String  strs[] =ids.split(",");
                //议题列表的数据
                QueryWrapper<OaTopic> queryWrappers = new QueryWrapper<>();
                queryWrappers.in("id",strs);
                List<OaTopic> oalist=topicService.list(queryWrappers);
                for(OaTopic oa:oalist){
                    oa.setIsHistory(1);
                    topicService.savetopic(oa);
                }
            }
            meetingService.savemeeting(oaMeeting);
            //meetingService.saveOrUpdate(oaMeeting);
        }
    }
}
