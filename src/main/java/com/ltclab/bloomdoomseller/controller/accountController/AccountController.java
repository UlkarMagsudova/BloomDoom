package com.ltclab.bloomdoomseller.controller.accountController;

import com.ltclab.bloomdoomseller.constant.AccountType;
import com.ltclab.bloomdoomseller.dto.request.accountRequestDto.AccountRequestDto;
import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.AccountResponseDto;
import com.ltclab.bloomdoomseller.service.accountService.AccountService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam(required = false)
                                                AccountType accountType,
                                                @Valid @RequestBody AccountRequestDto accountRequestDto) {
        if (accountType == null) {
            accountType = AccountType.CUSTOMER;
        }
        return ResponseEntity.ok(accountService.createAccount(accountType, accountRequestDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountRequestDto customerRequestDto) {
        return ResponseEntity.ok(accountService.updateAccount(id, customerRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllAccounts() {
        return ResponseEntity.ok(accountService.deleteAllAccounts());
    }
}