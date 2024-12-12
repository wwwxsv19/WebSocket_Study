package mine.testcode.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.presentation.dto.CreateDto;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import mine.testcode.domain.chat.repository.ChatRoomRepository;
import mine.testcode.domain.user.User;
import mine.testcode.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getAll() {
        return chatRoomRepository.findAll();
    }

    public List<ChatRoom> getAllByUser(Long userId) {
        return chatRoomRepository.findAllByCreateUserId(userId);
    }

    public ChatRoom findRoomById(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다!"));
    }

    public String createRoom(String userEmail) {
        User user = userService.getUserByUserEmail(userEmail);

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .chatRoomName(user.getUserName() + " 님의 채팅방")
                .createUserId(user.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
        chatRoomRepository.save(chatRoom);

        return chatRoom.getChatRoomId();
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        ChatRoom chatRoom = this.findRoomById(chatMessage.getChatRoomId());
        chatRoom.addMessage(chatMessage);
    }
}
