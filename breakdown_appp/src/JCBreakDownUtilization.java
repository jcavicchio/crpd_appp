import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Breakdown is a fundamental class whose purpose is to perform a breakdown
 * utilization study for a set of selected benchmark programs.
 * 
 * @author      John Cavicchio
 * @version     %I%, %G%
 * @since       1.0
 */
public class JCBreakDownUtilization 
{
    private static int INC_BRT_CYCLES = 10;
    private static long LARGEST_COST = Long.MAX_VALUE;
    private static int MIN_BRT_CYCLES = 0;
    private static int MAX_BRT_CYCLES = 390;
    private static int NUMBER_OF_TASKS = 14; // 15
    private static int SCHEDULE_OK = 1;
    private static int SCHEDULE_NOTOK = 0;
    private static int INC_UTIL_PCNT = 1;
    private static int MIN_UTIL_PCNT = 1;
    private static int MAX_UTIL_PCNT = 100;

    private static boolean _debugAdjustedWCETCost = false;
    private static boolean _debugBreakdownUtilizationValue = false;
    private static boolean _debugBinarySearch = false; // JCC2
    private static boolean _debugBRTHeapUsage = false;
    private static boolean _debugCacheReloadCycles = false; // JCC2
    private static boolean _debugComputeGCDValues = false;
    private static boolean _debugComputeTaskDeadlines = false;
    private static boolean _debugDisplayExecutionTime = true;
    private static boolean _debugEvaluateTaskDBF = false;
    private static boolean _debugEvaluateTaskDBFValues = false;
    private static boolean _debugEvaluateTaskSlack = false;
    private static boolean _debugHyperPeriod = false;
    private static boolean _debugMaximumDeadline = false;
    private static boolean _debugMaximumTaskUCBCost = false; // JCC
    private static boolean _debugOPPCallForTask = false;
    private static boolean _debugOptimalPreemptionPointPlacement = false; // JCC
    private static boolean _debugPreemptionTaskSetUtilization = false; // JCC
    private static boolean _debugTargetUtilization = false; // JCC2
    private static boolean _debugTaskComputeSlack = false;
    private static boolean _debugTaskMinimumWCETCost = false;
    private static boolean _debugTaskParameters = false;
    private static boolean _debugTaskSetQIParameters = false;
    private static boolean _debugTaskSetSchedulability = false; // JCC2
    private static boolean _debugTaskSetUtilization = false;
    private static boolean _debugTaskWCETCost = true; // JCC
    private static boolean _debugEnablePreemptionBound = false;
    
    private static boolean _simpleBenchmarkIncluded = true;
    private static boolean _bsBenchmarkIncluded = true;
    private static boolean _fibcallBenchmarkIncluded = true;
    private static boolean _lcdnumBenchmarkIncluded = true;
    private static boolean _sqrtBenchmarkIncluded = true;
    private static boolean _qurtBenchmarkIncluded = true;
    private static boolean _insertsortBenchmarkIncluded = true;
    private static boolean _minverBenchmarkIncluded = false;
    private static boolean _nsBenchmarkIncluded = true;
    private static boolean _udBenchmarkIncluded = true;
    private static boolean _crcBenchmarkIncluded = true;
    private static boolean _expintBenchmarkIncluded = true;
    private static boolean _jfdctintBenchmarkIncluded = true;
    private static boolean _matmultBenchmarkIncluded = true;
    private static boolean _bsort100BenchmarkIncluded = true;
    
    public static int computeTaskNPRWCET(String filePrefix)
    {
        int taskNPRWCET = 0;
       
        return taskNPRWCET;
    }
    
    public static double computeTaskSetUtilization(int numberOfTasks, long[] taskPeriods, long[] taskWCETs)
    {
    	double taskSetUtilization = 0.0;
    	int taskIdx;

	    for (taskIdx = 0; taskIdx < numberOfTasks; taskIdx++)
	    {
	    	taskSetUtilization += (double)taskWCETs[taskIdx]/(double)taskPeriods[taskIdx];
	    }
	
    	return taskSetUtilization;
    }

