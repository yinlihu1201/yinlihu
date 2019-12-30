package club.yinlihu.schedule.entity;

/**
 * 调度任务关系
 */
public class ScheduleTaskLinks {
    /**
     * 前置任务type
     */
    private ScheduleTask from;
    /**
     * 后续触发任务type
     */
    private ScheduleTask to;

    public ScheduleTask getFrom() {
        return from;
    }

    public void setFrom(ScheduleTask from) {
        this.from = from;
    }

    public ScheduleTask getTo() {
        return to;
    }

    public void setTo(ScheduleTask to) {
        this.to = to;
    }
}
