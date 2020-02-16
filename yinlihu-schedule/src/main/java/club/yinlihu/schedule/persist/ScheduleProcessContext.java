package club.yinlihu.schedule.persist;

import java.util.Map;

/**
 * 任务上下文信息
 */
public interface ScheduleProcessContext {
    /**
     * 保存上下文信息
     */
    void saveProcessContext(String configId, Map<String, Object> context);

    /**
     * 获取配置上下文信息
     * @param configId
     * @return
     */
    Map<String, Object> getProcessContext(String configId);

    /**
     * 删除上下文信息
     * @param configId
     */
    void delProcessContext(String configId);

    /**
     * 日志输出
     * @param log
     */
    void printLog(String configId, String log);

    /**
     * 获取日志
     * @param configId
     * @return
     */
    String getLog(String configId);

    /**
     * 清除日志
     * @param configId
     */
    void clearLog(String configId);
}
