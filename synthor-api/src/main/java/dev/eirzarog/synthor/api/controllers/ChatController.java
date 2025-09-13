package dev.eirzarog.synthor.api.controllers;

import dev.eirzarog.synthor.api.entities.dtos.responses.chat.ChatResponse;
import dev.eirzarog.synthor.api.services.ChatService;
import dev.eirzarog.synthor.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {



    Logger logger = LoggerFactory.getLogger(ChatController.class);
    // Example of circular dependency resolution using @Lazy
//    private UserController userController;
//
//    @Autowired
//    public ChatController(@Lazy UserController userController) {
//        this.userController = userController;
//    }

    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;

        logger.debug("ChatController initialized with ChatService: " + userService);

    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<ChatResponse> getChatById(@PathVariable String id) {
        // Returns chat data
        return null;
    }

//    @GetMapping("/chats")
//    public List<Chat> getAllChats(ChatCriteria criteria) throws GlobalException {
//
//        // validate that the login username is equal to the username
//        // and get its own chat
//        // τα κριτήρια που ζητάει ο χρήστης να περιλαμβάνουν το δικό του username
//        // αν είναι admin δεν χρειάζεται να κάνουμε κανένα έλεγχο
//
//
//        return chatService.getAll(criteria);
//    }



/*
    @PostMapping("/send")
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatDTO request){

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


