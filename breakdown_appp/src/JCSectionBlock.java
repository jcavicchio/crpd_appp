import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * JCSectionBlock is a fundamental class whose purpose is to contain
 * a contiguous linear section of basic block objects contained in a
 * control flow graph for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCSectionBlock extends JCSubBlock
{
	private static boolean _debugPredSuccLists = false;
	private static boolean _debugComputeSectionSolution = false;
	private static boolean _debugAddCostSolution = false; // JCC
	private static boolean _debugCostComparison = false;
	private static boolean _debugCostMapSolution = false;
	private static boolean _debugDisplaySectionCostMatrices = false;
	private static boolean _debugNextBasicBlockPair = false;
	private static boolean _debugOldSolutionCheck = false;
	private static boolean _displaySectionSolutions = false; // JCC2
	private static boolean _debugSolutionCheck = false;
	private static boolean _debugSolutionPreemptionPoints = false;
	private static boolean _debugStartEndBasicBlock = false;
	private static boolean _debugVisiblePreemptionPoints = false;
	private static boolean _debugOldPreemptionSolution = false;
	private static boolean _debugOldPreemptionSolutionCheck = false;
	private static boolean _debugMaximumPreemptionCost = false;
	private static boolean _debugMatrixEntries = false;
	private static boolean _debugInvalidMatrixEntries = false;
	private static boolean _debugFeasibleSolutions = false;
	private static boolean _debugSectionPreemptionMatrices = false;
	private static boolean _debugAddMinimumCostSolution = false;  // JCC
	private static boolean _debugDisplayPreemptionSectionSolutions = false;
	private static boolean _debugDisplayPreemptionSectionNumberOfSolutions = false; // JCC2
	private static boolean _processNoPreemptionPointSolutions = true; // JCC3
	
    private static int _nextID = 0;

	private static int _sbCount = 0;                     // Counter to count which section it is
	private static ArrayList<JCSectionBlock> _sectionBlocks = 
			          new ArrayList<JCSectionBlock>();   // Section block structure of the graph

	private static int _q;                               // Maximum non preemptive region parameter

	private int _sectionBlockID;
	private int _sectionNPRWCET;
    private ArrayList<Integer> _sectionBasicBlocks;
    private ArrayList<Integer> _predecessorSectionBlocks;
    private ArrayList<Integer> _successorSectionBlocks;
    private ArrayList<Integer> _predecessorQListSectionBlocks;
    private ArrayList<Integer> _successorQListSectionBlocks;
    private int _cnpMatrixID;
    private int _qMatrixID;
    private int _bMatrixID;
    private int _ppMatrixID;
    
	/**
	 * Default constructor.
	 */
	JCSectionBlock()
	{
		super();
		_sectionBlockID = _nextID;
		_nextID++;
		_sbCount = _nextID;
		_sectionNPRWCET = 0;
		_sectionBasicBlocks = new ArrayList<Integer>();
		_predecessorSectionBlocks = new ArrayList<Integer>();
		_successorSectionBlocks = new ArrayList<Integer>();
		_predecessorQListSectionBlocks = new ArrayList<Integer>();
		_successorQListSectionBlocks = new ArrayList<Integer>();
		setSectionBlock(_sectionBlockID, this);
		setStartingSectionID(_sectionBlockID);
		setEndingSectionID(_sectionBlockID);
	    _cnpMatrixID = -1;
	    _qMatrixID = -1;
	    _bMatrixID = -1;
	    _ppMatrixID = -1;
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCSectionBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_sbCount = 0;
		_sectionBlocks = new ArrayList<JCSectionBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the section block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return sectionBlockID   the identifier of this section block object
	 * 
	 * @see                     JCSectionBlock
	 */
	public int getSectionBlockID()
	{
		return _sectionBlockID;
	}

	/**
	 * Returns the string name of the section block object. 
	 *
	 * @return ID     the string name of this section block object
	 * 
	 * @see           JCSectionBlock
	 */
	public String getSectionBlockName()
	{
		String id = "JCSectionBlock" + _sectionBlockID;
		
		return id;
	}
	
	/**
	 * Returns the worst case non-preemptive execution time of the section
	 * block object. 
	 *
	 * @return _sectionNPRWCET   the non-preemptive worst case execution time of 
	 *                           this section block object
	 * 
	 * @see                      JCBasicBlock
	 * @see                      JCSectionBlock
	 */
	public int getSectionNPRWCET()
	{
		return _sectionNPRWCET;
	}

	/**
	 * Stores the section block object at the specified location in
	 * the section block array. 
	 *
	 * @param  sectionBlockID    the identifier of the stored section block object
	 * 
	 * @param  sectionBlock      the section block object to be stored
	 * 
	 * @see                      JCSectionBlock
	 */
	public static void setSectionBlock(int sectionBlockID, JCSectionBlock sectionBlock)
	{
		if (sectionBlockID < _sbCount)
		{
			_sectionBlocks.add(sectionBlock);
		}
	}
	
	/**
	 * Returns the section block object associated with the specified identifier. 
	 *
	 * @return sectionBlock  the section block object stored
	 * 
	 * @see                  JCSectionBlock
	 */
	public static JCSectionBlock getSectionBlock(int sectionBlockID)
	{
		if (sectionBlockID < _sbCount)
		{
	        return _sectionBlocks.get(sectionBlockID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets the size of the maximum non-preemptive region for 
	 * the current control flow graph. 
	 *
	 * @return  Q    the maximum non-preemptive region for this CFG
	 * 
	 * @see          JCSectionBlock
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
	 * @see         JCSectionBlock
	 */
	public static void setQValue(int Q)
	{
		_q = Q;
	}
	
	/**
	 * Adds the basic block object to this section block object. 
	 *
	 * @param  basicBlock  the basic block object to add to this section
	 *                     block object
	 *               
	 * @see          JCBasicBlock
	 * @see          JCSectionBlock
	 */
	public void addBasicBlock(JCBasicBlock basicBlock)
	{
		if (_sectionBasicBlocks.add(basicBlock.getBBlockID()) != true)
		{
			System.out.println("JCSectionBlock: Error adding basic block " + basicBlock.getBBlockName());
		}
		basicBlock.setSectionBlock(this);
		_sectionNPRWCET += basicBlock.getBBlockWCET();
	}
	
	/**
	 * Removes the basic block object from this section block object. 
	 *
	 * @param  basicBlock  the basic block object to remove from this
	 *                     section block object
	 *               
	 * @see          JCBasicBlock
	 * @see          JCSectionBlock
	 */
	public void removeBasicBlock(JCBasicBlock basicBlock)
	{
		if (_sectionBasicBlocks.remove((Integer)basicBlock.getBBlockID()) != true)
		{
			System.out.println("JCSectionBlock: Error removing basic block " + basicBlock.getBBlockName());
		}
		basicBlock.setSectionBlock(null);
		_sectionNPRWCET -= basicBlock.getBBlockWCET();
	}
	
	/**
	 * Determines if the specified basic block has a containing relationship to
	 * this section block object. 
	 *
	 * @param  basicBlock     the basic block object to check for a containing
	 *                        relationship to this section block object
	 *               
	 * @return hasBasicBlock  true/false indicating is the specified basic block
	 *                        object is contained in this section block
	 *                        
	 * @see                JCBasicBlock
	 */
	public boolean hasBasicBlock(JCBasicBlock basicBlock)
	{
		return _sectionBasicBlocks.contains(basicBlock.getBBlockID());
	}
	
	/**
	 * Determines if the specified basic block has a containing relationship to
	 * this section block object. 
	 *
	 * @param  basicBlockID   the basic block object to check for a containing
	 *                        relationship to this section block object
	 *               
	 * @return hasBasicBlock  true/false indicating is the specified basic block
	 *                        object is contained in this section block
	 *                        
	 * @see                JCBasicBlock
	 */
	public boolean hasBasicBlock(Integer basicBlockID)
	{
		return _sectionBasicBlocks.contains(basicBlockID);
	}

	/**
	 * Finds the basic block object from within this section block object
	 * with the specified block name. 
	 *
	 * @param  basicBlockName  the string name of the basic block object to search for
	 *               
	 * @return basicBlock      the found basic block object in this section block
	 *                         object
	 *               
	 * @see                    JCBasicBlock
	 * @see                    JCSectionBlock
	 */
	public JCBasicBlock findBasicBlock(String basicBlockName)
	{
	    JCBasicBlock basicBlock = null;
		JCBasicBlock currentBasicBlock;
		Integer currentBBID;
	    String currentBasicBlockName;
	    Iterator<Integer> iterator;
	    
	    if (basicBlockName != null)
	    {
		    iterator = getBasicBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (basicBlockName == null))
		        {
		        	currentBBID = iterator.next();
		        	currentBasicBlock = JCBasicBlock.getBasicBlock(currentBBID);
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
	 * Returns the basic block object at the specified index in this section block object.
	 *
	 * @param  bbIndex         the index of the basic block object to return
	 *               
	 * @return basicBlock      the found basic block object in this section block
	 *                         object
	 *               
	 * @see                    JCBasicBlock
	 * @see                    JCSectionBlock
	 */
	public JCBasicBlock getBasicBlockAtIndex(int bbIndex)
	{
		JCBasicBlock basicBlock;
		int bBlockID;
		
		bBlockID = _sectionBasicBlocks.get(bbIndex);
		basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
		
		return basicBlock;
	}
	
	/**
	 * Returns the number of basic block objects in this section block 
	 * object. 
	 *
	 * @return numberOfBasicBlocks  the number of basic blocks contained in this 
	 *                              section block object
	 *                        
	 * @see                   JCBasicBlock
	 * @see                   JCSectionBlock
	 */
	public long numberOfBasicBlocks()
	{
		return _sectionBasicBlocks.size();
	}
	
	/**
	 * Returns the basic block iterator for accessing the basic block objects 
	 * in this section block object. 
	 *
	 * @return iterator  the basic block iterator for accessing the 
	 *                   basic block objects
	 *                        
	 * @see           JCBasicBlock
	 * @see           JCSectionBlock
	 */
	public Iterator<Integer> getBasicBlockIterator()
	{
		return _sectionBasicBlocks.iterator();
	}
	
	/**
	 * Returns the left most basic block object contained in this section block object. 
	 *
	 * @return leftBasicBlock  the left most basic block object in this section block
	 *                         object
	 *               
	 * @see                    JCBasicBlock
	 * @see                    JCSectionBlock
	 */
	public JCBasicBlock getLeftMostBasicBlock()
	{
		JCBasicBlock leftBasicBlock = null;
		int leftBasicBlockID;
		
		if (numberOfBasicBlocks() > 0)
		{
			leftBasicBlockID = _sectionBasicBlocks.get(0);
			leftBasicBlock = JCBasicBlock.getBasicBlock(leftBasicBlockID);
		}
		
		return leftBasicBlock;
	}
	
	/**
	 * Returns the right most basic block object contained in this section block object. 
	 *
	 * @return rightBasicBlock  the right most basic block object in this section block
	 *                          object
	 *               
	 * @see                     JCBasicBlock
	 * @see                     JCSectionBlock
	 */
	public JCBasicBlock getRightMostBasicBlock()
	{
		JCBasicBlock rightBasicBlock = null;
		int rightBasicBlockID;
		int rightBasicBlockIndex;
		
		if (numberOfBasicBlocks() > 0)
		{
			rightBasicBlockIndex = (_sectionBasicBlocks.size() - 1);
			rightBasicBlockID = _sectionBasicBlocks.get(rightBasicBlockIndex);
			rightBasicBlock = JCBasicBlock.getBasicBlock(rightBasicBlockID);
		}
		
		return rightBasicBlock;
	}
	
	/**
	 * Updates the predecessor basic block relationships from this section block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block predecessor
	 *                   contained within
	 *               
	 * @see              JCSectionBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updatePredecessorBasicBlocks(JCSubBlock subBlock)
	{
		JCBasicBlock   leftMostBasicBlock;
		JCSectionBlock prevSection;
		JCBasicBlock   rightMostBasicBlock;
		
		if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			prevSection = (JCSectionBlock)subBlock;
			rightMostBasicBlock = prevSection.getRightMostBasicBlock();
			leftMostBasicBlock = getLeftMostBasicBlock();
			rightMostBasicBlock.addSuccessorBasicBlock(leftMostBasicBlock);
			leftMostBasicBlock.addPredecessorBasicBlock(rightMostBasicBlock);
			
			leftMostBasicBlock.updatePredecessorQListBasicBlocks(rightMostBasicBlock);

			updatePredecessorQListBasicBlocks();
		}
		else
		{
			subBlock.updateSuccessorBasicBlocks(this);
		}
	}
	
	/**
	 * Updates the successor basic block relationships from this section block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block successors
	 *                   contained within
	 *               
	 * @see              JCSectionBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updateSuccessorBasicBlocks(JCSubBlock subBlock)
	{
		JCBasicBlock   leftMostBasicBlock;
		JCSectionBlock nextSection;
		JCBasicBlock   rightMostBasicBlock;
		
		if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			nextSection = (JCSectionBlock)subBlock;
			leftMostBasicBlock = nextSection.getLeftMostBasicBlock();
			rightMostBasicBlock = getRightMostBasicBlock();
			rightMostBasicBlock.addSuccessorBasicBlock(leftMostBasicBlock);
			leftMostBasicBlock.addPredecessorBasicBlock(rightMostBasicBlock);
			
			leftMostBasicBlock.updatePredecessorQListBasicBlocks(rightMostBasicBlock);

			nextSection.updatePredecessorQListBasicBlocks();
		}
		else
		{
			subBlock.updatePredecessorBasicBlocks(this);
		}
	}
	
	/**
	 * Updates the Q list predecessor basic block relationships contained in 
	 * this section block object. 
	 *
	 * @see              JCSectionBlock
	 */
	public void updatePredecessorQListBasicBlocks()
	{
		int basicBlockID;
		Iterator<Integer> iterator;
		JCBasicBlock predecessorBasicBlock = null;
		JCBasicBlock successorBasicBlock = null;

		iterator = getBasicBlockIterator();

		// Iterate through all previous section entries.
		while (iterator.hasNext() == true)
		{
			basicBlockID = iterator.next();
			successorBasicBlock = JCBasicBlock.getBasicBlock(basicBlockID);
			if ((predecessorBasicBlock != null) && (successorBasicBlock != null))
			{
				successorBasicBlock.updatePredecessorQListBasicBlocks(predecessorBasicBlock);
			}
			predecessorBasicBlock = successorBasicBlock;
		}		
	}
	
	/**
	 * Adds the section block object as a predecessor of this section block object. 
	 *
	 * @param  sectionBlock  the section block object to add to this section block
	 *                       object's predecessor list
	 *               
	 * @see                  JCSectionBlock
	 */
	public void addPredecessorSectionBlock(JCSectionBlock sectionBlock)
	{
		int sectionBlockID;
		
		sectionBlockID = sectionBlock.getSectionBlockID();
		if (_predecessorSectionBlocks.contains(sectionBlockID) == false)
		{
			if (_predecessorSectionBlocks.add(sectionBlock.getSectionBlockID()) != true)
			{
				System.out.println("JCSectionBlock: Error adding predecessor section block " + sectionBlock.getSectionBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Section block (" + sectionBlock.getSectionBlockID() + ") ID: " + sectionBlock.getSectionBlockName() + " added as a predecessor to section block (" + this.getSectionBlockID() + ") ID: " + this.getSectionBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the predecessor section block object from this section block object. 
	 *
	 * @param  sectionBlock  the section block object to remove from this section block
	 *                       object
	 *               
	 * @see                  JCSectionBlock
	 */
	public void removePredecessorSectionBlock(JCSectionBlock sectionBlock)
	{
		if (_predecessorSectionBlocks.remove((Integer)sectionBlock.getSectionBlockID()) != true)
		{
			System.out.println("JCSectionBlock: Error removing predecessor section block " + sectionBlock.getSectionBlockName());
		}
	}
	
	/**
	 * Finds the predecessor section block object from within this section block object
	 * with the specified section block name. 
	 *
	 * @param  sectionBlockName  the string name of the predecessor section block object 
	 *                           to search for
	 *               
	 * @return sectionBlock      the found predecessor section block object in this section block
	 *                           object
	 *               
	 * @see                      JCSectionBlock
	 */
	public JCSectionBlock findPredecessorSectionBlock(String sectionBlockName)
	{
		JCSectionBlock sectionBlock = null;
		JCSectionBlock currentSectionBlock;
		Integer currentSBID;
	    String currentSectionBlockName;
	    Iterator<Integer> iterator;
	    
	    if (sectionBlockName != null)
	    {
		    iterator = getPredecessorSectionBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (sectionBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentSectionBlock = JCSectionBlock.getSectionBlock(currentSBID);
		        	currentSectionBlockName = currentSectionBlock.getSectionBlockName();
		        	if (currentSectionBlock != null)
		        	{
		        		if (currentSectionBlockName.compareTo(sectionBlockName) == 0)
		        		{
		        			sectionBlock = currentSectionBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return sectionBlock;
	}
	
	/**
	 * Returns the number of predecessor section block objects in this section block 
	 * object. 
	 *
	 * @return numberOfPredecessorSectionBlocks  the number of predecessor section blocks 
	 *                                           contained in this section block object
	 *                        
	 * @see                                      JCSectionBlock
	 */
	public long numberOfPredecessorSectionBlocks()
	{
		return _predecessorSectionBlocks.size();
	}
	
	/**
	 * Returns the predecessor section block iterator for accessing the 
	 * predecessor section block objects in this section block object. 
	 *
	 * @return iterator  the predecessor section block iterator for 
	 *                   accessing the predecessor section block objects
	 *                        
	 * @see              JCSectionBlock
	 */
	public Iterator<Integer> getPredecessorSectionBlockIterator()
	{
		return _predecessorSectionBlocks.iterator();
	}
	
	/**
	 * Adds the section block object as a successor of this section block object. 
	 *
	 * @param  sectionBlock  the section block object to add to this section block
	 *                       object's successor list
	 *               
	 * @see                  JCSectionBlock
	 */
	public void addSuccessorSectionBlock(JCSectionBlock sectionBlock)
	{
		int sectionBlockID;
		
		sectionBlockID = sectionBlock.getSectionBlockID();
		if (_successorSectionBlocks.contains(sectionBlockID) == false)
		{
			if (_successorSectionBlocks.add(sectionBlock.getSectionBlockID()) != true)
			{
				System.out.println("JCSectionBlock: Error adding successor section block " + sectionBlock.getSectionBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Section block (" + sectionBlock.getSectionBlockID() + ") ID: " + sectionBlock.getSectionBlockName() + " added as a successor to section block (" + this.getSectionBlockID() + ") ID: " + this.getSectionBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the successor section block object from this section block object. 
	 *
	 * @param  sectionBlock  the section block object to add to this section block
	 *                       object
	 *               
	 * @see                JCSectionBlock
	 */
	public void removeSuccessorSectionBlock(JCSectionBlock sectionBlock)
	{
		if (_successorSectionBlocks.remove((Integer)sectionBlock.getSectionBlockID()) != true)
		{
			System.out.println("JCSectionBlock: Error removing successor section block " + sectionBlock.getSectionBlockName());
		}
	}
	
	/**
	 * Finds the successor section block object from within this section block object
	 * with the specified section block name. 
	 *
	 * @param  sectionBlockName  the string name of the predecessor section block object 
	 *                           to search for
	 *               
	 * @return sectionBlock      the found predecessor section block object in this section block
	 *                           object
	 *               
	 * @see                      JCSectionBlock
	 */
	public JCSectionBlock findSuccessorSectionBlock(String sectionBlockName)
	{
		JCSectionBlock sectionBlock = null;
		JCSectionBlock currentSectionBlock;
		Integer currentSBID;
	    String currentSectionBlockName;
	    Iterator<Integer> iterator;
	    
	    if (sectionBlockName != null)
	    {
		    iterator = getSuccessorSectionBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (sectionBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentSectionBlock = JCSectionBlock.getSectionBlock(currentSBID);
		        	currentSectionBlockName = currentSectionBlock.getSectionBlockName();
		        	if (currentSectionBlock != null)
		        	{
		        		if (currentSectionBlockName.compareTo(sectionBlockName) == 0)
		        		{
		        			sectionBlock = currentSectionBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return sectionBlock;
	}
	
	/**
	 * Returns the number of successor section block objects in this section block 
	 * object. 
	 *
	 * @return numberOfSuccessorSectionBlocks  the number of successor section blocks 
	 *                                         contained in this section block object
	 *                        
	 * @see                                    JCSectionBlock
	 */
	public long numberOfSuccessorSectionBlocks()
	{
		return _successorSectionBlocks.size();
	}
	
	/**
	 * Returns the successor section block iterator for accessing the 
	 * successor section block objects in this section block object. 
	 *
	 * @return iterator  the successor section block iterator for 
	 *                   accessing the successor section block objects
	 *                        
	 * @see              JCSectionBlock
	 */
	public Iterator<Integer> getSuccessorSectionBlockIterator()
	{
		return _successorSectionBlocks.iterator();
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
		JCSectionBlock prevSection;
		JCSelectOptimalPP selectOptimalPP;
		
		if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			prevSection = (JCSectionBlock)subBlock;
			this.addPredecessorSectionBlock(prevSection);
			prevSection.addSuccessorSectionBlock(this);
			
			updatePredecessorQListSectionBlocks(prevSection);
			
			selectOptimalPP = JCSelectOptimalPP.getLatestSelectOptimalPP();
			//selectOptimalPP.computeAdjacentSectionCosts(prevSection, this);
		}
		else
		{
			subBlock.updateSuccessorSectionBlocks(this);
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
		JCSectionBlock nextSection;
		JCSelectOptimalPP selectOptimalPP;
		
		if (subBlock.getObjectTypeName().equals("JCSectionBlock") == true)
		{
			nextSection = (JCSectionBlock)subBlock;
			this.addSuccessorSectionBlock(nextSection);
			nextSection.addPredecessorSectionBlock(this);
			
			nextSection.updatePredecessorQListSectionBlocks(this);

			selectOptimalPP = JCSelectOptimalPP.getLatestSelectOptimalPP();
			//selectOptimalPP.computeAdjacentSectionCosts(this, nextSection);
		}
		else
		{
			subBlock.updatePredecessorSectionBlocks(this);
		}
	}
	
	/**
	 * Adds the section block object as a Q list predecessor of this section block object. 
	 *
	 * @param  sectionBlock  the section block object to add to this section block
	 *                       object's Q list
	 *               
	 * @see                  JCSectionBlock
	 */
	public void addPredecessorQListSectionBlock(JCSectionBlock sectionBlock)
	{
		int sectionBlockID;
		
		sectionBlockID = sectionBlock.getSectionBlockID();
		if (_predecessorQListSectionBlocks.contains(sectionBlockID) == false)
		{
			if (_predecessorQListSectionBlocks.add(sectionBlock.getSectionBlockID()) != true)
			{
				System.out.println("JCSectionBlock: Error adding predecessor Q list section block " + sectionBlock.getSectionBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Section block (" + sectionBlock.getSectionBlockID() + ") ID: " + sectionBlock.getSectionBlockName() + " added as a Q list predecessor to section block (" + this.getSectionBlockID() + ") ID: " + this.getSectionBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the Q list predecessor section block object from this section block object. 
	 *
	 * @param  sectionBlock  the section block object to remove from this section block
	 *                       object's predecessor Q list
	 *               
	 * @see                  JCSectionBlock
	 */
	public void removePredecessorQListSectionBlock(JCSectionBlock sectionBlock)
	{
		if (_predecessorQListSectionBlocks.remove((Integer)sectionBlock.getSectionBlockID()) != true)
		{
			System.out.println("JCSectionBlock: Error removing predecessor Q list section block " + sectionBlock.getSectionBlockName());
		}
	}
	
	/**
	 * Finds the Q list predecessor section block object from within this section block object
	 * with the specified section block name. 
	 *
	 * @param  sectionBlockName  the string name of the predecessor Q list section block object 
	 *                           to search for
	 *               
	 * @return sectionBlock      the found predecessor Q list section block object in this section 
	 *                           block object
	 *               
	 * @see                      JCSectionBlock
	 */
	public JCSectionBlock findPredecessorQListSectionBlock(String sectionBlockName)
	{
		JCSectionBlock sectionBlock = null;
		JCSectionBlock currentSectionBlock;
		Integer currentSBID;
	    String currentSectionBlockName;
	    Iterator<Integer> iterator;
	    
	    if (sectionBlockName != null)
	    {
		    iterator = getPredecessorQListSectionBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (sectionBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentSectionBlock = JCSectionBlock.getSectionBlock(currentSBID);
		        	currentSectionBlockName = currentSectionBlock.getSectionBlockName();
		        	if (currentSectionBlock != null)
		        	{
		        		if (currentSectionBlockName.compareTo(sectionBlockName) == 0)
		        		{
		        			sectionBlock = currentSectionBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return sectionBlock;
	}
	
	/**
	 * Returns the number of predecessor Q list section block objects in this section block 
	 * object. 
	 *
	 * @return numberOfQListPredecessorSectionBlocks  the number of predecessor section blocks 
	 *                                                contained in this section block object
	 *                        
	 * @see                                      JCSectionBlock
	 */
	public long numberOfQListPredecessorSectionBlocks()
	{
		return _predecessorQListSectionBlocks.size();
	}
	
	/**
	 * Returns the predecessor Q list section block iterator for accessing the 
	 * predecessor Q list section block objects in this section block object. 
	 *
	 * @return iterator  the predecessor section block iterator for 
	 *                   accessing the predecessor section block objects
	 *                        
	 * @see              JCSectionBlock
	 */
	public Iterator<Integer> getPredecessorQListSectionBlockIterator()
	{
		return _predecessorQListSectionBlocks.iterator();
	}
	
	/**
	 * Adds the section block object as a Q list successor of this section block object. 
	 *
	 * @param  sectionBlock  the section block object to add to this secction block
	 *                       object's Q list successors.
	 *               
	 * @see                  JCSectionBlock
	 */
	public void addSuccessorQListSectionBlock(JCSectionBlock sectionBlock)
	{
		int sectionBlockID;
		
		sectionBlockID = sectionBlock.getSectionBlockID();
		if (_successorQListSectionBlocks.contains(sectionBlockID) == false)
		{
			if (_successorQListSectionBlocks.add(sectionBlock.getSectionBlockID()) != true)
			{
				System.out.println("JCSectionBlock: Error adding successor Q list section block " + sectionBlock.getSectionBlockName());
			}
			else
			{
				if (_debugPredSuccLists == true)
				{
					System.out.println("Section block (" + sectionBlock.getSectionBlockID() + ") ID: " + sectionBlock.getSectionBlockName() + " added as a Q list successor to section block (" + this.getSectionBlockID() + ") ID: " + this.getSectionBlockName());
				}
			}
		}
	}
	
	/**
	 * Removes the Q list successor section block object from this section block object. 
	 *
	 * @param  sectionBlock  the section block object to add to this section block
	 *                       object
	 *               
	 * @see                  JCSectionBlock
	 */
	public void removeSuccessorQListSectionBlock(JCSectionBlock sectionBlock)
	{
		if (_successorQListSectionBlocks.remove((Integer)sectionBlock.getSectionBlockID()) != true)
		{
			System.out.println("JCSectionBlock: Error removing successor Q list section block " + sectionBlock.getSectionBlockName());
		}
	}
	
	/**
	 * Finds the successor Q list section block object from within this section block object
	 * with the specified section block name. 
	 *
	 * @param  sectionBlockName  the string name of the successor section block object 
	 *                           to search for
	 *               
	 * @return sectionBlock      the found successor section block object in this section block
	 *                           object
	 *               
	 * @see                      JCSectionBlock
	 */
	public JCSectionBlock findSuccessorQListSectionBlock(String sectionBlockName)
	{
		JCSectionBlock sectionBlock = null;
		JCSectionBlock currentSectionBlock;
		Integer currentSBID;
	    String currentSectionBlockName;
	    Iterator<Integer> iterator;
	    
	    if (sectionBlockName != null)
	    {
		    iterator = getSuccessorQListSectionBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (sectionBlockName == null))
		        {
		        	currentSBID = iterator.next();
		        	currentSectionBlock = JCSectionBlock.getSectionBlock(currentSBID);
		        	currentSectionBlockName = currentSectionBlock.getSectionBlockName();
		        	if (currentSectionBlock != null)
		        	{
		        		if (currentSectionBlockName.compareTo(sectionBlockName) == 0)
		        		{
		        			sectionBlock = currentSectionBlock;
		        		}
		        	}
		        }
		    }
	    }
	    return sectionBlock;
	}
	
	/**
	 * Returns the number of successor Q list section block objects in this section block 
	 * object. 
	 *
	 * @return numberOfSuccessorSectionBlocks  the number of successor section blocks 
	 *                                         contained in this section block object
	 *                        
	 * @see                                    JCSectionBlock
	 */
	public long numberOfSuccessorQListSectionBlocks()
	{
		return _successorQListSectionBlocks.size();
	}
	
	/**
	 * Returns the successor Q list section block iterator for accessing the 
	 * successor Q list section block objects in this section block object. 
	 *
	 * @return iterator  the successor section block iterator for 
	 *                   accessing the successor section block objects
	 *                        
	 * @see              JCSectionBlock
	 */
	public Iterator<Integer> getSuccessorQListSectionBlockIterator()
	{
		return _successorQListSectionBlocks.iterator();
	}
	
	/**
	 * Updates the predecessor Q list section block relationships starting from this 
	 * section object. 
	 *
	 * @param  prevSection  the section object to update the Q list predecessor
	 *                      section block contained within
	 *               
	 * @see              JCSectionBlock
	 */
	public void updatePredecessorQListSectionBlocks(JCSectionBlock prevSection)
	{
		final boolean debugDFSAlgorithm = false;
		
		JCSectionBlock currentSection;
		Iterator<Integer> iterator;
		Stack<Iterator<Integer>> iteratorStack = new Stack<Iterator<Integer>>();
		JCSectionBlock predecessorSection;
		int sectionBlockID;
		int sectionWCETLength = 0;
		Stack<JCSectionBlock> sectionStack = new Stack<JCSectionBlock>();
		Stack<Integer> wcetStack = new Stack<Integer>();
		
		// Update the Q list entries.
		this.addPredecessorQListSectionBlock(prevSection);
		prevSection.addSuccessorQListSectionBlock(this);
		sectionWCETLength = prevSection.getSectionNPRWCET();
		
		if (sectionWCETLength < JCSectionBlock._q)
		{
			// First time through setup the stacks with the first as if its the previous.
			iterator = prevSection.getPredecessorSectionBlockIterator();
			iteratorStack.push(iterator);
			sectionStack.push(prevSection);
			wcetStack.push(sectionWCETLength);
			
			while (iteratorStack.empty() == false)
			{
				// Return to the successor section block to continue depth first traversal.
				iterator = iteratorStack.pop();
				currentSection = sectionStack.pop();
				sectionWCETLength = wcetStack.pop();
				if (debugDFSAlgorithm == true)
				{
					System.out.println("updatePredecessorQListSectionBlocks: Processing section " + currentSection.getSectionBlockName() + ":");
				}
				
				// Iterate through all previous section entries.
				while (iterator.hasNext() == true)
				{
					sectionBlockID = iterator.next();
					predecessorSection = JCSectionBlock.getSectionBlock(sectionBlockID);
					if (debugDFSAlgorithm == true)
					{
						System.out.println("updatePredecessorQListSectionBlocks: Next predecessor section " + predecessorSection.getSectionBlockName() + ":");
					}
                    
					// Check to see if we've used up our Q window budget.
					if (sectionWCETLength < JCSectionBlock._q)
					{
						// Update the Q list entries.
						this.addPredecessorQListSectionBlock(predecessorSection);
						predecessorSection.addSuccessorQListSectionBlock(this);
						
						// Push the information about the current section onto the stacks.
						iteratorStack.push(iterator);
						sectionStack.push(predecessorSection);
						sectionWCETLength += predecessorSection.getSectionNPRWCET();
						wcetStack.push(sectionWCETLength);
						
						// Set the depth first predecessor section for the next pass.
						iterator = predecessorSection.getPredecessorSectionBlockIterator();
						currentSection = predecessorSection;
						if (debugDFSAlgorithm == true)
						{
							System.out.println("updatePredecessorQListSectionBlocks: Processing predecessor section " + currentSection.getSectionBlockName() + ":");
						}
					}
				}
			}
		}
	}
	
	/**
	 * Displays the computed preemption points solution.
	 *
	 * @see       JCCostKey
	 * @see       JCCostSolution
	 * @see       JCPreemptionPoints
	 * @see       JCSectionBlock
	 */
	public void displaySectionSolution()
	{
		JCCostKey minimumCostKey;
		JCCostSolution minimumCostSolution;
		ArrayList<JCCostSolution> minimumCostSolutionList;
		Iterator<JCCostSolution> minimumCostSolutionIterator;
		JCPreemptionPoints minimumCostPreemptionPoints;
		Iterator<JCPreemptionPoints> minimumPreemptionPointsIterator;
		JCSectionBlock sectionBlock;
		
		sectionBlock = this;
		minimumCostSolutionList = sectionBlock.getSolutionMap();
    	System.out.println("    JCSectionBlock: Number of minimum cost solutions for section " + sectionBlock.getSectionBlockName() + " is " + minimumCostSolutionList.size());
		minimumCostSolutionIterator = minimumCostSolutionList.iterator();
		while (minimumCostSolutionIterator.hasNext() == true)
		{
			minimumCostSolution = minimumCostSolutionIterator.next();
			minimumCostKey = minimumCostSolution.getSolutionKey();
	    	System.out.print("        JCSectionBlock: Minimum cost solution (" + minimumCostKey.getLeftIndex() + "," + minimumCostKey.getRightIndex() + "," + minimumCostKey.getLeftBasicBlock() + "," + minimumCostKey.getRightBasicBlock() + ") is " + minimumCostSolution.getSolutionCost());

	    	System.out.print(" Preemption points ");
			minimumPreemptionPointsIterator = minimumCostSolution.getPreemptionPointSolutionsIterator();
			while (minimumPreemptionPointsIterator.hasNext() == true)
			{
				minimumCostPreemptionPoints = minimumPreemptionPointsIterator.next();
				minimumCostPreemptionPoints.displayObjectInformation();
				System.out.print(" ");
			}
	       	System.out.println();
		}
    }
	
	/**
	 * Computes the potential preemption point solutions for the specified basic
	 * block object. 
	 *
	 * @param  leftmostBasicBlock   the leftmost basic block object in this specific
	 *                              section block
	 *               
	 * @param  successorBasicBlock  the basic block object to compute the preemption cost
	 *                              solution added as a successor to the current solution
	 *               
	 * @param  pcmMatrix            the preemption cost matrix used to compute the preemption cost
	 *                              solution
	 *               
	 * @see                         JCBasicBlock
	 * @see                         JCPreemptionCostMatrix
	 * @see                         JCSectionBlock
	 */
	public void computeBasicBlockSolution(JCBasicBlock leftmostBasicBlock, JCBasicBlock successorBasicBlock, JCPreemptionCostMatrix pcmMatrix)
	{
		int endingBasicBlockID;
		JCBasicBlock leftBasicBlock;
		int leftNPRValue;
		int maximumPreemptionCost;
		int newRightNPRValue;
		int preemptionCost;
		JCBasicBlock rightBasicBlock;
		int rightNPRValue;
		JCBasicBlock visiblePPBasicBlock;
		ArrayList<Integer> visiblePreemptionPoints;
		JCPreemptionPoints WOPPreemptionPoints;
		JCCostKey WOPCostKey;
		JCCostSolution WOPCostSolution = null;
		JCCostKey WPCostKey;
		JCCostSolution WPCostSolution = null;
		
        if (_debugStartEndBasicBlock == true)
        {
        	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " processing first basic block " + successorBasicBlock.getBBlockName() + " started");
        }

		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
		if (hasBasicBlock(endingBasicBlockID) == true)
		{
	        leftNPRValue = 0;
		}
		else
		{
			leftNPRValue = successorBasicBlock.getBBlockWCET();
		}
        leftBasicBlock = leftmostBasicBlock;
        
        rightNPRValue = successorBasicBlock.getBBlockWCET();
        rightBasicBlock = successorBasicBlock;
        
    	if (_debugNextBasicBlockPair == true)
        {
        	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " processing next left basic block " + leftBasicBlock.getBBlockName() + " processing next right basic block " + rightBasicBlock.getBBlockName());
        }

        if (_debugSolutionCheck == true)
        {
        	System.out.println("    JCSectionBlock: considering WOP solution for (" + leftNPRValue + "," + rightNPRValue + ") Right BB " + rightBasicBlock.getBBlockName() + " Cost " + rightBasicBlock.getBBlockWCET());
        }

        // Check to see if the current solution is feasible.
    	//if (successorBasicBlock.isFeasibleInclusive(leftNPRValue, rightNPRValue) == true)
    	//{
    		// Without preemption taken.
    		WOPCostKey = new JCCostKey(leftNPRValue, rightNPRValue);
    		WOPCostKey.setLeftBasicBlock(leftBasicBlock.getBBlockID());
    		WOPCostKey.setRightBasicBlock(rightBasicBlock.getBBlockID());
    		WOPPreemptionPoints = new JCPreemptionPoints(false);

    		WOPCostSolution = new JCCostSolution();
    		WOPCostSolution.setSolutionCost(successorBasicBlock.getBBlockWCET());
    		WOPCostSolution.setSolutionKey(WOPCostKey);
    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
    		if (hasBasicBlock(endingBasicBlockID) == true)
    		{
    			WOPPreemptionPoints.addPreemptionPoint(endingBasicBlockID);
    		}
    		
    		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
    		if (hasBasicBlock(endingBasicBlockID) == true)
    		{
    			WOPPreemptionPoints.addPreemptionPoint(endingBasicBlockID);
    		}
            
    		WOPCostSolution.combinePreemptionPoints(WOPPreemptionPoints);
    		
    		if (_debugAddCostSolution == true)
            {
            	System.out.print("    JCSectionBlock: adding WOP cost (" + leftNPRValue + "," + rightNPRValue  + "," + leftBasicBlock.getBBlockID() + "," + rightBasicBlock.getBBlockID() + ") = " + successorBasicBlock.getBBlockWCET() + " preemption points ");
            	WOPCostSolution.displayObjectInformation();
            	System.out.println();
            }
    		WOPCostSolution.addPreemptionPointSolution();
    		putCostSolution(WOPCostSolution, false);

    		// With preemption taken.
            maximumPreemptionCost = 0;
    		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
    		if (hasBasicBlock(endingBasicBlockID) == true)
    		{
                visiblePreemptionPoints = WOPCostSolution.getVisibleStartingPPs(this, WOPPreemptionPoints);
                if (_debugVisiblePreemptionPoints == true)
                {
                    System.out.print("    JCSectionBlock: Visible starting preemption points (size=" + visiblePreemptionPoints.size() + "): ");
                }
                
                if (visiblePreemptionPoints.size() > 0)
                {
        			for (Integer item : visiblePreemptionPoints) 
        			{
                		preemptionCost = pcmMatrix.getMatrixEntry(successorBasicBlock.getBBlockID(),
                                                                  item.intValue());
                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
                        
                        if (maximumPreemptionCost < preemptionCost)
                        {
                        	maximumPreemptionCost = preemptionCost;
                        }
    	                if (_debugVisiblePreemptionPoints == true)
    	                {
                            visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                    		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
    	                }
        			}
    			}

                if (_debugVisiblePreemptionPoints == true)
                {
                	System.out.println();
                }
			}
    	//}
            
    	newRightNPRValue = maximumPreemptionCost;
        WPCostKey = new JCCostKey(leftNPRValue, newRightNPRValue);
        WPCostKey.setLeftBasicBlock(leftBasicBlock.getBBlockID());
        WPCostKey.setRightBasicBlock(rightBasicBlock.getBBlockID());
        
        WPCostSolution = new JCCostSolution();
        WPCostSolution.setSolutionCost((successorBasicBlock.getBBlockWCET() + maximumPreemptionCost));
        WPCostSolution.setSolutionKey(WPCostKey);
		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
		if (hasBasicBlock(endingBasicBlockID) == true)
		{
			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
		}
		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
		if (hasBasicBlock(endingBasicBlockID) == true)
		{
			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
		}
		WPCostSolution.addPreemptionPoint(successorBasicBlock.getBBlockID());
        if (_debugAddCostSolution == true)
        {
        	System.out.print("    JCSectionBlock: adding WP cost (" + leftNPRValue + "," + newRightNPRValue + "," + leftBasicBlock.getBBlockID() + "," + rightBasicBlock.getBBlockID() + ") = " + (successorBasicBlock.getBBlockWCET() + maximumPreemptionCost) + " preemption points ");
        	WPCostSolution.displayObjectInformation();
        	System.out.println();
        }
		WPCostSolution.addPreemptionPointSolution();
		putCostSolution(WPCostSolution, false);
	
        if (_debugStartEndBasicBlock == true)
        {
        	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " processing first basic block " + successorBasicBlock.getBBlockName() + " completed");
        	System.out.println();
        }
	}		
	
	/**
	 * Computes the potential preemption point solutions for the specified basic
	 * block object. 
	 *
	 * @param  leftmostBasicBlock     the leftmost basic block object in this specific
	 *                                section block
	 *               
	 * @param  predecessorBasicBlock  the predecessor basic block object to the successor
	 *                                basic block
	 *               
	 * @param  successorBasicBlock    the basic block object to compute the preemption cost
	 *                                solution added as a successor to the current solution
	 *               
	 * @param  pcmMatrix              the preemption cost matrix used to compute the preemption cost
	 *                                solution
	 *               
	 * @see                           JCBasicBlock
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeBasicBlockSolution(JCBasicBlock leftmostBasicBlock, JCBasicBlock predecessorBasicBlock, JCBasicBlock successorBasicBlock, JCPreemptionCostMatrix pcmMatrix)
	{
		int costWOPreemption;
		int costWPreemption;
		int endingBasicBlockID;
		JCCostKey existingWOPCostKey;
		JCCostSolution existingWOPCostSolution;
		JCBasicBlock leftBasicBlock;
		Integer leftCostWOPEntry;
		Integer leftCostWPEntry;
		int leftNPRValue;
		Iterator<JCCostSolution> mapIterator;
		int maximumPreemptionCost;
		int newLeftWOPNPRValue;
		int newLeftWPNPRValue;
		int newRightNPRValue;
		ArrayList<JCCostSolution> oldSolutionMap = this.getSolutionMap();
		JCPreemptionPoints oldPreemptionPointsWOPEntry;
		Iterator<JCPreemptionPoints> preemptionPointSolutionIterator;
		int preemptionCost;
		JCBasicBlock rightBasicBlock;
		int rightNPRValue;
		JCBasicBlock visiblePPBasicBlock;
		ArrayList<Integer> visiblePreemptionPoints;
		ArrayList<Integer> visiblePreemptionPoints2;
		JCCostKey WOPCostKey;
		JCCostSolution WOPCostSolution = null;
		JCCostKey WPCostKey;
		JCCostSolution WPCostSolution = null;
		
        if (_debugStartEndBasicBlock == true)
        {
        	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " processing next basic block " + successorBasicBlock.getBBlockName() + " started");
        }

        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
        mapIterator = oldSolutionMap.iterator();

        // Process the left or predecessor Q list basic blocks.
		while (mapIterator.hasNext() == true)
		{
			existingWOPCostSolution = mapIterator.next();
            existingWOPCostKey = existingWOPCostSolution.getSolutionKey();
            
    		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
    		if ((hasBasicBlock(endingBasicBlockID) == true) && (successorBasicBlock.getBBlockID() == endingBasicBlockID))
    		{
    			rightNPRValue = 0;
    		}
    		else
    		{
    	        rightNPRValue = existingWOPCostKey.getRightIndex() + successorBasicBlock.getBBlockWCET();
    		}
        	rightBasicBlock = successorBasicBlock;
        	
    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
    		if (hasBasicBlock(endingBasicBlockID) == true)
    		{
    	        leftNPRValue = 0;
    		}
    		else
    		{
    			leftNPRValue =  existingWOPCostKey.getLeftIndex() + successorBasicBlock.getBBlockWCET();
    		}
	        leftBasicBlock = JCBasicBlock.getBasicBlock(existingWOPCostKey.getLeftBasicBlock());
	        
        	if (_debugNextBasicBlockPair == true)
            {
            	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " processing next left basic block " + leftBasicBlock.getBBlockName() + " processing next right basic block " + rightBasicBlock.getBBlockName());
            }

        	if (_debugOldSolutionCheck == true)
            {
            	System.out.println("    JCSectionBlock: **Searching old solution for (" + existingWOPCostKey.getLeftIndex() + ","  + (existingWOPCostKey.getRightIndex()) + ","  + (existingWOPCostKey.getLeftBasicBlock()) + ","  + (existingWOPCostKey.getRightBasicBlock()) + ")**");
            }
       	
        	//existingWOPCostSolution = oldSolutionMap.get(existingWOPCostKey);
            if (existingWOPCostSolution != null)
            {
                if (_debugOldSolutionCheck == true)
                {
                	System.out.println("    JCSectionBlock: **Processing old solution for (" + existingWOPCostKey.getLeftIndex() + ","  + (existingWOPCostKey.getRightIndex())  + ","  + (existingWOPCostKey.getLeftBasicBlock()) + ","  + (existingWOPCostKey.getRightBasicBlock()) + ")**");
                }

                // Setup the two cost possibilities for the current basic block: with and without preemption.
				costWPreemption = costWOPreemption = Integer.MAX_VALUE;
				oldPreemptionPointsWOPEntry = null;
				
                if (_debugSolutionCheck == true)
                {
                	System.out.println("    JCSectionBlock: considering new WOP solution for (" + leftNPRValue + "," + rightNPRValue + ") Right BB " + rightBasicBlock.getBBlockName() + " Cost " + rightBasicBlock.getBBlockWCET());
                }

                // Check to see if the current solution is feasible.
            	if ((leftNPRValue <= JCSectionBlock._q) && (rightNPRValue <= JCSectionBlock._q))
            	{
                    if (_debugOldSolutionCheck == true)
                    {
                    	System.out.println("    JCSectionBlock: checking new WOP solution for (" + leftNPRValue + ","  + rightNPRValue + ")");
                    }

            		leftCostWOPEntry = existingWOPCostSolution.getSolutionCost();
	                if (_debugCostMapSolution == true)
	                {
	                	System.out.println("    JCSectionBlock: costWOPreemption left costmap (" + leftNPRValue + "," + existingWOPCostKey.getRightIndex() + ") = " + leftCostWOPEntry + " number of preemption point solutions = " + existingWOPCostSolution.numberOfPreemptionPointSolutions());
	                }
	                
	                costWOPreemption = leftCostWOPEntry.intValue() + successorBasicBlock.getBBlockWCET();
            		
	                preemptionPointSolutionIterator = existingWOPCostSolution.getPreemptionPointSolutionsIterator();
	                while (preemptionPointSolutionIterator.hasNext() == true)
	                {
	                	oldPreemptionPointsWOPEntry = preemptionPointSolutionIterator.next();
    	                //oldPreemptionPointsWOPEntry = existingWOPCostSolution.getPreemptionPointsEntry();
    	                if (_debugSolutionPreemptionPoints == true)
    	                {
        	                System.out.print("    JCSectionBlock: Existing solution preemption points: ");
        	                oldPreemptionPointsWOPEntry.displayObjectInformation();
    		    	    	System.out.println();
    	                }
		    	    	
    	                // Determine the set of visible preemption points in the current solution.
                		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			oldPreemptionPointsWOPEntry.removePreemptionPoint(endingBasicBlockID);
                		}
                		
                		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			oldPreemptionPointsWOPEntry.removePreemptionPoint(endingBasicBlockID);
                		}
                		
    	                visiblePreemptionPoints = existingWOPCostSolution.getVisibleEndingPPs(this, oldPreemptionPointsWOPEntry);
    	                if (_debugVisiblePreemptionPoints == true)
    	                {
        	                System.out.print("    JCSectionBlock: Visible ending preemption points (size=" + visiblePreemptionPoints.size() + "): ");
    	                }
    	                
    	                maximumPreemptionCost = 0;
    	                if (visiblePreemptionPoints.size() > 0)
    	                {
    		    			for (Integer item : visiblePreemptionPoints) 
    		    			{
    		            		preemptionCost = pcmMatrix.getMatrixEntry(item.intValue(), 
    	                                                                  successorBasicBlock.getBBlockID());
    	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
    	                        
    	                        if (maximumPreemptionCost < preemptionCost)
    	                        {
    	                        	maximumPreemptionCost = preemptionCost;
    	                        }
    	    	                if (_debugVisiblePreemptionPoints == true)
    	    	                {
        	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
        	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
    	    	                }
    		    			}
    	    			}

    	                if (_debugVisiblePreemptionPoints == true)
    	                {
    	                	System.out.println();
    	                }
    	                
                		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			oldPreemptionPointsWOPEntry.addPreemptionPoint(endingBasicBlockID);
                		}
                		
                		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			oldPreemptionPointsWOPEntry.addPreemptionPoint(endingBasicBlockID);
                			
                			visiblePreemptionPoints2 = existingWOPCostSolution.getVisibleStartingPPs(this, oldPreemptionPointsWOPEntry);
        	                if (_debugVisiblePreemptionPoints == true)
        	                {
            	                System.out.print("    JCSectionBlock: Visible starting preemption points (size=" + visiblePreemptionPoints2.size() + "): ");
        	                }
        	                
        	                if (visiblePreemptionPoints2.size() > 0)
        	                {
        		    			for (Integer item : visiblePreemptionPoints2) 
        		    			{
        		            		preemptionCost = pcmMatrix.getMatrixEntry(successorBasicBlock.getBBlockID(),
        	                                                                  item.intValue());
        	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
        	                        
        	                        if (maximumPreemptionCost < preemptionCost)
        	                        {
        	                        	maximumPreemptionCost = preemptionCost;
        	                        }
        	    	                if (_debugVisiblePreemptionPoints == true)
        	    	                {
            	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
            	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
        	    	                }
        		    			}
        	    			}

        	                if (_debugVisiblePreemptionPoints == true)
        	                {
        	                	System.out.println();
        	                }
                		}
                   	
    	                if (_debugMaximumPreemptionCost == true)
    	                {
        	                System.out.println("    JCSectionBlock: Maximum preemption cost = " + maximumPreemptionCost);
    	                }
    	                
                    	// Validate that it is possible to consider a preemption point solution for this window.
                		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
    	                //newRightNPRValue = maximumPreemptionCost;
    	                newRightNPRValue = 0;
               		
    	                if (visiblePreemptionPoints.size() > 0)
    	                {
    	            		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
    	            		if (hasBasicBlock(endingBasicBlockID) == true)
    	            		{
    	            	        newLeftWPNPRValue = 0;
    	            		}
    	            		else
    	            		{
    	            			newLeftWPNPRValue =  existingWOPCostKey.getLeftIndex();
    	            		}
    	                }
    	                else
    	                {
    	        			newLeftWPNPRValue = leftNPRValue;
    	                }
    	                
                        if ((newLeftWPNPRValue <= JCSectionBlock._q) && (newRightNPRValue <= JCSectionBlock._q))
                        {
                            if (_debugOldSolutionCheck == true)
                            {
                            	System.out.println("    JCSectionBlock: checking new WP solution for (" + newLeftWPNPRValue + "," + newRightNPRValue + ")");
                            }

                			leftCostWPEntry = existingWOPCostSolution.getSolutionCost();

        	                if (_debugCostMapSolution == true)
        	                {
        	                	System.out.println("    JCSectionBlock: costWPreemption left costmap (" + existingWOPCostKey.getLeftIndex() + "," + existingWOPCostKey.getRightIndex() + ") = " + leftCostWPEntry);
        	                }
        	                
       	                    costWPreemption = leftCostWPEntry.intValue() + 
       	                                      maximumPreemptionCost +
        	                		          successorBasicBlock.getBBlockWCET();
    	                }
                    	
                    	if ((costWOPreemption < Integer.MAX_VALUE) || (costWPreemption < Integer.MAX_VALUE))
                    	{
        	                if (_debugCostComparison == true)
        	                {
        	                	System.out.println("    JCSectionBlock: Comparing costWOPreemption = " + costWOPreemption + " to costWPreemption = " + costWPreemption);
        	                }

        	                // Check to see which cost is smaller and store that solution. If equal, store both.

        	                // Without preemption taken. 
        	                //if (costWOPreemption <= costWPreemption)
        	                //{
        	                    if (visiblePreemptionPoints.size() > 0)
        	                    {
        	                		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
        	                		if (hasBasicBlock(endingBasicBlockID) == true)
        	                		{
        	                	        newLeftWOPNPRValue = 0;
        	                		}
        	                		else
        	            	    	{
        	            	    		newLeftWOPNPRValue =  existingWOPCostKey.getLeftIndex();
        	            	    	}
        	                    }
        	                    else
        	                    {
        	        	    		newLeftWOPNPRValue = leftNPRValue;
        	                    }

        	                	WOPCostKey = new JCCostKey(newLeftWOPNPRValue, rightNPRValue);
        	                	WOPCostKey.setLeftBasicBlock(leftBasicBlock.getBBlockID());
        	                	WOPCostKey.setRightBasicBlock(rightBasicBlock.getBBlockID());
        	                	
           	                	WOPCostSolution = new JCCostSolution();
        	                	WOPCostSolution.setSolutionCost(costWOPreemption);
        	                	WOPCostSolution.setSolutionKey(WOPCostKey);
        	                	
                        		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			WOPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			WOPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		WOPCostSolution.combinePreemptionPoints(oldPreemptionPointsWOPEntry);
            	                if (_debugAddCostSolution == true)
            	                {
            	                	System.out.print("    JCSectionBlock: adding WOP cost (" + newLeftWOPNPRValue + "," + rightNPRValue + "," + leftBasicBlock.getBBlockID() + "," + rightBasicBlock.getBBlockID() + ") = " + costWOPreemption + " preemption points ");
            	                	WOPCostSolution.displayObjectInformation();
            	                	System.out.println();
            	                }
                    			WOPCostSolution.addPreemptionPointSolution();
                        		putCostSolution(WOPCostSolution, false);
         	                //}
        	                
        	                // With preemption taken. 
        	                //if (costWOPreemption >= costWPreemption)
        	                //{
            	                if (visiblePreemptionPoints.size() > 0)
            	                {
            	            		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
            	            		if (hasBasicBlock(endingBasicBlockID) == true)
            	            		{
            	            	        newLeftWPNPRValue = 0;
            	            		}
            	            		else
            	            		{
            	            			newLeftWPNPRValue = existingWOPCostKey.getLeftIndex();
            	            		}
            	                }
            	                else
            	                {
            	        			newLeftWPNPRValue = leftNPRValue;
            	                }
            	                
                        		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			newRightNPRValue = 0;
                        		}
                        		else
                        		{
                	                //newRightNPRValue = maximumPreemptionCost;
                	                newRightNPRValue = 0;
                        		}
        		                WPCostKey = new JCCostKey(newLeftWPNPRValue, newRightNPRValue);
        		                WPCostKey.setLeftBasicBlock(leftBasicBlock.getBBlockID());
        		                WPCostKey.setRightBasicBlock(rightBasicBlock.getBBlockID());

        		                WPCostSolution = new JCCostSolution();
        		                WPCostSolution.setSolutionCost(costWPreemption);
        		                WPCostSolution.setSolutionKey(WPCostKey);
        		                
                        		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		
                        		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		
                        		WPCostSolution.addPreemptionPoint(successorBasicBlock.getBBlockID());
                        		WPCostSolution.combinePreemptionPoints(oldPreemptionPointsWOPEntry);
            	                if (_debugAddCostSolution == true)
            	                {
            	                	System.out.print("    JCSectionBlock: adding WP cost (" + newLeftWPNPRValue + "," + newRightNPRValue + "," + leftBasicBlock.getBBlockID() + "," + rightBasicBlock.getBBlockID() + ") = " + costWPreemption + " preemption points ");
            	                	WPCostSolution.displayObjectInformation();
            	                	System.out.println();
            	                }
                    			WPCostSolution.addPreemptionPointSolution();
                        		putCostSolution(WPCostSolution, false);
        	                //}
                    	}
	                }
            	}
        	}
		}
		
        if (_debugStartEndBasicBlock == true)
        {
        	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " processing next basic block " + successorBasicBlock.getBBlockName() + " completed");
        	System.out.println();
        }
        
        // Clean up the previous cost map and preemption points map information 
        // as we no longer need these data structures.  
        oldSolutionMap.clear();
        oldSolutionMap = null;
	}
	
	/**
	 * Computes the preemption cost solution for this section block object. 
	 *
	 * @param  pcmMatrix     the preemption cost matrix used to compute the 
	 *                       preemption cost solution
	 *               
	 * @see                  JCBasicBlock
	 * @see                  JCPreemptionCostMatrix
	 * @see                  JCSectionBlock
	 */
	public void computeSectionSolution(JCPreemptionCostMatrix pcmMatrix)
	{
		int basicBlockIndex;
		int basicBlockListSize;
		JCBasicBlock leftmostBasicBlock; 
		JCBasicBlock predecessorBasicBlock = null; 
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		JCBasicBlock successorBasicBlock = null;
		
        if (_debugComputeSectionSolution == true)
        {
        	System.out.println("    JCSectionBlock: Section " + this.getSectionBlockName() + " computing preemption cost solutions");
        }

        leftmostBasicBlock = getLeftMostBasicBlock();

		basicBlockListSize = (int)numberOfBasicBlocks();
		for (basicBlockIndex = 0; basicBlockIndex < basicBlockListSize; basicBlockIndex++)
		{
			successorBasicBlock = this.getBasicBlockAtIndex(basicBlockIndex);
			
			if (predecessorBasicBlock != null)
			{
				computeBasicBlockSolution(leftmostBasicBlock, predecessorBasicBlock, successorBasicBlock, pcmMatrix);
			}
			else
			{
				computeBasicBlockSolution(leftmostBasicBlock, successorBasicBlock, pcmMatrix);
			}
			
			predecessorBasicBlock = successorBasicBlock;
		}
		
		// Display the computed section solutions if necessary.
		if (_displaySectionSolutions == true)
		{
			displaySectionSolution();
		}
		else
		{
	    	if (_debugDisplayPreemptionSectionNumberOfSolutions == true)
	    	{
	            System.out.println("    JCSectionBlock: Number of minimum cost solutions for section " + getSectionBlockName() + " is " + getSolutionMap().size());
	    	}
	    	if (_debugDisplayPreemptionSectionSolutions == true)
	    	{
		    	solutionIterator = getSolutionMap().iterator();
		    	solutionCount = 1;
		    	while (solutionIterator.hasNext() == true)
		    	{
		    		solution = solutionIterator.next();
		    		System.out.println("        Solution " + solutionCount + " number of preemption point solutions is " + solution.numberOfPreemptionPointSolutions());
		    		solutionCount++;
		    	}
	    	}
		}
	}
	
	/**
	 * Computes the preemption point costs for the specified section. 
	 *
	 * @param  pcmMatrix     the preemption cost matrix used to compute the 
	 *                       preemption cost solution
	 * 
	 * @see                  JCBCostMatrix
	 * @see                  JCCNPCostMatrix
	 * @see                  JCPreemptionPointsMatrix
	 * @see                  JCQCostMatrix
	 * @see                  JCSectionBlock
	 */
	public void computeSectionSolutionMatrix(JCPreemptionCostMatrix pcmMatrix)
	{
		JCBasicBlock basicBlock;
		int bbjID;
		int bbkID;
		int bbWCET;
		JCBCostMatrix bCostMatrix;
		int bCostValue_jk;
		JCCNPCostMatrix cnpCostMatrix;
		int cnpCostValue;
		int j;
		int k;
		int m;
		int n;
		int pCostValue;
		JCPreemptionPointsMatrix ppMatrix;
		JCQCostMatrix qCostMatrix;
		int qCostValue;
		int qCostValue_jm;
		int qCostValue_mk;
		int q_i;
		int s;
		int totalPCost;
		
		n = (int) this.numberOfBasicBlocks();
		q_i = JCSectionBlock.getQValue();
		cnpCostMatrix = new JCCNPCostMatrix(n);
        _cnpMatrixID = cnpCostMatrix.getCNPMatrixID();
        JCCNPCostMatrix.setCNPCostMatrix(_cnpMatrixID, cnpCostMatrix);
		qCostMatrix = new JCQCostMatrix(n);
        _qMatrixID = qCostMatrix.getQMatrixID();
        JCQCostMatrix.setQCostMatrix(_qMatrixID, qCostMatrix);
		bCostMatrix = new JCBCostMatrix(n);
        _bMatrixID = bCostMatrix.getBMatrixID();
        JCBCostMatrix.setBCostMatrix(_bMatrixID, bCostMatrix);
		ppMatrix = new JCPreemptionPointsMatrix(n);
        _ppMatrixID = ppMatrix.getPPMatrixID();
        JCPreemptionPointsMatrix.setPreemptionPointsMatrix(_ppMatrixID, ppMatrix);
        
	    for (s=1; s<n; s++)
	    {
	        for (j=0; j<n-s; j++)
	    	{
		    	basicBlock = this.getBasicBlockAtIndex(j);
		    	bbjID = basicBlock.getBBlockID();
			    k = j + s;
		    	basicBlock = this.getBasicBlockAtIndex(k);
		    	bbkID = basicBlock.getBBlockID();
		    	bbWCET = basicBlock.getBBlockWCET();
		    	cnpCostValue = cnpCostMatrix.getMatrixEntry(j, (k-1)) + bbWCET;
		    	cnpCostMatrix.setMatrixEntry(j, k, cnpCostValue);

		    	if (_debugMatrixEntries == true)
		    	{
			    	System.out.println("cnpCostMatrix[" + j + "][" + k + "]=" + cnpCostValue);
		    	}
		    	
		    	pCostValue = pcmMatrix.getMatrixEntry(bbjID, bbkID); /* (j,k) */
		    	if (pCostValue > 0)
		    	{
			    	qCostValue = cnpCostValue + pCostValue;
		    	}
		    	else
		    	{
		    		qCostValue = cnpCostValue;
			    	if (_debugInvalidMatrixEntries == true)
			    	{
				    	System.out.println("Value <= 0: pCostMatrix[" + j + "][" + k + "]=" + pCostValue + " Basic Block ID indices: pCostMatrix[" + bbjID + "][" + bbkID + "]=" + pCostValue);
			    	}
		    	}
		    	qCostMatrix.setMatrixEntry(j, k, qCostValue);

		    	if (_debugMatrixEntries == true)
		    	{
			    	System.out.println("qCostMatrix[" + j + "][" + k + "]=" + qCostValue);
		    	}

 	    	    if (qCostValue <= q_i)
	     	    {
 	    	    	bCostValue_jk = qCostValue;
 	    	    	bCostMatrix.setMatrixEntry(j, k, bCostValue_jk);
 	    	    	ppMatrix.addPreemptionPoint(j, k, j);
 	    	    	ppMatrix.addPreemptionPoint(j, k, k);
			    	if (_debugMatrixEntries == true)
			    	{
				    	System.out.println("bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
			    	}
		        }
     		    else
		        {
     		    	bCostValue_jk = JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE;
     		    	bCostMatrix.setMatrixEntry(j, k, bCostValue_jk);
			    	if (_debugMatrixEntries == true)
			    	{
				    	System.out.println("bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
			    	}
                }
 	    	    
				for (m = j + 1; m < k; m++)
				{
		        	qCostValue_jm = qCostMatrix.getMatrixEntry(j, m);
	             	qCostValue_mk = qCostMatrix.getMatrixEntry(m, k);
		            if ((qCostValue_jm < JCQCostMatrix.Q_COST_MATRIX_LARGE_VALUE) && (qCostValue_mk < JCQCostMatrix.Q_COST_MATRIX_LARGE_VALUE))
		            {
		            	totalPCost = qCostValue_jm + qCostValue_mk;
		                if (totalPCost < bCostValue_jk)
			            {
		     		    	bCostMatrix.setMatrixEntry(j, k, totalPCost);

	    			    	if (_debugMatrixEntries == true)
	    			    	{
	    				    	System.out.println("bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
	    			    	}

				            ppMatrix.combineSectionPreemptionPoints(j, k, m);
			            }
		            }
				}
		    }
		}

	    if (_debugSectionPreemptionMatrices == true)
	    {
	    	cnpCostMatrix.displayObjectInformation();
	    	qCostMatrix.displayObjectInformation();
	    	bCostMatrix.displayObjectInformation();
	    	ppMatrix.displayObjectInformation(this);
	    }
	}
	
	/**
	 * Computes the potential preemption point solutions for this specified section. 
	 *
	 * @param  pcmMatrix     the preemption cost matrix used to compute the 
	 *                       preemption cost solution
	 * 
	 * @see                  JCBCostMatrix
	 * @see                  JCCNPCostMatrix
	 * @see                  JCPreemptionPointsMatrix
	 * @see                  JCQCostMatrix
	 * @see                  JCSectionBlock
	 */
	public void computeSectionSolutionsViaMatrix(JCPreemptionCostMatrix pcmMatrix)
	{
		JCBasicBlock basicBlock;
		int bbCount;
		JCBCostMatrix bCostMatrix;
		int bCostValue;
		JCCNPCostMatrix cnpCostMatrix;
		int endingBasicBlockID;
		int existingCostValue;
		JCBasicBlock leftBasicBlock;
		JCBasicBlock leftMostBasicBlock;
		int leftNPRValue;
		int leftBBIdx;
		int newLeftNPRValue;
		int newRightNPRValue;
		int pCostValue;
		int ppBBIdx;
		JCPreemptionPointsMatrix ppMatrix;
		JCQCostMatrix qCostMatrix;
		int q_i;
		JCBasicBlock rightBasicBlock;
		JCBasicBlock rightMostBasicBlock;
		int rightBBIdx;
		int rightNPRValue;
		JCCostSolution solution;
		int solutionCostValue;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		JCPreemptionPoints solutionPreemptionPoints;
		JCCostKey WPCostKey;
		JCCostSolution WPCostSolution;
		
		// Get the section cost matrices.
		cnpCostMatrix = JCCNPCostMatrix.getCNPCostMatrix(_cnpMatrixID);
		qCostMatrix = JCQCostMatrix.getQCostMatrix(_qMatrixID);
		bCostMatrix = JCBCostMatrix.getBCostMatrix(_bMatrixID);
		ppMatrix = JCPreemptionPointsMatrix.getPreemptionPointsMatrix(_ppMatrixID);
		q_i = JCSectionBlock.getQValue();
		
        // Create new hash map objects representing the new aggregate solution.
        createCostSolution();
        
        leftNPRValue = 0;
        
        bbCount = (int) numberOfBasicBlocks();
        leftMostBasicBlock = getBasicBlockAtIndex(0);
        rightMostBasicBlock = getBasicBlockAtIndex((bbCount-1));
        
        for (leftBBIdx = 0; ((leftBBIdx < bbCount) && (leftNPRValue <= q_i)); leftBBIdx++)
        {
            leftBasicBlock = getBasicBlockAtIndex(leftBBIdx);
            leftNPRValue += leftBasicBlock.getBBlockWCET();
            if (leftNPRValue <= q_i)
            {
                rightNPRValue = 0;
                for (rightBBIdx = (bbCount-1); ((rightBBIdx > leftBBIdx) && (rightNPRValue <= q_i)); rightBBIdx--)
                {
                    rightBasicBlock = getBasicBlockAtIndex(rightBBIdx);
                    if (rightNPRValue <= q_i)
                    {
                    	bCostValue = bCostMatrix.getMatrixEntry(leftBBIdx, rightBBIdx);
                    	if (bCostValue < JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE)
                    	{
                        	solutionCostValue = leftNPRValue + bCostValue + rightNPRValue;
                        	solutionPreemptionPoints = ppMatrix.getMatrixEntry(leftBBIdx, rightBBIdx);
                        	 	
                    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                    		if (hasBasicBlock(endingBasicBlockID) == true)
                    		{
                    			newLeftNPRValue = 0;
                    		}
                    		else
                    		{
                    			newLeftNPRValue = leftNPRValue;
                    		}
                        	
                    		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                    		if (hasBasicBlock(endingBasicBlockID) == true)
                    		{
                    			newRightNPRValue = 0;
                    		}
                    		else
                    		{
                    			newRightNPRValue = rightNPRValue;
                    		}

                    		WPCostKey = new JCCostKey(newLeftNPRValue, newRightNPRValue);
                        	WPCostKey.setLeftBasicBlock(leftMostBasicBlock.getBBlockID());
                        	WPCostKey.setRightBasicBlock(rightMostBasicBlock.getBBlockID());

                        	WPCostSolution = getCostSolution(WPCostKey);
                        	if (WPCostSolution == null)
                        	{
        		                WPCostSolution = new JCCostSolution();
        		                WPCostSolution.setSolutionCost(solutionCostValue);
        		                WPCostSolution.setSolutionKey(WPCostKey);
        		                
                        		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                        			{
                        				pCostValue = pcmMatrix.getMatrixEntry(endingBasicBlockID, leftBasicBlock.getBBlockID());
                        				if (pCostValue > 0)
                        				{
                            				solutionCostValue += pCostValue;
                    		                WPCostSolution.setSolutionCost(solutionCostValue);
                        				}
                        			}
                        			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		
                        		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                        			{
                        				pCostValue = pcmMatrix.getMatrixEntry(rightBasicBlock.getBBlockID(), endingBasicBlockID);
                        				if (pCostValue > 0)
                        				{
                            				solutionCostValue += pCostValue;
                    		                WPCostSolution.setSolutionCost(solutionCostValue);
                        				}
                        			}
                        			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		
                        		for (ppBBIdx = 0; ppBBIdx < bbCount; ppBBIdx++)
                        		{
                        			if (solutionPreemptionPoints.isPreemptionPointSelected(ppBBIdx) == true)
                        			{
                        				basicBlock = getBasicBlockAtIndex(ppBBIdx);
                                		WPCostSolution.addPreemptionPoint(basicBlock.getBBlockID());
                        			}
                        		}

                        		if (_debugAddCostSolution == true)
            	                {
            	                	System.out.print("    JCSectionBlock: adding multiple preemption cost (" + newLeftNPRValue + "," + newRightNPRValue + "," + leftMostBasicBlock.getBBlockID() + "," + rightMostBasicBlock.getBBlockID() + ") = " + solutionCostValue + " preemption points ");
            	                	WPCostSolution.displayObjectInformation();
            	                	System.out.println();
            	                }
                    			WPCostSolution.addPreemptionPointSolution();
                        		putCostSolution(WPCostSolution, false);
                        	}
                        	else
                        	{
                        		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                        			{
                        				pCostValue = pcmMatrix.getMatrixEntry(endingBasicBlockID, leftBasicBlock.getBBlockID());
                        				if (pCostValue > 0)
                        				{
                            				solutionCostValue += pCostValue;
                        				}
                        			}
                        			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		
                        		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                        		if (hasBasicBlock(endingBasicBlockID) == true)
                        		{
                        			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                        			{
                        				pCostValue = pcmMatrix.getMatrixEntry(leftBasicBlock.getBBlockID(), endingBasicBlockID);
                        				if (pCostValue > 0)
                        				{
                            				solutionCostValue += pCostValue;
                        				}
                        			}
                        			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                        		}
                        		
                        		for (ppBBIdx = 0; ppBBIdx < bbCount; ppBBIdx++)
                        		{
                        			if (solutionPreemptionPoints.isPreemptionPointSelected(ppBBIdx) == true)
                        			{
                        				basicBlock = getBasicBlockAtIndex(ppBBIdx);
                                		WPCostSolution.addPreemptionPoint(basicBlock.getBBlockID());
                        			}
                        		}

                            	existingCostValue = WPCostSolution.getSolutionCost();
                            	if (existingCostValue > solutionCostValue)
                            	{
                            		WPCostSolution.setSolutionCost(solutionCostValue);
                					if (_debugAddMinimumCostSolution == true)
                			        {
                			        	System.out.print("    JCSectionBlock: updating multiple minimum cost (" + newLeftNPRValue + "," + newRightNPRValue + ") = " + solutionCostValue + " preemption points size " + WPCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
                			        	WPCostSolution.displayObjectInformation();
                			        	System.out.println();
                			        }
                					WPCostSolution.clearPreemptionPointSolutions();
                        			WPCostSolution.addPreemptionPointSolution();
                            	}
                            	//else if (existingCostValue == solutionCostValue)
                            	//{
                				//	if (_debugAddMinimumCostSolution == true)
                			    //    {
                			    //    	System.out.print("    JCSectionBlock: add multiple preemption points for minimum section cost (" + newLeftNPRValue + "," + newRightNPRValue + ") = " + solutionCostValue + " preemption points size " + WPCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
                			    //    	WPCostSolution.displayObjectInformation();
                			    //    	System.out.println();
                			    //    }
                        		//	WPCostSolution.addPreemptionPointSolution();
                            	//}
                            	else
                            	{
                            		WPCostSolution.clearPreemptionPoints();
                            	}
                        	}
                    	}
                    }
                    
                    rightNPRValue += rightBasicBlock.getBBlockWCET();
                }
            }
        }
		
        leftNPRValue = 0;
        for (leftBBIdx = 0; ((leftBBIdx < bbCount) && (leftNPRValue <= q_i)); leftBBIdx++)
        {
            leftBasicBlock = getBasicBlockAtIndex(leftBBIdx);
            leftNPRValue += leftBasicBlock.getBBlockWCET();
            if (leftNPRValue <= q_i)
            {
            	if (leftBBIdx < (bbCount-1))
            	{
                    rightNPRValue = cnpCostMatrix.getMatrixEntry(leftBBIdx,(bbCount-1));
            	}
            	else
            	{
            		rightNPRValue = 0;
            	}
            	
                if (rightNPRValue <= q_i)
                {
                	solutionCostValue = leftNPRValue + rightNPRValue;
                	
            		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
            		if (hasBasicBlock(endingBasicBlockID) == true)
            		{
            			newLeftNPRValue = 0;
            		}
            		else
            		{
            			newLeftNPRValue = leftNPRValue;
            		}
                	
            		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
            		if (hasBasicBlock(endingBasicBlockID) == true)
            		{
            			newRightNPRValue = 0;
            		}
            		else
            		{
            			newRightNPRValue = rightNPRValue;
            		}

                	WPCostKey = new JCCostKey(newLeftNPRValue, newRightNPRValue);
                	WPCostKey.setLeftBasicBlock(leftMostBasicBlock.getBBlockID());
                	WPCostKey.setRightBasicBlock(rightMostBasicBlock.getBBlockID());

                	WPCostSolution = getCostSolution(WPCostKey);
                	if (WPCostSolution == null)
                	{
    	                WPCostSolution = new JCCostSolution();
    	                WPCostSolution.setSolutionCost(solutionCostValue);
    	                WPCostSolution.setSolutionKey(WPCostKey);
    	                
                		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                			{
                				pCostValue = pcmMatrix.getMatrixEntry(endingBasicBlockID, leftBasicBlock.getBBlockID());
                				if (pCostValue > 0)
                				{
                    				solutionCostValue += pCostValue;
            		                WPCostSolution.setSolutionCost(solutionCostValue);
                				}
                			}
                			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                		}
                		
                		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                			{
                                pCostValue = pcmMatrix.getMatrixEntry(leftBasicBlock.getBBlockID(), endingBasicBlockID);
                				if (pCostValue > 0)
                				{
                    				solutionCostValue += pCostValue;
            		                WPCostSolution.setSolutionCost(solutionCostValue);
                				}
                			}
                			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                		}
                		
                		WPCostSolution.addPreemptionPoint(leftBasicBlock.getBBlockID());
    	                if (_debugAddCostSolution == true)
    	                {
    	                	System.out.print("    JCSectionBlock: adding single preemption cost (" + newLeftNPRValue + "," + newRightNPRValue + "," + leftMostBasicBlock.getBBlockID() + "," + rightMostBasicBlock.getBBlockID() + ") = " + solutionCostValue + " preemption points ");
    	                	WPCostSolution.displayObjectInformation();
    	                	System.out.println();
    	                }
            			WPCostSolution.addPreemptionPointSolution();
                		putCostSolution(WPCostSolution, false);
                	}
                	else
                	{
                		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                			{
                				pCostValue = pcmMatrix.getMatrixEntry(endingBasicBlockID, leftBasicBlock.getBBlockID());
                				if (pCostValue > 0)
                				{
                    				solutionCostValue += pCostValue;
            		                WPCostSolution.setSolutionCost(solutionCostValue);
                				}
                			}
                			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                		}
                		
                		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
                		if (hasBasicBlock(endingBasicBlockID) == true)
                		{
                			if (WPCostSolution.isPreemptionPointSelected(endingBasicBlockID) == false)
                			{
                				pCostValue = pcmMatrix.getMatrixEntry(leftBasicBlock.getBBlockID(), endingBasicBlockID);
                        		if (pCostValue > 0)
                        		{
                    				solutionCostValue += pCostValue;
            		                WPCostSolution.setSolutionCost(solutionCostValue);
                        		}
                			}
                			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
                		}
                		
                		WPCostSolution.addPreemptionPoint(leftBasicBlock.getBBlockID());

                		existingCostValue = WPCostSolution.getSolutionCost();
                    	if (existingCostValue > solutionCostValue)
                    	{
                    		WPCostSolution.setSolutionCost(solutionCostValue);
        					if (_debugAddMinimumCostSolution == true)
        			        {
        			        	System.out.print("    JCSectionBlock: updating single minimum cost (" + newLeftNPRValue + "," + newRightNPRValue + ") = " + solutionCostValue + " preemption points size " + WPCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
        			        	WPCostSolution.displayObjectInformation();
        			        	System.out.println();
        			        }
        					WPCostSolution.clearPreemptionPointSolutions();
                			WPCostSolution.addPreemptionPointSolution();
                    	}
                    	//else if (existingCostValue == solutionCostValue)
                    	//{
        				//	if (_debugAddMinimumCostSolution == true)
        			    //    {
        			    //    	System.out.print("    JCSectionBlock: add single preemption points for minimum section cost (" + newLeftNPRValue + "," + newRightNPRValue + ") = " + solutionCostValue + " preemption points size " + WPCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
        			    //    	WPCostSolution.displayObjectInformation();
        			    //    	System.out.println();
        			    //    }
                		//	WPCostSolution.addPreemptionPointSolution();
                    	//}
                    	else
                    	{
                    		WPCostSolution.clearPreemptionPoints();
                    	}
                	}
                }
            }
        }
        
        if (_processNoPreemptionPointSolutions == true)
        {
            solutionCostValue = 0;      
            bbCount = (int) numberOfBasicBlocks();
            for (leftBBIdx = 0; (leftBBIdx < bbCount); leftBBIdx++)
            {
                leftBasicBlock = getBasicBlockAtIndex(leftBBIdx);
                solutionCostValue += leftBasicBlock.getBBlockWCET();
            }
            
            if (solutionCostValue <= q_i)
            {
                leftBBIdx = 0;
                leftBasicBlock = getBasicBlockAtIndex(leftBBIdx);
                leftNPRValue = solutionCostValue;
                rightBBIdx = (bbCount-1);
                rightBasicBlock = getBasicBlockAtIndex((bbCount-1));
                rightNPRValue = solutionCostValue;
                
        		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
        		if (hasBasicBlock(endingBasicBlockID) == true)
        		{
        			newLeftNPRValue = 0;
        		}
        		else
        		{
        			newLeftNPRValue = leftNPRValue;
        		}
            	
        		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
        		if (hasBasicBlock(endingBasicBlockID) == true)
        		{
        			newRightNPRValue = 0;
        		}
        		else
        		{
        			newRightNPRValue = rightNPRValue;
        		}

            	WPCostKey = new JCCostKey(newLeftNPRValue, newRightNPRValue);
            	WPCostKey.setLeftBasicBlock(leftMostBasicBlock.getBBlockID());
            	WPCostKey.setRightBasicBlock(rightMostBasicBlock.getBBlockID());
            	
              	WPCostSolution = getCostSolution(WPCostKey);
            	if (WPCostSolution == null)
            	{
                    WPCostSolution = new JCCostSolution();
                    WPCostSolution.setSolutionCost(solutionCostValue);
                    WPCostSolution.setSolutionKey(WPCostKey);
                    
            		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
            		if (hasBasicBlock(endingBasicBlockID) == true)
            		{
            			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
            		}
            		
            		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
            		if (hasBasicBlock(endingBasicBlockID) == true)
            		{
            			WPCostSolution.addPreemptionPoint(endingBasicBlockID);
            		}
            		
                    if (_debugAddCostSolution == true)
                    {
                    	System.out.print("    JCSectionBlock: adding single preemption cost (" + newLeftNPRValue + "," + newRightNPRValue + "," + leftMostBasicBlock.getBBlockID() + "," + rightMostBasicBlock.getBBlockID() + ") = " + solutionCostValue + " preemption points ");
                    	WPCostSolution.displayObjectInformation();
                    	System.out.println();
                    }
        			WPCostSolution.addPreemptionPointSolution();
            		putCostSolution(WPCostSolution, false);
            	}
            }
        }
        
		// Display the computed section solutions if necessary.
		if (_displaySectionSolutions == true)
		{
			displaySectionSolution();
		}
		else
		{
	    	if (_debugDisplayPreemptionSectionNumberOfSolutions == true)
	    	{
		        System.out.println("    JCSectionBlock: Number of minimum cost solutions for section " + getSectionBlockName() + " is " + getSolutionMap().size());
	    	}
        	if (_debugDisplayPreemptionSectionSolutions == true)
	    	{
		    	solutionIterator = getSolutionMap().iterator();
		    	solutionCount = 1;
		    	while (solutionIterator.hasNext() == true)
		    	{
		    		solution = solutionIterator.next();
		    		System.out.println("        Solution " + solutionCount + " number of preemption point solutions is " + solution.numberOfPreemptionPointSolutions());
		    		solutionCount++;
		    	}
	    	}
		}
	}
	
	public void computeSectionSolutionMatrixOld(JCPreemptionCostMatrix pcmMatrix)
	{
		JCBasicBlock basicBlock;
		int bbjID;
		int bbkID;
		int bbWCET;
		JCBCostMatrix bCostMatrix;
		int bCostValue_0j;
		int bCostValue_0k;
		int bCostValue_jk;
		JCCNPCostMatrix cnpCostMatrix;
		int cnpCostValue;
		int j;
		int k;
		int n;
		int pCostValue;
		JCPreemptionPointsMatrix ppMatrix;
		JCQCostMatrix qCostMatrix;
		int qCostValue;
		int q_i;
		int s;
		int totalPCost;
		
		n = (int) this.numberOfBasicBlocks();
		q_i = JCSectionBlock.getQValue();
		cnpCostMatrix = new JCCNPCostMatrix(n);
        _cnpMatrixID = cnpCostMatrix.getCNPMatrixID();
        JCCNPCostMatrix.setCNPCostMatrix(_cnpMatrixID, cnpCostMatrix);
		qCostMatrix = new JCQCostMatrix(n);
        _qMatrixID = qCostMatrix.getQMatrixID();
        JCQCostMatrix.setQCostMatrix(_qMatrixID, qCostMatrix);
		bCostMatrix = new JCBCostMatrix(n);
        _bMatrixID = bCostMatrix.getBMatrixID();
        JCBCostMatrix.setBCostMatrix(_bMatrixID, bCostMatrix);
		ppMatrix = new JCPreemptionPointsMatrix(n);
        _ppMatrixID = ppMatrix.getPPMatrixID();
        JCPreemptionPointsMatrix.setPreemptionPointsMatrix(_ppMatrixID, ppMatrix);
        
	    for (j=0; j<n; j++)
	    {
	    	basicBlock = this.getBasicBlockAtIndex(j);
	    	bbjID = basicBlock.getBBlockID();
	    	bbWCET = basicBlock.getBBlockWCET();
	    	pCostValue = pcmMatrix.getMatrixEntry(bbjID, bbjID); /* (j,j) */
	        if (bbWCET <= q_i)
	        {
	            cnpCostMatrix.setMatrixEntry(j, j, bbWCET);
	            qCostValue = bbWCET + pCostValue; 
	            qCostMatrix.setMatrixEntry(j, j, qCostValue);
	        }
	        else
	        {
	            if (pCostValue < q_i)
	            {
		            cnpCostMatrix.setMatrixEntry(j, j, bbWCET);
		            qCostValue = bbWCET + pCostValue; 
		            qCostMatrix.setMatrixEntry(j, j, qCostValue);
	            }
	            else
	            {
	            	if (_debugFeasibleSolutions == true)
	            	{
		                System.out.println("No feasible solution exists since bbID " + bbjID + " WCET = " + bbWCET + " > Qi " + q_i);
	                	System.out.println("No feasible solution exists since pCostMatrix[" + bbjID + "][" + bbjID + "] = " + pCostValue + " > Qi " + q_i);
	            	}
	                return;
	            }
	        }
	    }

	    for (s=1; s<n; s++)
	    {
	        for (j=0; j<n-s; j++)
	    	{
		    	basicBlock = this.getBasicBlockAtIndex(j);
		    	bbjID = basicBlock.getBBlockID();
			    k = j + s;
		    	basicBlock = this.getBasicBlockAtIndex(k);
		    	bbkID = basicBlock.getBBlockID();
		    	bbWCET = basicBlock.getBBlockWCET();
		    	cnpCostValue = cnpCostMatrix.getMatrixEntry(j, (k-1)) + bbWCET;
		    	cnpCostMatrix.setMatrixEntry(j, k, cnpCostValue);
		    	cnpCostMatrix.setMatrixEntry(k, j, cnpCostValue);

		    	if (_debugMatrixEntries == true)
		    	{
			    	System.out.println("cnpCostMatrix[" + j + "][" + k + "]=" + cnpCostValue);
		    	}
		    	
		    	pCostValue = pcmMatrix.getMatrixEntry(bbjID, bbkID); /* (j,k) */
		    	qCostValue = cnpCostValue + pCostValue;
		    	qCostMatrix.setMatrixEntry(j, k, qCostValue);
		    	qCostMatrix.setMatrixEntry(k, j, qCostValue);

		    	if (_debugMatrixEntries == true)
		    	{
			    	System.out.println("qCostMatrix[" + j + "][" + k + "]=" + qCostValue);
		    	}

 	    	    if (qCostValue <= q_i)
	     	    {
 	    	    	bCostValue_jk = qCostValue;
 	    	    	bCostMatrix.setMatrixEntry(j, k, bCostValue_jk);
 	    	    	ppMatrix.addPreemptionPoint(j, k, j);
 	    	    	ppMatrix.addPreemptionPoint(j, k, k);
			    	if (_debugMatrixEntries == true)
			    	{
				    	System.out.println("bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
			    	}
		        }
     		    else
		        {
     		    	bCostValue_jk = JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE;
     		    	bCostMatrix.setMatrixEntry(j, k, bCostValue_jk);
			    	if (_debugMatrixEntries == true)
			    	{
				    	System.out.println("bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
			    	}
                }
		    }
		}

	    for (k=2; k<n; k++)
	    {
	    	basicBlock = this.getBasicBlockAtIndex(k);
	    	bbkID = basicBlock.getBBlockID();
	    	
	        for (j=k-1; j>=1; j--)
		    {
		    	basicBlock = this.getBasicBlockAtIndex(j);
		    	bbjID = basicBlock.getBBlockID();
		    	
	        	bCostValue_jk = bCostMatrix.getMatrixEntry(j, k);
             	bCostValue_0j = bCostMatrix.getMatrixEntry(0, j);
             	bCostValue_0k = bCostMatrix.getMatrixEntry(0, k);
             	
	        	if (bCostValue_jk <= q_i)
	        	{
			    	if (_debugMatrixEntries == true)
			    	{
	                    System.out.println("Considering bCostMatrix[0][" + j + "]=" + bCostValue_0j + " and bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
	                    System.out.println("versus bCostMatrix[0][" + k + "]=" + bCostValue_0k);
			    	}
			    	
		            if ((bCostValue_0j < JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE) && (bCostValue_jk < JCBCostMatrix.B_COST_MATRIX_LARGE_VALUE))
		            {
		            	totalPCost = bCostValue_0j + bCostValue_jk;
		                if (totalPCost <= bCostValue_0k)
			            {
		     		    	bCostMatrix.setMatrixEntry(j, k, totalPCost);

	    			    	if (_debugMatrixEntries == true)
	    			    	{
	    				    	System.out.println("bCostMatrix[" + j + "][" + k + "]=" + bCostValue_jk);
	    			    	}

	     	    	    	ppMatrix.addPreemptionPoint(j, k, bbjID);
	     	    	    	ppMatrix.addPreemptionPoint(j, k, bbkID);
			            }
			        }
	        	}
		    }
	    }
	    
	    if (_debugSectionPreemptionMatrices == true)
	    {
	    	cnpCostMatrix.displayObjectInformation();
	    	qCostMatrix.displayObjectInformation();
	    	bCostMatrix.displayObjectInformation();
	    	ppMatrix.displayObjectInformation();
	    }
	}
	
	/**
	 * Returns the string section block object type name. 
	 *
	 * @return ID     the string type name of the section block object
	 * 
	 * @see           JCSectionBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCSectionBlock";
	}
    
	/**
	 * Displays information about this section block object. 
	 *
	 * @see           JCSectionBlock
	 */
    @Override
    public void displayObjectInformation()
    {
    	JCBasicBlock                 basicBlock;
    	Integer                      bBlockID;
    	JCBCostMatrix                bCostMatrix;
    	JCCNPCostMatrix              cnpCostMatrix;
    	Iterator<Integer>            iterator;
    	JCPreemptionPointsMatrix     ppMatrix;
    	JCQCostMatrix                qCostMatrix;
    	JCSectionBlock               sectionBlock;
       	Integer                      sectionBlockID;
       	
    	System.out.println("Section Block ID " + getSectionBlockID() + " Name " + getSectionBlockName() + " sub-block ID " + getSubBlockID() + " NPR WCET " + this.getSectionNPRWCET());
    	
    	iterator = this.getBasicBlockIterator();
    	System.out.print("    Basic Blocks: ");
    	while (iterator.hasNext() == true)
    	{
    		bBlockID = iterator.next();
    		basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
    		System.out.print("(" + basicBlock.getBBlockID() + "," + basicBlock.getBBlockName()  + "," + basicBlock.getBBlockWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getPredecessorSectionBlockIterator();
    	System.out.print("    Predecessors: ");
    	while (iterator.hasNext() == true)
    	{
    		sectionBlockID = iterator.next();
    		sectionBlock = JCSectionBlock.getSectionBlock(sectionBlockID);
    		System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSectionNPRWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getSuccessorSectionBlockIterator();
    	System.out.print("    Successors: ");
    	while (iterator.hasNext() == true)
    	{
    		sectionBlockID = iterator.next();
    		sectionBlock = JCSectionBlock.getSectionBlock(sectionBlockID);
    		System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSectionNPRWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getPredecessorQListSectionBlockIterator();
    	System.out.print("    Predecessor Q List: ");
    	while (iterator.hasNext() == true)
    	{
    		sectionBlockID = iterator.next();
    		sectionBlock = JCSectionBlock.getSectionBlock(sectionBlockID);
    		System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSectionNPRWCET() + ") ");
    	}
    	System.out.println();
    	
    	iterator = this.getSuccessorQListSectionBlockIterator();
    	System.out.print("    Successor Q List: ");
    	while (iterator.hasNext() == true)
    	{
    		sectionBlockID = iterator.next();
    		sectionBlock = JCSectionBlock.getSectionBlock(sectionBlockID);
    		System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSectionNPRWCET() + ") ");
    	}
    	System.out.println();    	

    	if (_debugDisplaySectionCostMatrices == true)
    	{
        	if (_cnpMatrixID > -1)
        	{
            	cnpCostMatrix = JCCNPCostMatrix.getCNPCostMatrix(_cnpMatrixID);
            	if (cnpCostMatrix != null)
            	{
            		cnpCostMatrix.displayObjectInformation();
            	}
        	}

        	if (_qMatrixID > -1)
        	{
            	qCostMatrix = JCQCostMatrix.getQCostMatrix(_qMatrixID);
            	if (qCostMatrix != null)
            	{
            		qCostMatrix.displayObjectInformation();
            	}
        	}

        	if (_bMatrixID > -1)
        	{
            	bCostMatrix = JCBCostMatrix.getBCostMatrix(_bMatrixID);
            	if (bCostMatrix != null)
            	{
            		bCostMatrix.displayObjectInformation();
            	}
        	}

        	if (_ppMatrixID > -1)
        	{
        		ppMatrix = JCPreemptionPointsMatrix.getPreemptionPointsMatrix(_ppMatrixID);
            	if (ppMatrix != null)
            	{
            		ppMatrix.displayObjectInformation();
            	}
        	}
    	}
    	
       	super.displayObjectInformation();
    }

	/**
	 * Displays information about all section block objects. 
	 *
	 * @see           JCSectionBlock
	 */
    public static void displayAllObjects()
    {
    	JCSectionBlock       currentSectionBlock;
	    int                  sectionBlockID;
		
    	System.out.println("The number of section blocks is " + _sectionBlocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (sectionBlockID = 0; sectionBlockID < _sectionBlocks.size(); sectionBlockID++)
		{
			currentSectionBlock = JCSectionBlock.getSectionBlock(sectionBlockID);
			currentSectionBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
