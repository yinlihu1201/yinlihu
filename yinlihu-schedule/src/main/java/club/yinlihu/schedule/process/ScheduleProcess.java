package club.yinlihu.schedule.process;

import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.persist.ScheduleRegister;
import org.reflections.Reflections;

import java.util.Set;

/**
 * 任务调度接口
 */
public abstract class ScheduleProcess {

    static {
        Reflections reflections = new Reflections("club.yinlihu.schedule.process.impl");
        Set<Class<? extends ScheduleProcess>> classSet = reflections.getSubTypesOf(ScheduleProcess.class);
        for (Class clazz : classSet) {
            ScheduleRegister.register(clazz);
        }
    }

    /**
     * 执行
     * @param configId 配置唯一标识
     * @return
     */
    public abstract ScheduleExcuteStatusEnum excute(String configId);
}
