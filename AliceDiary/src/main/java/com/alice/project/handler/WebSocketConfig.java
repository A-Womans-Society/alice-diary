package com.alice.project.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
		public void configureMessageBroker(MessageBrokerRegistry registry) {
			// TODO Auto-generated method stub
			registry.enableSimpleBroker("/sub");
			registry.setApplicationDestinationPrefixes("/pub");
//			WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
		}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/ws").setAllowedOrigins("*");
	}
	
//	private final ChatHandler chatHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//    
//        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
//    }
    	
	// 1:1채팅방 구현시 필요했던 메서드
//	@Bean
//	public ServerEndpointExporter serverEndpointExporter() {
//		return new ServerEndpointExporter();
//	}
	
	
}