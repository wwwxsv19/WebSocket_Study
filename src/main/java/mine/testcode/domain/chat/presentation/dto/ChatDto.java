package mine.testcode.domain.chat.presentation.dto;

import lombok.Getter;

public class ChatDto {
    @Getter
    public static class CreateRequest {
        private String userName;
    }
}
