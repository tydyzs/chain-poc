package org.git.modules.clm.doc.util;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;

public class DocUtil {
	public static void main(String[] args) {
		DocUtil.delFile("D:\\aaaa");
		System.out.println("删除完成！");
	}
	/*
	*删除文件和文件夹（文件夹包括里面所有内容）
	*/
	public static void delFile(String str){
		//delFile("D:\\aaaa");
		File file=new File(str);
		while(file.exists()){//存在
			if(file.isFile()){//文件
				file.delete();
				return;
			}
			String [] s=file.list();//获取文件夹中的内容，文件夹名数组
			if(s.length==0){//空文件夹
				file.delete();//是否删除当前文件夹
				return;
			}
			for(int i=0;i<s.length;i++){
				s[i]=str+"\\"+s[i];
				delFile(s[i]);
			}
		}
	}

	/**
	 * 保存文件方法
	 * */
	public static Boolean cpFile(InputStream in,String outPath){
		try{
			File file=new File(outPath.substring(0,outPath.lastIndexOf("/")));
			if (!file.exists()) {
				file.mkdirs();
			}
			File doc=new File(outPath);
			doc.createNewFile();
			FileOutputStream out=new FileOutputStream(outPath);
			return DocUtil.cpFile(in,out);
		}catch(Exception e){
			return false;
		}
	}
	public static Boolean cpFile(InputStream in,FileOutputStream out){
		try{
			int b;
			byte[] by=new byte[1024];
			while((b=in.read(by))!=-1){
				out.write(by,0,b);
			}
			in.close();
			out.close();
			System.out.println("复制完毕");
		}catch(Exception e){
			return false;
		}
		return true;
	}
}
