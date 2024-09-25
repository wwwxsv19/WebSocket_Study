package mine.testcode.domain.chat;

import lombok.*;
import mine.testcode.domain.chat.service.ChatService;
import mine.testcode.global.handler.types.MessageType;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    private String chatRoomId; // 채팅방 식별 아이디
    private String chatRoomName; // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String chatRoomId, String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getMessageType().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.initMessage(chatMessage.getSenderId() + "님이 입장하셨습니다");
        }

        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
