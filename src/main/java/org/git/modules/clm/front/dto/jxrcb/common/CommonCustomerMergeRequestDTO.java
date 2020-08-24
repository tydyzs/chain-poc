package org.git.modules.clm.front.dto.jxrcb.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.hibernate.validator.constraints.Length;

/**
 * CLM001_通用-客户合并(请求报文request)
 */
@Data
@XStreamAlias("request")
public class CommonCustomerMergeRequestDTO extends Request {

	@Length(max = 50)
	@XStreamAlias("reserve_ecif_cust_num")
	private String reserveEcifCustNum;//保留客户ECIF号

	@Length(max = 10)
	@XStreamAlias("reserve_cert_type")
	private String reserveCertType;//保留客户证件种类

	@Length(max = 50)
	@XStreamAlias("reserve_cert_num")
	private String reserveCertNum;//保留客户证件号码

	@Length(max = 50)
	@XStreamAlias("merge_ecif_cust_num")
	private String mergeEcifCustNum;//被合并客户ECIF号

	@Length(max = 10)
	@XStreamAlias("merge_cert_type")
	private String mergeCertType;//被合并客户证件种类

	@Length(max = 50)
	@XStreamAlias("merge_cert_num")
	private String mergeCertNum;//被合并客户证件号码

	@Length(max = 50)
	@XStreamAlias("remark1")
	private String remark1;//备注信息1

	@Length(max = 50)
	@XStreamAlias("remark2")
	private String remark2;//备注信息2

	@Length(max = 50)
	@XStreamAlias("remark3")
	private String remark3;//备注信息3

	@Length(max = 10)
	@XStreamAlias("user_num")
	private String userNum;//客户经理

	@Length(max = 10)
	@XStreamAlias("org_num")
	private String orgNum;//机构号

}
