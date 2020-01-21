package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.ScheduleEntity;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.entity.ScheduleProcessExcuteStatus;
import club.yinlihu.schedule.entity.ScheduleTask;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleRegister;
import club.yinlihu.schedule.process.ScheduleProcess;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
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
    public void excute(String configId, String scheduleName) {
        Map<String, Object> processContext = SchedulePersist.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(EXCUTE_STATUS);
        if (excuteStatus == null) {
            excuteStatus = initScheduleProcessExcuteStatus(scheduleName);
        }

        String excuteProcess;
        // 只有失败和初始化的时候执行下一步
        if (excuteStatus.getProcessStatus() != null
                && !StringUtils.equals(excuteStatus.getProcessStatus(), ScheduleExcuteStatusEnum.FAIL.getCode())) {
            excuteProcess = excuteStatus.getNextProcess();
        } else {
            excuteProcess = excuteStatus.getCurrentProcess();
        }


    }

    /**
     * 初始化执行状态数据
     * @return
     */
    private ScheduleProcessExcuteStatus initScheduleProcessExcuteStatus(String scheduleName){
        ScheduleProcessExcuteStatus excuteStatus = new ScheduleProcessExcuteStatus(scheduleName);
        List<String> processList = excuteStatus.getProcessList();

        excuteStatus.setCurrentProcess(processList.get(0));
        return excuteStatus;
    }

    /**
     * 执行指定任务
     * @param processName
     * @param configId
     */
    public ScheduleExcuteStatusEnum excuteProcess(String configId, String processName){
        ScheduleProcess scheduleProcess = ScheduleRegister.getProcessInstance(processName);
        return scheduleProcess.excute(configId);
    }
}
