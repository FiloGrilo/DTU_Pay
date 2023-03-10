package Entities;


import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement // Needed for XML serialization and deserialization
@Data// Automatic getter and setters and equals etc
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class DTUPayUser {

    private String _name;
    private String _bankID;
    private String _userRole;
    private String accountID;

    public String getName(){
        return this._name;
    }

    public String getAccountID(){
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getBankID(){
        return this._bankID;
    }

    public String getRole() { return this._userRole; }
    public boolean validAccount(){
        if(!this._userRole.equals("customer") && !this._userRole.equals("merchant")) return false;
        if(this._name.equals("")) return false;
        return true;
    }
    public void set_name(String name){ this._name=name;}
    /*

    private List<Payment> customerPayments = new ArrayList<>();

    public void addCustomerPayment(Payment payment) { this.customerPayments.add(payment); }

    public List<Payment> getCustomerPayments() { return List.copyOf(this.customerPayments);}

    * */
}