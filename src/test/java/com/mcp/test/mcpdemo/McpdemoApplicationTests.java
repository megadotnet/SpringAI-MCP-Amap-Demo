package com.mcp.test.mcpdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.CommandLineRunner;

/**
 * McpdemoApplicationTests 是一个基于Spring Boot的集成测试类。
 * 该类主要用于验证应用上下文能否正确加载，确保Spring组件正常初始化。
 */
@SpringBootTest(properties = {
    "spring.ai.mcp.client.toolcallback.enabled=false",
    "spring.ai.mcp.client.stdio.connections.amap-maps.command=",
    "spring.main.web-application-type=none",
    "spring.ai.openai.api-key=mock",
    "spring.ai.mcp.client.enabled=false"
})
public class McpdemoApplicationTests {

    @MockBean
    CommandLineRunner predefinedQuestions;

	/**
	 * 测试方法 contextLoads 验证Spring应用上下文是否能成功加载。
	 * 此方法不执行额外逻辑，仅通过注解 @Test 和 @SpringBootTest 确保上下文加载无误。
	 */
	@Test
	void contextLoads() {
	}

}
