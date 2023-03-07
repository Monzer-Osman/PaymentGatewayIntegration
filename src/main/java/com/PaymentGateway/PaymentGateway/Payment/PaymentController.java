package com.PaymentGateway.PaymentGateway.Payment;

import com.PaymentGateway.PaymentGateway.EmailClient.EmailClientController;
import com.PaymentGateway.PaymentGateway.EmailClient.MailDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/payments")
@NoArgsConstructor
public class PaymentController{
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private EmailClientController emailClientController;

	@Value("${stripe.apikey}")
	private String stripeKey;

	@GetMapping("/payment-form")
	public String getPaymentForm(){
		return "payment_form";
	}

	@PostMapping("/pay")
	public String payProcess(PaymentDto paymentDto) throws StripeException {
		String customerId = paymentService.createUser(paymentDto, stripeKey);
		String invoiceId = paymentService.generateInvoice(customerId, Long.valueOf(paymentDto.amount));
		String invoice = paymentService.getReceipt(
				paymentDto.firstName,
				paymentDto.lastName,
				Long.valueOf(paymentDto.amount),
				invoiceId);
		MailDto mailDto = new MailDto(paymentDto.email,
				"Donate Payment Confirmation", invoice);
		emailClientController.sendEmailMessage(mailDto);
		return "redirect:" + Invoice.retrieve(invoiceId).getHostedInvoiceUrl();
	}
}