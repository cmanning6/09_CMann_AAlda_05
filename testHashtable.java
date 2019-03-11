/**
* Authors: Chad Manning, Abraham Aldana
* Instrucor: Dr. Wang
* Course: CMPS 3390
* Created: March 9th, 2019
* Source: testHashtable.java
*/

import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

class testHashtable 
{
static boolean programSentinel = true;
static Scanner input = new Scanner(System.in);
static Hashtable<Member> hTable = null;


public static void main(String[] args) 
{
	while (programSentinel)
		commandAction(getCommand());
	System.out.println("See you later alligator!");
}

//Determines which function to call based on keystroke
public static void commandAction(char c) 
{
	switch(c) {
		case 'g' :
		case 'G' :
			createHashtable();
			break;
		case 'a' :
		case 'A' :
			insertMember();
			break;
		case 'c' :
		case 'C' :
			print();
			break;
		case 'r' :
		case 'R' :
			promptRemove();
			break;
		case 'f' :
		case 'F' :
			findMember();
			break;
		case 'i' :
		case 'I' :
			promptRemoveAt();
			break;
		case 't' :
		case 'T' :
			showTimeComplexity();
			break;
		case 'b' :
		case 'B' :
			blockInfo();
			break;
		case 'p' :
		case 'P' :
			listParameters();
			break;
		case 'v' :
		case 'V' :
			verifyReachable();
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
			getCommand();
			break;	
	}
}

//Gets keystroke from user
public static char getCommand() 
{
	System.out.print("Enter H/h/? for help, or command : ");
	return input.next().charAt(0);
}

//Creates new hashtable with parameters from user
public static void createHashtable() 
{
	clearScreen();
	System.out.print("Enter capacity : ");
	int cap = input.nextInt();
	System.out.println();
	System.out.print("Enter load factor : ");
	int ldf = input.nextInt();
	hTable = new Hashtable<Member>(cap, ldf, 20);

	for(int i = 0; i < cap*((float) ldf) / 100; ++i) {
		hTable.put(getMember());
	}
}

//Inserts new member into hashtable
public static void insertMember() 
{
	Member mem = getMember();
	System.out.print("Added ");
	System.out.println(mem.toString());
	hTable.put(mem);
}

//Prints contents of hashtable
private static void print() 
{
	int totDisplaced = 0;
	double average = 0.0;
	boolean print = true;

	clearScreen();
    System.out.println("+===========================================================+");
    System.out.println("|                Contents of Hash Table                     |");            
    System.out.printf("|Capacity-%d,    Size-%d                                    |\n"
    					, hTable.capacity, hTable.size);            
    System.out.printf("|Load Fac.-%.2f, Increment-0.2                              |\n"
    					,((double)hTable.maximumLoadFactor)/100);
    System.out.println("+===========================================================+");            
    System.out.println("|         Object Value              |Current| Home  |Displac|");            
    System.out.println("|                                   |Address|Address| ement |");            
    System.out.println("+-----------------------------------------------------------+");
    for(int i = 0; i < hTable.capacity; ++i){
    	if (hTable.table[i] == null){
    		if (print) {
    		System.out.printf("| %33s | %5d |       |       |\n",
    							" ", i);
			System.out.println("+-----------------------------------------------------------+");
    		}
    	} else {
    		Member mem = (Member) hTable.table[i];
    		String memStr = mem.toString().substring(0, 33);
    		int home = hTable.hash(mem.getID());
    		int disp = hTable.distance(i, home)%hTable.capacity;
        	if (print) {
        		System.out.printf("| %s | %5d | %5d | %5d |\n",
        						memStr, i,
        						home,
        						disp);
    			System.out.println("+-----------------------------------------------------------+");
        	}

        	if (disp > 0) {
        		average = average + ((disp-average)/++totDisplaced);
        		disp = 0;
        	}
        }
        if ((i+1)%10 == 0 && print) {
        	System.out.printf("\t\t\t%d more record(s) to list (Q/q to quit) : ",
        	 hTable.capacity - i - 1);
        	if (input.next().charAt(0) == 'q')
        		print = false;
        }
    }
    System.out.printf("|       Displacement: Total = %d Average = %.2f             |\n",
    	totDisplaced, average);
    System.out.println("+-----------------------------------------------------------+");
}

//Asks user which ID to remove from hashtable
public static void promptRemove() 
{
	clearScreen();
	System.out.print("Enter ID to be removed: ");
	int index = hTable.locate(input.nextInt());
	System.out.println();
	if (index < 0)
		System.out.println("Member does not exist.");
	else {
		Member mem = (Member) hTable.table[index];
		hTable.remove(index);
		System.out.printf("%s removed.\n", mem.toString().substring(0, 33));
	}
}

//Asks user which index to remove from table
public static void promptRemoveAt()
{
	System.out.print("Enter index to remove : ");
	int index = input.nextInt();
	System.out.println();
	if ((index < hTable.capacity) && (hTable.table[index] != null)) {
		System.out.printf("%s removed\n", hTable.remove(index).toString().substring(0,33));
	} else {
		System.out.println("Index does not exist.");
	}
}

//Finds a given member ID in the table
public static void findMember() 
{
	System.out.print("Enter ID to locate: ");
	int index = hTable.locate(input.nextInt());
	System.out.println();
	if (index < 0) {
		System.out.println("No member found.");
	} else {
		System.out.printf("%s \nCurrent address = %d \nHome address = %d\n\n",
			hTable.table[index].toString(), index, hTable.hash((Member) hTable.table[index]));
	}
}	

//Returns a new random member
public static Member getMember()
{
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
	
//Calculates time complexity of hash search
public static void showTimeComplexity() {
	double PHS = 0; //Practical Hashtable Successful
	double PHU = 0; //Practical Hashtable Unsuccessful
	double THS = 0; //Theoretic Hashtable Successful
	double THU = 0; //Theoretic Hashtable Unsuccessful
	double TBS = 0; //Theoretical Binary Search
	int reachable = 0; //Number of reachable elements
	int unreachable = 0; //Number of unreachable elements
	int index = 0;  //Home index for search item

	for (int i = 0; i < hTable.capacity ; ++i) {
		if ((index = hTable.locate((Member) hTable.table[i])) >= 0) {
			PHS += hTable.distance(i, index);
			++reachable;
		} else {
			PHU += hTable.distance(i, index);
			++unreachable;
		}
	}
	PHS /= hTable.capacity;
	PHU /= hTable.capacity;
	THS = (1 + 1.0/(1-reachable))/2;
	THU = (1+1.0/(Math.pow(1-unreachable, 2)))/2;
	TBS = Math.log10(2.0) * Math.log10(hTable.size);

	clearScreen();
    	System.out.println("+========================================================================+");
	System.out.println("|          Time Complexities of Practical & Theoretic Hashtable          |");
	System.out.println("|              Search vs. Theoretic Binary Search Algorithm              |");
	System.out.println("+========================================================================+");
	System.out.println("|  Practical  |  Practical  |  Theoretic  |  Theoretic  |   Theoretical  |");
	System.out.println("|  Hashtable  |  Hashtable  |  Hashtable  |  Hashtable  |     Binary     |");
	System.out.println("|  Successful | Unsuccessful|  Successful | Unsuccessful|     Search     |");
	System.out.println("+-------------+-------------+-------------+-------------+----------------+");
	System.out.printf("| %11.2f | %11.2f | %11.2f | %11.2f | %14.2f |\n"
		,PHS,PHU,THS,THU,TBS);
	System.out.println("+-------------+-------------+-------------+-------------+----------------+");
	System.out.println();


}

public static void blockInfo() {

	int count = 0;
	String data = "Empty";
	int startAddress = 0;
	int endAddress = 0;
	int size = 0;
	int dFlag = 0;
	int eFlag = 0;
	int dCount = 0;
	int eCount = 0;
	int dMax = 0;
	int eMax = 0;
	int dMin = 10;
	int eMin = 10;
	float dSize = 0;
	float eSize = 0;

	clearScreen();
	System.out.println("+===================================================+");
	System.out.println("|        Information on Data and Blank Blocks       |");
	System.out.println("+===================================================+");
	System.out.println("|  Block Type  |   Starting   |   Ending   |  Size  |");
	System.out.println("|              |    Address   |   Address  |        |");
	System.out.println("+--------------+--------------+------------+--------+");

	int i = 0;
	while (i < 12) {
		// Member mem = (Member) hTable.table[count];

		//end of table
		if (count == hTable.capacity) {
			endAddress = count - 1;
			size = (endAddress - startAddress) + 1;
			System.out.printf( "|    %5s     |     %3d      |    %3d     |  %3d   |\n+--------------+--------------+------------+--------+\n",data, startAddress, endAddress, size);
			if (dFlag == 1) {
				dSize += size;
				dCount++;
				if (size > dMax)
					dMax = size;
				if (size < dMin)
					dMin = size;
			}
			else {
				eSize += size;
				eCount++;
				if (size > eMax)
					eMax = size;
				if (size < eMin)
					eMin = size;
			}
			break;
		}

		Member mem = (Member) hTable.table[count];

		//empty
		if (mem == null) {
			if (eFlag == 0){
				dFlag = 0;
				eFlag = 1;

				if (count != 0) {
					endAddress = count - 1;
					size = (endAddress - startAddress) + 1;
					System.out.printf( "|    %5s     |     %3d      |    %3d     |  %3d   |\n+--------------+--------------+------------+--------+\n",data, startAddress, endAddress, size);
					dSize += size;
					dCount++;
					if (size > dMax)
						dMax = size;
					if (size < dMin)
						dMin = size;

				}

				data = "Empty";
				startAddress = count;
				i++;
			}
		}

		//full
		if (mem != null) {
			if (dFlag == 0){
				dFlag = 1;
				eFlag = 0;

				if (count != 0) {
					endAddress = count - 1;
					size = (endAddress - startAddress) + 1;
					System.out.printf( "|    %5s     |     %3d      |    %3d     |  %3d   |\n+--------------+--------------+------------+--------+\n",data, startAddress, endAddress, size);
					eSize += size;
					eCount++;
					if (size > eMax)
						eMax = size;
					if (size < eMin)
						eMin = size;
				}

				data = "Data";
				startAddress = count;
				i++;
			}
		}

		count++;
		if (i == 11) {
			System.out.println("\t\t\t\tEnter Q/q to quit listing ...");
			Scanner userInput = new Scanner(System.in);
			char option = userInput.next(".").charAt(0);
			if (option == 'q' || option == 'Q')
				i++;
			else
				i = 0;
		}
	}

	System.out.println("+------------+-------+---------+---------+----------+");
	System.out.println("| Block Type | Count | Maximum | Minimum | Avg Size |");
	System.out.printf( "|    Data    |  %3d  |   %3d   |   %3d   |   %.2f   |\n",dCount,dMax,dMin,dSize/dCount);
	System.out.printf( "|    Empty   |  %3d  |   %3d   |   %3d   |   %.2f   |\n",eCount,eMax,eMin,eSize/eCount);
	System.out.println("+------------+-------+---------+---------+----------+");
	System.out.println();
}

//List parameters of the hashtable
public static void listParameters() {

	clearScreen();
    System.out.println("+=====================================================================+");
    System.out.println("|                  Parameters of the Hash Table                       |");
	System.out.println("+=====================================================================+");
	System.out.println("|  Capacity  |    Size    |  Increment  |  Specified  |  Actual Load  |");
	System.out.println("|            |            |             | Load Factor |     Factor    |");
	System.out.println("+------------+------------+-------------+-------------+---------------+");
	System.out.printf("| %10d | %10d |    %3d%%     |   %4d%%     |    %4d%%      |\n",
		hTable.capacity,
		hTable.size,(int) (hTable.incPercentage*100),
		hTable.maximumLoadFactor,
		((int) (((double) hTable.size)/hTable.capacity*100)));
	System.out.println("+------------+------------+-------------+-------------+---------------+");
	System.out.println();

}

//Determines how many members based on list size are actually reachable
public static void verifyReachable() {
	int reachable = 0, unreachable = 0;

	for(int i = 0; i < hTable.capacity; ++i) {
		if (hTable.locate((Member) hTable.table[i]) > -2) ++reachable;
		else ++unreachable; 
	}
	clearScreen();
	System.out.printf("Hashtable size = %d\nReachable Members = %d\n" + 
		"Unreachable Members = %d\n\n",
		 hTable.capacity, reachable, unreachable);
}


//Prints menu of commands
public static void printMenu() 
{
	clearScreen();
	System.out.println("+===========================================================================================================================+");
	System.out.println("|                                                Implementation of Hashtable                                                |");
	System.out.println("|                                                        Assignment                                                         |");
	System.out.println("+===========================================================================================================================+");
	System.out.println("| Command  |                   Description                    | Command  |                   Description                    |");
	System.out.println("+----------+--------------------------------------------------+----------+--------------------------------------------------|");
	System.out.println("|   g G    |  Prompt for two integers, the capacity and the   |   t T    | Perform a successful search on each of object in |");
	System.out.println("|          |  load factor of a hash table. Create a new hash  |show Time | the hash table, and 'capacity' many unsuccessful |");
	System.out.println("|          |table, with 20% as increment percentage, generate |Complexity| searches, list the (1) average comparions from   |");
	System.out.println("|          |(capacity * load factor) many mixed Member objects|of Bin. & |all successful searchs, the theoretic susccessful |");
	System.out.println("|          |         and add them to the hash table.          |Hash. Srch|search complexity [(1 + 1/(1-a))/2], and the      |");
	System.out.println("+----------+--------------------------------------------------+See exampl|theoretical un-successful search time complexity  |");
	System.out.println("|   a A    | Instantiate a new member object, and the object  |e below.  |[(1+1/(1-a)**2)/2], where a is the loading factor.|");
	System.out.println("|          |   into the hash table. Display the newly added   +----------+--------------------------------------------------+");
	System.out.println("|          |  member, its home address and current address.   |   b B    |Display information on blocks formed by contiguous|");
	System.out.println("+----------+--------------------------------------------------+          |data or empty cells inside table. For each block, |");
	System.out.println("|   r R    | Ask for an ID of an object. Remove               |          |display the type of block (either data or empty), |");
	System.out.println("|          |     the object whose ID matchs the given ID.     |          |the starting and ending addresses, size of block. |");
	System.out.println("+----------+--------------------------------------------------+          |At the end of block listing, show the total number|");
	System.out.println("|   f F    |   Ask for an ID of a object,                     |          |   of blocks, the maximum, the minimum and the    |");
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

//Clears screen to avoid clutter
public static void clearScreen() 
{
	System.out.print("\033[H\033[2J");
	try {
	    Runtime.getRuntime().exec("clear");
	} catch(Exception e) {}
}

}
