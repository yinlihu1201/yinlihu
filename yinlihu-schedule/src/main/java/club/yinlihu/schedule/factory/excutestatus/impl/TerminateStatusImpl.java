package club.yinlihu.schedule.factory.excutestatus.impl;

import club.yinlihu.schedule.entity.ConfigResult;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.execute.ScheduleExcute;
import club.yinlihu.schedule.factory.excutestatus.ScheduleExcuteStatus;
import club.yinlihu.schedule.factory.excutestatus.ScheduleExcuteStatusFactory;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行成功类
 */
public class TerminateStatusImpl extends ScheduleExcuteStatus {
    private static final Logger LOG = LoggerFactory.getLogger(TerminateStatusImpl.class);

    private ScheduleProcessContext context = ScheduleInitialization.getInstance().getContext();

    public TerminateStatusImpl() {
        ScheduleExcuteStatusFactory.register(ScheduleExcuteStatusEnum.TERMINATE, this);
    }

    public ConfigResult doBefore(String configId) {
        boolean hasTerminateSchedule = false;
        if (hasTerminateSchedule) {
            // TODO： 可能会有对应的终止任务进行触发

        } else {
            context.delProcessContext(configId);
        }
        return ConfigResult.fail("terminate schedule!");
    }

    public ConfigResult doNow(String configId) {
        return null;
    }

    public ConfigResult doAfter(String configId) {
        return null;
    }

    public ConfigResult doEnd(String configId) {
        return null;
    }
}
