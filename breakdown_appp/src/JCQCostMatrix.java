import java.util.ArrayList;

/**
 * JCQCostMatrix is a fundamental class whose purpose is to represent a 
 * Q cost matrix for the specified program.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCQCostMatrix extends JCObject 
{	
	public static final int Q_COST_MATRIX_UNUSED_ENTRY = -1;
    public static final int Q_COST_MATRIX_LARGE_VALUE = Integer.MAX_VALUE;
    
    private static int _nextID = 0;

	private static int _qCount = 0;                        // Counter to count which matrix it is
    private static ArrayList<JCQCostMatrix> _qCostMatrices = 
	          new ArrayList<JCQCostMatrix>();              // Non-preemptive + preemptive cost matrix for a task

	private int _qMatrixID;
	private int _bbCount;                                  // Basic block count
	private Integer[][] _q_cost_matrix;
    
	/**
	 * Default constructor.
	 */
    JCQCostMatrix()
    {
		int columnIndex;
		int rowIndex;

    	_qMatrixID = _nextID;
    	_bbCount = 0;
		_nextID++;
		_qCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_q_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_q_cost_matrix[rowIndex][columnIndex] = Q_COST_MATRIX_UNUSED_ENTRY;
				}
			}
		}
		else
		{
			_q_cost_matrix = null;
		}
    }
    
	/**
	 * Constructor.
	 */
    JCQCostMatrix(int bbCount)
    {
		int columnIndex;
		int rowIndex;

    	_qMatrixID = _nextID;
    	_bbCount = bbCount;
		_nextID++;
		_qCount = _nextID;
		
		if (_bbCount > 0)
		{
	    	_q_cost_matrix = new Integer[_bbCount][_bbCount];
			for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
			{
				for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
				{
					_q_cost_matrix[rowIndex][columnIndex] = Q_COST_MATRIX_UNUSED_ENTRY;
				}
			}
		}
		else
		{
			_q_cost_matrix = null;
		}
    }
    
	/**
	 * Resets the static variables for this class. 
	 *
	 * @see                JCQCostMatrix
	 */
	public static void reset()
	{
		_nextID = 0;
		_qCount = 0;
		_qCostMatrices = new ArrayList<JCQCostMatrix>();
	}
	
	/**
	 * Returns the numeric identifier of the q cost matrix object that 
	 * can be used to store associations between objects. 
	 *
	 * @return _qMatrixID  the identifier of this q cost matrix object
	 * 
	 * @see                JCQCostMatrix
	 */
	public int getQMatrixID()
	{
		return _qMatrixID;
	}

	/**
	 * Sets the size or number of the basic block objects that comprise 
	 * the current control flow graph. 
	 *
	 * @param  n    the number of basic block objects in the CFG
	 * 
	 * @see         JCQCostMatrix
	 * @see         JCSectionBlock
	 */
	public void setNValue(int n)
	{
		_bbCount = n;
	}
	    
	/**
	 * Stores the q cost matrix object at the specified location in
	 * the q cost matrix array. 
	 *
	 * @param  qMatrixID    the identifier of the stored q cost matrix object
	 * 
	 * @param  qMatrix      the q cost matrix object to be stored in the graph
	 * 
	 * @see                 JCQCostMatrix
	 */
	public static void setQCostMatrix(int qMatrixID, JCQCostMatrix qMatrix)
	{
		if (qMatrixID < _qCount)
		{
	        _qCostMatrices.add(qMatrix);
		}
	}
	
	/**
	 * Returns the q cost matrix object associated with the specified identifier. 
	 *
	 * @return qCostMatrix  the q cost matrix object stored in the array
	 * 
	 * @see                 JCQCostMatrix
	 */
	public static JCQCostMatrix getQCostMatrix(int qMatrixID)
	{
		if (qMatrixID < _qCount)
		{
		    return _qCostMatrices.get(qMatrixID);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Sets the value of the specified cell in the q cost matrix. 
	 *
	 * @param  rowIndex      the row index of the stored value in the q cost matrix
	 * 
	 * @param  columnIndex   the column index of the stored value in the q cost matrix
	 * 
	 * @param  costValue     the cost value to store in the q cost matrix
	 * 
	 * @see                  JCQCostMatrix
	 */
     public void setMatrixEntry(int rowIndex, int columnIndex, int costValue) 
     {
         _q_cost_matrix[rowIndex][columnIndex] = costValue;
     }

 	/**
 	 * Returns the value of the specified cell in the q cost matrix. 
 	 *
 	 * @param  rowIndex      the row index of the stored value in the q cost matrix
 	 * 
 	 * @param  columnIndex   the column index of the stored value in the q cost matrix
 	 * 
 	 * @return costValue     the cost value stored in the q cost matrix
 	 * 
 	 * @see                  JCQCostMatrix
 	 */
      public int getMatrixEntry(int rowIndex, int columnIndex) 
      {
          return _q_cost_matrix[rowIndex][columnIndex];
      }

	/**
	 * Returns the string q cost matrix object type name. 
	 *
	 * @return ID     the string type name of the q cost matrix object
	 * 
	 * @see           JCQCostMatrix
	 */
	@Override
	public String getObjectTypeName() 
	{
    	return "JCQCostMatrix";
	}

	/**
	 * Displays information about this q cost matrix object. 
	 *
	 * @see           JCQCostMatrix
	 */
	@Override
	public void displayObjectInformation() 
	{
		int columnIndex;
		int rowIndex;
		
		System.out.println("Q Cost Matrix:");
		System.out.println("{");
		for (rowIndex = 0; rowIndex < _bbCount; rowIndex++)
		{
			System.out.print("[");
			for (columnIndex = 0; columnIndex < _bbCount; columnIndex++)
			{
				if (columnIndex > 0)
				{
					System.out.print(",");
				}
				System.out.print(_q_cost_matrix[rowIndex][columnIndex]);
			}
			System.out.println("]");
		}
		System.out.println("}");
	}

	/**
	 * Displays information about all q cost matrix objects. 
	 *
	 * @see           JCQCostMatrix
	 */
    public static void displayAllObjects()
    {
	    int                  qMatrixID;
	    JCQCostMatrix        currentQCostMatrix;
		
		for (qMatrixID = 0; qMatrixID < _qCostMatrices.size(); qMatrixID++)
		{
			currentQCostMatrix = JCQCostMatrix.getQCostMatrix(qMatrixID);
			currentQCostMatrix.displayObjectInformation();
		}
    }
}
