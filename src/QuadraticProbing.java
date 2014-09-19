import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class QuadraticProbing<K, V> implements HashTable<K, V> {
	private int initialSize = 8;
	private int currentSize = initialSize;
	private int numOfInsertions = 0;
	private int numOfCollisions = 0;
	private int numOfRehashing = 0;
	
	private Element[] array;
	private ArrayList<Element> temp;
	
	// a3mal constructor bel variable probingConstant wala la2?
	public QuadraticProbing(){
		array = new Element[initialSize];
		temp = new ArrayList<Element>();
	}//end constructor
	
	@Override
	public void put(K key, V value) {
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		int nextKey = home;
		int counter = 0;
		int firstFreePlace = -1;
		int beginCounter = 0;
		boolean enterTombstone = false;
		boolean inserted = false;
		
		while ( array[nextKey] != null ) {
			if (array[nextKey].getKey().equals( key ) ) {
				array[nextKey].setValue( value );
				array[nextKey].setTombstone( false );
				inserted = true;
				break;
			}
			if( array[nextKey].getTombstone() && !enterTombstone ){
				firstFreePlace = nextKey;
				enterTombstone = true;
				beginCounter = counter;
			}
			
			counter++;
			nextKey = hashFunction(home ,counter);
		}// end while.
		
		
		if( !inserted ){
			if( enterTombstone ){
				array[ firstFreePlace ] = new Element( key , value );
				numOfCollisions += ( counter - beginCounter - 1 );
			}else{
				array[ nextKey ] = new Element( key , value );
				numOfCollisions += counter;
			}
			numOfInsertions++;
		}//end if
		
		if( numOfInsertions >= ( 0.75 * currentSize ) ){
//			System.out.println( " Before: NumInsertions: " + numOfInsertions + "  currentSize: " + currentSize );
			resize();
//			System.out.println( "After : NumInsertions: " + numOfInsertions + "  currentSize: " + currentSize );
		}
		
	}// end method.

	@Override
	public V get(K key) {
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		int nextKey = home;
		int counter = 0;
		
		while ( array[nextKey] != null ) {
			if ( !array[nextKey].getTombstone() && array[nextKey].getKey().equals( key ) ) {
				return (V) array[nextKey].getValue();
			}
			counter++;
			nextKey = hashFunction(home ,counter );
		}// end while.
		
		return null;
	}//end method.

	@Override
	public void delete(K key) {
		
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		int nextKey = home;
		int counter = 0;
		
		while ( array[nextKey] != null ) {
			if ( !array[nextKey].getTombstone() && array[nextKey].getKey().equals( key ) ) {
				array[nextKey].setTombstone(true);
				numOfInsertions--;
				break;
			}
			counter++;
			nextKey = hashFunction(home ,counter );
		}// end while.
		
	}//end method.

	@Override
	public boolean contains(K key) {
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		int nextKey = home;
		int counter = 0;
		
		while ( array[nextKey] != null ) {
			if ( !array[nextKey].getTombstone() && array[nextKey].getKey().equals( key ) ) {
				return true;
			}
			counter++;
			nextKey = hashFunction(home,counter);
		}// end while.
		return false;
	}//end method

	@Override
	public boolean isEmpty() {
//		 momken a5leha order of 1.
		if( numOfInsertions == 0){
			return true;
		}
		return false;
		
	}//end method.

	@Override
	public int size() {
		// beyreturn number of insertions wala size 3ady?
		return numOfInsertions;
	}

	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<K>();
		for( int i = 0 ;i < array.length ;i++ ){
			if( array[i] != null && !array[i].getTombstone()){
				list.add( (K) array[i].getKey() );
			}
		}//end for i.
		
		return (Iterable<K>) list;
	}
	
	public void resize(){
		numOfRehashing++;
		for( int i = 0 ;i < currentSize ;i++){
			if( array[i] != null){
				temp.add(array[i]);
			}
		}//end for.
		
		currentSize = 2 * currentSize;
		numOfInsertions = 0;
//		numOfCollisions = 0;
		array = new Element[ currentSize ];
		
		for( int i = 0 ;i < temp.size() ;i++ ){
			Element e = temp.get(i);
			if( !e.getTombstone() ){
				K key = (K) e.getKey();
				V value = (V) e.getValue();
				put( key , value );
			}
			
		}//end for.
		temp = new ArrayList<Element>();
		
	}//end method.
	
	public int hashFunction( int home , int counter ){
		int x = (int) (Math.pow(counter, 2) + counter);
		int y = home + x / 2;
		return y;
	}//end method.
	
	public void printHashTable() {

		for (int i = 0; i < array.length; i++) {
			if( array[i] != null){
				System.out.println("index: " + i + " Key: " + array[i].getKey() + " Value: " + array[i].getValue()  +" T: " + array[i].getTombstone() );
			}else{
				System.out.println("NULL!!");
			}
		}// end for.

	}// end method.

	public int getMemoryUsed(){
		return currentSize;
	}
	
	public int getCollisions(){
		return numOfCollisions;
	}
	
	public int getRehashing(){
		return numOfRehashing;
	}
}// end class
