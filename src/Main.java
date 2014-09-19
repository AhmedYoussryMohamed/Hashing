import java.util.Iterator;


public class Main {
	
	public static void main(String[] args) {
//		testLinearProbing();
//		testQuadraticProbing();
//		testPseudoRandomProbing();
//		testSeparateChaining();
//		testBucketing();
//		testDoubleHashing();
//		test();
		mainTest();
		
	}//end main method.
	
	public static void test(){
		SeparateChaining<Integer, Integer> hash = new SeparateChaining<Integer, Integer>();
		System.out.println("Hash is empty? (true) " + hash.isEmpty());
		System.out.println("Insert 4567....");
		hash.put(4567, 4567);
		System.out.println("Hash is empty? (false) " + hash.isEmpty());
		System.out.println("Insert 67....");
		hash.put(67, 67);
		System.out.println("Insert 37....");
		hash.put(37, 37);
		System.out.println("Hash size? (3) " + hash.size());
//		hash.print();
		System.out.println("Contains 4567? (true): " + hash.contains(4567));
		System.out.println("Contains 37? (true): " + hash.contains(37));
		System.out.println("Contains 7? (false): " + hash.contains(7));
		System.out.println("Insert 0 - 29....");
		for (int i = 0; i < 30; i++) hash.put(i, i);
//		hash.print();
		System.out.println("Contains 7? (true): " + hash.contains(7));
		System.out.println("Delete 4567....");
		hash.delete(4567);
		System.out.println("Contains 4567? (false): " + hash.contains(4567));
		System.out.println("Contains 100? (false): " + hash.contains(100));
		System.out.println("Delete a non-existing entry: 100");
		hash.delete(100);
		System.out.println("Contains 100? (false): " + hash.contains(100));
		System.out.println("Insert Duplicate: 37");
		System.out.println("Insert 37 (val 73)....");
		hash.put(37, 73);
		System.out.println("New value of 37: " + hash.get(37));
		System.out.println("Hash is empty? (false) " + hash.isEmpty());
		System.out.println("Hash size? (31) " + hash.size());
		Iterable<Integer> keys = hash.keys();
		System.out.println("Print keys: ");
		System.out.println(keys.toString());
	}
	
	public static void mainTest(){
		
//		LinearProbing<Integer, Integer> hash = new LinearProbing<Integer,Integer>();
//		QuadraticProbing<Integer, Integer> hash = new QuadraticProbing<Integer,Integer>();
//		PseudoRandomProbing<Integer, Integer> hash = new PseudoRandomProbing<Integer,Integer>();
		DoubleHashing<Integer, Integer> hash = new DoubleHashing<Integer,Integer>();
//		Bucketing<Integer,Integer> hash = new Bucketing<Integer,Integer>();
//		SeparateChaining<Integer, Integer> hash = new SeparateChaining<Integer,Integer>();
		
		int constant = 100000;
		System.out.println("Inserting " + constant + " elements in the HashTable:");
		for( int i = 0 ;i < constant ;i++){
			hash.put( i, i );
		}//end for.
//		hash.printHashTable();
		System.out.println("Memory Used: " + hash.getMemoryUsed()  );
		System.out.println("Number of Rehashing: " + hash.getRehashing());
		System.out.println("Number of Collisions: " + hash.getCollisions()  );
		
		
	}//end method.
	
	public static void testLinearProbing(){
		System.out.println("------- Linear Probing:--------------- ");
		HashTable<Integer, Integer> check = new LinearProbing<Integer ,Integer >();
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println("\nPUT:");
		check.put( 0 , 5 );
		check.put(10, 15);
		check.put(20 , 25);
		
		
		check.put(30	, 35);
		check.put(40	, 45);
		check.put(50	, 55);
		

		check.put(60	, 55);
		check.put(70	, 55);
		check.put(80	, 55);
		check.put(90	, 55);
		
		check.delete(0);
//		check.put(0,200);
		System.out.println("\nCheck:");
		check.printHashTable();
		
		System.out.println("\nGet:");
		System.out.println( check.get( new Integer(20) ) );
		System.out.println( check.get( new Integer(90) ) );
		
		System.out.println("\nContains:");
		System.out.println( check.contains(3) );
		
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println( "\nIterable:  " );
		Iterable it= check.keys();
		Iterator t = it.iterator();
		while( t.hasNext() ){
			System.out.println( t.next() );
		}
	}//end method.
	
	public static void testQuadraticProbing(){
		System.out.println("------- Quadratic Probing:--------------- ");
		HashTable<Integer, Integer> check = new QuadraticProbing<Integer ,Integer >();
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println("\nPUT:");
		check.put( 0 , 5 );
		check.put(10	, 15);
		check.put(20	, 25);
		
		
		check.put(30	, 35);
		check.put(40	, 45);
		
		check.put(50	, 55);
		check.put(50	, 255);
		check.delete(10);
		System.out.println("\nCheck:");
		check.printHashTable();
		
		System.out.println("\nGet:");
		System.out.println( check.get( new Integer(20) ) );
		System.out.println( check.get( new Integer(10) ) );
		
		System.out.println("\nContains:");
		System.out.println( check.contains(30) );
		
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println( "\nIterable:  " );
		Iterable it= check.keys();
		Iterator t = it.iterator();
		while( t.hasNext() ){
			System.out.println( t.next() );
		}
	}//end method.
	
