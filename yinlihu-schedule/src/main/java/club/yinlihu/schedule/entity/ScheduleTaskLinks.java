package club.yinlihu.schedule.entity;

/**
 * 调度任务关系
 */
public class ScheduleTaskLinks {
    /**
     * 前置任务type
     */
    private String from;
    /**
     * 后续触发任务type
     */
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
