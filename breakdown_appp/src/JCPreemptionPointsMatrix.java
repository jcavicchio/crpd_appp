import java.util.ArrayList;

/**
 * JCPreemptionPointsMatrix is a fundamental class whose purpose is to represent a 
 * preemption points matrix for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCPreemptionPointsMatrix extends JCObject 
{
    private static int _nextID = 0;

	private static int _ppCount = 0;                           // Counter to count which matrix it is
    private static ArrayList<JCPreemptionPointsMatrix> _preemptionPointsMatrices = 
	          new ArrayList<JCPreemptionPointsMatrix>();       // Preemption cost matrix for a task

	private int _ppMatrixID;
    private int _bbCount = 0;                                  // Basic block count	
	private JCPreemptionPoints[][] _preemption_points_matrix;
    
	/**
	 * Default constructor.
	 */
    JCPreemptionPointsMatrix()
    {
		int columnIndex;
		int rowIndex;

		_ppMatrixID = _nextID;
		_bbCount = 0;
		_nextID++;
		_ppCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_preemption_points_matrix = new JCPreemptionPoints[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_preemption_points_matrix[rowIndex][columnIndex] = null;
				}
			}
		}
		else
		{
			_preemption_points_matrix = null;
		}
    }
    
	/**
	 * Default constructor.
	 */
    JCPreemptionPointsMatrix(int bbCount)
    {
		int columnIndex;
		int rowIndex;

		_ppMatrixID = _nextID;
		_bbCount = bbCount;
		_nextID++;
		_ppCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_preemption_points_matrix = new JCPreemptionPoints[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_preemption_points_matrix[rowIndex][columnIndex] = null;
				}
			}
		}
		else
		{
			_preemption_points_matrix = null;
		}
    }
    
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCPreemptionPointsMatrix
	 */
	public static void reset()
	{
		_nextID = 0;
		_ppCount = 0;
		_preemptionPointsMatrices = new ArrayList<JCPreemptionPointsMatrix>();
	}
	
	/**
	 * Returns the numeric identifier of the preemption points matrix object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _ppMatrixID   the identifier of this preemption points matrix object
	 * 
	 * @see                  JCPreemptionPointsMatrix
	 */
	public int getPPMatrixID()
	{
		return _ppMatrixID;
	}

	/**
	 * Sets the size or number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCPreemptionPointsMatrix
	 * @see         JCSectionBlock
	 */
	public void setNValue(int n)
	{
		_bbCount = n;
	}
	    
	/**
	 * Stores the preemption points matrix object at the specified location in
	 * the preemption points matrix array. 
	 *
	 * @param  ppMatrixID    the identifier of the stored preemption points matrix object
	 * 
	 * @param  ppMatrix      the preemption points matrix object to be stored in the graph
	 * 
	 * @see                  JCPreemptionPointsMatrix
	 */
	public static void setPreemptionPointsMatrix(int ppMatrixID, JCPreemptionPointsMatrix ppMatrix)
	{
		if (ppMatrixID < _ppCount)
		{
	        _preemptionPointsMatrices.add(ppMatrix);
		}
	}
	
	/**
	 * Returns the preemption points matrix object associated with the specified identifier. 
	 *
	 * @return preemptionPointsMatrix  the preemption points matrix object stored in the array
	 * 
	 * @see                            JCPreemptionPointsMatrix
	 */
	public static JCPreemptionPointsMatrix getPreemptionPointsMatrix(int ppMatrixID)
	{
		if (ppMatrixID < _ppCount)
		{
	        return _preemptionPointsMatrices.get(ppMatrixID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Adds a preemption point to the specified cell in the preemption points matrix. 
	 *
	 * @param  rowIndex      the row index of the stored value in the preemption points matrix
	 * 
	 * @param  columnIndex   the column index of the stored value in the preemption points matrix
	 * 
	 * @param  bBlockID      the identifier of the basic block preemption point location
	 * 
	 * @see                  JCPreemptionPoints
	 * @see                  JCPreemptionPointsMatrix
	 */
     public void addPreemptionPoint(int rowIndex, int columnIndex, int bBlockID) 
     {
    	 if (_preemption_points_matrix[rowIndex][columnIndex] == null)
    	 {
    		 _preemption_points_matrix[rowIndex][columnIndex] = new JCPreemptionPoints(true);
    	 }
         _preemption_points_matrix[rowIndex][columnIndex].addPreemptionPoint(bBlockID);
     }
	
 	/**
 	 * Removes a preemption point to the specified cell in the preemption points matrix. 
 	 *
 	 * @param  rowIndex      the row index of the stored value in the preemption points matrix
 	 * 
 	 * @param  columnIndex   the column index of the stored value in the preemption points matrix
 	 * 
 	 * @param  bBlockID      the identifier of the basic block preemption point location
 	 * 
 	 * @see                  JCPreemptionPoints
 	 * @see                  JCPreemptionPointsMatrix
 	 */
      public void removePreemptionPoint(int rowIndex, int columnIndex, int bBlockID) 
      {
     	 if (_preemption_points_matrix[rowIndex][columnIndex] != null)
     	 {
             _preemption_points_matrix[rowIndex][columnIndex].removePreemptionPoint(bBlockID);
     	 }
      }

 	/**
 	 * Returns the value of the specified cell in the preemption points matrix. 
 	 *
 	 * @param  rowIndex      the row index of the stored value in the preemption points matrix
 	 * 
 	 * @param  columnIndex   the column index of the stored value in the preemption points matrix
 	 * 
	 * @return isPreemptionPointSelected   true/false indicating preemption point selected
	 * 
 	 * @see                  JCPreemptionPointsMatrix
 	 */
      public boolean isPreemptionPointSelected(int rowIndex, int columnIndex, int bBlockID)
      {
          if (_preemption_points_matrix[rowIndex][columnIndex] != null)
      	  {
              return _preemption_points_matrix[rowIndex][columnIndex].isPreemptionPointSelected(bBlockID);
      	  }
          else
          {
        	  return false;
          }
      }

  	/**
  	 * Combines the stored preemption points from the specified two locations. 
  	 *
   	 * @param  rowIndex      the row index of the stored value in the preemption points matrix
   	 * 
   	 * @param  columnIndex   the column index of the stored value in the preemption points matrix
   	 * 
   	 * @param  columnIndex2  the column index of the stored value in the preemption points matrix
   	 * 
   	 * @see                  JCPreemptionPointsMatrix
   	 */
     public void combineSectionPreemptionPoints(int rowIndex, int columnIndex, int columnIndex2)
     {
   	     JCPreemptionPoints combinedEntry;
   	     JCPreemptionPoints firstPoint;
   	     JCPreemptionPoints secondPoint;
   	  
    	 if (_preemption_points_matrix[rowIndex][columnIndex] == null)
    	 {
    		 _preemption_points_matrix[rowIndex][columnIndex] = new JCPreemptionPoints(true);
    	 }
    	 combinedEntry = _preemption_points_matrix[rowIndex][columnIndex];
    	
      	 if (_preemption_points_matrix[rowIndex][columnIndex2] == null)
      	 {
      		 _preemption_points_matrix[rowIndex][columnIndex2] = new JCPreemptionPoints(true);
      	 }
         firstPoint = _preemption_points_matrix[rowIndex][columnIndex2];
  	  
       	 if (_preemption_points_matrix[columnIndex2][columnIndex] == null)
       	 {
       		 _preemption_points_matrix[columnIndex2][columnIndex] = new JCPreemptionPoints(true);
       	 }
       	 secondPoint = _preemption_points_matrix[columnIndex2][columnIndex];
   	 
       	 combinedEntry.combineSectionPreemptionPoints(firstPoint, secondPoint);
    }
      
   	/**
   	 * Combines the stored preemption points from the specified four locations in
   	 * a conditional block. 
   	 *
   	 * @param  rowIndex      the row index of the stored value in the preemption points matrix
   	 * 
   	 * @param  rowIndex2     the row index of the stored value in the preemption points matrix
   	 * 
   	 * @param  columnIndex2  the column index of the stored value in the preemption points matrix
   	 * 
   	 * @param  columnIndex   the column index of the stored value in the preemption points matrix
   	 * 
   	 * @see                  JCPreemptionPointsMatrix
   	 */
    public void combineCondPreemptionPoints(int rowIndex, int rowIndex2, int columnIndex2, int columnIndex)
    {
        JCPreemptionPoints combinedEntry;
        JCPreemptionPoints firstPoint;
        JCPreemptionPoints secondPoint;
        JCPreemptionPoints thirdPoint;
     	  
        if (_preemption_points_matrix[rowIndex][columnIndex] == null)
        {
            _preemption_points_matrix[rowIndex][columnIndex] = new JCPreemptionPoints(true);
      	}
        combinedEntry = _preemption_points_matrix[rowIndex][columnIndex];
      	
        if (_preemption_points_matrix[rowIndex][rowIndex2] == null)
        {
            _preemption_points_matrix[rowIndex][rowIndex2] = new JCPreemptionPoints(true);
        }
        firstPoint = _preemption_points_matrix[rowIndex][rowIndex2];
  	  
        if (_preemption_points_matrix[rowIndex2][columnIndex2] == null)
        {
            _preemption_points_matrix[rowIndex2][columnIndex2] = new JCPreemptionPoints(true);
        }
        secondPoint = _preemption_points_matrix[rowIndex2][columnIndex2];
	  
        if (_preemption_points_matrix[columnIndex2][columnIndex] == null)
        {
            _preemption_points_matrix[columnIndex2][columnIndex] = new JCPreemptionPoints(true);
        }
        thirdPoint = _preemption_points_matrix[columnIndex2][columnIndex];
     	 
        combinedEntry.combineCondPreemptionPoints(firstPoint, secondPoint, thirdPoint);
    }
       
   	/**
   	 * Returns the value of the specified cell in the preemption points matrix. 
   	 *
   	 * @param  rowIndex          the row index of the stored value in the preemption points matrix
   	 * 
   	 * @param  columnIndex       the column index of the stored value in the preemption points matrix
   	 * 
  	 * @return preemptionPoints  set of preemption points selected
  	 * 
   	 * @see                      JCPreemptionPointsMatrix
   	 */
    public JCPreemptionPoints getMatrixEntry(int rowIndex, int columnIndex)
    {
        if (_preemption_points_matrix[rowIndex][columnIndex] == null)
        {
            _preemption_points_matrix[rowIndex][columnIndex] = new JCPreemptionPoints(true);
        }
        return _preemption_points_matrix[rowIndex][columnIndex];
    }

   	/**
   	 * Returns the value of the specified cell in the preemption points matrix. 
   	 *
   	 * @param  rowIndex          the row index of the stored value in the preemption points matrix
   	 * 
   	 * @param  columnIndex       the column index of the stored value in the preemption points matrix
   	 * 
  	 * @param  preemptionPoints  set of preemption points selected
  	 * 
   	 * @see                      JCPreemptionPointsMatrix
   	 */
    public void setMatrixEntry(int rowIndex, int columnIndex, JCPreemptionPoints preemptionPoints)
    {
        if (_preemption_points_matrix[rowIndex][columnIndex] == null)
        {
            _preemption_points_matrix[rowIndex][columnIndex] = new JCPreemptionPoints(true);
        }
        _preemption_points_matrix[rowIndex][columnIndex].setPreemptionPoints(preemptionPoints);
    }

	/**
	 * Returns the string preemption points matrix object type name. 
	 *
	 * @return ID     the string type name of the preemption points matrix object
	 * 
	 * @see           JCPreemptionCost
	 */
	@Override
	public String getObjectTypeName() 
	{
    	return "JCPreemptionCost";
	}

	/**
	 * Displays information about this preemption points matrix object. 
	 *
	 * @see           JCPreemptionPointsMatrix
	 */
	@Override
	public void displayObjectInformation() 
	{
		final boolean displayBBIDs = false;
		final boolean displayBBNames = true;
		final boolean displayPPHexValues = false;
		
		JCBasicBlock basicBlock;
		int bBlockID;
		int columnIndex;
		int displayCount;
		int rowIndex;
		
		System.out.println("Preemption Points Matrix:");
		System.out.println("{");
		for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
		{
			System.out.print("[");
			for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
			{
				displayCount = 0;
				if (columnIndex > 0)
				{
					System.out.print(",");
				}
				System.out.print("(");
				if (_preemption_points_matrix[rowIndex][columnIndex] != null)
				{
					if ((displayBBIDs == true) || (displayBBNames == true))
					{
						for (bBlockID = 0; bBlockID < _bbCount; bBlockID++)
						{
							if (_preemption_points_matrix[rowIndex][columnIndex].isPreemptionPointSelected(bBlockID) == true)
							{
								if (displayCount > 0)
								{
									System.out.print(",");
								}
								displayCount++;
								if (displayBBIDs == true)
								{
									System.out.print(bBlockID);
								}
								if (displayBBNames == true)
								{
									basicBlock = JCBasicBlock.getBasicBlock(bBlockID);
									System.out.print(basicBlock.getBBlockName());
								}
							}
						}
					}
					else if (displayPPHexValues == true)
					{
						_preemption_points_matrix[rowIndex][columnIndex].displayHexValue();
					}
				}
				System.out.print(")");
			}
			System.out.println("]");
		}
		System.out.println("}");
	}
	
	/**
	 * Displays information about this preemption points matrix object. 
	 *
	 * @see           JCPreemptionPointsMatrix
	 */
	public void displayObjectInformation(JCSectionBlock sectionBlock) 
	{
		final boolean displayBBIDs = false;
		final boolean displayBBNames = true;
		final boolean displayPPHexValues = false;
		
		JCBasicBlock basicBlock;
		int bBlockID;
		int columnIndex;
		int displayCount;
		int rowIndex;
		
		System.out.println("Preemption Points Matrix:");
		System.out.println("{");
		for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
		{
			System.out.print("[");
			for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
			{
				displayCount = 0;
				if (columnIndex > 0)
				{
					System.out.print(",");
				}
				System.out.print("(");
				if (_preemption_points_matrix[rowIndex][columnIndex] != null)
				{
					if ((displayBBIDs == true) || (displayBBNames == true))
					{
						for (bBlockID = 0; bBlockID < _bbCount; bBlockID++)
						{
							if (_preemption_points_matrix[rowIndex][columnIndex].isPreemptionPointSelected(bBlockID) == true)
							{
								if (displayCount > 0)
								{
									System.out.print(",");
								}
								displayCount++;
								if (displayBBIDs == true)
								{
									System.out.print(bBlockID);
								}
								if (displayBBNames == true)
								{
									basicBlock = sectionBlock.getBasicBlockAtIndex(bBlockID);
									System.out.print(basicBlock.getBBlockName());
								}
							}
						}
					}
					else if (displayPPHexValues == true)
					{
						_preemption_points_matrix[rowIndex][columnIndex].displayHexValue();
					}
				}
				System.out.print(")");
			}
			System.out.println("]");
		}
		System.out.println("}");
	}

	/**
	 * Displays information about all preemption points matrix objects. 
	 *
	 * @see           JCPreemptionPointsMatrix
	 */
    public static void displayAllObjects()
    {
	    int                       ppMatrixID;
	    JCPreemptionPointsMatrix  currentPreemptionPointMatrix;
		
		for (ppMatrixID = 0; ppMatrixID < _preemptionPointsMatrices.size(); ppMatrixID++)
		{
			currentPreemptionPointMatrix = JCPreemptionPointsMatrix.getPreemptionPointsMatrix(ppMatrixID);
			currentPreemptionPointMatrix.displayObjectInformation();
		}
    }
}
