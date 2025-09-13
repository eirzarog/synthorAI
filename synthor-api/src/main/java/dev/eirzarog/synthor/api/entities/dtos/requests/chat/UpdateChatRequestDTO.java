package dev.eirzarog.synthor.api.entities.dtos.requests.chat;

public class UpdateChatRequestDTO {
    public static class SendMessageRequest {
        private String content;
        private String chatId;
        // Used when client SENDS a new message
    }
}
