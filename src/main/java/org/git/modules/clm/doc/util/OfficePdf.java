package org.git.modules.clm.doc.util;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.git.core.tool.utils.StringUtil;

import java.io.File;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by lenovo12 on 2018/8/18.
 * doc docx ex.. ex..x ppt pptx
 */
public final class OfficePdf {
	private OfficePdf(){}
	public static OfficeManager officeManager = null;
	/**
	 * 将office格式的文件转为pdf
	 * @param sourceFilePath 源文件路径
	 * @return
	 */
	public static File openOfficeToPDF(String outputFilePath,String sourceFilePath){
		return OfficePdf(outputFilePath,sourceFilePath);
	}

	/**
	 * 将office文档转换为pdf文档
	 * @param sourceFilePath 原文件路径
	 * @return
	 */
	public static File OfficePdf(String outputFilePath,String sourceFilePath){
		try{
			if(StringUtil.isEmpty(sourceFilePath))
			{
				//打印日志...
				return null;
			}
			File sourceFile = new File(sourceFilePath);
			if(!sourceFile.exists())
			{
				//打印日志...
				return null;
			}
			//启动openOffice
			if(OfficePdf.officeManager==null) {
				OfficePdf.officeManager = getOfficeManager();
			}
			OfficeDocumentConverter converter = new OfficeDocumentConverter(OfficePdf.officeManager);
			return convertFile(sourceFile,outputFilePath,sourceFilePath,converter);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("转换异常");
		}finally {
           /*if(officeManager != null){
               try {
                   officeManager.stop();
               } catch (OfficeException e) {
                   e.printStackTrace();
               }
           }*/
		}
		return null;
	}

	/**
	 * 转换文件
	 * @param sourceFile 原文件
	 * @param after_convert_file_path 转换后存放位置
	 * @param sourceFilePath 原文件路径
	 * @param converter 转换器
	 * @return
	 */
	public static File convertFile(File sourceFile,
								   String after_convert_file_path,String sourceFilePath,OfficeDocumentConverter converter) throws OfficeException {
		File outputFile = new File(after_convert_file_path);
		if(!outputFile.getParentFile().exists()){
			//如果上级目录不存在也则创建一个
			outputFile.getParentFile().mkdirs();
		}
		converter.convert(sourceFile,outputFile);
		return outputFile;
	}

	public static OfficeManager getOfficeManager(){
		DefaultOfficeManagerBuilder builder = new DefaultOfficeManagerBuilder();
		builder.setOfficeHome(getOfficeHome());
		OfficeManager officeManager = builder.build();
		try {
			officeManager.start();
		} catch (OfficeException e) {
			//打印日志
			System.out.println("start openOffice Fail!");
			e.printStackTrace();
		}
		return officeManager;
	}

	/**
	 * 获取转换后文件存放的路径
	 * @param sourceFilePath 源文件
	 * @return
	 */
	public static String getAfterConverFilePath(String filepath, String sourceFilePath){
		//截取源文件文件名
		String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1);
		return filepath+ sourceFileName.replaceAll("\\."+FileUtil.getFileSuffix(sourceFileName),".pdf");
	}

	/**
	 * 获取openOffice的安装目录
	 * @return
	 */
	public static String getOfficeHome(){
		String osName = System.getProperty("os.name");
		if(Pattern.matches("Windows.*",osName))
		{
			return "C:/Program Files (x86)/OpenOffice 4";
		}
		else if(Pattern.matches("Linux.*",osName))
		{
			return "/usr/temp";
		}
		else if (Pattern.matches("Mac.*",osName))
		{
			return "/Application/openOfficeSoft";
		}
		return null;
	}

	/**
	 * 文件处理
	 * @param fileName;is_switch(是否需要转换pdf，0不需要）
	 * @return
	 */
	public static String fileHandler(String outputFilePath, String inputfilePath, String fileName,String is_switch){
		String fileSuffix = FileUtil.getFileSuffix(fileName);
		System.out.println(fileSuffix);
		if("0".equals(is_switch))
		{
			return inputfilePath;
		}
		else
		{
			return openOfficeToPDF(outputFilePath,inputfilePath).getAbsolutePath();
		}

	}
	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replaceAll("-","");
		String rootPath="/data/officePdf";
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.indexOf("Windows")!=-1){
			rootPath="d:/officePdf/";
		}
		String inputPath="d:/aaa/pdf.pdf";
		String fileName="pdf.pdf";
		String pdfPath=rootPath+"/"+ uuid+".pdf";
		fileHandler(pdfPath,inputPath,fileName,"0");
		System.out.println(os+"转换完成！");

	}
}

