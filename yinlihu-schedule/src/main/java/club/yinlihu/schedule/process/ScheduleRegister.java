package club.yinlihu.schedule.process;

import club.yinlihu.schedule.exception.ScheduleExcetion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度注册/读取
 */
public class ScheduleRegister {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleRegister.class);

    private static Map<String, ScheduleProcess> processMap = new HashMap<String, ScheduleProcess>();

    /**
     * 注册
     * @param <T>
     */
    public static <T> void register(Class clazz, ScheduleProcess scheduleProcess) {
        String className = clazz.getSimpleName();
        if (processMap.get(className) != null) {
            throw new ScheduleExcetion(className + " repeat register schedule, please check your code!");
        }
        try {
            LOG.info("register class className {}", className);
            processMap.put(className, scheduleProcess);
        } catch (Exception e) {
            throw new ScheduleExcetion("register instance fail!",e);
        }
    }

    /**
     * 获取配置流程
     * @param processName
     * @return
     */
    public static ScheduleProcess getProcessInstance(String processName){
        ScheduleProcess process = processMap.get(processName);
        if (process == null) {
            throw new ScheduleExcetion("no process named " + processName);
        }
        return process;
    }
}
