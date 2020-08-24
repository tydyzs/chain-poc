package org.git.modules.clm.chart.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.modules.clm.chart.mapper.GroupChartMapper;
import org.git.modules.clm.chart.service.IGroupChartService;
import org.git.modules.clm.chart.vo.CrdGroupTreeVO;
import org.git.modules.clm.chart.vo.CrdGroupVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GroupChartServiceImpl extends ServiceImpl<GroupChartMapper, T> implements IGroupChartService {

	/**
	 * 集团客户360视图 集团组织结构
	 * @param orgNum
	 * @param memberCustomerNum
	 * @return
	 */
	@Override
	public List<CrdGroupVO> queryGroupMemberList(String orgNum, String memberCustomerNum) {
		return baseMapper.queryGroupMemberList(orgNum,memberCustomerNum);
	}

	@Override
	public List<CrdGroupTreeVO> queryGroupMemberByCsNum(String customerNum) {
		return ForestNodeMerger.merge(baseMapper.queryGroupMemberByCsNum(customerNum));
	}


}
