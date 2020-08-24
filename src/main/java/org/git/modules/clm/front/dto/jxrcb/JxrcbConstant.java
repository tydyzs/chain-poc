package org.git.modules.clm.front.dto.jxrcb;

import org.git.modules.clm.front.dto.jxrcb.bank.BankCreditDetailRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bank.BankCreditDetailResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.bank.LoanCreditDetailRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bank.LoanCreditQueryResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountRequestDTO;
import org.git.modules.clm.front.dto.jxrcb.bill.BillDiscountResponseDTO;
import org.git.modules.clm.front.dto.jxrcb.common.*;
import org.git.modules.clm.front.dto.jxrcb.ecif.*;
import org.git.modules.clm.front.dto.jxrcb.fund.*;
import org.git.modules.clm.front.dto.jxrcb.loan.*;

/**
 * 接口对象映射配置类
 */
public enum JxrcbConstant {

	//-----------以下为我方作为服务请求方Requester-------------
	//对公客户综合信息查询
	S00880000420202(ECIFCompanyCustomerRequestDTO.class, ECIFCompanyCustomerResponseDTO.class),
	//客户清单查询
	S00880000420301(ECIFCustomerListRequestDTO.class, ECIFCustomerListResponseDTO.class),
	//对私客户综合信息查询
	S00880000420102(ECIFPrivateCustomerRequestDTO.class, ECIFPrivateCustomerResponseDTO.class),
	//ecif实时-查询同业客户信息
	ST001(ECIFBankCustomerRequestDTO.class, ECIFBankCustomerResponseDTO.class),
	//信贷系统-产品信息查询
	S00070000681102(LoanBusinessTypeRequestDTO.class, LoanBusinessTypeResponseDTO.class),
	//信贷系统-对公客户信息查询
	S11080000693072(LoanCompanyCustomerRequestDTO.class, LoanCompanyCustomerResponseDTO.class),
	//资金-对账通知
	S00100000430200(FundAccountRequestDTO.class, FundAccountResponseDTO.class),

	//-----------以下为我方作为服务提供方Provider-------------
	//通用-客户合并
	S00870000259001(CommonCustomerMergeRequestDTO.class, CommonCustomerMergeResponseDTO.class),
	//通用-额度查询
	S00870000259002(CreditQueryRequestDTO.class, CreditQueryResponseDTO.class),
	//通用-三级额度查询
	S00870000259003(BankCreditDetailRequestDTO.class, BankCreditDetailResponseDTO.class),

	//-----------------资金系统----------------------
	//资金实时-额度授信
	S00870000259201(FundCreditExtensionRequestDTO.class, FundCreditExtensionResponseDTO.class),
	//资金实时-额度圈存
	S00870000259202(FundCreditDepositRequestDTO.class, FundCreditDepositResponseDTO.class),
	//资金实时-资金客户状态维护
	S00870000259203(FundCustomerStateRequestDTO.class, FundCustomerStateResponseDTO.class),
	//资金实时-额度占用转让
	S00870000259204(FundCreditOccupancyRequestDTO.class, FundCreditOccupancyResponseDTO.class),
	//资金实时-额度用信
	S00870000259205(FundCreditUseRequestDTO.class, FundCreditUseResponseDTO.class),
	//资金实时-日间批量用信
	S00870000259206(FundBatchRequestDTO.class, FundBatchResponseDTO.class),
	//资金实时-日间批量用信
	S00870000259207(FreeOfPaymentRequestDTO.class, FreeOfPaymentResponseDTO.class),
	//-----------------信贷系统----------------------
	//信贷系统-额度申请
	S00870000259101(LoanCreditApplyRequestDTO.class, LoanCreditApplyResponseDTO.class),
	//信贷系统-合同信息同步
	S00870000259102(LoanContractInfoRequestDTO.class, LoanContractInfoResponseDTO.class),
	//信贷系统-借据信息同步
	S00870000259103(LoanSummaryInfoRequestDTO.class, LoanSummaryInfoResponseDTO.class),
	//信贷系统-项目协议同步
	S00870000259104(LoanProjectRequestDTO.class, LoanProjectResponseDTO.class),
	//信贷系统-实时还款
	S00870000259105(LoanRepayRequestDTO.class, LoanRepayResponseDTO.class),
	//信贷系统-银票签发
	S00870000259106(LoanBillSignRequestDTO.class, LoanBillSignResponseDTO.class),
	//信贷系统-押品信息同步
	S00870000259107(LoanGuarantyInfoRequestDTO.class, LoanGuarantyInfoResponseDTO.class),
	//信贷系统-额度冻结解冻
	//S00870000259***(LoanCreditFreezeRequestDTO.class, LoanCreditFreezeResponseDTO.class),
	//信贷系统-担保合同信息同步
	//S00870000259***(LoanSubContractInfoRequestDTO.class, LoanSubContractInfoResponseDTO.class),

