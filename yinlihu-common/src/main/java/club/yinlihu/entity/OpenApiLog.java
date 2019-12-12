package club.yinlihu.entity;

/**
 * open-api日志
 */
public class OpenApiLog {
    // 版本
    private String version;
    // 平台
    private String platform;
    // 方法名
    private String methodName;
    // 类名
    private String className;
    // 请求参数
    private String requestParam;
    // 返回参数
    private String returnResult;
    // 总耗时
    private long totalTime;
    // 开始时间
    private long startTime;
    // 结束时间
    private long entTime;

    public String getRequestParam() {
        return requestParam;
    }
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
    public String getReturnResult() {
        return returnResult;
    }
    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }
    public long getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEntTime() {
        return entTime;
    }
    public void setEntTime(long entTime) {
        this.entTime = entTime;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }


}
