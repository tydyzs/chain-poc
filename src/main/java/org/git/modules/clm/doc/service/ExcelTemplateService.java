package org.git.modules.clm.doc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.modules.clm.doc.entity.BankCreditTable;
import org.git.modules.clm.doc.mapper.ExcelTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * chain-boot
 *
 * @author Haijie
 * @version 1.0
 * @description
 * @date 2020/2/4
 * @since 1.8
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExcelTemplateService {

	private ExcelTemplateMapper excelTemplateMapper;

	public List<BankCreditTable> selectBankCreditTable(String customerName) {
		return excelTemplateMapper.selectBankCreditTable(customerName);
	}

	public IPage<BankCreditTable> selectBankCreditTablePage(IPage page, String customerName) {
		List list = excelTemplateMapper.selectBankCreditTable(customerName);
		return page.setTotal(list.size()).setRecords(list);
	}

}
