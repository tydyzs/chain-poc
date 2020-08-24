package org.git.modules.clm.rcm.mapper;


import org.git.modules.clm.credit.entity.TbCrdStatisCsm;
import org.git.modules.clm.rcm.entity.RcmIndex;
import org.git.modules.clm.rcm.vo.RcmConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @description: 限额检查查询
 * @author: Haijie
 * @date: 2019/12/4
 * @version 1.0
 **/
public interface RcmQuotaCheckMapper {

	/**
	 * 检查机构启用生效的限额指标
	 * @param orgNum
	 * @return
	 */
	boolean checkQuotaIndex(String orgNum);

	/**
	 * 查询某机构最大十家客户
	 * @param orgNum
	 * @return
	 */
	List<TbCrdStatisCsm> listTopTenCustomer(String orgNum);

	/**
	 * 查询机构启用生效的限额指标
	 * @param orgNum
	 * @return
	 */
	List<RcmConfigVO> listQuotaIndex(String orgNum);


}
