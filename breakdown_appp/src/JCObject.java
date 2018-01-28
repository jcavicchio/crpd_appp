
/**
 * JCObject is an abstract class whose purpose is to define required
 * functions to be implemented by all objects.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public abstract class JCObject 
{
	/**
	 * Returns the string based object type name. 
	 *
	 * @return ID     the string type name of the object
	 * 
	 * @see           JCObject
	 */
    public abstract String getObjectTypeName();
    
	/**
	 * Displays information about this block object. 
	 *
	 * @see           JCObject
	 */
    public abstract void displayObjectInformation();

}
