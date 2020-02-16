package club.yinlihu.schedule.process.demonstration;

import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.process.ScheduleProcess;

public class DemoProcess extends ScheduleProcess {
    public ScheduleExcuteStatusEnum excute(String configId) {
        System.out.println("DemoProcess" + configId);
        return ScheduleExcuteStatusEnum.FAIL;
    }
}
