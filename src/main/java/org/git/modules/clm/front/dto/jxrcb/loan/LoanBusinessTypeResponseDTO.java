package org.git.modules.clm.front.dto.jxrcb.loan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

/**
 * 信贷系统-业务品种查询(响应报文response)
 */
@Data
@XStreamAlias("response")
public class LoanBusinessTypeResponseDTO extends Response {

	@XStreamAlias("O1TTPAGE")
	private int O1TTPAGE;//总页数

	@XStreamAlias("O1TTRECD")
	private int O1TTRECD;//总条数

	@Length(max = 10)
	@XStreamAlias("O1BAKUP1")
	private String O1BAKUP1;//备用1

	@Length(max = 30)
	@XStreamAlias("O1BAKUP2")
	private String O1BAKUP2;//备用2

	@XStreamAlias("O1BAKUP3")
	private BigDecimal O1BAKUP3;//备用3

	@XStreamAlias("items")
	private List<ProductInfoDTO> items;

}
