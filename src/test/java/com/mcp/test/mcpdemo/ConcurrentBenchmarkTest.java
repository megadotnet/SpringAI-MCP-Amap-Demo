package com.mcp.test.mcpdemo;

import com.mcp.test.mcpdemo.controller.LLMController;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConcurrentBenchmarkTest {

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);

        when(builder.build()).thenReturn(chatClient);

        when(chatClient.prompt(anyString()).call().content()).thenAnswer(invocation -> {
            Thread.sleep(100); // Simulate network latency
            return "Mock Response for " + invocation.getArgument(0);
        });

        LLMController controller = new LLMController(builder);

        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        long start = System.currentTimeMillis();
        for (int i = 0; i < numThreads; i++) {
            // Use same hashcode string to enforce same bucket usage
            final String key = (i % 2 == 0) ? "Aa" : "BB";
            executor.submit(() -> {
                try {
                    controller.hello(key);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        long end = System.currentTimeMillis();

        System.out.println("Concurrent test taken: " + (end - start) + " ms");
        executor.shutdown();
    }
}
