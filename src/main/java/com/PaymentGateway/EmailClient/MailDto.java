package com.PaymentGateway.EmailClient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDto {
    public String to;
    public String subject;
    public String body;
}
