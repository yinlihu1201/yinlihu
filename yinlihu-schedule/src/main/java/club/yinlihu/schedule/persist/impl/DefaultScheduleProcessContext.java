package club.yinlihu.schedule.persist.impl;

import club.yinlihu.schedule.exception.ScheduleExcetion;
import club.yinlihu.schedule.persist.ScheduleProcessContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务上下文信息
 */
public class DefaultScheduleProcessContext implements ScheduleProcessContext {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultScheduleProcessContext.class);

    private static final String LOG_MAP = "schedule_config_log";

    // 配置上下文信息
    public static Map<String, Map<String, Object>> processContextMap = new HashMap<String, Map<String, Object>>();

    /**
     * 保存上下文信息
     */
    public void saveProcessContext(String configId, Map<String, Object> context){
        if (context == null) {
            throw new ScheduleExcetion("process context is null!");
        }
        processContextMap.put(configId, context);
    }

    /**
     * 获取配置上下文信息
     * @param configId
     * @return
     */
    public Map<String, Object> getProcessContext(String configId){
        Map<String, Object> context = processContextMap.get(configId);
        if (context == null) {
            context = new HashMap<String, Object>();
        }
        return context;
    }

    /**
     * 删除上下文信息
     * @param configId
     */
    public void delProcessContext(String configId){
        processContextMap.remove(configId);
    }

    public void printLog(String configId, String log) {
        Map<String, Object> configMap = processContextMap.get(configId);
        Object scheduleLog = configMap.get(LOG_MAP);
        StringBuffer sBuffer;
        if (scheduleLog == null) {
            sBuffer = new StringBuffer();
        } else {
            sBuffer = new StringBuffer(String.valueOf(scheduleLog));
        }
        sBuffer.append(log);
        configMap.put(LOG_MAP, sBuffer.toString());
    }

    public String getLog(String configId) {
        Map<String, Object> configMap = processContextMap.get(configId);
        Object scheduleLog = configMap.get(LOG_MAP);
        return String.valueOf(scheduleLog);
    }

    public void clearLog(String configId) {
        Map<String, Object> configMap = processContextMap.get(configId);
        if (configMap == null) {
            return;
        }
        processContextMap.get(configId).remove(LOG_MAP);
    }


    private DefaultScheduleProcessContext(){}

    private static ScheduleProcessContext context = new DefaultScheduleProcessContext();

    public static ScheduleProcessContext getInstance(){
        return context;
    }
}
