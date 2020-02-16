package club.yinlihu.schedule.init;

import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import club.yinlihu.schedule.process.ScheduleProcess;

/**
 * 任务初始化接口
 */
public abstract class ScheduleInitialization {
    protected static ScheduleInitialization scheduleInitialization;

    protected void setScheduleInit(ScheduleInitialization scheduleInitialization){
        this.scheduleInitialization = scheduleInitialization;
    }
    public static ScheduleInitialization getInstance(){
        return scheduleInitialization;
    }

    /**
     * 有类似于spring
     * 这种管理对象的，则调用此方法
     */
    public abstract void init();

    /**
     * 初始化上下文信息: 实现 ScheduleProcessContext 并且初始化到这个方法中（建议使用单例模式）
     * @return
     */
    public abstract ScheduleProcessContext getContext();

    /**
     * 获取任务： 实现 SchedulePersist 并且初始化到这个方法中（建议使用单例模式）
     * @return
     */
    public abstract SchedulePersist getSchedule();

    /**
     * 调度存放路径
     * @param schedulePath 调度存放路径
     */
    public void init(String schedulePath){
        // 调度任务初始化：没有spring等框架进行管理对象的时候，调用此方法
        ScheduleProcess.init(schedulePath);
        init();
    }

    /**
     * 初始化任务数据：可以不执行
     */
    protected void scheduleInit(){

    };
}
