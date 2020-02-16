package club.yinlihu.schedule.init.impl;

import club.yinlihu.schedule.entity.Schedule;
import club.yinlihu.schedule.entity.ScheduleTask;
import club.yinlihu.schedule.entity.ScheduleTypeEnum;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import club.yinlihu.schedule.persist.impl.DefaultSchedulePersist;
import club.yinlihu.schedule.persist.impl.DefaultScheduleProcessContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 单机任务初始化
 */
public class DefaultScheduleInitialization extends ScheduleInitialization {

    public void init() {
        // 设置当前执行方式
        this.setScheduleInit(this);

        this.scheduleInit();
    }

    public ScheduleProcessContext getContext() {
        return DefaultScheduleProcessContext.getInstance();
    }

    public SchedulePersist getSchedule() {
        return DefaultSchedulePersist.getInstance();
    }

    protected void scheduleInit() {
        List<ScheduleTask> taskList = new ArrayList<ScheduleTask>();

        ScheduleTask task = new ScheduleTask("DemoProcess","任务1");
        taskList.add(task);

        ScheduleTask task2 = new ScheduleTask("Demo2Process","任务2");
        taskList.add(task2);

        ScheduleTask task3 = new ScheduleTask("Demo3Process","任务3");
        taskList.add(task3);

        Schedule se = new Schedule("schedule1", "调度1", taskList, ScheduleTypeEnum.AUTO);

        DefaultSchedulePersist.getInstance().saveSchedule(se);
    }
}
