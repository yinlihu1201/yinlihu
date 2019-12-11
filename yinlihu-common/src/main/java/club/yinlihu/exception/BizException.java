package club.yinlihu.exception;

/**
 * 自定义异常
 */
public class BizException extends RuntimeException {

    // 错误码记录
    protected ErrorCode errorCode;

    public BizException(){
        super(ErrorCode.BIZ_ERROR.getDesc());
        this.errorCode = ErrorCode.BIZ_ERROR;
    }

    public BizException(final ErrorCode errorCode){
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public BizException(final String errorMessage){
        super(errorMessage);
        this.errorCode = ErrorCode.BIZ_ERROR;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
