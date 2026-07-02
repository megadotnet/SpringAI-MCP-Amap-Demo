package com.mcp.test.mcpdemo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.*;

public class McpdemoApplicationCommandLineRunnerTest {

    @Test
    void testPredefinedQuestions() throws Exception {
        // Arrange
        McpdemoApplication app = new McpdemoApplication();

        ChatClient.Builder builderMock = mock(ChatClient.Builder.class);
        ToolCallbackProvider toolsMock = mock(ToolCallbackProvider.class);
        ConfigurableApplicationContext contextMock = mock(ConfigurableApplicationContext.class);
        ChatClient chatClientMock = mock(ChatClient.class);
        ChatClient.ChatClientRequestSpec requestSpecMock = mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec callResponseSpecMock = mock(ChatClient.CallResponseSpec.class);

        when(builderMock.defaultToolCallbacks(toolsMock)).thenReturn(builderMock);
        when(builderMock.build()).thenReturn(chatClientMock);

        String expectedInput = "请问当前IP地址的物理位置周边有几个加油站?";
        when(chatClientMock.prompt(expectedInput)).thenReturn(requestSpecMock);
        when(requestSpecMock.call()).thenReturn(callResponseSpecMock);
        when(callResponseSpecMock.content()).thenReturn("模拟回答：附近有3个加油站");

        // Act
        CommandLineRunner runner = app.predefinedQuestions(builderMock, toolsMock, contextMock);
        runner.run();

        // Assert
        verify(builderMock).defaultToolCallbacks(toolsMock);
        verify(builderMock).build();
        verify(chatClientMock).prompt(expectedInput);
        verify(requestSpecMock).call();
        verify(callResponseSpecMock).content();
        verify(contextMock).close();
    }
}
