package org.git.modules.clm.front.dto.jxrcb;

/**
 * 业务常量类
 */
public class JxrcbBizConstant {

	public static final String SECOND_CREDIT = "ED2";//额度二级编号前缀
	public static final String THIRD_CREDIT = "ED3";//额度三级编号前缀
	public static final String OP_TYPE_ADD = "01";//操作类型 新增
	public static final String OP_TYPE_UPDATE = "02";//操作类型 修改
	public static final String OP_TYPE_DELETE = "03";//操作类型 修改
	public static final String OP_TYPE_MERGE = "12";//操作类型 合并
	public static final String OP_TYPE_FK = "09";//操作类型 放款后


	public static final String RESOLVE_BY_NUM = "01";//识别方式 01按客户编号
	public static final String RESOLVE_BY_PAPERS = "02";//识别方式 02按证件查询

	public static final String GUARANTEE_TYPE_XY = "0";//担保方式-信用
	public static final String GUARANTEE_TYPE_BZ = "1";//担保方式-保证
	public static final String GUARANTEE_TYPE_DY = "2";//担保方式-抵押
	public static final String GUARANTEE_TYPE_ZY = "3";//担保方式-质押
	public static final String GUARANTEE_TYPE_LB = "4";//担保方式-联保

	/**业务产品编码*/
	public static final String BUSI_PRD_NUM_1="300007";//直贴入
	public static final String BUSI_PRD_NUM_2="300008";//转贴入
	public static final String BUSI_PRD_NUM_3="300009";//转贴出
	public static final String BUSI_PRD_NUM_4="300010";//再贴出
	public static final String BUSI_PRD_NUM_5="300011";//票据到期

	public static final String CRD_TYPE_SX = "1";//额度类型-授信额度
	public static final String CRD_TYPE_DB = "2";//额度类型-担保额度
	public static final String CRD_TYPE_TD = "3";//额度类型-第三方额度


	public static final String APPROVE_STATUS_0 = "0";//批复状态-审批中
	public static final String APPROVE_STATUS_1 = "1";//批复状态-失效
	public static final String APPROVE_STATUS_2 = "2";//批复状态-生效

	/**
	 * 合同状态CD000094
	 * 0待审核 1已审核 2已生效 3已注销 4已失效
	 */
	public static final String CONTRACT_STATUS_0 = "0";
	public static final String CONTRACT_STATUS_1 = "1";
	public static final String CONTRACT_STATUS_2 = "2";
	public static final String CONTRACT_STATUS_3 = "3";
	public static final String CONTRACT_STATUS_4 = "4";

	/**
	 * 借据状态CD000175
	 * 00 未发放  01正常  02逾期  03部分逾期  07核销  08销户  09票据置换  10资产置换  11股权置换
	 */
	public static final String SUMMARY_STATUS_00 = "00";
	public static final String SUMMARY_STATUS_01 = "01";
	public static final String SUMMARY_STATUS_02 = "02";
	public static final String SUMMARY_STATUS_03 = "03";
	public static final String SUMMARY_STATUS_07 = "07";
	public static final String SUMMARY_STATUS_08 = "08";
	public static final String SUMMARY_STATUS_09 = "09";
	public static final String SUMMARY_STATUS_10 = "10";
	public static final String SUMMARY_STATUS_11 = "11";

	/**
	 * 产品额度占用方式
	 * 1	合同占用 2	放款占用
	 */
	public static final String LIMIT_USED_TYPE_HT = "1";
	public static final String LIMIT_USED_TYPE_JJ = "2";

	/**
	 * 是否
	 * 0否 1是
	 */
	public static final String YES = "1";
	public static final String NO = "0";


