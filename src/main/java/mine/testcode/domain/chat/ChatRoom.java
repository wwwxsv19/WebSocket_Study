package mine.testcode.domain.chat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import mine.testcode.domain.chat.service.ChatService;
import mine.testcode.global.handler.types.MessageType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "oring_chatroom")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    private String chatRoomId; // 채팅방 식별 아이디

    private String chatRoomName; // 채팅방 이름

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<ChatMessage> messageSet = new HashSet<>();

    // @Transient
    @JdbcTypeCode(SqlTypes.JSON)
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String chatRoomId, String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getMessageType().equals(MessageType.ENTER)) {
            chatMessage.initMessage(chatMessage.getSenderId() + "님이 입장하셨습니다");
            messageSet.add(chatMessage);
            sessions.add(session);
        }

        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
