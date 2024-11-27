package com.ltclab.bloomdoomseller.repository.password;

import com.ltclab.bloomdoomseller.entity.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationDate;

    @ManyToOne
    private Account account;

    public PasswordResetToken(String token, Account account) {
        this.token = token;
        this.account = account;
        this.expirationDate = LocalDateTime.now().plusHours(1); // Token expires in 1 hour
    }
}
