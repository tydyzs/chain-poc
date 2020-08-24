package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.common.constant.CommonConstant;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * CLM201_资金实时-额度授信（启用、切分、调整）(请求报文request)
 * crd_segm_info(切分数组)
 */
@Data
@XStreamAlias("item")
public class CrdSegmInfoRequestDTO {

	@Length(max = 10)
	@XStreamAlias("crd_detail_prd")
	private String crdDetailPrd;//明细额度产品

	@Pattern(regexp = CommonConstant.PATTERN_AMT,message = "数据类型应为DECIMAL(24,2)")
	@XStreamAlias("crd_detail_amt")
	private String crdDetailAmt;//明细额度
}
