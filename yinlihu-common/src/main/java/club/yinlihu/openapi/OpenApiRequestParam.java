package club.yinlihu.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Open-API请求参数
 */
public class OpenApiRequestParam {
    @NotBlank(message="SecretId不能为空!")
    @JsonProperty(value = "SecretId")
    private String SecretId;

    @NotBlank(message="Timestamp不能为空!")
    @JsonProperty(value = "Timestamp")
    private String Timestamp;

    @NotBlank(message="Signature不能为空!")
    @JsonProperty(value = "Signature")
    private String Signature;

    @NotBlank(message="apiMethod不能为空!")
    @JsonProperty(value = "apiMethod")
    private String apiMethod;

    @NotBlank(message="版本不能为空!")
    @JsonProperty(value = "version")
    private String version;

    @NotBlank(message="platform不能为空!")
    @JsonProperty(value = "platform")
    private String platform;

    @NotBlank(message="data不能为空!")
    private String data;

    public String getSecretId() {
        return SecretId;
    }

    public void setSecretId(String SecretId) {
        this.SecretId = SecretId;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
