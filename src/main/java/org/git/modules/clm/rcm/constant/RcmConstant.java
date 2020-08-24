package org.git.modules.clm.rcm.constant;


/**
 * 限额管理常量
 *
 * @author liuye
 */
public class RcmConstant {
	/**
	 * 限额管理-参数配置 生效状态 1：生效
	 */
	public static final String NET_STATE_EFFECTIVE = "1";
	/**
	 * 限额管理-参数配置 生效状态 2:失效
	 */
	public static final String NET_STATE_UNEFFECTIVE = "2";

	/**
	 * 用于TB_RCM_NET_CAPITAL主键生成
	 */
	public static final String RCM_NET_CAPITAL_TYPE = "NC";

	/**
	 * 同业业务限额指标
	 */
	public static final String QUOTA_INDEX_TYPE_BANK = "1";

	/**
	 * 非同业业务限额指标
	 */
	public static final String QUOTA_INDEX_TYPE_CREDIT = "2";

	/**
	 * 用于tb_rcm_index主键生成
	 */
	public static final String RCM_CONFIGURATION_BASE_TYPE = "CB";

	/**
	 * 指标状态-启用
	 */
	public static final String QUOTA_INDEX_STATE_USING = "1";

	/**
	 * 指标状态-停用
	 */
	public static final String QUOTA_INDEX_STATE_UNSING = "2";

	/**
	 * 用于TB_RCM_CONFIGURATION_INFO主键生成
	 */
	public static final String RCM_CONFIGURATION_INFO_TYPE = "CI";


	/**
	 * 阈值层级 1:控制值
	 */
	public static final String QUOTA_LEVEL_CONTROLLER = "1";

	/**
	 * 阈值层级 2:预警值
	 */
	public static final String QUOTA_LEVEL_WARNING = "2";

	/**
	 * 阈值层级 3:观察值
	 */
	public static final String QUOTA_LEVEL_OBSERVE = "3";

	/**
	 * 阈值类型 1:金额
	 */
	public static final String QUOTA_CONTROL_TYPE_BALANCE = "1";

	/**
	 * 阈值类型 2:百分比
	 */
	public static final String QUOTA_LEVEL_WARNING_PERC = "2";

	/**
	 * 限额设定-限额状态 0：未生效
	 */
	public static final String QUOTA_STATE_INVALID = "0";
	/**
	 * 限额设定-限额状态 1：生效
	 */
	public static final String QUOTA_STATE_EFFECT = "1";

	/**
	 * 限额设定-限额状态 2：取消
	 */
	public static final String QUOTA_STATE_CANCEL = "2";


	/**
	 * 限额设定-应对措施：0-通过 1-提示 2-触发报备 3-禁止操作
	 */
	public static final String QUOTA_NODE_MEASURE_PASS = "1";
	public static final String QUOTA_NODE_MEASURE_NOTICE = "2";
	public static final String QUOTA_NODE_MEASURE_WARING = "3";
	public static final String QUOTA_NODE_MEASURE_FORBID = "4";


	/**
	 * 限额控制节点
	 * 0、所有阶段；
	 * 1、授信申请流程中；
	 * 2、授信申请通过；
	 * 3、合同申请流程中；
	 * 4、合同申请生效；
	 * 5、放款申请流程中；
	 * 6、放款申请生效
	 */
	public static final String QUOTA_CONTROL_NODE_PF_ING = "1";
	public static final String QUOTA_CONTROL_NODE_PF_OVER = "2";
	public static final String QUOTA_CONTROL_NODE_HT_ING = "3";
	public static final String QUOTA_CONTROL_NODE_HT_OVER = "4";
	public static final String QUOTA_CONTROL_NODE_JJ_ING = "5";
	public static final String QUOTA_CONTROL_NODE_JJ_OVER = "6";

	/**
	 * 业务类型(1、额度申请  2、合同申请 3、放款申请4、同业业务申请)
	 */
	public static final String QUOTA_BIZ_TYPE_PF = "1";
	public static final String QUOTA_BIZ_TYPE_HT = "2";
	public static final String QUOTA_BIZ_TYPE_JJ = "3";
	public static final String QUOTA_BIZ_TYPE_TY = "4";

	/**
	 * 区域类型CD000257
	 * 1	区域内
	 * 2	区域外
	 */
	public static final String QUOTA_REGION_IN = "1";
	public static final String QUOTA_REGION_OUT = "2";


	/**
	 * 限额分析表报 查询条件常量
	 * 金额单位： wan - 万     yi - 亿
	 */
	public static final String AMT_UNIT_TEN_THOUSAND = "wan";
	public static final String AMT_UNIT_ONE_HUNDRED_MILLION = "yi";

	/**
	 * 限额指标编号
	 */
	public static final String LOAN_TEN_CUS_QUOTA_NUM = "CB20191226310923";//最大十家客户贷款集中度
	public static final String CREDIT_TEN_CUS_QUOTA_NUM = "CB20191226053358";//最大十家客户授信集中度
	public static final String CREDIT_TEN_GROUP_CUS_QUOTA_NUM = "CB20191226559982";//最大十家集团客户授信集中度

}
