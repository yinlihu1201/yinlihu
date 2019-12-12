package club.yinlihu.openapi;

import com.alibaba.druid.util.StringUtils;

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

    public static OpenApiVersion getVersion(String version) {
        for (OpenApiVersion v :OpenApiVersion.values()) {
            if (StringUtils.equals(v.getVersion(),version)) {
                return v;
            }
        }
        return null;
    }
}
