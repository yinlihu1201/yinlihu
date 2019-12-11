package club.yinlihu.exception;

/**
 * 错误编码及提示
 */
public enum ErrorCode {
    BIZ_ERROR("10001","系统异常！"),
    OPEN_API_REPEAT_ERROR("10002","open-api方法重复注入！"),
    OPEN_API_ERROR("10003","open-api方法注入异常！"),
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
