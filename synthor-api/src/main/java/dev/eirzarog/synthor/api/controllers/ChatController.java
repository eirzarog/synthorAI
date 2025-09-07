package dev.eirzarog.synthor.api.controllers;

import dev.eirzarog.synthor.api.models.dtos.requests.ChatRequest;
import dev.eirzarog.synthor.api.models.dtos.responses.ChatResponse;
import dev.eirzarog.synthor.api.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
/*
    @PostMapping("/send")
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request){

        try{
            ChatResponse response = chatService.processMessage(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("Error: " + e.getMessage()));
        }

    }*/
}


