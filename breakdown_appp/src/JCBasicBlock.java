import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * JCBasicBlock is a fundamental class whose purpose is to contain
 * the control flow graph basic block objects for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCBasicBlock extends JCObject
{
	private static boolean _debugPredSuccLists = false;
	
    private static int _nextID = 0;
    
	private static int _bbCount = 0;                   // Counter to count which node it is
	private static ArrayList<JCBasicBlock> _graphNodes = 
			          new ArrayList<JCBasicBlock>();   // Basic block structure of the graph

	private static int _q;                             // Maximum non preemptive region parameter
	private static int _startingBBID;
	private static int _endingBBID;

	private int _bBlockID;
	private String _ID;
	private int _WCET;
	private int _sectionID;
    private ArrayList<Integer> _predecessorBasicBlocks;
    private ArrayList<Integer> _successorBasicBlocks;
    private ArrayList<Integer> _predecessorQListBasicBlocks;
    private ArrayList<Integer> _successorQListBasicBlocks;
	
	/**
	 * Default constructor.
	 */
	JCBasicBlock()
	{
		_bBlockID = _nextID;
		_nextID++;
		_sectionID = -1;
		_ID = "";
		_WCET = 0;
		_predecessorBasicBlocks = new ArrayList<Integer>();
		_successorBasicBlocks = new ArrayList<Integer>();
		_predecessorQListBasicBlocks = new ArrayList<Integer>();
		_successorQListBasicBlocks = new ArrayList<Integer>();
	}
	
	/**
	 * The constructor used by the parser.
	 */
	JCBasicBlock(String id, int wcet)
	{	
		_bBlockID = _nextID;
		_nextID++;
		_sectionID = -1;
		_ID = id;
		_WCET = wcet;
		_predecessorBasicBlocks = new ArrayList<Integer>();
		_successorBasicBlocks = new ArrayList<Integer>();
		_predecessorQListBasicBlocks = new ArrayList<Integer>();
		_successorQListBasicBlocks = new ArrayList<Integer>();
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCBasicBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_bbCount = 0;
		_graphNodes = new ArrayList<JCBasicBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the basic block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _bBlockID   the identifier of this basic block object
	 * 
	 * @see                JCBasicBlock
	 */
	public int getBBlockID()
	{
		return _bBlockID;
	}

	/**
	 * Returns the string name of the basic block object. 
	 *
	 * @return _ID     the string name of this basic block object
	 * 
	 * @see            JCBasicBlock
	 */
	public String getBBlockName()
	{
		return _ID;
	}
	
	/**
	 * Returns the worst case execution time of the basic block object. 
	 *
	 * @return _WCET   the worst case execution time of this basic block object
	 * 
	 * @see            JCBasicBlock
	 */
	public int getBBlockWCET()
	{
		return _WCET;
	}
	
	/**
	 * Gets the number of basic blocks contained in 
	 * the current control flow graph. 
	 *
	 * @return  N    the basic block count for this CFG
	 * 
	 * @see          JCBasicBlock
	 */
	public static int getNValue()
	{
		return _bbCount;
	}
	
	/**
	 * Sets the size of number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCBasicBlock
	 */
	public static void setNValue(int n)
	{
		_bbCount = n;
	}
	
	/**
	 * Gets the size of the maximum non-preemptive region for 
	 * the current control flow graph. 
	 *
	 * @return  Q    the maximum non-preemptive region for this CFG
	 * 
	 * @see          JCBasicBlock
	 */
	public static int getQValue()
	{
		return _q;
	}
	
	/**
	 * Sets the size of the maximum non-preemptive region for 
	 * the current control flow graph. 
	 *
	 * @param  Q    the maximum non-preemptive region for this CFG
	 * 
	 * @see         JCBasicBlock
	 */
	public static void setQValue(int Q)
	{
		_q = Q;
	}
	
	/**
	 * Gets the starting program basic block for this preemption 
	 * point placement problem 
	 *
	 * @return bBlockObj          the program starting basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSectionBlock
	 */
	public static JCBasicBlock getStartingBasicBlock()
	{
		JCBasicBlock bBlockObj = null;
		
		if (_startingBBID >= 0)
		{
			bBlockObj = JCBasicBlock.getBasicBlock(_startingBBID);
		}
		
		return bBlockObj;
	}
	
	/**
	 * Sets the starting program basic block for this 
	 * preemption point placement problem 
	 *
	 * @param bBlockObj           the program starting basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSectionBlock
	 */
	public static void setStartingBasicBlock(JCBasicBlock bBlockObj)
	{
		if (bBlockObj != null)
		{
			_startingBBID = bBlockObj.getBBlockID();
		}
		else
		{
			_startingBBID = -1;
		}
	}
	
	/**
	 * Gets the ending program basic block for this preemption 
	 * point placement problem 
	 *
	 * @return bBlockObj          the program ending basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSectionBlock
	 */
	public static JCBasicBlock getEndingBasicBlock()
	{
		JCBasicBlock bBlockObj = null;
		
		if (_endingBBID >= 0)
		{
			bBlockObj = JCBasicBlock.getBasicBlock(_endingBBID);
		}
		
		return bBlockObj;
	}
	
	/**
	 * Sets the ending program basic block for this 
	 * preemption point placement problem 
	 *
	 * @param bBlockObj           the program ending basic block
	 * 
	 * @see                       JCBasicBlock
	 * @see                       JCSectionBlock
	 */
	public static void setEndingBasicBlock(JCBasicBlock bBlockObj)
	{
		if (bBlockObj != null)
		{
			_endingBBID = bBlockObj.getBBlockID();
		}
		else
		{
			_endingBBID = -1;
		}
	}
	
	/**
	 * Stores the basic block object at the specified location in
	 * the control flow graph node array. 
	 *
	 * @param  bBlockID    the identifier of the stored basic block object
	 * 
	 * @param  basicBlock  the basic block object to be stored in the graph
	 * 
	 * @see                JCBasicBlock
	 */
	public static void setBasicBlock(int bBlockID, JCBasicBlock basicBlock)
	{
		if (bBlockID < _bbCount)
		{
	        _graphNodes.add(basicBlock);
		}
	}
	
	/**
	 * Returns the basic block object associated with the specified identifier. 
	 *
	 * @return basicBlock  the basic block object stored in the graph
	 * 
	 * @see                 JCBasicBlock
	 */
	public static JCBasicBlock getBasicBlock(int bBlockID)
	{
		if (bBlockID < _bbCount)
		{
	        return _graphNodes.get(bBlockID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Stores the parent section block object containing this basic block
	 * object. 
	 *
	 * @param  sectionBlock  the section block object to be stored as parent
	 * 
	 * @see                  JCBasicBlock
	 * @see                  JCSectionBlock
	 */
	public void setSectionBlock(JCSectionBlock sectionBlock)
	{
		if (sectionBlock != null)
		{
			this._sectionID = sectionBlock.getSectionBlockID();
		}
		else
		{
			this._sectionID = -1;
		}
	}
	
	/**
	 * Returns the parent section block object containing this basic block
	 * object. 
	 *
	 * @return  sectionBlock  the section block object parent
	 * 
	 * @see                   JCBasicBlock
	 * @see                   JCSectionBlock
	 */
	public JCSectionBlock getSectionBlock()
	{
		return JCSectionBlock.getSectionBlock(_sectionID);
	}
	
	/**
	 * Determines if the interface around this basic block object meets the
	 * inclusive constraint limited by the maximum non-preemptive region.
	 *
	 * @param zeta1      the left offset or zeta1 of the predecessor interface
	 * 
	 * @param zeta2      the right offset or zeta2 of the successor interface
	 * 
	 * @return feasible  true/false indicating if this solution is feasible
	 * 
	 * @see              JCBasicBlock
	 */
	public boolean isFeasibleInclusive(int zeta1, int zeta2)
	{
		if ((zeta1 + zeta2 + _WCET) > JCBasicBlock._q)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Determines if the interface around this basic block object meets the
	 * exclusive constraint limited by the maximum non-preemptive region.
	 *
	 * @param zeta1      the left offset or zeta1 of the predecessor interface
	 * 
	 * @param zeta2      the right offset or zeta2 of the successor interface
	 * 
	 * @return feasible  true/false indicating if this solution is feasible
	 * 
	 * @see              JCBasicBlock
	 */
	public boolean isFeasibleExclusive(int zeta1, int zeta2)
	{
		if ((zeta1 + zeta2 + _WCET) >= JCBasicBlock._q)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Gets the cost entry associated with the specified cost key indices
	 * used to represent execution interfaces between subgraphs. 
	 *
	 * @param left   the left offset or zeta1 of the predecessor interface
	 * 
	 * @param right  the right offset or zeta2 of the successor interface
	 * 
	 * @return cost  the cost associated with the specified zeta1 and zeta2
	 *               values 
	 *                        
	 * @see          Integer
	 * @see          JCBasicBlock
	 * @see          JCCostKey
	 */
	public int getCostEntry(int left, int right)
	{
		if (isFeasibleInclusive(left, right) == true)
		{
			return _WCET;
		}
		else
		{
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * Gets the cost entry associated with the specified cost key indices
	 * used to represent execution interfaces between subgraphs. 
	 *
	 * @param costKey  the left offset or zeta1 of the predecessor interface and
	 *                 the right offset or zeta2 of the successor interface
	 * 
	 * @return cost    the cost associated with the specified zeta1 and zeta2
	 *                 values 
	 *                        
	 * @see            Integer
	 * @see            JCBasicBlock
	 * @see            JCCostKey
	 */
	public int getCostEntry(JCCostKey costKey)
	{
		if (isFeasibleInclusive(costKey.getLeftIndex(), costKey.getRightIndex()) == true)
		{
			return _WCET;
		}
		else
		{
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * Adds the basic block object as a predecessor of this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to add to this basic block
	 *                     object
	 *               
	 * @see                JCBasicBlock
	 */
	public void addPredecessorBasicBlock(JCBasicBlock basicBlock)
	{
		int basicBlockID;
		
		basicBlockID = basicBlock.getBBlockID();
		if (_predecessorBasicBlocks.contains(basicBlockID) == false)
		{
			if (_predecessorBasicBlocks.add(basicBlock.getBBlockID()) != true)
			{
				System.out.println("JCBasicBlock: Error adding predecessor basic block " + basicBlock.getBBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Basic block (" + basicBlock.getBBlockID() + ") ID: " + basicBlock.getBBlockName() + " added as a predecessor to basic block (" + this.getBBlockID() + ") ID: " + this.getBBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the predecessor basic block object from this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to remove from this basic block
	 *                     object
	 *               
	 * @see                JCBasicBlock
	 */
	public void removePredecessorBasicBlock(JCBasicBlock basicBlock)
	{
		if (_predecessorBasicBlocks.remove((Integer)basicBlock.getBBlockID()) != true)
		{
			System.out.println("JCBasicBlock: Error removing predecessor basic block " + basicBlock.getBBlockName());
		}
	}
	
	/**
	 * Determines if the specified basic block has a predecessor relationship to
	 * this basic block object. 
	 *
	 * @param  basicBlock     the basic block object to check for a predecessor
	 *                        relationship to this basic block object
	 *               
	 * @return hasPredecessor true/false indicating is the specified basic block
	 *                        object is a predecessor of this basic block
	 *                        
	 * @see                JCBasicBlock
	 */
	public boolean hasPredecessorBasicBlock(JCBasicBlock basicBlock)
	{
		return _predecessorBasicBlocks.contains(basicBlock.getBBlockID());
	}
	
	/**
	 * Finds the predecessor basic block object from within this basic block object
	 * with the specified basic block name. 
	 *
	 * @param  basicBlockName    the string name of the predecessor basic block object 
	 *                           to search for
	 *               
	 * @return basicBlock        the found predecessor basic block object in this basic block
	 *                           object
	 *               
	 * @see                      JCBasicBlock
	 */
	public JCBasicBlock findPredecessorBasicBlock(String basicBlockName)
	{
		JCBasicBlock basicBlock = null;
		JCBasicBlock currentBasicBlock;
		Integer currentSBID;
	    String currentBasicBlockName;
	    Iterator<Integer> iterator;
	    
	    if (basicBlockName != null)
	    {
		    iterator = getPredecessorBasicBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (basicBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentBasicBlock = JCBasicBlock.getBasicBlock(currentSBID);
		        	currentBasicBlockName = currentBasicBlock.getBBlockName();
		        	if (currentBasicBlock != null)
		        	{
		        		if (currentBasicBlockName.compareTo(basicBlockName) == 0)
		        		{
		        			basicBlock = currentBasicBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return basicBlock;
	}
	
	/**
	 * Returns the number of predecessor basic block objects in this basic block 
	 * object. 
	 *
	 * @return numberOfPredecessorBasicBlocks  the number of predecessor basic blocks 
	 *                                       contained in this basic block object
	 *                        
	 * @see                                  JCBasicBlock
	 */
	public long numberOfPredecessorBasicBlocks()
	{
		return _predecessorBasicBlocks.size();
	}
	
	/**
	 * Returns the predecessor basic block iterator for accessing the 
	 * predecessor basic block objects in this basic block object. 
	 *
	 * @return iterator  the predecessor basic block iterator for 
	 *                   accessing the predecessor basic block objects
	 *                        
	 * @see              JCBasicBlock
	 */
	public Iterator<Integer> getPredecessorBasicBlockIterator()
	{
		return _predecessorBasicBlocks.iterator();
	}
	
	
	/**
	 * Adds the basic block object as a successor of this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to add to this basic block
	 *                   object
	 *               
	 * @see          JCBasicBlock
	 */
	public void addSuccessorBasicBlock(JCBasicBlock basicBlock)
	{
		int basicBlockID;
		
		basicBlockID = basicBlock.getBBlockID();
		if (_successorBasicBlocks.contains(basicBlockID) == false)
		{
			if (_successorBasicBlocks.add(basicBlock.getBBlockID()) != true)
			{
				System.out.println("JCBasicBlock: Error adding successor basic block " + basicBlock.getBBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Basic block (" + basicBlock.getBBlockID() + ") ID: " + basicBlock.getBBlockName() + " added as a successor to basic block (" + this.getBBlockID() + ") ID: " + this.getBBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the successor basic block object from this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to remove from this basic block
	 *                     object
	 *               
	 * @see                JCBasicBlock
	 */
	public void removeSuccessorBasicBlock(JCBasicBlock basicBlock)
	{
		if (_successorBasicBlocks.remove((Integer)basicBlock.getBBlockID()) != true)
		{
			System.out.println("JCBasicBlock: Error removing successor basic block " + basicBlock.getBBlockName());
		}
	}
	
	/**
	 * Determines if the specified basic block has a successor relationship to
	 * this basic block object. 
	 *
	 * @param  basicBlock     the basic block object to check for a successor
	 *                        relationship to this basic block object
	 *               
	 * @return hasSuccessor   true/false indicating is the specified basic block
	 *                        object is a successor of this basic block
	 *                        
	 * @see                JCBasicBlock
	 */
	public boolean hasSuccessorBasicBlock(JCBasicBlock basicBlock)
	{
		return _successorBasicBlocks.contains(basicBlock.getBBlockID());
	}
	
	/**
	 * Finds the successor basic block object from within this basic block object
	 * with the specified basic block name. 
	 *
	 * @param  basicBlockName    the string name of the successor basic block object
	 *                           to search for
	 *               
	 * @return basicBlock        the found successor basic block object in this basic block
	 *                           object
	 *               
	 * @see                      JCBasicBlock
	 */
	public JCBasicBlock findSuccessorBasicBlock(String basicBlockName)
	{
		JCBasicBlock basicBlock = null;
		JCBasicBlock currentBasicBlock;
		Integer currentSBID;
	    String currentBasicBlockName;
	    Iterator<Integer> iterator;
	    
	    if (basicBlockName != null)
	    {
		    iterator = getSuccessorBasicBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (basicBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentBasicBlock = JCBasicBlock.getBasicBlock(currentSBID);
		        	currentBasicBlockName = currentBasicBlock.getBBlockName();
		        	if (currentBasicBlock != null)
		        	{
		        		if (currentBasicBlockName.compareTo(basicBlockName) == 0)
		        		{
		        			basicBlock = currentBasicBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return basicBlock;
	}
	
	/**
	 * Returns the number of successor basic block objects in this basic block 
	 * object. 
	 *
	 * @return numberOfSuccessorBasicBlocks  the number of successor basic blocks 
	 *                                       contained in this basic block object
	 *                        
	 * @see                                  JCBasicBlock
	 */
	public long numberOfSuccessorBasicBlocks()
	{
		return _successorBasicBlocks.size();
	}
	
	/**
	 * Returns the successor basic block iterator for accessing the 
	 * successor basic block objects in this basic block object. 
	 *
	 * @return iterator  the successor basic block iterator for 
	 *                   accessing the successor basic block objects
	 *                        
	 * @see              JCBasicBlock
	 */
	public Iterator<Integer> getSuccessorBasicBlockIterator()
	{
		return _successorBasicBlocks.iterator();
	}
		
	/**
	 * Adds the basic block object as a Q list predecessor of this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to add to this basic block
	 *                     object's predecessor Q list
	 *               
	 * @see                JCBasicBlock
	 */
	public void addPredecessorQListBasicBlock(JCBasicBlock basicBlock)
	{
		int basicBlockID;
		
		basicBlockID = basicBlock.getBBlockID();
		if (_predecessorQListBasicBlocks.contains(basicBlockID) == false)
		{
			if (_predecessorQListBasicBlocks.add(basicBlock.getBBlockID()) != true)
			{
				System.out.println("JCBasicBlock: Error adding Q list predecessor basic block " + basicBlock.getBBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Basic block (" + basicBlock.getBBlockID() + ") ID: " + basicBlock.getBBlockName() + " added as a Q list predecessor to basic block (" + this.getBBlockID() + ") ID: " + this.getBBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the predecessor basic block object from this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to remove from this basic block
	 *                     object's predecessor Q list
	 *               
	 * @see                JCBasicBlock
	 */
	public void removePredecessorQListBasicBlock(JCBasicBlock basicBlock)
	{
		if (_predecessorQListBasicBlocks.remove((Integer)basicBlock.getBBlockID()) != true)
		{
			System.out.println("JCBasicBlock: Error removing Q list predecessor basic block " + basicBlock.getBBlockName());
		}
	}
	
	/**
	 * Determines if the specified basic block has a Q list predecessor relationship to
	 * this basic block object. 
	 *
	 * @param  basicBlock     the basic block object to check for a Q list predecessor
	 *                        relationship to this basic block object
	 *               
	 * @return hasPredecessor true/false indicating is the specified basic block
	 *                        object is a Q list predecessor of this basic block
	 *                        
	 * @see                JCBasicBlock
	 */
	public boolean hasPredecessorQListBasicBlock(JCBasicBlock basicBlock)
	{
		return _predecessorQListBasicBlocks.contains(basicBlock.getBBlockID());
	}
	
    /**
     * Returns the Q list predecessor basic block object at the specified index in this 
     * basic block object.
     *
     * @param  bbIndex         the index of the Q list predecessor basic block object to return
     *               
     * @return basicBlock      the found basic block object
     *               
     * @see                    JCBasicBlock
     */
    public JCBasicBlock getPredecessorQListBasicBlockAtIndex(int bbIndex)
    {
	    JCBasicBlock basicBlock;
	    int bBlockID;
	
        bBlockID = _predecessorQListBasicBlocks.get(bbIndex);
        basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
        
	    return basicBlock;
    }

	/**
	 * Finds the predecessor Q list basic block object from within this basic block object
	 * with the specified basic block name. 
	 *
	 * @param  basicBlockName    the string name of the predecessor Q list basic block object 
	 *                           to search for
	 *               
	 * @return basicBlock        the found predecessor Q list basic block object in this basic block
	 *                           object
	 *               
	 * @see                      JCBasicBlock
	 */
	public JCBasicBlock findPredecessorQListBasicBlock(String basicBlockName)
	{
		JCBasicBlock basicBlock = null;
		JCBasicBlock currentBasicBlock;
		Integer currentSBID;
	    String currentBasicBlockName;
	    Iterator<Integer> iterator;
	    
	    if (basicBlockName != null)
	    {
		    iterator = getPredecessorQListBasicBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (basicBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentBasicBlock = JCBasicBlock.getBasicBlock(currentSBID);
		        	currentBasicBlockName = currentBasicBlock.getBBlockName();
		        	if (currentBasicBlock != null)
		        	{
		        		if (currentBasicBlockName.compareTo(basicBlockName) == 0)
		        		{
		        			basicBlock = currentBasicBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return basicBlock;
	}
	
	/**
	 * Returns the number of Q list predecessor basic block objects in this basic block 
	 * object. 
	 *
	 * @return numberOfPredecessorBasicBlocks  the number of predecessor basic blocks 
	 *                                         contained in this basic block object
	 *                        
	 * @see                                    JCBasicBlock
	 */
	public long numberOfPredecessorQListBasicBlocks()
	{
		return _predecessorQListBasicBlocks.size();
	}
	
	/**
	 * Returns the Q list predecessor basic block iterator for accessing the 
	 * Q list predecessor basic block objects in this basic block object. 
	 *
	 * @return iterator  the predecessor basic block iterator for 
	 *                   accessing the predecessor basic block objects
	 *                        
	 * @see              JCBasicBlock
	 */
	public Iterator<Integer> getPredecessorQListBasicBlockIterator()
	{
		return _predecessorQListBasicBlocks.iterator();
	}
	
	
	/**
	 * Adds the basic block object as a Q list successor of this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to add to this basic block
	 *                     object's successor Q list
	 *               
	 * @see          JCBasicBlock
	 */
	public void addSuccessorQListBasicBlock(JCBasicBlock basicBlock)
	{
		int basicBlockID;
		
		basicBlockID = basicBlock.getBBlockID();
		if (_successorQListBasicBlocks.contains(basicBlockID) == false)
		{
			if (_successorQListBasicBlocks.add(basicBlock.getBBlockID()) != true)
			{
				System.out.println("JCBasicBlock: Error adding Q list successor basic block " + basicBlock.getBBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Basic block (" + basicBlock.getBBlockID() + ") ID: " + basicBlock.getBBlockName() + " added as a Q list successor to basic block (" + this.getBBlockID() + ") ID: " + this.getBBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the successor basic block object from this basic block object. 
	 *
	 * @param  basicBlock  the basic block object to remove from this basic block
	 *                     object's successor Q list
	 *               
	 * @see                JCBasicBlock
	 */
	public void removeSuccessorQListBasicBlock(JCBasicBlock basicBlock)
	{
		if (_successorQListBasicBlocks.remove((Integer)basicBlock.getBBlockID()) != true)
		{
			System.out.println("JCBasicBlock: Error removing Q list successor basic block " + basicBlock.getBBlockName());
		}
	}
	
	/**
	 * Determines if the specified basic block has a Q list successor relationship to
	 * this basic block object. 
	 *
	 * @param  basicBlock     the basic block object to check for a successor
	 *                        relationship to this basic block object
	 *               
	 * @return hasSuccessor   true/false indicating is the specified basic block
	 *                        object is a successor of this basic block
	 *                        
	 * @see                   JCBasicBlock
	 */
	public boolean hasSuccessorQListBasicBlock(JCBasicBlock basicBlock)
	{
		return _successorQListBasicBlocks.contains(basicBlock.getBBlockID());
	}
	
    /**
     * Returns the Q list successor basic block object at the specified index in this 
     * basic block object.
     *
     * @param  bbIndex         the index of the Q list successor basic block object to return
     *               
     * @return basicBlock      the found basic block object
     *               
     * @see                    JCBasicBlock
     */
    public JCBasicBlock getSuccessorQListBasicBlockAtIndex(int bbIndex)
    {
	    JCBasicBlock basicBlock;
	    int bBlockID;
	
        bBlockID = _successorQListBasicBlocks.get(bbIndex);
        basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
        
	    return basicBlock;
    }

	/**
	 * Finds the Q list successor basic block object from within this basic block object
	 * with the specified basic block name. 
	 *
	 * @param  basicBlockName    the string name of the Q list successor basic block object
	 *                           to search for
	 *               
	 * @return basicBlock        the found Q list successor basic block object in this basic block
	 *                           object
	 *               
	 * @see                      JCBasicBlock
	 */
	public JCBasicBlock findSuccessorQListBasicBlock(String basicBlockName)
	{
		JCBasicBlock basicBlock = null;
		JCBasicBlock currentBasicBlock;
		Integer currentSBID;
	    String currentBasicBlockName;
	    Iterator<Integer> iterator;
	    
	    if (basicBlockName != null)
	    {
		    iterator = getSuccessorQListBasicBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (basicBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentBasicBlock = JCBasicBlock.getBasicBlock(currentSBID);
		        	currentBasicBlockName = currentBasicBlock.getBBlockName();
		        	if (currentBasicBlock != null)
		        	{
		        		if (currentBasicBlockName.compareTo(basicBlockName) == 0)
		        		{
		        			basicBlock = currentBasicBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return basicBlock;
	}
	
	/**
	 * Returns the number of Q list successor basic block objects in this basic block 
	 * object. 
	 *
	 * @return numberOfSuccessorBasicBlocks  the number of Q list successor basic blocks 
	 *                                       contained in this basic block object
	 *                        
	 * @see                                  JCBasicBlock
	 */
	public long numberOfSuccessorQListBasicBlocks()
	{
		return _successorQListBasicBlocks.size();
	}
	
	/**
	 * Returns the successor Q list basic block iterator for accessing the 
	 * Q list successor basic block objects in this basic block object. 
	 *
	 * @return iterator  the successor Q list basic block iterator for 
	 *                   accessing the Q list successor basic block objects
	 *                        
	 * @see              JCBasicBlock
	 */
	public Iterator<Integer> getSuccessorQListBasicBlockIterator()
	{
		return _successorQListBasicBlocks.iterator();
	}
	
	/**
	 * Updates the predecessor Q list basic block relationships starting from this 
	 * basic block object. 
	 *
	 * @param  prevBasicBlock  the basic block object to update the Q list predecessor
	 *                         basic block contained within
	 *               
	 * @see                    JCBasicBlock
	 */
	public void updatePredecessorQListBasicBlocks(JCBasicBlock prevBasicBlock)
	{
		final boolean debugDFSAlgorithm = false;
		
		int basicBlockID;
		Stack<JCBasicBlock> basicBlockStack = new Stack<JCBasicBlock>();
		int basicBlockWCETLength = this.getBBlockWCET();
		JCBasicBlock currentBasicBlock;
		Iterator<Integer> iterator;
		Stack<Iterator<Integer>> iteratorStack = new Stack<Iterator<Integer>>();
		JCBasicBlock predecessorBasicBlock;
		Stack<Integer> wcetStack = new Stack<Integer>();
		
		// Update the Q list entries.
		this.addPredecessorQListBasicBlock(prevBasicBlock);
		prevBasicBlock.addSuccessorQListBasicBlock(this);
		basicBlockWCETLength += prevBasicBlock.getBBlockWCET();
		
		if (basicBlockWCETLength <= JCBasicBlock._q)
		{
			// First time through setup the stacks with the first as if its the previous.
			iterator = prevBasicBlock.getPredecessorBasicBlockIterator();
			iteratorStack.push(iterator);
			basicBlockStack.push(prevBasicBlock);
			wcetStack.push(basicBlockWCETLength);
			
			while (iteratorStack.empty() == false)
			{
				// Return to the successor basic block to continue depth first traversal.
				iterator = iteratorStack.pop();
				currentBasicBlock = basicBlockStack.pop();
				basicBlockWCETLength = wcetStack.pop();
				if (debugDFSAlgorithm == true)
				{
					System.out.println("updatePredecessorQListBasicBlocks: Processing basic block " + currentBasicBlock.getBBlockName() + ":");
				}
				
				// Iterate through all previous section entries.
				while (iterator.hasNext() == true)
				{
					basicBlockID = iterator.next();
					predecessorBasicBlock = JCBasicBlock.getBasicBlock(basicBlockID);
					if (debugDFSAlgorithm == true)
					{
						System.out.println("updatePredecessorQListBasicBlocks: Next predecessor basic block " + predecessorBasicBlock.getBBlockName() + ":");
					}
					// Check to see if we've used up our Q window budget.
					if ((basicBlockWCETLength <= JCBasicBlock._q) && (predecessorBasicBlock != null))
					{
						// Update the Q list entries.
						this.addPredecessorQListBasicBlock(predecessorBasicBlock);
						predecessorBasicBlock.addSuccessorQListBasicBlock(this);
						
						// Push the information about the current basic block onto the stacks.
						iteratorStack.push(iterator);
						basicBlockStack.push(predecessorBasicBlock);
						basicBlockWCETLength += predecessorBasicBlock.getBBlockWCET();
						wcetStack.push(basicBlockWCETLength);
						
						// Set the depth first predecessor basic block for the next pass.
						iterator = predecessorBasicBlock.getPredecessorBasicBlockIterator();
						currentBasicBlock = predecessorBasicBlock;
						if (debugDFSAlgorithm == true)
						{
							System.out.println("updatePredecessorQListBasicBlocks: Processing predecessor basic block " + currentBasicBlock.getBBlockName() + ":");
						}
					}
					else if (predecessorBasicBlock == null)
                    {
                        //System.out.println("updatePredecessorQListBasicBlocks: Null basic block ID: " + basicBlockID);
                    }
				}
			}
		}
	}
	
	/**
	 * Returns the string basic block object type name. 
	 *
	 * @return ID     the string type name of the basic block object
	 * 
	 * @see           JCBasicBlock
	 */
    @Override
    public String getObjectTypeName()
    {
    	return "JCBasicBlock";
    }
    
	/**
	 * Displays information about this basic block object. 
	 *
	 * @see           JCBasicBlock
	 */
    @Override
    public void displayObjectInformation()
    {
    	JCBasicBlock      basicBlock;
    	Integer           bBlockID;
    	Iterator<Integer> iterator;
    	
    	System.out.println("Basic Block ID " + getBBlockID() + " Name " + getBBlockName() + " WCET " + this.getBBlockWCET());
    	
    	iterator = this.getPredecessorBasicBlockIterator();
    	System.out.print("    Predecessors: ");
    	while (iterator.hasNext() == true)
    	{
    		bBlockID = iterator.next();
    		basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
    		System.out.print("(" + basicBlock.getBBlockID() + "," + basicBlock.getBBlockName()  + "," + basicBlock.getBBlockWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getSuccessorBasicBlockIterator();
    	System.out.print("    Successors: ");
    	while (iterator.hasNext() == true)
    	{
    		bBlockID = iterator.next();
    		basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
    		System.out.print("(" + basicBlock.getBBlockID() + "," + basicBlock.getBBlockName() + "," + basicBlock.getBBlockWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getPredecessorQListBasicBlockIterator();
    	System.out.print("    Predecessor Q List: ");
    	while (iterator.hasNext() == true)
    	{
    		bBlockID = iterator.next();
    		basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
    		System.out.print("(" + basicBlock.getBBlockID() + "," + basicBlock.getBBlockName() + "," + basicBlock.getBBlockWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getSuccessorQListBasicBlockIterator();
    	System.out.print("    Successor Q List: ");
    	while (iterator.hasNext() == true)
    	{
    		bBlockID = iterator.next();
    		basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
    		System.out.print("(" + basicBlock.getBBlockID() + "," + basicBlock.getBBlockName()  + "," + basicBlock.getBBlockWCET() + ") ");
    	}
    	System.out.println();    	
    }
    
	/**
	 * Displays information about all basic block objects. 
	 *
	 * @see           JCBasicBlock
	 */
    public static void displayAllObjects()
    {
	    int            bBlockID;
    	JCBasicBlock   currentBasicBlock;
		
    	System.out.println("The number of basic blocks is " + _graphNodes.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (bBlockID = 0; bBlockID < _graphNodes.size(); bBlockID++)
		{
			currentBasicBlock = JCBasicBlock.getBasicBlock(bBlockID);
			currentBasicBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}