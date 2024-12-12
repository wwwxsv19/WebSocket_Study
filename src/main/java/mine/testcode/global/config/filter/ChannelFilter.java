package mine.testcode.global.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.global.utils.JwtUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChannelFilter implements ChannelInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("ChannelFilter 시작");

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
            String header = accessor.getFirstNativeHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                throw new IllegalArgumentException("존재하지 않거나 잘못된 토큰입니다.");
            }

            String token = header.substring(7);

            if (jwtUtil.isExpired(token)) {
                throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
            }

            log.info("토큰 : {}", token);

            String userEmail = jwtUtil.getUserEmail(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(userEmail, null, null);
            accessor.setUser(auth);
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            log.info("구독 요청입니다.");
        } else {
            throw new IllegalArgumentException("StompHeaderAccessor 가 존재하지 않습니다.");
        }

        log.info("ChannelFilter 통과");

        return message;
    }
}
