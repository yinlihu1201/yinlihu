import club.yinlihu.schedule.entity.ScheduleEntity;
import club.yinlihu.schedule.entity.ScheduleTask;
import club.yinlihu.schedule.persist.ScheduleReader;
import club.yinlihu.schedule.persist.ScheduleWrite;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DemoTest {

    @Test
    public void demo() {
        ScheduleReader read = new ScheduleReader();
        System.out.println(JSONObject.toJSONString(read.readSchedule()));
    }

    @Test
    public void write() {
        List<ScheduleTask> taskList = new ArrayList<ScheduleTask>();
        ScheduleTask task = new ScheduleTask("task1","任务1");

        taskList.add(task);
        ScheduleEntity se = new ScheduleEntity("schedule1", "调度1", taskList);
        ScheduleWrite.saveSchedule(se);
    }
}
