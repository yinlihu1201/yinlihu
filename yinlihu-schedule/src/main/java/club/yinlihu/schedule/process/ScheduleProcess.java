package club.yinlihu.schedule.process;

import club.yinlihu.schedule.entity.ScheduleExcuteStatusEnum;
import club.yinlihu.schedule.exception.ScheduleExcetion;
import org.reflections.Reflections;

import java.util.Set;

/**
 * 任务调度接口
 */
public abstract class ScheduleProcess {

    /**
     * 包初始化位置:如果没有用spring这种进行类的初始化的话，调用此方法进行初始化
     * 但是如果使用了spring进行对象的管理，则此类不进行调用
     * @param schedulePackagePath 指定任务调度的代码位置
     */
    public static void init(String schedulePackagePath) {
        try {
            Reflections reflections = new Reflections(schedulePackagePath);
            Set<Class<? extends ScheduleProcess>> classSet = reflections.getSubTypesOf(ScheduleProcess.class);
            for (Class clazz : classSet) {
                clazz.newInstance();
            }
        } catch (ScheduleExcetion se) {
            throw se;
        } catch (Exception e) {
            throw new ScheduleExcetion("init process error!");
        }
    }

    protected ScheduleProcess(){
        Class<? extends ScheduleProcess> aClass = this.getClass();
        ScheduleRegister.register(aClass, this);
    }

    /**
     * 执行
     * @param configId 配置唯一标识
     * @return
     */
    public abstract ScheduleExcuteStatusEnum excute(String configId);
}
