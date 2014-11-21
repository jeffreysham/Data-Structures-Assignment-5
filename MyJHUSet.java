/**
 * MyJHUSet is an implementation of JHUSet.
 * @author Jeffrey Sham, JHED ID: jsham2
 * @param <T>
 *
 */
public class MyJHUSet<T> implements JHUSet<T> {

    /** Set Start Size to 10. 
     */
    private static final int START_SIZE = 10;
    
    /** Array size counter.
     */
    private int size;
    
    /** Generic Array.
     */
    private T[] array;
    
    /** Constructor of MyJHUSet.   
     */
    public MyJHUSet() {
        this.size = 0;
        this.array = (T[]) new Object[START_SIZE];
    }
    
    /** Find out how many elements are in the set.
        @return the number
     */
    @Override
    public int size() {
        return this.size;
    }

    /** See if the set is empty.
        @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /** Add an item to the set, no duplicates allowed.
        @param o the item to add
        @return true if added, false otherwise (was duplicate)
     */
    @Override
    public boolean add(T o) {
        if (this.contains(o)) {
            return false;
        }
        
        if (this.size >= this.array.length) {
            T[] temp = (T[]) new Object[this.array.length * 2];
 
            System.arraycopy(this.array, 0, temp, 0, this.size);
             
            this.array = temp;
        }
     
        //Add the object into the array
        this.array[this.size++] = (T) o;

        return true;
    }

    /** Remove an item from the set if it's there.
        @param o the item to remove
        @return true if removed, false if not found
     */
    @Override
    public boolean remove(Object o) {
        if (this.contains(o)) {
            int indexOfObject = 0;
            for (int i = 0; i < this.size; i++) {
                if (this.array[i].equals(o)) {
                    indexOfObject = i;
                    break;
                }
            }
            
            if (indexOfObject == this.size - 1) {
                this.array[indexOfObject] = null;
            } else {
                this.array[indexOfObject] = this.array[this.size - 1];
                this.array[this.size - 1] = null;
            }

            this.size--;
            return true;
        }
        
        return false;
    }

    
    /** Search for an item in the set.
        @param o the item to search for
        @return true if found, false otherwise
     */
    @Override
    public boolean contains(Object o) {
        
        for (int i = 0; i < this.size; i++) {
            if (this.array[i].equals(o)) {
                return true;
            }
        }
        
        return false;
    }

    /** Create the union of two sets, no duplicates.
        @param that the other set to union with this
        @return a new set which contains all the elements in this and that
     */
    @Override
    public JHUSet<T> union(JHUSet<T> that) {
        int setSize = that.size();
        MyJHUSet<T> newSet = new MyJHUSet<T>();
        MyJHUSet<T> tempSet;
        
        if (that instanceof MyJHUSet) {
            tempSet = (MyJHUSet<T>) that;
            
            for (int i = 0; i < this.size; i++) {
                newSet.add(this.array[i]);
            }
            
            for (int i = 0; i < setSize; i++) {
                newSet.add(tempSet.array[i]);
            }
            
            return newSet;
            
        } else {
            System.out.println("Sorry, the set entered was not a MyJHUSet.");
            return null;
        }
        
    }

    /** Create the intersection of two sets, which is every item that
        appears in both sets.  For example, if this is {2, 3, 1, 4} and
        that is {3, 10, 2} then the intersection is {2, 3}.
        @param that the other set to union with this
        @return a new set which is (this intersect that)
     */
    @Override
    public JHUSet<T> intersect(JHUSet<T> that) {
        
        int setSize = that.size();
        MyJHUSet<T> newSet = new MyJHUSet<T>();
        MyJHUSet<T> tempSet;
        
        if (that instanceof MyJHUSet) {
            tempSet = (MyJHUSet<T>) that;
            
            if (this.size > setSize) {
                for (int i = 0; i < setSize; i++) {
                    if (this.contains(tempSet.array[i])) {
                        newSet.add(tempSet.array[i]);
                    }
                }
            } else {
                for (int i = 0; i < this.size; i++) {
                    if (tempSet.contains(this.array[i])) {
                        newSet.add(this.array[i]);
                    }
                }
            }
            
            return newSet;
        } else {
            System.out.println("Sorry, the set entered was not a MyJHUSet.");
            return null;
        }
    }

    /** This method is inherited from Object, and should be overridden to
        return the set contents in classic format such as "{1, 5, 2, 6}".
        @return the set contents
     */
    @Override
    public String toString() {       
        String setContent = "{";
        
        for (int i = 0; i < this.size; i++) {
            if (i == this.size - 1) {
                setContent += this.array[i];
            } else {
                setContent += this.array[i] + ", ";
            }
        }
  
        setContent += "}";
        
        return setContent;
    }

    /**
     * This method retrieves the object at the given 
     * index of the array.
     * @param index The index of the object.
     * @return The object at the index.
     */
    public T get(int index) {
        if (index < this.size && index >= 0) {
            return this.array[index];
        }
        return null;
    }
    
}
