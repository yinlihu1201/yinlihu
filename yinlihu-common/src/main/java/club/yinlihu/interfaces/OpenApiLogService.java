package club.yinlihu.interfaces;

import club.yinlihu.entity.OpenApiLog;
import org.springframework.scheduling.annotation.Async;

public interface OpenApiLogService {
    /**
     * open-api日志文件保存
     * 异步保存
     */
    @Async("asyncServiceExecutor")
    void saveLog(OpenApiLog log);
}
