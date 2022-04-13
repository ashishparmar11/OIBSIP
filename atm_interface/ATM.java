package atm_interface;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args)
	{
		Scanner sc= new Scanner(System.in);
		
		BANK theBank = new BANK("BANK OF ASHISH");
		USER bUser = theBank.addUser("ASHISH", "PARMAR", "12345");
		
		ACCOUNT newAccount = new ACCOUNT("Checking",bUser,theBank);
		bUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		USER currUser;
		while(true)
		{
			currUser= ATM.mainMenuPrompt(theBank,sc);
			
			ATM.printUserMenu(currUser,sc);
		}
	}

	private static void printUserMenu(USER currUser, Scanner sc) {
		currUser.printAccountsSummary();
		int choice;
		do {
			System.out.println("Welcome "+currUser.getFirstName());
			System.out.println("What would you would like to do?");
		
			System.out.println("1. Show Account transaction history ");
			System.out.println("2. Withdrawl");
			System.out.println("3. Deposit");
			System.out.println("4. Transfer");
			System.out.println("5. Quit");
			
			System.out.println();
			System.out.println("Enter choice:");
			choice = sc.nextInt();
			
			if(choice <1 || choice > 5)
				System.out.println("Invalid choice . please choose 1-5");
			
		}while(choice< 1 || choice> 5);
		switch(choice)
		{
		case 1:
			ATM.showTranHistory(currUser,sc);
			break;
		case 2:
			ATM.withdrawMoney(currUser,sc);
			break;
		case 3:
			ATM.depositMoney(currUser,sc);
			break;
		case 4:
			ATM.transferMoney(currUser,sc);
			break;
		case 5:
			sc.nextLine();
			break;
		}
		if(choice != 5)
		{
			ATM.printUserMenu(currUser, sc);
		}
		
	}

	private static void depositMoney(USER currUser, Scanner sc) {
		
		int toAcc;
		
		double amount;
		double accbal;
		String memo;
		do
		{
			System.out.printf("Enter the number (1-%d) of the account to deposit in :",
					currUser.numAccount());
			toAcc = sc.nextInt()-1;
			if(toAcc <0 || toAcc > currUser.numAccount())
			{
				System.out.println("Invald accoung. Please try again");
			}
		}while(toAcc <0 || toAcc > currUser.numAccount());
		
		accbal = currUser.getAccountBalance(toAcc);

		do
		{
			System.out.printf("Enter the amount to get transfer (max $%.02f) : $",accbal);
			amount =sc.nextDouble();
			
			if(amount<0)
				System.out.println("Amount must be grater than 0");
			
				
		}
		while(amount <0);
		
		sc.nextLine();
		System.out.println("Enter  a memo: ");
		memo = sc.nextLine();
		
		currUser.addAccTransaction(toAcc, amount, memo);
	}

	private static void withdrawMoney(USER currUser, Scanner sc) {
		
		int fromAcc;
		
		double amount;
		double accbal;
		String memo;
		do
		{
			System.out.printf("Enter the number (1- %d) of the account to withdraw from :",
							currUser.numAccount());
			fromAcc = sc.nextInt()-1;//a
			if(fromAcc <0 || fromAcc > currUser.numAccount())
			{
				System.out.println("Invald accoung. Please try again");
			}
		}while(fromAcc <0 || fromAcc > currUser.numAccount());
		
		accbal = currUser.getAccountBalance(fromAcc);

		do
		{
			System.out.printf("Enter the amount to get transfer (max $%.02f) : $",accbal);
			amount =sc.nextDouble();
			
			if(amount<0)
				System.out.println("Amount must be grater than 0");
			else if(amount > accbal)
				System.out.println(" Amount must not be grater than "+accbal);
				
		}
		while(amount <0|| amount> accbal);
		
		sc.nextLine();
		System.out.println("Enter  a memo: ");
		memo = sc.nextLine();
		
		currUser.addAccTransaction(fromAcc, -1*amount, memo);
		
	}

	private static void transferMoney(USER currUser, Scanner sc) {
		int fromAcc;
		int toAcc;
		double amount;
		double accbal;
		do
		{
			System.out.printf("Enter the number (1-%d) of the account to transfer from",currUser.numAccount());
			fromAcc = sc.nextInt()-1;
			if(fromAcc <0 || fromAcc > currUser.numAccount())
			{
				System.out.println("Invald accoung. Please try again");
			}
		}while(fromAcc <0 || fromAcc > currUser.numAccount());
		
		accbal = currUser.getAccountBalance(fromAcc);
		
		do
		{
			System.out.println("Enter the number of the account to transfer to :");
			toAcc = sc.nextInt();
			if(toAcc <0 || toAcc > currUser.numAccount())
			{
				System.out.println("Invald accoung. Please try again");
			}
		}while(toAcc <0 || toAcc > currUser.numAccount());
		
		do
		{
			System.out.printf("Enter the amount to get transfer (max $%.02f) : $",accbal);
			amount =sc.nextDouble();
			
			if(amount<0)
				System.out.println("Amount must be grater than 0");
			else if(amount > accbal)
				System.out.println(" Amount must not be grater than "+accbal);
				
		}
		while(amount <0|| amount> accbal);
		
		currUser.addAccTransaction(fromAcc,-1*amount, String.format
			("Transfer to account %s",currUser.getAccUUID(toAcc)));
		currUser.addAccTransaction(toAcc,-1*amount, String.format
				("Transfer to account %s",currUser.getAccUUID(fromAcc)));
	}

	private static void showTranHistory(USER currUser, Scanner sc) {
		int theAcc;
		do {
			System.out.printf("Enter the number (1-%d) of the account: \n"
					+ "whose transaction you want to see",currUser.numAccount());
			
			
			theAcc = sc.nextInt()-1;
			if(theAcc < 0 || theAcc >= currUser.numAccount())
			{
				System.out.println("Invalid account. Please try again");
			}
				
		}while(theAcc < 0 || theAcc >= currUser.numAccount());
		
		currUser.printAccoTransactionHistory(theAcc);

		
	}

	private static USER mainMenuPrompt(BANK theBank, Scanner sc) {
		String userId;
		String pin;
		USER auth;
		do
		{
			System.out.println("Welcome to "+theBank.getName());
			System.out.println("Enter userId : ");
			userId = sc.nextLine();
			System.out.println("Enter pin Number :");
			pin = sc.nextLine();
			
			auth = theBank.userLogin(userId, pin);
			if(auth==null)
			{	
				System.out.println("Incorrect user ID/pin combination .");
				System.out.println("Please try again");
				
			}
			
		}
		while(auth==null);
		return auth;
	}
	

	
	
	
	
	
	
	
	

}
