package org.git.modules.clm.chart.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdStatisAreaVO;
import org.git.modules.clm.chart.vo.CrdStatisVO;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;

import java.util.List;
import java.util.Map;


public interface IOrgChartService extends IService<T> {


	List<CrdStatisVO> listStatisCrdpt(String orgNum);

	List<CrdStatisVO> listStatisCustype(String orgNum);

	List<CrdStatisVO> listStatisGtype(String orgNum);

	List<CrdStatisVO> listStatisIndustry(String orgNum);

	List<CrdStatisVO> listStatisProduct(String orgNum);

	List<CrdStatisVO> listStatisUscale(String orgNum);

	List<TbCrdStatisOrg> getStatisOrg(String orgNum);

	List<CrdStatisAreaVO> getStatisArea();

}
