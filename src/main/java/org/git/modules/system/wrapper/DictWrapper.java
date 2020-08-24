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
package org.git.modules.system.wrapper;

import org.git.common.cache.DictCache;
import org.git.modules.system.entity.Dict;
import org.git.modules.system.vo.DictVO;
import org.git.core.mp.support.BaseEntityWrapper;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.node.ForestNodeMerger;
import org.git.core.tool.node.INode;
import org.git.core.tool.utils.BeanUtil;
import org.git.core.tool.utils.Func;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

	public static DictWrapper build() {
		return new DictWrapper();
	}

	@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
		assert dictVO != null;
		if (Func.equals(dict.getParentId(), ChainConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(ChainConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = DictCache.getById(dict.getParentId());
			if(parent != null){
			dictVO.setParentName(parent.getDictValue());
			}
		}
		return dictVO;
	}

	public List<INode> listNodeVO(List<Dict> list) {
		List<INode> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
