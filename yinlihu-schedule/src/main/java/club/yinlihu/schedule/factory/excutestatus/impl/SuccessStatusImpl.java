package club.yinlihu.schedule.factory.excutestatus.impl;

import club.yinlihu.schedule.entity.*;
import club.yinlihu.schedule.execute.ScheduleExcute;
import club.yinlihu.schedule.factory.excutestatus.ScheduleExcuteStatus;
import club.yinlihu.schedule.factory.excutestatus.ScheduleExcuteStatusFactory;
import club.yinlihu.schedule.init.ScheduleInitialization;
import club.yinlihu.schedule.persist.SchedulePersist;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 执行成功类
 */
public class SuccessStatusImpl extends ScheduleExcuteStatus {
    private static final Logger LOG = LoggerFactory.getLogger(SuccessStatusImpl.class);

    private ScheduleProcessContext context = ScheduleInitialization.getInstance().getContext();

    private SchedulePersist persist = ScheduleInitialization.getInstance().getSchedule();

    public SuccessStatusImpl() {
        ScheduleExcuteStatusFactory.register(ScheduleExcuteStatusEnum.SUCESS, this);
    }

    public ConfigResult doBefore(String configId) {
        return null;
    }

    public ConfigResult doNow(String configId) {
        return null;
    }

    public ConfigResult doAfter(String configId) {
        Map<String, Object> processContext = context.getProcessContext(configId);
        ScheduleProcessExcuteStatus excuteStatus = (ScheduleProcessExcuteStatus)processContext.get(ScheduleConstant.EXCUTE_STATUS);

        // 手动和自动的触发方式不同: 手动的是需要人为代码里进行触发
        Schedule schedule = persist.getSchedule(excuteStatus.getScheduleName());
        if (ScheduleTypeEnum.AUTO.getType().equalsIgnoreCase(schedule.getScheduleType())) {
            ScheduleExcute.getInstance().excute(configId);
        }
        return ConfigResult.ok();
    }

    public ConfigResult doEnd(String configId) {
        LOG.info("ConfigId:{}，任务执行完成", configId);
        // TODO：上下文信息以后需要存放到某个地方，用于以后读取
        // TODO：以后得实现依赖任务触发
        context.delProcessContext(configId);
        return ConfigResult.ok();
    }
}
