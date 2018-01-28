import java.util.Arrays;

/**
 * JCCostKey is a fundamental class whose purpose is to specify a 
 * zetaPred (zeta1) and zetaSucc (zeta2) pairing supporting the 
 * execution interface between the predecessor subgraph and the 
 * successor subgraph.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
class JCCostKey
{
	private final int _zetaPredIndex = 0;
	private final int _zetaSuccIndex = 1;
	private final int _predBasicBlock = 2;
	private final int _succBasicBlock = 3;
	
	private final int _keyArraySize = 4;
	
	int[] _keysArray = null;
	
	/**
	 * Default constructor.
	 */
    JCCostKey()
    {
    	int idx;
    	
    	_keysArray = new int[_keyArraySize];
    	
    	for (idx = 0; idx < _keyArraySize; idx++)
    	{
		    _keysArray[idx] = 0;
    	}
    }
    
	/**
	 * The constructor used by the parser.
	 */
	JCCostKey(int zeta_pred, int zeta_succ)
	{
		_keysArray = new int[_keyArraySize];
		
		_keysArray[_zetaPredIndex] = zeta_pred;
		_keysArray[_zetaSuccIndex] = zeta_succ;
		_keysArray[_predBasicBlock] = 0;
		_keysArray[_succBasicBlock] = 0;
	}
	
	/**
	 * Sets the cost key indices used to represent execution interfaces 
	 * between subgraphs. 
	 *
	 * @param zeta_pred   the left offset or zeta1 of the predecessor interface
	 * 
	 * @param zeta_succ   the right offset or zeta2 of the successor interface
	 * 
	 * @see               JCCostKey
	 */
	public void setIndices(int zeta_pred, int zeta_succ)
	{
		_keysArray[_zetaPredIndex] = zeta_pred;
		_keysArray[_zetaSuccIndex] = zeta_succ;
	}
	
	/**
	 * Gets the left cost key index used to represent execution interfaces 
	 * between subgraphs. 
	 *
	 * @return zeta_pred  the left offset or zeta_pred of the predecessor interface
	 * 
	 * @see          JCCostKey
	 */
	public int getLeftIndex()
	{
		return _keysArray[_zetaPredIndex];
	}
	
	/**
	 * Sets the left cost key index used to represent execution interfaces 
	 * between subgraphs. 
	 *
	 * @param zeta_pred  the left offset or zeta_pred of the predecessor interface
	 * 
	 * @see              JCCostKey
	 */
	public void setLeftIndex(int zeta_pred)
	{
		_keysArray[_zetaPredIndex] = zeta_pred;
	}
	
	/**
	 * Gets the right cost key index used to represent execution interfaces 
	 * between subgraphs. 
	 *
	 * @return right  the right offset or zeta_succ of the successor interface
	 * 
	 * @see           JCCostKey
	 */
	public int getRightIndex()
	{
		return _keysArray[_zetaSuccIndex];
	}
	
	/**
	 * Sets the right cost key index used to represent execution interfaces 
	 * between subgraphs. 
	 *
	 * @param zeta_succ  the right offset or zeta_succ of the successor interface
	 * 
	 * @see              JCCostKey
	 */
	public void setRightIndex(int zeta_succ)
	{
		_keysArray[_zetaSuccIndex] = zeta_succ;
	}
	
	/**
	 * Gets the left basic block ID used to represent subgraph boundaries. 
	 *
	 * @return basicBlockID  the left basic block ID
	 * 
	 * @see                  JCCostKey
	 */
	public int getLeftBasicBlock()
	{
		return _keysArray[_predBasicBlock];
	}
	
	/**
	 * Sets the left basic block ID used to represent subgraph boundaries. 
	 *
	 * @param basicBlockID  the left basic block ID
	 * 
	 * @see                 JCCostKey
	 */
	public void setLeftBasicBlock(int basicBlockID)
	{
		_keysArray[_predBasicBlock] = basicBlockID;
	}
		
	/**
	 * Gets the right basic block ID used to represent subgraph boundaries. 
	 *
	 * @return basicBlockID  the right basic block ID
	 * 
	 * @see                  JCCostKey
	 */
	public int getRightBasicBlock()
	{
		return _keysArray[_succBasicBlock];
	}
	
	/**
	 * Sets the right basic block ID used to represent subgraph boundaries. 
	 *
	 * @param basicBlockID  the right basic block ID
	 * 
	 * @see                 JCCostKey
	 */
	public void setRightBasicBlock(int basicBlockID)
	{
		_keysArray[_succBasicBlock] = basicBlockID;
	}
	
	/**
	 * Returns the numeric hash code corresponding to the cost key used to
	 * represent execution interfaces between subgraphs. 
	 *
	 * @return hashCode   the computed hash code of this cost key object
	 * 
	 * @see               Arrays
	 * @see               JCCostKey
	 */
	public int hashCode()
	{
		return Arrays.hashCode(_keysArray);
	}
	
	/**
	 * Determines is the specified object is a JCCostKey object with 
	 * matching cost key indices.
	 *
	 * @return object     the cost key object to compare indices with
	 * 
	 * @see               JCCostKey
	 */
	public boolean equals (Object o)
	{
		return (o instanceof JCCostKey) && 
				(_keysArray[_zetaPredIndex] == ((JCCostKey) o)._keysArray[_zetaPredIndex]) &&
				(_keysArray[_zetaSuccIndex] == ((JCCostKey) o)._keysArray[_zetaSuccIndex]) &&
				(_keysArray[_predBasicBlock] == ((JCCostKey) o)._keysArray[_predBasicBlock]) &&
                (_keysArray[_succBasicBlock] == ((JCCostKey) o)._keysArray[_succBasicBlock]);
	}
	
	/**
	 * Converts this object into a representative string for display purposes.
	 *
	 * @return valueString    the representative string for display purposes
	 * 
	 * @see                   JCCostKey
	 */
	@Override
	public String toString()
	{
		String valueString;
		
		valueString = "JCCostKey (" + _keysArray[_zetaPredIndex] + "," + _keysArray[_zetaSuccIndex] + "," + _keysArray[_predBasicBlock] + "," + _keysArray[_succBasicBlock] + ")";
		return valueString;
	}
}