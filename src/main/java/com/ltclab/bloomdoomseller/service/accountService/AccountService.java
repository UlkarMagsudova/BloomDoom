package com.ltclab.bloomdoomseller.service.accountService;

import com.ltclab.bloomdoomseller.constant.AccountType;
import com.ltclab.bloomdoomseller.dto.request.accountRequestDto.AccountRequestDto;
import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.AccountResponseDto;
import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.exception.PasswordValidationException;
import com.ltclab.bloomdoomseller.exception.UniqueFieldException;
import com.ltclab.bloomdoomseller.passwordvalidator.PasswordEncryptionUtil;
import com.ltclab.bloomdoomseller.passwordvalidator.PasswordValidator;
import com.ltclab.bloomdoomseller.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordValidator passwordValidator = PasswordValidator.defaultValidator();
    private final PasswordEncryptionUtil passwordEncryptionUtil;
    private final ModelMapper modelMapper;

    @Transactional
    public String createAccount(AccountType accountType, AccountRequestDto accountRequestDto) {

        if (accountRepository.existsByEmailOrPhone(accountRequestDto.getEmail(), accountRequestDto.getPhone())) {
            log.warn("Account already exists with email or phone: {}", accountRequestDto.getEmail());
            throw new AlreadyExistsException("Account already exists with the provided email or phone.");
        }
        List<String> passwordRejections = passwordValidator.validate(accountRequestDto.getPassword());
        if (!passwordRejections.isEmpty()) {
            String rejectionMessages = String.join(", ", passwordRejections);
            log.warn("Password validation failed: {}", rejectionMessages);
            throw new PasswordValidationException("Invalid password: " + rejectionMessages);  // Custom exception
        }
        String encryptedPassword = passwordEncryptionUtil.encryptPassword(accountRequestDto.getPassword());
        accountRequestDto.setPassword(encryptedPassword);
        Account account = modelMapper.map(accountRequestDto, Account.class);
        account.setAccountType(accountType.toString());  // Assuming accountType is an enum
        accountRepository.save(account);
        return "Account has been created successfully!";
    }


    public List<AccountResponseDto> getAllAccounts() {
        log.info("Finding all accounts from DB.");
        List<Account> accounts = accountRepository.findAll();
        return accounts
                .stream()
                .map(c -> modelMapper.map(c, AccountResponseDto.class))
                .collect(Collectors.toList());
    }

    public AccountResponseDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found by ID - " + id));
        log.info("Find account by id {}", id);
        return modelMapper.map(account, AccountResponseDto.class);
    }

    public String updateAccount(Long id, AccountRequestDto accountRequestDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found by ID - " + id));

        if (!account.getEmail().equals(accountRequestDto.getEmail())) {
            if (accountRepository.existsByEmailOrPhone(accountRequestDto.getEmail(), accountRequestDto.getPhone())) {
                throw new UniqueFieldException("Account email already exists.");
            }
        }
        if (!account.getName().equals(accountRequestDto.getName())) {
            if (accountRepository.existsByName(accountRequestDto.getName())) {
                throw new UniqueFieldException("Account name already exists.");
            }
        }

        modelMapper.map(accountRequestDto, account);
        account.setId(id);
        accountRepository.save(account);
        log.info("Account with name {} has been updated.", accountRequestDto.getName());
        return "Account updated successfully";
    }

    public String deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account with id " + id + " does not exist."));

        accountRepository.delete(account);
        log.info("Account with id {} has been deleted.", id);
        return "Account deleted successfully";
    }

    public String deleteAllAccounts() {
        accountRepository.deleteAll();
        log.info("All accounts have been deleted.");
        return "All accounts deleted successfully";
    }
}