package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.ScheduleConstant;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.entity.ScheduleProcessExcuteStatus;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleQueue;
import club.yinlihu.schedule.persist.ScheduleRegister;
import club.yinlihu.schedule.process.ScheduleProcess;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 任务调度
 */
public class ScheduleExcute {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleExcute.class);

    private static ScheduleQueue scheduleQueue = new ScheduleQueue();

    /**
     * 执行任务，自动获取信息触发下一步
     * @param configId
     */
    public static void excute(String configId) {
        Map<String, Object> processContext = SchedulePersist.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(ScheduleConstant.EXCUTE_STATUS);
        excuteStatus.setProcessStatus(ScheduleExcuteStatusEnum.EXCUTING.getCode());
        // 更新数据
        SchedulePersist.saveProcessContext(configId, processContext);

        String excuteProcess;
        // 只有失败和初始化的时候执行下一步
        if (StringUtils.equals(excuteStatus.getProcessStatus(), ScheduleExcuteStatusEnum.FAIL.getCode())) {
            excuteProcess = excuteStatus.getCurrentProcess();
        } else {
            excuteProcess = excuteStatus.getNextProcess();
        }

        ScheduleExcuteStatusEnum excuteResult = excuteProcess(configId, excuteProcess);

        /*
        成功触发下一个任务
        失败停止任务触发
        暂停停止任务触发
         */
        String codeResult = excuteResult.getCode();
        excuteStatus.setProcessStatus(codeResult);
        excuteStatus.setCurrentProcess(excuteProcess);
        processContext.put(ScheduleConstant.EXCUTE_STATUS, excuteStatus);

        // 保存上下文信息
        SchedulePersist.saveProcessContext(configId, processContext);

        // 如果成功了，执行成功执行，最后一个任务不管是暂停还是成功，只要不是失败，都算成功
        if (excuteStatus.isLastProcess()
                && !StringUtils.equalsIgnoreCase(excuteStatus.getProcessStatus(), ScheduleExcuteStatusEnum.FAIL.getCode())) {
            // TODO：上下文信息以后需要存放到某个地方，用于以后读取
            // SchedulePersist.delProcessContext(configId);
            // TODO：以后得实现依赖任务触发
        } else {
            // 如果是其他，则再次下发
            // 下发任务
            scheduleQueue.add(configId);
        }
    }

    /**
     * 执行指定任务
     * @param processName
     * @param configId
     */
    public static ScheduleExcuteStatusEnum excuteProcess(String configId, String processName){
        try {
            ScheduleProcess scheduleProcess = ScheduleRegister.getProcessInstance(processName);
            return scheduleProcess.excute(configId);
        } catch (Exception e) {
            LOG.error("excute process exception!", e);
            return ScheduleExcuteStatusEnum.FAIL;
        }
    }
}