	/**
	 * 用信流水交易类型
	 * 01	预占用  02预占用撤销  03占用  04占用撤销  05恢复（若金额为零则剩余全部恢复）  06恢复撤销  09其他
	 */
	public static final String SERIAL_TRAN_TYPE_01 = "01";
	public static final String SERIAL_TRAN_TYPE_02 = "02";
	public static final String SERIAL_TRAN_TYPE_03 = "03";
	public static final String SERIAL_TRAN_TYPE_04 = "04";
	public static final String SERIAL_TRAN_TYPE_05 = "05";
	/**
	 * 参考数据字典CD000192
	 * 05 客户额度冻结
	 * 06 客户准入调整
	 */
	public static final String TRAN_TYPE_CD_05 = "05";
	public static final String TRAN_TYPE_CD_06 = "06";
	/**
	 * 参考数据字典CD000176
	 * 01 正常
	 * 02 部分冻结
	 * 03 全部冻结
	 * 04 已终结
	 */
	public static final String CRD_STATUS_01 = "01";
	public static final String CRD_STATUS_02 = "02";
	public static final String CRD_STATUS_03 = "03";
	public static final String CRD_STATUS_04 = "04";
	/**
	 * 参考数据字典CD000196
	 * 0：禁止
	 * 1：准入
	 */
	public static final String CRD_ADMIT_FLAG_0 = "0";
	public static final String CRD_ADMIT_FLAG_1 = "1";
	/**
	 * 事件处理状态：0 未处理  1 处理成功 2 处理失败
	 */
	public static final String event_status_0 = "0";
	public static final String event_status_1 = "1";
	public static final String event_status_2 = "2";

	public static final String BIZ_TYPE_YW = "YW";//业务申请
	public static final String BIZ_TYPE_JJ = "JJ";//借据
	public static final String BIZ_TYPE_PJ = "PJ";//票据
	public static final String BIZ_TYPE_BZ = "BZ";//保证人

	/**
	 * 客户类型：1个人客户  2公司客户  3同业客户
	 */
	public static final String CUST_TYPE_GR = "1";//1个人客户
	public static final String CUST_TYPE_GS = "2";//2公司客户
	public static final String CUST_TYPE_TY = "3";//3同业客户

	public static final String CURRENCY_CNY = "01";//币种-人民币

	/**
	 * 额度状态 01正常
	 */
	public static final String CREDIT_STATUS_NORMAL = "01";
	/**
	 * 额度状态 03全部冻结
	 */
	public static final String CREDIT_STATUS_FROZEN_ALL = "03";

	public static final String TRAN_SYSTEM_LOAN = "0007";//信贷系统

	public static final String CONN_TYPE_PHONE_NUMBER = "501";//联系方式 手机
	public static final String CONN_TYPE_UNIT_PHONE = "302";//联系方式 单位电话
	public static final String CONN_TYPE_FAMILY_PHONE = "301";//联系方式 家庭电话
	public static final String CERT_TYPE_ID_CARD = "1";//居民身份证
	public static final String MANAGEMENT_RIGHT = "1";//管护权

	public static final String CUSTOMER_TYPE_PRIVATE = "1";//对私客户
	public static final String CUSTOMER_TYPE_CORPORATION = "2";//对公客户
	public static final String CUSTOMER_TYPE_INTERBANK = "3";//同业客户

	public static final String COUNTRY_CHN= "156";//CD000001国家和地区-中国



	/**
	 * CD000210	贷款期限类型	1	短期贷款
	 * CD000210	贷款期限类型	2	中长期贷款
	 * CD000210	贷款期限类型	3	长期贷款
	 */
	public static final String TERM_TYPE_DQ = "1";//短期贷款
	public static final String TERM_TYPE_ZQ = "2";//中长期贷款
	public static final String TERM_TYPE_CQ = "3";//长期贷款

	/**
	 * CD000169	年月日	Y	年
	 * CD000169	年月日	M	月
	 * CD000169	年月日	D	日
	 */
	public static final String TERM_UNIT_Y = "Y";//年
	public static final String TERM_UNIT_M = "M";//月
	public static final String TERM_UNIT_D = "D";//日

	public static final String REL_TYPE_LEGAL = "32";//关系人类型-法人代表

	/**
	 * 交易类型 01维护综合额度
	 */
	public static final String TRAN_TYPE_COMPOSITE_CREDIT = "01";
	/**
	 * 交易类型 02维护切分额度
	 */
	public static final String TRAN_TYPE_SPLIT_CREDIT = "02";
	/**
	 * 本方交易状态 0未处理
	 */
	public static final String TRAN_EVENT_STATUS_UNPROCES = "0";
	/**
	 * 本方交易状态 1成功
	 */
	public static final String TRAN_EVENT_STATUS_SUCCEED = "1";
	/**
	 * 本方交易状态 2失败
	 */
	public static final String TRAN_EVENT_STATUS_FAILED = "2";
	/**
	 * 对方对账状态 0未处理
	 */
	public static final String TRAN_ACCT_STATUS_UNPROCES = "0";
	/**
	 * 对方对账状态 1成功
	 */
	public static final String TRAN_ACCT_STATUS_SUCCEED = "1";
	/**
	 * 对方对账状态 2失败
	 */
	public static final String TRAN_ACCT_STATUS_FAILED = "2";

