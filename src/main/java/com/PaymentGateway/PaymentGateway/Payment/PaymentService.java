package com.PaymentGateway.PaymentGateway.Payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Service
public class PaymentService{
    public String createUser(PaymentDto paymentDto, String stripeApikey) throws StripeException {
        Stripe.apiKey = stripeApikey;
        Map<String, Object> params = new HashMap<>();
        params.put("name", paymentDto.firstName + " " + paymentDto.lastName);
        params.put("email", paymentDto.email);
        params.put("balance", paymentDto.amount);
        Customer customer = Customer.create(params);
        return customer.getId();
    }

    public String generateInvoice(String customerId, Long total) throws StripeException {
        Map<String, Object> invoiceParams = new HashMap<>();
        invoiceParams.put("customer", customerId);
        invoiceParams.put("collection_method", "send_invoice");
        invoiceParams.put("days_until_due", 7);
        Invoice invoice = Invoice.create(invoiceParams);
        invoice.setSubtotal(total);
        invoice.finalizeInvoice();
        invoice.sendInvoice();
        return invoice.getId();
    }

    public String getReceipt(String firstName, String lastName, Long amount, String invoiceId) throws StripeException {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Hello Dear " + firstName + " " + lastName + ", ");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("Thanks for donate by " + amount + "$");
        receipt.append("\n");
        receipt.append("you're just one step away from completing the donation,");
        receipt.append("\n");
        receipt.append("can you please confirm the payment process through the provided link,");
        receipt.append("\n");
        receipt.append("for more details here is the invoice pdf");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append(Invoice.retrieve(invoiceId).getInvoicePdf());
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("\n");
        receipt.append("Best Regards PaymentGateway team.");
        return receipt.toString();
    }
}