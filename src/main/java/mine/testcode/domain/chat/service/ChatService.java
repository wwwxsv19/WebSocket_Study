package mine.testcode.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.presentation.dto.ChatDto;
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
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

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
        chatRooms.put(randomId, chatRoom);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }
}
