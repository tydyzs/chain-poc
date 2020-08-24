package xml;


import org.git.common.utils.XStreamHelper;
import org.git.modules.clm.front.dto.jxrcb.RootNode;
import org.git.core.tool.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class XmlSwitch {

	public static void main(String[] args) throws IOException {
		String xml = FileUtil.readToString(new File("src\\test\\java\\xml\\ecif-response.xml"));
		System.out.println("将要解析的消息内容：" + xml);
		//设置消息转换器
//		XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
////		xStreamMarshaller.setStreamDriver(XStreamUtil.XPP3_DRIVER);
////		xStreamMarshaller.setEncoding(XStreamMarshaller.DEFAULT_ENCODING);//默认utf8
////		xStreamMarshaller.setAnnotatedClasses(RootNode.class);//设置从类级注释元数据中读取别名的带注释的类。
////		xStreamMarshaller.setAutodetectAnnotations(true);
//		XStream stream = XStreamUtil.newInstance();
//		XMLConverter converter1 = new XMLConverter(stream.getMapper(), stream.getReflectionProvider());
//		stream.registerConverter(converter1);
//		stream.allowTypesByWildcard(new String[]{"org.git.**"});

		StringReader sr = new StringReader(xml);
		XStreamHelper xStreamUtil = new XStreamHelper();
		RootNode object = xStreamUtil.unmarshalXML(xml,RootNode.class);
		System.out.println("--------" + object.getClass().getTypeName());
		System.out.println(object.getServiceBody().getRequest());
		System.out.println(object.getServiceBody().getResponse());
		System.out.println("over----");

//		String xml2 = xStreamUtil.marshalXML(object,RootNode.class);
//		System.out.println(xml2);



	}
}