    public static long gcd(long a, long b)
    {
    	long c;
    	long as = a;
    	long bs = b;

        while ( a != 0 ) 
        {
            c = a; 
            a = b%a;  
            b = c;
        }
        
        if (_debugComputeGCDValues == true)
        {
            System.out.println("GCD of " + as + " and " + bs + " is " + b);
        }
        
        return b;
    }

    public static long lcm(long x, long y) 
    {
    	long a;
    	long b;
    	long t;
    	long gcd;
    	long lcmm;
     
        a = x;
        b = y;
     
        while (b != 0) 
        {
            t = b;
            b = a % b;
            a = t;
        }
     
        gcd = a;
        lcmm = (x*y)/gcd;
     
        if (_debugComputeGCDValues == true)
        {
            System.out.println("Greatest common divisor of " + x + " and " + y + " = " + gcd);
            System.out.println("Least common multiple of " + x + " and " + y + " = " + lcmm);
        }
        
        return lcmm;
    }

    public static long computeHyperPeriod(long numberOfTasks, long[] taskPeriods)
    {
    	long hyperPeriod = 1;
        int idx;

        for (idx=0; idx<numberOfTasks; idx++)
        {
            hyperPeriod = lcm(hyperPeriod, taskPeriods[idx]);
        }
        return hyperPeriod;
    }

    public static long getMaxDeadline(long numberOfTasks, long[] taskDeadlines)
    {
        int idx;
        long maxDeadline = Long.MIN_VALUE;

        for (idx=0; idx<numberOfTasks; idx++)
        {
            if (maxDeadline < taskDeadlines[idx])
            {
                 maxDeadline = taskDeadlines[idx];
            }
        }
        return maxDeadline;
    }

    public static long getNextDeadline(long dcurr, int numberOfTasks, long[] taskDeadlines, long[] taskPeriods)
    {
    	long currDeadline;
    	long nextDeadline = LARGEST_COST;
        int taskIdx;

        for (taskIdx = 0; taskIdx < numberOfTasks; taskIdx++)
        {
            currDeadline = ((dcurr / taskPeriods[taskIdx]) * taskPeriods[taskIdx]) + taskDeadlines[taskIdx];
            if ((nextDeadline > currDeadline) && (currDeadline > dcurr))
            {
                nextDeadline = currDeadline;
            }
        }
        return nextDeadline;
    }

    public static long evaluateTaskDBF(long t, long p, long d, long e)
    {
    	long value;

        if (_debugEvaluateTaskDBFValues == true)
        {
            System.out.println("t:" + t + " p:" + p + " d:" + d + " e:" + e);
        }
        
        if (t >= d)
        {
            value = (((t - d)/p) + 1) * e;
        }
        else
        {
            value = 0;
        }

        return value;
    }

    public static long evaluateSlack(long dk, int numberOfTasks, long[] taskPeriods, long[] taskDeadlines, long[] taskWCETs) 
    {
    	long  dj = getNextDeadline(0, numberOfTasks, taskDeadlines, taskPeriods);
        long  currSlack;
        long  minSlack = LARGEST_COST;
        long  sum;
        long  taskDBF;
        int  taskIdx;

        while (dj <= dk)
        {
            sum = 0;
            for (taskIdx = 0; taskIdx < numberOfTasks; taskIdx++)
            {
                taskDBF = evaluateTaskDBF(dj, taskPeriods[taskIdx], taskDeadlines[taskIdx], taskWCETs[taskIdx]);
                if (_debugEvaluateTaskDBF == true)
                {
                    System.out.println("t:" + dj + " p:" + taskPeriods[taskIdx] + " d:" + taskDeadlines[taskIdx] + " e:" + taskWCETs[taskIdx] + " dbf:" + taskDBF);
                }
                sum += taskDBF; 
            }
            
            currSlack = dj - sum;  
            if (minSlack > currSlack)
            {
                minSlack = currSlack;
            }
            
            if (_debugEvaluateTaskSlack == true)
            {
                System.out.println("t:" + dj + " sum:" + sum + " slack:" + currSlack + " minSlack:" + minSlack);
            }
            dj = getNextDeadline(dj, numberOfTasks, taskDeadlines, taskPeriods);
        }

        return minSlack;
    }

