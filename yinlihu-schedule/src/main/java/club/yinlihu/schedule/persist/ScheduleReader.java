package club.yinlihu.schedule.persist;

import club.yinlihu.schedule.entity.ScheduleEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 调度读取
 */
public class ScheduleReader {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleReader.class);

    public static List<ScheduleEntity> readSchedule(){
        FileInputStream is = null;
        try {
            is = new FileInputStream(getScheduleFile());
            // InputStream is = this.getClass().getResourceAsStream("/schedule.json");
            String scheduleJSON = IOUtils.toString(is, "utf-8");

            List<ScheduleEntity> scheduleEntities = JSONObject.parseArray(scheduleJSON, ScheduleEntity.class);
            if (scheduleEntities == null) {
                scheduleEntities = new ArrayList<ScheduleEntity>();
            }
            return scheduleEntities;
        } catch (IOException e) {
            LOG.error("读取文件错误,schedule.json不存在！",e);
            return new ArrayList<ScheduleEntity>();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOG.error("读取文件错误,关闭文件失败！",e);
            }
        }
    }

    public static File getScheduleFile() {
        File file = new File("C:\\Users\\Administrator\\Desktop\\shedule.json");
        return file;
    }
}
