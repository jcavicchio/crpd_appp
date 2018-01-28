import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * JCBlock is a fundamental class whose purpose is to contain a set of
 * sub-blocks representing a conditional section, loop body, or entire
 * program. These sub-blocks form the structure of the control flow graph 
 * basic block objects for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCBlock extends JCSubBlock
{
	private static boolean _debugAddCostSolution = false;
	private static boolean _debugAddMaximumCostSolution = false;
	private static boolean _debugAddMinimumCostSolution = false;
	private static boolean _displayBlockSolutions = false; // JCC
	private static boolean _debugCostMapSizes = false;
	private static boolean _debugCostMapSolution = false;
	private static boolean _debugSectionCostMap = false;
	private static boolean _debugNextBasicBlockPair = false;
	private static boolean _debugOldSolutionCheck = false;
	private static boolean _debugSearchOldSolutionCheck = false;
	private static boolean _debugSolutionCheck = false;
	private static boolean _debugStartEndBasicBlock = false;
	private static boolean _debugUpdateCostSolution = false;
	private static boolean _debugSolutionCombinedCostKeys = false;
	private static boolean _debugSolutionPreemptionPoints = false;
	private static boolean _debugSolutionVisiblePreemptionPoints = false;
	private static boolean _debugVisiblePreemptionPoints = false;
	private static boolean _debugMaximumPreemptionCost = false;
	private static boolean _debugNPRRegionCheck = false;
	private static boolean _debugConditionalSectionCost = false;
	private static boolean _debugConditionalPreemptionCost = false;
	private static boolean _debugBlockCost = false;
	private static boolean _debugBlockPreemptionCost = false;
	private static boolean _debugSupportMultiplePreemptionPoints = true; // JCC3
	private static boolean _debugDisplayPreemptionBlockSolutions = false;
	private static boolean _debugDisplayPreemptionBlockNumberOfSolutions = false; // JCC2
	private static boolean _debugConditionalSolutionProgress = false;
	private static boolean _debugInputPreemptionSolutionSizes = false; // JCC2

	private static int _nextID = 0;

	private static int _blkCount = 0;                  // Counter to count which block object it is
	private static ArrayList<JCBlock> _blocks = 
			          new ArrayList<JCBlock>();        // Block structure of the graph

	private static int _q;                             // Maximum non preemptive region parameter

	private int _blockID;
    private ArrayList<Integer> _subBlocks;

	/**
	 * Default constructor.
	 */
	JCBlock()
	{
		super();
		_blockID = _nextID;
		_nextID++;
		_blkCount = _nextID;
		_subBlocks = new ArrayList<Integer>();
		setBlock(_blockID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_blkCount = 0;
		_blocks = new ArrayList<JCBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the block object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _blockID   the identifier of this block object
	 * 
	 * @see               JCBlock
	 */
	public int getBlockID()
	{
		return _blockID;
	}

	/**
	 * Returns the string name of the block object. 
	 *
	 * @return _ID     the string name of this block object
	 * 
	 * @see            JCBlock
	 */
	public String getBlockName()
	{
		String id = "JCBlock" + _blockID;
		
		return id;
	}
	
	/**
	 * Stores the block object at the specified location in
	 * the block array. 
	 *
	 * @param  blockID    the identifier of the stored block object
	 * 
	 * @param  blockObj   the block object to be stored
	 * 
	 * @see                JCBlock
	 */
	public static void setBlock(int blockID, JCBlock blockObj)
	{
		if (blockID < _blkCount)
		{
			_blocks.add(blockObj);
		}
	}
	
	/**
	 * Returns the block object associated with the specified identifier. 
	 *
	 * @param  blockID    the identifier of the stored block object
	 * 
	 * @return blockObj   the block object stored
	 * 
	 * @see                JCBlock
	 */
	public static JCBlock getBlock(int blockID)
	{
		if (blockID < _blkCount)
		{
	        return _blocks.get(blockID);
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
	 * @see          JCBlock
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
	 * @see         JCBlock
	 */
	public static void setQValue(int Q)
	{
		_q = Q;
	}
	
	/**
	 * Adds the sub-block object to this block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this
	 *                   block object
	 *               
	 * @see          JCBlock
	 * @see          JCSubBlock
	 */
	public void addSubBlock(JCSubBlock subBlock)
	{
		JCSubBlock prevSubBlock;
		int prevSubblockID;
		int sbIndex;
		
		if (_subBlocks.contains(subBlock.getSubBlockID()) == false)
		{
			if (_subBlocks.add(subBlock.getSubBlockID()) != true)
			{
				System.out.println("JCBlock: Error adding sub-block " + subBlock.getSubBlockID());
			}
			subBlock.setParentBlock(this);
			
			sbIndex = _subBlocks.indexOf(subBlock.getSubBlockID());
			if (sbIndex > 0)
			{
				prevSubblockID = _subBlocks.get((sbIndex-1));
				prevSubBlock = JCSubBlock.getSubBlock(prevSubblockID);
				prevSubBlock.addSuccessorSubBlock(subBlock);
				subBlock.addPredecessorSubBlock(prevSubBlock);
				
				subBlock.updatePredecessorSectionBlocks(prevSubBlock);
			}
		}
	}
	
	/**
	 * Adds the sub-block object to this block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this
	 *                   block object
	 *               
	 * @see          JCBlock
	 * @see          JCSubBlock
	 */
	public void reAddSubBlock(JCSubBlock subBlock)
	{
		if (_subBlocks.contains(subBlock.getSubBlockID()) == false)
		{
			if (_subBlocks.add(subBlock.getSubBlockID()) != true)
			{
				System.out.println("JCBlock: Error re-adding sub-block " + subBlock.getSubBlockID());
			}
			subBlock.setParentBlock(this);
		}
	}
	
	/**
	 * Removes the sub-block object from this block object. 
	 *
	 * @param  subBlock  the sub-block object to add to this
	 *                   block object
	 *               
	 * @see          JCBlock
	 * @see          JCSubBlock
	 */
	public void removeSubBlock(JCSubBlock subBlock)
	{
		if (_subBlocks.remove((Integer)subBlock.getSubBlockID()) != true)
		{
			System.out.println("JCBlock: Error removing sub-block " + subBlock.getSubBlockID());
		}
		subBlock.setParentBlock(null);
	}
	
	/**
	 * Finds the sub-block object from within this block object
	 * with the specified block name. 
	 *
	 * @param  subBlockName    the string name of the sub-block object 
	 *                         to search for
	 *               
	 * @return subBlock        the found sub-block object in this block
	 *                         object
	 *               
	 * @see                    JCBlock
	 * @see                    JCSubBlock
	 */
	public JCSubBlock findSubBlock(String subBlockName)
	{
		JCSubBlock currentSubBlock;
		Integer currentSubBlockID;
	    String currentSubBlockName;
	    Iterator<Integer> iterator;
		JCSubBlock subBlock = null;
	    
	    if (subBlockName != null)
	    {
		    iterator = getSubBlockIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (subBlockName == null))
		        {
		        	currentSubBlockID = iterator.next();
		        	currentSubBlock = JCSubBlock.getSubBlock(currentSubBlockID);
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
	 * Returns the sub-block object at the specified index in this 
	 * block object.
	 *
	 * @param  sbIndex         the index of the sub-block object to return
	 *               
	 * @return subBlock        the found sub-block object in this block
	 *                         object
	 *               
	 * @see                    JCBlock
	 * @see                    JCSubBlock
	 */
	public JCSubBlock getSubBlockAtIndex(int sbIndex)
	{
		JCSubBlock subBlock;
		int sBlockID;
		
		sBlockID = _subBlocks.get(sbIndex);
		subBlock = JCSubBlock.getSubBlock(sBlockID);
		
		return subBlock;
	}
	
	/**
	 * Returns the index of the sub-block object contained within this 
	 * block object.
	 *
	 *  @param  subBlock       the found sub-block object in this block
	 *                         object
	 *               
	 *  @return sbIndex        the index of the sub-block object to return
	 *               
	 * @see                    JCBlock
	 * @see                    JCSubBlock
	 */
	public int indexOfSubBlock(JCSubBlock subBlock)
	{
	    int sbIndex;
	    
	    sbIndex = _subBlocks.indexOf(subBlock.getSubBlockID());
	    
	    return sbIndex;
	}
	
	/**
	 * Returns the number of sub-block objects in this block 
	 * object. 
	 *
	 * @return numberOfSubBlocks  the number of sub-blocks contained in this 
	 *                            block object
	 *                        
	 * @see                       JCBlock
	 * @see                       JCSubBlock
	 */
	public long numberOfSubBlocks()
	{
		return _subBlocks.size();
	}
	
	/**
	 * Returns the sub-block iterator for accessing the sub-block objects 
	 * in this block object. 
	 *
	 * @return iterator  the sub-block iterator for accessing the 
	 *                   sub-block objects
	 *                        
	 * @see              JCBlock
	 * @see              JCSubBlock
	 */
	public Iterator<Integer> getSubBlockIterator()
	{
		return _subBlocks.iterator();
	}
	
	/**
	 * Updates the predecessor basic block relationships from this block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block predecessor
	 *                   contained within
	 *               
	 * @see              JCBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updatePredecessorBasicBlocks(JCSubBlock subBlock)
	{
		JCSubBlock currentSubBlock;
		int currentSubBlockID;
		int currentSubBlockIndex;
		
		if (numberOfSubBlocks() > 0)
		{
			currentSubBlockIndex = 0;
			currentSubBlockID = _subBlocks.get(currentSubBlockIndex);
			currentSubBlock = JCSubBlock.getSubBlock(currentSubBlockID);
			currentSubBlock.updatePredecessorBasicBlocks(subBlock);
		}
	}
	
	/**
	 * Updates the successor basic block relationships from this block object. 
	 *
	 * @param  subBlock  the sub-block object to update the basic block successors
	 *                   contained within
	 *               
	 * @see              JCBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updateSuccessorBasicBlocks(JCSubBlock subBlock)
	{
		JCSubBlock currentSubBlock;
		int currentSubBlockID;
		int currentSubBlockIndex;
		
		if (numberOfSubBlocks() > 0)
		{
			currentSubBlockIndex = ((int)numberOfSubBlocks() - 1);
			currentSubBlockID = _subBlocks.get(currentSubBlockIndex);
			currentSubBlock = JCSubBlock.getSubBlock(currentSubBlockID);
			//System.out.println("updateSuccessorBasicBlocks: JCBlock: (" + this.getBlockID() + ") name " + this.getBlockName() + "(" + this.getSubBlockID() + ") updating successor basic blocks for sub-block " + currentSubBlockID + " type " + currentSubBlock.getObjectTypeName() + " with sub-block " + subBlock.getSubBlockID() + " type " + subBlock.getObjectTypeName());
			currentSubBlock.updateSuccessorBasicBlocks(subBlock);
		}
	}
	
	/**
	 * Updates the predecessor section block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the section block predecessor
	 *                   contained within
	 *               
	 * @see              JCBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updatePredecessorSectionBlocks(JCSubBlock subBlock)
	{
		JCSubBlock currentSubBlock;
		int currentSubBlockID;
		int currentSubBlockIndex;
		
		if (numberOfSubBlocks() > 0)
		{
			currentSubBlockIndex = 0;
			currentSubBlockID = _subBlocks.get(currentSubBlockIndex);
			currentSubBlock = JCSubBlock.getSubBlock(currentSubBlockID);
			currentSubBlock.updatePredecessorSectionBlocks(subBlock);
		}
	}
	
	/**
	 * Updates the successor section block relationships from this sub-block object. 
	 *
	 * @param  subBlock  the sub-block object to update the section block successors
	 *                   contained within
	 *               
	 * @see              JCBlock
	 * @see              JCSubBlock
	 */
	@Override
	public void updateSuccessorSectionBlocks(JCSubBlock subBlock)
	{
		JCSubBlock currentSubBlock;
		int currentSubBlockID;
		int currentSubBlockIndex;
		
		if (numberOfSubBlocks() > 0)
		{
			currentSubBlockIndex = ((int)numberOfSubBlocks() - 1);
			currentSubBlockID = _subBlocks.get(currentSubBlockIndex);
			currentSubBlock = JCSubBlock.getSubBlock(currentSubBlockID);
			currentSubBlock.updateSuccessorSectionBlocks(subBlock);
		}
	}
	
	/**
	 * Computes the potential preemption point solutions for the specified basic
	 * block object. 
	 *
	 * @param  predecessorBasicBlock  the predecessor basic block object to the successor
	 *                                basic block
	 *               
	 * @param  conditionalBlock       the conditional block object to compute the preemption cost
	 *                                solution with respect to the predecessor basic block and
	 *                                successor basic block
	 *               
	 * @param  successorBasicBlock    the basic block object to compute the preemption cost
	 *                                solution added as a successor to the current solution
	 *               
	 * @param  pcmMatrix              the preemption cost matrix used to compute the preemption cost
	 *                                solution
	 *               
	 * @see                           JCBasicBlock
	 * @see                           JCConditionalBlock
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeConditionalSolutionOld(JCBasicBlock predecessorBasicBlock, int leftNPROffsetValue, JCConditionalBlock conditionalBlock, JCBasicBlock successorBasicBlock, int rightNPROffsetValue, JCPreemptionCostMatrix pcmMatrix)
	{
		boolean adjustLeftNPRValue = true;
		JCCostKey checkCostKey = new JCCostKey();
		JCConditionalSection conditionalSection;
		int condSectionIndex;
		int condSectionSize;
		JCCostKey costKey;
		JCCostSolution costSolutionEntry;
		int endingBasicBlockID;
		JCCostSolution existingCostSolution;
		JCBasicBlock leftBasicBlock;
		Integer leftCostWOPEntry;
		Integer leftCostWPEntry;
		int leftCostWOPreemption;
		int leftCostWPreemption;
		JCPreemptionPoints leftCostWPPreemptionPointsEntry = null;
		JCPreemptionPoints leftCostWOPPreemptionPointsEntry = null;
		int leftIndex;
		int leftQListSize;
		int leftNPRValue;
		int leftPreemptionCost;
		int maximumCostValue;
		Integer middleCostWOPEntry;
		Integer middleCostWPEntry;
		JCPreemptionPoints maximumCostPreemptionPointsEntry = null;
		JCPreemptionPoints middleCostWBothPreemptionPointsEntry = null;
		JCPreemptionPoints middleCostWLeftPreemptionPointsEntry = null;
		JCPreemptionPoints middleCostWRightPreemptionPointsEntry = null;
		JCPreemptionPoints middleCostWOPPreemptionPointsEntry = null;
		JCPreemptionPoints minimumCostPreemptionPointsEntry = null;
		int middleCostWOPreemption;
		int middleCostWBothPreemption;
		int middleCostWLeftPreemption;
		int middleCostWRightPreemption;
		int minimumCostValue;
		ArrayList<JCCostSolution> oldSolutionMap = getSolutionMap();
		JCSectionBlock predecessorSection;
		Integer rightCostWOPEntry;
		Integer rightCostWPEntry;
		JCPreemptionPoints rightCostWPPreemptionPointsEntry = null;
		JCPreemptionPoints rightCostWOPPreemptionPointsEntry = null;
		int rightCostWOPreemption;
		int rightCostWPreemption;
		JCBasicBlock rightBasicBlock;
		int rightIndex;
		int rightPreemptionCost;
		int rightQListSize;
		int rightNPRValue;
		int sectionCostValue;
		JCSectionBlock successorSection;
		
        if (_debugStartEndBasicBlock == true)
        {
        	System.out.println("    JCBlock: computeConditionalSolutionOld: Block " + this.getBlockName() + " processing next basic block " + successorBasicBlock.getBBlockName() + " started");
        }

        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
		leftQListSize = (int)predecessorBasicBlock.numberOfPredecessorQListBasicBlocks();
		predecessorSection = predecessorBasicBlock.getSectionBlock();
        rightQListSize = (int)successorBasicBlock.numberOfSuccessorQListBasicBlocks();
		successorSection = successorBasicBlock.getSectionBlock();
		
        leftIndex = 0;
        leftNPRValue = 0;
        leftBasicBlock = predecessorBasicBlock;
        
        // Process the left or predecessor Q list basic blocks.
		do
		{
			rightIndex = 0;
	        rightNPRValue = 0;
	        rightBasicBlock = successorBasicBlock;
	        
	        // Process the right or successor Q list basic blocks.
            do
            {
                if (_debugNextBasicBlockPair == true)
                {
                	System.out.println("    JCBlock: computeConditionalSolutionOld: Block " + this.getBlockName() + " processing next basic block pair " + leftBasicBlock.getBBlockName() + "," + rightBasicBlock.getBBlockName());
                }
                
                // Compute the maximum cost value across all conditional section blocks.
				maximumCostValue = Integer.MIN_VALUE;
				maximumCostPreemptionPointsEntry = null;
				
                if (_debugSolutionCheck == true)
                {
                	System.out.println("    JCBlock: computeConditionalSolutionOld: considering left WOP solution for (" + leftNPRValue + ",0) BB " + leftBasicBlock.getBBlockName() + " Cost " + leftBasicBlock.getBBlockWCET());
                }

                // Check to see if the current left WOP solution is feasible.
                leftCostWOPreemption = Integer.MAX_VALUE;
            	if (predecessorBasicBlock.isFeasibleInclusive(leftNPRValue, 0) == true)
            	{
                    if (_debugOldSolutionCheck == true)
                    {
                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old left WOP solution for (" + leftNPRValue + ","  + (leftNPROffsetValue+predecessorBasicBlock.getBBlockWCET()) + ")");
                    }

            		checkCostKey.setIndices(leftNPRValue, (leftNPROffsetValue+predecessorBasicBlock.getBBlockWCET()));
                    existingCostSolution = predecessorSection.getCostSolution(checkCostKey);
                    if (existingCostSolution != null)
                    {
            		    leftCostWOPEntry = existingCostSolution.getSolutionCost();
            		    
    	                if (_debugCostMapSolution == true)
    	                {
    	                	System.out.println("    JCBlock: computeConditionalSolutionOld: leftCostWOPreemption left costmap (" + leftNPRValue + "," + (leftNPROffsetValue+predecessorBasicBlock.getBBlockWCET()) + ") = " + leftCostWOPEntry);
    	                }
    	                
    	                leftCostWOPreemption = leftCostWOPEntry.intValue();
                		
    	                leftCostWOPPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
	                }
            	}		

        		leftPreemptionCost = pcmMatrix.getMatrixEntry(leftBasicBlock.getBBlockID(), 
                                                              predecessorBasicBlock.getBBlockID());
        		leftPreemptionCost = (leftPreemptionCost < 0) ? 0 : leftPreemptionCost;

                // Check to see if the current left WP solution is feasible.
        		leftCostWPreemption = Integer.MAX_VALUE;
                if (leftPreemptionCost < JCBlock.getQValue())
                {
                    if (_debugOldSolutionCheck == true)
                    {
                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old left WP solution for (" + leftNPRValue + "," + leftPreemptionCost + ")");
                    }

               		checkCostKey.setIndices(leftNPRValue, leftPreemptionCost);
                    existingCostSolution = predecessorSection.getCostSolution(checkCostKey);
                    if (existingCostSolution != null)
                    {
               		    leftCostWPEntry = existingCostSolution.getSolutionCost();

                        if (_debugCostMapSolution == true)
                        {
                           	System.out.println("    JCBlock: computeConditionalSolutionOld: leftCostWPreemption left costmap (" + leftNPRValue + "," + leftPreemptionCost + ") = " + leftCostWPEntry);
                        }
                           
                        leftCostWPPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
                              
                        leftCostWPreemption = leftCostWPEntry.intValue();
                    }
                }
               	
                if (_debugSolutionCheck == true)
                {
                	System.out.println("    JCBlock: computeConditionalSolutionOld: considering right WOP solution for (0," + rightNPRValue + ") BB " + leftBasicBlock.getBBlockName() + " Cost " + leftBasicBlock.getBBlockWCET());
                }

                // Check to see if the current right WOP solution is feasible.
                rightCostWOPreemption = Integer.MAX_VALUE;
            	if (successorBasicBlock.isFeasibleInclusive(0, rightNPRValue) == true)
            	{
                    if (_debugOldSolutionCheck == true)
                    {
                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old right WOP solution for (" + (rightNPROffsetValue+successorBasicBlock.getBBlockWCET()) + "," + rightNPRValue + ")");
                    }

            		checkCostKey.setIndices((rightNPROffsetValue+successorBasicBlock.getBBlockWCET()), rightNPRValue);
                    existingCostSolution = successorSection.getCostSolution(checkCostKey);
                    if (existingCostSolution != null)
                    {
            		    rightCostWOPEntry = existingCostSolution.getSolutionCost();

    	                if (_debugCostMapSolution == true)
    	                {
    	                	System.out.println("    JCBlock: computeConditionalSolutionOld: rightCostWOPreemption right costmap (" + (rightNPROffsetValue+successorBasicBlock.getBBlockWCET()) + "," + rightNPRValue + ") = " + rightCostWOPEntry);
    	                }
    	                
    	                rightCostWOPreemption = rightCostWOPEntry.intValue();
                		
    	                rightCostWOPPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
	                }
            	}		

        		rightPreemptionCost = pcmMatrix.getMatrixEntry(successorBasicBlock.getBBlockID(),
        				                                       rightBasicBlock.getBBlockID());
        		rightPreemptionCost = (rightPreemptionCost < 0) ? 0 : rightPreemptionCost;

                // Check to see if the current right WP solution is feasible.
    			rightCostWPreemption = Integer.MAX_VALUE;
                if (rightPreemptionCost < JCBlock.getQValue())
                {
                	if (_debugOldSolutionCheck == true)
                	{
                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old right WP solution for (" + rightPreemptionCost + "," + rightNPRValue + ")");
                	}

                	checkCostKey.setIndices(rightPreemptionCost, rightNPRValue);
                    existingCostSolution = successorSection.getCostSolution(checkCostKey);
                    if (existingCostSolution != null)
                    {
                	    rightCostWPEntry = existingCostSolution.getSolutionCost();

                		if (_debugCostMapSolution == true)
                		{
                    		System.out.println("    JCBlock: computeConditionalSolutionOld: rightCostWPreemption right costmap (" + rightPreemptionCost + "," + rightNPRValue + ") = " + rightCostWPEntry);
                		}

                		rightCostWPPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();

                		rightCostWPreemption = rightCostWPEntry.intValue();
                	}
                }

            	// Loop through all the conditional sections to compute the minimum cost for each.
            	condSectionSize = (int)conditionalBlock.numberOfConditionalSections();
				for (condSectionIndex = 0; condSectionIndex < condSectionSize; condSectionIndex++)
				{
					conditionalSection = conditionalBlock.getConditionalSectionAtIndex(condSectionIndex);
	                
					// Compute the minimum cost value for the current conditional section block.
					minimumCostValue = Integer.MAX_VALUE;
					minimumCostPreemptionPointsEntry = null;
					
	                if (_debugSolutionCheck == true)
	                {
	                	System.out.println("    JCBlock: computeConditionalSolutionOld: considering WOP solution for (0," + rightNPRValue + ") BB " + rightBasicBlock.getBBlockName() + " Cost " + rightBasicBlock.getBBlockWCET());
	                }

	                // Check to see if the current middle WOP solution is feasible.
	            	if ((predecessorBasicBlock.isFeasibleInclusive(leftNPRValue, 0) == true) &&
	                	(successorBasicBlock.isFeasibleInclusive(0, rightNPRValue) == true))
	            	{
	                    if (_debugOldSolutionCheck == true)
	                    {
	                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old middle WOP solution for (" + leftNPRValue + ","  + rightNPRValue + ")");
	                    }

	            		// Get the middle cost entry corresponding no preemption on either side.
	            		checkCostKey.setIndices(leftNPRValue, rightNPRValue);
	                    existingCostSolution = conditionalSection.getCostSolution(checkCostKey);
	                    if (existingCostSolution != null)
	                    {
	            		    middleCostWOPEntry = existingCostSolution.getSolutionCost();

	    	                if (_debugCostMapSolution == true)
	    	                {
	    	                	System.out.println("    JCBlock: computeConditionalSolutionOld: middleCostWOPreemption middle costmap (" + leftNPRValue + "," + rightNPRValue + ") = " + middleCostWOPEntry);
	    	                }
	    	                
	    	                middleCostWOPreemption = middleCostWOPEntry.intValue();
	                		
	    	                middleCostWOPPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
		                }
	            		else
	            		{
	            			middleCostWOPreemption = Integer.MAX_VALUE;
	            		}

	            		if (_debugOldSolutionCheck == true)
	                    {
	                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old middle W left P solution for (" + leftPreemptionCost + ","  + rightNPRValue + ")");
	                    }

	            		// Get the middle cost entry corresponding to preemption on the left side.
	            		checkCostKey.setIndices(leftPreemptionCost, rightNPRValue);
	                    existingCostSolution = conditionalSection.getCostSolution(checkCostKey);
	                    if (existingCostSolution != null)
	                    {
	            		    middleCostWPEntry = existingCostSolution.getSolutionCost();

	    	                if (_debugCostMapSolution == true)
	    	                {
	    	                	System.out.println("    JCBlock: computeConditionalSolutionOld: middleCostWLeftPreemption middle costmap (" + leftPreemptionCost + "," + rightNPRValue + ") = " + middleCostWPEntry);
	    	                }
	    	                
	    	                middleCostWLeftPreemption = middleCostWPEntry.intValue();
	                		
	    	                middleCostWLeftPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
		                }
	            		else
	            		{
	            			middleCostWLeftPreemption = Integer.MAX_VALUE;
	            		}

	            		if (_debugOldSolutionCheck == true)
	                    {
	                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old middle W right P solution for (" + leftNPRValue + ","  + rightPreemptionCost + ")");
	                    }

	            		// Get the middle cost entry corresponding to preemption on the right side.
	            		checkCostKey.setIndices(leftNPRValue, rightPreemptionCost);
	                    existingCostSolution = conditionalSection.getCostSolution(checkCostKey);
	                    if (existingCostSolution != null)
	                    {
	            		    middleCostWPEntry = existingCostSolution.getSolutionCost();

	    	                if (_debugCostMapSolution == true)
	    	                {
	    	                	System.out.println("    JCBlock: computeConditionalSolutionOld: middleCostWRightPreemption middle costmap (" + leftNPRValue + "," + rightPreemptionCost + ") = " + middleCostWPEntry);
	    	                }
	    	                
	    	                middleCostWRightPreemption = middleCostWPEntry.intValue();
	                		
	    	                middleCostWRightPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
		                }
	            		else
	            		{
	    	                middleCostWRightPreemption = Integer.MAX_VALUE;
	            		}

	            		if (_debugOldSolutionCheck == true)
	                    {
	                    	System.out.println("    JCBlock: computeConditionalSolutionOld: checking old middle W both P solution for (" + leftPreemptionCost + ","  + rightPreemptionCost + ")");
	                    }

	            		// Get the middle cost entry corresponding to preemption on both sides.
	            		checkCostKey.setIndices(leftPreemptionCost, rightPreemptionCost);
	                    existingCostSolution = conditionalSection.getCostSolution(checkCostKey);
	                    if (existingCostSolution != null)
	                    {
	            		    middleCostWPEntry = existingCostSolution.getSolutionCost();

	    	                if (_debugCostMapSolution == true)
	    	                {
	    	                	System.out.println("    JCBlock: computeConditionalSolutionOld: middleCostWBothPreemption middle costmap (" + leftPreemptionCost + "," + rightPreemptionCost + ") = " + middleCostWPEntry);
	    	                }
	    	                
	    	                middleCostWBothPreemption = middleCostWPEntry.intValue();
	                		
	    	                middleCostWBothPreemptionPointsEntry = existingCostSolution.getPreemptionPointsEntry();
		                }
	            		else
	            		{
	            			middleCostWBothPreemption = Integer.MAX_VALUE;
	            		}
	            		
	            		// Check the cost corresponding to no preemption on either side.
	            		if ((leftCostWOPreemption != Integer.MAX_VALUE) &&
		            		(rightCostWOPreemption != Integer.MAX_VALUE) &&
		            		(middleCostWOPreemption != Integer.MAX_VALUE))
	            		{
	            			sectionCostValue = leftCostWOPreemption + middleCostWOPreemption + rightCostWOPreemption;
	            			if (sectionCostValue < minimumCostValue)
	            			{
	            				minimumCostValue = sectionCostValue;
	                    		minimumCostPreemptionPointsEntry = new JCPreemptionPoints(false);
	                    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
	                    		minimumCostPreemptionPointsEntry.addPreemptionPoint(endingBasicBlockID);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(leftCostWOPPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(middleCostWOPPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(rightCostWOPPreemptionPointsEntry);

	                    		if (_debugUpdateCostSolution == true)
	        	                {
	        	                	System.out.print("    JCBlock: computeConditionalSolutionOld: updating conditional section " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftNPRValue + "," + rightNPRValue + ") = " + sectionCostValue + " preemption points ");
	        	                	minimumCostPreemptionPointsEntry.displayObjectInformation();
	        	                	System.out.println();
	        	                }
	            			}
	            		}
	            		
	            		// Check the cost corresponding to a preemption on the left side only.
	            		if ((leftCostWPreemption != Integer.MAX_VALUE) &&
		            		(rightCostWOPreemption != Integer.MAX_VALUE) &&
		            		(middleCostWLeftPreemption != Integer.MAX_VALUE))
	            		{
	            			sectionCostValue = leftCostWPreemption + middleCostWLeftPreemption + rightCostWOPreemption;
	            			if (sectionCostValue < minimumCostValue)
	            			{
	            				minimumCostValue = sectionCostValue;
	                    		minimumCostPreemptionPointsEntry = new JCPreemptionPoints(false);
	                    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
	                    		minimumCostPreemptionPointsEntry.addPreemptionPoint(endingBasicBlockID);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(leftCostWPPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(middleCostWLeftPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(rightCostWOPPreemptionPointsEntry);

	                    		if (_debugUpdateCostSolution == true)
	        	                {
	        	                	System.out.print("    JCBlock: computeConditionalSolutionOld: updating conditional section " + conditionalSection.getBlockName() + " minimum left WP cost (" + leftNPRValue + "," + rightNPRValue + ") = " + sectionCostValue + " preemption points ");
	        	                	minimumCostPreemptionPointsEntry.displayObjectInformation();
	        	                	System.out.println();
	        	                }
	            			}
	            		}
	            		
	            		// Check the cost corresponding to a preemption on the right side only.
	            		if ((leftCostWOPreemption != Integer.MAX_VALUE) &&
		            		(rightCostWPreemption != Integer.MAX_VALUE) &&
		            		(middleCostWRightPreemption != Integer.MAX_VALUE))
	            		{
	            			sectionCostValue = leftCostWOPreemption + middleCostWRightPreemption + rightCostWPreemption;
	            			if (sectionCostValue < minimumCostValue)
	            			{
	            				minimumCostValue = sectionCostValue;
	                    		minimumCostPreemptionPointsEntry = new JCPreemptionPoints(false);
	                    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
	                    		minimumCostPreemptionPointsEntry.addPreemptionPoint(endingBasicBlockID);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(leftCostWOPPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(middleCostWRightPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(rightCostWPPreemptionPointsEntry);

	                    		if (_debugUpdateCostSolution == true)
	        	                {
	        	                	System.out.print("    JCBlock: computeConditionalSolutionOld: updating conditional section " + conditionalSection.getBlockName() + " minimum right WP cost (" + leftNPRValue + "," + rightNPRValue + ") = " + sectionCostValue + " preemption points ");
	        	                	minimumCostPreemptionPointsEntry.displayObjectInformation();
	        	                	System.out.println();
	        	                }
	            			}
	            		}
	            		
	            		// Check the cost corresponding to a preemption on both the left and right sides.
	            		if ((leftCostWPreemption != Integer.MAX_VALUE) &&
		            		(rightCostWPreemption != Integer.MAX_VALUE) &&
		            		(middleCostWBothPreemption != Integer.MAX_VALUE))
	            		{
	            			sectionCostValue = leftCostWPreemption + middleCostWBothPreemption + rightCostWPreemption;
	            			if (sectionCostValue < minimumCostValue)
	            			{
	            				minimumCostValue = sectionCostValue;
	                    		minimumCostPreemptionPointsEntry = new JCPreemptionPoints(false);
	                    		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
	                    		minimumCostPreemptionPointsEntry.addPreemptionPoint(endingBasicBlockID);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(leftCostWPPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(middleCostWBothPreemptionPointsEntry);
	                    		minimumCostPreemptionPointsEntry.combinePreemptionPoints(rightCostWPPreemptionPointsEntry);
	            			}

                    		if (_debugUpdateCostSolution == true)
        	                {
        	                	System.out.print("    JCBlock: computeConditionalSolutionOld: updating conditional section " + conditionalSection.getBlockName() + " minimum both WP cost (" + leftNPRValue + "," + rightNPRValue + ") = " + sectionCostValue + " preemption points ");
        	                	minimumCostPreemptionPointsEntry.displayObjectInformation();
        	                	System.out.println();
        	                }
	            		}
	            	}
	            	
            		// Check to see if the new minimum cost value for the current section exceeds the current maximum value.
        			if (maximumCostValue < minimumCostValue)
        			{
        				maximumCostValue = minimumCostValue;
                		maximumCostPreemptionPointsEntry = minimumCostPreemptionPointsEntry;

                		if (_debugUpdateCostSolution == true)
    	                {
    	                	System.out.print("    JCBlock: computeConditionalSolutionOld: updating conditional section " + conditionalSection.getBlockName() + " maximum both WP cost (" + leftNPRValue + "," + rightNPRValue + ") = " + minimumCostValue + " preemption points ");
    	                	maximumCostPreemptionPointsEntry.displayObjectInformation();
    	                	System.out.println();
    	                }
        			}
				}
				
				// Update the current cost map entry with the maximum computed cost.
				if (maximumCostValue != Integer.MAX_VALUE)
				{
	        		costKey = new JCCostKey(leftNPRValue, rightNPRValue);
            		costKey.setLeftBasicBlock(leftBasicBlock.getBBlockID());
            		costKey.setRightBasicBlock(rightBasicBlock.getBBlockID());
            		costSolutionEntry = new JCCostSolution();
            		costSolutionEntry.setSolutionCost(maximumCostValue);
            		costSolutionEntry.setSolutionKey(costKey);
            		
            		endingBasicBlockID = (JCBasicBlock.getStartingBasicBlock().getBBlockID());
            		if (predecessorSection.hasBasicBlock(endingBasicBlockID) == true)
            		{
            		    costSolutionEntry.addPreemptionPoint(endingBasicBlockID);
            		}
            		endingBasicBlockID = (JCBasicBlock.getEndingBasicBlock().getBBlockID());
            		if (successorSection.hasBasicBlock(endingBasicBlockID) == true)
            		{
            		    costSolutionEntry.addPreemptionPoint(endingBasicBlockID);
            		}
            		costSolutionEntry.combinePreemptionPoints(maximumCostPreemptionPointsEntry);
            		putCostSolution(costSolutionEntry, true);
	        		
	                if (_debugAddCostSolution == true)
	                {
	                	System.out.print("    JCBlock: computeConditionalSolutionOld: adding maximum cost (" + leftNPRValue + "," + rightNPRValue + ") = " + maximumCostValue + " preemption points ");
	                	maximumCostPreemptionPointsEntry.displayObjectInformation();
	                	System.out.println();
	                }
                }

            	rightIndex++;
            	if (rightIndex <= rightQListSize)
            	{
                    rightBasicBlock = successorBasicBlock.getSuccessorQListBasicBlockAtIndex((rightIndex-1));
                    rightNPRValue += rightBasicBlock.getBBlockWCET();
            	}
            } while (rightIndex <= rightQListSize);
            
            leftIndex++;
        	if (leftIndex <= leftQListSize)
        	{
        		// Only increment the left non-preemptive region value once the left basic block 
        		// has reached the left most basic block for this section.
        		if (adjustLeftNPRValue == true)
        		{
        		    leftNPRValue += leftBasicBlock.getBBlockWCET();
        		}
    		    leftBasicBlock = predecessorBasicBlock.getPredecessorQListBasicBlockAtIndex((leftIndex-1));
        	}
		} while (leftIndex <= leftQListSize);
		
        if (_debugStartEndBasicBlock == true)
        {
        	System.out.println("    JCBlock: computeConditionalSolutionOld: Block " + this.getBlockName() + " processing next basic block " + successorBasicBlock.getBBlockName() + " completed");
        	System.out.println();
        }
        
        // Clean up the previous cost map and preemption points map information 
        // as we no longer need these data structures.  
        oldSolutionMap.clear();
        oldSolutionMap = null;
	}
	
	/**
	 * Displays the input preemption point solutions for debugging purposes.
	 *
	 * @param  predecessorBlock       the predecessor block object to the conditional block
	 *                                object
	 *               
	 * @param  conditionalBlock       the conditional block object to compute the preemption cost
	 *                                solution with respect to the predecessor basic block and
	 *                                successor basic block
	 *               
	 * @param  successorBlock         the successor block object to the conditional block
	 *                                object
	 *               
	 * @see                           JCBlock
	 * @see                           JCCostKey
	 * @see                           JCCostSolution
	 * @see                           JCConditionalBlock
	 * @see                           JCConditionalSection
	 * @see                           JCSubBlock
	 */
	public void displayPreemptionSolutionSizes(JCSubBlock predecessorBlock, JCConditionalBlock conditionalBlock, JCSubBlock successorBlock)
	{
		JCConditionalSection conditionalSection;
		int condSectionIndex;
		int condSectionSize;
		JCCostKey existingLeftCostKey;
		JCCostSolution existingLeftCostSolution;
		JCCostKey existingMiddleCostKey;
		JCCostSolution existingMiddleCostSolution;
		JCCostKey existingRightCostKey;
		JCCostSolution existingRightCostSolution;
		Iterator<JCCostSolution> leftPredecessorMapIterator;
		Iterator<JCCostSolution> middleConditionalSectionMapIterator;
		Iterator<JCCostSolution> rightSuccessorMapIterator;
		int solutionNumber;

		if (predecessorBlock != null)
		{
			System.out.println("The number of predecessor solutions is " + predecessorBlock.getSolutionMap().size());
			solutionNumber = 1;
			leftPredecessorMapIterator = predecessorBlock.getSolutionMap().iterator();
			while (leftPredecessorMapIterator.hasNext() == true)
			{
				existingLeftCostSolution = leftPredecessorMapIterator.next();
				existingLeftCostKey = existingLeftCostSolution.getSolutionKey();
				System.out.println("    Solution " + solutionNumber + " (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex())  + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ") number of preemption solutions is " + existingLeftCostSolution.numberOfPreemptionPointSolutions());
				solutionNumber++;
			}
		}
		
		if (conditionalBlock != null)
		{
	    	condSectionSize = (int)conditionalBlock.numberOfConditionalSections();
	    	System.out.println("The number of conditional sections is " + condSectionSize);
			for (condSectionIndex = 0; condSectionIndex < condSectionSize; condSectionIndex++)
			{
				conditionalSection = conditionalBlock.getConditionalSectionAtIndex(condSectionIndex);
				System.out.println("The number of condition section " + (condSectionIndex+1) + " solutions is " + conditionalSection.getSolutionMap().size());
				solutionNumber = 1;
	            middleConditionalSectionMapIterator = conditionalSection.getSolutionMap().iterator();
				while (middleConditionalSectionMapIterator.hasNext() == true)
				{
					existingMiddleCostSolution = middleConditionalSectionMapIterator.next();
					existingMiddleCostKey = existingMiddleCostSolution.getSolutionKey();
					System.out.println("    Solution " + solutionNumber + " (" + existingMiddleCostKey.getLeftIndex() + ","  + (existingMiddleCostKey.getRightIndex())  + ","  + (existingMiddleCostKey.getLeftBasicBlock()) + ","  + (existingMiddleCostKey.getRightBasicBlock()) + ") number of preemption solutions is " + existingMiddleCostSolution.numberOfPreemptionPointSolutions());
					solutionNumber++;
				}
			}
		}
		
		if (successorBlock != null)
		{
			System.out.println("The number of successor solutions is " + successorBlock.getSolutionMap().size());
			solutionNumber = 1;
	        rightSuccessorMapIterator = successorBlock.getSolutionMap().iterator();
			while (rightSuccessorMapIterator.hasNext() == true)
			{
				existingRightCostSolution = rightSuccessorMapIterator.next();
				existingRightCostKey = existingRightCostSolution.getSolutionKey();
				System.out.println("    Solution " + solutionNumber + " (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex())  + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ") number of preemption solutions is " + existingRightCostSolution.numberOfPreemptionPointSolutions());
				solutionNumber++;
			}
		}
	}
	
	/**
	 * Computes the potential preemption point solutions for the specified basic
	 * block object. 
	 *
	 * @param  predecessorBlock       the predecessor block object to the conditional block
	 *                                object
	 *               
	 * @param  conditionalBlock       the conditional block object to compute the preemption cost
	 *                                solution with respect to the predecessor basic block and
	 *                                successor basic block
	 *               
	 * @param  successorBlock         the successor block object to the conditional block
	 *                                object
	 *               
	 * @param  pcmMatrix              the preemption cost matrix used to compute the preemption cost
	 *                                solution
	 *               
	 * @see                           ArrayList
	 * @see                           JCBasicBlock
	 * @see                           JCBlock
	 * @see                           JCCostKey
	 * @see                           JCCostSolution
	 * @see                           JCConditionalBlock
	 * @see                           JCConditionalSection
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeConditionalSolution(JCSubBlock predecessorBlock, JCConditionalBlock conditionalBlock, JCSubBlock successorBlock, JCPreemptionCostMatrix pcmMatrix)
	{
		JCConditionalSection conditionalSection;
		int condSectionIndex;
		int condSectionSize;
		JCCostKey existingCostKey;
		JCCostSolution existingCostSolution;
		int existingCostValue;
		JCCostKey existingLeftCostKey;
		JCCostSolution existingLeftCostSolution;
		JCCostKey existingMiddleCostKey;
		JCCostSolution existingMiddleCostSolution;
		JCCostKey existingRightCostKey;
		JCCostSolution existingRightCostSolution;
		boolean first_time;
		int leftIterationCount = 0;
        int leftMaximumPreemptionCost;
		Iterator<JCCostSolution> leftPredecessorMapIterator;
		int leftPredecessorNPRValue;
	    JCBasicBlock leftPredecessorBasicBlock;
	    JCBasicBlock leftPredecessorQBasicBlock;
		JCSectionBlock leftPredecessorSection;
		int leftPredecessorSectionID;
		JCPreemptionPoints leftPreemptionPoints;
		Iterator<JCPreemptionPoints> leftPreemptionPointSolutionIterator;
		int leftRightMaximumPreemptionCost;
	    JCBasicBlock leftSuccessorBasicBlock;
		int leftSuccessorNPRValue;
	    JCBasicBlock leftSuccessorQBasicBlock;
		JCSectionBlock leftSuccessorSection;
		int leftSuccessorSectionID;
		ArrayList<Integer> leftVisiblePreemptionPoints;
		JCCostKey maximumCostKey;
		JCPreemptionPoints maximumCostPreemptionPoints;
		Iterator<JCPreemptionPoints> maximumSectionPreemptionPointsIterator;
		JCCostSolution maximumCostSolution;
		int maximumCostValue;
		ArrayList<JCPreemptionPoints> maximumPreemptionPointsList;
		Iterator<JCCostSolution> middleConditionalSectionMapIterator;
		int middlePredecessorNPRValue;
	    JCBasicBlock middlePredecessorBasicBlock;
	    JCBasicBlock middlePredecessorQBasicBlock;
		JCSectionBlock middlePredecessorSection;
		int middlePredecessorSectionID;
		JCPreemptionPoints middlePreemptionPoints;
		Iterator<JCPreemptionPoints> middlePreemptionPointSolutionIterator;
	    JCBasicBlock middleSuccessorBasicBlock;
		int middleSuccessorNPRValue;
	    JCBasicBlock middleSuccessorQBasicBlock;
     	JCSectionBlock middleSuccessorSection;
		int middleSuccessorSectionID;
		ArrayList<Integer> middleLeftVisiblePreemptionPoints;
		ArrayList<Integer> middleRightVisiblePreemptionPoints;
		JCCostKey minimumConditionalCostKey;
		JCCostSolution minimumConditionalCostSolution;
		JCPreemptionPoints minimumConditionalPreemptionPoints;
		int minimumConditionalCostValue;
		Iterator<JCPreemptionPoints> minimumPreemptionPointsIterator;
		JCCostKey minimumSectionCostKey;
		JCCostSolution minimumSectionCostSolution;
		JCPreemptionPoints minimumSectionPreemptionPoints;
		Iterator<JCPreemptionPoints> minimumSectionPreemptionPointsIterator;
		int minimumSectionCostValue;
		JCPreemptionPoints newMaximumPreemptionPoints;
        int preemptionCost;
        int rightMaximumPreemptionCost;
        JCBasicBlock rightPredecessorBasicBlock;
		int rightPredecessorNPRValue;
	    JCBasicBlock rightPredecessorQBasicBlock;
		JCSectionBlock rightPredecessorSection;
		int rightPredecessorSectionID;
		JCPreemptionPoints rightPreemptionPoints;
		Iterator<JCPreemptionPoints> rightPreemptionPointSolutionIterator;
	    JCBasicBlock rightSuccessorBasicBlock;
		Iterator<JCCostSolution> rightSuccessorMapIterator;
		int rightSuccessorNPRValue;
	    JCBasicBlock rightSuccessorQBasicBlock;
		JCSectionBlock rightSuccessorSection;
		int rightSuccessorSectionID;
		ArrayList<Integer> rightVisiblePreemptionPoints;
		int sectionCostValue;
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		ArrayList<JCCostSolution> solutionMap;
		int solutionPreemptionCost;
		JCSubBlock subBlock;
		boolean validSectionSolutions;
		boolean validSolution;
		JCBasicBlock visiblePPBasicBlock;
		
		if (_debugInputPreemptionSolutionSizes == true)
		{
            System.out.println("computeConditionalSolution(): input preemption solution sizes:");
			displayPreemptionSolutionSizes(predecessorBlock, conditionalBlock, successorBlock);
		}
		
        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
        leftPredecessorSectionID = predecessorBlock.getStartingSectionID();
        leftPredecessorSection = JCSectionBlock.getSectionBlock(leftPredecessorSectionID);
        leftPredecessorBasicBlock = leftPredecessorSection.getLeftMostBasicBlock();
        rightPredecessorSectionID = successorBlock.getStartingSectionID();
        rightPredecessorSection = JCSectionBlock.getSectionBlock(rightPredecessorSectionID);
        rightPredecessorBasicBlock = rightPredecessorSection.getLeftMostBasicBlock();
        leftSuccessorSectionID = predecessorBlock.getEndingSectionID();
        leftSuccessorSection = JCSectionBlock.getSectionBlock(leftSuccessorSectionID);
        leftSuccessorBasicBlock = leftSuccessorSection.getRightMostBasicBlock();
        rightSuccessorSectionID = successorBlock.getEndingSectionID();
        rightSuccessorSection = JCSectionBlock.getSectionBlock(rightSuccessorSectionID);
        rightSuccessorBasicBlock = rightSuccessorSection.getRightMostBasicBlock();
		
        leftPredecessorMapIterator = predecessorBlock.getSolutionMap().iterator();
        if (_debugCostMapSizes == true)
        {
        	if (predecessorBlock.getSolutionMap().size() == 0)
        	{
                System.out.println("computeConditionalSolution(): predecessorBlock cost map size " + predecessorBlock.getSolutionMap().size() + " sub-block id " + predecessorBlock.getSubBlockID() + " sub-block name " + predecessorBlock.getSubBlockName());
        	}
        }
        
        // Process the left or predecessor solutions.
		while (leftPredecessorMapIterator.hasNext() == true)
		{
	        if (_debugConditionalSolutionProgress == true)
	        {
	            if ((leftIterationCount % 10) == 0)
	            {
	            	System.out.print(".");
	            }
	        }
	        
			existingLeftCostSolution = leftPredecessorMapIterator.next();
			existingLeftCostKey = existingLeftCostSolution.getSolutionKey();
			leftPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLeftCostKey.getLeftBasicBlock());
	        leftPredecessorNPRValue = existingLeftCostKey.getLeftIndex();
            leftSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLeftCostKey.getRightBasicBlock());
            leftSuccessorNPRValue = existingLeftCostKey.getRightIndex();
			
        	if (_debugNextBasicBlockPair == true)
            {
            	System.out.println("    JCBlock: computeConditionalSolution: Block " + this.getBlockName() + " processing next left predecessor basic block " + leftPredecessorQBasicBlock.getBBlockName() + " next left successor basic block " + leftSuccessorQBasicBlock.getBBlockName());
            }

	        rightSuccessorMapIterator = successorBlock.getSolutionMap().iterator();
	        if (_debugCostMapSizes == true)
	        {
	        	if (successorBlock.getSolutionMap().size() == 0)
	        	{
			        System.out.println("computeConditionalSolution(): successorBlock cost map size " + successorBlock.getSolutionMap().size());
	        	}
	        }
	        
	        // Process the right or successor solutions.
			while (rightSuccessorMapIterator.hasNext() == true)
			{
				existingRightCostSolution = rightSuccessorMapIterator.next();
				existingRightCostKey = existingRightCostSolution.getSolutionKey();
				rightSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingRightCostKey.getRightBasicBlock());
				rightSuccessorNPRValue = existingRightCostKey.getRightIndex();
      	        rightPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingRightCostKey.getLeftBasicBlock());
      	        rightPredecessorNPRValue = existingRightCostKey.getLeftIndex();
				
				// Compute the minimum cost value for the current conditional left predecessor and right successor cost key values.
				minimumConditionalCostValue = Integer.MAX_VALUE;
				minimumConditionalCostKey = null;
				minimumConditionalCostSolution = null;
				minimumConditionalPreemptionPoints = new JCPreemptionPoints(false);
				
            	if (_debugNextBasicBlockPair == true)
                {
                	System.out.println("    JCBlock: computeConditionalSolution: Block " + this.getBlockName() + " processing next right predecessor basic block " + rightPredecessorQBasicBlock.getBBlockName() + " next right successor basic block " + rightSuccessorQBasicBlock.getBBlockName());
                }
                
                // Compute the maximum cost value across all conditional section blocks.
				maximumCostValue = Integer.MIN_VALUE;
				maximumCostKey = null;
				maximumCostSolution = null;
				maximumCostPreemptionPoints = new JCPreemptionPoints(false);
				validSectionSolutions = true;
				
            	// Loop through all the conditional sections to compute the minimum cost for each.
            	condSectionSize = (int)conditionalBlock.numberOfConditionalSections();
    	        if (_debugCostMapSizes == true)
    	        {
    	        	if (condSectionSize == 0)
    	        	{
            	        System.out.println("computeConditionalSolution(): number of conditional sections " + condSectionSize);
    	        	}
    	        }
    	        
				for (condSectionIndex = 0; condSectionIndex < condSectionSize; condSectionIndex++)
				{
					conditionalSection = conditionalBlock.getConditionalSectionAtIndex(condSectionIndex);
					
					// Check for the case where the cost map has been computed for a subordinate
					// sub-block object.
					solutionMap = conditionalSection.getSolutionMap();
					if ((conditionalSection.numberOfSubBlocks() == 1) && (solutionMap.size() == 0))
					{
						subBlock = conditionalSection.getSubBlockAtIndex(0);
						solutionMap = subBlock.getSolutionMap();
						if (solutionMap.size() > 0)
						{
							conditionalSection.setSolutionMap(solutionMap);
                            if (_debugSectionCostMap == true)
                            {
                            	System.out.println("    JCBlock: computeConditionalSolution: costmap from " + subBlock.getSubBlockName() + " stored into conditional section " + conditionalSection.getConditionalSectionName() + " size = " + solutionMap.size());
                            	solutionIterator = solutionMap.iterator();
        		    			while (solutionIterator.hasNext() == true) 
        		    			{
        		    				existingCostSolution = solutionIterator.next();
        		    				existingCostKey = existingCostSolution.getSolutionKey();
                                	System.out.println("    JCBlock: computeConditionalSolution: costmap key " + existingCostKey);
                                	System.out.println("    JCBlock: computeConditionalSolution: costmap solution " + existingCostSolution.getSolutionCost());
        		    			}
                            }
						}
					}
					
			        middlePredecessorSectionID = conditionalSection.getStartingSectionID();
			        middlePredecessorSection = JCSectionBlock.getSectionBlock(middlePredecessorSectionID);
			        middlePredecessorBasicBlock = middlePredecessorSection.getLeftMostBasicBlock();
					middleSuccessorSectionID = conditionalSection.getEndingSectionID();
					middleSuccessorSection = JCSectionBlock.getSectionBlock(middleSuccessorSectionID);
					middleSuccessorBasicBlock = middleSuccessorSection.getRightMostBasicBlock();
	                
					// Compute the minimum cost value for the current conditional section block.
					minimumSectionCostValue = Integer.MAX_VALUE;
					minimumSectionCostKey = null;
					minimumSectionCostSolution = null;
					minimumSectionPreemptionPoints = new JCPreemptionPoints(false);
					
                    if (_debugSolutionCheck == true)
                    {
                    	System.out.println("    JCBlock: computeConditionalSolution: considering solutions for conditional section " + conditionalSection.getConditionalSectionName());
                    }
                    
                	if (_debugSearchOldSolutionCheck == true)
                    {
                    	System.out.println("    JCBlock: computeConditionalSolution: **Searching old left solution for (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex()) + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ")**");
                    }

                    if (existingLeftCostSolution != null)
                    {
                		// Process the previous preemption based solutions generated.
                        if (_debugOldSolutionCheck == true)
                        {
                        	System.out.println("    JCBlock: computeConditionalSolution: **Processing old left solution for (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex())  + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ")**");
                        }
                        
    	                if (_debugCostMapSolution == true)
    	                {
    	                	System.out.println("    JCBlock: computeConditionalSolution: left costmap (" + leftPredecessorNPRValue + "," + leftSuccessorNPRValue + ") = " + existingLeftCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingLeftCostSolution.numberOfPreemptionPointSolutions());
    	                }

    	                leftPreemptionPointSolutionIterator = existingLeftCostSolution.getPreemptionPointSolutionsIterator();
    	                while (leftPreemptionPointSolutionIterator.hasNext() == true)
    	                {
    	                	leftPreemptionPoints = leftPreemptionPointSolutionIterator.next();
            	            if (_debugSolutionPreemptionPoints == true)
            	            {
            	                System.out.print("    JCBlock: computeConditionalSolution: Existing left costmap solution preemption points: ");
            	                leftPreemptionPoints.displayObjectInformation();
        		    	    	System.out.println();
        	                }
    		    	    	
        	                leftVisiblePreemptionPoints = existingLeftCostSolution.getVisibleEndingPPs(predecessorBlock, leftPreemptionPoints);
        	                
  	    	                if (_debugVisiblePreemptionPoints == true)
  	    	                {
            	                System.out.print("    JCBlock: computeConditionalSolution: Left visible ending preemption points (size=" + leftVisiblePreemptionPoints.size() + "): ");
            	                if (leftVisiblePreemptionPoints.size() > 0)
            	                {
            		    			for (Integer item : leftVisiblePreemptionPoints) 
            		    			{
              	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
              	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
            		    			}
            	                }
            	                System.out.println();
  	    	                }
  	    	                
                        	if (_debugSearchOldSolutionCheck == true)
                            {
                            	System.out.println("    JCBlock: computeConditionalSolution: **Searching old right solution for (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex()) + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ")**");
                            }

                            if (existingRightCostSolution != null)
                            {
                        		// Process the previous preemption based solutions generated.
                                if (_debugOldSolutionCheck == true)
                                {
                                	System.out.println("    JCBlock: computeConditionalSolution: **Processing old right solution for (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex())  + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ")**");
                                }
                                
            	                if (_debugCostMapSolution == true)
            	                {
            	                	System.out.println("    JCBlock: computeConditionalSolution: right costmap (" + rightPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + existingRightCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingRightCostSolution.numberOfPreemptionPointSolutions());
            	                }
                    		    
            	                rightPreemptionPointSolutionIterator = existingRightCostSolution.getPreemptionPointSolutionsIterator();
            	                while (rightPreemptionPointSolutionIterator.hasNext() == true)
            	                {
            	                	rightPreemptionPoints = rightPreemptionPointSolutionIterator.next();
                	                if (_debugSolutionPreemptionPoints == true)
                	                {
                    	                System.out.print("    JCBlock: computeConditionalSolution: Existing right costmap solution preemption points: ");
                    	                rightPreemptionPoints.displayObjectInformation();
                		    	    	System.out.println();
                	                }
            		    	    	
                	                rightVisiblePreemptionPoints = existingRightCostSolution.getVisibleStartingPPs(successorBlock, rightPreemptionPoints);
                	                
          	    	                if (_debugVisiblePreemptionPoints == true)
          	    	                {
                    	                System.out.print("    JCBlock: computeConditionalSolution: Right visible starting preemption points (size=" + rightVisiblePreemptionPoints.size() + "): ");
                    	                if (rightVisiblePreemptionPoints.size() > 0)
                    	                {
                    		    			for (Integer item : rightVisiblePreemptionPoints) 
                    		    			{
                      	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                      	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                    		    			}
                    	                }
                    	                System.out.println();
          	    	                }
               	                
                                    middleConditionalSectionMapIterator = conditionalSection.getSolutionMap().iterator();
                        	        if (_debugCostMapSizes == true)
                        	        {
                        	        	if (conditionalSection.getSolutionMap().size() == 0)
                        	        	{
                                	        System.out.println("computeConditionalSolution(): conditionalSection " + conditionalSection.getConditionalSectionName() + " cost map size " + conditionalSection.getSolutionMap().size() + " subblock count " + conditionalSection.numberOfSubBlocks());
                        	        	}
                        	        }
                        	        
                        	        // Process the middle conditional section solutions.
                        			while (middleConditionalSectionMapIterator.hasNext() == true)
                        			{
                        				existingMiddleCostSolution = middleConditionalSectionMapIterator.next();
                        				existingMiddleCostKey = existingMiddleCostSolution.getSolutionKey();
                        				middleSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingMiddleCostKey.getRightBasicBlock());
                        				middleSuccessorNPRValue = existingMiddleCostKey.getRightIndex();
                              	        middlePredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingMiddleCostKey.getLeftBasicBlock());
                              	        middlePredecessorNPRValue = existingMiddleCostKey.getLeftIndex();
                        				
                                    	if (_debugNextBasicBlockPair == true)
                                        {
                                        	System.out.println("    JCBlock: computeConditionalSolution: Block " + this.getBlockName() + " processing next middle predecessor basic block " + middlePredecessorQBasicBlock.getBBlockName() + " next middle successor basic block " + middleSuccessorQBasicBlock.getBBlockName());
                                        }

                                    	if (_debugSearchOldSolutionCheck == true)
                                        {
                                        	System.out.println("    JCBlock: computeConditionalSolution: **Searching old middle solution for (" + existingMiddleCostKey.getLeftIndex() + ","  + (existingMiddleCostKey.getRightIndex()) + ","  + (existingMiddleCostKey.getLeftBasicBlock()) + ","  + (existingMiddleCostKey.getRightBasicBlock()) + ")**");
                                        }

                                        if (existingMiddleCostSolution != null)
                                        {
                                		    // Process the previous preemption based solutions generated.
                                            if (_debugOldSolutionCheck == true)
                                            {
                                            	System.out.println("    JCBlock: computeConditionalSolution: **Processing old middle solution for (" + existingMiddleCostKey.getLeftIndex() + ","  + (existingMiddleCostKey.getRightIndex())  + ","  + (existingMiddleCostKey.getLeftBasicBlock()) + ","  + (existingMiddleCostKey.getRightBasicBlock()) + ")**");
                                            }

            	                            if (_debugCostMapSolution == true)
            	                            {
            	                            	System.out.println("    JCBlock: computeConditionalSolution: middle costmap (" + middlePredecessorNPRValue + "," + middleSuccessorNPRValue + ") = " + existingMiddleCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingMiddleCostSolution.numberOfPreemptionPointSolutions());
                                            }

                        	                middlePreemptionPointSolutionIterator = existingMiddleCostSolution.getPreemptionPointSolutionsIterator();
                        	                while (middlePreemptionPointSolutionIterator.hasNext() == true)
                        	                {
                        	                	middlePreemptionPoints = middlePreemptionPointSolutionIterator.next();
                                	            if (_debugSolutionPreemptionPoints == true)
                                	            {
                                	                System.out.print("    JCBlock: computeConditionalSolution: Existing middle costmap solution preemption points: ");
                                	                middlePreemptionPoints.displayObjectInformation();
                            		    	    	System.out.println();
                            	                }
                        		    	    	
                            	                middleLeftVisiblePreemptionPoints = existingMiddleCostSolution.getVisibleStartingPPs(conditionalSection, middlePreemptionPoints);
                            	                
                      	    	                if (_debugVisiblePreemptionPoints == true)
                      	    	                {
                                	                System.out.print("    JCBlock: computeConditionalSolution: Middle left visible starting preemption points (size=" + middleLeftVisiblePreemptionPoints.size() + "): ");
                                	                if (middleLeftVisiblePreemptionPoints.size() > 0)
                                	                {
                                		    			for (Integer item : middleLeftVisiblePreemptionPoints) 
                                		    			{
                                  	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                                  	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                                		    			}
                                	                }
                                	                System.out.println();
                      	    	                }
                            	                
                            	                leftMaximumPreemptionCost = 0;
                            	                if ((leftVisiblePreemptionPoints.size() > 0) && (middleLeftVisiblePreemptionPoints.size() > 0))
                            	                {
                            		    			for (Integer leftItem : leftVisiblePreemptionPoints) 
                            		    			{
                                		    			for (Integer middleItem : middleLeftVisiblePreemptionPoints) 
                                		    			{
                                		            		preemptionCost = pcmMatrix.getMatrixEntry(leftItem.intValue(), 
                                		            				                                  middleItem.intValue());
                                  	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
                                  	                        
                                  	                        if (leftMaximumPreemptionCost < preemptionCost)
                                  	                        {
                                  	                        	leftMaximumPreemptionCost = preemptionCost;
                                  	                        }
                                		    			}
                            		    			}
                            	    			}
                                                   	
                            	                if (_debugMaximumPreemptionCost == true)
                            	                {
                                	                System.out.println("    JCBlock: computeConditionalSolution: Left maximum preemption cost = " + leftMaximumPreemptionCost);
                            	                }
                            	                
                            	                middleRightVisiblePreemptionPoints = existingMiddleCostSolution.getVisibleEndingPPs(conditionalSection, middlePreemptionPoints);
                            	                
                      	    	                if (_debugVisiblePreemptionPoints == true)
                      	    	                {
                                	                System.out.print("    JCBlock: computeConditionalSolution: Middle right visible ending preemption points (size=" + middleRightVisiblePreemptionPoints.size() + "): ");
                                	                if (middleRightVisiblePreemptionPoints.size() > 0)
                                	                {
                                		    			for (Integer item : middleRightVisiblePreemptionPoints) 
                                		    			{
                                  	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                                  	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                                		    			}
                                	                }
                                	                System.out.println();
                      	    	                }
                            	                
                      	    	                rightMaximumPreemptionCost = 0;
                            	                if ((rightVisiblePreemptionPoints.size() > 0) && (middleRightVisiblePreemptionPoints.size() > 0))
                            	                {
                            		    			for (Integer rightItem : rightVisiblePreemptionPoints) 
                            		    			{
                                		    			for (Integer middleItem : middleRightVisiblePreemptionPoints) 
                                		    			{
                                		            		preemptionCost = pcmMatrix.getMatrixEntry(middleItem.intValue(), 
                                		            				                                  rightItem.intValue());
                                  	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
                                  	                        
                                  	                        if (rightMaximumPreemptionCost < preemptionCost)
                                  	                        {
                                  	                        	rightMaximumPreemptionCost = preemptionCost;
                                  	                        }
                                		    			}
                            		    			}
                            	    			}
                            	                
                              	                if (_debugMaximumPreemptionCost == true)
                            	                {
                                	                System.out.println("    JCBlock: computeConditionalSolution: Right maximum preemption cost = " + rightMaximumPreemptionCost);
                            	                }
                              	                
       	                                        // Check to ensure that maximum non-preemptive region constraint is satisfied.
                                                //if (((leftSuccessorNPRValue + leftMaximumPreemptionCost + middlePredecessorNPRValue) <= JCBlock.getQValue()) &&
                                                //    ((middleSuccessorNPRValue + rightMaximumPreemptionCost + rightPredecessorNPRValue) <= JCBlock.getQValue()))
                              	                validSolution = (((existingLeftCostKey.getRightIndex() + leftMaximumPreemptionCost + existingMiddleCostKey.getLeftIndex()) <= JCBlock.getQValue()) &&
                                                                 ((existingMiddleCostKey.getRightIndex() + rightMaximumPreemptionCost + existingRightCostKey.getLeftIndex()) <= JCBlock.getQValue()));
                              	                
                              	                if (_debugNPRRegionCheck == true)
                            	                {
                                	                System.out.println("    JCBlock: computeConditionalSolution: Q = " + JCBlock.getQValue() + " Left NPR cost (" + existingLeftCostKey.getRightIndex() + "," + leftMaximumPreemptionCost + "," + existingMiddleCostKey.getLeftIndex() + ") = " + (existingLeftCostKey.getRightIndex() + leftMaximumPreemptionCost + existingMiddleCostKey.getLeftIndex()) +
                                	                                   " Right NPR cost (" + existingMiddleCostKey.getRightIndex() + "," + rightMaximumPreemptionCost + "," + existingRightCostKey.getLeftIndex() + ") = " + (existingMiddleCostKey.getRightIndex() + rightMaximumPreemptionCost + existingRightCostKey.getLeftIndex()) + " validSolution = " + validSolution);
                            	                }
                              	                
                              	                solutionPreemptionCost = leftMaximumPreemptionCost + rightMaximumPreemptionCost;
                              	                
                                            	leftRightMaximumPreemptionCost = -1;
                                                if ((middleLeftVisiblePreemptionPoints.size() == 0) && (middleRightVisiblePreemptionPoints.size() == 0) &&
                                                	(leftVisiblePreemptionPoints.size() > 0) && (rightVisiblePreemptionPoints.size() > 0) && (validSolution == false))
                                                {
                                  	                if (_debugNPRRegionCheck == true)
                                	                {
                                	                    System.out.println("    JCBlock: computeConditionalSolution: Left visible PPs = " + leftVisiblePreemptionPoints.size() +
                     	                    		           " Middle left visible PPs = " + middleLeftVisiblePreemptionPoints.size() +
                     	                    		           " Middle right visible PPs = " + middleRightVisiblePreemptionPoints.size() +
                     	                    		           " Right visible PPs = " + rightVisiblePreemptionPoints.size());
                                	                }
                                  	                
                                                	leftRightMaximumPreemptionCost = 0;
                            		    			for (Integer leftItem : leftVisiblePreemptionPoints) 
                            		    			{
                                		    			for (Integer rightItem : rightVisiblePreemptionPoints) 
                                		    			{
                                		            		preemptionCost = pcmMatrix.getMatrixEntry(leftItem.intValue(), 
                                		            				                                  rightItem.intValue());
                                  	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
                                  	                        
                                  	                        if (leftRightMaximumPreemptionCost < preemptionCost)
                                  	                        {
                                  	                        	leftRightMaximumPreemptionCost = preemptionCost;
                                  	                        }
                                		    			}
                            		    			}
                                                   	
                            	                    if (_debugMaximumPreemptionCost == true)
                            	                    {
                                	                    System.out.println("    JCBlock: computeConditionalSolution: Left Right maximum preemption cost = " + leftRightMaximumPreemptionCost);
                            	                    }
                                  	                
           	                                        // Check to ensure that maximum non-preemptive region constraint is satisfied.
                                  	                validSolution = ((existingLeftCostKey.getRightIndex() + leftRightMaximumPreemptionCost + existingMiddleCostSolution.getSolutionCost() + existingRightCostKey.getLeftIndex()) <= JCBlock.getQValue());
                                  	                solutionPreemptionCost = leftRightMaximumPreemptionCost;
                                	                
                                  	                if (_debugNPRRegionCheck == true)
                                	                {
                                    	                System.out.print("    JCBlock: computeConditionalSolution: *Q = " + JCBlock.getQValue() + " Left Right NPR cost (" + existingLeftCostKey.getRightIndex() + "," + leftRightMaximumPreemptionCost + "," + existingMiddleCostSolution.getSolutionCost() + "," + existingRightCostKey.getLeftIndex() + ") = " + (existingLeftCostKey.getRightIndex() + leftRightMaximumPreemptionCost + existingMiddleCostSolution.getSolutionCost() + existingRightCostKey.getLeftIndex()) + " validSolution = " + validSolution + " middle preemption points = ");
                                    	                middlePreemptionPoints.displayObjectInformation();
                                    	                System.out.println("");
                                	                }
                                                }
                                                
                              	                if (validSolution == true)
                                                {
                         	            			sectionCostValue = existingLeftCostSolution.getSolutionCost() + 
                         	            					           solutionPreemptionCost +
                         	            					           existingMiddleCostSolution.getSolutionCost() + 
                         	            					           existingRightCostSolution.getSolutionCost();

                                  	                if (_debugConditionalSectionCost == true)
                                	                {
                                    	                System.out.println("    JCBlock: computeConditionalSolution: sectionCost = " + sectionCostValue + " minimumCostValue = " + minimumSectionCostValue);
                                	                }
                                  	                
                                  	                if (_debugConditionalPreemptionCost == true)
                                	                {
                                    	                System.out.println("    JCBlock: computeConditionalSolution: preemptionCost = " + solutionPreemptionCost + " leftMaximumPreemptionCost = " + leftMaximumPreemptionCost + " rightMaximumPreemptionCost = " + rightMaximumPreemptionCost + " leftRightMaximumPreemptionCost = " + leftRightMaximumPreemptionCost);
                                	                }
                                	                
                         	            			if (sectionCostValue < minimumSectionCostValue)
            	                        			{
                                    	                if (_debugSolutionCombinedCostKeys == true)
                                    	                {
                                        	                System.out.println("    JCBlock: computeConditionalSolution: Q = " + JCBlock.getQValue() + " Left cost key (" + existingLeftCostKey.getLeftIndex() + "," + existingLeftCostKey.getRightIndex() + "," + existingLeftCostKey.getLeftBasicBlock() + "," + existingLeftCostKey.getRightBasicBlock() + ")" +
                             	                		           " Middle cost key (" + existingMiddleCostKey.getLeftIndex() + "," + existingMiddleCostKey.getRightIndex() + "," + existingMiddleCostKey.getLeftBasicBlock() + "," + existingMiddleCostKey.getRightBasicBlock() + ")" +
       	                                                           " Right cost key (" + existingRightCostKey.getLeftIndex() + "," + existingRightCostKey.getRightIndex() + "," + existingRightCostKey.getLeftBasicBlock() + "," + existingRightCostKey.getRightBasicBlock()+ ")");
                                    	                }
                                    	                
                                    	                if (_debugSolutionVisiblePreemptionPoints == true)
                                    	                {
                                        	                System.out.print("    JCBlock: computeConditionalSolution: Middle right visible ending preemption points (size=" + middleRightVisiblePreemptionPoints.size() + "): ");
                                        	                if (middleRightVisiblePreemptionPoints.size() > 0)
                                        	                {
                                        		    			for (Integer item : middleRightVisiblePreemptionPoints) 
                                        		    			{
                                          	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                                          	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                                        		    			}
                                        	                }
                                        	                System.out.println();
                                        	                System.out.print("    JCBlock: computeConditionalSolution: Right visible starting preemption points (size=" + rightVisiblePreemptionPoints.size() + "): ");
                                        	                if (rightVisiblePreemptionPoints.size() > 0)
                                        	                {
                                        		    			for (Integer item : rightVisiblePreemptionPoints) 
                                        		    			{
                                          	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                                          	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                                        		    			}
                                        	                }
                                        	                System.out.println();
                                    	                }
                                    	                
            	                        				minimumSectionCostValue = sectionCostValue;
            	                                		minimumSectionCostKey = new JCCostKey(existingLeftCostKey.getLeftIndex(), existingRightCostKey.getRightIndex());
            	                                		minimumSectionCostKey.setLeftBasicBlock(leftPredecessorQBasicBlock.getBBlockID());
            	                                		minimumSectionCostKey.setRightBasicBlock(rightSuccessorQBasicBlock.getBBlockID());
            	                                		minimumSectionCostSolution = new JCCostSolution();
            	                                		minimumSectionCostSolution.setSolutionCost(sectionCostValue);
            	                                		minimumSectionCostSolution.setSolutionKey(minimumSectionCostKey);
            	                        			  	minimumSectionPreemptionPoints = new JCPreemptionPoints(false);
            	                        			  	minimumSectionPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
            	                        			  	minimumSectionPreemptionPoints.combinePreemptionPoints(middlePreemptionPoints);
            	                        			  	minimumSectionPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);
            	                                		minimumSectionCostSolution.combinePreemptionPoints(minimumSectionPreemptionPoints);
            	        	                    		if (_debugUpdateCostSolution == true)
            	        	        	                {
            	        	        	                	System.out.print("    JCBlock: computeConditionalSolution: updating conditional section " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + sectionCostValue + " minimum section cost preemption points size " + minimumSectionCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
            	        	        	                	minimumSectionPreemptionPoints.displayObjectInformation();
            	        	        	                	System.out.println();
            	        	        	                }
            	        	                    		minimumSectionCostSolution.addPreemptionPointSolution();
            	                        			}
                         	            			else if (sectionCostValue == minimumSectionCostValue)
                         	            			{
            	                        			  	minimumSectionPreemptionPoints = new JCPreemptionPoints(false);
            	                        			  	minimumSectionPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
            	                        			  	minimumSectionPreemptionPoints.combinePreemptionPoints(middlePreemptionPoints);
            	                        			  	minimumSectionPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);
            	                        			  	// Check to see if an existing visible preemption points solution exists
            	                        			  	if (minimumSectionCostSolution.hasVisiblePreemptionPointSolution(predecessorBlock, successorBlock, minimumSectionPreemptionPoints) == false)
            	                        			  	{
                	                        			  	minimumSectionCostSolution.clearPreemptionPoints();
                	                                		minimumSectionCostSolution.combinePreemptionPoints(minimumSectionPreemptionPoints);
                	        	                    		if (_debugUpdateCostSolution == true)
                	        	        	                {
                	        	        	                	System.out.print("    JCBlock: computeConditionalSolution: add preemption points for conditional section " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + sectionCostValue + " preemption points size " + minimumSectionCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
                	        	        	                	minimumSectionPreemptionPoints.displayObjectInformation();
                	        	        	                	System.out.println();
                	        	        	                }
                	        	                    		minimumSectionCostSolution.addPreemptionPointSolution();
            	                        			  	}
                         	            			}
                                                }
                                            }
                                        }
                                	}
            	                }
                            }
    	                }
                    }
                
                    first_time = false;
                    
    				// Check to see if the new minimum cost value for the current section exceeds the current maximum value.
    				if ((minimumSectionCostSolution != null) && (minimumSectionCostValue != Integer.MAX_VALUE))
    				{
        				if (maximumCostValue < minimumSectionCostValue)
        				{
        					maximumCostValue = minimumSectionCostValue;
        					
        					// First time simply use the minimum section solution.
        					if (maximumCostSolution == null)
        					{
                                maximumCostSolution = minimumSectionCostSolution;
                                maximumCostKey = minimumSectionCostKey;
                                first_time = true;
        					}
        					// Update the current maximum cost solution.
        					else
        					{
        						maximumCostSolution.setSolutionCost(maximumCostValue);
        					}
         				}
        				
        				// Combine the existing preemption point solutions to capture the solutions from other conditional sections.
        				if (first_time == false)
        				{
                    		if (_debugUpdateCostSolution == true)
        	                {
        	                	System.out.println("    JCBlock: computeConditionalSolution: add preemption points for conditional section " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumSectionCostValue + " maximum section preemption points size " + maximumCostSolution.numberOfPreemptionPointSolutions() + " minimum section preemption points size " + minimumSectionCostSolution.numberOfPreemptionPointSolutions());
        	                }

                    		if (_debugUpdateCostSolution == true)
        	                {
        	                	System.out.print("    JCBlock: computeConditionalSolution: existing preemption points for maximum cost solution " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumSectionCostValue + " maximum cost preemption points size " + maximumCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
        	                }
                    		maximumPreemptionPointsList = new ArrayList<JCPreemptionPoints>();
        					maximumSectionPreemptionPointsIterator = maximumCostSolution.getPreemptionPointSolutionsIterator();
        					while (maximumSectionPreemptionPointsIterator.hasNext() == true)
        					{
        						maximumCostPreemptionPoints = maximumSectionPreemptionPointsIterator.next();
                        		if (_debugUpdateCostSolution == true)
            	                {
                        			maximumCostPreemptionPoints.displayObjectInformation();
            	                }
        						maximumPreemptionPointsList.add(maximumCostPreemptionPoints);
        					}        						
                    		if (_debugUpdateCostSolution == true)
        	                {
        	                	System.out.println();
        	                }
        					maximumCostSolution.clearPreemptionPointSolutions();
        					
    						maximumSectionPreemptionPointsIterator = maximumPreemptionPointsList.iterator();
        					while (maximumSectionPreemptionPointsIterator.hasNext() == true)
        					{
        						maximumCostPreemptionPoints = maximumSectionPreemptionPointsIterator.next();
            					minimumSectionPreemptionPointsIterator = minimumSectionCostSolution.getPreemptionPointSolutionsIterator();
                        		if (_debugUpdateCostSolution == true)
            	                {
            	                	System.out.print("    JCBlock: computeConditionalSolution: existing preemption points for minimum section cost solution " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumSectionCostValue + " minimum section preemption points size " + minimumSectionCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
            	                }
            					while (minimumSectionPreemptionPointsIterator.hasNext() == true)
            					{
            						minimumSectionPreemptionPoints = minimumSectionPreemptionPointsIterator.next();
                            		if (_debugUpdateCostSolution == true)
                	                {
            	                	    minimumSectionPreemptionPoints.displayObjectInformation();
                	                }
            						newMaximumPreemptionPoints = new JCPreemptionPoints(false);
            						newMaximumPreemptionPoints.combinePreemptionPoints(maximumCostPreemptionPoints);
            						newMaximumPreemptionPoints.combinePreemptionPoints(minimumSectionPreemptionPoints);
            						if (maximumCostSolution.hasVisiblePreemptionPointSolution(predecessorBlock, successorBlock, newMaximumPreemptionPoints) == false)
            						{
                						maximumCostSolution.addPreemptionPointSolution(newMaximumPreemptionPoints);
            						}
            					}
                        		if (_debugUpdateCostSolution == true)
            	                {
            	                	System.out.println();
            	                }
        					}        						
                    		if (_debugUpdateCostSolution == true)
        	                {
        	                	System.out.println("    JCBlock: computeConditionalSolution: add preemption points for conditional section " + conditionalSection.getBlockName() + " minimum WOP cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumSectionCostValue + " maximum section preemption points size " + maximumCostSolution.numberOfPreemptionPointSolutions());
        	                }
        				}
        				
    					if (_debugUpdateCostSolution == true)
    		            {
    		            	System.out.print("    JCBlock: computeConditionalSolution: updating conditional section " + conditionalSection.getBlockName() + " maximum cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + maximumCostValue + " maximum cost preemption points size " + maximumCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
        					maximumSectionPreemptionPointsIterator = maximumCostSolution.getPreemptionPointSolutionsIterator();
        					while (maximumSectionPreemptionPointsIterator.hasNext() == true)
        					{
        						maximumCostPreemptionPoints = maximumSectionPreemptionPointsIterator.next();
        						maximumCostPreemptionPoints.displayObjectInformation();
        					}
    		            	System.out.println();
    		            }
    				}
    				else
    				{
    					validSectionSolutions = false;
    				}
				} // Conditional Sections loop

				// Update the current cost map entry with the maximum computed cost.
				if ((maximumCostSolution != null) && (maximumCostValue != Integer.MAX_VALUE) && (validSectionSolutions == true))
				{					
			        if (_debugAddMaximumCostSolution == true)
			        {
			        	System.out.print("    JCBlock: computeConditionalSolution: adding maximum cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + maximumCostValue + " preemption points size " + maximumCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
    					maximumSectionPreemptionPointsIterator = maximumCostSolution.getPreemptionPointSolutionsIterator();
    					while (maximumSectionPreemptionPointsIterator.hasNext() == true)
    					{
    						maximumCostPreemptionPoints = maximumSectionPreemptionPointsIterator.next();
    			        	maximumCostPreemptionPoints.displayObjectInformation();
    					}
			        	System.out.println();
			        }
					
					minimumConditionalCostValue = maximumCostValue;
					minimumConditionalCostKey = maximumCostKey;
					minimumConditionalCostSolution = maximumCostSolution;
					minimumConditionalPreemptionPoints = maximumCostPreemptionPoints;
					
                    existingCostSolution = getCostSolution(minimumConditionalCostKey);
                    if (existingCostSolution != null)
                    {
                    	existingCostValue = existingCostSolution.getSolutionCost();
                    	if (existingCostValue > minimumConditionalCostValue)
                    	{
                    		existingCostSolution.setSolutionCost(minimumConditionalCostValue);
        					if (_debugAddMinimumCostSolution == true)
        			        {
        			        	System.out.print("    JCBlock: computeConditionalSolution: adding minimum conditional cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumConditionalCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
        			        }
                    		existingCostSolution.clearPreemptionPointSolutions();
                    		minimumPreemptionPointsIterator = minimumConditionalCostSolution.getPreemptionPointSolutionsIterator();
                    		while (minimumPreemptionPointsIterator.hasNext() == true)
                    		{
                    			minimumConditionalPreemptionPoints = minimumPreemptionPointsIterator.next();
                    		    existingCostSolution.addPreemptionPointSolution(minimumConditionalPreemptionPoints);
	        					if (_debugAddMinimumCostSolution == true)
	        			        {
	        			        	minimumConditionalPreemptionPoints.displayObjectInformation();
	        			        }
                    		}
        					if (_debugAddMinimumCostSolution == true)
        			        {
        			        	System.out.println();
        			        }
                    	}
                    	else if (existingCostValue == minimumConditionalCostValue)
                    	{
        					if (_debugSupportMultiplePreemptionPoints == true)
        					{
            					if (_debugAddMinimumCostSolution == true)
            			        {
            			        	System.out.print("    JCBlock: computeConditionalSolution: add preemption points for minimum conditional cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumConditionalCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
            			        }
                        		minimumPreemptionPointsIterator = minimumConditionalCostSolution.getPreemptionPointSolutionsIterator();
                        		while (minimumPreemptionPointsIterator.hasNext() == true)
                        		{
                        			minimumConditionalPreemptionPoints = minimumPreemptionPointsIterator.next();
                        			if (existingCostSolution.hasVisiblePreemptionPointSolution(predecessorBlock, successorBlock, minimumConditionalPreemptionPoints) == false)
                        			{
                            		    existingCostSolution.addPreemptionPointSolution(minimumConditionalPreemptionPoints);
        	        					if (_debugAddMinimumCostSolution == true)
        	        			        {
        	        			        	minimumConditionalPreemptionPoints.displayObjectInformation();
        	        			        }
                        			}
                        		}
            					if (_debugAddMinimumCostSolution == true)
            			        {
            			        	System.out.println();
            			        }
        					}
                    	}
                    }
                    else
                    {
    					putCostSolution(minimumConditionalCostSolution, true);

    					if (_debugAddMinimumCostSolution == true)
    			        {
    			        	System.out.print("    JCBlock: computeConditionalSolution: adding minimum conditional cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumConditionalCostValue + " preemption points size " + minimumConditionalCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
                    		minimumPreemptionPointsIterator = minimumConditionalCostSolution.getPreemptionPointSolutionsIterator();
                    		while (minimumPreemptionPointsIterator.hasNext() == true)
                    		{
                    			minimumConditionalPreemptionPoints = minimumPreemptionPointsIterator.next();
        			        	minimumConditionalPreemptionPoints.displayObjectInformation();
                    		}
    			        	System.out.println();
    			        }
                    }
			    }
            }
			leftIterationCount++;
		} 
		
		// Display the computed block solutions if necessary.
		if (_displayBlockSolutions == true)
		{
			displayBlockSolution();
		}
		else
		{
	    	if (_debugDisplayPreemptionBlockNumberOfSolutions == true)
	    	{
	            System.out.println("    JCBlock: computeConditionalSolution: Number of minimum cost solutions for block " + getBlockName() + " is " + getSolutionMap().size());
	    	}
	    	if (_debugDisplayPreemptionBlockSolutions == true)
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
		
        // Clean up the previous cost map and preemption points map information 
        // as we no longer need these data structures.  
        predecessorBlock.deleteCostSolution();
        successorBlock.deleteCostSolution();
    	condSectionSize = (int)conditionalBlock.numberOfConditionalSections();
		for (condSectionIndex = 0; condSectionIndex < condSectionSize; condSectionIndex++)
		{
			conditionalSection = conditionalBlock.getConditionalSectionAtIndex(condSectionIndex);		
			conditionalSection.deleteCostSolution();
		}
	}

	/**
	 * Displays the computed preemption points solution.
	 *
	 * @see       JCBlock
	 * @see       JCCostKey
	 * @see       JCCostSolution
	 * @see       JCPreemptionPoints
	 */
	public void displayBlockSolution()
	{
		JCBlock bBlock;
		JCCostKey minimumCostKey;
		JCCostSolution minimumCostSolution;
		ArrayList<JCCostSolution> minimumCostSolutionList;
		Iterator<JCCostSolution> minimumCostSolutionIterator;
		JCPreemptionPoints minimumCostPreemptionPoints;
		Iterator<JCPreemptionPoints> minimumPreemptionPointsIterator;
		
		bBlock = this;
		minimumCostSolutionList = bBlock.getSolutionMap();
    	System.out.println("    JCBlock: Number of minimum cost solutions for block " + bBlock.getBlockName() + " is " + minimumCostSolutionList.size());
		minimumCostSolutionIterator = minimumCostSolutionList.iterator();
		while (minimumCostSolutionIterator.hasNext() == true)
		{
			minimumCostSolution = minimumCostSolutionIterator.next();
			minimumCostKey = minimumCostSolution.getSolutionKey();
	    	System.out.print("        JCBlock: Minimum cost solution (" + minimumCostKey.getLeftIndex() + "," + minimumCostKey.getRightIndex() + "," + minimumCostKey.getLeftBasicBlock() + "," + minimumCostKey.getRightBasicBlock() + ") is " + minimumCostSolution.getSolutionCost());

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
	 * @param  predecessorBlock       the predecessor block object to the conditional block
	 *                                object
	 *               
	 * @param  successorBlock         the successor block object to the conditional block
	 *                                object
	 *               
	 * @param  pcmMatrix              the preemption cost matrix used to compute the preemption cost
	 *                                solution
	 *               
	 * @see                           ArrayList
	 * @see                           JCBasicBlock
	 * @see                           JCBlock
	 * @see                           JCCostKey
	 * @see                           JCCostSolution
	 * @see                           JCConditionalBlock
	 * @see                           JCConditionalSection
	 * @see                           JCPreemptionCostMatrix
	 * @see                           JCSectionBlock
	 */
	public void computeBlocksSolution(JCSubBlock predecessorBlock, JCSubBlock successorBlock, JCPreemptionCostMatrix pcmMatrix)
	{	
		JCCostSolution existingCostSolution;
		int existingCostValue;
		JCCostKey existingLeftCostKey;
		JCCostSolution existingLeftCostSolution;
		JCCostKey existingRightCostKey;
		JCCostSolution existingRightCostSolution;
        int maximumPreemptionCost;
		Iterator<JCCostSolution> leftPredecessorMapIterator;
		int leftPredecessorNPRValue;
	    JCBasicBlock leftPredecessorBasicBlock;
	    JCBasicBlock leftPredecessorQBasicBlock;
		JCSectionBlock leftPredecessorSection;
		int leftPredecessorSectionID;
		JCPreemptionPoints leftPreemptionPoints;
		Iterator<JCPreemptionPoints> leftPreemptionPointSolutionIterator;
	    JCBasicBlock leftSuccessorBasicBlock;
		int leftSuccessorNPRValue;
	    JCBasicBlock leftSuccessorQBasicBlock;
		JCSectionBlock leftSuccessorSection;
		int leftSuccessorSectionID;
		ArrayList<Integer> leftVisiblePreemptionPoints;
		JCCostKey minimumBlocksCostKey;
		JCCostSolution minimumBlocksCostSolution;
		JCPreemptionPoints minimumBlocksPreemptionPoints;
		int minimumBlocksCostValue;
        int preemptionCost;
        JCBasicBlock rightPredecessorBasicBlock;
		int rightPredecessorNPRValue;
	    JCBasicBlock rightPredecessorQBasicBlock;
		JCSectionBlock rightPredecessorSection;
		int rightPredecessorSectionID;
		JCPreemptionPoints rightPreemptionPoints;
		Iterator<JCPreemptionPoints> rightPreemptionPointSolutionIterator;
	    JCBasicBlock rightSuccessorBasicBlock;
		Iterator<JCCostSolution> rightSuccessorMapIterator;
		int rightSuccessorNPRValue;
	    JCBasicBlock rightSuccessorQBasicBlock;
		JCSectionBlock rightSuccessorSection;
		int rightSuccessorSectionID;
		ArrayList<Integer> rightVisiblePreemptionPoints;
		int combinedCostValue;
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		int solutionPreemptionCost;
		boolean validSolution;
		JCBasicBlock visiblePPBasicBlock;
		
		if (_debugInputPreemptionSolutionSizes == true)
		{
            System.out.println("computeBlocksSolution(): input preemption solution sizes:");
			displayPreemptionSolutionSizes(predecessorBlock, null, successorBlock);
		}
		
        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
        leftPredecessorSectionID = predecessorBlock.getStartingSectionID();
        leftPredecessorSection = JCSectionBlock.getSectionBlock(leftPredecessorSectionID);
        leftPredecessorBasicBlock = leftPredecessorSection.getLeftMostBasicBlock();
        rightPredecessorSectionID = successorBlock.getStartingSectionID();
        rightPredecessorSection = JCSectionBlock.getSectionBlock(rightPredecessorSectionID);
        rightPredecessorBasicBlock = rightPredecessorSection.getLeftMostBasicBlock();
        leftSuccessorSectionID = predecessorBlock.getEndingSectionID();
        leftSuccessorSection = JCSectionBlock.getSectionBlock(leftSuccessorSectionID);
        leftSuccessorBasicBlock = leftSuccessorSection.getRightMostBasicBlock();
        rightSuccessorSectionID = successorBlock.getEndingSectionID();
        rightSuccessorSection = JCSectionBlock.getSectionBlock(rightSuccessorSectionID);
        rightSuccessorBasicBlock = rightSuccessorSection.getRightMostBasicBlock();
		
        leftPredecessorMapIterator = predecessorBlock.getSolutionMap().iterator();
        if (predecessorBlock.getSolutionMap() != null)
        {
            leftPredecessorMapIterator = predecessorBlock.getSolutionMap().iterator();

            if (_debugCostMapSizes == true)
            {
                if (predecessorBlock.getSolutionMap().size() == 0)
                {
                    System.out.println("    JCBlock: computeBlocksSolution: Number of minimum cost solutions for predecessor sub-block " + predecessorBlock.getSubBlockName() + " is " + predecessorBlock.getSolutionMap().size());
                }
                
                if (successorBlock.getSolutionMap().size() == 0)
                {
                    System.out.println("    JCBlock: computeBlocksSolution: Number of minimum cost solutions for successor sub-block " + successorBlock.getSubBlockName() + " is " + successorBlock.getSolutionMap().size());
                }
            }
            
            // Process the left or predecessor solutions.
    		while (leftPredecessorMapIterator.hasNext() == true)
    		{
    			existingLeftCostSolution = leftPredecessorMapIterator.next();
    			existingLeftCostKey = existingLeftCostSolution.getSolutionKey();
    			leftPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLeftCostKey.getLeftBasicBlock());
    	        leftPredecessorNPRValue = existingLeftCostKey.getLeftIndex();
                leftSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLeftCostKey.getRightBasicBlock());
                leftSuccessorNPRValue = existingLeftCostKey.getRightIndex();
    			
            	if (_debugNextBasicBlockPair == true)
                {
                	System.out.println("    JCBlock: computeBlocksSolution: Block " + this.getBlockName() + " processing next left predecessor basic block " + leftPredecessorQBasicBlock.getBBlockName() + " next left successor basic block " + leftSuccessorQBasicBlock.getBBlockName());
                }

    	        rightSuccessorMapIterator = successorBlock.getSolutionMap().iterator();
                if (successorBlock.getSolutionMap() != null)
                {
        	        rightSuccessorMapIterator = successorBlock.getSolutionMap().iterator();

        	        // Process the right or successor solutions.
        			while (rightSuccessorMapIterator.hasNext() == true)
        			{
        				existingRightCostSolution = rightSuccessorMapIterator.next();
        				existingRightCostKey = existingRightCostSolution.getSolutionKey();
        				rightSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingRightCostKey.getRightBasicBlock());
        				rightSuccessorNPRValue = existingRightCostKey.getRightIndex();
              	        rightPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingRightCostKey.getLeftBasicBlock());
              	        rightPredecessorNPRValue = existingRightCostKey.getLeftIndex();
        				
        				// Compute the minimum cost value for the current conditional left predecessor and right successor cost key values.
        				minimumBlocksCostValue = Integer.MAX_VALUE;
        				minimumBlocksCostKey = null;
        				minimumBlocksCostSolution = null;
        				minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
        				
                    	if (_debugNextBasicBlockPair == true)
                        {
                        	System.out.println("    JCBlock: computeBlocksSolution: Block " + this.getBlockName() + " processing next right predecessor basic block " + rightPredecessorQBasicBlock.getBBlockName() + " next right successor basic block " + rightSuccessorQBasicBlock.getBBlockName());
                        }
                        
                    	if (_debugSearchOldSolutionCheck == true)
                        {
                        	System.out.println("    JCBlock: computeBlocksSolution: **Searching old left solution for (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex()) + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ")**");
                        }

                        if (existingLeftCostSolution != null)
                        {
                    		// Process the previous preemption based solutions generated.
                            if (_debugOldSolutionCheck == true)
                            {
                            	System.out.println("    JCBlock: computeBlocksSolution: **Processing old left solution for (" + existingLeftCostKey.getLeftIndex() + ","  + (existingLeftCostKey.getRightIndex())  + ","  + (existingLeftCostKey.getLeftBasicBlock()) + ","  + (existingLeftCostKey.getRightBasicBlock()) + ")**");
                            }
                            
        	                if (_debugCostMapSolution == true)
        	                {
        	                	System.out.println("    JCBlock: computeBlocksSolution: left costmap (" + leftPredecessorNPRValue + "," + leftSuccessorNPRValue + ") = " + existingLeftCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingLeftCostSolution.numberOfPreemptionPointSolutions());
        	                }

        	                leftPreemptionPointSolutionIterator = existingLeftCostSolution.getPreemptionPointSolutionsIterator();
        	                while (leftPreemptionPointSolutionIterator.hasNext() == true)
        	                {
        	                	leftPreemptionPoints = leftPreemptionPointSolutionIterator.next();
            	                if (_debugSolutionPreemptionPoints == true)
            	                {
                	                System.out.print("    JCBlock: computeBlocksSolution: Existing left costmap solution preemption points: ");
                	                leftPreemptionPoints.displayObjectInformation();
            		    	    	System.out.println();
            	                }
        		    	    	
            	                leftVisiblePreemptionPoints = existingLeftCostSolution.getVisibleEndingPPs(predecessorBlock, leftPreemptionPoints);
            	                
            	                if (_debugVisiblePreemptionPoints == true)
            	                {
                	                System.out.print("    JCBlock: computeBlocksSolution: Left visible ending preemption points (size=" + leftVisiblePreemptionPoints.size() + "): ");
                	                if (leftVisiblePreemptionPoints.size() > 0)
                	                {
                		    			for (Integer item : leftVisiblePreemptionPoints) 
                		    			{
                  	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                  	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                		    			}
                	                }
                	                System.out.println();
        	    	            }
        	    	                
                            	if (_debugSearchOldSolutionCheck == true)
                                {
                                	System.out.println("    JCBlock: computeBlocksSolution: **Searching old right solution for (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex()) + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ")**");
                                }

                                if (existingRightCostSolution != null)
                                {
                            		// Process the previous preemption based solutions generated.
                                    if (_debugOldSolutionCheck == true)
                                    {
                                    	System.out.println("    JCBlock: computeBlocksSolution: **Processing old right solution for (" + existingRightCostKey.getLeftIndex() + ","  + (existingRightCostKey.getRightIndex())  + ","  + (existingRightCostKey.getLeftBasicBlock()) + ","  + (existingRightCostKey.getRightBasicBlock()) + ")**");
                                    }
                                    
                	                if (_debugCostMapSolution == true)
                	                {
                	                	System.out.println("    JCBlock: computeBlocksSolution: right costmap (" + rightPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + existingRightCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingRightCostSolution.numberOfPreemptionPointSolutions());
                	                }
                        		    
                	                rightPreemptionPointSolutionIterator = existingRightCostSolution.getPreemptionPointSolutionsIterator();
                	                while (rightPreemptionPointSolutionIterator.hasNext() == true)
                	                {
                	                	rightPreemptionPoints = rightPreemptionPointSolutionIterator.next();
                    	                if (_debugSolutionPreemptionPoints == true)
                    	                {
                        	                System.out.print("    JCBlock: computeBlocksSolution: Existing right costmap solution preemption points: ");
                        	                rightPreemptionPoints.displayObjectInformation();
                    		    	    	System.out.println();
                    	                }
                		    	    	
                    	                rightVisiblePreemptionPoints = existingRightCostSolution.getVisibleStartingPPs(successorBlock, rightPreemptionPoints);
                    	                
              	    	                if (_debugVisiblePreemptionPoints == true)
              	    	                {
                        	                System.out.print("    JCBlock: computeBlocksSolution: Right visible starting preemption points (size=" + rightVisiblePreemptionPoints.size() + "): ");
                        	                if (rightVisiblePreemptionPoints.size() > 0)
                        	                {
                        		    			for (Integer item : rightVisiblePreemptionPoints) 
                        		    			{
                          	                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                          	                		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
                        		    			}
                        	                }
                        	                System.out.println();
              	    	                }
                       	                
                    	                maximumPreemptionCost = 0;
                    	                if ((leftVisiblePreemptionPoints.size() > 0) && (rightVisiblePreemptionPoints.size() > 0))
                    	                {
                    		    			for (Integer leftItem : leftVisiblePreemptionPoints) 
                    		    			{
                        		    			for (Integer rightItem : rightVisiblePreemptionPoints) 
                        		    			{
                        		            		preemptionCost = pcmMatrix.getMatrixEntry(leftItem.intValue(), 
                        		            				                                  rightItem.intValue());
                          	                        preemptionCost = (preemptionCost < 0) ? 0 : preemptionCost;
                          	                        
                          	                        if (maximumPreemptionCost < preemptionCost)
                          	                        {
                          	                        	maximumPreemptionCost = preemptionCost;
                          	                        }
                        		    			}
                    		    			}
                    	    			}
                                       	
                    	                if (_debugMaximumPreemptionCost == true)
                    	                {
                        	                System.out.println("    JCBlock: computeBlocksSolution: Maximum preemption cost = " + maximumPreemptionCost);
                    	                }
                    	                
                      	                if (_debugNPRRegionCheck == true)
                    	                {
                        	                System.out.println("    JCBlock: computeBlocksSolution: Q = " + JCBlock.getQValue() + " Left NPR cost (" + existingLeftCostKey.getRightIndex() + "," + maximumPreemptionCost + "," + existingRightCostKey.getLeftIndex() + ") = " + (existingLeftCostKey.getRightIndex() + maximumPreemptionCost + existingRightCostKey.getLeftIndex()));
                    	                }
                      	                
                                        // Check to ensure that maximum non-preemptive region constraint is satisfied.
                      	                validSolution = ((existingLeftCostKey.getRightIndex() + maximumPreemptionCost + existingRightCostKey.getLeftIndex()) <= JCBlock.getQValue());
                                        
                      	                solutionPreemptionCost = maximumPreemptionCost;
                                      	                                                                
                                      	if (validSolution == true)
                                        {
                                 	    	combinedCostValue = existingLeftCostSolution.getSolutionCost() + 
                                 	    	                    solutionPreemptionCost +
                                 	    	 		            existingRightCostSolution.getSolutionCost();
                                 	            			
                          	                if (_debugBlockCost == true)
                        	                {
                                            	System.out.println("    JCBlock: computeBlocksSolution: combinedCost = " + combinedCostValue);
                        	                }
                          	                
                          	                if (_debugBlockPreemptionCost == true)
                        	                {
                                            	System.out.println("    JCBlock: computeBlocksSolution: preemptionCost = " + solutionPreemptionCost);
                        	                }
                                        	                
                        	                if (_debugSolutionCombinedCostKeys == true)
                        	                {
                            	                System.out.println("    JCBlock: computeBlocksSolution: Q = " + JCBlock.getQValue() + " Left cost key (" + existingLeftCostKey.getLeftIndex() + "," + existingLeftCostKey.getRightIndex() + "," + existingLeftCostKey.getLeftBasicBlock() + "," + existingLeftCostKey.getRightBasicBlock() + ")" +
                                                                   " Right cost key (" + existingRightCostKey.getLeftIndex() + "," + existingRightCostKey.getRightIndex() + "," + existingRightCostKey.getLeftBasicBlock() + "," + existingRightCostKey.getRightBasicBlock()+ ")");
                        	                }
                        	                
                        	                minimumBlocksCostValue = combinedCostValue;
                                    		minimumBlocksCostKey = new JCCostKey(existingLeftCostKey.getLeftIndex(), existingRightCostKey.getRightIndex());
                                    		minimumBlocksCostKey.setLeftBasicBlock(leftPredecessorQBasicBlock.getBBlockID());
                                    		minimumBlocksCostKey.setRightBasicBlock(rightSuccessorQBasicBlock.getBBlockID());

                    	                    existingCostSolution = getCostSolution(minimumBlocksCostKey);
                    	                    if (existingCostSolution != null)
                    	                    {
                    	                    	existingCostValue = existingCostSolution.getSolutionCost();
                    	                    	if (existingCostValue > minimumBlocksCostValue)
                    	                    	{
                    	                    		existingCostSolution.setSolutionCost(minimumBlocksCostValue);
                    	                    		existingCostSolution.clearPreemptionPointSolutions();

                    	                    		minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
                                    			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
                                    			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);

                                    			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(predecessorBlock, successorBlock, minimumBlocksPreemptionPoints) == false)
                                    			  	{
                                        			  	existingCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

                                        			  	if (_debugAddMinimumCostSolution == true)
                        	        			        {
                        	        			        	System.out.print("    JCBlock: computeBlocksSolution: adding minimum blocks cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumBlocksCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
                        	        			        	minimumBlocksPreemptionPoints.displayObjectInformation();
                        	        			        	System.out.println();
                        	        			        }
                                    			  	}
                    	                    	}
                    	                    	else if (existingCostValue == minimumBlocksCostValue)
                    	                    	{
                    	        					if (_debugSupportMultiplePreemptionPoints == true)
                    	        					{
                                        			  	minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
                                        			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
                                        			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);
                                                
                                        			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(predecessorBlock, successorBlock, minimumBlocksPreemptionPoints) == false)
                                        			  	{
                                            			  	existingCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);
                                                            
                            	        					if (_debugAddMinimumCostSolution == true)
                            	        			        {
                            	        			        	System.out.print("    JCBlock: computeBlocksSolution: updating minimum blocks cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumBlocksCostValue + " preemption points ");
                            	        			        	minimumBlocksPreemptionPoints.displayObjectInformation();
                            	        			        	System.out.println();
                            	        			        }
                                        			  	}
                    	                    	    }
                    	                    	}
                    	                    }
                    	                    else
                    	                    {
                                        		minimumBlocksCostSolution = new JCCostSolution();
                                        		minimumBlocksCostSolution.setSolutionCost(combinedCostValue);
                                        		minimumBlocksCostSolution.setSolutionKey(minimumBlocksCostKey);
                                			  	minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
                                			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(leftPreemptionPoints);
                                			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(rightPreemptionPoints);

                                			  	minimumBlocksCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

                                			  	putCostSolution(minimumBlocksCostSolution, true);

                            					if (_debugAddMinimumCostSolution == true)
                            			        {
                            			        	System.out.print("    JCBlock: computeBlocksSolution: adding minimum blocks cost (" + leftPredecessorNPRValue + "," + rightSuccessorNPRValue + ") = " + minimumBlocksCostValue + " preemption points ");
                            			        	minimumBlocksPreemptionPoints.displayObjectInformation();
                            			        	System.out.println();
                            			        }
                    	                    }
                            			}
                                    }
                                }
                        	}
                        }
                    }
                }
            }
                    
    		
    		// Display the computed block solutions if necessary.
    		if (_displayBlockSolutions == true)
    		{
    			displayBlockSolution();
    		}
    		else
    		{
    	    	if (_debugDisplayPreemptionBlockNumberOfSolutions == true)
    	    	{
                    System.out.println("    JCBlock: computeBlocksSolution: Number of minimum cost solutions for block " + getBlockName() + " is " + getSolutionMap().size());
    	    	}
    	    	if (_debugDisplayPreemptionBlockSolutions == true)
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
    		
            // Clean up the previous cost map and preemption points map information 
            // as we no longer need these data structures.  
            predecessorBlock.deleteCostSolution();
            successorBlock.deleteCostSolution();
        }
	}
	
	/**
	 * Returns the string block object type name. 
	 *
	 * @return ID     the string type name of the block object
	 * 
	 * @see           JCBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCBlock";
	}
    
	/**
	 * Displays information about this block object. 
	 *
	 * @see           JCBlock
	 */
    @Override
    public void displayObjectInformation()
    {
    	JCBlock                      block;
    	JCConditionalBlock           conditionalBlock;
    	JCConditionalSection         conditionalSection;
    	JCFunctionBlock              functionBlock;
    	JCFunctionCallBlock          functionCallBlock;
    	Iterator<Integer>            iterator;
    	JCLoopBlock                  loopBlock;
    	String                       objectType;
    	JCSectionBlock               sectionBlock;
    	JCSubBlock                   subBlock;
       	Integer                      subBlockID;

       	objectType = this.getObjectTypeName();
       	if (objectType.compareTo("JCBlock") == 0)
       	{
           	System.out.println("Block ID " + this.getBlockID() + " Name " + this.getBlockName() + " sub-block ID " + this.getSubBlockID() + " sub-block Name " + this.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCConditionalSection") == 0)
       	{
       		conditionalSection = (JCConditionalSection) this;
           	System.out.println("Conditional Section ID " + conditionalSection.getConditionalSectionID() + " Name " + conditionalSection.getConditionalSectionName() + " Block ID " + conditionalSection.getBlockID() + " Name " + conditionalSection.getBlockName() + " sub-block ID " + conditionalSection.getSubBlockID() + " sub-block name " + conditionalSection.getSubBlockName());
       	}
       	else if (objectType.compareTo("JCLoopBlock") == 0)
       	{
       		loopBlock = (JCLoopBlock) this;
           	System.out.println("Loop Block ID " + loopBlock.getLoopBlockID() + " Name " + loopBlock.getLoopBlockName() + " Block ID " + loopBlock.getBlockID() + " Name " + loopBlock.getBlockName() + " sub-block ID " + loopBlock.getSubBlockID() + " sub-block name " + loopBlock.getSubBlockName());
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
    		
    	iterator = this.getSubBlockIterator();
    	System.out.print("    SubBlocks: ");
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
           	else if (objectType.compareTo("JCSectionBlock") == 0)
           	{
           		sectionBlock = (JCSectionBlock) subBlock;
               	System.out.print("(" + sectionBlock.getSectionBlockID() + "," + sectionBlock.getSectionBlockName() + "," + sectionBlock.getSubBlockID() + "," + sectionBlock.getSubBlockName() + ") ");
           	}
           	else if (objectType.compareTo("JCConditionalSection") == 0)
           	{
           		conditionalSection = (JCConditionalSection) subBlock;
               	System.out.print("(" + conditionalSection.getConditionalSectionID() + "," + conditionalSection.getConditionalSectionName() + "," + conditionalSection.getBlockID() + "," + conditionalSection.getBlockName() + "," + conditionalSection.getSubBlockID() + "," + conditionalSection.getSubBlockName() + ") ");
           	}
           	else if (objectType.compareTo("JCConditionalBlock") == 0)
           	{
           		conditionalBlock = (JCConditionalBlock) subBlock;
               	System.out.print("(" + conditionalBlock.getConditionalBlockID() + "," + conditionalBlock.getConditionalBlockName() + "," + conditionalBlock.getSubBlockID() + "," + conditionalBlock.getSubBlockName() + ") ");
           	}
           	else if (objectType.compareTo("JCLoopBlock") == 0)
           	{
           		loopBlock = (JCLoopBlock) subBlock;
               	System.out.print("(" + loopBlock.getLoopBlockID() + "," + loopBlock.getLoopBlockName() + "," + loopBlock.getBlockID() + "," + loopBlock.getBlockName() + "," + loopBlock.getSubBlockID() + "," + loopBlock.getSubBlockName() + ") ");
           	}
           	else if (objectType.compareTo("JCFunctionBlock") == 0)
           	{
           		functionBlock = (JCFunctionBlock) subBlock;
               	System.out.print("(" + functionBlock.getFunctionBlockID() + "," + functionBlock.getFunctionBlockName() + "," + functionBlock.getBlockID() + "," + functionBlock.getBlockName() + "," + functionBlock.getSubBlockID() + "," + functionBlock.getSubBlockName() + ") ");
           	}
           	else if (objectType.compareTo("JCFunctionCallBlock") == 0)
           	{
           		functionCallBlock = (JCFunctionCallBlock) subBlock;
               	System.out.print("(" + functionCallBlock.getFunctionCallBlockID() + "," + functionCallBlock.getFunctionCallBlockName() + "," + functionCallBlock.getBlockID() + "," + functionCallBlock.getBlockName() + "," + functionCallBlock.getSubBlockID() + "," + functionCallBlock.getSubBlockName() + ") ");
           	}
           	else
           	{
        		System.out.print("(" + subBlock.getSubBlockID() + "," + subBlock.getSubBlockName() + ") ");
           	}
    	}
    	System.out.println();
    	
       	super.displayObjectInformation();
    }

	/**
	 * Displays information about all block objects. 
	 *
	 * @see           JCBlock
	 */
    public static void displayAllObjects()
    {
	    int                  blockID;
    	JCBlock              currentBlock;
		
    	System.out.println("The number of blocks is " + _blocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (blockID = 0; blockID < _blocks.size(); blockID++)
		{
			currentBlock = JCBlock.getBlock(blockID);
			currentBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
