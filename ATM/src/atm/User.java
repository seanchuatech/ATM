package atm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class User {
	
	//The first name of the user
	private String firstName;
	
	//The last name of the user
	private String lastName;
	
	
	//The ID number of the user
	private String uuid;
	
	//The MP5 hash of the user's pin number
	private byte pinHash[];
	
	//The list of accounts of this user
	private ArrayList<Account> accounts;
	
	
	/*
	 * @param firstName the user's first name
	 * @param lastName the user's last name
	 * @param pin the user's account pin number
	 * @param theBank the the Bank object that the user is a customer of
	 */
	public User(String firstName, String lastName, String pin, Bank theBank) {
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pani's MD5 has, rather than the original value for security reasons.
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("error caught no such algo exception");
			e.printStackTrace();
			System.exit(1);
		}
		
		// get a new unique universal ID for the user
		this.uuid = theBank.getNewUserUUD();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		//print log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
	}
	
	
	/**
	 * Add an account for the user
	 * @param anAcct the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	/**
	 * Return the user's UUD
	 * @return the UUID
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/*
	 * check whether a given pin matchers the true User pin
	 * @param aPin the pin to check
	 * @return whether the pin is valid or not
	 */
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("error caught no such algo exception");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
		
	}
	
	
	//return user first name
	public String getFirstName() {
		return this.firstName;
	}
	
	//print summaries for the accounts of this user.
	
	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	
	/*
	 * Get the number of accounts of the user
	 * @return the number of accounts
	 */
	public int numAccounts() {
		return this.accounts.size();
	}
	
	/*
	 * Print transaction history for a particular account.
	 * @param acctIdx the index of the account to use
	 */
	public void printAcctTransactionHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}
	
	//@param acctIdx
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}
	
	/*
	 * Get the UUID of a particular account
	 * @param acctIdx the index of the account to use
	 * @return the UUID of the account
	 */
	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}
	
	/*
	 * Add a transaction to a particular account
	 * @param acctIdk the index of the account
	 * @param amount the amount of the transaction
	 * @param memo the memo of the transaction
	 */
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
}
