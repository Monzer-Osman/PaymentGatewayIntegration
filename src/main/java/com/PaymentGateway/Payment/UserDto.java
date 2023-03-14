package com.PaymentGateway.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    public String firstName;
    public String lastName;
    public String email;
}