	public static void testPseudoRandomProbing(){
		System.out.println("------- Pseudo Random Probing:--------------- ");
		HashTable<Integer, Integer> check = new PseudoRandomProbing<Integer ,Integer >();
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println("\nPUT:");
		check.put( 0 , 5 );
		check.put(10	, 15);
		check.put(110	, 25);
		
		
		check.put(20	, 35);
		check.put(30	, 45);
		
		check.put(40	, 55);
		check.put(50	, 9);
		check.put(60	, 20);
		check.put(70	, 54);
		check.put(80	, 53);
		check.put(90	, 12);
		check.delete(20);
		System.out.println("\nCheck:");
		check.printHashTable();
		
		System.out.println("\nGet:");
		System.out.println( check.get( new Integer(20) ) );
		System.out.println( check.get( new Integer(90) ) );
		
		System.out.println("\nContains:");
		System.out.println( check.contains(30) );
		
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println( "\nIterable:  " );
		Iterable it= check.keys();
		Iterator t = it.iterator();
		while( t.hasNext() ){
			System.out.println( t.next() );
		}
	}//end method.
	
	public static void testSeparateChaining(){
		System.out.println("------- Separate Chaining Probing:--------------- ");
		HashTable<Integer, Integer> check = new SeparateChaining<Integer ,Integer >();
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println("\nPUT:");
		check.put( 0 , 5 );
		check.put(1	, 15);
		check.put(2	, 25);
		check.put(3	, 25);
		check.put(4	, 25);
		check.put(5	, 25);
		check.put(6	, 25);
		check.put(7	, 25);
		check.put(8	, 25);
		check.put(9	, 25);
		check.put(10	, 25);
		check.put(11	, 25);
		check.put(12	, 25);
		check.put(13	, 25);

		check.put(14	, 25);
		check.put(15	, 25);
		check.put(16	, 25);
		check.put(17	, 25);
		check.put(18	, 25);
		check.put(19	, 25);
		check.put(20	, 25);
		check.put(21	, 25);
		check.put(23	, 25);
		check.put(24	, 25);
		check.put(25	, 25);
		check.put(26	, 25);
		check.put(27	, 25);
		check.put(28	, 25);
		check.put(29	, 25);
		
		check.put(35, 35);
		check.put(12, 45);
		
		check.put(44	, 55);
//		check.delete(0);
//		check.delete(2);
//		check.delete(4);
//		check.delete(5);
		check.put(-1, 10);
		System.out.println("\nCheck:");
		check.printHashTable();
		
		System.out.println("\nGet:");
		System.out.println( check.get( new Integer(2) ) );
		System.out.println( check.get( new Integer(0) ) );
		
		System.out.println("\nContains:");
		System.out.println( check.contains(-1) );
		
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println( "\nIterable:  " );
		Iterable it= check.keys();
		Iterator t = it.iterator();
		while( t.hasNext() ){
			System.out.println( t.next() );
		}
	}//end method.
	
	public static void testBucketing(){
		System.out.println("------- Bucketing Probing:--------------- ");
		HashTable<Integer, Integer> check = new Bucketing<Integer ,Integer >();
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println("\nPUT:");
		check.put( 0 , 5 );
		check.put(1	, 15);
		check.put(2	, 25);
		
		
		check.put(3, 35);
		check.put(4, 45);
		
		check.put(5	, 55);
//		check.delete(0);
//		check.delete(2);
//		check.delete(4);
		check.delete(5);
		check.put(6	, 55);
		check.put(7	, 55);
		check.put(8	, 55);
		check.put(9	, 55);
		check.put(10	, 55);
		check.put(11	, 55);
		check.put(12	, 55);
		check.put(12	, 120);
		
		System.out.println("\nCheck:");
		check.printHashTable();
		
		System.out.println("\nGet:");
		System.out.println( check.get( new Integer(2) ) );
		System.out.println( check.get( new Integer(5) ) );
		
		System.out.println("\nContains:");
		System.out.println( check.contains(3) );
		
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println( "\nIterable:  " );
		Iterable it= check.keys();
		Iterator t = it.iterator();
		while( t.hasNext() ){
			System.out.println( t.next() );
		}
	}//end method.
	
	public static void testDoubleHashing(){
		System.out.println("------- Double Hashing Probing:--------------- ");
		HashTable<Integer, Integer> check = new DoubleHashing<Integer ,Integer >();
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println("\nPUT:");
		check.put( 55 , 1 );
		check.put(39 , 2);
		check.put(92 , 3);
		
		
		check.put(10, 1);
		check.put(110, 45);
		
		check.put(0	, 55);
		
		check.delete(0);
//		check.delete(2);
//		check.delete(4);
//		check.delete(5);
		
		System.out.println("\nCheck:");
		check.printHashTable();
		
		System.out.println("\nGet:");
		System.out.println( check.get( new Integer(0) ) );
		System.out.println( check.get( new Integer(10) ) );
		
		System.out.println("\nContains:");
		System.out.println( check.contains(92) );
		
		System.out.println("\nEmpty:");
		System.out.println( check.isEmpty() );
		
		System.out.println( "\nIterable:  " );
		Iterable it= check.keys();
		Iterator t = it.iterator();
		while( t.hasNext() ){
			System.out.println( t.next() );
		}
	}//end method.
	
	
}//end class.
