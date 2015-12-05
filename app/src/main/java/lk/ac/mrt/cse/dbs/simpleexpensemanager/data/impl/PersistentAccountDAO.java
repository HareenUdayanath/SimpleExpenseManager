package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.DBHelper;

/**
 * Created by Asus on 12/3/2015.
 */
public class PersistentAccountDAO implements AccountDAO {
    /*
    * this class can be used instead of InMemoryAccountDAO
     * */

    /*
    * instead of a account list this class use database to maintain persistent data
    * collection of accounts
    * */

    DBHelper db = null;

    public PersistentAccountDAO(Context context){
        this.db = DBHelper.getInstance(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        return db.getAccountNumberList();
    }

    @Override
    public List<Account> getAccountsList() {
        return db.getAccountList();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account account = db.getAccount(accountNo);
        /**
         * if there is no account corresponding to that account number
         * db.getaccount method will return null
         */

        if (account!=null){
            return account;
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
        db.addAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        /**
         * db.removeAccount will return false when the deletion of account is unsuccessful
         * most probable reason for this is that the given account number is not in the database
         */
        if (!db.removeAccount(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        /**
         * db.updateBalance will return false when the update of account is unsuccessful
         * most probable reason for this is also the given account number is not in the database
         */
        if(!db.updateBalance(accountNo,expenseType,amount)){
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }
}
