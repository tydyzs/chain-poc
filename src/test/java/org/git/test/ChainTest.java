package org.git.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.git.core.test.ChainBootTest;
import org.git.core.test.ChainSpringRunner;

/**
 * Chain单元测试
 *
 * @author Chill
 */
@RunWith(ChainSpringRunner.class)
@ChainBootTest(appName = "chain-runner", profile = "test")
public class ChainTest {

	@Test
	public void contextLoads() {
		System.out.println("测试～～～～～～");
	}

}
