package mine.testcode.domain.chat.repository;

import mine.testcode.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    List<ChatRoom> findAllByCreateUserId(Long createUserId);
}
