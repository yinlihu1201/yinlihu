package club.yinlihu.interfaces.impl;

import club.yinlihu.entity.OpenApiLog;
import club.yinlihu.interfaces.OpenApiLogService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("defaultOpenApiLogService")
public class OpenApiLogServiceImpl implements OpenApiLogService {

    private static final Logger LOG = LoggerFactory.getLogger(OpenApiLogServiceImpl.class);

    @Override
    public void saveLog(OpenApiLog log) {
        LOG.info("保存日志:{}",JSONObject.toJSONString(log));
    }
}
