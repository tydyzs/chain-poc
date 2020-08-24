package org.git.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.thoughtworks.xstream.mapper.Mapper;
import org.git.modules.clm.front.dto.jxrcb.Request;

import java.io.*;
import java.util.TimeZone;

/**
 * XStream XML解析工具
 */
public class XStreamUtil {


	private static final DateConverter DATE_CONVERTER =
		new DateConverter("yyyy-MM-dd HH:mm:ss", null, TimeZone.getTimeZone("GMT+8"));

	private static NameCoder NAME_CODER = new XmlFriendlyNameCoder("-_", "_");
	private static DomDriver DOM_DRIVER = new DomDriver("UTF-8", NAME_CODER);
	public static Xpp3Driver XPP3_DRIVER = new Xpp3Driver(NAME_CODER);
	private static Xpp3DomDriver XPP3_DOM_DRIVER = new Xpp3DomDriver(NAME_CODER);

	private static ThreadLocal<XStream> localXStream = new ThreadLocal<>();

	public static XStream getXStream() {
		if (localXStream.get() == null) {
			newInstance();
		}
		return localXStream.get();
	}


	private static void newInstance() {
		XStream xStream = new XStream(XPP3_DRIVER);
		XStream.setupDefaultSecurity(xStream);//安全配置1.5版本后移除
		xStream.allowTypesByWildcard(new String[]{"org.git.**"});//允许的转换类型，根据通配符
		xStream.registerConverter(DATE_CONVERTER);//转换器
		localXStream.set(xStream);
//		xStream.processAnnotations(processAnnotations);//处理类
	}

	/**
	 * 将XML转为Bean对象
	 *
	 * @param xml
	 * @param <T>
	 * @return
	 */
	public static <T> T fromXML(String xml, Class clz) {
		getXStream().processAnnotations(clz);
		T t = (T) getXStream().fromXML(xml);
		return t;
	}

	/**
	 * 将XML转为Bean对象
	 *
	 * @param xml
	 * @param <T>
	 * @return
	 */
	public static <T> T fromXML(String xml) {
		T t = (T) getXStream().fromXML(xml);
		return t;
	}

	/**
	 * 将XML转为Bean对象
	 *
	 * @param input
	 * @param clz
	 * @return
	 */
	public static Object fromXML(InputStream input, Class clz) {
		getXStream().processAnnotations(clz);
		return getXStream().fromXML(input);
	}

	/**
	 * 将XML转为Bean对象
	 *
	 * @param input
	 * @return
	 */
	public static Object fromXML(InputStream input) {
		return getXStream().fromXML(input);
	}

	/**
	 * 将Bean对象转为XML字符串
	 *
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj) {
		return getXStream().toXML(obj);
	}


	/**
	 * 将Bean对象转为XML文件
	 *
	 * @param obj
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public void toXMLFile(Object obj, String filePath) throws FileNotFoundException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
		getXStream().toXML(obj, fileOutputStream);
	}
}