    public static long optimalPPPlacement(String fileNamePrefix, long maxTaskQI, long cacheBRTCycles)
    {
		BufferedReader costFileBr = null;
		FileReader costFileFr = null;
    	String costFileName = fileNamePrefix + "_cost.txt";
		String currentLine;
    	String grammarFileName = fileNamePrefix + "_grammarwithq.txt";
		BufferedWriter newGrammarFileBw = null;
		FileWriter newGrammarFileFw = null;
		BufferedReader originalGrammarFileBr = null;
		FileReader originalGrammarFileFr = null;
    	String originalGrammarFileName = fileNamePrefix + "_grammar.txt";
		JCPreemptionPointParser parser;
		long totalCost = LARGEST_COST;
		
        try 
        {
        	newGrammarFileFw = new FileWriter(grammarFileName);
        	newGrammarFileBw = new BufferedWriter(newGrammarFileFw);
        	originalGrammarFileFr = new FileReader(originalGrammarFileName);
        	originalGrammarFileBr = new BufferedReader(originalGrammarFileFr);

        	currentLine = cacheBRTCycles + " " + maxTaskQI + " ";
        	newGrammarFileBw.write(currentLine);
        	
        	currentLine = originalGrammarFileBr.readLine();
			while (currentLine != null) 
			{
	        	newGrammarFileBw.write(currentLine);
	        	newGrammarFileBw.newLine();
	        	currentLine = originalGrammarFileBr.readLine();
			}
        } 
        catch (IOException e) 
        {
			e.printStackTrace();
		} 
        finally 
        {
		    try 
		    {
				if (newGrammarFileBw != null)
				{
					newGrammarFileBw.close();
				}
				
				if (newGrammarFileFw != null)
				{
					newGrammarFileFw.close();
				}
				
				if (originalGrammarFileBr != null)
				{
					originalGrammarFileBr.close();
				}
				
				if (originalGrammarFileFr != null)
				{
					originalGrammarFileFr.close();
				}
				
				parser = new JCPreemptionPointParser();
				parser.setParseFileName(grammarFileName);
				parser.parseFile();
				
				costFileFr = new FileReader(costFileName);
				costFileBr = new BufferedReader(costFileFr);
	        	currentLine = costFileBr.readLine();
				if (currentLine != null) 
				{
					totalCost = Long.parseLong(currentLine);
				}
			} 
		    catch (IOException ex) 
		    {
				ex.printStackTrace();
			}
		    finally
		    {
			    try 
			    {
					if (costFileBr != null)
					{
						costFileBr.close();
					}
					
					if (costFileFr != null)
					{
						costFileFr.close();
					}
			    }
			    catch (IOException ex) 
			    {
					ex.printStackTrace();
				}
		    }
        }

    	return totalCost;
    }
    
	public static void displayHeapStatistics()
	{
		// Get current size of heap in bytes
		long heapSize = Runtime.getRuntime().totalMemory(); 
		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		// Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		System.out.println("Maximum heap: " + heapMaxSize + " bytes used heap: " + heapSize + " bytes free heap: " + heapFreeSize + " bytes");
	}
	
