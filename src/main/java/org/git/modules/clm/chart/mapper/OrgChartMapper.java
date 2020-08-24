package org.git.modules.clm.chart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.chart.vo.CrdQueryVO;
import org.git.modules.clm.chart.vo.CrdStatisAreaVO;
import org.git.modules.clm.chart.vo.CrdStatisVO;
import org.git.modules.clm.chart.vo.GrtVO;
import org.git.modules.clm.credit.entity.TbCrdStatisOrg;
import org.git.modules.system.entity.Dept;

import java.util.List;
import java.util.Map;

public interface OrgChartMapper extends BaseMapper<T> {

	List<CrdStatisVO> listStatisIndustry(@Param("orgNum") String orgNum);

	List<CrdStatisVO> listStatisCrdpt(@Param("orgNum") String orgNum);

	List<CrdStatisVO> listStatisUscale(@Param("orgNum") String orgNum);

	List<CrdStatisVO> listStatisCustype(@Param("orgNum") String orgNum);

	List<CrdStatisVO> listStatisGtype(@Param("orgNum") String orgNum);

	List<CrdStatisVO> listStatisProduct(@Param("orgNum") String orgNum);

	List<TbCrdStatisOrg> getStatisOrg(@Param("orgNum") String orgNum);

	List<CrdStatisAreaVO> getStatisArea();

}
