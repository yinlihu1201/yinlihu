package club.yinlihu.schedule.entity;

import club.yinlihu.schedule.exception.ScheduleExcetion;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.persist.SchedulePersist;

import java.util.ArrayList;
import java.util.List;

/**
 * 调度实体类
 */
public class ScheduleProcessExcuteStatus {

    private SchedulePersist persist = ScheduleInitialization.getInstance().getSchedule();

    /**
     * 获取下一个步骤类型
     * @return
     */
    public String getNextProcess() {
        int index = processList.indexOf(currentProcess);
        if (index < 0) {
            throw new ScheduleExcetion(currentProcess + " current process not exist in " + scheduleName);
        }

        if (index < processList.size() - 1) {
            return processList.get(index + 1);
        }

        return null;
    }

    public String getLastProcess(){
        String process = processList.get(processList.size() - 1);
        return process;
    }

    /**
     * 是否是最后一步
     * @return
     */
    public boolean isLastProcess(){
        return (processList.size() - 1) == processList.indexOf(currentProcess);
    }

    // 当前步骤
    private String currentProcess;
    // 执行状态
    private String processStatus;
    // 任务名称
    private String scheduleName;
    // 执行任务列表
    private List<String> processList;


    // 状态初始化
    public ScheduleProcessExcuteStatus(String scheduleName) {
        // 查询任务执行到的步骤
        Schedule schedule = persist.getSchedule(scheduleName);
        List<ScheduleTask> scheduleTaskList = schedule.getScheduleTask();

        List<String> processList = new ArrayList<String>();
        for (ScheduleTask st : scheduleTaskList) {
            processList.add(st.getTaskType());
        }

        this.scheduleName = scheduleName;
        this.processList = processList;
        this.currentProcess = processList.get(0);
        this.processStatus = ScheduleExcuteStatusEnum.WAITSTART.getCode();
    }

    public String getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(String currentProcess) {
        this.currentProcess = currentProcess;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public List<String> getProcessList() {
        return processList;
    }

    public void setProcessList(List<String> processList) {
        this.processList = processList;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }
}
