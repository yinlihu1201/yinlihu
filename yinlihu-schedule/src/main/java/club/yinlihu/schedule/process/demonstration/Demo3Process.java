package club.yinlihu.schedule.process.demonstration;

import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.process.ScheduleProcess;

public class Demo3Process extends ScheduleProcess {
    public ScheduleExcuteStatusEnum excute(String configId) {
        System.out.println("Demo3Process" + configId);
        return ScheduleExcuteStatusEnum.SUCESS;
    }
}
