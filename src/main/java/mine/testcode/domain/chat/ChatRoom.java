package mine.testcode.domain.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "chatroom_tbl")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom {
    @Id
    private String chatRoomId; // 채팅방 식별 아이디

    private String chatRoomName; // 채팅방 이름

    private Long createUserId;

    private LocalDateTime createdAt;

    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ChatMessage> messageList = new ArrayList<>();

    public void addMessage(ChatMessage chatMessage) {
        messageList.add(chatMessage);
    }
}
