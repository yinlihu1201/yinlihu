package club.yinlihu.schedule.execute;

import club.yinlihu.schedule.entity.ScheduleProcessExcuteStatus;
import club.yinlihu.schedule.persist.ScheduleQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务调度
 */
public class ScheduleListener {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleListener.class);

    private static ScheduleQueue scheduleQueue = new ScheduleQueue();

    /**
     * 触发任务
     */
    public static void listener() {
        // 这里使用线程进行控制
        while(true) {
            String configId = scheduleQueue.poll();
            LOG.info("处理任务{}" , configId);
            if (StringUtils.isBlank(configId)) {
                continue;
            }
            ScheduleExcute.excute(configId);
        }
    }
}
