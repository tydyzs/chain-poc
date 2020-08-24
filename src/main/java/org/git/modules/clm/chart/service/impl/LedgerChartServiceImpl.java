package org.git.modules.clm.chart.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.mapper.LedgerChartMapper;
import org.git.modules.clm.chart.service.ILedgerChartService;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LedgerChartServiceImpl extends ServiceImpl<LedgerChartMapper, T> implements ILedgerChartService {

	@Autowired
	private IDeptService deptService;
	/**
	 * 查询同业额度视图基本信息
	 * @param crdQueryVO
	 * @return
	 */
	@Override
	public List<CrdQueryVO> ledgerDetail(CrdQueryVO crdQueryVO) {
		return baseMapper.ledgerDetail(crdQueryVO);
	}

	@Override
	public List<CrdQueryVO> queryLedgerDeatilByOrgType(String customerNum) {
		String SLSorgNum=deptService.selectProvincialCooperative().getId()+"";
		CrdQueryVO crdQueryVO = new CrdQueryVO();
		crdQueryVO.setCustomerNum(customerNum);
		crdQueryVO.setCrdGrantOrgNum(SLSorgNum);
		return baseMapper.queryLedgerDeatilByOrgType(crdQueryVO);
	}

	@Override
	public List<CrdQueryVO> queryLedgerDeatilByOrgTypeThree(String crdGrantOrgNum, String customerNum) {
		CrdQueryVO crdQueryVO = new CrdQueryVO();
		crdQueryVO.setCustomerNum(customerNum);
		crdQueryVO.setCrdGrantOrgNum(crdGrantOrgNum);
		return baseMapper.queryLedgerDeatilByOrgTypeThree(crdQueryVO);
	}


}
