package atm_interface;

import java.util.ArrayList;
import java.security.*;
public class USER {

	private String firstName;
	private String lastName;
	private String user_id;
	private byte pinNumber[];
	private ArrayList<ACCOUNT> accounts;
	
	public USER(String firstName,String lastName, String pin, BANK theBank)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinNumber = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		this.user_id = theBank.getNewUserId();
		this.accounts = new ArrayList<ACCOUNT>();
		System.out.println("New user , "+firstName+" "+lastName+" with id "+this.user_id+" created");
		
	}

	public void addAccount(ACCOUNT anAccount) {
		this.accounts.add(anAccount);
	}

	public String getUUID() {
		return user_id;
		
	}

	public boolean validatePin(String pin) {
		
		try 
		{
			MessageDigest md= MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinNumber);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public String getFirstName() {
		return this.firstName ;
	}

	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary \n:",this.firstName);
		for(int a=0;a<this.accounts.size();a++)
		{
			System.out.printf("%d) %s\n",(a+1),this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}

	public int numAccount() {
		return this.accounts.size();
	}

	public void printAccoTransactionHistory(int theAcc) {
		
		this.accounts.get(theAcc).printTransHistory();
	}

	public double getAccountBalance(int fromAcc) {
		return this.accounts.get(fromAcc).getBalance();
	}

	public String getAccUUID(int toAcc) {
		return this.accounts.get(toAcc).getUUID();
	}

	public void addAccTransaction(int fromAcc, double d, String memo) {
		this.accounts.get(fromAcc).addTransaction(d,memo);
		
	}

	
	
}
