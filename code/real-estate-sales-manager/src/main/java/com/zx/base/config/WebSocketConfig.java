package com.zx.base.config;

import com.zx.base.model.WsResponseModel;
import com.zx.system.model.SysUserlogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiafl.
 * @version 2017/12/12
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final Object object = new Object();

    public static Map<String, String> Clients = new HashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/ws").setAllowedOrigins("*").addInterceptors(getHandshakeInterceptor());
        stompEndpointRegistry.addEndpoint("/ws").addInterceptors(getHandshakeInterceptor()).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 设置服务器端向客户端发送的前缀
        config.enableSimpleBroker("/meta");
        // 设置客户端向服务端发送请求的访问前缀
        config.setApplicationDestinationPrefixes("/app");
        //设置 给指定用户发送（一对一）的主题前缀
        //config.setUserDestinationPrefix("/user");
    }

    /**
     * 输入通道参数设置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //设置消息输入通道的线程池线程数
        //线程活动时间
        registration.taskExecutor().corePoolSize(4) .maxPoolSize(8) .keepAliveSeconds(60);
        registration.interceptors(getChannelInterceptor());
    }

    @Bean
    public HttpSessionHandshakeInterceptor getHandshakeInterceptor() {
        return new WebSocketHandshakeInterceptor();
    }

    @Bean
    public PresenceChannelInterceptor getChannelInterceptor() {
        return new PresenceChannelInterceptor();
    }


    class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
            if (serverHttpRequest instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) serverHttpRequest;
                HttpSession session = serverRequest.getServletRequest().getSession();
                if (session != null) {
                    map.put("currentUserLogin", session.getAttribute("currentUserLogin"));
                    return true;
                }
            }
            return false;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        }
    }

    class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

        @Override
        public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
            StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
            if (sha.getCommand() == null) {
                return;
            }
            SysUserlogin userLogin = (SysUserlogin) sha.getSessionAttributes().get("currentUserLogin");
            if (userLogin == null ) {
                return;
            }
            String sessionId = sha.getSessionId();
            switch (sha.getCommand()) {
                case CONNECT:
                    onConnect(sessionId, userLogin);
                    break;
                case CONNECTED:
                    break;
                case DISCONNECT:
                    onDisconnect(sessionId,userLogin);
                    break;
                default:
                    break;
            }
        }
    }

    private void onConnect(String sessionId, SysUserlogin userlogin) {
        synchronized (object) {
            // 复制标签的情况：
            // web 认为是一个Session
            // 所以不需要加入到sockect客户端列表
            if (Clients.containsValue(userlogin.getLogintoken())){
                return;
            }
            Clients.put(sessionId, userlogin.getLogintoken());
            WsResponseModel responseModel = new WsResponseModel();
            responseModel.setChannel("/meta/onlineCnt");
            String extra =  String.format("{\"sessionId\":\"%s\",\"username\":\"%s\",\"loginPhoto\":\"%s\",\"type\":\"%s\"}",sessionId,userlogin.getRealname(),userlogin.getLoginphoto(),"Online");
            responseModel.setExtra(extra);
            messagingTemplate.convertAndSend("/meta/onlineCnt", responseModel);
        }
    }

    private void onDisconnect(String sessionId,SysUserlogin userlogin) {
        synchronized (object) {
            if (Clients.containsKey(sessionId)) {
                Clients.remove(sessionId);
                WsResponseModel responseModel = new WsResponseModel();
                responseModel.setChannel("/meta/onlineCnt");
                String extra =  String.format("{\"sessionId\":\"%s\",\"username\":\"%s\",\"loginPhoto\":\"%s\",\"type\":\"%s\"}",sessionId,userlogin.getRealname(),userlogin.getLoginphoto(),"Outline");
                responseModel.setExtra(extra);
                messagingTemplate.convertAndSend("/meta/onlineCnt", responseModel);
            }
        }
    }
}
