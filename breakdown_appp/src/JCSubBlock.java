import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * JCSubBlock is a fundamental class whose purpose is to provide a super
 * class representation used for conditional sections, linear sections,
 * loops, or blocks.  These sub-blocks form the structure of the control flow graph 
 * basic block objects for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public abstract class JCSubBlock extends JCObject
{
    private static int _nextID = 0;

	private static int _subblockCount = 0;                   // Counter to count which sub-block object it is
	private static ArrayList<JCSubBlock> _subblocks = 
			          new ArrayList<JCSubBlock>();           // Sub-block structure of the graph

	private int _subblockID;
    private ArrayList<Integer> _predecessorBlocks;
    private ArrayList<Integer> _successorBlocks;
	private int _startingSectionID;
	private int _endingSectionID;
	private int _parentBlockID;
    
	private ArrayList<JCCostSolution> _solutionMap;
    
	/**
	 * Default constructor.
	 */
	JCSubBlock()
	{
		_subblockID = _nextID;
		_nextID++;
		_subblockCount = _nextID;
		_predecessorBlocks = new ArrayList<Integer>();
		_successorBlocks = new ArrayList<Integer>();
		_startingSectionID = -1;
		_endingSectionID = -1;
		_parentBlockID = -1;
		_solutionMap = new ArrayList<JCCostSolution>();
		setSubBlock(_subblockID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCSubBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_subblockCount = 0;
		_subblocks = new ArrayList<JCSubBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the sub-block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _subblockID   the identifier of this sub-block object
	 * 
	 * @see                  JCSubBlock
	 */
	public int getSubBlockID()
	{
		return _subblockID;
	}

	/**
	 * Returns the string name of the sub-block object. 
	 *
	 * @return _ID     the string name of this sub-block object
	 * 
	 * @see            JCSubBlock
	 */
	public String getSubBlockName()
	{
		String id = "JCSubBlock" + _subblockID;
		
		return id;
	}
	
	/**
	 * Stores the sub-block object at the specified location in
	 * the sub-block array. 
	 *
	 * @param  subblockID    the identifier of the stored sub-block object
	 * 
	 * @param  subblockObj   the sub-block object to be stored
	 * 
	 * @see                   JCSubBlock
	 */
	public static void setSubBlock(int subblockID, JCSubBlock subblockObj)
	{
		if (subblockID < _subblockCount)
		{
			_subblocks.add(subblockObj);
		}
	}
	
	/**
	 * Returns the sub-block object associated with the specified identifier. 
	 *
	 * @param  subblockID    the identifier of the stored sub-block object
	 * 
	 * @return subblockObj   the sub-block object stored
	 * 
	 * @see                  JCSubBlock
	 */
	public static JCSubBlock getSubBlock(int subblockID)
	{
		if (subblockID < _subblockCount)
		{
	        return _subblocks.get(subblockID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Adds the sub-block object as a predecessor of this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this sub-block
	 *                   object
	 *               
	 * @see          JCSubBlock
	 */
	public void addPredecessorSubBlock(JCSubBlock subBlock)
	{
		if (_predecessorBlocks.add(subBlock.getSubBlockID()) != true)
		{
			System.out.println("JCSubBlock: Error adding predecessor sub-block " + subBlock.getSubBlockName());
		}
		updatePredecessorBasicBlocks(subBlock);
	}
	
	/**
	 * Removes the predecessor sub-block object from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this sub-block
	 *                   object
	 *               
	 * @see              JCSubBlock
	 */
	public void removePredecessorSubBlock(JCSubBlock subBlock)
	{
		if (_predecessorBlocks.remove((Integer)subBlock.getSubBlockID()) != true)
		{
			System.out.println("JCSubBlock: Error removing predecessor sub-block " + subBlock.getSubBlockName());
		}
	}
	
	/**
	 * Finds the predecessor sub-block object from within this sub-block object
	 * with the specified sub-block name. 
	 *
	 * @param  subBlockName    the string name of the predecessor sub-block object 
	 *                         to search for
	 *               
	 * @return subBlock        the found predecessor sub-block object in this sub-block
	 *                         object
	 *               
	 * @see                    JCSubBlock
	 */
	public JCSubBlock findPredecessorSubBlock(String subBlockName)
	{
		JCSubBlock currentSubBlock;
		Integer currentSBID;
	    String currentSubBlockName;
	    Iterator<Integer> iterator;
		JCSubBlock subBlock = null;
	    
	    if (subBlockName != null)
	    {
		    iterator = getPredecessorSubBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (subBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentSubBlock = JCSubBlock.getSubBlock(currentSBID);
		        	currentSubBlockName = currentSubBlock.getSubBlockName();
		        	if (currentSubBlock != null)
		        	{
		        		if (currentSubBlockName.compareTo(subBlockName) == 0)
		        		{
		        			subBlock = currentSubBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return subBlock;
	}
	
	/**
	 * Returns the number of predecessor sub-block objects in this sub-block 
	 * object. 
	 *
	 * @return numberOfPredecessorSubBlocks  the number of predecessor sub-blocks 
	 *                                       contained in this sub-block object
	 *                        
	 * @see                                  JCSubBlock
	 */
	public long numberOfPredecessorSubBlocks()
	{
		return _predecessorBlocks.size();
	}
	
	/**
	 * Returns the predecessor sub-block iterator for accessing the 
	 * predecessor sub-block objects in this sub-block object. 
	 *
	 * @return iterator  the predecessor sub-block iterator for 
	 *                   accessing the predecessor sub-block objects
	 *                        
	 * @see              JCSubBlock
	 */
	public Iterator<Integer> getPredecessorSubBlockIterator()
	{
		return _predecessorBlocks.iterator();
	}
		
	/**
	 * Adds the sub-block object as a successor of this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this sub-block
	 *                   object
	 *               
	 * @see          JCSubBlock
	 */
	public void addSuccessorSubBlock(JCSubBlock subBlock)
	{
		if (_successorBlocks.add(subBlock.getSubBlockID()) != true)
		{
			System.out.println("JCSubBlock: Error adding successor sub-block " + subBlock.getSubBlockName());
		}
		updateSuccessorBasicBlocks(subBlock);
	}
	
	/**
	 * Removes the successor sub-block object from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this sub-block
	 *                   object
	 *               
	 * @see              JCSubBlock
	 */
	public void removeSuccessorSubBlock(JCSubBlock subBlock)
	{
		if (_successorBlocks.remove((Integer)subBlock.getSubBlockID()) != true)
		{
			System.out.println("JCSubBlock: Error removing successor sub-block " + subBlock.getSubBlockName());
		}
	}
	
	/**
	 * Finds the successor sub-block object from within this sub-block object
	 * with the specified sub-block name. 
	 *
	 * @param  subBlockName    the string name of the successor sub-block object
	 *                         to search for
	 *               
	 * @return subBlock        the found successor sub-block object in this sub-block
	 *                         object
	 *               
	 * @see                    JCSubBlock
	 */
	public JCSubBlock findSuccessorSubBlock(String subBlockName)
	{
		JCSubBlock currentSubBlock;
		Integer currentSBID;
	    String currentSubBlockName;
	    Iterator<Integer> iterator;
		JCSubBlock subBlock = null;
	    
	    if (subBlockName != null)
	    {
		    iterator = getPredecessorSubBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (subBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentSubBlock = JCSubBlock.getSubBlock(currentSBID);
		        	currentSubBlockName = currentSubBlock.getSubBlockName();
		        	if (currentSubBlock != null)
		        	{
		        		if (currentSubBlockName.compareTo(subBlockName) == 0)
		        		{
		        			subBlock = currentSubBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return subBlock;
	}
	
	/**
	 * Returns the number of successor sub-block objects in this sub-block 
	 * object. 
	 *
	 * @return numberOfPredecessorSubBlocks  the number of successor sub-blocks 
	 *                                       contained in this sub-block object
	 *                        
	 * @see                                  JCSubBlock
	 */
	public long numberOfSuccessorSubBlocks()
	{
		return _successorBlocks.size();
	}
	
	/**
	 * Returns the successor sub-block iterator for accessing the 
	 * successor sub-block objects in this sub-block object. 
	 *
	 * @return iterator  the successor sub-block iterator for 
	 *                   accessing the successor sub-block objects
	 *                        
	 * @see              JCSubBlock
	 */
	public Iterator<Integer> getSuccessorSubBlockIterator()
	{
		return _successorBlocks.iterator();
	}
	
	/**
	 * Gets the starting section for this sub-block. 
	 *
	 * @return startingSectionID   the section ID of the leftmost section
	 * 
	 * @see                        JCSectionBlock
	 * @see                        JCSubBlock
	 */
	Integer getStartingSectionID()
	{
		return _startingSectionID;
	}
	
	/**
	 * Sets the starting section for this sub-block 
	 *
	 * @param startingSectionID   the section ID of the leftmost section
	 * 
	 * @see                       JCSectionBlock
	 * @see                       JCSubBlock
	 */
	void setStartingSectionID(int startingSectionID)
	{
		_startingSectionID = startingSectionID;
	}
	
	/**
	 * Gets the ending section for this sub-block. 
	 *
	 * @return endingSectionID     the section ID of the rightmost section
	 * 
	 * @see                        JCSectionBlock
	 * @see                        JCSubBlock
	 */
	Integer getEndingSectionID()
	{
		return _endingSectionID;
	}
	
	/**
	 * Sets the ending section for this sub-block 
	 *
	 * @param endingSectionID     the section ID of the rightmost section
	 * 
	 * @see                       JCSectionBlock
	 * @see                       JCSubBlock
	 */
	void setEndingSectionID(int endingSectionID)
	{
		_endingSectionID = endingSectionID;
	}
	
	/**
	 * Gets the parent block for this sub-block 
	 *
	 * @param blockObj            the parent containing block for this sub-block object
	 * 
	 * @see                       JCBlock
	 * @see                       JCSubBlock
	 */
	JCBlock getParentBlock()
	{
		JCBlock blockObj = null;
		
		if (_parentBlockID >= 0)
		{
			blockObj = JCBlock.getBlock(_parentBlockID);
		}
		
		return blockObj;
	}
	
	/**
	 * Sets the parent block for this sub-block 
	 *
	 * @param blockObj            the parent containing block for this sub-block object
	 * 
	 * @see                       JCBlock
	 * @see                       JCSubBlock
	 */
	void setParentBlock(JCBlock blockObj)
	{
		if (blockObj != null)
		{
		    _parentBlockID = blockObj.getBlockID();
		}
		else
		{
			_parentBlockID = -1;
		}
	}
	
	/**
	 * Puts the cost solution entry associated with the specified cost key indices
	 * used to represent execution interfaces between subgraphs. 
	 *
	 * @param costSolution  the selected preemption points associated 
	 *                      with the specified zeta1 and zeta2 values 
	 *
	 * @param blockSolution true if this cost solution is being added
	 *                      for a block object
	 * 
	 * @see            HashMap
	 * @see            JCCostKey
	 * @see            JCCostSolution
	 * @see            JCSubBlock
	 */
	public void putCostSolution(JCCostSolution costSolution, boolean blockSolution)
	{
		if (blockSolution == true)
		{
			if (getCostSolution(costSolution.getSolutionKey()) == null)
			{
				_solutionMap.add(costSolution);
			}
		}
		else
		{
			_solutionMap.add(costSolution);
		}
	}

	/**
	 * Creates new cost and preemption points hash maps representing a new solution. 
	 *
	 * @see            ArrayList
	 * @see            HashMap
	 * @see            JCCostKey
	 * @see            JCCostSolution
	 * @see            JCSubBlock
	 */
	public void createCostSolution()
	{
		_solutionMap = new ArrayList<JCCostSolution>();
	}
	
	/**
	 * Gets the cost solution entry associated with the specified cost key indices
	 * used to represent execution interfaces between subgraphs. 
	 *
	 * @param costSolutionKey  the left offset or zeta1 of the predecessor interface and
	 *                         the right offset or zeta2 of the successor interface
	 * 
	 * @return costSolution  the selected preemption points associated 
	 *                       with the specified zeta1 and zeta2 values 
	 *                        
	 * @see            ArrayList
	 * @see            JCCostKey
	 * @see            JCCostSolution
	 * @see            JCSubBlock
	 */
	JCCostSolution getCostSolution(JCCostKey solutionKey)
	{
		int keyIndex;
		JCCostKey mapCostKey;
		JCCostSolution mapCostSolution = null;
		
		for (keyIndex = 0; keyIndex < _solutionMap.size(); keyIndex++)
		{
			mapCostSolution = _solutionMap.get(keyIndex);
			mapCostKey = mapCostSolution.getSolutionKey();
			if ((mapCostKey.getLeftIndex() == solutionKey.getLeftIndex()) &&
				(mapCostKey.getLeftBasicBlock() == solutionKey.getLeftBasicBlock()) &&
			    (mapCostKey.getRightIndex() == solutionKey.getRightIndex()) &&
			    (mapCostKey.getRightBasicBlock() == solutionKey.getRightBasicBlock()))
			{
				break;
			}
			mapCostSolution = null;
		}

		if (mapCostSolution != null)
		{
			//System.out.println("    JCCostSolution found cost solution at " + keyIndex);
		}
		
		return mapCostSolution;
	}
	
	/**
	 * Removes the solution and preemption points collection contents. 
	 *
	 * @see            ArrayList
	 * @see            JCCostKey
	 * @see            JCCostSolution
	 * @see            JCSubBlock
	 */
	public void deleteCostSolution()
	{
		_solutionMap.clear();
		_solutionMap = null;
	}
	
	/**
	 * Gets the solution collection object. 
	 *
	 * @return solutionMap  the solutions collection object.
	 *                        
	 * @see            ArrayList
	 * @see            JCCostKey
	 * @see            JCCostSolution
	 * @see            JCSubBlock
	 */
	public ArrayList<JCCostSolution> getSolutionMap()
	{
		return _solutionMap;
	}
	
	/**
	 * Sets the solution collection object. 
	 *
	 * @param solutionMap  the solutions collection object.
	 *                        
	 * @see            HashMap
	 * @see            JCCostKey
	 * @see            JCCostSolution
	 * @see            JCSubBlock
	 */
	public void setSolutionMap(ArrayList<JCCostSolution> solutionMap)
	{
		_solutionMap = solutionMap;
	}
	
	/**
	 * Updates the predecessor basic block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block predecessor
	 *                   contained within
	 *               
	 * @see              JCBasicBlock
	 * @see              JCSubBlock
	 */
	public abstract void updatePredecessorBasicBlocks(JCSubBlock subBlock);
	
	/**
	 * Updates the successor basic block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block successors
	 *                   contained within
	 *               
	 * @see              JCBasicBlock
	 * @see              JCSubBlock
	 */
	public abstract void updateSuccessorBasicBlocks(JCSubBlock subBlock);
	
	/**
	 * Updates the predecessor section block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the section block predecessor
	 *                   contained within
	 *               
	 * @see              JCSectionBlock
	 * @see              JCSubBlock
	 */
	public abstract void updatePredecessorSectionBlocks(JCSubBlock subBlock);
	
	/**
	 * Updates the successor section block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the section block successors
	 *                   contained within
	 *               
	 * @see              JCSectionBlock
	 * @see              JCSubBlock
	 */
	public abstract void updateSuccessorSectionBlocks(JCSubBlock subBlock);
	
	/**
	 * Returns the string sub-block object type name. 
	 *
	 * @return ID     the string type name of the sub-block object
	 * 
	 * @see           JCSubBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCSubBlock";
	}
    
	/**
	 * Displays information about this sub-block object. 
	 *
	 * @see           JCSubBlock
	 */
    @Override
    public void displayObjectInformation()
    {
    	JCBlock                      block;
    	JCConditionalBlock           conditionalBlock;
    	JCConditionalSection         conditionalSection;
    	JCCostSolution               costSolution;
    	Iterator<JCCostSolution>     costSolutionIterator;
    	JCFunctionBlock              functionBlock;
    	JCFunctionCallBlock          functionCallBlock;
    	Iterator<Integer>            iterator;
    	JCLoopBlock                  loopBlock;
    	String                       objectType;
    	JCBlock                      parentBlock;
    	JCSectionBlock               sectionBlock;
    	JCSubBlock                   subBlock;
       	Integer                      subBlockID;

    	System.out.println("Sub Block ID " + getSubBlockID() + " Name " + getSubBlockName());
    	
       	objectType = getObjectTypeName();
       	if (objectType.compareTo("JCBlock") == 0)
       	{
       		block = (JCBlock) this;
           	System.out.println("Block ID " + block.getBlockID() + " Name " + block.getBlockName() + " sub-block ID " + block.getSubBlockID() + " sub-block Name " + block.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCSectionBlock") == 0)
       	{
       		sectionBlock = (JCSectionBlock) this;
           	System.out.println("Section Block ID " + sectionBlock.getSectionBlockID() + " Name " + sectionBlock.getSectionBlockName() + " sub-block ID " + sectionBlock.getSubBlockID() + " sub-block Name " + sectionBlock.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCConditionalSection") == 0)
       	{
       		conditionalSection = (JCConditionalSection) this;
           	System.out.println("Conditional Section ID " + conditionalSection.getConditionalSectionID() + " Name " + conditionalSection.getConditionalSectionName() + " block ID " + conditionalSection.getBlockID() + " block name " + conditionalSection.getBlockName() + " sub-block ID " + conditionalSection.getSubBlockID() + " sub-block Name " + conditionalSection.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCConditionalBlock") == 0)
       	{
       		conditionalBlock = (JCConditionalBlock) this;
           	System.out.println("Conditional Block ID " + conditionalBlock.getConditionalBlockID() + " Name " + conditionalBlock.getConditionalBlockName() + " sub-block ID " + conditionalBlock.getSubBlockID() + " sub-block Name " + conditionalBlock.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCLoopBlock") == 0)
       	{
       		loopBlock = (JCLoopBlock) this;
           	System.out.println("Loop Block ID " + loopBlock.getLoopBlockID() + " Name " + loopBlock.getLoopBlockName() + " block ID " + loopBlock.getBlockID() + " block name " + loopBlock.getBlockName() + " sub-block ID " + loopBlock.getSubBlockID() + " sub-block Name " + loopBlock.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCFunctionBlock") == 0)
       	{
       		functionBlock = (JCFunctionBlock) this;
           	System.out.println("Function Block ID " + functionBlock.getFunctionBlockID() + " Name " + functionBlock.getFunctionBlockName() + " Block ID " + functionBlock.getBlockID() + " Name " + functionBlock.getBlockName() + " sub-block ID " + functionBlock.getSubBlockID() + " sub-block name " + functionBlock.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCFunctionCallBlock") == 0)
       	{
       		functionCallBlock = (JCFunctionCallBlock) this;
           	System.out.println("Function Call Block ID " + functionCallBlock.getFunctionCallBlockID() + " Name " + functionCallBlock.getFunctionCallBlockName() + " Block ID " + functionCallBlock.getBlockID() + " Name " + functionCallBlock.getBlockName() + " sub-block ID " + functionCallBlock.getSubBlockID() + " sub-block name " + functionCallBlock.getSubBlockName());
       	}
    		
    	iterator = this.getPredecessorSubBlockIterator();
    	System.out.print("    Predecessors: ");
    	while (iterator.hasNext() == true)
    	{
    		subBlockID = iterator.next();
    		subBlock = JCSubBlock.getSubBlock(subBlockID);
           	objectType = subBlock.getObjectTypeName();
	       	if (objectType.compareTo("JCBlock") == 0)
	       	{
	       		block = (JCBlock) subBlock;
	           	System.out.print("(" + block.getBlockID() + "," + block.getBlockName() + "," + block.getSubBlockID() + "," + block.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCConditionalSection") == 0)
	       	{
	       		conditionalSection = (JCConditionalSection) subBlock;
	           	System.out.print("(" + conditionalSection.getConditionalSectionID() + "," + conditionalSection.getConditionalSectionName() + "," + conditionalSection.getBlockID() + "," + conditionalSection.getBlockName() + "," + conditionalSection.getSubBlockID() + "," + conditionalSection.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCSectionBlock") == 0)
	       	{
	       		sectionBlock = (JCSectionBlock) subBlock;
	           	System.out.println("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCConditionalBlock") == 0)
	       	{
	       		conditionalBlock = (JCConditionalBlock) subBlock;
	           	System.out.println("(" + conditionalBlock.getConditionalBlockID() + "," + conditionalBlock.getConditionalBlockName() + "," + conditionalBlock.getSubBlockID() + "," + conditionalBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCLoopBlock") == 0)
	       	{
	       		loopBlock = (JCLoopBlock) subBlock;
	           	System.out.print("(" + loopBlock.getLoopBlockID() + "," + loopBlock.getLoopBlockName() + "," + loopBlock.getBlockID() + "," + loopBlock.getBlockName() + "," + loopBlock.getSubBlockID() + "," + loopBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCFunctionBlock") == 0)
	       	{
	       		functionBlock = (JCFunctionBlock) subBlock;
	           	System.out.println("(" + functionBlock.getFunctionBlockID() + "," + functionBlock.getFunctionBlockName() + "," + functionBlock.getBlockID() + "," + functionBlock.getBlockName() + "," + functionBlock.getSubBlockID() + "," + functionBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCFunctionCallBlock") == 0)
	       	{
	       		functionCallBlock = (JCFunctionCallBlock) subBlock;
	           	System.out.println("(" + functionCallBlock.getFunctionCallBlockID() + "," + functionCallBlock.getFunctionCallBlockName() + "," + functionCallBlock.getBlockID() + "," + functionCallBlock.getBlockName() + "," + functionCallBlock.getSubBlockID() + "," + functionCallBlock.getSubBlockName() + ") ");
	       	}
	       	else
	       	{
	    		System.out.print("(" + subBlock.getSubBlockID() + "," + subBlock.getSubBlockName() + "," + subBlock.getSubBlockID() + ") ");
	       	}
    	}
    	System.out.println();
    	
    	iterator = this.getSuccessorSubBlockIterator();
    	System.out.print("    Successors: ");
    	while (iterator.hasNext() == true)
    	{
    		subBlockID = iterator.next();
    		subBlock = JCSubBlock.getSubBlock(subBlockID);
	       	objectType = subBlock.getObjectTypeName();
	       	if (objectType.compareTo("JCBlock") == 0)
	       	{
	       		block = (JCBlock) subBlock;
	           	System.out.print("(" + block.getBlockID() + "," + block.getBlockName() + "," + block.getSubBlockID() + "," + block.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCConditionalSection") == 0)
	       	{
	       		conditionalSection = (JCConditionalSection) subBlock;
	           	System.out.print("(" + conditionalSection.getConditionalSectionID() + "," + conditionalSection.getConditionalSectionName() + "," + conditionalSection.getBlockID() + "," + conditionalSection.getBlockName() + "," + conditionalSection.getSubBlockID() + "," + conditionalSection.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCSectionBlock") == 0)
	       	{
	       		sectionBlock = (JCSectionBlock) subBlock;
	           	System.out.println("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCConditionalBlock") == 0)
	       	{
	       		conditionalBlock = (JCConditionalBlock) subBlock;
	           	System.out.println("(" + conditionalBlock.getConditionalBlockID() + "," + conditionalBlock.getConditionalBlockName() + "," + conditionalBlock.getSubBlockID() + "," + conditionalBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCLoopBlock") == 0)
	       	{
	       		loopBlock = (JCLoopBlock) subBlock;
	           	System.out.print("(" + loopBlock.getLoopBlockID() + "," + loopBlock.getLoopBlockName() + "," + loopBlock.getBlockID() + "," + loopBlock.getBlockName() + "," + loopBlock.getSubBlockID() + "," + loopBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCFunctionBlock") == 0)
	       	{
	       		functionBlock = (JCFunctionBlock) subBlock;
	           	System.out.println("(" + functionBlock.getFunctionBlockID() + "," + functionBlock.getFunctionBlockName() + "," + functionBlock.getBlockID() + "," + functionBlock.getBlockName() + "," + functionBlock.getSubBlockID() + "," + functionBlock.getSubBlockName() + ") ");
	       	}
	       	else if (objectType.compareTo("JCFunctionCallBlock") == 0)
	       	{
	       		functionCallBlock = (JCFunctionCallBlock) subBlock;
	           	System.out.println("(" + functionCallBlock.getFunctionCallBlockID() + "," + functionCallBlock.getFunctionCallBlockName() + "," + functionCallBlock.getBlockID() + "," + functionCallBlock.getBlockName() + "," + functionCallBlock.getSubBlockID() + "," + functionCallBlock.getSubBlockName() + ") ");
	       	}
	       	else
	       	{
	    		System.out.print("(" + subBlock.getSubBlockID() + "," + subBlock.getSubBlockName() + "," + subBlock.getSubBlockID() + ") ");
	       	}
    	}
    	System.out.println();
    	
    	System.out.print("    Starting section: ");
    	if (_startingSectionID > -1)
    	{
    		sectionBlock = JCSectionBlock.getSectionBlock(_startingSectionID);
    		if (sectionBlock != null)
    		{
        		System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSectionNPRWCET() + ") ");
    		}
    	}
    	System.out.println();
    	
    	System.out.print("    Ending section: ");
    	if (_endingSectionID > -1)
    	{
    		sectionBlock = JCSectionBlock.getSectionBlock(_endingSectionID);
    		if (sectionBlock != null)
    		{
        		System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSectionNPRWCET() + ") ");
    		}
    	}
    	System.out.println();
    	
    	System.out.print("    Parent block: ");
    	if (_parentBlockID > -1)
    	{
    		parentBlock = JCBlock.getBlock(_parentBlockID);
    		if (parentBlock != null)
    		{
    	       	objectType = parentBlock.getObjectTypeName();
    	       	if (objectType.compareTo("JCBlock") == 0)
    	       	{
    	       		block = (JCBlock) parentBlock;
    	           	System.out.print("(" + block.getBlockID() + "," + block.getBlockName() + "," + block.getSubBlockID() + "," + block.getSubBlockName() + ") ");
    	       	}
    	       	else if (objectType.compareTo("JCConditionalSection") == 0)
    	       	{
    	       		conditionalSection = (JCConditionalSection) parentBlock;
    	           	System.out.print("(" + conditionalSection.getConditionalSectionID() + "," + conditionalSection.getConditionalSectionName() + "," + conditionalSection.getBlockID() + "," + conditionalSection.getBlockName() + "," + conditionalSection.getSubBlockID() + "," + conditionalSection.getSubBlockName() + ") ");
    	       	}
    	       	else if (objectType.compareTo("JCLoopBlock") == 0)
    	       	{
    	       		loopBlock = (JCLoopBlock) parentBlock;
    	           	System.out.print("(" + loopBlock.getLoopBlockID() + "," + loopBlock.getLoopBlockName() + "," + loopBlock.getBlockID() + "," + loopBlock.getBlockName() + "," + loopBlock.getSubBlockID() + "," + loopBlock.getSubBlockName() + ") ");
    	       	}
    	       	else if (objectType.compareTo("JCFunctionBlock") == 0)
    	       	{
    	       		functionBlock = (JCFunctionBlock) parentBlock;
    	           	System.out.println("Function Block ID " + functionBlock.getFunctionBlockID() + " Name " + functionBlock.getFunctionBlockName() + " Block ID " + functionBlock.getBlockID() + " Name " + functionBlock.getBlockName() + " sub-block ID " + functionBlock.getSubBlockID() + " sub-block name " + functionBlock.getSubBlockName());
    	       	}
    	       	else if (objectType.compareTo("JCFunctionCallBlock") == 0)
    	       	{
    	       		functionCallBlock = (JCFunctionCallBlock) parentBlock;
    	           	System.out.println("Function Call Block ID " + functionCallBlock.getFunctionCallBlockID() + " Name " + functionCallBlock.getFunctionCallBlockName() + " Block ID " + functionCallBlock.getBlockID() + " Name " + functionCallBlock.getBlockName() + " sub-block ID " + functionCallBlock.getSubBlockID() + " sub-block name " + functionCallBlock.getSubBlockName());
    	       	}
    	       	else
    	       	{
            		System.out.print("(" + parentBlock.getBlockID() + "," + parentBlock.getBlockName() + "," + parentBlock.getSubBlockID() + "," + parentBlock.getSubBlockName() + ") ");
    	       	}
    		}
    	}
    	System.out.println();
    	
    	System.out.print("    Cost solutions: ");
    	if (_solutionMap != null)
    	{
        	costSolutionIterator = _solutionMap.iterator();
        	while (costSolutionIterator.hasNext() == true)
        	{
        		costSolution = costSolutionIterator.next();
        		costSolution.displayObjectInformation();
        	}
    	}
    	System.out.println();
    }

	/**
	 * Displays information about all sub-block objects. 
	 *
	 * @see           JCSubBlock
	 */
    public static void displayAllObjects()
    {
	    int                  subBlockID;
	    JCSubBlock           currentSubBlock;
		
    	System.out.println("The number of sub-blocks is " + _subblocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (subBlockID = 0; subBlockID < _subblocks.size(); subBlockID++)
		{
			currentSubBlock = JCSubBlock.getSubBlock(subBlockID);
			currentSubBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
