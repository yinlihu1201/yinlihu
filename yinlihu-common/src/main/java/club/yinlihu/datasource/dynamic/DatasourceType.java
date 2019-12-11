package club.yinlihu.datasource.dynamic;

/**
 * 数据源类型
 * @author yinlihu
 * @create 2019/12/9
 */
public enum DatasourceType{

    MASTER("master", "主数据库"),
    CLUSTER("cluster", "从数据库");
    ;
    private String type;
    private String desc;

    DatasourceType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
