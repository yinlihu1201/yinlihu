package club.yinlihu.schedule.factory.excutestatus;

import club.yinlihu.schedule.entity.ScheduleConstant;
import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.exception.ScheduleExcetion;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 任务执行工厂类
 */
public class ScheduleExcuteStatusFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleExcuteStatusFactory.class);

    private static Map<String, ScheduleExcuteStatus> excuteStatusMap = new HashMap<String, ScheduleExcuteStatus>();

    static{
        try {
            Reflections reflections = new Reflections(ScheduleConstant.EXCUTE_FACTORY_SCAN_PACKAGE);
            Set<Class<? extends ScheduleExcuteStatus>> classSet = reflections.getSubTypesOf(ScheduleExcuteStatus.class);
            for (Class clazz : classSet) {
                clazz.newInstance();
            }
        }  catch (Exception e) {
            throw new ScheduleExcetion(e);
        }
    }

    /**
     * 注册
     */
    public static void register(ScheduleExcuteStatusEnum status, ScheduleExcuteStatus statusObj){
        excuteStatusMap.put(status.getCode(), statusObj);
        LOG.info("register schedule excute status {}", status.getDesc());
    }

    /**
     * 获取对象
     * @param status
     * @return
     */
    public static ScheduleExcuteStatus getInstance(String status){
        ScheduleExcuteStatus scheduleExcuteStatus = excuteStatusMap.get(status);
        return scheduleExcuteStatus;
    }
}
