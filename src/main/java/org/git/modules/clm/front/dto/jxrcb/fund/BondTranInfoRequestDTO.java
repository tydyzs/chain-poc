package org.git.modules.clm.front.dto.jxrcb.fund;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.git.modules.clm.front.dto.jxrcb.Request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CLM207-资金-纯券转让数组(请求报文request)
 */
@Data
@XStreamAlias("bond_tran_info")
public class BondTranInfoRequestDTO extends Request {

	@Valid
	@NotNull
	@XStreamAlias("bond_out_infos")
	private List<BondTranOutInfoRequestDTO> bondOutInfo;//纯券转出信息数组

	@Valid
	@XStreamAlias("bond_in_info")
	private BondTranInInfoRequestDTO bondInInfo;//纯券转入信息
}
