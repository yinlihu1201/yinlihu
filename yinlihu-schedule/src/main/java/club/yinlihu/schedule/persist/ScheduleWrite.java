package club.yinlihu.schedule.persist;

import club.yinlihu.schedule.entity.ScheduleEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * schedule persist interface
 */
public class ScheduleWrite {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleWrite.class);
    /**
     * save scheudule
     * 保存在
     */
    public static void saveSchedule(ScheduleEntity scheduleEntity) {
        LOG.info("save schedule {}",JSONObject.toJSONString(scheduleEntity));
        List<ScheduleEntity> scheduleEntities = ScheduleReader.readSchedule();
        scheduleEntities.add(scheduleEntity);

        // 将List写入文件中
        File scheduleFile = ScheduleReader.getScheduleFile();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(scheduleFile);
            fileOutputStream.write(JSONObject.toJSONString(scheduleEntities).getBytes());
        } catch (IOException e) {
            LOG.error("写入文件失败！",e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                LOG.error("写入文件失败！",e);
            }
        }
    }
}
