package club.yinlihu.schedule.entity;

/**
 * 调度任务
 */
public class ScheduleTask {
    /**
     * 任务唯一标识
     */
    private String taskType;
    /**
     * 任务描述
     */
    private String taskDesc;

    public ScheduleTask(String taskType, String taskDesc) {
        this.taskType = taskType;
        this.taskDesc = taskDesc;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
