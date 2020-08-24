package org.git.modules.clm.batch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

public interface IBatchDataService extends IService<T> {
	//合同信息
	String contractDataHandle();
	//批复信息
	String approveDataHandle();
	//借据信息
	String summaryDataHandle();
	//项目协议信息
	String projectDataHandle();
	//实时还款信息
	String recoveryEventDataHandle();

	//银票签发
	String billDataHandle();

	//信用卡
	String ccDataHandle();

}
