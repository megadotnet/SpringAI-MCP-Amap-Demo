package com.mcp.test.mcpdemo;

import com.mcp.test.mcpdemo.controller.LLMController;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

public class LLMControllerBenchmarkTest {

    @Test
    public void test() throws InterruptedException {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);

        when(builder.build()).thenReturn(chatClient);

        when(chatClient.prompt(anyString()).call().content()).thenAnswer(invocation -> {
            Thread.sleep(100); // Simulate network latency
            return "Mock Response";
        });

        LLMController controller = new LLMController(builder);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            controller.hello("AI编程趋势是什么");
        }
        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