	/**
	 * 是否检测 1.检测
	 */
	public static final String CHECK_FLAG_YES = "1";
	/**
	 * 客户准入状态 0.禁入
	 */
	public static final String CUSTOMER_ADMIT_FLAG_BAN = "0";
	/**
	 * 客户准入状态 1.准入
	 */
	public static final String CUSTOMER_ADMIT_FLAG_OK = "1";
	/**
	 * 凭证状态-无效
	 */
	public static final String CERT_STATUS_INVALID = "00";
	/**
	 * 凭证状态-预占用
	 */
	public static final String CERT_STATUS_PRE = "01";
	/**
	 * 凭证状态-占用
	 */
	public static final String CERT_STATUS_USED = "03";
	/**
	 * 凭证状态-结清
	 */
	public static final String CERT_STATUS_SETTLED = "10";

	/**
	 * 预占用
	 */
	public static final String TRAN_TYPE_PRE = "01";

	/**
	 * 预占用撤销
	 */
	public static final String TRAN_TYPE_PRE_CANCEL = "02";

	/**
	 * 占用
	 */
	public static final String TRAN_TYPE_USE = "03";

	/**
	 * 占用撤销
	 */
	public static final String TRAN_TYPE_USE_CANCEL = "04";

	/**
	 * 恢复（若金额为零则剩余全部恢复）
	 */
	public static final String TRAN_TYPE_RESUME = "05";


	/**
	 * 恢复撤销
	 */
	public static final String TRAN_TYPE_RESUME_CANCEL = "06";

	/**
	 * 其他（不涉及额度用信）考虑到未来限额扩展，应是全部其他交易
	 */
	public static final String TRAN_TYPE_OTHER = "09";

	/**
	 * 直接占用
	 */
	public static final String TRAN_TYPE_DIRECT_USE = "13";

	/**
	 * 直接占用撤销
	 */
	public static final String TRAN_TYPE_DIRECT_USE_CANCEL = "14";


	/**
	 * 额度状态-生效
	 */
	public static final String CERT_STATUS_EFFECT = "01";

	/**
	 * 业务产品凭证信息表主键开头
	 */
	public static final String CRD_BUSI_CERT_INFO_HEADER = "IH";

	/**
	 * 接口编号205
	 */
	public static final String CLM205 = "00870000259205";
	/**
	 * 接口编号201
	 */
	public static final String CLM201 = "00870000259201";
	/**
	 * 接口编号204
	 */
	public static final String CLM204 = "00870000259204";
	/**
	 * 接口编号206
	 */
	public static final String CLM206 = "00870000259206";
	/**
	 * 接口编号301
	 */
	public static final String CLM301 = "00870000259301";

	/**
	 * 串用开关 1可串用
	 */
	public static final String CONTACT_USE_YES = "1";
	/**
	 * 资金系统省联社（资金营运中心 )
	 * */
	public static final String ZJXT_SNS="01122";

	/**
	 * 202接口额度状态
	 * */
	public static final String CRD_TYPE_2="2";
	public static final String CRD_TYPE_3="3";

	/**
	 * 凭证检验方法名 凭证起始日校验
	 */
	public static final String METHOD_COUNTDATEVALID = "countDateValid";

	/**
	 * 凭证检验方法名 凭证状态
	 */
	public static final String METHOD_CHECKCERTSTATUS = "checkCertStatus";

	/**
	 * 不涉额产品编号（虚拟）
	 */
	public static final String CRD_DETAIL_PRD_03019999 = "03019999";

	/**
	 * 不涉额产品编号（虚拟）
	 */
	public static final String CRD_DETAIL_NUM_BLANK = "0";

	/**
	 * 交易方向-正向 CD000270
	 */
	public static final String TRAN_DIRECTION_FORWARD = "0";

	/**
	 * 交易方向-反向 CD000270
	 */
	public static final String TRAN_DIRECTION_OPPOSITE  = "1";
	/**
	 * 产品类型 信贷类产品
	 */
	public static final String PRODUCT_TARGER_1 = "1";
	/**
	 * 产品类型 同业类产品
	 */
	public static final String PRODUCT_TARGER_2 = "2";
	/**
	 * 产品类型 同有产品
	 */
	public static final String PRODUCT_TARGER_3 = "3";
}
