package org.git.modules.clm.doc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.git.modules.clm.doc.entity.Doc;
import org.git.modules.clm.doc.vo.DocVO;

import java.util.List;

public interface DocMapper extends BaseMapper<Doc> {
	/**
	 * 自定义分页
	 *
	 * @param page   分页
	 * @param docvo 实体
	 * @return List<NoticeVO>
	 */
	List<DocVO> selectDocPage(IPage page, DocVO docvo);
}
