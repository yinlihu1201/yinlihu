package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.ScheduleConstant;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.entity.ScheduleProcessExcuteStatus;
import club.yinlihu.schedule.exception.ScheduleExcetion;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 任务调度
 */
public class ScheduleTrigger {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleTrigger.class);

    private ScheduleProcessContext context = ScheduleInitialization.getInstance().getContext();

    /**
     * 触发任务
     * @param configId 唯一标识
     * @param scheduleName 任务名称
     */
    public synchronized void startTrigger(String configId, String scheduleName) {
        LOG.info("trigger schedulle：{}，configId：{}",scheduleName , configId);
        Map<String, Object> processContext = context.getProcessContext(configId);

        // 若当前有任务在执行，则此唯一标识则不可进行任务触发
        if (processContext.size() != 0) {
            throw new ScheduleExcetion("configId " + configId + " is running");
        }

        processContext.put(ScheduleConstant.EXCUTE_STATUS, new ScheduleProcessExcuteStatus(scheduleName));
        context.saveProcessContext(configId, processContext);

        submit(configId);
    }

    /**
     * 继续执行任务:失败或者暂停任务
     * @param configId
     */
    public void continueTrigger(String configId){
        submit(configId);
    }

    /**
     * 跳过执行此任务
     * @param configId
     */
    public void skipTrigger(String configId){
        Map<String, Object> processContext = context.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(ScheduleConstant.EXCUTE_STATUS);
        if (excuteStatus == null) {
            throw new ScheduleExcetion("shedule not exists!");
        }

        excuteStatus.setProcessStatus(ScheduleExcuteStatusEnum.SUCESS.getCode());

        processContext.put(ScheduleConstant.EXCUTE_STATUS, excuteStatus);

        // 保存上下文信息
        context.saveProcessContext(configId, processContext);

        // 下发任务
        submit(configId);
    }

    /**
     * 结束调度
     */
    public void terminateSchedule(String configId){
        Map<String, Object> processContext = context.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(ScheduleConstant.EXCUTE_STATUS);
        if (excuteStatus == null) {
            throw new ScheduleExcetion("shedule not exists!");
        }

        excuteStatus.setProcessStatus(ScheduleExcuteStatusEnum.TERMINATE.getCode());

        context.saveProcessContext(configId, processContext);

        submit(configId);
    }


    /**
     * 提交任务
     * @param configId
     */
    private void submit(String configId){
        // 下发任务
        ScheduleExcute.getInstance().excute(configId);
    }


    private static ScheduleTrigger instance = new ScheduleTrigger();

    private ScheduleTrigger(){}

    public static ScheduleTrigger getInstance(){
        return instance;
    }
}
