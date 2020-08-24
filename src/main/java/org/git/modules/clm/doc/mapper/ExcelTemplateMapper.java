package org.git.modules.clm.doc.mapper;

import org.git.modules.clm.doc.entity.BankCreditTable;

import java.util.List;

public interface ExcelTemplateMapper {


	List<BankCreditTable> selectBankCreditTable(String customerName);

}
