package club.yinlihu.schedule.entity;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 调度实体类
 */
public class ScheduleEntity {
    /**
     * 调度名称：应该是唯一的
     */
    private String scheduleName;
    /**
     * 调度描述
     */
    private String scheduleDesc;
    /**
     * 调度任务
     */
    private List<ScheduleTask> scheduleTask;
    /**
     * 调度任务执行关系
     */
    private List<ScheduleTaskLinks> scheduleTaskLinks;

    public ScheduleEntity(String scheduleName, String scheduleDesc, List<ScheduleTask> scheduleTask) {
        if (StringUtils.isBlank(scheduleName)) {
            throw new RuntimeException("调度名称不可为空！");
        }
        if (StringUtils.isBlank(scheduleDesc)) {
            throw new RuntimeException("调度描述不可为空！");
        }
        if (CollectionUtils.isEmpty(scheduleTask)) {
            throw new RuntimeException("调度任务不可为空！");
        }
        this.scheduleName = scheduleName;
        this.scheduleDesc = scheduleDesc;
        this.scheduleTask = scheduleTask;
    }

    /*public ScheduleEntity(String scheduleName, String scheduleDesc, List<ScheduleTask> scheduleTask, List<ScheduleTaskLinks> scheduleTaskLinks) {
        if (StringUtils.isBlank(scheduleName)) {
            throw new RuntimeException("调度名称不可为空！");
        }
        if (StringUtils.isBlank(scheduleDesc)) {
            throw new RuntimeException("调度描述不可为空！");
        }
        if (CollectionUtils.isEmpty(scheduleTask)) {
            throw new RuntimeException("调度任务不可为空！");
        }
        if (CollectionUtils.isEmpty(scheduleTaskLinks)) {
            throw new RuntimeException("调度任务关联不可为空！");
        }
        this.scheduleName = scheduleName;
        this.scheduleDesc = scheduleDesc;
        this.scheduleTask = scheduleTask;
        this.scheduleTaskLinks = scheduleTaskLinks;
    }*/

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getScheduleDesc() {
        return scheduleDesc;
    }

    public void setScheduleDesc(String scheduleDesc) {
        this.scheduleDesc = scheduleDesc;
    }

    public List<ScheduleTask> getScheduleTask() {
        return scheduleTask;
    }

    public void setScheduleTask(List<ScheduleTask> scheduleTask) {
        this.scheduleTask = scheduleTask;
    }

    public List<ScheduleTaskLinks> getScheduleTaskLinks() {
        return scheduleTaskLinks;
    }

    public void setScheduleTaskLinks(List<ScheduleTaskLinks> scheduleTaskLinks) {
        this.scheduleTaskLinks = scheduleTaskLinks;
    }
}
