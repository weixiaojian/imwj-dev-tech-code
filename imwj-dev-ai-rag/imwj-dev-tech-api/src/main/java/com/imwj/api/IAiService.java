package com.imwj.api;


import org.springframework.ai.chat.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @author wj
 * @create 2025-05-28 18:12
 * @description
 */
public interface IAiService {

    ChatResponse generate(String model, String message);

    Flux<ChatResponse> generateStream(String model, String message);

    Flux<ChatResponse> generateStreamRag(String model, String ragTag, String message);

}
