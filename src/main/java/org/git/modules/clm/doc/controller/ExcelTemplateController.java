package org.git.modules.clm.doc.controller;

import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.entity.vo.TemplateExcelConstants;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.git.common.utils.BizNumUtil;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.ResourceUtil;
import org.git.modules.clm.doc.entity.BankCreditTable;
import org.git.modules.clm.doc.service.ExcelTemplateService;
import org.git.modules.clm.rcm.dto.RcmCreditTenCusRptDTO;
import org.git.modules.clm.rcm.dto.RcmCreditTenGroupCusRptDTO;
import org.git.modules.clm.rcm.dto.RcmLoanTenCusRptDTO;
import org.git.modules.clm.rcm.service.RcmAnalyzeQueryRptService;
import org.git.modules.clm.rcm.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * chain-boot
 * Excel模板报表导出
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/2/3
 * @since 1.8
 */
@Controller
@RequestMapping("git-doc/excel-report")
@AllArgsConstructor
@Api(value = "Excel模板报表导出", tags = "Excel模板报表导出")
public class ExcelTemplateController {

	private ExcelTemplateService excelTemplateService;

	private RcmAnalyzeQueryRptService rcmAnalyzeQueryRptService;

	@ApiOperation("下载同业授信余额表")
	@RequestMapping(path = "download/bankCreditTable", method = {RequestMethod.GET, RequestMethod.POST})
	public String downloadBankCreditTable(ModelMap modelMap, String customerName) throws FileNotFoundException {
		Map<String, Object> map = new HashMap<String, Object>();
		String path = ResourceUtil.getURL("classpath:templates/excel/BankCreditTable.xlsx").getFile();
		TemplateExportParams params = new TemplateExportParams(path);
//		TemplateExportParams params = new TemplateExportParams("src/main/java/org/git/modules/clm/doc/template/BankCreditTable.xlsx");
		List<BankCreditTable> list = excelTemplateService.selectBankCreditTable(customerName);
		map.put("list", list);
		modelMap.put(TemplateExcelConstants.FILE_NAME, "同业授信余额表" + BizNumUtil.getBizNumWithDateTime(""));
		modelMap.put(TemplateExcelConstants.PARAMS, params);
		modelMap.put(TemplateExcelConstants.MAP_DATA, map);
		return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;

	}

	@ApiOperation("查询同业授信余额表")
	@GetMapping("query/bankCreditTable")
	@ResponseBody
	public R<IPage<BankCreditTable>> queryBankCreditTable(String customerName, Query query) {
		IPage<BankCreditTable> pages = excelTemplateService.selectBankCreditTablePage(Condition.getPage(query), customerName);
		return R.data(pages);

	}

