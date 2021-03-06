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
package org.git.modules.clm.param.service;

import org.git.modules.clm.param.entity.Product;
import org.git.modules.clm.param.vo.ProductVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;

import java.util.List;

/**
 * 服务类
 *
 * @author git
 * @since 2019-10-21
 */
public interface IProductService extends IService<Product> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param product
	 * @return
	 */
	IPage<ProductVO> selectProductPage(IPage<ProductVO> page, ProductVO product);

	R deleteproduct(String productNum);

	R saveProduct(Product product, ChainUser chainUser);

	R updateProduct(Product product, ChainUser chainUser);

	List<Product> listByProTarger(String proTarger);

	List<Product> listProduct(Product product);
}
