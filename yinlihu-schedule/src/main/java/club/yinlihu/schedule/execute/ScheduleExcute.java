package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.*;
import club.yinlihu.schedule.factory.excutestatus.ScheduleExcuteStatus;
import club.yinlihu.schedule.factory.excutestatus.ScheduleExcuteStatusFactory;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import club.yinlihu.schedule.process.ScheduleProcess;
import club.yinlihu.schedule.process.ScheduleRegister;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 任务调度
 */
public class ScheduleExcute {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleExcute.class);

    private ScheduleProcessContext context = ScheduleInitialization.getInstance().getContext();

    /**
     * 执行任务，自动获取信息触发下一步
     * @param configId
     */
    public void excute(String configId) {
        Map<String, Object> processContext = context.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(ScheduleConstant.EXCUTE_STATUS);

        // 前置任务
        ConfigResult configResult = this.beforeProcess(configId, excuteStatus);
        if (!configResult.isSuccess()) {
            LOG.info("before prcess message: {}" , configResult.getMsg());
            return;
        }

        // 如果是最后一步并且是执行成功，代表流程跳过结束
        excute(configId, excuteStatus, processContext);

        // 任务后置处理
        this.afterProcess(configId, excuteStatus);
    }

    private void excute(String configId, ScheduleProcessExcuteStatus excuteStatus, Map<String, Object> processContext){
        String excuteProcessStep = this.getExcuteProcessStep(excuteStatus);
        LOG.info("excute process configId:{} process:{}", configId, excuteProcessStep);

        // 将任务改为执行中
        excuteStatus.setProcessStatus(ScheduleExcuteStatusEnum.EXCUTING.getCode());
        excuteStatus.setCurrentProcess(excuteProcessStep);

        // 更新数据: 将数据同步成当前步骤的
        context.saveProcessContext(configId, processContext);

        ScheduleExcuteStatusEnum excuteResult = excuteProcess(configId, excuteProcessStep);

        String codeResult = excuteResult.getCode();
        excuteStatus.setProcessStatus(codeResult);
        processContext.put(ScheduleConstant.EXCUTE_STATUS, excuteStatus);

        // 保存上下文信息
        context.saveProcessContext(configId, processContext);
    }

    /**
     * 执行任务前置任务
     * @return
     */
    private ConfigResult beforeProcess(String configId, ScheduleProcessExcuteStatus excuteStatus){
        ScheduleExcuteStatus instance = ScheduleExcuteStatusFactory.getInstance(excuteStatus.getProcessStatus());
        if (instance == null) {
            LOG.debug("there is no excute for {}", excuteStatus.getProcessStatus());
            return ConfigResult.ok();
        }

        return instance.doBefore(configId);
    }

    /**
     * 任务后置处理
     * @param configId
     * @param excuteStatus
     */
    private ConfigResult afterProcess(String configId, ScheduleProcessExcuteStatus excuteStatus){
        ScheduleExcuteStatus instance = ScheduleExcuteStatusFactory.getInstance(excuteStatus.getProcessStatus());
        if (instance == null) {
            LOG.debug("there is no excute for {}", excuteStatus.getProcessStatus());
            return ConfigResult.ok();
        }

        if (excuteStatus.isLastProcess()) {
            instance.doEnd(configId);
            return ConfigResult.ok();
        }

        return instance.doAfter(configId);
    }

    /**
     * 执行指定任务
     * @param processName
     * @param configId
     */
    private ScheduleExcuteStatusEnum excuteProcess(String configId, String processName){
        try {
            ScheduleProcess scheduleProcess = ScheduleRegister.getProcessInstance(processName);
            return scheduleProcess.excute(configId);
        } catch (Exception e) {
            LOG.error("excute process exception!", e);
            return ScheduleExcuteStatusEnum.FAIL;
        }
    }

    /**
     * 获取当前执行步骤
     * @param excuteStatus
     * @return
     */
    private String getExcuteProcessStep(ScheduleProcessExcuteStatus excuteStatus){
        String excuteProcessStep;
        // 只有失败和初始化的时候执行下一步
        if (StringUtils.equals(excuteStatus.getProcessStatus(), ScheduleExcuteStatusEnum.FAIL.getCode())
                || StringUtils.equals(excuteStatus.getProcessStatus(), ScheduleExcuteStatusEnum.WAITSTART.getCode())) {
            excuteProcessStep = excuteStatus.getCurrentProcess();
        } else {
            excuteProcessStep = excuteStatus.getNextProcess();
        }

        return excuteProcessStep;
    }


    private static ScheduleExcute instance = new ScheduleExcute();

    private ScheduleExcute(){}

    public static ScheduleExcute getInstance(){
        return instance;
    }
}
