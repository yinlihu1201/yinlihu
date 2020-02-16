package club.yinlihu.schedule.entity;

/**
 * 配置结果
 */
public class ConfigResult {

    private static final String SUCCESS = "0";
    private static final String FAIL = "1";

    private String code = SUCCESS;

    private String msg = "success";

    private Object data;

    private ConfigResult(){}

    public static ConfigResult ok(){
        return result(SUCCESS, "success", null);
    }
    public static ConfigResult ok(String msg){
        return result(SUCCESS, msg, null);
    }
    public static ConfigResult ok(String msg, Object data){
        return result(SUCCESS, msg, data);
    }

    public static ConfigResult fail(){
        return result(FAIL, "fail", null);
    }
    public static ConfigResult fail(String msg){
        return result(FAIL, msg, null);
    }
    public static ConfigResult fail(String msg, Object data){
        return result(FAIL, msg, data);
    }

    public static ConfigResult result(String code, String msg, Object data){
        ConfigResult configResult = new ConfigResult();
        configResult.setCode(code);
        configResult.setMsg(msg);
        configResult.setData(data);
        return configResult;
    }

    public boolean isSuccess(){
        return SUCCESS.equalsIgnoreCase(this.getCode());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
