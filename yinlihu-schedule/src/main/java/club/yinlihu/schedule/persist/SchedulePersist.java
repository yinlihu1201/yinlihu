package club.yinlihu.schedule.persist;

import club.yinlihu.schedule.entity.Schedule;

import java.util.List;

/**
 * 调度读取
 */
public interface SchedulePersist {

    /**
     * 保存任务
     */
    void saveSchedule(Schedule schedule);

    /**
     * 获取任务
     * @param scheduleName
     * @return
     */
    Schedule getSchedule(String scheduleName);

    /**
     * 获取所有任务
     * @return
     */
    List<Schedule> getAllSchedule();
}
