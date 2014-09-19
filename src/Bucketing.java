import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Bucketing<K, V> implements HashTable<K, V> {
	private int initialSize = 10;
	private int currentSize = initialSize;
	private int numOfInsertions = 0;
	private long numOfCollisions = 0;
	private int numOfRehashing = 0;
	private int bucketSize;
	private int numBuckets;
	
	
	private Element[] array;
	private ArrayList<Element> temp;
	private Element[] overflowArray;
	
	public Bucketing(){
		bucketSize = 5;
		numBuckets = initialSize / bucketSize;
		
		array = new Element[initialSize];
		overflowArray = new Element[ 2 * bucketSize ];
		temp = new ArrayList<Element>();
	}//end constructor
	
	@Override
	public void put(K key, V value) {
		int home = (key.hashCode() % numBuckets) * bucketSize;
		home = Math.abs(home);
		int counter = 0;
		int firstFreePlace = -1;
		int beginCounter = 0;
		boolean enterTombstone = false;
		boolean inserted = false;
		
		for( int i = 0 ; i < bucketSize ;i++){
			int pos = home + i;
			if( array[pos] == null ){
				inserted = true;
				array[pos] = new Element( key ,value );
				numOfInsertions++;
				numOfCollisions += i;
				break;
			}//end if.
			
			if( array[pos].getTombstone() && !enterTombstone ){
				enterTombstone = true;
				beginCounter = i;
				firstFreePlace = pos;
			}//end if.
			
			if( array[pos].getKey().equals( key )){
				inserted = true;
				array[pos].setValue( value );
				array[pos].setTombstone(false);
				break;
			}
		}//end for i.
		
		
		if( !inserted ){
			// search fel overFlow;
			int index = -1;
			enterTombstone = false;
			boolean duplicate = false;
			for( int i = 0 ;i < overflowArray.length ;i++){
				if( overflowArray[i] == null ){
					index = i;
					break;
				}
				if( overflowArray[i].getTombstone() && !enterTombstone ){
					enterTombstone = true;
					index = i;
				}//end if.
				if( overflowArray[i].equals( key ) ){
					overflowArray[i].setValue( value );
					overflowArray[i].setTombstone(false);
					duplicate = true;
					break;
				}
			}//end for.
			if( !duplicate ){
				
				if( firstFreePlace != -1 ){
					array[ firstFreePlace ] = new Element( key , value );
					numOfCollisions += beginCounter;
					numOfInsertions++;
				}else if( index != -1 ){
					overflowArray[ index ] = new Element( key , value );
					numOfCollisions += ( bucketSize + index );
					numOfInsertions++;
				}else{
					System.out.println("ERROR!!");
				}
				
			}//end if.
			
		}//end if
		
		if( numOfInsertions >= ( 0.75 * (currentSize ) ) ){
//			System.out.println( " Before: NumInsertions: " + numOfInsertions + "  currentSize: " + currentSize );
			resize();
//			System.out.println( "After : NumInsertions: " + numOfInsertions + "  currentSize: " + currentSize );
		}
		
	}// end method.

	@Override
	public V get(K key) {
		int home = (key.hashCode() % numBuckets) * bucketSize;
		home = Math.abs(home);
		
		for( int i = 0 ;i < bucketSize ;i++){
			int pos = home + i;
			if(array[pos] == null){
				return null;
			}
			if( !array[pos].getTombstone() && array[pos].getKey().equals( key ) ){
				return (V) array[pos].getValue();
			}
		}//end for i.
		
		for( int i = 0 ; i < overflowArray.length ;i++){
			if(overflowArray[i] == null){
				return null;
			}
			if( !overflowArray[i].getTombstone() && overflowArray[i].getKey().equals( key ) ){
				return (V) overflowArray[i].getValue();
			}
		}//end for.
		
		return null;
	}//end method.

	@Override
	public void delete(K key) {
		
		int home = (key.hashCode() % numBuckets) * bucketSize;
		home = Math.abs(home);
		boolean found = false;
		
		for( int i = 0 ;i < bucketSize ;i++){
			int pos = home + i;
			if(array[pos] == null){
				found = true;
				break;
			}
			if( !array[pos].getTombstone() && array[pos].getKey().equals( key ) ){
				array[pos].setTombstone(true);
				found = true;
			}
		}//end for i.
		if( !found ){
			
			for( int i = 0 ; i < overflowArray.length ;i++){
				if(overflowArray[i] == null){
					break;
				}
				if( !overflowArray[i].getTombstone() && overflowArray[i].getKey().equals( key ) ){
					overflowArray[i].setTombstone(true);
				}
			}//end for.
			
		}//end if.
		
		
		
	}//end method.

	@Override
	public boolean contains(K key) {
		int home = (key.hashCode() % numBuckets) * bucketSize;
		home = Math.abs(home);
		
		for( int i = 0 ;i < bucketSize ;i++){
			int pos = home + i;
			if(array[pos] == null){
				return false;
			}
			if( !array[pos].getTombstone() && array[pos].getKey().equals( key ) ){
				return true;
			}
		}//end for i.
	
		for( int i = 0 ; i < overflowArray.length ;i++){
			if(overflowArray[i] == null){
				return false;
			}
			if( !overflowArray[i].getTombstone() && overflowArray[i].getKey().equals( key ) ){
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
		
		for( int i = 0 ;i < overflowArray.length ;i++ ){
			if( overflowArray[i] != null && !overflowArray[i].getTombstone()){
				list.add( (K) overflowArray[i].getKey() );
			}
		}//end for i.
		
		return (Iterable<K>) list;
	}
	
	public void resize(){
		numOfRehashing++;
		for( int i = 0 ; i < array.length ;i++){
			if( array[i] != null){
				temp.add( array[i] );
			}
		}//end for.
		
		for( int i = 0 ; i < overflowArray.length ;i++){
			if( overflowArray[i] != null){
				temp.add( overflowArray[i] );
			}
		}//end for.
		
		currentSize = 2 * currentSize;
		numOfInsertions = 0;
//		numOfCollisions = 0;
		numBuckets = currentSize / bucketSize;
		array = new Element[ currentSize ];
		int overflowSize = overflowArray.length;
		overflowArray = new Element[ 2*overflowSize ];
		
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
		for (int i = 0; i < array.length; i++) {
			if( array[i] != null){
				System.out.println("index: " + i + " Key: " + array[i].getKey() + " Value: " + array[i].getValue()  +" T: " + array[i].getTombstone() );
			}else{
				System.out.println("NULL!!");
			}
		}// end for.

	}// end method.
	
	public int getMemoryUsed(){
		int size = currentSize + overflowArray.length;
		return size;
	}
	
	public long getCollisions(){
		return numOfCollisions;
	}
	
	public int getRehashing(){
		return numOfRehashing;
	}
	
}// end class
