package com.mcp.test.mcpdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 主应用程序类，使用Spring Boot的@SpringBootApplication注解
 */
@SpringBootApplication
@Slf4j
public class McpdemoApplication {

    /**
     * 主程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(McpdemoApplication.class, args);
    }



    /**
     * 创建并配置CommandLineRunner bean，用于在应用程序启动后执行一些预定义的操作
     * 这里用于演示如何使用ChatClient发送预定义的问题，并打印结果
     * @param chatClientBuilder ChatClient的构建器
     * @param tools 工具回调提供者
     * @param context 配置应用程序上下文，以便在操作完成后关闭
     * @return CommandLineRunner实例
     */
    @Bean
    public CommandLineRunner predefinedQuestions(ChatClient.Builder chatClientBuilder, ToolCallbackProvider tools,
                                                ConfigurableApplicationContext context) {
        // 预定义的用户输入
        String userInput="请问当前IP地址的物理位置周边有几个加油站?";
        return args -> {
            // 构建ChatClient实例
            var chatClient = chatClientBuilder
                    .defaultToolCallbacks(tools)
                    .build();

            // 打印问题
			log.info("\n>>> QUESTION: " + userInput);
            // 使用ChatClient发送问题，并打印回答内容
			log.info("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());

            // 关闭应用程序上下文
            context.close();
        };
    }

}

