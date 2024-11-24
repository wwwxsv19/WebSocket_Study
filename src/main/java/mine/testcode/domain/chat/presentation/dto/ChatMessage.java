package mine.testcode.domain.chat.presentation.dto;

import lombok.*;
import mine.testcode.domain.chat.types.MessageType;

@Getter
public class ChatMessage {
    private String chatRoomId; // 룸 아이디
    private MessageType messageType; // 메시지 유형 ( 입장 or 문자 )
    private String senderId; // 보낸 이
    private String message; // 메시지 내용

    public void initMessage(String message) {
        this.message = message;
    }
}
