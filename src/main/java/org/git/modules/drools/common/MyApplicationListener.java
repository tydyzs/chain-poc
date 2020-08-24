package org.git.modules.drools.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.git.modules.drools.annotation.Drools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 扫描指定包下带有指定注解的class
 * @author jaffreyen
 * @date 2018/3/5
 */
@Slf4j
@Data
@Component
public class MyApplicationListener implements ApplicationListener {

	@Value("${clm.drools.scanpackage}")
	private String scanPackage;

	private Map<String,Class<?>> classCache = new HashMap<>();
	/**
	 * Handle an application event.
	 *
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		final String RESOURCE_PATTERN = "/**/*.class";
		// 扫描的包名
		final String BASE_PACKAGE =  this.scanPackage;//"org.git.modules.drools.entity";

		try {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE)
				+ RESOURCE_PATTERN;
			Resource[] resources = resourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					//扫描到的class
					String className = reader.getClassMetadata().getClassName();
					Class<?> clazz = Class.forName(className);
					//判断是否有指定注解
					Drools annotation = clazz.getAnnotation(Drools.class);
					if(annotation != null){
						//这个类使用了自定义注解
						classCache.put(className, clazz);
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			log.error("读取class失败", e);
		}
	}
}
