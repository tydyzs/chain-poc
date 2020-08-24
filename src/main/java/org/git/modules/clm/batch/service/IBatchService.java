package org.git.modules.clm.batch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

public interface IBatchService extends IService<T> {

	String btCreditRecount();

	String btGuaranteeRecount();

	String btThirdRecount();

	String btCreditStatis();

	String btCcStatis();

}
