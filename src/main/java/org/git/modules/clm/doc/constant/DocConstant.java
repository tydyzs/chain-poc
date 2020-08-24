package org.git.modules.clm.doc.constant;

import io.minio.MinioClient;
import org.git.modules.clm.doc.util.MinioClientUtils;


/**
 * 文档管理常量
 *
 * @author shenhuancheng
 */
public class DocConstant {
	/**
	 * MinIo文档服务器公共数据
	 */
	public static MinioClientUtils mi=null;
	public static MinioClient client=null;

	public static final String ENDPOINT = "http://127.0.0.1:9000/";
	public static final String ACCESSKEY = "PADSBTN7YU4SRZHYKH15";
	public static final String SECRETKEY ="OpBEvVqCK0tUIoc1WWy4Vqlet7uXnjrEDSSw7Vah";

	/**本地/服务器 minio配置
	public static final String ENDPOINT = "http://127.0.0.1:9000/";
	public static final String ACCESSKEY = "QWK5UBVU2JPAAJFFIAM5";
	public static final String SECRETKEY ="otTUFZeIc+5dEwyiE+B8Pj4hUqnOAkAahV1ebx8U";
	 */
	/**
	 * 统一授信minio桶名称
	 */
	public static final String TYSX_BUCKET_NAME = "tysx";
	/**
	 * pdf文档转换临时文件路径（linux/windows)；
	 * 统一授信minio桶路径（linux/windows)
	 */
	public static String PDF_ROOT_PATH_LINUX="/data/ucs/officePdf";
	public static String DOC_ROOT_PATH_LINUX="/data/ucs/minio/Photos/"+DocConstant.TYSX_BUCKET_NAME;
	public static String PDF_ROOT_PATH_WINDOWS="d:/officePdf/";
	public static String DOC_ROOT_PATH_WINDOWS="D:/MinIo/Photos/"+DocConstant.TYSX_BUCKET_NAME;


}
