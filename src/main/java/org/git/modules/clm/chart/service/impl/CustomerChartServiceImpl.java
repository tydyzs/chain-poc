package org.git.modules.clm.chart.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.modules.clm.chart.mapper.CustomerChartMapper;
import org.git.modules.clm.chart.service.ICustomerChartService;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.chart.vo.GrtVO;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;
import org.git.modules.system.entity.Dept;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerChartServiceImpl extends ServiceImpl<CustomerChartMapper, T> implements ICustomerChartService {

	@Override
	public List<Dept> listOrgByCust(String customerNum) {
		return baseMapper.listOrgByCust(customerNum);
	}

	@Override
	public IPage<CrdQueryVO> listCrdSumPage(IPage<CrdQueryVO> page, CrdQueryVO crdQueryVO) {
		return page.setRecords(baseMapper.listCrdSumPage(page, crdQueryVO));
	}

	@Override
	public List<CrdQueryVO> listCrdDetail(CrdQueryVO crdQueryVO) {
		return baseMapper.listCrdDetail(crdQueryVO);
	}

	@Override
	public List<CrdQueryVO> listGuaranteeCrd(CrdQueryVO crdQueryVO) {
		return baseMapper.listGuaranteeCrd(crdQueryVO);
	}

	@Override
	public IPage<GrtVO> listGrtPage(IPage<GrtVO> page, String customerNum, String orgNum) {
		//字典翻译
		List<GrtVO> list = transVO2(baseMapper.listGrtPage(page, customerNum, orgNum));
		return page.setRecords(list);
	}

	@Override
	public TbCrdStatisCsm sumCsmCrdInfo(TbCrdStatisCsm tbCrdStatisCsm) {
//		System.out.println("查询参数，客户号：" + tbCrdStatisCsm.getCustomerNum() + "，机构号：" + orgNum);
		return baseMapper.sumCsmCrdInfoByCus(tbCrdStatisCsm);
	}

	@Override
	public IPage<CrdQueryVO> getCrdListSumPage(IPage<CrdQueryVO> page, CrdQueryVO crdQueryVO, String customerType) {
		// 字典翻译
		List<CrdQueryVO> list = transVO(baseMapper.getCrdListSumPage(page, crdQueryVO, customerType));
		return page.setRecords(list);
	}

	/**
	 * 同业客户额度台账列表
	 *
	 * @param page
	 * @param crdQueryVO
	 * @param customerType
	 * @return
	 */
	@Override
	public IPage<CrdQueryVO> queryLedgerCrdSumPage(IPage<CrdQueryVO> page, CrdQueryVO crdQueryVO, String customerType) {
		return page.setRecords(baseMapper.queryLedgerCrdSumPage(page, crdQueryVO, customerType));
	}

	/**
	 * 转换类，将CrdQueryVO进行字典翻译
	 *
	 * @param crdQueryVOList
	 * @return
	 */
	public List<CrdQueryVO> transVO(List<CrdQueryVO> crdQueryVOList) {
		List<CrdQueryVO> list = new ArrayList<>();
		for (CrdQueryVO crdQueryVO : crdQueryVOList) {
			// 证件类型
			crdQueryVO.setCertTypeName(DictCache.getValue("CD000003", crdQueryVO.getCertType()));
			// 币种
			crdQueryVO.setCurrencyCdName(DictCache.getValue("CD000019", crdQueryVO.getCurrencyCd()));
			// 机构
			crdQueryVO.setOrgNumName(SysCache.getDeptName(crdQueryVO.getOrgNum()));

			list.add(crdQueryVO);
		}
		return list;
	}

	/**
	 * 转换类，将GrtVO进行字典翻译
	 *
	 * @param grtVOList
	 * @return
	 */
	public List<GrtVO> transVO2(List<GrtVO> grtVOList) {
		List<GrtVO> list = new ArrayList<>();
		for (GrtVO grtVO : grtVOList) {
			// 担保合同类型
			grtVO.setSubcontractTypeName(DictCache.getValue("CD000102", grtVO.getSubcontractType()));
			// 担保物类型
			grtVO.setPledgeTypeName(DictCache.getValue("CD000209", grtVO.getPledgeType()));
			// 币种
			grtVO.setCurrencyCdName(DictCache.getValue("CD000019", grtVO.getCurrencyCd()));
			// 机构
			grtVO.setOrgNumName(SysCache.getDeptName(grtVO.getOrgNum()));
			//经办人
			grtVO.setUserNumName(UserCache.getUserName(grtVO.getUserNum()));

			list.add(grtVO);
		}
		return list;
	}
}
