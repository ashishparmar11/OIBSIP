package atm_interface;

import java.util.Date;

public class TRANJACTION {

	private double amount;
	private Date timestamp;
	private String acc_statement;
	private ACCOUNT inAccount;
	
	public TRANJACTION(double amount, ACCOUNT inAccount)
	{
		this.amount = amount;
		this.acc_statement = acc_statement;
		this.timestamp = new Date();
		this.inAccount = inAccount;
	}
	public TRANJACTION(double amount,String acc_statement, ACCOUNT inAccount)
	{
		this(amount ,inAccount);
		this.acc_statement = acc_statement;
	}
	public double getAmount() {
		return this.amount;
	}
	public String getSummaryyLine() 
	{
		if(this.amount >= 0)
		{
			return String.format("%s : $%.2f : %s",this.timestamp.toString(),
					this.amount,this.acc_statement);
		}
		else
			return String.format("%s : $(%.2f) : %s",this.timestamp.toString(),
					this.amount,this.acc_statement);
	}
}
