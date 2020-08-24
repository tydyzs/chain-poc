package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM202_资金-额度圈存(请求报文request)
 * crd_eark_info(圈存分配数组)
 */
@Data
@XStreamAlias("item")
public class CrdEarkInfoRequestDTO  extends Request {

	@NotBlank
	@Length(max = 12)
	@XStreamAlias("crd_allot_org_num")
	private String crdAllotOrgNum;//分配成员行

	@NotBlank
	@Length(max = 24)
	@XStreamAlias("crd_alloc_amt")
	private String crdAllocAmt;//分配额度
}
