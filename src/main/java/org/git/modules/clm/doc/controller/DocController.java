package org.git.modules.clm.doc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.git.core.mp.support.Condition;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.secure.annotation.PreAuth;
import org.git.core.tool.api.R;
import org.git.core.tool.constant.ChainConstant;
import org.git.core.tool.constant.RoleConstant;
import org.git.modules.clm.doc.constant.DocConstant;
import org.git.modules.clm.doc.entity.Doc;
import org.git.modules.clm.doc.service.DocService;
import org.git.modules.clm.doc.vo.DocVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


/**
 * 首页
 *
 * @author shenhuancheng
 */
@RestController
@RequestMapping("git-doc")
@Slf4j
@ApiIgnore
@AllArgsConstructor
@Api(value = "文档管理", tags = "接口")
public class DocController {

	@Autowired
	private DocService service;


	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperation(value = "详情", notes = "传入doc")
	public R<IPage<Doc>> page(@ApiIgnore @RequestParam Map<String, Object> doc, Query query) {
		IPage<Doc> pages = service.page(Condition.getPage(query), Condition.getQueryWrapper(doc, Doc.class));
		return R.data(pages);
	}

	/**
	 * 文档列表
	 */
	@GetMapping("/list1")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "docTypeCode", value = "文档分类编码", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "docName", value = "文档名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "列表", notes = "传入docTypeCode和docName")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<Doc>> list(@ApiIgnore @RequestParam Map<String, Object> doc, Query query, ChainUser chainUser) {
		QueryWrapper<Doc> queryWrapper = Condition.getQueryWrapper(doc, Doc.class);
		IPage<Doc> pages = service.page(Condition.getPage(query), (!chainUser.getTenantId().equals(ChainConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Doc::getId, chainUser.getTenantId()) : queryWrapper);
		log.info(String.valueOf(chainUser.getUserId()));
		return R.data(pages);
	}
	/**
	 * 多表联合查询自定义分页
	 */
	@GetMapping("/query_doc")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "docTypeCode", value = "文档分类编码", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "docName", value = "文档名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "自定义分页", notes = "传入doc")
	public R<IPage<DocVO>> query_doc(@ApiIgnore @RequestParam Map<String, Object> map, Query query, ChainUser chainUser) {
		IPage<DocVO> pages = service.selectDocPage(Condition.getPage(query), map,query,chainUser);
		return R.data(pages);
	}


	/**
	 * @Description: 删除文件
	 * @author shenhuancheng
	 *
	 */
	@PostMapping("/delete_doc")
	@ResponseBody
	@ApiOperation(value = "删除文件", notes = "传入文档id，文档路径和名称")
	public R delete_doc(String docId,String docName,String docTypeName){
		//return service.deleteDoc(docId,docName,docTypeName);//minio方案暂留
		return service.deleteDocLocal(docId,docName,docTypeName);
	}
	/**
	 * @Description: 发布文件
	 * @author shenhuancheng
	 *
	 */
	@PostMapping("/isReleases")
	@ResponseBody
	@ApiOperation(value = "发布文件", notes = "传入doc")
	public R isReleases(Doc doc){
		return R.data(service.isReleases(doc));
	}
	/**
	 * @Description: 验证文件是否存在(minio验证方法）
	 * @author shenhuancheng
	 *
	 */
	@PostMapping("/is_existence_minio")
	@ResponseBody
	@ApiOperation(value = "验证文件是否存在(minio）", notes = "传入文件名和文档分类")
	public int existence_minio(String docname,String doc_type) {
		int existence=DocConstant.mi.is_existence(DocConstant.TYSX_BUCKET_NAME, doc_type+"/"+docname);
		return existence;
	}
	/**
	 * @Description: 验证文件是否存在(数据库验证方法）
	 * @author shenhuancheng
	 *
	 */
	@PostMapping("/is_existence_db")
	@ApiOperation(value = "验证文件是否存在(数据库验证方法）", notes = "传入文件名和文档分类")
	public int existence_db(String docname,String doc_type) {
		return service.existence_db(docname,doc_type);
	}
	/**
	 * @Description: 单文件上传
	 * @author shenhuancheng
	 *
	 */
	@PostMapping("/uploadSing")
	@ResponseBody
	@Transactional
	@ApiOperation(value = "文件上传", notes = "传入文件，文件目录，文件目录编码，文件权限")
	public R uploadSing(@RequestParam("file") MultipartFile file,ChainUser chainUser,String docTypeCode,String docTypeName,String docAuth){
		//return service.uploadSing(file,chainUser,docTypeCode,docTypeName,docAuth);//minio版
		return service.uploadSingLocal(file,chainUser,docTypeCode,docTypeName,docAuth);
	}
	/**
	 * @Description: 文件下载接口
	 * @author shenhuancheng
	 * @email
	 */
	@RequestMapping("/download")
	@ApiOperation(value = "文件下载", notes = "传入文件名，文件目录名")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String docName, String docTypeName) throws IOException {
		//service.downLoad(request,response,docName,docTypeName);//minio版本。
		service.downLoadLocal(request,response,docName,docTypeName);
	}

	/**
	 * @Description: 多文件上传
	 * @author shenhuancheng
	 * @email
	 */
	@PostMapping("/uploads")
	@ResponseBody
	@Transactional
	public R uploads(@RequestParam("file") MultipartFile[] files,ChainUser chainUser,String docTypeCode,String docTypeName,String docAuth) throws IOException {
		return R.fail("待开发！");
	}

	/**
	 * @Description: 文件预览接口
	 * @author shenhuancheng
	 * @email
	 */
	@GetMapping("/preview")
	@ApiOperation(value = "文件预览", notes = "传入文件名，文件目录名")
	public void preview(HttpServletResponse response,String docName,String docTypeName,String is_switch) throws IOException {
		//service.preview(response,docName,docTypeName,is_switch);//minio获取文件
		service.previewLocal(response,docName,docTypeName,is_switch);//本地获取文件
	}
}
