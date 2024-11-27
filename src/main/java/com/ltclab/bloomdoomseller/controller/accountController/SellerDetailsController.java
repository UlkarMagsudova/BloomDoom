package com.ltclab.bloomdoomseller.controller.accountController;

import com.ltclab.bloomdoomseller.dto.request.accountRequestDto.SellerDetailsRequestDto;
import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.SellerDetailsResponseDto;
import com.ltclab.bloomdoomseller.service.accountService.SellerDetailsService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SellerDetailsController {
    private final SellerDetailsService sellerDetailsService;

    @PostMapping("/create")
    public ResponseEntity<String> createSellerDetails(@Valid @RequestBody SellerDetailsRequestDto sellerDetailsRequestDto) {
        return ResponseEntity.ok(sellerDetailsService.createSellerDetails(sellerDetailsRequestDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SellerDetailsResponseDto>> getAllMySellerDetails() {
        return ResponseEntity.ok(sellerDetailsService.getAllMySellerDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDetailsResponseDto> getSellerDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(sellerDetailsService.getSellerDetailsById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSellerDetails(@Valid @RequestBody SellerDetailsRequestDto sellerDetailsRequestDto,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(sellerDetailsService.updateSellerDetails(sellerDetailsRequestDto, id));
    }

    @GetMapping("/shop/{shopName}")
    public ResponseEntity<SellerDetailsResponseDto> getSellerDetailsByShopName(@PathVariable String shopName) {
        return ResponseEntity.ok(sellerDetailsService.getSellerDetailsByShopName(shopName));
    }

    @GetMapping("/account")
    public ResponseEntity<SellerDetailsResponseDto> getSellerDetailsByAccount(@RequestParam Long accountId) {
        return ResponseEntity.ok(sellerDetailsService.getSellerDetailsByAccountId(accountId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSellerDetails(@PathVariable Long id) {
        return ResponseEntity.ok(sellerDetailsService.deleteSellerDetails(id));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllSellerDetails() {
        return ResponseEntity.ok(sellerDetailsService.deleteAllSellerDetails());
    }
}