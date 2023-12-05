package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer>{

    @Query("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findByPostedBy(Integer id);

    @Query("SELECT m FROM Message m WHERE m.message_id = ?1")
    Message findByMessageId(Integer id);

}
