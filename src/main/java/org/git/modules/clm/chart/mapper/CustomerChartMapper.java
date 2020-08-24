package org.git.modules.clm.chart.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.chart.vo.GrtVO;
import org.git.modules.clm.loan.entity.CrdDetail;
import org.git.modules.system.entity.Dept;
import org.git.modules.clm.credit.entity.TbCrdStatisCsm;

import java.util.List;

public interface CustomerChartMapper extends BaseMapper<T> {

	List<Dept> listOrgByCust(String customerNum);

	List<CrdQueryVO> listCrdSumPage(IPage page, CrdQueryVO crdQueryVO);

	List<CrdQueryVO> listCrdDetail(CrdQueryVO crdQueryVO);

	List<CrdQueryVO> getCrdListSumPage(IPage page,CrdQueryVO crdQueryVO,String customerType);

	List<CrdQueryVO> queryLedgerCrdSumPage(IPage page,CrdQueryVO crdQueryVO,String customerType);

	List<CrdQueryVO> listGuaranteeCrd(CrdQueryVO crdQueryVO);

	List<GrtVO> listGrtPage(IPage page, String customerNum, String orgNum);

	TbCrdStatisCsm sumCsmCrdInfoByCus(TbCrdStatisCsm tbCrdStatisCsm);

	TbCrdStatisCsm sumCsmCrdInfo(TbCrdStatisCsm tbCrdStatisCsm);

}
