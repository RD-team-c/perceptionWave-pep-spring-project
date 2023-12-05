package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("SELECT a FROM Account a WHERE a.account_id = ?1")
    Account findByAccountId(Integer id);

    Account findByUsername(String username);

    Account findByUsernameAndPassword(String username, String password);
}
