/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.git.modules.clm.param.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.cache.DictCache;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.StringUtil;
import org.git.modules.clm.front.dto.jxrcb.JxrcbBizConstant;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.mapper.CrdProductMapper;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.clm.param.vo.CrdProductVO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author git
 * @since 2019-10-21
 */
@Service
public class CrdProductServiceImpl extends ServiceImpl<CrdProductMapper, CrdProduct> implements ICrdProductService {

	@Override
	public IPage<CrdProductVO> selectCrdProductPage(IPage<CrdProductVO> page, CrdProductVO crdProduct) {
		List<CrdProductVO> crdProductVOS = ProductTransVO(baseMapper.selectCrdProductPage(page, crdProduct));
		return page.setRecords(crdProductVOS);
	}

	/**
	 * 转换类，将CrdProductVO进行字典翻译
	 * zwj 2020-02-28
	 *
	 * @param CrdProductVOs
	 */
	public List<CrdProductVO> ProductTransVO(List<CrdProductVO> CrdProductVOs) {
		List<CrdProductVO> list = new ArrayList<CrdProductVO>();
		String productTypeName = "";
		String projectTypeName = "";
		for (CrdProductVO crdProductVO : CrdProductVOs) {
			if(crdProductVO.getProductType() != null && !"".equals(crdProductVO.getProductType())){
				String[] productTypes = crdProductVO.getProductType().split(",");
				StringBuffer productTypeNames = new StringBuffer();
				for (String productType : productTypes) {
					productType = DictCache.getValue("CD000061", productType);
					productTypeNames.append(",").append(productType);
				}
				productTypeName = productTypeNames.substring(1);
			}
			if(crdProductVO.getProjectType() != null && !"".equals(crdProductVO.getProjectType())){
				String[] projectTypes = crdProductVO.getProjectType().split(",");
				StringBuffer projectTypeNames = new StringBuffer();
				for (String projectType : projectTypes) {
					projectType = DictCache.getValue("CD000172", projectType);
					projectTypeNames.append(",").append(projectType);
				}
				projectTypeName = projectTypeNames.substring(1);
			}
			//业务种类名称
			crdProductVO.setProductTypeName(productTypeName);
			//项目类型名称
			crdProductVO.setProjectTypeName(projectTypeName);
			//担保方式名称
			crdProductVO.setMainGuaranteeTypeName(DictCache.getValue("CD000100", crdProductVO.getMainGuaranteeType()));
			list.add(crdProductVO);
		}
		return list;
	}

	/**
	 * 新增关联关系
	 *
	 * @param crdProduct
	 * @param chainUser
	 * @return
	 */
	@Override
	public R saveCrdProduct(CrdProduct crdProduct, ChainUser chainUser) {
		/**
		 * 先查询关联关系是否存在
		 */
		String crdProductNum = crdProduct.getCrdProductNum();
		crdProduct.setCrdProductNum(null);
		List<CrdProduct> list = baseMapper.selectList(Wrappers.query(crdProduct));
		if (list.size() > 0) {
			return R.fail("关联关系已存在！");
		} else {
			crdProduct.setCrdProductNum(crdProductNum);
			crdProduct.setUserNum(chainUser.getUserId() + "");
			crdProduct.setOrgNum(chainUser.getDeptId());
			crdProduct.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
			crdProduct.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
			return R.data(baseMapper.insert(crdProduct));
		}
	}

	/**
	 * 删除某额度及所有下级额度对应的关联关系表的数据
	 *
	 * @param crdProductNum
	 * @return
	 */
	@Override
	public int deleteCrdProduct(String crdProductNum) {
		int i = baseMapper.deleteCrdProduct(crdProductNum);
		return i;
	}


	/**
	 * 根据业务品种查询额度数据
	 *
	 * @param （业务品种；业务种类；担保方式；客户类型；项目类型；是否低风险；额度类型);(productNum，productType,mainGuaranteeType,custType,projectType,crdProductNumLike)
	 * @return
	 */
	@Override
	public Crd selectCrd(CrdProduct crdProduct) {
		if (StringUtil.isEmpty(crdProduct.getCrdProductType()) || StringUtil.isEmpty(crdProduct.getCustType())) {
			return null;
		}
		CrdProduct p = new CrdProduct();
		p.setCrdProductType(crdProduct.getCrdProductType());//额度类型
		p.setCustType(crdProduct.getCustType());//客户类型

		if (JxrcbBizConstant.CRD_TYPE_SX.equals(crdProduct.getCrdProductType())) {//授信额度
			if (JxrcbBizConstant.CUST_TYPE_GR.equals(crdProduct.getCustType())) {//个人
				p.setMainGuaranteeType(crdProduct.getMainGuaranteeType());//担保方式
			} else if (JxrcbBizConstant.CUST_TYPE_GS.equals(crdProduct.getCustType())) {//公司
				p.setProductNum(crdProduct.getProductNum());//业务品种
				p.setProductType(crdProduct.getProductType());//业务种类
			} else if (JxrcbBizConstant.CUST_TYPE_TY.equals(crdProduct.getCustType())) {//同业
				p.setProductNum(crdProduct.getProductNum());//业务品种
			} else {
				return null;
			}
			if (JxrcbBizConstant.YES.equals(crdProduct.getIsLowRisk())) {//低风险业务
				p.setIsLowRisk(crdProduct.getIsLowRisk());
				p.setProductNum(null);
				p.setProductType(null);
				p.setMainGuaranteeType(null);
			}
		} else if (JxrcbBizConstant.CRD_TYPE_DB.equals(crdProduct.getCrdProductType())) {//担保额度
			p.setMainGuaranteeType(crdProduct.getMainGuaranteeType());//担保方式
		} else if (JxrcbBizConstant.CRD_TYPE_TD.equals(crdProduct.getCrdProductType())) {//第三方额度
			p.setProjectType(crdProduct.getProjectType());//项目类型
		}

		System.out.println("正在查询额度品种，参数：" + p.toString());
		return baseMapper.selectCrd(p);
	}
}
