package mine.testcode.domain.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import mine.testcode.domain.chat.service.ChatService;
import mine.testcode.domain.chat.types.MessageType;
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

    public void addMessage(ChatMessage chatMessage) {
        messageSet.add(chatMessage);
    }

    @Builder
    public ChatRoom(String chatRoomId, String chatRoomName) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
    }
}
