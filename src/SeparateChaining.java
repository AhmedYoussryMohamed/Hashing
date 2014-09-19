import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.omg.CORBA.Current;

public class SeparateChaining<K, V> implements HashTable<K, V> {
	
	private int initialSize = 10;
	private int currentSize = initialSize;
	private int numOfInsertions = 0;
	private int numOfCollisions = 0;
	private int numOfRehashing = 0;
	
	private LinkedList<Element>[] array;
	private ArrayList<Element> temp;
	
	// a3mal constructor bel variable probingConstant wala la2?
	public SeparateChaining(){
		array = new LinkedList[initialSize];
		for(int i=0; i < initialSize; i++) array[i] = new LinkedList<Element>();
		temp = new ArrayList<Element>();
	}//end constructor
	
	@Override
	public void put(K key, V value) {
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		boolean inserted = false;
		
		for( int i = 0 ;i < array[home].size() ;i++){
			Element e = array[home].get(i);
			if( e.getKey().equals(key) ){
				inserted = true;
				e.setValue(value);
				break;
			}//end if.
		
		}//end for i.
		
		if( !inserted ){
			numOfCollisions += array[home].size();
			array[ home ].add( new Element( key , value ) ) ;
			numOfInsertions++;
		}//end if
		
		if( numOfInsertions >= ( 3 * currentSize ) ){
//			System.out.println( " Before: NumInsertions: " + numOfInsertions + "  currentSize: " + currentSize );
			resize();
//			System.out.println( "After : NumInsertions: " + numOfInsertions + "  currentSize: " + currentSize );
		}
		
	}// end method.

	@Override
	public V get(K key) {
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		
		for( int i = 0 ; i < array[home].size() ;i++){
			Element e = array[home].get( i );
			if( e.getKey().equals(key) ){
				return (V) e.getValue();
			}
		}//end for.
		
		return null;
	}//end method.

	@Override
	public void delete(K key) {
		
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		
		for( int i = 0 ;i < array[home].size() ;i++ ){
			Element e = array[home].get( i );
			if( e.getKey().equals(key) ){
				numOfInsertions--;
				array[home].remove(i);
			}
		}//end for.
		
		
	}//end method.

	@Override
	public boolean contains(K key) {
		int home = key.hashCode() % currentSize;
		home = Math.abs(home);
		for( int i = 0 ;i < array[home].size() ;i++){
			Element e = array[home].get(i);
			if( e.getKey().equals( key ) ){
				return true;
			}
		}//end for.
		
		return false;
	}//end method

	@Override
	public boolean isEmpty() {
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
			for( int j = 0 ; j < array[i].size() ;j++){
				Element e = array[i].get(j);
				list.add( (K) e.getKey() );
			}//end for j.
		}//end for i.
		
		return (Iterable<K>) list;
	}
	
	public void resize(){
		numOfRehashing++;
		for( int i = 0 ;i < currentSize ;i++){
			for( int j = 0; j < array[i].size() ;j++){
				temp.add( array[i].get(j) );
			}
		}//end for.
		
		currentSize = 2 * currentSize;
		numOfInsertions = 0;
//		numOfCollisions = 0;
		array = new LinkedList[currentSize];
		for(int i=0; i < currentSize; i++) array[i] = new LinkedList<Element>();
		
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
	
	
	public void printHashTable() {
		System.out.println(array.length);
		for (int i = 0; i < array.length; i++) {
			for( int j = 0; j < array[i].size() ;j++){
				Element e = array[i].get(j);
				System.out.println("Home: " + i + " Key: " + e.getKey() + " Value: " + e.getValue()  );
			
			}
		}// end for.

	}// end method.
	
	public int getMemoryUsed(){
		int size = currentSize;
		for( int i = 0 ; i < currentSize ;i++ ){
			size += array[i].size();
		}
		return size;
	}//end method.
	
	public int getCollisions(){
		return numOfCollisions;
	}
	
	public int getRehashing(){
		return numOfRehashing;
	}
	
}// end class
