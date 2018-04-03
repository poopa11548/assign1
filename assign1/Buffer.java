package assign1;

import java.util.Iterator;

public interface Buffer {
	
	void add(boolean b);
	
	void add(Iterator<Boolean> iterator);
	
	Iterator<Boolean> iterator();
	
	byte[] toByteArray();
}
