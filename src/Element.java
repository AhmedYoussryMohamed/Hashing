
public class Element<K, V> {
	
	private K key;
	private V value;
	private boolean tombstone;
	
	public Element( K key , V value ){
		this.key = key;
		this.value = value;
	}//end constructor.
	
	public void setKey( K key ){
		this.key = key;
	}
	
	public void setValue( V value ){
		this.value = value;
	}
	
	public void setTombstone(boolean b){
		tombstone = b;
	}
	
	public K getKey(){
		return key;
	}
	
	public V getValue(){
		return value;
	}
	
	public boolean getTombstone(){
		return tombstone;
	}
	
}//end class.
