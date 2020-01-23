package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.ScheduleConstant;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.entity.ScheduleProcessExcuteStatus;
import club.yinlihu.schedule.exception.ScheduleExcetion;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 任务调度
 */
public class ScheduleTrigger {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleTrigger.class);

    private static ScheduleQueue scheduleQueue = new ScheduleQueue();

    /**
     * 触发任务
     * @param configId 唯一标识
     * @param scheduleName 任务名称
     */
    public static void startTrigger(String configId, String scheduleName) {
        LOG.info("配置流程{}，触发任务为{}", configId, scheduleName);
        Map<String, Object> processContext = SchedulePersist.getProcessContext(configId);

        processContext.put(ScheduleConstant.EXCUTE_STATUS, new ScheduleProcessExcuteStatus(scheduleName));
        SchedulePersist.saveProcessContext(configId, processContext);

        submit(configId);
    }

    /**
     * 继续执行任务
     * @param configId
     */
    public static void continueTrigger(String configId){
        submit(configId);
    }

    /**
     * 跳过执行此任务
     * @param configId
     */
    public static void skipTrigger(String configId){
        Map<String, Object> processContext = SchedulePersist.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(ScheduleConstant.EXCUTE_STATUS);
        excuteStatus.setProcessStatus(ScheduleExcuteStatusEnum.SUCESS.getCode());

        processContext.put(ScheduleConstant.EXCUTE_STATUS, excuteStatus);

        // 保存上下文信息
        SchedulePersist.saveProcessContext(configId, processContext);

        // 下发任务
        scheduleQueue.add(configId);
    }


    /**
     * 提交任务
     * @param configId
     */
    public static void submit(String configId){
        // 下发任务
        scheduleQueue.add(configId);
    }
}