	//-----------------票据系统----------------------
	//票据实时-直贴、转帖用信交易
	S00870000259301(BillDiscountRequestDTO.class, BillDiscountResponseDTO.class),
	//票据额度查询
	S00870000259004(LoanCreditDetailRequestDTO.class, LoanCreditQueryResponseDTO.class),
	;


	public static final String ESB_STATUS_COMPLETE = "COMPLETE";//COMPLETE代表服务成功
	public static final String ESB_STATUS_FAIL = "FAIL";//FAIL代表服务失败

	public static final int ESB_CODE_SUCCESS = 0000;//FAIL代表服务处理成功
	public static final int ESB_CODE_FAIL = 10000;//报文与对象不匹配
	public static final int ESB_CODE_FAIL_F10101 = 10101;//额度申请接口错误码
	public static final int ESB_CODE_FAIL_F10201 = 10201;//合同同步接口错误码
	public static final int ESB_CODE_FAIL_F10301 = 10301;//借据同步接口错误码
	public static final int ESB_CODE_FAIL_F10601 = 10601;//银票签发接口错误码
	public static final int ESB_CODE_FAIL_F10401 = 10401;//银票签发接口错误码

	public static final int ESB_CODE_FAIL_F20102 = 20102;//ECIF对私客户信息查询错误码

	public static final int ESB_CODE_FAIL_F10701 = 10701;//押品信息同步错误码
	public static final int ESB_CODE_FAIL_F10801 = 10801;//担保合同信息同步错误码
	/**
	 * S00870000259302票据贴现，再贴，转贴业务错误码
	 */
	public static final int ESB_CODE_FAIL_F30200 = 30200;//额度预占用、占用、恢复申请处理异常
	public static final int ESB_CODE_FAIL_F30201 = 30201;//事件落地前凭证用信金额累加与用信金额不相等！
	public static final int ESB_CODE_FAIL_F30202 = 30202;//事件落地前数据重复校验不通过！
	public static final int ESB_CODE_FAIL_F30203 = 30203;//数据拆分前检查u通过
	public static final int ESB_CODE_FAIL_F30204 = 30204;//部分人行清算行号未找到客户编号
	public static final int ESB_CODE_FAIL_F30205 = 30205;//此接口不支持票据到期交易
	public static final int ESB_CODE_FAIL_F30206 = 30206;//额度产品不存在
	public static final int ESB_CODE_FAIL_F30207 = 30207;//额度明细不存在
	public static final int ESB_CODE_FAIL_F30208 = 30208;//业务产品信息不存在
	/**
	 * S008700002592031.2.3客户额度状态维护（冻结、准入）错误码
	 */
	public static final int ESB_CODE_FAIL_F20301 = 20301;//交易已存在，校验不通过。


	public static final String CLM_TERMINAL_ID = "0087";//授信额度终端代码

	public static final String ECIF_SERVICE_ID_P01 = "00880000420102";//ECIF对私客户信息查询服务代码
	public static final String ECIF_SERVICE_ID_C02 = "00880000420202";//ECIF对公客户信息查询服务代码
	public static final String ECIF_SERVICE_ID_L02 = "11080000693072";//信贷对公客户信息查询服务代码
	public static final String LOAN_SERVICE_ID_B01= "00070000681102";//信贷产品信息查询服务代码


