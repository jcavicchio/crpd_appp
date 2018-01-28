import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * JCCostSolutionEntry is a fundamental class whose purpose is to store a 
 * proposed minimum preemption cost solution for a specific subgraph derivation. 
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCCostSolution 
{
	private static boolean _debugSolutionPreemptionPoints = false;
	private static boolean _debugVisiblePreemptionPoints = false;
	
	private Integer _solutionCost;
	private JCCostKey _solutionKey;
	private JCPreemptionPoints _solutionPreemptionPoints;
	private ArrayList<JCPreemptionPoints> _preemptionPointSolutions;

	/**
	 * Default constructor.
	 */
	JCCostSolution()
    {
		_solutionCost = Integer.MAX_VALUE;
		_solutionPreemptionPoints = new JCPreemptionPoints(false);
		_solutionKey = null;
		_preemptionPointSolutions = new ArrayList<JCPreemptionPoints>();
    }
	
	/**
	 * Gets the solution cost for this proposed solution. 
	 *
	 * @return solutionCost   the specified solution cost
	 * 
	 * @see                   JCCostSolution
	 */
	Integer getSolutionCost()
	{
		return _solutionCost;
	}
	
	/**
	 * Sets the solution cost for this proposed solution. 
	 *
	 * @param solutionCost   the specified solution cost
	 * 
	 * @see                  JCCostSolution
	 */
	void setSolutionCost(int solutionCost)
	{
		_solutionCost = solutionCost;
	}
	
	/**
	 * Gets the solution key for this proposed solution. 
	 *
	 * @return solutionKey    the specified solution key
	 * 
	 * @see                   JCCostSolution
	 */
	JCCostKey getSolutionKey()
	{
		return _solutionKey;
	}
	
	/**
	 * Sets the solution key for this proposed solution. 
	 *
	 * @param solutionKey    the specified solution key
	 * 
	 * @see                  JCCostSolution
	 */
	void setSolutionKey(JCCostKey solutionKey)
	{
		_solutionKey = solutionKey;
	}

	/**
	 * Adds the current preemption points object to this cost solution object. 
	 *
	 * @see                      JCPreemptionPoints
	 * @see                      JCSectionBlock
	 */
	public void addPreemptionPointSolution()
	{
		if (hasPreemptionPointSolution(_solutionPreemptionPoints) == false)
		{
			if (_preemptionPointSolutions.add(_solutionPreemptionPoints) != true)
			{
				System.out.println("JCCostSolution: Error adding preemption points");
			}
		}
		else
		{
			//System.out.print("JCCostSolution: preemption points solution already exists preemption points: ");
			//_solutionPreemptionPoints.displayObjectInformation();
			//System.out.println();
		}
		_solutionPreemptionPoints = new JCPreemptionPoints(false);
	}
	
	/**
	 * Adds the preemption points object to this cost solution object. 
	 *
	 * @param  preemptionPoints  the preemption points object to add to this 
	 *                           cost solution object
	 *               
	 * @see                      JCCostSolution
	 * @see                      JCPreemptionPoints
	 */
	public void addPreemptionPointSolution(JCPreemptionPoints preemptionPoints)
	{
		if (hasPreemptionPointSolution(preemptionPoints) == false)
		{
			if (_preemptionPointSolutions.add(preemptionPoints) != true)
			{
				System.out.println("JCCostSolution: Error adding preemption points");
			}
		}
		else
		{
			//System.out.print("JCCostSolution: preemption points solution already exists preemption points: ");
			//preemptionPoints.displayObjectInformation();
			//System.out.println();
		}
	}

	/**
	 * Removes the preemption points object from this cost solution object. 
	 *
	 * @param  preemptionPoints  the preemption points object to add to this 
	 *                           cost solution object
	 *               
	 * @see                      JCPreemptionPoints
	 * @see                      JCSectionBlock
	 */
	public void removePreemptionPointSolution()
	{
		if (_preemptionPointSolutions.remove(_solutionPreemptionPoints) != true)
		{
			System.out.println("JCSectionBlock: Error removing preemption points");
		}
		_solutionPreemptionPoints = new JCPreemptionPoints(false);
	}
	
	/**
	 * Removes the preemption points object from this cost solution object. 
	 *
	 * @param  preemptionPoints  the preemption points object to add to this 
	 *                           cost solution object
	 *               
	 * @see                      JCPreemptionPoints
	 * @see                      JCSectionBlock
	 */
	public void removePreemptionPointSolution(JCPreemptionPoints preemptionPoints)
	{
		if (_preemptionPointSolutions.remove(preemptionPoints) != true)
		{
			System.out.println("JCSectionBlock: Error removing preemption points");
		}
	}
	
	/**
	 * Clears the preemption point solutions objects from this cost solution object. 
	 *
	 * @see                      JCPreemptionPoints
	 * @see                      JCSectionBlock
	 */
	public void clearPreemptionPointSolutions()
	{
		_preemptionPointSolutions.clear();
	}
	
	/**
	 * Finds the preemption points object from within this section block object
	 * with the specified set of preemption points. 
	 *
	 * @param  preemptionPoints  the preemption points object to add to this 
	 *                           cost solution object
	 *               
	 * @see                      JCPreemptionPoints
	 * @see                      JCSectionBlock
	 */
	public JCPreemptionPoints findPreemptionPointSolution(JCPreemptionPoints preemptionPoints)
	{
		JCPreemptionPoints currentPreemptionPoints;
		JCPreemptionPoints foundPreemptionPoints = null;
	    Iterator<JCPreemptionPoints> iterator;
	    
	    if (preemptionPoints != null)
	    {
		    iterator = getPreemptionPointSolutionsIterator();
		    if (iterator != null)
		    {
		        while ((true == iterator.hasNext()) && (foundPreemptionPoints == null))
		        {
		        	currentPreemptionPoints = iterator.next();
		        	if (currentPreemptionPoints != null)
		        	{
		        		if (currentPreemptionPoints.equals(preemptionPoints) == true)
		        		{
		        			foundPreemptionPoints = currentPreemptionPoints;
		        		}
		        	}
		        }
		    }
	    }
	    return foundPreemptionPoints;
	}
	
	/**
	 * Determines if the specified preemption points solution is part of this
	 * cost solution object. 
	 *
	 * @param  preemptionPoints            the preemption points object to add to this 
	 *                                     cost solution object
	 *               
	 * @return hasPreemptionPointSolution  true/false indicating if an existing
	 *                                     specified solution is already stored.
	 *                                     
	 * @see                                JCPreemptionPoints
	 * @see                                JCSectionBlock
	 */
	public boolean hasPreemptionPointSolution(JCPreemptionPoints preemptionPoints)
	{
		JCPreemptionPoints foundPreemptionPoints;
		boolean hasPPSolution;
		
		foundPreemptionPoints = findPreemptionPointSolution(preemptionPoints);
		if (foundPreemptionPoints != null)
		{
			hasPPSolution = true;
		}
		else
		{
			hasPPSolution = false;
		}
		return hasPPSolution;
	}
	
	/**
	 * Determines if the specified preemption points solution is part of this
	 * cost solution object. 
	 *
	 * @return hasPreemptionPointSolution  true/false indicating if an existing
	 *                                     specified solution is already stored.
	 *                                     
	 * @see                                JCPreemptionPoints
	 * @see                                JCSectionBlock
	 */
	public boolean hasPreemptionPointSolution()
	{
		return hasPreemptionPointSolution(_solutionPreemptionPoints);
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
	public long numberOfPreemptionPointSolutions()
	{
		return _preemptionPointSolutions.size();
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
	public Iterator<JCPreemptionPoints> getPreemptionPointSolutionsIterator()
	{
		return _preemptionPointSolutions.iterator();
	}
	
	/**
	 * Gets the visible starting preemption points for the specified section object. 
	 *
	 * @param  currentSectionBlock  the section to get visible preemption points for
	 * 
	 * @param solutionPreemptionPoints the existing preemption points set to compute for
	 * 
	 * @param  previousPreemptionPoints the previously visible preemption points
	 * 
	 * @return nextPreemptionPoints the visible preemption points
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 * @see                JCSectionBlock
	 */
	public void getVisibleStartingPPsSection(JCSectionBlock currentSectionBlock, JCPreemptionPoints solutionPreemptionPoints, ArrayList<Integer> preemptionPoints)
	{
		int                basicBlockID;
		int                leftMostBasicBlockID;
		int                rightMostBasicBlockID;

		leftMostBasicBlockID = currentSectionBlock.getLeftMostBasicBlock().getBBlockID();
		rightMostBasicBlockID = currentSectionBlock.getRightMostBasicBlock().getBBlockID();
		for (basicBlockID = leftMostBasicBlockID; basicBlockID <= rightMostBasicBlockID; basicBlockID++)
		{
			//System.out.println("getVisibleStartingPPsSection: Checking basic block " + basicBlockID);
			if (solutionPreemptionPoints.isPreemptionPointSelected(basicBlockID) == true)
			{
			    preemptionPoints.add(basicBlockID);
				//System.out.println("getVisibleStartingPPsSection: Basic block " + basicBlockID + " is a preemption point");
			    break;
			}
		}
	}
	
	/**
	 * Gets the visible starting preemption points for the specified section object. 
	 *
	 * @param  currentSectionBlock  the section to get visible preemption points for
	 * 
	 * @param  endingSectionBlockID the ending section block bounding the region
	 * 
	 * @param solutionPreemptionPoints the existing preemption points set to compute for
	 * 
	 * @param  previousPreemptionPoints the previously visible preemption points
	 * 
	 * @return nextPreemptionPoints the visible preemption points
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 * @see                JCSectionBlock
	 */
	public ArrayList<Integer> getVisibleStartingPPs(JCSectionBlock currentSectionBlock, int endingSectionBlockID, JCPreemptionPoints solutionPreemptionPoints, ArrayList<Integer> previousPreemptionPoints)
	{
		boolean             allSections = true;
		ArrayList<Integer>  nextPreemptionPoints = new ArrayList<Integer>();
        JCSectionBlock      nextSectionBlock;
        int                 nextSectionBlockID;
		ArrayList<Integer>  preemptionPoints = new ArrayList<Integer>();
		Iterator<Integer>   predecessorSectionBlockIterator;
		ArrayList<Integer>  sectionPreemptionPoints = null;
		
		getVisibleStartingPPsSection(currentSectionBlock, solutionPreemptionPoints, preemptionPoints);
		if (preemptionPoints.size() == 0)
		{
			for (Integer item : previousPreemptionPoints) 
			{
				if (preemptionPoints.contains(item.intValue()) == false)
				{
				   preemptionPoints.add(item.intValue());
				}
			}
		}
		
		if (currentSectionBlock.getSectionBlockID() != endingSectionBlockID)
		{
			predecessorSectionBlockIterator = currentSectionBlock.getPredecessorSectionBlockIterator();
			while (predecessorSectionBlockIterator.hasNext() == true)
			{
				nextSectionBlockID = predecessorSectionBlockIterator.next();
				nextSectionBlock = JCSectionBlock.getSectionBlock(nextSectionBlockID);
				sectionPreemptionPoints = getVisibleEndingPPs(nextSectionBlock, endingSectionBlockID, solutionPreemptionPoints, preemptionPoints);
				if (0 == sectionPreemptionPoints.size())
				{
					allSections = false;
				}
				else
				{
					for (Integer item : sectionPreemptionPoints) 
					{
						if (nextPreemptionPoints.contains(item.intValue()) == false)
						{
							nextPreemptionPoints.add(item.intValue());
						}
					}
				}
			}
			
			if (allSections == false)
			{
				for (Integer item : preemptionPoints) 
				{
					if (nextPreemptionPoints.contains(item.intValue()) == false)
					{
						nextPreemptionPoints.add(item.intValue());
					}
				}
			}			
		}
		else
		{
			nextPreemptionPoints = preemptionPoints;
		}
		
		return nextPreemptionPoints;
	}
	
	/**
	 * Gets the visible starting preemption points for the specified sub-block object. 
	 *
	 * @param  subBlock                the sub-block to get visible preemption points for
	 * 
	 * @param solutionPreemptionPoints the existing preemption points set to compute for
	 * 
	 * @return preemptionPoints        the visible preemption points selected
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 */
	public ArrayList<Integer> getVisibleStartingPPs(JCSubBlock subBlock, JCPreemptionPoints solutionPreemptionPoints)
	{
		JCSectionBlock     currentSectionBlock;
		int                endingSectionBlockID = subBlock.getStartingSectionID();
		ArrayList<Integer> preemptionPoints;
		ArrayList<Integer> previousPreemptionPoints = new ArrayList<Integer>();
		int                startingSectionBlockID = subBlock.getEndingSectionID();
		
		currentSectionBlock = JCSectionBlock.getSectionBlock(endingSectionBlockID);
		preemptionPoints = getVisibleStartingPPs(currentSectionBlock, startingSectionBlockID, 
				                                 solutionPreemptionPoints, previousPreemptionPoints);
		
		return preemptionPoints;
	}
	
	/**
	 * Gets the visible ending preemption points for the specified section object. 
	 *
	 * @param  currentSectionBlock     the section to get visible preemption points for
	 * 
	 * @param solutionPreemptionPoints the existing preemption points set to compute for
	 * 
	 * @return nextPreemptionPoints    the visible preemption points
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 * @see                JCSectionBlock
	 */
	public void getVisibleEndingPPsSection(JCSectionBlock currentSectionBlock, JCPreemptionPoints solutionPreemptionPoints, ArrayList<Integer> preemptionPoints)
	{
		int                basicBlockID;
		int                leftMostBasicBlockID;
		int                rightMostBasicBlockID;

		leftMostBasicBlockID = currentSectionBlock.getLeftMostBasicBlock().getBBlockID();
		rightMostBasicBlockID = currentSectionBlock.getRightMostBasicBlock().getBBlockID();
		for (basicBlockID = rightMostBasicBlockID; basicBlockID >= leftMostBasicBlockID; basicBlockID--)
		{
			//System.out.println("getVisibleEndingPPsSection: Checking basic block " + basicBlockID);
			if (solutionPreemptionPoints.isPreemptionPointSelected(basicBlockID) == true)
			{
			    preemptionPoints.add(basicBlockID);
				//System.out.println("getVisibleEndingPPsSection: Basic block " + basicBlockID + " is a preemption point");
			    break;
			}
		}
	}
	
	/**
	 * Gets the visible ending preemption points for the specified section object. 
	 *
	 * @param  currentSectionBlock  the section to get visible preemption points for
	 * 
	 * @param  endingSectionBlockID the ending section block bounding the region
	 * 
	 * @param solutionPreemptionPoints the existing preemption points set to compute for
	 * 
	 * @param  previousPreemptionPoints the previously visible preemption points
	 * 
	 * @return nextPreemptionPoints the visible preemption points
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 * @see                JCSectionBlock
	 */
	public ArrayList<Integer> getVisibleEndingPPs(JCSectionBlock currentSectionBlock, int endingSectionBlockID, 
			                                      JCPreemptionPoints solutionPreemptionPoints, 
			                                      ArrayList<Integer> previousPreemptionPoints)
	{
		boolean             allSections = true;
		ArrayList<Integer>  nextPreemptionPoints = new ArrayList<Integer>();
        JCSectionBlock      nextSectionBlock;
        int                 nextSectionBlockID;
		ArrayList<Integer>  preemptionPoints = new ArrayList<Integer>();
		Iterator<Integer>   successorSectionBlockIterator;
		ArrayList<Integer>  sectionPreemptionPoints = null;
		
		getVisibleEndingPPsSection(currentSectionBlock, solutionPreemptionPoints, preemptionPoints);
		if (preemptionPoints.size() == 0)
		{
			for (Integer item : previousPreemptionPoints) 
			{
				if (preemptionPoints.contains(item.intValue()) == false)
				{
				   preemptionPoints.add(item.intValue());
				}
			}
		}
		
		if (currentSectionBlock.getSectionBlockID() != endingSectionBlockID)
		{
			successorSectionBlockIterator = currentSectionBlock.getSuccessorSectionBlockIterator();
			while (successorSectionBlockIterator.hasNext() == true)
			{
				nextSectionBlockID = successorSectionBlockIterator.next();
				nextSectionBlock = JCSectionBlock.getSectionBlock(nextSectionBlockID);
				sectionPreemptionPoints = getVisibleEndingPPs(nextSectionBlock, endingSectionBlockID, solutionPreemptionPoints, preemptionPoints);
				if (0 == sectionPreemptionPoints.size())
				{
					allSections = false;
				}
				else
				{
					for (Integer item : sectionPreemptionPoints) 
					{
						if (nextPreemptionPoints.contains(item.intValue()) == false)
						{
							nextPreemptionPoints.add(item.intValue());
						}
					}
				}
			}
			
			if (allSections == false)
			{
				for (Integer item : preemptionPoints) 
				{
					if (nextPreemptionPoints.contains(item.intValue()) == false)
					{
						nextPreemptionPoints.add(item.intValue());
					}
				}
			}			
		}
		else
		{
			nextPreemptionPoints = preemptionPoints;
		}
		
		return nextPreemptionPoints;
	}
	
	/**
	 * Gets the visible ending preemption points for the specified sub-block object. 
	 *
	 * @param  subBlock                the sub-block to get visible preemption points for
	 * 
	 * @param solutionPreemptionPoints the existing preemption points set to compute for
	 * 
	 * @return preemptionPoints        the visible preemption points selected
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 */
	public ArrayList<Integer> getVisibleEndingPPs(JCSubBlock subBlock, JCPreemptionPoints solutionPreemptionPoints)
	{
		JCSectionBlock     currentSectionBlock;
		int                endingSectionBlockID = subBlock.getEndingSectionID();
		ArrayList<Integer> preemptionPoints;
		ArrayList<Integer> previousPreemptionPoints = new ArrayList<Integer>();
		int                startingSectionBlockID = subBlock.getStartingSectionID();
		
		currentSectionBlock = JCSectionBlock.getSectionBlock(startingSectionBlockID);
		preemptionPoints = getVisibleEndingPPs(currentSectionBlock, endingSectionBlockID, solutionPreemptionPoints, previousPreemptionPoints);
		
		return preemptionPoints;
	}
	
	/**
	 * Adds the specified basic block object as a preemption point. 
	 *
	 * @param  bBlockID    the identifier of the basic block preemption point location
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 */
	public void addPreemptionPoint(int bBlockID)
	{
		_solutionPreemptionPoints.addPreemptionPoint(bBlockID);
	}

	/**
	 * Removes the specified basic block object as a preemption point. 
	 *
	 * @param  bBlockID    the identifier of the basic block preemption point location
	 * 
	 * @see                JCBasicBlock
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 */
	public void removePreemptionPoint(int bBlockID)
	{
		_solutionPreemptionPoints.removePreemptionPoint(bBlockID);
	}
	
	/**
	 * Determines if the specified preemption point is selected. 
	 *
	 * @param  bBlockID    the identifier of the basic block preemption point location
	 * 
	 * @return isPreemptionPointSelected   true/false indicating preemption point selected
	 * 
	 * @see                                JCCostSolution
	 * @see                                JCPreemptionPoints
	 */
	public boolean isPreemptionPointSelected(int bBlockID)
	{
		return _solutionPreemptionPoints.isPreemptionPointSelected(bBlockID);
	}
	
	/**
	 * Combines the stored preemption points from the specified two locations. 
	 *
	 * @param  firstPoint    the preemption points entry of the first location
	 * 
	 * @param  secondPoint   the preemption points entry of the second location
	 * 
	 * @see                  JCCostSolution
	 * @see                  JCPreemptionPoints
	 */
    public void combineSectionPreemptionPoints(JCPreemptionPoints firstPoint, JCPreemptionPoints secondPoint)
    {
    	_solutionPreemptionPoints.combineSectionPreemptionPoints(firstPoint, secondPoint);
    }
    
	/**
	 * Combines the stored preemption points from the specified two locations. 
	 *
	 * @param  firstPoint    the preemption points entry of the first location
	 * 
	 * @param  secondPoint   the preemption points entry of the second location
	 * 
	 * @param  thirdPoint    the preemption points entry of the third location
	 * 
	 * @see                  JCCostSolution
	 * @see                  JCPreemptionPoints
	 */
    public void combineCondPreemptionPoints(JCPreemptionPoints firstPoint, JCPreemptionPoints secondPoint, JCPreemptionPoints thirdPoint)
    {
    	_solutionPreemptionPoints.combineCondPreemptionPoints(firstPoint, secondPoint, thirdPoint);
    }
    
	/**
	 * Combines the stored preemption points from the specified two locations. 
	 *
	 * @param  firstPoint    the preemption points entry of the first location
	 * 
	 * @see                  JCCostSolution
	 * @see                  JCPreemptionPoints
	 */
    public void combinePreemptionPoints(JCPreemptionPoints firstPoint)
    {
    	_solutionPreemptionPoints.combinePreemptionPoints(firstPoint);
    }
    
	/**
	 * Sets the stored preemption points from the specified preemption points object. 
	 *
	 * @param  preemptionPoints  the preemption points entry of the first location
	 * 
	 * @see                      JCCostSolution
	 * @see                      JCPreemptionPoints
	 */
    public void setPreemptionPoints(JCPreemptionPoints preemptionPoints)
    {
    	_solutionPreemptionPoints.setPreemptionPoints(preemptionPoints);
    }
    
	/**
	 * Clears the stored preemption points from the specified preemption points object. 
	 *
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 */
    public void clearPreemptionPoints()
    {
    	_solutionPreemptionPoints.clearPreemptionPoints();
    }
    
	/**
	 * Gets the stored preemption points object. 
	 *
	 * @param  preemptionPoints  the preemption points entry in this solution
	 * 
	 * @see                      JCCostSolution
	 * @see                      JCPreemptionPoints
	 */
    public JCPreemptionPoints getPreemptionPointsEntry()
    {
    	return _solutionPreemptionPoints;
    }
    
	/**
	 * Checks if an existing visible points solution matches the potential
	 * visible preemption points solution to be added. 
	 *
	 * @param  predecessorBlock          the predecessor block object
	 *               
	 * @param  successorBlock            the successor block object
	 *                                
	 * @param  solutionPreemptionPoints  the preemption points entry to add in this solution
	 * 
	 * @return hasExistingPPSolution     true/false indicating existing visible preemption point solution
	 * 
	 * @see                              JCCostSolution
	 * @see                              JCPreemptionPoints
	 */
    public boolean hasVisiblePreemptionPointSolution(JCSubBlock predecessorBlock, JCSubBlock successorBlock, JCPreemptionPoints solutionPreemptionPoints)
    {
    	boolean allLeftVisiblePPs;
    	boolean allRightVisiblePPs;
    	Integer existingLeftVisibleBasicBlockID;
    	ArrayList<Integer> existingLeftVisiblePreemptionPoints;
    	Iterator<Integer> existingLeftVisiblePreemptionPointsIterator;
    	Integer existingRightVisibleBasicBlockID;
    	ArrayList<Integer> existingRightVisiblePreemptionPoints;
    	Iterator<Integer> existingRightVisiblePreemptionPointsIterator;
    	JCPreemptionPoints existingPreemptionPoints;
    	Iterator<JCPreemptionPoints> existingPreemptionPointSolutionIterator;
    	boolean hasExistingPPSolution = false;
    	ArrayList<Integer> potentialLeftVisiblePreemptionPoints;
    	ArrayList<Integer> potentialRightVisiblePreemptionPoints;
    	JCBasicBlock visiblePPBasicBlock;
    	
        potentialLeftVisiblePreemptionPoints = this.getVisibleStartingPPs(predecessorBlock, solutionPreemptionPoints);
        
        if (_debugVisiblePreemptionPoints == true)
        {
            System.out.print("    JCCostSolution: hasVisiblePreemptionPointSolution: Potential solution visible starting preemption points (size=" + potentialLeftVisiblePreemptionPoints.size() + "): ");
            if (potentialLeftVisiblePreemptionPoints.size() > 0)
            {
    			for (Integer item : potentialLeftVisiblePreemptionPoints) 
    			{
                      visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
              		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
    			}
            }
            System.out.println();
        }
        
        potentialRightVisiblePreemptionPoints = this.getVisibleEndingPPs(successorBlock, solutionPreemptionPoints);
        
        if (_debugVisiblePreemptionPoints == true)
        {
            System.out.print("    JCCostSolution: hasVisiblePreemptionPointSolution: Potential solution visible ending preemption points (size=" + potentialRightVisiblePreemptionPoints.size() + "): ");
            if (potentialRightVisiblePreemptionPoints.size() > 0)
            {
    			for (Integer item : potentialRightVisiblePreemptionPoints) 
    			{
                    visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
              		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
    			}
            }
            System.out.println();
        }
        
        existingPreemptionPointSolutionIterator = this.getPreemptionPointSolutionsIterator();
        while ((existingPreemptionPointSolutionIterator.hasNext() == true) && (hasExistingPPSolution == false))
        {
        	existingPreemptionPoints = existingPreemptionPointSolutionIterator.next();
            if (_debugSolutionPreemptionPoints == true)
            {
                System.out.print("    JCCostSolution: hasVisiblePreemptionPointSolution: Existing costmap solution preemption points: ");
                existingPreemptionPoints.displayObjectInformation();
    	    	System.out.println();
            }
            
            existingLeftVisiblePreemptionPoints = this.getVisibleStartingPPs(predecessorBlock, existingPreemptionPoints);
            
            if (_debugVisiblePreemptionPoints == true)
            {
                System.out.print("    JCCostSolution: hasVisiblePreemptionPointSolution: Existing solution visible starting preemption points (size=" + existingLeftVisiblePreemptionPoints.size() + "): ");
                if (existingLeftVisiblePreemptionPoints.size() > 0)
                {
        			for (Integer item : potentialLeftVisiblePreemptionPoints) 
        			{
                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                  		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
        			}
                }
                System.out.println();
            }
            
            existingRightVisiblePreemptionPoints = this.getVisibleEndingPPs(successorBlock, existingPreemptionPoints);
            
            if (_debugVisiblePreemptionPoints == true)
            {
                System.out.print("    JCCostSolution: hasVisiblePreemptionPointSolution: Existing solution visible ending preemption points (size=" + existingRightVisiblePreemptionPoints.size() + "): ");
                if (existingRightVisiblePreemptionPoints.size() > 0)
                {
        			for (Integer item : existingRightVisiblePreemptionPoints) 
        			{
                        visiblePPBasicBlock = JCBasicBlock.getBasicBlock(item.intValue());
                  		System.out.print("(" + visiblePPBasicBlock.getBBlockID() + "," + visiblePPBasicBlock.getBBlockName()  + "," + visiblePPBasicBlock.getBBlockWCET() + ") ");
        			}
                }
                System.out.println();
            }
            
            if ((potentialLeftVisiblePreemptionPoints.size() == existingLeftVisiblePreemptionPoints.size()) &&
            	(potentialRightVisiblePreemptionPoints.size() == existingRightVisiblePreemptionPoints.size()))
            {
            	allLeftVisiblePPs = true;
            	existingLeftVisiblePreemptionPointsIterator = existingLeftVisiblePreemptionPoints.iterator();
            	while ((existingLeftVisiblePreemptionPointsIterator.hasNext() == true) && (allLeftVisiblePPs == true))
            	{
            		existingLeftVisibleBasicBlockID = existingLeftVisiblePreemptionPointsIterator.next();
            		if (potentialLeftVisiblePreemptionPoints.contains(existingLeftVisibleBasicBlockID) == false)
            		{
            			allLeftVisiblePPs = false;
            		}
            	}
            	
            	if (allLeftVisiblePPs == true)
            	{
                	allRightVisiblePPs = true;
                	existingRightVisiblePreemptionPointsIterator = existingRightVisiblePreemptionPoints.iterator();
                	while ((existingRightVisiblePreemptionPointsIterator.hasNext() == true) && (allLeftVisiblePPs == true))
                	{
                		existingRightVisibleBasicBlockID = existingRightVisiblePreemptionPointsIterator.next();
                		if (potentialRightVisiblePreemptionPoints.contains(existingRightVisibleBasicBlockID) == false)
                		{
                			allRightVisiblePPs = false;
                		}
                	}
                	
                	if (allRightVisiblePPs == true)
                	{
                		hasExistingPPSolution = true;
                	}
            	}
            }
        }
        
    	return hasExistingPPSolution;
    }
    
	/**
	 * Displays the hexadecimal representation of the stored preemption points. 
	 *
	 * @see                JCCostSolution
	 * @see                JCPreemptionPoints
	 */
    public void displayHexValue()
    {
    	_solutionPreemptionPoints.displayHexValue();
    }
    
	/**
	 * Displays information about this cost solution entry object. 
	 *
	 * @see           JCCostSolution
	 * @see           JCPreemptionPoints
	 */
	public void displayObjectInformation() 
	{
		Iterator<JCPreemptionPoints> iterator;
		JCPreemptionPoints preemptionPoints;
		
		System.out.print("[" + _solutionCost + "," + this._solutionKey.toString() + ",");
		iterator = this._preemptionPointSolutions.iterator();
		while (iterator.hasNext() == true)
		{
			preemptionPoints = iterator.next();
			preemptionPoints.displayObjectInformation();
			System.out.print(",");
		}
		System.out.println("]");
	}
}
