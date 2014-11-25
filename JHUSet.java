/** Set interface that represents an unordered, unbounded 
    container of unique objects.
    @param <T> the base type of the objects
*/

public interface JHUSet<T>  {

   /** Find out how many elements are in the set.
       @return the number
   */
    int size();

   /** See if the set is empty.
       @return true if empty, false otherwise
   */
    boolean isEmpty();

   /** Add an item to the set, no duplicates allowed.
       @param o the item to add
       @return true if added, false otherwise (was duplicate)
   */
    boolean add(T o);

   /** Remove an item from the set if it's there.
       @param o the item to remove
       @return true if removed, false if not found
   */
    boolean remove(Object o);

   /** Search for an item in the set.
       @param o the item to search for
       @return true if found, false otherwise
   */
    boolean contains(Object o);

   /** Create the union of two sets, no duplicates.
       @param that the other set to union with this
       @return a new set which contains all the elements in this and that
   */
    JHUSet<T> union(JHUSet<T> that);

   /** Create the intersection of two sets, which is every item that
       appears in both sets.  For example, if this is {2, 3, 1, 4} and
       that is {3, 10, 2} then the intersection is {2, 3}.
       @param that the other set to union with this
       @return a new set which is (this intersect that)
   */
    JHUSet<T> intersect(JHUSet<T> that);

   /* NOTE: union and intersection are written to create a new set so
    * that the user has the option to leave this set unmodified, or to
    * change it by assigning the new set returned to the old variable.
    */

   /** This method is inherited from Object, and should be overridden to
       return the set contents in classic format such as "{1, 5, 2, 6}".
       @return the set contents
   */
    String toString();

}
