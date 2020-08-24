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
package org.git.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.git.common.cache.DictCache;
import org.git.common.cache.SysCache;
import org.git.common.cache.UserCache;
import org.git.core.mp.support.Condition;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.utils.Func;
import org.git.core.tool.utils.StringPool;
import org.git.modules.clm.front.entity.TerminalConfig;
import org.git.modules.clm.param.entity.Crd;
import org.git.modules.clm.param.entity.Product;
import org.git.modules.system.entity.Dept;
import org.git.modules.system.entity.Dict;
import org.git.modules.system.entity.User;
import org.git.modules.system.mapper.DictMapper;
import org.git.modules.system.service.IDictService;
import org.git.modules.system.vo.DictVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.git.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

	@Override
	public IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict) {
		return page.setRecords(baseMapper.selectDictPage(page, dict));
	}

	@Override
	public List<DictVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public String getValue(String code, String dictKey) {
		return Func.toStr(baseMapper.getValue(code, dictKey), StringPool.EMPTY);
	}

	@Override
	public List<Dict> getList(String code) {
		return baseMapper.getList(code);
	}

	@Override
	@CacheEvict(cacheNames = {DICT_CACHE}, allEntries = true)
	public boolean submit(Dict dict) {
//		LambdaQueryWrapper<Dict> lqw = Wrappers.<Dict>query().lambda().eq(Dict::getCode, dict.getCode()).eq(Dict::getDictKey, dict.getDictKey());
//		Integer cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(Dict::getId, dict.getId()));
//		if (cnt > 0) {
//			throw new ApiException("当前字典键值已存在!");
//		}
		Dict checkDict = new Dict();
		checkDict.setParentId(dict.getParentId());
		checkDict.setDictKey(dict.getDictKey());
		Integer cntByKey = baseMapper.selectCount(Condition.getQueryWrapper(checkDict));
		if (cntByKey > 0) {
			throw new ApiException("当前字典键值已存在!");
		}
		checkDict.setDictValue(dict.getDictValue());
		checkDict.setDictKey(null);
		Integer cntByValue = baseMapper.selectCount(Condition.getQueryWrapper(checkDict));
		if (cntByValue > 0) {
			throw new ApiException("当前字典名称已存在!");
		}
		dict.setIsDeleted(ChainConstant.DB_NOT_DELETED);
		Dict parentDict = DictCache.getById(dict.getParentId());
		if (parentDict != null) {
			dict.setCode(parentDict.getCode());
			dict.setCodeCn(parentDict.getCodeCn());
		}

		return saveOrUpdate(dict);
	}

	@Override
	public boolean removeDict(String ids) {
		Integer cnt = baseMapper.selectCount(Wrappers.<Dict>query().lambda().in(Dict::getParentId, Arrays.asList(Func.toStrArray(ids))));
		if (cnt > 0) {
			throw new ApiException("请先删除子节点!");
		}
		return removeByIds(Arrays.asList(Func.toStrArray(ids)));
	}

	/**
	 * 字典转换器
	 *
	 * @param code
	 * @return Map<String, String>
	 * @author chenchuan
	 */
	@Override
	public Map<String, String> dictTranslate(String code) {
		//从缓存中获取相关信息
		Map<String, String> userMap = new HashMap<String, String>();
		if ("user".equals(code)) {//用户账号翻译
//			List<User> userList = userMapper.selectList(Condition.getQueryWrapper(new User()));
			List<User> userList = UserCache.getAllUser();
			for (User user : userList) {
				userMap.put(user.getAccount(), user.getName());
			}
		} else if ("userId".equals(code)) {//用户ID翻译
//			List<User> userList = userMapper.selectList(Condition.getQueryWrapper(new User()));
			List<User> userList = UserCache.getAllUser();
			for (User user : userList) {
				userMap.put(user.getId(), user.getName());
			}
		} else if ("org".equals(code)) {//机构翻译
			//		List<Dept> deptList = deptMapper.selectList(Condition.getQueryWrapper(new Dept()));
			List<Dept> deptList = SysCache.getAllDept();
			for (Dept dept : deptList) {
				userMap.put(dept.getId(), dept.getDeptName());
			}
		} else if ("product".equals(code)) {//业务品种翻译
			List<Product> productList = SysCache.getAllProduct();
			for (Product product : productList) {
				userMap.put(product.getProductNum(), product.getProductName());
			}
		} else if ("crd".equals(code)) {//额度品种翻译
			List<Crd> crdList = SysCache.getAllCrd();
			for (Crd crd : crdList) {
				userMap.put(crd.getCrdProductNum(), crd.getCrdProductName());
			}
		} else if ("system".equals(code)) {//来源系统翻译
			List<TerminalConfig> crdList = SysCache.getAllTerminalConfig();
			for (TerminalConfig tc : crdList) {
				userMap.put(tc.getTerminalCode(), tc.getTerminalName());
			}
		} else {//字典翻译
			List<Dict> dictList = DictCache.getList(code);
			for (Dict dict : dictList) {
				userMap.put(dict.getDictKey(), dict.getDictValue());
			}
		}
		return userMap;
	}

	@Override
	public List<DictVO> treeByCode(String code) {
		return ForestNodeMerger.merge(baseMapper.treeByCode(code));
	}

	@Override
	public List<DictVO> keyAsValuesTree(String code) {
		return ForestNodeMerger.merge(baseMapper.keyAsValuesTree(code));
	}
}
