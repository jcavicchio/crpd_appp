import java.util.ArrayList;
import java.util.Iterator;

/**
 * JCLoop is a fundamental class whose purpose is to contain a set of
 * sub-blocks representing a loop body. These sub-blocks form the 
 * structure of the control flow graph basic block objects for the 
 * specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCLoopBlock extends JCBlock 
{
	private static boolean _debugAddMaximumCostSolution = false;
	private static boolean _debugAddMinimumCostSolution = false; // JCC
	private static boolean _displayBlockSolutions = false;
	private static boolean _debugCostMapSizes = false; // JCC
	private static boolean _debugCostMapSolution = false; // JCC
	private static boolean _debugSectionCostMap = false;
	private static boolean _debugNextBasicBlockPair = false;
	private static boolean _debugOldSolutionCheck = false;
	private static boolean _debugSearchOldSolutionCheck = false;
	private static boolean _debugSolutionCheck = false;
	private static boolean _debugUpdateCostSolution = false;
	private static boolean _debugSolutionCombinedCostKeys = false; // JCC
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
	private static boolean _debugDisplayPreemptionLoopBlockSolutions = false;
	private static boolean _debugDisplayPreemptionLoopBlockNumberOfSolutions = false; // JCC2
	private static boolean _debugLoopSolutionProgress = false;
	
	private static int _nextID = 0;

	private static int _loopCount = 0;                 // Counter to count which loop object it is
	private static ArrayList<JCLoopBlock> _loopBlocks = 
			          new ArrayList<JCLoopBlock>();    // Loops contained in the graph

	private int _loopID;
	
	private int _maxIterations;

	/**
	 * Default constructor.
	 */
    JCLoopBlock()
	{
		super();
		_loopID = _nextID;
		_nextID++;
		_loopCount = _nextID;
		_maxIterations = 1;
		setLoopBlock(_loopID, this);
	}
	
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCLoopBlock
	 */
	public static void reset()
	{
		_nextID = 0;
		_loopCount = 0;
		_loopBlocks = new ArrayList<JCLoopBlock>();
	}
	
	/**
	 * Returns the numeric identifier of the loop object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _loopID    the identifier of this loop object
	 * 
	 * @see               JCLoopBlock
	 */
	public int getLoopBlockID()
	{
		return _loopID;
	}

	/**
	 * Returns the string name of the loop object. 
	 *
	 * @return _ID     the string name of this loop object
	 * 
	 * @see            JCLoopBlock
	 */
	public String getLoopBlockName()
	{
		String id = "JCLoopBlock" + _loopID;
		
		return id;
	}
	
	/**
	 * Gets the maximum number of iterations for this loop object. 
	 *
	 * @return  maxIterations    the maximum number of iterations for this loop
	 * 
	 * @see                      JCLoop
	 */
	public int getMaxLoopIterations()
	{
		return _maxIterations;
	}
	
	/**
	 * Sets the maximum number of iterations for this loop object. 
	 *
	 * @param  maxIterations    the maximum number of iterations for this loop
	 * 
	 * @see                     JCLoop
	 */
	public void setMaxLoopIterations(int maxIterations)
	{
		_maxIterations = maxIterations;
	}
	
	/**
	 * Stores the loop object at the specified location in
	 * the loop array. 
	 *
	 * @param  loopID     the identifier of the stored loop object
	 * 
	 * @param  loopObj    the loop object to be stored
	 * 
	 * @see               JCLoopBlock
	 */
	public static void setLoopBlock(int loopID, JCLoopBlock loopObj)
	{
		if (loopID < _loopCount)
		{
			_loopBlocks.add(loopObj);
		}
	}
	
	/**
	 * Returns the loop object associated with the specified identifier. 
	 *
	 * @param  loopID     the identifier of the stored loop object
	 * 
	 * @return loopObj    the loop object stored
	 * 
	 * @see                JCLoopBlock
	 */
	public static JCLoopBlock getLoopBlock(int loopID)
	{
		if (loopID < _loopCount)
		{
	        return _loopBlocks.get(loopID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Computes the potential preemption point solutions for the specified loop
	 * block object. 
	 *
	 * @param  loopBlock       the predecessor block object to the conditional block
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
	public void computeLoopBlockSolution(JCBlock loopBlock, JCPreemptionCostMatrix pcmMatrix)
	{	
		JCCostSolution existingCostSolution;
		int existingCostValue;
		JCCostKey existingLoopCostKey;
		JCCostSolution existingLoopCostSolution;
		Iterator<JCCostSolution> loopBlockMapIterator;
	    int loopSolutionCostValue;
		int loopPredecessorNPRValue;
	    JCBasicBlock loopPredecessorQBasicBlock;
		JCPreemptionPoints loopPreemptionPoints;
		Iterator<JCPreemptionPoints> loopPreemptionPointSolutionIterator;
		int loopSuccessorNPRValue;
	    JCBasicBlock loopSuccessorQBasicBlock;
	    int maximumLoopIterations;
		JCCostKey minimumBlocksCostKey;
		JCCostSolution minimumBlocksCostSolution;
		JCPreemptionPoints minimumBlocksPreemptionPoints;
		int minimumBlocksCostValue;
		JCCostSolution solution;
		int solutionCount;
		Iterator<JCCostSolution> solutionIterator;
		boolean validSolution;
		
        // Create new hash map objects representing the new aggregate solution containing the next basic block.
        createCostSolution();
        
        maximumLoopIterations = getMaxLoopIterations();
        
        loopBlockMapIterator = loopBlock.getSolutionMap().iterator();

        // Process the left or predecessor solutions.
		while (loopBlockMapIterator.hasNext() == true)
		{
			existingLoopCostSolution = loopBlockMapIterator.next();
			existingLoopCostKey = existingLoopCostSolution.getSolutionKey();
			loopPredecessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLoopCostKey.getLeftBasicBlock());
	        loopPredecessorNPRValue = existingLoopCostKey.getLeftIndex();
            loopSuccessorQBasicBlock = JCBasicBlock.getBasicBlock(existingLoopCostKey.getRightBasicBlock());
            loopSuccessorNPRValue = existingLoopCostKey.getRightIndex();
			
        	if (_debugNextBasicBlockPair == true)
            {
            	System.out.println("    JCLoopBlock: computeLoopBlockSolution: Block " + this.getBlockName() + " processing next left predecessor basic block " + loopPredecessorQBasicBlock.getBBlockName() + " next left successor basic block " + loopSuccessorQBasicBlock.getBBlockName());
            }

            if (existingLoopCostSolution != null)
            {
        		// Process the previous preemption based solutions generated.
                if (_debugOldSolutionCheck == true)
                {
                	System.out.println("    JCLoopBlock: computeLoopBlockSolution: **Processing old loop solution for (" + existingLoopCostKey.getLeftIndex() + ","  + (existingLoopCostKey.getRightIndex())  + ","  + (existingLoopCostKey.getLeftBasicBlock()) + ","  + (existingLoopCostKey.getRightBasicBlock()) + ")**");
                }
                
                if (_debugCostMapSolution == true)
                {
                	System.out.println("    JCLoopBlock: computeLoopBlockSolution: loop costmap (" + loopPredecessorNPRValue + "," + loopSuccessorNPRValue + ") = " + existingLoopCostSolution.getSolutionCost() + " number of preemption point solutions = " + existingLoopCostSolution.numberOfPreemptionPointSolutions());
                }

                loopPreemptionPointSolutionIterator = existingLoopCostSolution.getPreemptionPointSolutionsIterator();
                while (loopPreemptionPointSolutionIterator.hasNext() == true)
                {
                	loopPreemptionPoints = loopPreemptionPointSolutionIterator.next();
    	            if (_debugSolutionPreemptionPoints == true)
    	            {
    	                System.out.print("    JCLoopBlock: computeLoopBlockSolution: Existing loop costmap solution preemption points: ");
    	                loopPreemptionPoints.displayObjectInformation();
		    	    	System.out.println();
	                }
	    	    	
                	if (_debugSearchOldSolutionCheck == true)
                    {
                    	System.out.println("    JCLoopBlock: computeLoopBlockSolution: **Searching old solution for (" + existingLoopCostKey.getLeftIndex() + ","  + (existingLoopCostKey.getRightIndex()) + ","  + (existingLoopCostKey.getLeftBasicBlock()) + ","  + (existingLoopCostKey.getRightBasicBlock()) + ")**");
                    }

    	            if (_debugNPRRegionCheck == true)
                    {
    	                System.out.println("    JCLoopBlock: computeLoopBlockSolution: Q = " + JCBlock.getQValue() + " Loop NPR cost (" + existingLoopCostKey.getLeftIndex() + "," + existingLoopCostKey.getRightIndex() + ") = " + (existingLoopCostKey.getRightIndex() + existingLoopCostKey.getLeftIndex()));
                    }
    	                
                    // Check to ensure that maximum non-preemptive region constraint is satisfied.
    	            validSolution = ((existingLoopCostKey.getRightIndex() + existingLoopCostKey.getLeftIndex()) <= JCBlock.getQValue());
                    
                  	if (validSolution == true)
                    {
    	                if (_debugSolutionCombinedCostKeys == true)
    	                {
        	                System.out.println("    JCLoopBlock: computeLoopBlockSolution: Q = " + JCBlock.getQValue() + " Loop cost key (" + existingLoopCostKey.getLeftIndex() + "," + existingLoopCostKey.getRightIndex() + "," + existingLoopCostKey.getLeftBasicBlock() + "," + existingLoopCostKey.getRightBasicBlock() + ")");
    	                }
    	                
    	                minimumBlocksCostValue = existingLoopCostSolution.getSolutionCost();
    	                loopSolutionCostValue = minimumBlocksCostValue * maximumLoopIterations;
                		minimumBlocksCostKey = new JCCostKey(existingLoopCostKey.getLeftIndex(), existingLoopCostKey.getRightIndex());
                		minimumBlocksCostKey.setLeftBasicBlock(loopPredecessorQBasicBlock.getBBlockID());
                		minimumBlocksCostKey.setRightBasicBlock(loopSuccessorQBasicBlock.getBBlockID());

                        existingCostSolution = getCostSolution(minimumBlocksCostKey);
                        if (existingCostSolution != null)
                        {
                        	existingCostValue = existingCostSolution.getSolutionCost();
                        	if (existingCostValue > loopSolutionCostValue)
                        	{
                        		existingCostSolution.setSolutionCost(loopSolutionCostValue);
                        		existingCostSolution.clearPreemptionPointSolutions();

                        		minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
                			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(loopPreemptionPoints);

                			  	existingCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

                			  	if (_debugAddMinimumCostSolution == true)
            			        {
            			        	System.out.print("    JCLoopBlock: computeLoopBlockSolution: adding minimum blocks cost (" + loopPredecessorNPRValue + "," + loopSuccessorNPRValue + ") = " + loopSolutionCostValue + " preemption points size " + existingCostSolution.numberOfPreemptionPointSolutions() + " preemption points ");
            			        	minimumBlocksPreemptionPoints.displayObjectInformation();
            			        	System.out.println();
            			        }
                        	}
                        	else if (existingCostValue == loopSolutionCostValue)
                        	{
            					if (_debugSupportMultiplePreemptionPoints == true)
            					{
                    			  	minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
                    			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(loopPreemptionPoints);

                    			  	if (existingCostSolution.hasVisiblePreemptionPointSolution(loopBlock, loopBlock, minimumBlocksPreemptionPoints) == false)
                    			  	{
                        			  	existingCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

        	        					if (_debugAddMinimumCostSolution == true)
        	        			        {
        	        			        	System.out.print("    JCLoopBlock: computeLoopBlockSolution: updating minimum blocks cost (" + loopPredecessorNPRValue + "," + loopSuccessorNPRValue + ") = " + loopSolutionCostValue + " preemption points ");
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
                    		loopSolutionCostValue = existingLoopCostSolution.getSolutionCost() * maximumLoopIterations;
                    		minimumBlocksCostSolution.setSolutionCost(loopSolutionCostValue);
                    		minimumBlocksCostSolution.setSolutionKey(minimumBlocksCostKey);
            			  	minimumBlocksPreemptionPoints = new JCPreemptionPoints(false);
            			  	minimumBlocksPreemptionPoints.combinePreemptionPoints(loopPreemptionPoints);

            			  	minimumBlocksCostSolution.addPreemptionPointSolution(minimumBlocksPreemptionPoints);

            			  	putCostSolution(minimumBlocksCostSolution, true);

        					if (_debugAddMinimumCostSolution == true)
        			        {
        			        	System.out.print("    JCLoopBlock: computeLoopBlockSolution: adding minimum blocks cost (" + loopPredecessorNPRValue + "," + loopSuccessorNPRValue + ") = " + loopSolutionCostValue + " preemption points ");
        			        	minimumBlocksPreemptionPoints.displayObjectInformation();
        			        	System.out.println();
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
	    	if (_debugDisplayPreemptionLoopBlockNumberOfSolutions == true)
	    	{
	            System.out.println("    JCLoopBlock: computeLoopBlockSolution: Number of minimum cost solutions for loop block " + getLoopBlockName() + " is " + getSolutionMap().size());
	    	}
	    	if (_debugDisplayPreemptionLoopBlockSolutions == true)
	    	{
		    	solutionIterator = getSolutionMap().iterator();
		    	solutionCount = 1;
		    	while (solutionIterator.hasNext() == true)
		    	{
		    		solution = solutionIterator.next();
		    		System.out.println("        Solution " + solutionCount + " cost " + solution.getSolutionCost() + " number of preemption point solutions is " + solution.numberOfPreemptionPointSolutions());
		    		solutionCount++;
		    	}
	    	}
		}
		
        // Clean up the previous cost map and preemption points map information 
        // as we no longer need these data structures.  
        loopBlock.deleteCostSolution();
	}
	
	/**
	 * Returns the string loop object type name. 
	 *
	 * @return ID     the string type name of the loop object
	 * 
	 * @see           JCLoopBlock
	 */
	@Override
	public String getObjectTypeName()
	{
		return "JCLoopBlock";
	}
    
	/**
	 * Displays information about this loop object. 
	 *
	 * @see           JCLoopBlock
	 */
    @Override
    public void displayObjectInformation()
    {
       	System.out.println("Loop Block ID " + getLoopBlockID() + " Name " + getLoopBlockName() + " block ID " + getBlockID() + " block name " + getBlockName() + " sub-block ID " + getSubBlockID() + " sub-block Name " + getSubBlockName());

    	super.displayObjectInformation();   	
    }

	/**
	 * Displays information about all loop block objects. 
	 *
	 * @see           JCLoopBlock
	 */
    public static void displayAllObjects()
    {
	    int                  loopID;
	    JCLoopBlock          currentLoopBlock;
		
    	System.out.println("The number of loop blocks is " + _loopBlocks.size());
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    	System.out.println("....................................................................................................................................");
		for (loopID = 0; loopID < _loopBlocks.size(); loopID++)
		{
			currentLoopBlock = JCLoopBlock.getLoopBlock(loopID);
			currentLoopBlock.displayObjectInformation();
	    	System.out.println("....................................................................................................................................");
		}
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
}
