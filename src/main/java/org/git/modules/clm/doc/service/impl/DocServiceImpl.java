package org.git.modules.clm.doc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.git.modules.clm.doc.constant.DocConstant;
import org.git.modules.clm.doc.entity.Doc;
import org.git.modules.clm.doc.mapper.DocMapper;
import org.git.modules.clm.doc.service.DocService;
import org.git.modules.clm.doc.util.DocUtil;
import org.git.modules.clm.doc.util.OfficePdf;
import org.git.modules.clm.doc.vo.DocVO;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements DocService {

	@Value("${chain.document.upload-path-windows}")
	private String uploadPathWindows;
	@Value("${chain.document.upload-path-linux}")
	private String uploadPathLinux;

	@Override
	public IPage<DocVO> selectDocPage(IPage<DocVO> page, Map<String, Object> map, Query query, ChainUser chainUser) {
		DocVO docvo=new DocVO();
		docvo.setDocTypeCode("".equals((String)map.get("docTypeCode"))?null:(String)map.get("docTypeCode"));
		docvo.setDocName("".equals((String)map.get("docName"))?null:(String)map.get("docName"));
		docvo.setUserNum(chainUser.getUserId()+"");
		docvo.setOrgNum(chainUser.getDeptId()+"");
		return page.setRecords(baseMapper.selectDocPage(page, docvo));
	}
	/**
	 * 删除文件（minio方案)
	 * */
	@Transactional
	@Override
	public R deleteDoc(String docId,String docName,String docTypeName){
		try{
			String docpath=docTypeName+"/"+docName;
			baseMapper.deleteById(docId);
			DocConstant.mi.removeObject(DocConstant.TYSX_BUCKET_NAME, docpath);
			return R.success("删除成功！");
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail( "删除失败！");
		}
	}
	/**
	 * 删除文件（本地文档.方案)
	 * */
	@Transactional
	@Override
	public R deleteDocLocal(String docId,String docName,String docTypeName){
		try{
			baseMapper.deleteById(docId);
			String docpath=getDocPath()+"/"+docTypeName+"/"+docName;
			DocUtil.delFile(docpath);
			return R.success("删除成功！");
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail( "删除失败！");
		}
	}
	@Override
	public int isReleases(Doc doc){
		if("0".equals(doc.getIsRelease())){
			doc.setIsRelease("1");
		}else{
			doc.setIsRelease("0");
		}
		return baseMapper.updateById(doc);
	}
	@Override
	public int existence_db(String docname,String doc_type){
		int existence=0;
		Doc doc=new Doc();
		doc.setDocRootName(DocConstant.TYSX_BUCKET_NAME);
		doc.setDocTypeCode(doc_type);
		doc.setDocName(docname);
		List<Doc> list = baseMapper.selectList(Wrappers.query(doc));
		if(list.size()>0){
			existence=1;
		}
		return existence;
	}

	/**
	 * 单文件上传（minio版）
	 * */
	@Override
	public R uploadSing(@RequestParam("file") MultipartFile file, ChainUser chainUser, String docTypeCode, String docTypeName, String docAuth){
		String fileName=file.getOriginalFilename();
		String docSuffix=fileName.substring(fileName.lastIndexOf(".")+1);
		String docpath=docTypeName+"/"+fileName;
		int existence=0;
		/*先验证文件是否已经存在，若存在返回错误，提示修改文件名（0 不存在，1 存在，-1 io错误，-2 其他错误）*/
		/*existence=DocConstant.mi.is_existence(DocConstant.TYSX_BUCKET_NAME, docpath);*/
		existence=existence_db(fileName,docTypeCode);
		if(existence==0) {
			try {
				InputStream in= file.getInputStream();
				DocConstant.mi.putObject(DocConstant.TYSX_BUCKET_NAME, docpath, in);
				log.info("文件上传到服务器成功！");
				/*保存信息到数据库*/
				Doc doc = new Doc();
				doc.setDocRootName(DocConstant.TYSX_BUCKET_NAME);
				doc.setDocName(fileName);
				doc.setDocTypeCode(docTypeCode);
				doc.setDocTypeName(docTypeName);
				doc.setDocAuth(docAuth);
				doc.setDocSuffix(docSuffix);
				doc.setUserNum(chainUser.getUserId()+"");
				doc.setOrgNum(chainUser.getDeptId());
				doc.setCreateTime(new Date());
				baseMapper.insert(doc);
			} catch (Exception e) {
				log.info("文件上传到服务器失败！");
				log.error(e.toString());
				return R.fail(fileName+"上传失败！");
			}
			return R.success(fileName+"上传完成！");
		}else{
			return R.fail("文件"+fileName+"已存在！");
		}
	}

	/**
	 * 单文件上传（本地版）
	 * */
	@Override
	public R uploadSingLocal(@RequestParam("file") MultipartFile file, ChainUser chainUser, String docTypeCode, String docTypeName, String docAuth){
		String fileName=file.getOriginalFilename();
		fileName=fileName.lastIndexOf("\\")==-1?fileName:fileName.substring(fileName.lastIndexOf("\\")+1);
		String docSuffix=fileName.substring(fileName.lastIndexOf(".")+1);
		String docpath=getDocPath()+"/"+docTypeName+"/"+fileName;
		int existence=0;
		/*先验证文件是否已经存在，若存在返回错误，提示修改文件名（0 不存在，1 存在，-1 io错误，-2 其他错误）*/
		/*existence=DocConstant.mi.is_existence(DocConstant.TYSX_BUCKET_NAME, docpath);*/
		existence=existence_db(fileName,docTypeCode);
		if(existence==0) {
			try {
				InputStream in= file.getInputStream();
				Boolean b=DocUtil.cpFile(in,docpath);
				if(!b){
					return R.fail("文件"+fileName+"上传失败！");
				}
				log.info("文件上传到服务器成功！");
				/*保存信息到数据库*/
				Doc doc = new Doc();
				doc.setDocRootName(DocConstant.TYSX_BUCKET_NAME);
				doc.setDocName(fileName);
				doc.setDocTypeCode(docTypeCode);
				doc.setDocTypeName(docTypeName);
				doc.setDocAuth(docAuth);
				doc.setDocSuffix(docSuffix);
				doc.setUserNum(chainUser.getUserId()+"");
				doc.setOrgNum(chainUser.getDeptId());
				doc.setCreateTime(new Date());
				baseMapper.insert(doc);
			} catch (Exception e) {
				log.info("文件上传到服务器失败！");
				log.error(e.toString());
				return R.fail(fileName+"上传失败！");
			}
			return R.success(fileName+"上传完成！");
		}else{
			return R.fail("文件"+fileName+"已存在！");
		}
	}

	/**
	 * 文档下载（minio版本）
	 * */
	@Override
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String docName, String docTypeName)throws IOException{
		response.reset();
		String bucketName=DocConstant.TYSX_BUCKET_NAME;
		String file_path_name=docTypeName+"/"+docName;
		/*设置文件返回配置*/
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(docName, "UTF-8"));
		log.info("开始下载");
		InputStream in=DocConstant.mi.download(bucketName,file_path_name);
		OutputStream out=response.getOutputStream();
		//批量写入
		int b;
		byte[] by=new byte[1024];
		while((b=in.read(by))!=-1){
			out.write(by,0,b);
		}
		try {
			out.close();
			in.close();
		} catch (IOException e) {
			log.error(e.toString());
		}
		log.info("下载完毕");
	}
	/**
	 * 文档下载（本地版本）
	 * */
	@Override
	public void downLoadLocal(HttpServletRequest request, HttpServletResponse response, String docName, String docTypeName)throws IOException{
		response.reset();
		String file_path_name=docTypeName+"/"+docName;
		/*设置文件返回配置*/
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(docName, "UTF-8"));
		log.debug("开始下载");
		FileInputStream in=new FileInputStream(getDocPath()+file_path_name);
		OutputStream out=response.getOutputStream();
		//批量写入
		int b;
		byte[] by=new byte[1024];
		while((b=in.read(by))!=-1){
			out.write(by,0,b);
		}
		try {
			out.close();
			in.close();
		} catch (IOException e) {
			log.error(e.toString());
		}
		log.debug("下载完毕");
	}

	/**
	 * minio方式获取文件预览
	 * */
	@Override
	public void preview(HttpServletResponse response,String docName,String docTypeName,String is_switch)  throws IOException {
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		String pdfRootPath=DocConstant.PDF_ROOT_PATH_LINUX;
		String docRootPath=DocConstant.DOC_ROOT_PATH_LINUX;
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.indexOf("Windows")!=-1){
			pdfRootPath=DocConstant.PDF_ROOT_PATH_WINDOWS;
			docRootPath=DocConstant.DOC_ROOT_PATH_WINDOWS;
		}
		String inputPath=docRootPath+"/"+docTypeName+"/"+docName;
		String newPdfPath=pdfRootPath+"/"+ uuid+".pdf";
		String pdfPath= OfficePdf.fileHandler(newPdfPath,inputPath,docName,is_switch);
		log.info(os+"转换完成！");
		InputStream in = null;
		OutputStream out = null;
		try{
			if(pdfPath != null){
				in = new FileInputStream(pdfPath);
			}
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len = in.read(b)) != -1){
				out.write(b);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
			DocUtil.delFile(newPdfPath);
		}
	}
	/**
	 * 本地方式获取文件预览
	 * */
	@Override
	public void previewLocal(HttpServletResponse response,String docName,String docTypeName,String is_switch)  throws IOException {
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		String pdfRootPath=DocConstant.PDF_ROOT_PATH_LINUX;
		String docRootPath=DocConstant.DOC_ROOT_PATH_LINUX;
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.indexOf("Windows")!=-1){
			pdfRootPath=DocConstant.PDF_ROOT_PATH_WINDOWS;
			docRootPath=DocConstant.DOC_ROOT_PATH_WINDOWS;
		}
		String inputPath=docRootPath+"/"+docTypeName+"/"+docName;
		String newPdfPath=pdfRootPath+"/"+ uuid+".pdf";

		String file_path_name=getDocPath()+docTypeName+"/"+docName;
		String pdfPath= OfficePdf.fileHandler(newPdfPath,file_path_name,docName,is_switch);
		log.info(os+"转换完成！");
		InputStream in = null;
		OutputStream out = null;
		try{
			if(pdfPath != null){
				in = new FileInputStream(pdfPath);
			}
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while((len = in.read(b)) != -1){
				out.write(b);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
			DocUtil.delFile(newPdfPath);
		}
	}
	/**
	 * 获取配置文件中的文档路径
	 * */
	public String getDocPath(){
		String uploadPath=uploadPathLinux;
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.indexOf("Windows")!=-1){
			uploadPath=uploadPathWindows;
		}
		return uploadPath;
	}
}
