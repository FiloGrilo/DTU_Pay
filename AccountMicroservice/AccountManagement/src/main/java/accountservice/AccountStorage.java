package accountservice;

import java.util.ArrayList;
import java.util.List;

public class AccountStorage implements IAccountStorage {

    private List<DTUPayUser> CustomerList = new ArrayList<>();

    private List<DTUPayUser> MerchantList = new ArrayList<>();

    private int AccountCounter = 0;


    @Override
    public void addAccount(DTUPayUser account) {
        if (account.getRole().equals("customer")){
            CustomerList.add(account);
            AccountCounter++;
        } else if (account.getRole().equals("merchant")) {
            MerchantList.add(account);
            AccountCounter++;
        } else {
            throw new IllegalArgumentException("Invalid Role add acc");
        }
    }

    @Override
    public boolean deleteAccount(DTUPayUser account) {
        return CustomerList.remove(account);
    }

    @Override
    public List<DTUPayUser> getCustomerStorage() {
        return List.copyOf(CustomerList); }

    @Override
    public List<DTUPayUser> getMerchantStorage() {
        return List.copyOf(MerchantList);
    }

    @Override
    public String getAccountCounter() {
        return String.valueOf(AccountCounter);
    }

    @Override
    public DTUPayUser searchAccountByID(String accountID) {

        for (DTUPayUser customer : CustomerList){ // consider use of .stream() .filter() .collect()
            if (customer.getAccountID() == accountID){ return customer; }
        }

        for (DTUPayUser merchant : MerchantList){
            if (merchant.getAccountID() == accountID){ return merchant; }
        }

        return null;

    }

    @Override
    public boolean bankIDAlreadyExists(String bankID) { // maybe also use role parameter
        for (DTUPayUser customer : CustomerList){ // consider use of .stream() .filter() .collect()
            if(customer.getBankID().equals(bankID)) return true;
        }
        for (DTUPayUser merchant : MerchantList){ // consider use of .stream() .filter() .collect()
            if(merchant.getBankID().equals(bankID)) return true;
        }
        return false;
    }
}
