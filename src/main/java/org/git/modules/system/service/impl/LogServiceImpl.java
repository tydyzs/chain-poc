package org.git.modules.system.service.impl;

import lombok.AllArgsConstructor;
import org.git.modules.system.service.ILogService;
import org.git.modules.system.service.ILogUsualService;
import org.git.core.log.model.LogApi;
import org.git.core.log.model.LogError;
import org.git.core.log.model.LogUsual;
import org.git.modules.system.service.ILogApiService;
import org.git.modules.system.service.ILogErrorService;
import org.springframework.stereotype.Service;

/**
 * Created by Chain.
 *
 * @author zhuangqian
 */
@Service
@AllArgsConstructor
public class LogServiceImpl implements ILogService {

	ILogUsualService usualService;
	ILogApiService apiService;
	ILogErrorService errorService;

	@Override
	public Boolean saveUsualLog(LogUsual log) {
		return usualService.save(log);
	}

	@Override
	public Boolean saveApiLog(LogApi log) {
		return apiService.save(log);
	}

	@Override
	public Boolean saveErrorLog(LogError log) {
		return errorService.save(log);
	}

}
