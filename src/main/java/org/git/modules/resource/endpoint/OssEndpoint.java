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
package org.git.modules.resource.endpoint;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.git.core.launch.constant.AppConstant;
import org.git.core.oss.model.ChainFile;
import org.git.core.oss.model.OssFile;
import org.git.core.tool.api.R;
import org.git.core.tool.utils.Func;
import org.git.modules.resource.builder.OssBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@Api(value = "对象存储端点", tags = "对象存储端点")
@RequestMapping(AppConstant.APPLICATION_RESOURCE_NAME + "/oss/endpoint")
public class OssEndpoint {

	private OssBuilder ossBuilder;

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return Bucket
	 */
	@SneakyThrows
	@PostMapping("/make-bucket")
	public R makeBucket(@RequestParam String bucketName) {
		ossBuilder.template().makeBucket(bucketName);
		return R.success("创建成功");
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-bucket")
	public R removeBucket(@RequestParam String bucketName) {
		ossBuilder.template().removeBucket(bucketName);
		return R.success("删除成功");
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileName       存储桶对象名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/copy-file")
	public R copyFile(@RequestParam String fileName, @RequestParam String destBucketName, String destFileName) {
		ossBuilder.template().copyFile(fileName, destBucketName, destFileName);
		return R.success("操作成功");
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public R<OssFile> statFile(@RequestParam String fileName) {
		return R.data(ossBuilder.template().statFile(fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public R<String> filePath(@RequestParam String fileName) {
		return R.data(ossBuilder.template().filePath(fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	public R<String> fileLink(@RequestParam String fileName) {
		return R.data(ossBuilder.template().fileLink(fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	public R<ChainFile> putFile(@RequestParam MultipartFile file) {
		ChainFile chainFile = ossBuilder.template().putFile(file.getOriginalFilename(), file.getInputStream());
		return R.data(chainFile);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-by-name")
	public R<ChainFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
		ChainFile chainFile = ossBuilder.template().putFile(fileName, file.getInputStream());
		return R.data(chainFile);
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	public R removeFile(@RequestParam String fileName) {
		ossBuilder.template().removeFile(fileName);
		return R.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	public R removeFiles(@RequestParam String fileNames) {
		ossBuilder.template().removeFiles(Func.toStrList(fileNames));
		return R.success("操作成功");
	}

}
