package club.yinlihu.schedule.persist;

import club.yinlihu.schedule.exception.ScheduleExcetion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 调度读取
 */
public class ScheduleQueue {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleQueue.class);

    private CountDownLatch latch= new CountDownLatch(1);
    private Semaphore semaphore = new Semaphore(10);

    private Queue<String> configIdQueue = new ConcurrentLinkedQueue<String>();

    // 控制队列数量

    public synchronized void add(String configId){
        boolean addFlag = configIdQueue.add(configId);

        // TODO：存放失败的进行记录
        if (!addFlag) {
            LOG.info("添加队列失败！{}", configId);
            return;
        }

        try {
            semaphore.acquire();
            latch.countDown();
        } catch (InterruptedException e) {
            LOG.error("添加队列异常！", e);
            throw new ScheduleExcetion("添加队列异常！");
        }
    }

    public synchronized String poll(){
        if (configIdQueue.size() <= 0) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new ScheduleExcetion(e);
            }
        }

        String configId = configIdQueue.poll();
        semaphore.release();
        return configId;
    }

    public int size() {
        return configIdQueue.size();
    }
}
