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
import lombok.extern.slf4j.Slf4j;
import org.git.common.utils.CommonUtil;
import org.git.common.utils.ElTree;
import org.git.core.mp.support.Condition;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.CrdProduct;
import org.git.modules.clm.param.service.ICrdProductService;
import org.git.modules.clm.param.vo.CrdVO;
import org.git.modules.clm.param.mapper.CrdMapper;
import org.git.modules.clm.param.service.ICrdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 *
 * @author git
 * @since 2019-10-21
 */
@Service
@Slf4j
public class CrdServiceImpl extends ServiceImpl<CrdMapper, Crd> implements ICrdService {
	@Autowired
	private ICrdProductService icrdproductservice;

	@Override
	public IPage<CrdVO> selectCrdPage(IPage<CrdVO> page, CrdVO crd) {
		return page.setRecords(baseMapper.selectCrdPage(page, crd));
	}

	@Override
	public synchronized Map<String, String> getCrdId(String crdId) {
		Map<String, String> map = new HashMap<String, String>();
		int i = crdId.length() == 2 ? 4 : 8;
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(i);
		formatter.setGroupingUsed(false);
		String maxid = baseMapper.getCrdMaxId(crdId);
		String newcrdId = "";
		//没有子集直接追加
		if (maxid == null) {
			newcrdId = crdId + (crdId.length() == 2 ? "01" : "0001");
		} else {
			newcrdId = formatter.format(Integer.parseInt(maxid) + 1);
		}
		map.put("crdId", newcrdId);
		return map;
	}

	@Override
	@Transactional
	public R deletecrd(String crdProductNum) {
		int i = baseMapper.selectCrdProduct(crdProductNum);
		if (i > 0) {
			return R.fail("该额度下存在批复信息，不可删除！");
		} else {
			//删除该额度及下级额度所有与业务产品的关联关系
			icrdproductservice.deleteCrdProduct(crdProductNum);
			//删除额度以及其所有下级额度
			Crd crd = new Crd();
			crd.setCrdProductNum(crdProductNum);
			baseMapper.delete(Wrappers.query(crd));
			crd = new Crd();
			crd.setSuperCrdNum(crdProductNum);
			baseMapper.delete(Wrappers.query(crd));
			return R.success("删除成功！");
		}
	}

	@Override
	public R<List<Map<String, Object>>> queryTree() {
		List<Map<String, Object>> list = baseMapper.selectMaps(null);
		ElTree et = new ElTree();
		List<Map<String, Object>> resData = et.Transformation(list, "CRD_PRODUCT_NUM", "SUPER_CRD_NUM", "CRD_PRODUCT_NAME");
		return R.data(resData);
	}

	@Override
	public synchronized R saveCrd(Crd crd, ChainUser chainUser) {
		Map map = new HashMap();
		map.put("status", "0");
		map.put("str", "新增成功！");
		//判断额度产品名称是否重复
		String crdProductName = crd.getCrdProductName();
		if(baseMapper.getCrdByName(crdProductName) == null){
			//id已存在，重新生成。
			String crdId = crd.getCrdProductNum();
			while (baseMapper.selectById(crdId) != null) {
				NumberFormat formatter = NumberFormat.getNumberInstance();
				formatter.setMinimumIntegerDigits(crdId.length());
				formatter.setGroupingUsed(false);
				crdId = formatter.format(Integer.parseInt(crdId) + 1);
				crd.setCrdProductNum(crdId);
				map.put("status", "1");
				map.put("str", "编码已存在；已从新生成编码：" + crdId);
			}
			crd.setCreateTime(CommonUtil.getWorkDateTime());
			crd.setUpdateTime(CommonUtil.getWorkDateTime());
			crd.setUserNum(chainUser.getUserId() + "");
			crd.setUpdateUserNum(chainUser.getUserId() + "");
			crd.setOrgNum(chainUser.getDeptId());
			crd.setUpdateOrgNum(chainUser.getDeptId());
			int i = baseMapper.insert(crd);
		} else {
			return R.fail("额度产品名称已存在！");
		}

		return R.data(map);
	}

	@Override
	public R updateCrd(Crd crd, ChainUser chainUser) {
		crd.setUpdateUserNum(chainUser.getUserId() + "");
		crd.setUpdateOrgNum(chainUser.getDeptId());
		crd.setUpdateTime(CommonUtil.getWorkDateTime());
		return R.data(baseMapper.updateById(crd));
	}

	@Override
	public List<Crd> listCrd(Crd crd) {
		return baseMapper.selectList(Condition.getQueryWrapper(crd));
	}

	@Override
	public Crd getCrdByName(String crdProductName){
		return baseMapper.getCrdByName(crdProductName);
	}
}
