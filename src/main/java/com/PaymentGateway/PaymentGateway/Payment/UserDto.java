package com.PaymentGateway.PaymentGateway.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    public String fullName;
    public String email;
    public Integer amount;
}
