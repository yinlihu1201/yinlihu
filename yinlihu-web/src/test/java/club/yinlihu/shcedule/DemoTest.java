package club.yinlihu.shcedule;

import club.yinlihu.schedule.persist.ScheduleReader;
import org.junit.Test;

public class DemoTest {

    @Test
    public void demo() {
        ScheduleReader read = new ScheduleReader();
        System.out.println(read.readSchedule());
    }
}
