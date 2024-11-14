package mine.testcode.domain.chat.repository;

import mine.testcode.domain.chat.presentation.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
