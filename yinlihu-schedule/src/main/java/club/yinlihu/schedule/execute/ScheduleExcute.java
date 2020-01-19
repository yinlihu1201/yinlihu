package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.ScheduleEntity;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.entity.ScheduleProcessExcuteStatus;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleRegister;
import club.yinlihu.schedule.process.ScheduleProcess;

import java.util.Map;

/**
 * 任务调度
 */
public class ScheduleExcute {

    private static String EXCUTE_STATUS = "excute_status";

    /**
     * 执行任务，自动获取信息触发下一步
     * @param configId
     */
    public void excute(String configId) {
        Map<String, Object> processContext = SchedulePersist.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(EXCUTE_STATUS);
    }
    /**
     * 执行指定任务
     * @param processName
     * @param configId
     */
    public void excuteProcess(String configId, String processName){
        ScheduleProcess scheduleProcess = ScheduleRegister.getProcessInstance(processName);
        ScheduleExcuteStatusEnum excute = scheduleProcess.excute(configId);
    }
}
