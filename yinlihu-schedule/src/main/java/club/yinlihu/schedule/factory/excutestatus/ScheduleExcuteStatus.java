package club.yinlihu.schedule.factory.excutestatus;

import club.yinlihu.schedule.entity.ConfigResult;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;

/**
 * 任务执行方法
 */
public abstract class ScheduleExcuteStatus {
    /**
     * 前置
     */
    public abstract ConfigResult doBefore(String configId);

    /**
     * 当前
     */
    public abstract ConfigResult doNow(String configId);

    /**
     * 后置
     */
    public abstract ConfigResult doAfter(String configId);

    /**
     * 结束
     */
    public abstract ConfigResult doEnd(String configId);
}
