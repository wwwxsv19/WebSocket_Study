package mine.testcode.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.presentation.dto.ChatDto;
import mine.testcode.domain.chat.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chat", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/")
    public ChatRoom createRoom(@RequestBody ChatDto.CreateRequest request) {
        return chatService.createRoom(request);
    }

    @GetMapping("/")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    @GetMapping("/{chatRoomId}")
    public ChatRoom findRoomInfo(@PathVariable String chatRoomId) {
        return chatService.findRoomById(chatRoomId);
    }
}
