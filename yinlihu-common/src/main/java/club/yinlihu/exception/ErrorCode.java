package club.yinlihu.exception;

/**
 * 错误编码及提示
 */
public enum ErrorCode {
    BIZ_ERROR("10001","系统异常！"),
    OPEN_API_REPEAT_ERROR("10002","open-api方法重复注入！"),
    OPEN_API_ERROR("10003","open-api方法注入异常！"),
    OPEN_API_HEADER_ERROR("10004","open-api请求头信息错误！"),
    OPEN_API_TIMEOUT_ERROR("10005","open-api请求超时！"),
    OPEN_API_VERSION_ERROR("10006","open-api方法版本不存在！"),
    OPEN_API_NOT_EXISTS_ERROR("10006","open-api方法不存在或者此版本无此方法！"),
    OPEN_API_PARAM_ERROR("10006","open-api传参错误！"),
    SYS_ERROR("99999","业务异常！");
    ;
    /** 错误码 */
    private final String code;

    /** 描述 */
    private final String desc;

    /**
     * @param code 错误码
     * @param desc 描述
     */
    private ErrorCode(final String code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
