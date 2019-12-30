package club.yinlihu.schedule.persist;

import club.yinlihu.schedule.entity.ScheduleEntity;
import club.yinlihu.schedule.entity.ScheduleTask;
import club.yinlihu.schedule.entity.ScheduleTaskLinks;
import club.yinlihu.schedule.exception.ScheduleExcetion;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度读取
 */
public class SchedulePersist {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulePersist.class);

    protected static List<ScheduleEntity> scheduleEntities;

    public static Map<String, ScheduleEntity> scheduleEntityMap = new HashMap<String, ScheduleEntity>();

    public static void init() {
        List<ScheduleTask> taskList = new ArrayList<ScheduleTask>();

        ScheduleTask task = new ScheduleTask("task1","任务1");
        taskList.add(task);

        ScheduleTask task2 = new ScheduleTask("task2","任务2");
        taskList.add(task2);

        ScheduleTaskLinks links = new ScheduleTaskLinks();
        links.setFrom(task);
        links.setTo(task2);

        ScheduleEntity se = new ScheduleEntity("schedule1", "调度1", taskList);

        List<ScheduleTaskLinks> linkList = new ArrayList<ScheduleTaskLinks>();
        linkList.add(links);
        se.setScheduleTaskLinks(linkList);

        scheduleEntities = new ArrayList<ScheduleEntity>();
        scheduleEntities.add(se);

        if (scheduleEntityMap.containsKey(se.getScheduleName())) {
            throw new ScheduleExcetion("调度名重复！");
        }
        scheduleEntityMap.put(se.getScheduleName(),se);
    }

    public static List<ScheduleEntity> readSchedule(){
        if (scheduleEntities == null) {
            init();
        }
        return scheduleEntities;
    }

    /**
     * save scheudule
     * 保存在
     */
    public static void saveSchedule(ScheduleEntity scheduleEntity) {
        LOG.info("save schedule {}",JSONObject.toJSONString(scheduleEntity));
        List<ScheduleEntity> scheduleEntities = SchedulePersist.readSchedule();
        scheduleEntities.add(scheduleEntity);

        refresh();
    }

    public static void refresh() {

    }

    public static ScheduleEntity readScheduleEntity(String taskName) {
        ScheduleEntity scheduleEntity = scheduleEntityMap.get(taskName);
        if (scheduleEntity == null) {
            throw new ScheduleExcetion("");
        }
        return scheduleEntity;
    }
}