	/**
	 * 业务准入状态为非准入！
	 */
	public static final int ESB_CODE_FAIL_F20501 = 20501;//业务准入状态为非准入！
	/**
	 * 凭证状态不支持该交易！
	 */
	public static final int ESB_CODE_FAIL_F20502 = 20502;//凭证状态不支持该交易！
	/**
	 * 额度状态为非生效！
	 */
	public static final int ESB_CODE_FAIL_F20503 = 20503;

	/**
	 * 凭证有效日期不在额度有效期内！
	 */
	public static final int ESB_CODE_FAIL_F20504 = 20504;

	/**
	 * 成员行切分额度剩余额度不足！
	 */
	public static final int ESB_CODE_FAIL_F20505 = 20505;
	/**
	 * 成员行授信额度剩余额度不足！
	 */
	public static final int ESB_CODE_FAIL_F20506 = 20506;
	/**
	 * 省联社切分额度剩余额度不足！
	 */
	public static final int ESB_CODE_FAIL_F20507 = 20507;
	/**
	 * 省联社授信额度剩余额度不足！
	 */
	public static final int ESB_CODE_FAIL_F20508 = 20508;
	/**
	 * 业务用信数组不能为空！
	 */
	public static final int ESB_CODE_FAIL_F20509 = 20509;
	/**
	 * 凭证数组不能为空！
	 */
	public static final int ESB_CODE_FAIL_F20510 = 20510;
	/**
	 * 凭证用信总金额与用信金额不一致！
	 */
	public static final int ESB_CODE_FAIL_F20511 = 20511;
	/**
	 * 该交易已处理！
	 */
	public static final int ESB_CODE_FAIL_F20512 = 20512;

	/**
	 * 接口方法调用异常
	 */
	public static final int ESB_CODE_FAIL_F20500 = 20500;

	/**
	 * 当前机构的明细额度为空
	 */
	public static final int ESB_CODE_FAIL_F20513 = 20513;
	/**
	 * 当前机构的省联社机构不存在
	 */
	public static final int ESB_CODE_FAIL_F20514 = 20514;
	/**
	 * 当前机构的省联社明细额度为空
	 */
	public static final int ESB_CODE_FAIL_F20515 = 20515;
	/**
	 * 当前机构的未对客户进行综合授信
	 */
	public static final int ESB_CODE_FAIL_F20516= 20516;
	/**
	 * 当前机构的省联社未对客户进行综合授信
	 */
	public static final int ESB_CODE_FAIL_F20517 = 20517;
	/**
	 * 当前产品不能做此交易
	 */
	public static final int ESB_CODE_FAIL_F20518 = 20518;
	/**
	 * 当前字段不能为空
	 */
	public static final int ESB_CODE_FAIL_F20519 = 20519;
	/**
	 * 额度转让失败
	 */
	public static final int ESB_CODE_FAIL_F20400 = 20400;
	/**
	 * 转出额度不能为空
	 */
	public static final int ESB_CODE_FAIL_F20401 = 20401;
	/**
	 * 转入额度不能为空
	 */
	public static final int ESB_CODE_FAIL_F20402 = 20402;

	/**
	 * 交易金额与转出金额不相等
	 */
	public static final int ESB_CODE_FAIL_F20403 = 20403;

	/**
	 * 转入金额与转出金额不相等
	 */
	public static final int ESB_CODE_FAIL_F20404 = 20404;
	/**
	 * 该交易已处理
	 */
	public static final int ESB_CODE_FAIL_F20405 = 20405;
	/**
	 * 当前机构的当前客户明细额度为空
	 */
	public static final int ESB_CODE_FAIL_F20406 = 20406;

	/**
	 * 额度转让-转入数组不能为空
	 */
	public static final int ESB_CODE_FAIL_F20407 = 20407;

	/**
	 * 额度转让-转出数组不能为空
	 */
	public static final int ESB_CODE_FAIL_F20408 = 20408;

	/**
	 * 未获取相应的凭证信息
	 */
	public static final int ESB_CODE_FAIL_F20409 = 20409;

