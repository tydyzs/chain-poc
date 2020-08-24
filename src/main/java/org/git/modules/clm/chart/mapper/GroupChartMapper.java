package org.git.modules.clm.chart.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdGroupTreeVO;
import org.git.modules.clm.chart.vo.CrdGroupVO;


import java.util.List;

public interface GroupChartMapper extends BaseMapper<T> {


	/**
	 * 集团客户360视图 集团组织结构
	 * @param orgNum
	 * @param memberCustomerNum
	 * @return
	 */
	List<CrdGroupVO> queryGroupMemberList(String orgNum,String memberCustomerNum);


	List<CrdGroupTreeVO> queryGroupMemberByCsNum(String customerNum);

}
