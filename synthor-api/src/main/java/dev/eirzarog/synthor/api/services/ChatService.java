package dev.eirzarog.synthor.api.services;


import org.springframework.stereotype.Service;


@Service
public class ChatService {

   // private final ChatRepository chatRepository;

//    @Autowired
//    public ChatService(ChatRepository chatRepository) {
//        this.chatRepository = chatRepository;
//    }
//
//    public List<Chat> getAll(ChatCriteria criteria) throws GlobalException {
//
//        Instant twoYearsAgo = Instant.now().minusSeconds(60 * 60 * 24 * 365 * 2);
//
//        if (criteria.getFrom() != null && criteria.getFrom().isBefore(twoYearsAgo)) {
//            throw new GlobalException(HttpStatus.BAD_REQUEST,
//                    "The 'from' date cannot be more than two years ago.");
//        }
//
//        return chatRepository.findAll(criteria.getUserId(),
//               criteria.getUsername(),
//               criteria.getFrom(),
//                criteria.getTo());
//    }
}
