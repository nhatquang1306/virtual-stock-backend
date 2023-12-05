package com.techelevator.controller;

import com.techelevator.dao.ChatDao;
import com.techelevator.model.Chat;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
public class ChatController {

    private ChatDao chatDao;
    public ChatController(ChatDao chatDao) {
        this.chatDao = chatDao;
    }
    @RequestMapping(value = "/games/{id}/chat", method = RequestMethod.POST)
    public void insertChat(Principal principal, @PathVariable int id, @RequestBody Chat chat) {
        chatDao.insertChat(principal.getName(), id, chat);
    }

}
