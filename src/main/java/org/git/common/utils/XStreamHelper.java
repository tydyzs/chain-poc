package org.git.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.git.modules.clm.front.service.XMLConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.*;
import java.util.TimeZone;

/**
 * XStream XML解析工具
 */
@Slf4j
public class XStreamHelper {


	private static final DateConverter DATE_CONVERTER =
		new DateConverter("yyyy-MM-dd HH:mm:ss", null, TimeZone.getTimeZone("GMT+8"));

	private static NameCoder NAME_CODER = new XmlFriendlyNameCoder("-_", "_");
	private static DomDriver DOM_DRIVER = new DomDriver("UTF-8", NAME_CODER);
	public static Xpp3Driver XPP3_DRIVER = new Xpp3Driver(NAME_CODER);
	private static Xpp3DomDriver XPP3_DOM_DRIVER = new Xpp3DomDriver(NAME_CODER);

	private XStreamMarshaller xStreamMarshaller;

	public XStreamMarshaller newInstance(Class clazz) {
		if (xStreamMarshaller == null) {
			xStreamMarshaller = new XStreamMarshaller();
			xStreamMarshaller.setStreamDriver(XStreamHelper.XPP3_DRIVER);
			xStreamMarshaller.setEncoding(XStreamMarshaller.DEFAULT_ENCODING);//默认utf8
			xStreamMarshaller.setAnnotatedClasses(clazz);//RootNode.class设置从类级注释元数据中读取别名的带注释的类。
			xStreamMarshaller.setAutodetectAnnotations(true);
			XStream xStream = xStreamMarshaller.getXStream();
			XMLConverter converter = new XMLConverter(xStream.getMapper(), xStream.getReflectionProvider());
			xStream.registerConverter(converter);
			xStream.allowTypesByWildcard(new String[]{"org.git.**"});
		}

		return xStreamMarshaller;
	}

	public <T> T unmarshalXML(String xmlStr, Class clazz) {
		try {
			newInstance(clazz);
			StringReader stringReader = new StringReader(xmlStr);
			Object object = xStreamMarshaller.unmarshalReader(stringReader);
			return (T) object;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("unmarshalXML解析XML异常");

		}
		return null;
	}

	public String marshalXML(Object object, Class clazz) {

		try {
			newInstance(clazz);
			StringWriter stringWriter = new StringWriter();
			xStreamMarshaller.marshalWriter(object, stringWriter);
			return stringWriter.toString();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("marshalXML解析XML异常");
		}
		return null;

	}

}