	public static void main(String[] args) 
	{
		int benchmarkIndex = 0;
	    long breakdownUtilization;
	    long cacheBRTCycles;
	    long currDeadline;
	    boolean binarySearchDone;
		String[] fileNamePrefixes;
		long hyperPeriod;
		long maxDeadline;
		long[] maxTaskQI;
		long[] maxUCBCount;
		double preemptTaskUtilization;
		long scaleFactor;
		int schedulable;
		long slack;
		int targetTaskUtilizationHigh;
		int targetTaskUtilizationLow;
		int targetTaskUtilizationMid;
		long[] taskDeadlines;
	    long[] taskPeriods;
	    double taskSetUtilization;
	    long[] taskWCETs;
		int taskIdx;
		long[] totalPCost;
		long[] totalPOCost;
		
		long startTime;
		long endTime;
		long elapsedTime;
		
		long brtStartTime;
		long brtEndTime;
		long brtElapsedTime;
		
    	startTime = System.nanoTime();

    	totalPCost = new long[NUMBER_OF_TASKS];
		totalPOCost = new long[NUMBER_OF_TASKS];
		maxTaskQI = new long[NUMBER_OF_TASKS];
		taskDeadlines = new long[NUMBER_OF_TASKS];
		taskPeriods = new long[NUMBER_OF_TASKS];
		taskWCETs = new long[NUMBER_OF_TASKS];
		maxUCBCount = new long[NUMBER_OF_TASKS];
		fileNamePrefixes = new String[NUMBER_OF_TASKS];
		
		if (_simpleBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "simple";
			taskWCETs[benchmarkIndex] = 92;
			maxUCBCount[benchmarkIndex] = 45;
			benchmarkIndex++;
		}
		
		if (_bsBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "bs";
			taskWCETs[benchmarkIndex] = 852;
			maxUCBCount[benchmarkIndex] = 178;
			benchmarkIndex++;
		}
		
		if (_fibcallBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "fibcall";
			taskWCETs[benchmarkIndex] = 3908;
			maxUCBCount[benchmarkIndex] = 130;
			benchmarkIndex++;
		}
		
		if (_lcdnumBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "lcdnum";
			taskWCETs[benchmarkIndex] = 4636;
			maxUCBCount[benchmarkIndex] = 331;
			benchmarkIndex++;
		}
		
		if (_sqrtBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "sqrt";
			taskWCETs[benchmarkIndex] = 11250;
			maxUCBCount[benchmarkIndex] = 519;
			benchmarkIndex++;
    	}
		
		if (_qurtBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "qurt";
			taskWCETs[benchmarkIndex] = 14821;
			maxUCBCount[benchmarkIndex] = 2537;
			benchmarkIndex++;
    	}
		
		if (_insertsortBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "insertsort";
			taskWCETs[benchmarkIndex] = 25384;
			maxUCBCount[benchmarkIndex] = 296;
			benchmarkIndex++;
    	}
		
		if (_minverBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "minver";
			taskWCETs[benchmarkIndex] = 60733;
			maxUCBCount[benchmarkIndex] = 2912;
			benchmarkIndex++;
    	}
		
		if (_nsBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "ns";
			taskWCETs[benchmarkIndex] = 253024;
			maxUCBCount[benchmarkIndex] = 445;
			benchmarkIndex++;
		}
		
		if (_udBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "ud";
			taskWCETs[benchmarkIndex] = 398906;
			maxUCBCount[benchmarkIndex] = 1174;
			benchmarkIndex++;
    	}
		
		if (_crcBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "crc"; 
			taskWCETs[benchmarkIndex] = 574260; 
			maxUCBCount[benchmarkIndex] = 1118;
			benchmarkIndex++;
    	}
		
		if (_expintBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "expint";
			taskWCETs[benchmarkIndex] = 1088401; 
			maxUCBCount[benchmarkIndex] = 607;
			benchmarkIndex++;
    	}

		if (_jfdctintBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "jfdctint";
			taskWCETs[benchmarkIndex] = 1458456;
			maxUCBCount[benchmarkIndex] = 1895;
			benchmarkIndex++;
    	}
		
		if (_matmultBenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "matmult";
			taskWCETs[benchmarkIndex] = 1760729;
			maxUCBCount[benchmarkIndex] = 590;
			benchmarkIndex++;
    	}
		
		if (_bsort100BenchmarkIncluded == true)
		{
			fileNamePrefixes[benchmarkIndex] = "bsort100";
			taskWCETs[benchmarkIndex] = 3118906;
			maxUCBCount[benchmarkIndex] = 454;
			benchmarkIndex++;
    	}
		
		cacheBRTCycles = MAX_BRT_CYCLES;
	    while (cacheBRTCycles > MIN_BRT_CYCLES)
	    {
	    	brtStartTime = System.nanoTime();

	    	if (_debugCacheReloadCycles == true)
	    	{
		        System.out.println("Processing with cache reload cycles = " + cacheBRTCycles);
	    	}

	        /* Start with a minimum working target task set utilization and work higher. */
	        targetTaskUtilizationLow = MIN_UTIL_PCNT;
	        targetTaskUtilizationHigh = MAX_UTIL_PCNT;
        	targetTaskUtilizationMid = (targetTaskUtilizationLow + targetTaskUtilizationHigh)/2;
	        binarySearchDone = false;
	        breakdownUtilization = 0;
	        while (binarySearchDone == false)
	        {
		        schedulable = SCHEDULE_OK;
		        
		    	if (_debugTargetUtilization == true)
		    	{
		    		System.out.println("Processing with target utilization = " + targetTaskUtilizationMid);
		    	}
		    	
	            scaleFactor = (100*NUMBER_OF_TASKS)/targetTaskUtilizationMid;
	            for (taskIdx=0; taskIdx < NUMBER_OF_TASKS; taskIdx++)
	            {
	                //taskWCETs[taskIdx] = computeTaskNPRWCET(fileNamePrefixes[taskIdx]);
	                taskPeriods[taskIdx] = taskDeadlines[taskIdx] = scaleFactor*taskWCETs[taskIdx];
	                if (_debugTaskParameters == true)
	                {
	                	System.out.println("Task " + (taskIdx+1) + "(" + fileNamePrefixes[taskIdx] + "): e,p,d (" + taskWCETs[taskIdx] + "," + taskPeriods[taskIdx] + "," + taskDeadlines[taskIdx]);
	                }
	            }
	            
	            taskSetUtilization = computeTaskSetUtilization(NUMBER_OF_TASKS, taskPeriods, taskWCETs);
	            if (_debugTaskSetUtilization == true)
	            {
	            	System.out.println("Task Set utilization is " + taskSetUtilization);
	            }
	            
	            /* Compute the maximum non-preemptive region parameter for each task. */
	            hyperPeriod = computeHyperPeriod(NUMBER_OF_TASKS, taskPeriods);
	            if (_debugHyperPeriod == true)
	            {
	            	System.out.println("Hyper Period: " + hyperPeriod);
	            }
	            
	            maxDeadline = getMaxDeadline(NUMBER_OF_TASKS, taskDeadlines);
	            if (_debugMaximumDeadline == true)
	            {
	            	System.out.println("Maximum Deadline: " + maxDeadline);
	            }
	            
	            currDeadline = 0;
	            currDeadline = getNextDeadline(currDeadline, NUMBER_OF_TASKS, taskDeadlines, taskPeriods);
	            
	            //while (currDeadline <= maxDeadline)
	            //{
	                if (_debugComputeTaskDeadlines == true)
	                {
	                	System.out.println("Deadline: " + currDeadline);
	                }
	                
	                slack = evaluateSlack(currDeadline, NUMBER_OF_TASKS, taskPeriods, taskDeadlines, taskWCETs);
	                if (_debugTaskComputeSlack == true)
	                {
	                	System.out.println("Deadline " + currDeadline + ": slack: " + slack);
	                }
	                
	                for (taskIdx = 0; taskIdx < NUMBER_OF_TASKS; taskIdx++)
	                {
	                    //if (taskDeadlines[taskIdx] == currDeadline)
	                    //{
	                    //    maxTaskQI[taskIdx] = (taskWCETs[taskIdx] < slack) ? taskWCETs[taskIdx] : slack;
	                        maxTaskQI[taskIdx] = slack;
	                    //}
	                }

	                //currDeadline = getNextDeadline(currDeadline, NUMBER_OF_TASKS, taskDeadlines, taskPeriods);
	            //}

	            if (_debugTaskSetQIParameters == true)
	            {
	                for (taskIdx = 0; taskIdx < NUMBER_OF_TASKS; taskIdx++)
	                {
	                	System.out.println("Task " + (taskIdx+1) + "(" + fileNamePrefixes[taskIdx] + "): QI: " + maxTaskQI[taskIdx]);
	                }
	            }
	            
	            /* Perform optimal preemption point placement on the specified tasks. */
	            schedulable = SCHEDULE_OK;
	            for (taskIdx=0; taskIdx < NUMBER_OF_TASKS; taskIdx++)
	            {
	                /* Perform optimal preemption point placement on the loaded data. */
	                if (_debugOptimalPreemptionPointPlacement == true)
	                {
	                	System.out.println();
	                    System.out.println("Placing preemption points for task " + (taskIdx+1) + " (" + fileNamePrefixes[taskIdx] + ") QI " + maxTaskQI[taskIdx]);
	                }
	                
	                if (_debugOPPCallForTask == true)
	                {
	                	System.out.println("Placing PP for task " + fileNamePrefixes[taskIdx]);
	                }
	                totalPCost[taskIdx] = optimalPPPlacement(fileNamePrefixes[taskIdx], maxTaskQI[taskIdx], cacheBRTCycles);

	                System.gc();
	                System.runFinalization();
	                
	                /* Bound the preemption cost by the task UCB count times the cache block reload cycles. */
	                if (_debugEnablePreemptionBound == true)
	                {
		                if ((totalPCost[taskIdx] < LARGEST_COST) && (totalPCost[taskIdx] > 0))
		                {
		                    totalPOCost[taskIdx] = totalPCost[taskIdx] - taskWCETs[taskIdx];
		                    if (totalPOCost[taskIdx] > (maxUCBCount[taskIdx]*cacheBRTCycles))
		                    {
		                        if (_debugMaximumTaskUCBCost == true)
		                        {
		                        	System.out.println("BRT: " + cacheBRTCycles + " The maximum UCB cost (" + maxUCBCount[taskIdx]*cacheBRTCycles + ") for task " + (taskIdx+1) + " (" + fileNamePrefixes[taskIdx] + ") is exceeded: " + totalPCost[taskIdx]);
		                        }
		                        
		                        totalPCost[taskIdx] = totalPCost[taskIdx] - totalPOCost[taskIdx] + (maxUCBCount[taskIdx]*cacheBRTCycles);
		                        if (_debugAdjustedWCETCost == true)
		                        {
		                        	System.out.println("The adjusted WCET cost for task " + (taskIdx+1) + " (" + fileNamePrefixes[taskIdx] + ") is " + totalPCost[taskIdx]);
		                        }
		                    }
		                }
	                }

	                if ((totalPCost[taskIdx] >= LARGEST_COST) || (totalPCost[taskIdx] < 0) || (totalPCost[taskIdx] > taskDeadlines[taskIdx]))
	                {
	                    schedulable = SCHEDULE_NOTOK;
	                    if (_debugTaskWCETCost == true)
	                    {
	                    	System.out.println("The minimum WCET cost for task " + (taskIdx+1) + " (" + fileNamePrefixes[taskIdx] + ") is: " + totalPCost[taskIdx] + " deadline: " + taskDeadlines[taskIdx] + " Qi: " + maxTaskQI[taskIdx]);
	                    }
	                }
	                else
	                {
	                    if (totalPCost[taskIdx] > taskWCETs[taskIdx])
	                    {
	                        if (_debugTaskMinimumWCETCost == true)
	                        {
	                        	System.out.println("The minimum WCET cost for task " + (taskIdx+1) + " (" + fileNamePrefixes[taskIdx] + ") is: " + totalPCost[taskIdx]);
	                        	System.out.println("    Task NPR WCET is " + taskWCETs[taskIdx] + ", task preemption cost is " + (totalPCost[taskIdx]-taskWCETs[taskIdx]));
	                        }
	                    }
	                }
	            }

                if (schedulable == SCHEDULE_OK)
                {
                    preemptTaskUtilization = computeTaskSetUtilization(NUMBER_OF_TASKS, taskPeriods, totalPCost);

                    if (preemptTaskUtilization > 1.0)
                    {
                        if (_debugPreemptionTaskSetUtilization == true)
                        {
                        	System.out.println("Task Set utilization with preemption is " + preemptTaskUtilization);
                        }
                        schedulable = SCHEDULE_NOTOK;
                    }
                }

                if (schedulable == SCHEDULE_OK)
                {
                    breakdownUtilization = targetTaskUtilizationMid;
                    if (_debugTaskSetSchedulability == true)
                    {
                    	System.out.println("The taskset is SCHEDULABLE for a utilization of " + breakdownUtilization + " percent.");
                    }
                }
                else
                {
                    if (_debugTaskSetSchedulability == true)
                    {
                	    System.out.println("The taskset is NOT SCHEDULABLE for a utilization of " + targetTaskUtilizationMid + " percent.");
                    }
                }
            
                if (schedulable == SCHEDULE_OK)
                {
                	targetTaskUtilizationLow = targetTaskUtilizationMid;
                }
                else
                {
                	if (targetTaskUtilizationMid > targetTaskUtilizationLow)
                	{
                    	targetTaskUtilizationHigh = targetTaskUtilizationMid - 1;
                	}
                	else
                	{
                    	targetTaskUtilizationHigh = targetTaskUtilizationMid;
                	}
                }
                
                if (targetTaskUtilizationLow == targetTaskUtilizationHigh)
                {
                    breakdownUtilization = targetTaskUtilizationMid;
                    binarySearchDone = true;
                }
                else if ((targetTaskUtilizationLow + 1) == targetTaskUtilizationHigh)
                {
                	if ((targetTaskUtilizationMid == targetTaskUtilizationHigh) && 
                		(schedulable == SCHEDULE_NOTOK))
                	{
                		targetTaskUtilizationMid = targetTaskUtilizationLow;
                	}
                	else if ((targetTaskUtilizationMid == targetTaskUtilizationLow) && 
                    		 (schedulable == SCHEDULE_OK))
                	{
                		targetTaskUtilizationMid = targetTaskUtilizationHigh;
                	}
                	else
                	{
                        breakdownUtilization = targetTaskUtilizationMid;
                        binarySearchDone = true;
                	}
                }
                else
                {
                    if (schedulable == SCHEDULE_OK)
                    {
                    	if (targetTaskUtilizationMid != ((targetTaskUtilizationMid + targetTaskUtilizationHigh)/2))
                    	{
                        	targetTaskUtilizationMid = (targetTaskUtilizationMid + targetTaskUtilizationHigh)/2;
                    	}
                    	else
                    	{
                    		targetTaskUtilizationMid = targetTaskUtilizationHigh;
                    	}
                    }
                    else
                    {
                    	if (targetTaskUtilizationMid != ((targetTaskUtilizationLow + targetTaskUtilizationMid)/2))
                    	{
                        	targetTaskUtilizationMid = (targetTaskUtilizationLow + targetTaskUtilizationMid)/2;
                    	}
                    	else
                    	{
                    		targetTaskUtilizationMid = targetTaskUtilizationLow;
                    	}
                    }
                }
    	        if (_debugBinarySearch == true)
    	        {
                    System.out.println("Binary search: low " + targetTaskUtilizationLow + " mid " + targetTaskUtilizationMid + " high " + targetTaskUtilizationHigh);
    	        }
		    }
	        
	    	brtEndTime = System.nanoTime();
	    	brtElapsedTime = brtEndTime - brtStartTime;
	    	
	        System.out.println("BRT: " + cacheBRTCycles + " U: " + (double)((double)(breakdownUtilization+1)/(double)100.0) + " T: " + (brtElapsedTime/1000000000l)); 
	        if (_debugBreakdownUtilizationValue == true)
	        {
	        	System.out.println("The breakdown utilization for cache block reload cycles of " + cacheBRTCycles + " is: " + (breakdownUtilization+1) + " percent");
	        }

	        cacheBRTCycles -= INC_BRT_CYCLES;
	        
			if (_debugBRTHeapUsage == true)
			{
				displayHeapStatistics();
			}
	    }
	    
    	endTime = System.nanoTime();
    	elapsedTime = endTime - startTime;
		if (_debugDisplayExecutionTime == true)
		{
	    	System.out.println("Computation time is " + (elapsedTime/1000000000l) + " seconds " + (elapsedTime/1000000l) + " milliseconds " + (elapsedTime/1000l) + " microseconds");
		}
        
		if (_debugBRTHeapUsage == true)
		{
			displayHeapStatistics();
		}
	}
}
