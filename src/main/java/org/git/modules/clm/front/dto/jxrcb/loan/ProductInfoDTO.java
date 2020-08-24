package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 信贷-业务品种查询
 * pledge_info(押品信息分组)
 */
@Data
@XStreamAlias("item")
public class ProductInfoDTO {

	@Length(max = 7)
	@XStreamAlias("O2PRODUCTID")
	private String O2PRODUCTID;//产品编号

	@Length(max = 5)
	@XStreamAlias("O2PRODUCTNAME")
	private String O2PRODUCTNAME;//产品名称

	@Length(max = 5)
	@XStreamAlias("O2PRODUCTSTATE")
	private String O2PRODUCTSTATE;//产品状态

	@Length(max = 10)
	@XStreamAlias("O2STARTDATE")
	private String O2STARTDATE;//产品上市日期

	@Length(max = 10)
	@XStreamAlias("O2ENDDATE")
	private String O2ENDDATE;//产品下市日期

	@Length(max = 5)
	@XStreamAlias("O2LOWRISKFLAG")
	private String O2LOWRISKFLAG;//低风险产品标志

	@Length(max = 30)
	@XStreamAlias("O2BUSINESSLINE")
	private String O2BUSINESSLINE;//业务条线

	@Length(max = 30)
	@XStreamAlias("O2CAPITALSOURCE")
	private String O2CAPITALSOURCE;//贷款资金来源

	@Length(max = 10)
	@XStreamAlias("O2INTEREXMODEL")
	private String O2INTEREXMODEL;//利率执行方式

	@XStreamAlias("O2MININTERFLOATPOINT")
	private BigDecimal O2MININTERFLOATPOINT;//最低浮点

	@XStreamAlias("O2MAXINTERFLOATPOINT")
	private BigDecimal O2MAXINTERFLOATPOINT;//最高浮点

	@XStreamAlias("O2MININTERFLOAT")
	private BigDecimal O2MININTERFLOAT;//最低利率浮动比

	@XStreamAlias("O2MAXINTERFLOAT")
	private BigDecimal O2MAXINTERFLOAT;//最高利率浮动比

	@XStreamAlias("O2MINAMOUNT")
	private BigDecimal O2MINAMOUNT;//贷款金额最低金额

	@XStreamAlias("O2MAXAMOUNT")
	private BigDecimal O2MAXAMOUNT;//贷款金额最高金额

	@Length(max = 5)
	@XStreamAlias("O2EDCYCLEFLAG")
	private String O2EDCYCLEFLAG;//额度是否循环

	@Length(max = 5)
	@XStreamAlias("O2MINLOANTERM")
	private String O2MINLOANTERM;//贷款最低期限

	@Length(max = 5)
	@XStreamAlias("O2MAXLOANTERm")
	private String O2MAXLOANTERm;//贷款最长期限

	@Length(max = 100)
	@XStreamAlias("O2PRODUCTCHANNEL")
	private String O2PRODUCTCHANNEL;//允许发放渠道

	@Length(max = 100)
	@XStreamAlias("O2JZFLAG")
	private String O2JZFLAG;//介质

	@XStreamAlias("O2MAXZQTIMES")
	private BigDecimal O2MAXZQTIMES;//最大展期次数

	@Length(max = 5)
	@XStreamAlias("O2TDFLAG")
	private String O2TDFLAG;//通贷标志

	@Length(max = 5)
	@XStreamAlias("O2CURRENCY")
	private String O2CURRENCY;//币种

	@Length(max = 5)
	@XStreamAlias("O2TXFLAG")
	private String O2TXFLAG;//是否贴息

	@Length(max = 5)
	@XStreamAlias("O2XYFLAG")
	private String O2XYFLAG;//是否有项目协议

	@Length(max = 5)
	@XStreamAlias("O2RETURNTYPE")
	private String O2RETURNTYPE;//还款方式

	@XStreamAlias("O2KXDAY")
	private BigDecimal O2KXDAY;//宽限天数

	@Length(max = 5)
	@XStreamAlias("O2THFLAG")
	private String O2THFLAG;//通还标志

	@Length(max = 5)
	@XStreamAlias("O2DHCHECKFLAG")
	private String O2DHCHECKFLAG;//是否需要贷后首次检查

	@XStreamAlias("O2CHECKDAY")
	private BigDecimal O2CHECKDAY;//首次检查距放款天数

	@Length(max = 5)
	@XStreamAlias("O2BATCHCHECKFLAG")
	private String O2BATCHCHECKFLAG;//是否支持批量

	@Length(max = 5)
	@XStreamAlias("O2CHECKCYCLE")
	private String O2CHECKCYCLE;//贷后常规检查频度

	@Length(max = 5)
	@XStreamAlias("O2ZXFLAG")
	private String O2ZXFLAG;//是否助学

	@Length(max = 5)
	@XStreamAlias("O2AJFLAG")
	private String O2AJFLAG;//是否按揭

	@Length(max = 5)
	@XStreamAlias("O2CREDITFLAG")
	private String O2CREDITFLAG;//是否适用授信

	@Length(max = 5)
	@XStreamAlias("O2LOANFLAG")
	private String O2LOANFLAG;//是否适用贷审流程

	@Length(max = 5)
	@XStreamAlias("O2STFLAG")
	private String O2STFLAG;//是否适用社团贷款

	@XStreamAlias("O2ORDERNUM")
	private int O2ORDERNUM;//产品排序


	@Length(max = 5)
	@XStreamAlias("O2AUTOFLAG")
	private String O2AUTOFLAG;//是否适用自动审批

	@Length(max = 5)
	@XStreamAlias("O2LOANBATCHFLAG")
	private String O2LOANBATCHFLAG;//是否适用批量贷审流程

	@Length(max = 5)
	@XStreamAlias("O2MATRIXFLAG")
	private String O2MATRIXFLAG;//是否跑五级分类标志

	@Length(max = 5)
	@XStreamAlias("O2ISBZ")
	private String O2ISBZ;//是否并账类贷款

	@Length(max = 5)
	@XStreamAlias("O2LOANPRICINGBASERATETYPE")
	private String O2LOANPRICINGBASERATETYPE;//贷款定价基础利率

	@Length(max = 10)
	@XStreamAlias("O2BAKUP1")
	private String O2BAKUP1;//备用1

	@Length(max = 30)
	@XStreamAlias("O2BAKUP2")
	private String O2BAKUP2;//备用2

	@Length(max = 30)
	@XStreamAlias("O2BAKUP3")
	private String O2BAKUP3;//备用3

	@Length(max = 100)
	@XStreamAlias("O2PRODUCTDESCRIBE")
	private String O2PRODUCTDESCRIBE;// 产品描述

	@Length(max = 100)
	@XStreamAlias("O2LOANKINDCLASSIFY")
	private String O2LOANKINDCLASSIFY;//业务种类分类

	@Length(max = 100)
	@XStreamAlias("O2CUSTOMERCLASSIFY")
	private String O2CUSTOMERCLASSIFY;//客户主体分类

	@Length(max = 100)
	@XStreamAlias("O2ASSURETYPECLASSIFY")
	private String O2ASSURETYPECLASSIFY;//担保方式分类

}
