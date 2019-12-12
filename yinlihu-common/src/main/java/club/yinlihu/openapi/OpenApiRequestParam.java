package club.yinlihu.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Open-API请求参数
 */
public class OpenApiRequestParam {

    @NotBlank(message = "api不可为空")
    @JsonProperty(value = "api")
    private String api;
    @NotBlank(message = "版本不可为空")
    @JsonProperty(value = "version")
    private String version;
    @NotBlank(message = "数据不可为空，并且是json格式的字符")
    @JsonProperty(value = "data")
    private String data;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getApi() {
        return api;
    }
    public void setApi(String api) {
        this.api = api;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OpenApiRequestParam{" +
                "api='" + api + '\'' +
                ", version='" + version + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
