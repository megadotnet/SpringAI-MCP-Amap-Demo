package com.mcp.test.mcpdemo;

import com.mcp.test.mcpdemo.controller.LLMController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LLMControllerSecurityTest {

    private LLMController controller;
    private ChatClient chatClient;

    @BeforeEach
    public void setup() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        when(builder.build()).thenReturn(chatClient);

        controller = new LLMController(builder);
    }

    @Test
    public void testHello_WithValidInput_ReturnsContent() {
        String input = "Valid question";
        String expectedResponse = "Valid response";

        when(chatClient.prompt(input).call().content()).thenReturn(expectedResponse);

        String actualResponse = controller.hello(input);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testHello_WithNullInput_ThrowsBadRequest() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.hello(null);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Input cannot be empty"));
    }

    @Test
    public void testHello_WithEmptyInput_ThrowsBadRequest() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.hello("   ");
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Input cannot be empty"));
    }

    @Test
    public void testHello_WithOversizedInput_ThrowsBadRequest() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            sb.append("A");
        }
        String oversizedInput = sb.toString();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            controller.hello(oversizedInput);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Input is too long"));
    }
}