	/**
	 * 最大十家客户贷款集中度及最大十家单一客户明细表导出
	 * 刘烨
	 */
	@GetMapping("download/downLoanTenCusRptTable")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "最大十家客户贷款集中度及最大十家单一客户明细表", notes = "传入查询条件\n" +
		"根据结果中quotaControlType字段的值选择展示不同的控制值、预警值、观察值。quotaControlType为1时，阈值类型为金额；为2时阈值类型为占比\n" +
		"(金额类型的值，受金额单位的控制)")
	public String downLoanTenCusRptTable(HttpServletResponse response, ModelMap modelMap, RcmQuotaAnalysisRptQueryVO queryVO) throws FileNotFoundException, UnsupportedEncodingException {
		String path = ResourceUtil.getURL("classpath:templates/excel/loanTenCusRptTable.xlsx").getFile();
		TemplateExportParams params = new TemplateExportParams(path);
		String fileName = "最大十家客户贷款集中度及单一客户明细表" + BizNumUtil.getBizNumWithDateTime("");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition",
			"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		RcmLoanTenCusRptDTO rcmLoanTenCusRpt = rcmAnalyzeQueryRptService.selectRcmLoanTenCusRptPage(queryVO);
		List<RcmLoanTenCusRptVO> list = rcmLoanTenCusRpt.getLoanTenCusRptVOList();
		Map<String, Object> result = new HashMap();
		String amtUnit = queryVO.getAmtUnit();
		String unit = "";
		if ("10000".equals(amtUnit)) {
			unit = "万元";
		} else if ("100000000".equals(amtUnit)) {
			unit = "亿元";
		}
		result.put("unit", unit);
		RcmQuotaInfoVO rcmQuotaInfo = rcmLoanTenCusRpt.getQuotaInfoVO();
		String zero = "0";
		if (rcmQuotaInfo != null) {
			result.put("controlValue", "1".equals(rcmQuotaInfo.getControlValueType()) ? rcmQuotaInfo.getControlValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getControlValue().stripTrailingZeros().toPlainString() + "%");
			result.put("warningValue", "1".equals(rcmQuotaInfo.getWarnValueType()) ? rcmQuotaInfo.getWarnValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getWarnValue().stripTrailingZeros().toPlainString() + "%");
			result.put("observationValue", "1".equals(rcmQuotaInfo.getWarnValueType()) ? rcmQuotaInfo.getObserveValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getObserveValue().stripTrailingZeros().toPlainString() + "%");
			BigDecimal totalChange = rcmQuotaInfo.getQuotaUsedRatio();
			result.put("change", totalChange==null ? zero:totalChange.stripTrailingZeros().toPlainString());
			result.put("totalAmt", rcmQuotaInfo.getQuotaUsedAmt() == null ? zero : rcmQuotaInfo.getQuotaUsedAmt().stripTrailingZeros().toPlainString());
		} else {
			result.put("controlValue", zero);
			result.put("warningValue", zero);
			result.put("observationValue", zero);
			result.put("change", zero);
			result.put("totalAmt", zero);
		}

		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> lm = new HashMap<String, String>();
				lm.put("num", String.valueOf(i + 1));
				lm.put("customerName", list.get(i).getCustomerName());
				lm.put("loanBalance", list.get(i).getLoanBalance() == null ? zero : list.get(i).getLoanBalance().stripTrailingZeros().toPlainString());
				BigDecimal change = list.get(i).getChange();
				if (change == null) {
					lm.put("change", zero);
				} else if (change.compareTo(new BigDecimal(zero)) > 0) {
					lm.put("change", "↑" + change.stripTrailingZeros().toPlainString());
				} else if (change.compareTo(new BigDecimal(zero)) < 0) {
					lm.put("change", "↓" + change.multiply(new BigDecimal("-1")).stripTrailingZeros().toPlainString());
				} else if (change.compareTo(new BigDecimal(zero)) == 0) {
					lm.put("change", change.stripTrailingZeros().toPlainString());
				}
				lm.put("mingcheng", "设计");
				maplist.add(lm);
			}
		}
		result.put("maplist", maplist);
		modelMap.put(TemplateExcelConstants.FILE_NAME, fileName);
		modelMap.put(TemplateExcelConstants.PARAMS, params);
		modelMap.put(TemplateExcelConstants.MAP_DATA, result);
		return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * 查询客户授信集中度简表并导出
	 * write by 刘烨
	 */
	@GetMapping("/download/downRcmCusQuotaRpt")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询客户授信集中度", notes = "传入查询条件")
	public String downRcmCusQuotaRpt(HttpServletResponse response, ModelMap modelMap, RcmQuotaAnalysisRptQueryVO queryVO) throws FileNotFoundException, UnsupportedEncodingException {
		String path = ResourceUtil.getURL("classpath:templates/excel/rcmCusQuotaRpt.xlsx").getFile();
		TemplateExportParams params = new TemplateExportParams(path);
		String fileName = "客户授信集中度简表" + BizNumUtil.getBizNumWithDateTime("");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition",
			"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		Map<String, Object> result = new HashMap<String, Object>();
		List<RcmCusQuotaRptVO> list = rcmAnalyzeQueryRptService.selectRcmCusQuotaRptList(queryVO);
		String amtUnit = queryVO.getAmtUnit();
		String unit = "";
		if ("10000".equals(amtUnit)) {
			unit = "万元";
		} else if ("100000000".equals(amtUnit)) {
			unit = "亿元";
		}
		result.put("unit", unit);
		result.put("totalYear", queryVO.getTotalYear());
		result.put("totalMonth", queryVO.getTotalMonth());
		result.put("result", list);
		modelMap.put(TemplateExcelConstants.FILE_NAME, fileName);
		modelMap.put(TemplateExcelConstants.PARAMS, params);
		modelMap.put(TemplateExcelConstants.MAP_DATA, result);
		return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;
	}


	/**
	 * 最大十家客户授信集中度及单一客户明细表导出
	 * 刘烨
	 */
	@GetMapping("download/downCreditTenCusRpt")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "最大十家客户授信集中度及单一客户明细表", notes = "传入查询条件\n" +
		"根据结果中quotaControlType字段的值选择展示不同的控制值、预警值、观察值。quotaControlType为1时，阈值类型为金额；为2时阈值类型为占比\n" +
		"(金额类型的值，受金额单位的控制)")
	public String downCreditTenCusRpt(HttpServletResponse response, ModelMap modelMap, RcmQuotaAnalysisRptQueryVO queryVO) throws FileNotFoundException, UnsupportedEncodingException {
		String path = ResourceUtil.getURL("classpath:templates/excel/loanTenCusRptTable.xlsx").getFile();
		TemplateExportParams params = new TemplateExportParams(path);
		String fileName = "最大十家客户授信集中度及单一客户明细表" + BizNumUtil.getBizNumWithDateTime("");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition",
			"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		RcmCreditTenCusRptDTO rcmLoanTenCusRpt = rcmAnalyzeQueryRptService.selectRcmCreditTenCusRptPage(queryVO);
		List<RcmCreditTenCusRptVO> list = rcmLoanTenCusRpt.getCreditTenCusRptVOList();
		Map<String, Object> result = new HashMap<String, Object>();
		String amtUnit = queryVO.getAmtUnit();
		String unit = "";
		if ("10000".equals(amtUnit)) {
			unit = "万元";
		} else if ("100000000".equals(amtUnit)) {
			unit = "亿元";
		}
		result.put("unit", unit);
		RcmQuotaInfoVO rcmQuotaInfo = rcmLoanTenCusRpt.getQuotaInfoVO();
		String zero = "0";
		if (rcmQuotaInfo != null) {
			result.put("controlValue", "1".equals(rcmQuotaInfo.getControlValueType()) ? rcmQuotaInfo.getControlValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getControlValue().stripTrailingZeros().toPlainString() + "%");
			result.put("warningValue", "1".equals(rcmQuotaInfo.getWarnValueType()) ? rcmQuotaInfo.getWarnValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getWarnValue().stripTrailingZeros().toPlainString() + "%");
			result.put("observationValue", "1".equals(rcmQuotaInfo.getObserveValueType()) ? rcmQuotaInfo.getObserveValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getObserveValue().stripTrailingZeros().toPlainString() + "%");
			BigDecimal totalChange = rcmQuotaInfo.getQuotaUsedRatio();
			result.put("change", totalChange==null ? zero:totalChange.stripTrailingZeros().toPlainString());
			result.put("totalAmt", rcmQuotaInfo.getQuotaUsedAmt() == null ? zero : rcmQuotaInfo.getQuotaUsedAmt().stripTrailingZeros().toPlainString());
		} else {
			result.put("controlValue", zero);
			result.put("warningValue", zero);
			result.put("observationValue", zero);
			result.put("change", zero);
			result.put("totalAmt", zero);
		}

		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> lm = new HashMap<String, String>();
				lm.put("num", String.valueOf(i + 1));
				lm.put("customerName", list.get(i).getCustomerName());
				lm.put("loanBalance", list.get(i).getCrdBalance() == null ? zero : list.get(i).getCrdBalance().stripTrailingZeros().toPlainString());
				BigDecimal change = list.get(i).getChange();
				if (change == null) {
					lm.put("change", zero);
				} else if (change.compareTo(new BigDecimal(zero)) > 0) {
					lm.put("change", "↑" + change.stripTrailingZeros().toPlainString());
				} else if (change.compareTo(new BigDecimal(zero)) < 0) {
					lm.put("change", "↓" + change.multiply(new BigDecimal("-1")).stripTrailingZeros().toPlainString());
				} else if (change.compareTo(new BigDecimal(zero)) == 0) {
					lm.put("change", change.stripTrailingZeros().toPlainString());
				}
				lm.put("mingcheng", "设计");
				maplist.add(lm);
			}
		}
		result.put("maplist", maplist);
		modelMap.put(TemplateExcelConstants.FILE_NAME, fileName);
		modelMap.put(TemplateExcelConstants.PARAMS, params);
		modelMap.put(TemplateExcelConstants.MAP_DATA, result);
		return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * 最大十家集团客户授信集中度及集团客户明细表导出
	 * 刘烨
	 */
	@GetMapping("download/downCreditTenGroupCusRpt")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "最大十家集团客户授信集中度及集团客户明细表", notes = "传入查询条件\n" +
		"根据结果中quotaControlType字段的值选择展示不同的控制值、预警值、观察值。quotaControlType为1时，阈值类型为金额；为2时阈值类型为占比\n" +
		"(金额类型的值，受金额单位的控制)")
	public String downCreditTenGroupCusRpt(HttpServletResponse response, ModelMap modelMap, RcmQuotaAnalysisRptQueryVO queryVO) throws FileNotFoundException, UnsupportedEncodingException {
		String path = ResourceUtil.getURL("classpath:templates/excel/loanTenCusRptTable.xlsx").getFile();
		TemplateExportParams params = new TemplateExportParams(path);
		String fileName = "最大十家集团客户授信集中度及集团客户明细表" + BizNumUtil.getBizNumWithDateTime("");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition",
			"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		RcmCreditTenGroupCusRptDTO rcmLoanTenCusRpt = rcmAnalyzeQueryRptService.selectRcmCreditTenGroupCusRptPage(queryVO);
		List<RcmCreditTenGroupCusRptVO> list = rcmLoanTenCusRpt.getCreditTenGroupCusRptVOList();
		Map<String, Object> result = new HashMap<String, Object>();
		String amtUnit = queryVO.getAmtUnit();
		String unit = "";
		if ("10000".equals(amtUnit)) {
			unit = "万元";
		} else if ("100000000".equals(amtUnit)) {
			unit = "亿元";
		}
		result.put("unit", unit);
		RcmQuotaInfoVO rcmQuotaInfo = rcmLoanTenCusRpt.getQuotaInfoVO();
		String zero = "0";
		if (rcmQuotaInfo != null) {
			result.put("controlValue", "1".equals(rcmQuotaInfo.getControlValueType()) ? rcmQuotaInfo.getControlValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getControlValue().stripTrailingZeros().toPlainString() + "%");
			result.put("warningValue", "1".equals(rcmQuotaInfo.getWarnValueType()) ? rcmQuotaInfo.getWarnValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getWarnValue().stripTrailingZeros().toPlainString() + "%");
			result.put("observationValue", "1".equals(rcmQuotaInfo.getObserveValueType()) ? rcmQuotaInfo.getObserveValue().stripTrailingZeros().toPlainString() : rcmQuotaInfo.getObserveValue().stripTrailingZeros().toPlainString() + "%");
			BigDecimal totalChange = rcmQuotaInfo.getQuotaUsedRatio();
			result.put("change", totalChange==null ? zero:totalChange.stripTrailingZeros().toPlainString());
			result.put("totalAmt", rcmQuotaInfo.getQuotaUsedAmt() == null ? zero : rcmQuotaInfo.getQuotaUsedAmt().stripTrailingZeros().toPlainString());
		} else {
			result.put("controlValue", zero);
			result.put("warningValue", zero);
			result.put("observationValue", zero);
			result.put("change", zero);
			result.put("totalAmt", zero);
		}

		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> lm = new HashMap<String, String>();
				lm.put("num", String.valueOf(i + 1));
				lm.put("customerName", list.get(i).getCustomerName());
				lm.put("loanBalance", list.get(i).getCrdBalance() == null ? zero : list.get(i).getCrdBalance().stripTrailingZeros().toPlainString());
				BigDecimal change = list.get(i).getChange();
				if (change == null) {
					lm.put("change", zero);
				} else if (change.compareTo(new BigDecimal(zero)) > 0) {
					lm.put("change", "↑" + change.stripTrailingZeros().toPlainString());
				} else if (change.compareTo(new BigDecimal(zero)) < 0) {
					lm.put("change", "↓" + change.multiply(new BigDecimal("-1")).stripTrailingZeros().toPlainString());
				} else if (change.compareTo(new BigDecimal(zero)) == 0) {
					lm.put("change", change.stripTrailingZeros().toPlainString());
				}
				lm.put("mingcheng", "设计");
				maplist.add(lm);
			}
		}
		result.put("maplist", maplist);
		modelMap.put(TemplateExcelConstants.FILE_NAME, fileName);
		modelMap.put(TemplateExcelConstants.PARAMS, params);
		modelMap.put(TemplateExcelConstants.MAP_DATA, result);
		return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;
	}
