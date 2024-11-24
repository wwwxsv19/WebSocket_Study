package mine.testcode.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.presentation.dto.ChatDto;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import mine.testcode.domain.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAllRoom() {
        // return new ArrayList<>(chatRooms.values());
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoomById(String chatRoomId) {
        // return chatRooms.get(chatRoomId);
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다!"));
    }

    public ChatRoom createRoom(ChatDto.CreateRequest request) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(randomId)
                .chatRoomName(request.getUserName())
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public ChatRoom saveChatMessage(ChatMessage chatMessage) {
        ChatRoom chatRoom = this.findRoomById(chatMessage.getChatRoomId());
        chatRoom.addMessage(chatMessage);
        return chatRoom;
    }
}
