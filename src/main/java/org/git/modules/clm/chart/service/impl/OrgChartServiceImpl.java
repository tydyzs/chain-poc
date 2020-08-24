package org.git.modules.clm.chart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.chart.mapper.OrgChartMapper;
import org.git.modules.clm.chart.service.IOrgChartService;
import org.git.modules.clm.chart.vo.CrdStatisAreaVO;
import org.git.modules.clm.chart.vo.CrdStatisVO;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;
import org.git.modules.clm.credit.mapper.TbCrdStatisOrgMapper;
import org.git.modules.clm.credit.service.ITbCrdApproveService;
import org.git.modules.clm.credit.service.ITbCrdStatisOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class OrgChartServiceImpl extends ServiceImpl<OrgChartMapper, T> implements IOrgChartService {

	@Autowired
	ITbCrdStatisOrgService iTbCrdStatisOrgService;

	@Override
	public List<CrdStatisVO> listStatisCrdpt(String orgNum) {
		return baseMapper.listStatisCrdpt(orgNum);
	}

	@Override
	public List<CrdStatisVO> listStatisIndustry(String orgNum) {
		return baseMapper.listStatisIndustry(orgNum);
	}

	@Override
	public List<CrdStatisVO> listStatisCustype(String orgNum) {
		return baseMapper.listStatisCustype(orgNum);
	}

	@Override
	public List<CrdStatisVO> listStatisGtype(String orgNum) {
		return baseMapper.listStatisGtype(orgNum);
	}

	@Override
	public List<CrdStatisVO> listStatisProduct(String orgNum) {
		return baseMapper.listStatisProduct(orgNum);
	}

	@Override
	public List<CrdStatisVO> listStatisUscale(String orgNum) {
		return baseMapper.listStatisUscale(orgNum);
	}

	@Override
	public List<TbCrdStatisOrg> getStatisOrg(String orgNum) {
		return baseMapper.getStatisOrg(orgNum);
	}

	@Override
	public List<CrdStatisAreaVO> getStatisArea() {
		return baseMapper.getStatisArea();
	}
}
