package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment() {
        return ResponseEntity.ok("Payment successful");
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<String> getPaymentDetails(@PathVariable Long paymentId) {
        return ResponseEntity.ok("Payment details for ID: " + paymentId);
    }

    @PostMapping("/refund")
    public ResponseEntity<String> processRefund() {
        return ResponseEntity.ok("Refund processed");
    }
}
