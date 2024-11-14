package mine.testcode.domain.chat.presentation.dto;

import lombok.*;
import mine.testcode.global.handler.types.MessageType;

@Getter
public class ChatMessageDto {
    private MessageType messageType; // 메시지 유형 ( 입장 or 문자 )
    private String chatRoomId; // 룸 아이디
    private String senderId; // 보낸 이
    private String message; // 메시지 내용

    public void initMessage(String message) {
        this.message = message;
    }
}

// ws://localhost:8080/api/ws/chat
// { "messageType" : "ENTER", "chatRoomId" : "7f4a1918-6691-4567-b591-102c829b7fb5", "senderId" : "박강은", "message" : "하이" }