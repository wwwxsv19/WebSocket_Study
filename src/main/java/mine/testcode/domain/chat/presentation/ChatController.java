package mine.testcode.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.presentation.dto.CreateDto;
import mine.testcode.domain.chat.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chat", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    // 모든 채팅방 불러오기
    @GetMapping("/")
    public List<ChatRoom> findAllRoom() {
        return chatService.getAll();
    }
}
