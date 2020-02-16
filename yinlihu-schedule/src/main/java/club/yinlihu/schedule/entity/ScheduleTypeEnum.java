package club.yinlihu.schedule.entity;

public enum ScheduleTypeEnum {
    AUTO("1", "自动流程"),
    MANUAL("2", "手动流程");

    private String type;

    private String desc;

    ScheduleTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
