package com.example.demo.controller;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MocksRepository;
import com.example.demo.repository.domain.Account;
import com.example.demo.repository.domain.MockConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AccountsController {
    @Autowired
    private AccountRepository repository;

    @GetMapping("/accounts/{id}")
    public ResponseEntity get(@PathVariable("id") String id) {
        Optional<Account> account = repository.findById(id);
        if(account.isPresent()) {
            return ResponseEntity.ok(account.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/accounts")
    public final ResponseEntity getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/accounts")
    public final Account post(@RequestBody Account account) {
        return repository.save(account);
    }

    @DeleteMapping("/accounts/{id}")
    public final void delete(@PathVariable("id") String id) {
        Optional<Account> account = this.repository.findById(id);
        if(account.isPresent()) {
            this.repository.delete(account.get());
        }
    }


}