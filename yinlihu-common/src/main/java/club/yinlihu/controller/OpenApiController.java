package club.yinlihu.controller;

import club.yinlihu.constants.CommonConstants;
import club.yinlihu.entity.OpenApiLog;
import club.yinlihu.entity.ResultMap;
import club.yinlihu.exception.BizException;
import club.yinlihu.exception.ErrorCode;
import club.yinlihu.interfaces.OpenApiLogService;
import club.yinlihu.openapi.OpenApiParse;
import club.yinlihu.openapi.OpenApiRequestParam;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公共调用类
 */
@RestController
public class OpenApiController implements ApplicationContextAware {
	private static final Logger LOG = LoggerFactory.getLogger(OpenApiController.class);

	@Autowired
	private OpenApiParse openApiParse;

	// 应用上下文
	private ApplicationContext context;
	@Value("${openapi.log-service}")
	private String openApiLogServiceConfig;

	/**
	 * 公共调用操作api,调用参数如下，apiMethod是ApiMethod对应的method
	 *
	 }
	 * @param requestParam
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "api")
	public ResultMap api(HttpServletRequest request, @RequestBody @Validated OpenApiRequestParam requestParam, BindingResult bindingResult) {
		ResultMap resultMap;
		long start = System.currentTimeMillis();
		resultMap = checkParams(bindingResult);
		if (!ResultMap.isSuccess(resultMap)) {
			return resultMap;
		}

		// 校验头信息
		resultMap = this.checkedHeader(request);
		if (!ResultMap.isSuccess(resultMap)) {
			return resultMap;
		}

		LOG.info("request api request param {}", JSONObject.toJSONString(requestParam));
		ResultMap result = openApiParse.execute(request, requestParam);
		long end = System.currentTimeMillis();
		LOG.info("request api end,result {}, total time {}", JSONObject.toJSONString(result), end - start);

		saveApiLog(start,end,requestParam,result,request);
		return result;
	}

	/**
	 * 保存APIlog
	 */
	private void saveApiLog(long start, long end ,OpenApiRequestParam requestParam, ResultMap result, HttpServletRequest request) {
		// 这里可以异步保存API调用请求参数和返回参数
		long total = end - start;
		OpenApiLog log = new OpenApiLog();
		log.setClassName(String.valueOf(request.getAttribute(CommonConstants.OPEN_API_CLASS_NAME)));
		log.setMethodName(String.valueOf(request.getAttribute(CommonConstants.OPEN_API_METHOD_NAME)));
		log.setRequestParam(JSONObject.toJSONString(requestParam));
		log.setReturnResult(JSONObject.toJSONString(result));
		log.setVersion(requestParam.getVersion());
		log.setPlatform(request.getHeader(CommonConstants.OPEN_API_REQUEST_HEADER_PLATFORM));
		log.setEntTime(end);
		log.setStartTime(start);
		log.setTotalTime(total);
		request.removeAttribute(CommonConstants.OPEN_API_CLASS_NAME);
		request.removeAttribute(CommonConstants.OPEN_API_METHOD_NAME);
		if (StringUtils.isBlank(openApiLogServiceConfig)) {
			throw new BizException("日志保存类错误！");
		}
		OpenApiLogService openApiLogService = context.getBean(openApiLogServiceConfig, OpenApiLogService.class);
		if (openApiLogService == null) {
			throw new BizException(openApiLogServiceConfig + ",spring上下文中无此对象");
		}
		// 获取用户自定义的
		openApiLogService.saveLog(log);
	}

	/**
	 * 参数信息校验
	 * @param bindingResult
	 * @return
	 */
	private ResultMap checkParams(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for (ObjectError error : errorList) {
				return ResultMap.fail(error.getDefaultMessage());
			}
		}
		return ResultMap.ok();
	}

	/**
	 * 校验头部信息
	 * @return
	 */
	private ResultMap checkedHeader(HttpServletRequest request) {
		// 校验头部信息
		String platform = request.getHeader(CommonConstants.OPEN_API_REQUEST_HEADER_PLATFORM);
		String timestamp = request.getHeader(CommonConstants.OPEN_API_REQUEST_HEADER_TIMESTAMP);
		String token = request.getHeader(CommonConstants.OPEN_API_REQUEST_HEADER_TOKEN);

		if (StringUtils.isAnyBlank(platform,timestamp,token)) {
			return ResultMap.fail(ErrorCode.OPEN_API_HEADER_ERROR);
		}

		if (!NumberUtils.isParsable(timestamp)) {
			return ResultMap.fail(ErrorCode.OPEN_API_HEADER_ERROR);
		}

		long l = NumberUtils.toLong(timestamp);
		/*if ((System.currentTimeMillis() - l) > 300000) {
			return ResultMap.fail(ErrorCode.OPEN_API_TIMEOUT_ERROR);
		}*/

		// 校验token

		return ResultMap.ok();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
