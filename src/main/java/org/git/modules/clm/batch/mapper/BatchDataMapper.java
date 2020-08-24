package org.git.modules.clm.batch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.batch.vo.TtCrdBillInfoSourceVO;
import org.git.modules.clm.front.dto.jxrcb.loan.*;

import java.util.HashMap;
import java.util.List;

public interface BatchDataMapper extends BaseMapper<T> {
	//合同
	List<HashMap> listContractNum();
	List<SubContractInfoRequestDTO> listSubContractInfo(String contract_num);
	List<PledgeInfoRequestDTO> listSuretyInfo(String subcontract_num);
	List<SuretyInfoRequestDTO> listSuretyPersonInfo(String subcontract_num);
    //批复
	List<HashMap> listApproveNum();
	List<LimitDetailInfoRequestDTO> listApproveDetailInfo(String approveNum);
	//借据
	List<HashMap> listSummaryNum();
	//项目协议
	List<HashMap> listProjectNum();
	//实时还款信息
	List<HashMap> listrecoveryEventNum();
	//银票
	List<HashMap> listContractNumOfBill();
	List<BillInfoRequestDTO> listBillInfo(String contract_num);
	List<HashMap> getHeadInfo(String contract_num);
	String isEqualsBillInfo(TtCrdBillInfoSourceVO vo);

	//信用卡
	void ccDataInsertOrUpdate(String optDate);
}
