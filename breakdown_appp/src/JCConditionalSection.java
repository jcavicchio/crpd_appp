import java.util.ArrayList;
import java.util.Iterator;

/**
 * JCConditionalSection is a fundamental class whose purpose is to represent a 
 * single optional path within a conditional block containing other conditional 
 * section objects contained in a control flow graph for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCConditionalSection extends JCBlock 
{
    private static int _nextID = 0;

	private static int _csCount = 0;                           // Counter to count which conditional section it is
	private static ArrayList<JCConditionalSection> _conditionalSections = 
			          new ArrayList<JCConditionalSection>();  // Conditional blocks in the graph

	private int _conditionalSectionID;

	/**
	 * Default constructor.
	 */
    JCConditionalSection()
	{
    	super();
    	_conditionalSectionID = _nextID;
		_nextID++;
		_csCount = _nextID;
		setConditionalSection(_conditionalSectionID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCConditionalSection
	 */
	public static void reset()
	{
		_nextID = 0;
		_csCount = 0;
		_conditionalSections = new ArrayList<JCConditionalSection>();
	}
	
	/**
	 * Returns the numeric identifier of the conditional section object that 
	 * can be used to store associations between objects. 
	 *
	 * @return conditionalSectionID   the identifier of this conditional section object
	 * 
	 * @see                           JCConditionalSection
	 */
	public int getConditionalSectionID()
	{
		return _conditionalSectionID;
	}

	/**
	 * Returns the string name of the conditional section object. 
	 *
	 * @return ID     the string name of this conditional section object
	 * 
	 * @see           JCConditionalSection
	 */
	public String getConditionalSectionName()
	{
		String id = "JCConditionalSection" + _conditionalSectionID;
		
		return id;
	}
	
   /**
	 * Stores the conditional section object at the specified location in
	 * the conditional section array. 
	 *
	 * @param  conditionalSectionID         the identifier of the stored conditional section object
	 * 
	 * @param  conditionalSectionBlock      the conditional block object to be stored
	 * 
	 * @see                                 JCConditionalSection
	 */
	public static void setConditionalSection(int conditionalSectionID, JCConditionalSection conditionalSectionBlock)
	{
		if (conditionalSectionID < _csCount)
		{
			_conditionalSections.add(conditionalSectionBlock);
		}
	}
	
	/**
	 * Returns the conditional section object associated with the specified identifier. 
	 *
	 * @param  conditionalSectionID    the identifier of the stored conditional section object
	 * 
	 * @return conditionalBlock        the conditional section object stored
	 * 
	 * @see                            JCConditionalSection
	 */
	public static JCConditionalSection getConditionalSection(int conditionalSectionID)
	{
		if (conditionalSectionID < _csCount)
		{
	        return _conditionalSections.get(conditionalSectionID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns the string conditional block object type name. 
	 *
	 * @return ID     the string type name of the section block object
	 * 
	 * @see           JCConditionalSection
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCConditionalSection";
	}
    
	/**
	 * Displays information about this conditional section object. 
	 *
	 * @see           JCConditionalSection
	 */
    @Override
    public void displayObjectInformation()
    {
       	System.out.println("Conditional Section ID " + getConditionalSectionID() + " Name " + getConditionalSectionName() + " block ID " + getBlockID() + " block name " + getBlockName() + " sub-block ID " + getSubBlockID() + " sub-block Name " + getSubBlockName());

    	super.displayObjectInformation();   	
    }

	/**
	 * Displays information about all conditional section objects. 
	 *
	 * @see           JCConditionalSection
	 */
    public static void displayAllObjects()
    {
	    int                  conditionalSectionID;
    	JCConditionalSection currentConditionalSection;
		
    	System.out.println("The number of conditional sections is " + _conditionalSections.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (conditionalSectionID = 0; conditionalSectionID < _conditionalSections.size(); conditionalSectionID++)
		{
			currentConditionalSection = JCConditionalSection.getConditionalSection(conditionalSectionID);
			currentConditionalSection.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
