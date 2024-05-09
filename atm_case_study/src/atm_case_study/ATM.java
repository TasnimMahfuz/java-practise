package atm_case_study;

public class ATM {
	
	private boolean userAuthenticated;
	private int currentAccountNumber;
	
	private Screen screen;
	private CashDispenser cashDispenser;
	private Keypad keypad;
	private DepositSlot depositSlot;
	private BankDatabase bankDatabase;
	
	
	private static final int BALANCE_INQUIRY = 1;
	private static final int WITHDRAWAL = 2;
	private static final int DEPOSIT = 3;
	private static final int EXIT = 4;
	
	public ATM()
	{
		this.userAuthenticated = false;
		this.currentAccountNumber = 0;
		
		this.screen = new Screen();
		this.cashDispenser = new CashDispenser();
		this.keypad = new Keypad();
		this.depositSlot = new Depositslot();
		this.bankDatabase = new BankDatabase();
	}
	
	public void run()
	{
		while(true)
		{
			while(!userAuthenticated)
			{
				screen.displayMessageLine("\nWelcome\n");
				authenticateUser();
			}
			
			performTransactions();
			
			userAuthenticated = false;
			currentAccountNumber = 0;
			
			screen.displayMessageLine("\nThank You. Goodbye\n");
			
		}
		
	}
	
	private void authenticateUser()
	{
		screen.displayMessage("\nPlease Enter your account number: \n");
		int accountNumber = keypad.getInput();
		
		screen.displayMessage("\nEnter Your Pin:\n");
		int pin = keypad.getInput();
		
		userAuthenticated = bankDatabase.authenticateUser(accountNumber,pin);
		
		if(this.userAuthenticated)
		{
			this.currentAccountNumber = accountNumber;
		}
		else
		{
			screen.displayMessage("Invalid Credentials. Please try again.\n");
		}
	}
	
	private void performTransactions()
	{
		Transaction currentTransaction = null;
		boolean userExited = false;
		
		while(!userExited)
		{
			int mainMenuSelection = displayMainMenu();
			
			switch(mainMenuSelection)
			{
			case BALANCE_INQUIRY:
			case WITHDRAWAL:
			case DEPOSIT:
				
				currentTransaction = createTransaction(mainMenuSelection);
				
				currentTransaction.execute();
				break;
			case EXIT:
				
				screen.displayMessageLine("\nExiting the System\n");
				userExited = true;
				break;
			default:
				screen.displayMessageLine("\nYou Did not enter valid selection. Try Again!\n");
				break;
			}
		}
	}
	
	private int displayMainMenu()
	{
	screen.displayMessageLine( "\nMain menu:" );
	screen.displayMessageLine( "1 - View my balance" );
	screen.displayMessageLine( "2 - Withdraw cash" );
	screen.displayMessageLine( "3 - Deposit funds" );
	screen.displayMessageLine( "4 - Exit\n" );
	screen.displayMessage( "Enter a choice: " );
	return keypad.getInput(); // return user's selection
	}
	
	private Transaction createTransaction(int type)
	{
		Transaction temp = null;
		
		switch(type)
		{
		case BALANCE_INQUIRY:
			temp  = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
			break;
		case WITHDRAWAL:
			temp = new WithDrawal(currentAccountNumber,screen, bankDatabase, keypad, cashDispenser);
			break;
		case DEPOSIT:
			temp = new Deposit(currentAccountNumber, screen , bankDatabase, keypad, depositSlot);
			break;
		}
		
		return temp;
	}
	
	
}
