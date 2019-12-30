package club.yinlihu.shcedule;

import club.yinlihu.schedule.persist.SchedulePersist;
import org.junit.Test;

public class DemoTest {

    @Test
    public void demo() {
        SchedulePersist read = new SchedulePersist();
        System.out.println(read.readSchedule());
    }
}
