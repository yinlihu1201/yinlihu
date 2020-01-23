package club.yinlihu.schedule.process.impl;

import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.process.ScheduleProcess;

public class Demo3Process extends ScheduleProcess {
    public ScheduleExcuteStatusEnum excute(String configId) {
        System.out.println("Demo2Process" + configId);
        return ScheduleExcuteStatusEnum.FAIL;
    }
}
