package atm_interface;

import java.util.ArrayList;
import java.util.Random;

public class BANK {

	private String name;
	private ArrayList<USER> users;
	private ArrayList<ACCOUNT> accounts;
	
	
	
	public BANK(String name) {
		this.name =name;
		this.users = new ArrayList<USER>();
		this.accounts = new ArrayList<ACCOUNT>();
	}


	public String getNewUserId()
	{
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		do {
			uuid="";
			for(int i=0;i<len;i++)
				uuid +=((Integer)rng.nextInt(10)).toString();
			nonUnique = false;
			for(USER u : this.users)
			{
				if(uuid.compareTo(u.getUUID())==0)
				{
					nonUnique = true;
					break;
				}
			}
		}
		while(nonUnique);
		
		return uuid;
	}


	public String getNewAccountId() {
		
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		do {
			uuid="";
			for(int i=0;i<len;i++)
				uuid +=((Integer)rng.nextInt(10)).toString();
			
			nonUnique = false;
			for(ACCOUNT a : this.accounts)
			{
				if(uuid.compareTo(a.getUUID())==0)
				{
					nonUnique = true;
					break;
				}
			}
		}
		while(nonUnique);
		
		return uuid;
	}

    public void addAccount(ACCOUNT anAccount)
    {
    	this.accounts.add(anAccount);
    }
	
	public USER addUser(String firstName, String lastName,String pinNumber)
	{
		USER newUser = new USER(firstName, lastName,pinNumber,this);
		this.users.add(newUser);
		
		ACCOUNT newAccount = new ACCOUNT("Savings", newUser, this);
		
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
	}
	public USER userLogin(String userId, String pin)
	{
		for(USER u : this.users)
		{
			if(u.getUUID().compareTo(userId)==0 && u.validatePin(pin))
			{
				return u;
			}
				
		}
		return null;
	}

	public String getName()
	{
		return this.name;
	}
	
	
	
	
}
