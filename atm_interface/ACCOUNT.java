package atm_interface;

import java.util.ArrayList;

public class ACCOUNT {

	private String name;
	private String uuid;
	private USER holder;
	
	private ArrayList<TRANJACTION> transaction;
	
	public ACCOUNT(String name,USER holder, BANK theBank )
	{
		this.name = name;
		this.holder = holder;
		this.uuid = theBank.getNewAccountId();
		
		this.transaction = new ArrayList<TRANJACTION>();
		
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getSummaryLine() {
		double balance = this.getBalance();
		
		if(balance >=0)
		{
			return String.format("%s : $%.02f : %s",this.uuid ,balance,this.name);
		}
		else
			return String.format("%s : $(%.02f) :%s",this.uuid , balance,this.name);

	}

	double getBalance() 
	{
		double balance=0;
		for(TRANJACTION t: this.transaction)
			balance += t.getAmount();
		return balance;
	}

	public void printTransHistory() 
	{
		System.out.println("Transaction history for account \n"+this.uuid);
		for(int i=this.transaction.size()-1;i>=0;i--)
		{
			System.out.println("\n"+this.transaction.get(i).getSummaryyLine());
		}
		System.out.println();
		
	}

	public void addTransaction(double d, String memo) {
		
		TRANJACTION transaction = new TRANJACTION(d,memo,this);
		this.transaction.add(transaction);
	}
}
