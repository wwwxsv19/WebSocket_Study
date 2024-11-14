package mine.testcode.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.presentation.ChatRoom;
import mine.testcode.domain.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper mapper;

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomRepository.findAll());
    }

    public ChatRoom findRoomById(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다!"));
    }

    public ChatRoom createRoom(String chatRoomName) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(randomId)
                .chatRoomName(chatRoomName)
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
