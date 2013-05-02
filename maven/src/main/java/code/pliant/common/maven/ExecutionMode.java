package code.pliant.common.maven;


/**
 * Description of execution modes.
 * 
 * @author Daniel Rugg
 */
public enum ExecutionMode {
	
	/**
	 * Execution mode, available only on windows, for interacting with the processes.
	 */
	INTERACTIVE, 
	
	/**
	 * Standard execution mode, where the process will run and end.
	 */
	BATCH
}
