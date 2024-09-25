package mine.testcode.domain.chat;

import lombok.*;
import mine.testcode.global.handler.types.MessageType;

@Getter
public class ChatMessage {
    private MessageType messageType; // 메시지 유형 ( 입장 or 문자 )
    private String chatRoomId; // 룸 아이디
    private String senderId; // 보낸 이
    private String message; // 메시지 내용

    public void initMessage(String message) {
        this.message = message;
    }
}
