package org.git.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "clm")
public class ClmConfiguration {
	private boolean loginSingle;
	private long loginExpire;
}
