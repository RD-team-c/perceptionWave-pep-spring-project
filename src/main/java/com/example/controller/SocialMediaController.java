package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.service.*;
import com.example.entity.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // user story 1: New User Registrations
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account existingAccount = accountService.findByUsername(account.getUsername());
        if (existingAccount != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Account savedAccount = accountService.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

     // User Story 2: Process User Logins
     @PostMapping("/login")
     public ResponseEntity<Account> login(@RequestBody Account account) {
         Account existingAccount = accountService.findByUsernameAndPassword(account.getUsername(), account.getPassword());
         if (existingAccount == null) {
             return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
         }
         return new ResponseEntity<>(existingAccount, HttpStatus.OK);
     }
 
     // User Story 3: Process Creation of New Messages
     @PostMapping("/messages")
     public ResponseEntity<Message> createMessage(@RequestBody Message message) {
         if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
 
         Account existingAccount = accountService.findByAccountId(message.getPosted_by());
         if (existingAccount == null) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
 
         Message savedMessage = messageService.save(message);
         return new ResponseEntity<>(savedMessage, HttpStatus.OK);
     }
 
     // User Story 4: Retrieve All Messages
     @GetMapping("/messages")
     public ResponseEntity<List<Message>> getAllMessages() {
         List<Message> messages = messageService.findAll();
         return new ResponseEntity<>(messages, HttpStatus.OK);
     }
 
     // User Story 5: Retrieve a Message by its ID
@GetMapping("/messages/{message_id}")
public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id) {
    Message message = messageService.findByMessageId(message_id);
    if (message == null) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(message, HttpStatus.OK);
}

 
     // User Story 6: Delete a Message by its ID
     @DeleteMapping("/messages/{message_id}")
public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
    Message existingMessage = messageService.findByMessageId(messageId);
    if (existingMessage == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    int rowsUpdated = messageService.delete(existingMessage);
    return new ResponseEntity<>(rowsUpdated, HttpStatus.OK);
}
 
     // User Story 7: Update a Message Text by its ID
     @PatchMapping("/messages/{message_id}")
public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody String newText) {
    Message existingMessage = messageService.findByMessageId(messageId);
    if (existingMessage == null || newText == null || newText.isEmpty() || newText.length() > 255) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    existingMessage.setMessage_text(newText);
    int rowsUpdated = messageService.saveAndReturnRowsUpdated(existingMessage);
    return new ResponseEntity<>(rowsUpdated, HttpStatus.OK);
}

     // User Story 8: Retrieve All Messages Written by a Particular User
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        Account existingAccount = accountService.findByAccountId(accountId);
        if (existingAccount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Message> messages = messageService.findByPostedBy(accountId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
