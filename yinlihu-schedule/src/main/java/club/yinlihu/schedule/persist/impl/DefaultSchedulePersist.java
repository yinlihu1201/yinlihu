package club.yinlihu.schedule.persist.impl;

import club.yinlihu.schedule.entity.Schedule;
import club.yinlihu.schedule.exception.ScheduleExcetion;
import club.yinlihu.schedule.persist.SchedulePersist;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度读取
 */
public class DefaultSchedulePersist implements SchedulePersist {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSchedulePersist.class);

    private static List<Schedule> scheduleEntities = new ArrayList<Schedule>();

    private static Map<String, Schedule> scheduleEntityMap = new HashMap<String, Schedule>();

    /**
     * save scheudule
     * 保存在
     */
    public void saveSchedule(Schedule schedule) {
        LOG.info("save schedule {}",JSONObject.toJSONString(schedule));

        if (scheduleEntityMap.containsKey(schedule.getScheduleName())) {
            throw new ScheduleExcetion("调度名重复！");
        }
        scheduleEntityMap.put(schedule.getScheduleName(), schedule);

        scheduleEntities.add(schedule);
    }

    public Schedule getSchedule(String scheduleName) {
        Schedule schedule = scheduleEntityMap.get(scheduleName);
        if (schedule == null) {
            throw new ScheduleExcetion("schedule not exist!");
        }
        return schedule;
    }

    public List<Schedule> getAllSchedule(){
        return scheduleEntities;
    }

    private DefaultSchedulePersist(){}

    private static DefaultSchedulePersist persist = new DefaultSchedulePersist();

    public static DefaultSchedulePersist getInstance(){
        return persist;
    }
}
