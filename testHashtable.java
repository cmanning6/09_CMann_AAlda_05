import java.util.Scanner;
import java.util.Random;

class testHashtable {
	static boolean programSentinel = true;
	static Scanner input = new Scanner(System.in);
	static Hashtable<Member> hTable = null;


	public static void main(String[] args) {
		while (programSentinel)
			commandAction(getCommand());
	}

	public static void commandAction(char c) {
		switch(c) {
			case 'c' : 
			case 'C' : 
				getCommand();
				break;
			case 'g' : 
			case 'G' : 
				createHashtable();
				break;
			case 'a' : 
			case 'A' : 
				insertMember();
				break;
			case 'h' : 
			case 'H' : 
			case '?' : 
				printMenu();
				break;
			case 'q' : 
			case 'Q' : 
				programSentinel = false;
				break;
			default : 
				break;	
		}
	}

	public static char getCommand() {
		System.out.print("Enter H/h/? for help, or command : ");
		return input.next().charAt(0);
	}

	public static void createHashtable() {
		System.out.print("Enter capacity : ");
		int cap = input.nextInt();
		System.out.println();
		System.out.print("Enter load factor : ");
		int ldf = input.nextInt();
		hTable = new Hashtable<Member>(cap, ldf, 20);

		for(int i = 0; i < 40/*cap*ldf*/; ++i)
			hTable.put(getMember());
	}

	public static void insertMember() {
		Member mem = getMember();
		System.out.print("Added ");
		System.out.println(mem.toString());
		hTable.put(mem);
	}

	private static void print() {
        System.out.println("+======================================================+");
        System.out.println("|                Contents of Hash Table                |");            
        System.out.printf(" |[Capacity-%d, Size-%d Load Fac.-%d%, Increment-0.2] |"
        					, hTable.capacity,hTable.size,hTable.maximumLoadFactor);            
        System.out.println("+======================================================+");            
        System.out.println("|         Object Value         |Current| Home  |Displac|");            
        System.out.println("|                              |Address|Address| ement |");            
                        
                        
                        
              
    }

	public static Member getMember(){
		Random rnd = new Random();
		switch(rnd.nextInt(5)) {
			case 0 :
				return new Member();
			case 1 :
				return new Staff();
			case 2 :
				return new Employee();
			case 3 :
				return new Faculty();
			case 4 :
				return new Student();
			default : 
				return new Member();
		}
	}


	public static void printMenu() {

		System.out.println("+===========================================================================================================================+");
		System.out.println("|                                               Implementation of Hashtable                                                |");
		System.out.println("|                                                       Assignment                                                         |");
		System.out.println("+===========================================================================================================================+");
		System.out.println("| Command  |                   Description                    | Command  |                   Description                    |");
		System.out.println("+----------+--------------------------------------------------+----------+--------------------------------------------------|");
		System.out.println("|   g G    |  Prompt for two integers, the capacity and the  |   t T    | Perform a successful search on each of object in |");
		System.out.println("|          |  load factor of a hash table. Create a new hash  |show Time | the hash table, and 'capacity' many unsuccessful |");
		System.out.println("|          |table, with 20% as increment percentage, generate |Complexity| searches, list the (1) avareage comparions from  |");
		System.out.println("|          |(capacity * load factor) many mixed Member objects|of Bin. & |all successful searchs, the theoretic susccessful |");
		System.out.println("|          |         and add them to the hash table.          |Hash. Srch|search complexity [(1 + 1/(1-a))/2], and the      |");
		System.out.println("+----------+--------------------------------------------------+See exampl|theoretical un-successful search time complexity  |");
		System.out.println("|   a A    | Instantiate a new member object, and the object  |e below.  |[(1+1/(1-a)**2)/2], where a is the loading factor.|");
		System.out.println("|          |   into the hash table. Display the newly added   +----------+--------------------------------------------------+");
		System.out.println("|          |  member, its home address and current address.   |   b B    |Display information on blocks formed by contiguous|");
		System.out.println("+----------+--------------------------------------------------+          |data or empty cells inside table. For each block, |");
		System.out.println("|   r R    | Ask for an ID or hash code of an object. Remove  |          |display the type of block (either data or empty), |");
		System.out.println("|          |     the object whose ID matchs the given ID.     |          |the starting and ending addresses, size of block. |");
		System.out.println("+----------+--------------------------------------------------+          |At the end of block listing, show the total number|");
		System.out.println("|   f F    |   Ask for an ID or hash code, of a object,       |          |   of blocks, the maximum, the minimum and the    |");
		System.out.println("|          | display the object, ID current address and home  |          |  average block sizes, for each type. Allow quit  |");
		System.out.println("|          |                     address.                     |          | listing the total, maximum, minimum and average  |");
		System.out.println("+----------+--------------------------------------------------+          |must show the correct data even if the listing of |");
		System.out.println("|   c C    |Show contents of hash table in a tabular way. one |          |                   block ends.                    |");
		System.out.println("|          | object per line and ten objects per screen. for  +----------+--------------------------------------------------+");
		System.out.println("|          |   each line, the following columns have to be    |   p P    |List the table parameters. The parameters include.|");
		System.out.println("|          | displayed: OBject, current address, home address |          |    the capacity, size, load factor, increment    |");
		System.out.println("|          |  and the displacement from home address to its   |          |      percentage,and the actual load factor.      |");
		System.out.println("|          |                 current address.                 +----------+--------------------------------------------------+");
		System.out.println("+----------+--------------------------------------------------+   v V    |    Verify whether all non-null elements in table |");
		System.out.println("|  i I x   |   Remove object at I, and do shift.              |          |         are rearchable or not.                   |");
		System.out.println("+----------+--------------------------------------------------+----------+--------------------------------------------------+");
		System.out.println("|  h H ?   |                 Show this menu.                  |   q Q    |                Exit the program.                 |");
		System.out.println("+----------+--------------------------------------------------+----------+--------------------------------------------------+");
	}
}