	/**
	 * 额度转让数组转让客户产品不一致
	 */
	public static final int ESB_CODE_FAIL_F20410 = 20410;
	/**
	 * 未获取到省联社机构
	 */
	public static final int ESB_CODE_FAIL_F20411 = 20411;
	/**
	 *
	 */
	public static final int ESB_CODE_FAIL_F20412 = 20412;

	/**
	 * 授信失败
	 */
	public static final int ESB_CODE_FAIL_F20100 = 20100;

	/**
	 * 省联社未对客户进行综合授信
	 */
	public static final int ESB_CODE_FAIL_F20101 = 20101;

	/**
	 * 省联社未对客户进行切分授信
	 */
	public static final int ESB_CODE_FAIL_F20103 = 20103;
	/**
	 * 未对客户进行切分授信
	 */
	public static final int ESB_CODE_FAIL_F20104 = 20104;
	/**
	 * 成员行综合授信额度大于省联社授信额度。
	 */
	public static final int ESB_CODE_FAIL_F20105 = 20105;
	/**
	 * 成员行单业务切分额度大于省联社切分额度
	 */
	public static final int ESB_CODE_FAIL_F20106 = 20106;
	/**
	 * 获取授信产品信息失败
	 */
	public static final int ESB_CODE_FAIL_F20107 = 20107;
	/**
	 * 额度切分信息不能为空
	 */
	public static final int ESB_CODE_FAIL_F20108 = 20108;
	/**
	 * 成员行综合授信有效期不在省联社综合授信范围内
	 */
	public static final int ESB_CODE_FAIL_F20109 = 20109;
	/**
	 * 未查询到当前机构的省联社
	 */
	public static final int ESB_CODE_FAIL_F20110 = 20110;
	/**
	 * 请求报文转换异常
	 */
	public static final int ESB_CODE_FAIL_F20111 = 20111;

	/**
	 * 获取二级额度信息失败
	 */
	public static final int ESB_CODE_FAIL_F20112 = 20112;
	/**
	 * 日间批量用信处理异常
	 */
	public static final int ESB_CODE_FAIL_F20600 = 20600;

	/**
	 * 用信数据不能为空
	 */
	public static final int ESB_CODE_FAIL_F20601 = 20601;

	/**
	 * 系统错误
	 */
	public static final int ESB_CODE_FAIL_F20700 = 20700;
	/**
	 * 交易重复错误
	 */
	public static final int ESB_CODE_FAIL_F20701 = 20701;
	/**
	 * 纯券过户数组为空
	 */
	public static final int ESB_CODE_FAIL_F20702 = 20702;
	/**
	 * 纯券转让-转出数组为空
	 */
	public static final int ESB_CODE_FAIL_F20703 = 20703;
	/**
	 * 纯券转让-转入信息为空
	 */
	public static final int ESB_CODE_FAIL_F20704 = 20704;
	/**
	 * 凭证信息为空
	 */
	public static final int ESB_CODE_FAIL_F20705 = 20705;
	/**
	 * 获取流水信息失败
	 */
	public static final int ESB_CODE_FAIL_F20706 = 20706;
	/**
	 * 获取凭证原编号信息失败
	 */
	public static final int ESB_CODE_FAIL_F20707 = 20707;
	/**
	 * 获取凭证原编号信息失败
	 */
	public static final int ESB_CODE_FAIL_F00300 = 00300;
	/**
	 * 获取ECIF客户号失败
	 */
	public static final int ESB_CODE_FAIL_F00301 = 00301;
	/**
	 * 获取当前机构省联社机构失败
	 */
	public static final int ESB_CODE_FAIL_F00302 = 00302;
	/**
	 * 获取产品信息失败
	 */
	public static final int ESB_CODE_FAIL_F00303 = 00303;
	private Class request;
	private Class response;


	JxrcbConstant() {

	}

	JxrcbConstant(Class request, Class response) {
		this.request = request;
		this.response = response;
	}

	public Class getRequest() {
		return request;
	}

	public Class getResponse() {
		return response;
	}

}
