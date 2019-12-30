package club.yinlihu.schedule.exception;

/**
 * 异常
 */
public class ScheduleExcetion extends RuntimeException {
    public ScheduleExcetion() {
    }

    public ScheduleExcetion(String message) {
        super(message);
    }

    public ScheduleExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public ScheduleExcetion(Throwable cause) {
        super(cause);
    }

    public ScheduleExcetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
