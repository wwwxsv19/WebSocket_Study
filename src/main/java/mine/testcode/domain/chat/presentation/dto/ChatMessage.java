package mine.testcode.domain.chat.presentation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
public class ChatMessage {
    private String chatRoomId;
    private MessageType messageType; // 메시지 유형 ( ENTER or TALK )
    private String senderId;
    private String message; // 메시지 내용
    private LocalDateTime sentAt;

    public enum MessageType {
        ENTER, // 입장
        TALK // 채팅
    }

    public void initChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void initSentAt() {
        this.sentAt = java.time.LocalDateTime.now();
    }
}
