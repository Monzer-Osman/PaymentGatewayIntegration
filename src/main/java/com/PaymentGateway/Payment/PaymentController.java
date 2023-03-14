package com.PaymentGateway.Payment;

import com.PaymentGateway.EmailClient.EmailClientController;
import com.PaymentGateway.EmailClient.MailDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;
    private EmailClientController emailClientController;

    @GetMapping("/payment-form")
    public String getPaymentForm() {
        return "payment_form";
    }

    @PostMapping("/pay/{amount}")
    public String payProcess(UserDto userDto, @PathVariable String amount) throws StripeException {
        amount += "00";
        String customerId = paymentService.createUser(userDto, Integer.parseInt(amount));
        String invoiceId = paymentService.generateInvoice(customerId, Integer.parseInt(amount));
        String emailBody = paymentService.generateEmailBody(
                userDto.firstName,
                Long.parseLong(amount.substring(0, amount.length() - 2)),
                invoiceId);
        sendConfirmationMail(userDto.email, emailBody);
        return "redirect:" + Invoice.retrieve(invoiceId).getHostedInvoiceUrl();
    }

    private void sendConfirmationMail(String email, String emailBody) {
        MailDto mailDto = new MailDto(email,
                "Thank you for your donate",
                emailBody);
        emailClientController.sendEmailMessage(mailDto);
    }
}