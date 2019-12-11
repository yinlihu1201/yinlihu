package club.yinlihu.controller;

import com.alibaba.fastjson.JSONObject;
import com.suning.spcp.paas.dto.sncloud.SNCloudCallResult;
import com.suning.spcp.paas.dto.sncloud.SnCloudApiJobRequestParam;
import com.suning.spcp.paas.impl.service.sncloud.job.api.ApiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 公共调用类
 */
@RestController
@RequestMapping("sncloud/v1/api")
public class SnCloudJobApiController {
	private static final Logger LOG = LoggerFactory.getLogger(SnCloudJobApiController.class);

	@Autowired
	private ApiRepository apiRepository;

	/**
	 * 公共调用操作api,调用参数如下，apiMethod是ApiMethod对应的method
	 *
	 }
	 * @param snCloudApiJobRequestParam
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/actionMethod")
	public SNCloudCallResult<?> actionMethod(HttpServletRequest request, @RequestBody @Validated SnCloudApiJobRequestParam snCloudApiJobRequestParam, BindingResult bindingResult) {
		SNCloudCallResult<Map> callResult = new SNCloudCallResult<>();
		if (checkParams(callResult, bindingResult) != null) {
			return callResult;
		}
		LOG.info("request api success param {}", JSONObject.toJSONString(snCloudApiJobRequestParam));
		SNCloudCallResult result = apiRepository.executeAction(request, snCloudApiJobRequestParam);
		LOG.info("request api end,result {}", JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 公共调用查询api,调用参数如下，apiMethod是ApiMethod对应的method
	 *
	 }
	 * @param snCloudApiJobRequestParam
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/queryMethod")
	public SNCloudCallResult<?> queryMethod(HttpServletRequest request, @RequestBody @Validated SnCloudApiJobRequestParam snCloudApiJobRequestParam, BindingResult bindingResult) {
		SNCloudCallResult<Map> callResult = new SNCloudCallResult<>();
		if (checkParams(callResult, bindingResult) != null) {
			return callResult;
		}
		LOG.info("request api success param {}", JSONObject.toJSONString(snCloudApiJobRequestParam));
		SNCloudCallResult result = apiRepository.executeQuery(request, snCloudApiJobRequestParam);
		LOG.info("request api end,result {}", JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 参数信息校验
	 * @param callResult
	 * @param bindingResult
	 * @return
	 */
	private SNCloudCallResult<?> checkParams(SNCloudCallResult<?> callResult, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			callResult.setCode(SNCloudCallResult.CALLBACK_FAILED);
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for (ObjectError error : errorList) {
				callResult.setMessage(error.getDefaultMessage());
				break;
			}
			return callResult;
		} else {
			return null;
		}
	}
}
