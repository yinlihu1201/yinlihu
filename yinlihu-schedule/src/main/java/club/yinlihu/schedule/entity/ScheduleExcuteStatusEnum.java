package club.yinlihu.schedule.entity;

/**
 * 执行实体类
 */
public enum ScheduleExcuteStatusEnum {
    WAITSTART("0", "未执行"),
    SUCESS("1", "成功"),
    FAIL("2","失败"),
    STOP("3","暂停"),
    EXCUTING("4","执行中"),
    TERMINATE("5","终止");

    private String code;
    private String desc;

    private ScheduleExcuteStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
