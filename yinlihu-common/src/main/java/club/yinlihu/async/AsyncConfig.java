package club.yinlihu.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 支持@Async异步执行
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncConfig.class);

    public static final ThreadPoolTaskExecutor asyncServiceExecutor = new ThreadPoolTaskExecutor();

    @Bean("asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        LOG.info("start asyncServiceExecutor");
        //配置核心线程数
        asyncServiceExecutor.setCorePoolSize(100);
        //配置最大线程数
        asyncServiceExecutor.setMaxPoolSize(200);
        //配置队列大小
        asyncServiceExecutor.setQueueCapacity(20000);
        //配置线程池中的线程的名称前缀
        asyncServiceExecutor.setThreadNamePrefix("yinlihu-async-service-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        asyncServiceExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        asyncServiceExecutor.initialize();
        return asyncServiceExecutor;
    }

    /**
     * 线程可视化
     */
    public void printThreadPoolLog() {
        ThreadPoolExecutor threadPoolExecutor = asyncServiceExecutor.getThreadPoolExecutor();
        LOG.info("taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }
}
