package club.yinlihu.openapi;

/**
 * openApi版本
 */
public enum OpenApiVersion {
    V1("v1"),
    V2("v2"),
    V3("v3");
    ;
    private String version;
    OpenApiVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }
}
