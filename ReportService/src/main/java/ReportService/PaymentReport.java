package ReportService;

import java.math.BigDecimal;

//TODO: Make this the same as payment in paymentService
public class PaymentReport {
    public String cid;
   public String mid;
   public BigDecimal amount;

   public String customerToken;

   public PaymentReport paymentToPaymentReport(Payment p, String cid){
       PaymentReport pr = new PaymentReport();
       pr.cid = cid;
       pr.mid = p.getMerchantBankID();
       pr.amount = p.getAmount();
       pr.customerToken = pr.customerToken;
       return pr;
   }

}