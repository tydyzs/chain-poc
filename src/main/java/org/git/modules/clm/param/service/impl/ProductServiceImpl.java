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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.core.mp.support.Condition;
import org.git.core.tool.utils.BeanUtil;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.clm.param.vo.ProductVO;
import org.git.modules.clm.param.mapper.ProductMapper;
import org.git.modules.clm.param.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.git.modules.system.entity.Menu;
import org.git.modules.system.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 服务实现类
 *
 * @author git
 * @since 2019-10-21
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
	@Autowired
	private ICrdProductService icrdproductservice;

	/**
	 * 转换类，将productVO进行字典翻译
	 * cc 2020-02-20
	 *
	 * @param productVOs
	 */
	public List<ProductVO> transVO(List<ProductVO> productVOs) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		for (ProductVO productVO : productVOs) {
			//产品状态
			productVO.setProductStatusCdName(DictCache.getValue("CD000178", productVO.getProductStatusCd()));
			//产品额度占用方式
			productVO.setLimitUsedTypeName(DictCache.getValue("CD000179", productVO.getLimitUsedType()));
			//风险暴露类型
			productVO.setRiskExposureTypeName(DictCache.getValue("CD000177", productVO.getRiskExposureType()));
			//表内外标志
			productVO.setInoutTableName(DictCache.getValue("CD000187", productVO.getInoutTable()));
			//机构名称
			productVO.setCreateDeptName(SysCache.getDeptName(productVO.getOrgNum()));
			//用户名称
			productVO.setCreateUserName(UserCache.getUser(productVO.getUserNum()).getName());
			list.add(productVO);
		}
		return list;
	}

	@Override
	public IPage<ProductVO> selectProductPage(IPage<ProductVO> page, ProductVO product) {
		//将ProductVO进行字典翻译
		List<ProductVO> productVOs = transVO(baseMapper.selectProductPage(page, product));
		return page.setRecords(productVOs);
	}

	@Override
	@Transactional
	public R deleteproduct(String productNum) {
		String[] productNums = productNum.split(",");
		for (String num : productNums) {
			int i = baseMapper.selectApprove(num);
			if (i > 0) {
				return R.fail("业务品种编号为："+ num +"的品种下存在批复信息，不可删除！");
			} else {
				//删除业务品种
				Product p = new Product();
				p.setProductNum(num);
				baseMapper.delete(Wrappers.query(p));
				//删除关联关系
				CrdProduct cp = new CrdProduct();
				cp.setProductNum(num);
				icrdproductservice.remove(Wrappers.query(cp));
			}
		}
		return R.success("删除成功！");
	}

	@Override
	public R saveProduct(Product product, ChainUser chainUser) {
		product.setUserNum(chainUser.getUserId() + "");
		product.setOrgNum(chainUser.getDeptId() + "");
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
		return R.data(baseMapper.insert(product));
	}

	@Override
	public R updateProduct(Product product, ChainUser chainUser) {
		product.setUpdateUserNum(chainUser.getUserId() + "");
		product.setUpdateOrgNum(chainUser.getDeptId() + "");
		product.setUpdateTime(new Date());
		return R.data(baseMapper.updateById(product));
	}

	@Override
	public List<Product> listByProTarger(String proTarger) {
		return baseMapper.listByProTarger(proTarger);
	}

	@Override
	public List<Product> listProduct(Product product) {
		return baseMapper.selectList(Condition.getQueryWrapper(product));
	}
}
