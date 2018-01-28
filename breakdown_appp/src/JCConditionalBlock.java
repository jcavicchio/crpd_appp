import java.util.ArrayList;
import java.util.Iterator;

/**
 * JCConditionalBlock is a fundamental class whose purpose is to contain
 * a conditional block containing of conditional section objects contained in a
 * control flow graph for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCConditionalBlock extends JCSubBlock 
{
    private static int _nextID = 0;

	private static int _cbCount = 0;                         // Counter to count which conditional it is
	private static ArrayList<JCConditionalBlock> _conditionalBlocks = 
			          new ArrayList<JCConditionalBlock>();   // Conditional blocks in the graph

	private int _conditionalBlockID;
    private ArrayList<Integer> _conditionalSections;

	/**
	 * Default constructor.
	 */
    JCConditionalBlock()
	{
    	super();
    	_conditionalBlockID = _nextID;
		_nextID++;
		_cbCount = _nextID;
		_conditionalSections = new ArrayList<Integer>();
		setConditionalBlock(_conditionalBlockID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCConditionalBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_cbCount = 0;
		_conditionalBlocks = new ArrayList<JCConditionalBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the conditional block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return conditionalBlockID   the identifier of this conditional block object
	 * 
	 * @see                         JCConditionalBlock
	 */
	public int getConditionalBlockID()
	{
		return _conditionalBlockID;
	}


	/**
	 * Returns the string name of the conditional block object. 
	 *
	 * @return ID     the string name of this block object
	 * 
	 * @see           JCConditionalBlock
	 */
	public String getConditionalBlockName()
	{
		String id = "JCConditionalBlock" + _conditionalBlockID;
		
		return id;
	}

	/**
	 * Stores the conditional block object at the specified location in
	 * the conditional block array. 
	 *
	 * @param  conditionalBlockID    the identifier of the stored conditional block object
	 * 
	 * @param  conditionalBlock      the conditional block object to be stored
	 * 
	 * @see                          JCConditionalBlock
	 */
	public static void setConditionalBlock(int conditionalBlockID, JCConditionalBlock conditionalBlock)
	{
		if (conditionalBlockID < _cbCount)
		{
			_conditionalBlocks.add(conditionalBlock);
		}
	}
	
	/**
	 * Returns the conditional block object associated with the specified identifier. 
	 *
	 * @param  conditionalBlockID    the identifier of the stored conditional block object
	 * 
	 * @return conditionalBlock      the conditional block object stored
	 * 
	 * @see                          JCConditionalBlock
	 */
	public static JCConditionalBlock getConditionalBlock(int conditionalBlockID)
	{
		if (conditionalBlockID < _cbCount)
		{
	        return _conditionalBlocks.get(conditionalBlockID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Adds the conditional section object to this conditional block object. 
	 *
	 * @param  conditionalSection  the conditional section object to add to this
	 *                             conditional block object
	 *               
	 * @see                        JCConditionalBlock
	 * @see                        JCConditionalSection
	 */
	public void addConditionalSection(JCConditionalSection conditionalSection)
	{
		if (_conditionalSections.add(conditionalSection.getConditionalSectionID()) != true)
		{
			System.out.println("JCConditionalBlock: Error adding conditional section " + conditionalSection.getConditionalSectionID());
		}
	}
	
	/**
	 * Removes the conditional section object from this conditional block object. 
	 *
	 * @param  conditionalSection  the conditional section object to add to this
	 *                             conditional block object
	 *               
	 * @see                        JCConditionalBlock
	 * @see                        JCConditionalSection
	 */
	public void removeConditionalSection(JCConditionalSection conditionalSection)
	{
		if (_conditionalSections.remove((Integer)conditionalSection.getConditionalSectionID()) != true)
		{
			System.out.println("JCConditionalBlock: Error removing sub-block " + conditionalSection.getConditionalSectionID());
		}
	}
	
	/**
	 * Finds the conditional section object from within this conditional block object
	 * with the specified name. 
	 *
	 * @param  conditionalSectionName    the string name of the conditional section object to search for
	 *               
	 * @return subBlock        the found conditional section object in this conditional block
	 *                         object
	 *               
	 * @see                    JCConditionalBlock
	 * @see                    JCConditionalSection
	 */
	public JCConditionalSection findConditionalSection(String conditionalSectionName)
	{
	    JCConditionalSection conditionalSection = null;
		JCConditionalSection currentConditionalSection;
		Integer currentConditionalSectionID;
	    String currentConditionalSectionName;
	    Iterator<Integer> iterator;
	    
	    if (conditionalSectionName != null)
	    {
		    iterator = getConditionalSectionIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (conditionalSectionName == null))
		        {
		        	currentConditionalSectionID = iterator.next();
		        	currentConditionalSection = JCConditionalSection.getConditionalSection(currentConditionalSectionID);
		        	currentConditionalSectionName = currentConditionalSection.getConditionalSectionName();
		        	if (currentConditionalSection != null)
		        	{
		        		if (currentConditionalSectionName.compareTo(conditionalSectionName) == 0)
		        		{
		        			conditionalSection = currentConditionalSection;
		        		}
		        	}
		        }
		    }
	    }
	    return conditionalSection;
	}
	
	/**
	 * Returns the conditional section object at the specified index in this 
	 * conditional block object.
	 *
	 * @param  csIndex              the index of the conditional section object to return
	 *               
	 * @return conditionalSection   the found conditional section object in this 
	 *                              conditional block object
	 *               
	 * @see                         JCConditionalBlock
	 * @see                         JCConditionalSection
	 */
	public JCConditionalSection getConditionalSectionAtIndex(int csIndex)
	{
		JCConditionalSection conditionalSection;
		int cSectionID;
		
		cSectionID = _conditionalSections.get(csIndex);
		conditionalSection = JCConditionalSection.getConditionalSection(cSectionID);
		
		return conditionalSection;
	}
	
	/**
	 * Returns the number of conditional section objects in this conditional block 
	 * object. 
	 *
	 * @return numberOfConditionalSections  the number of conditional section contained in this 
	 *                                      conditional block object
	 *                        
	 * @see                                 JCConditionalBlock
	 * @see                                 JCConditionalSection
	 */
	public long numberOfConditionalSections()
	{
		return _conditionalSections.size();
	}
	
	/**
	 * Returns the conditional section iterator for accessing the conditional section objects 
	 * in this conditional block object. 
	 *
	 * @return iterator  the conditional section iterator for accessing the 
	 *                   conditional section objects
	 *                        
	 * @see              JCConditionalBlock
	 * @see              JCConditionalSection
	 */
	public Iterator<Integer> getConditionalSectionIterator()
	{
		return _conditionalSections.iterator();
	}
	
	/**
	 * Updates the predecessor basic block relationships from this conditional block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block predecessor
	 *                   contained within
	 *               
	 * @see              JCConditionalBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updatePredecessorBasicBlocks(JCSubBlock subBlock)
	{
		Iterator<Integer> conditionalSectionIterator;
		JCConditionalSection currentConditionalSection;
		Integer currentConditionalSectionID;
		
		if (numberOfConditionalSections() > 0)
		{
			conditionalSectionIterator = getConditionalSectionIterator();
		    if (conditionalSectionIterator != null)
		    {
		        while (true == conditionalSectionIterator.hasNext())
		        {
		        	currentConditionalSectionID = conditionalSectionIterator.next();
		        	currentConditionalSection = JCConditionalSection.getConditionalSection(currentConditionalSectionID);
		        	currentConditionalSection.updatePredecessorBasicBlocks(subBlock);
		        }
		    }
		}
	}
	
	/**
	 * Updates the successor basic block relationships from this conditional block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block successors
	 *                   contained within
	 *               
	 * @see              JCConditionalBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updateSuccessorBasicBlocks(JCSubBlock subBlock)
	{
		Iterator<Integer> conditionalSectionIterator;
		JCConditionalSection currentConditionalSection;
		Integer currentConditionalSectionID;
		
		if (numberOfConditionalSections() > 0)
		{
			conditionalSectionIterator = getConditionalSectionIterator();
		    if (conditionalSectionIterator != null)
		    {
		        while (true == conditionalSectionIterator.hasNext())
		        {
		        	currentConditionalSectionID = conditionalSectionIterator.next();
		        	currentConditionalSection = JCConditionalSection.getConditionalSection(currentConditionalSectionID);
		        	currentConditionalSection.updateSuccessorBasicBlocks(subBlock);
		        }
		    }
		}
	}
	
	/**
	 * Updates the predecessor section block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the section block predecessor
	 *                   contained within
	 *               
	 * @see              JCSectionBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updatePredecessorSectionBlocks(JCSubBlock subBlock)
	{
		Iterator<Integer> conditionalSectionIterator;
		JCConditionalSection currentConditionalSection;
		Integer currentConditionalSectionID;
		
		if (numberOfConditionalSections() > 0)
		{
			conditionalSectionIterator = getConditionalSectionIterator();
		    if (conditionalSectionIterator != null)
		    {
		        while (true == conditionalSectionIterator.hasNext())
		        {
		        	currentConditionalSectionID = conditionalSectionIterator.next();
		        	currentConditionalSection = JCConditionalSection.getConditionalSection(currentConditionalSectionID);
		        	currentConditionalSection.updatePredecessorSectionBlocks(subBlock);
		        }
		    }
		}
	}
	
	/**
	 * Updates the successor section block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the section block successors
	 *                   contained within
	 *               
	 * @see              JCSectionBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updateSuccessorSectionBlocks(JCSubBlock subBlock)
	{
		Iterator<Integer> conditionalSectionIterator;
		JCConditionalSection currentConditionalSection;
		Integer currentConditionalSectionID;
		
		if (numberOfConditionalSections() > 0)
		{
			conditionalSectionIterator = getConditionalSectionIterator();
		    if (conditionalSectionIterator != null)
		    {
		        while (true == conditionalSectionIterator.hasNext())
		        {
		        	currentConditionalSectionID = conditionalSectionIterator.next();
		        	currentConditionalSection = JCConditionalSection.getConditionalSection(currentConditionalSectionID);
		        	currentConditionalSection.updateSuccessorSectionBlocks(subBlock);
		        }
		    }
		}
	}
	
	/**
	 * Returns the string conditional block object type name. 
	 *
	 * @return ID     the string type name of the conditional block object
	 * 
	 * @see           JCConditionalBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCConditionalBlock";
	}
    
	/**
	 * Displays information about this conditional block object. 
	 *
	 * @see           JCConditionalBlock
	 */
    @Override
    public void displayObjectInformation()
    {
    	JCConditionalSection         conditionalSection;
       	Integer                      conditionalSectionID;
    	Iterator<Integer>            iterator;

       	System.out.println("Conditional Block ID " + getConditionalBlockID() + " Name " + getConditionalBlockName() + " sub-block ID " + getSubBlockID() + " sub-block Name " + getSubBlockName());
	
	    iterator = this.getConditionalSectionIterator();
    	System.out.print("    Conditional Sections: ");
	    while (iterator.hasNext() == true)
	    {
	    	conditionalSectionID = iterator.next();
	    	conditionalSection = JCConditionalSection.getConditionalSection(conditionalSectionID);
           	System.out.print("(" + conditionalSection.getConditionalSectionID() + "," + conditionalSection.getConditionalSectionName() + "," + conditionalSection.getBlockID() + "," + conditionalSection.getBlockName() + "," + conditionalSection.getSubBlockID() + "," + conditionalSection.getSubBlockName() + ")");
    	}
    	System.out.println();

    	super.displayObjectInformation();
    }

	/**
	 * Displays information about all conditional block objects. 
	 *
	 * @see           JCConditionalBlock
	 */
    public static void displayAllObjects()
    {
	    int                  conditionalBlockID;
	    JCConditionalBlock   currentConditionalBlock;
		
    	System.out.println("The number of conditional blocks is " + _conditionalBlocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (conditionalBlockID = 0; conditionalBlockID < _conditionalBlocks.size(); conditionalBlockID++)
		{
			currentConditionalBlock = JCConditionalBlock.getConditionalBlock(conditionalBlockID);
			currentConditionalBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
