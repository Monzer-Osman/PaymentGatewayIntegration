package com.PaymentGateway.Payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Service
@NoArgsConstructor
public class PaymentService {
    @Value("${stripe.apikey}")
    private String stripeApikey;

    public String createUser(UserDto userDto, Integer amount) throws StripeException {
        Stripe.apiKey = stripeApikey;
        Map<String, Object> params = new HashMap<>();
        params.put("name", userDto.firstName + " " + userDto.lastName);
        params.put("email", userDto.email);
        params.put("balance", amount);
        Customer customer = Customer.create(params);
        return customer.getId();
    }

    public String generateInvoice(String customerId, Integer total) throws StripeException {
        Map<String, Object> invoiceParams = new HashMap<>();
        invoiceParams.put("customer", customerId);
        invoiceParams.put("collection_method", "send_invoice");
        invoiceParams.put("days_until_due", 7);
        Invoice invoice = Invoice.create(invoiceParams);
        invoice.setSubtotal((long) total);
        invoice.finalizeInvoice();
        invoice.sendInvoice();
        return invoice.getId();
    }

    public String generateEmailBody(String firstName, Long amount, String invoiceId) throws StripeException {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Dear " + firstName + ", ");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("We have received your desire to donate " + amount + "$");
        receipt.append("\n");
        receipt.append("and we thank you for your generosity,");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("for now please before you confirm the payment process through the provided link,");
        receipt.append("\n");
        receipt.append("make sure that you review the invoice and then confirm the payment :)");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("again we thank you very much and hope you have a good day.");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append(Invoice.retrieve(invoiceId).getInvoicePdf());
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("Regards,");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("PaymentGateway Team.");
        return receipt.toString();
    }
}