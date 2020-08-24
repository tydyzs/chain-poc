package org.git.modules.clm.doc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.git.modules.clm.doc.entity.Doc;
import org.git.modules.clm.doc.vo.DocVO;
import org.git.core.mp.support.Query;
import org.git.core.secure.ChainUser;
import org.git.core.tool.api.R;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface DocService extends IService<Doc> {
	IPage<DocVO> selectDocPage(IPage<DocVO> page, Map<String, Object> map, Query query, ChainUser chainUser );
	R deleteDoc(String docId,String docName,String docTypeName);
	R deleteDocLocal(String docId,String docName,String docTypeName);
	int isReleases(Doc doc);
	int existence_db(String docname,String doc_type);
	R uploadSing(@RequestParam("file") MultipartFile file, ChainUser chainUser, String docTypeCode, String docTypeName, String docAuth);
	R uploadSingLocal(@RequestParam("file") MultipartFile file, ChainUser chainUser, String docTypeCode, String docTypeName, String docAuth);
	void downLoad(HttpServletRequest request, HttpServletResponse response, String docName, String docTypeName) throws IOException;
	void downLoadLocal(HttpServletRequest request, HttpServletResponse response, String docName, String docTypeName) throws IOException;
	void preview(HttpServletResponse response,String docName,String docTypeName,String is_switch) throws IOException;
	void previewLocal(HttpServletResponse response,String docName,String docTypeName,String is_switch) throws IOException;
}