/*
	@ApiOperation("例子")
	@RequestMapping("sample")
	public String sample(ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		TemplateExportParams params = new TemplateExportParams("src/main/java/org/git/modules/clm/doc/template/foreach.xlsx");
		List<TemplateExcelExportEntity> list = new ArrayList<TemplateExcelExportEntity>();

		for (int i = 0; i < 4; i++) {
			TemplateExcelExportEntity entity = new TemplateExcelExportEntity();
			entity.setIndex(i + 1 + "");
			entity.setAccountType("开源项目");
			entity.setProjectName("EasyPoi " + i + "期");
			entity.setAmountApplied(i * 10000 + "");
			entity.setApprovedAmount((i + 1) * 10000 - 100 + "");
			list.add(entity);
		}
		map.put("entitylist", list);
		map.put("manmark", "1");
		map.put("letest", "12345678");
		map.put("fntest", "12345678.2341234");
		map.put("fdtest", null);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 1; i++) {
			Map<String, Object> testMap = new HashMap<String, Object>();

			testMap.put("id", "xman");
			testMap.put("name", "小明" + i);
			testMap.put("sex", "1");
			mapList.add(testMap);
		}
		map.put("maplist", mapList);

		mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 6; i++) {
			Map<String, Object> testMap = new HashMap<String, Object>();

			testMap.put("si", "xman");
			mapList.add(testMap);
		}
		map.put("sitest", mapList);
		modelMap.put(TemplateExcelConstants.FILE_NAME, "用户信息");
		modelMap.put(TemplateExcelConstants.PARAMS, params);
		modelMap.put(TemplateExcelConstants.MAP_DATA, map);
//		PoiBaseView.render(modelMap, request, response, TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW);
		return TemplateExcelConstants.EASYPOI_TEMPLATE_EXCEL_VIEW;

	}*/

}
