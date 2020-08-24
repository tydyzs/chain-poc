package org.git.modules.clm.front.service;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;
import org.git.modules.clm.front.dto.jxrcb.JxrcbConstant;
import org.git.modules.clm.front.dto.jxrcb.Request;
import org.git.modules.clm.front.dto.jxrcb.Response;
import org.git.modules.clm.front.dto.jxrcb.ServiceHeader;
import org.git.core.tool.utils.StringUtil;
import org.springframework.util.Assert;

public class XMLConverter extends AbstractReflectionConverter {

	private String serviceId;

	public XMLConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
		super(mapper, reflectionProvider);
	}

	@Override
	public boolean canConvert(Class type) {
		return ServiceHeader.class.isAssignableFrom(type) || Request.class.isAssignableFrom(type) || Response.class.isAssignableFrom(type);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if (ServiceHeader.class.equals(context.getRequiredType())) {
			ServiceHeader header = (ServiceHeader) super.unmarshal(reader, context);
			serviceId = header.getServiceId();
			return header;
		}
		return super.unmarshal(reader, context);
	}

	@Override
	protected Object instantiateNewInstance(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if (StringUtil.isNotBlank(serviceId)) {
			//请求体
			if (Request.class.equals(context.getRequiredType())) {
				Class request = JxrcbConstant.valueOf("S" + serviceId.toUpperCase()).getRequest();
				Assert.state(!StringUtil.isEmpty(request), serviceId + "请求配置类为空！");
				return reflectionProvider.newInstance(request);
			}
			//响应体
			if (Response.class.equals(context.getRequiredType())) {
				Class response = JxrcbConstant.valueOf("S" + serviceId.toUpperCase()).getResponse();
				Assert.state(!StringUtil.isEmpty(response), serviceId + "响应配置类为空！");
				return reflectionProvider.newInstance(response);
			}
		}
		return super.instantiateNewInstance(reader, context);
	}
}
