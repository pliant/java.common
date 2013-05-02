package code.pliant.common.spring;

/**
 * Provides a list of constant qualifier values that can be used in wiring classes together.  The 
 * value of each qualify is the same as their given name.
 * 
 * @author Daniel Rugg
 */
public class Qualifiers {

	/**
	 * Constant to use to tag a bean with a qualifier of 'SERVICE'.
	 */
	public static final String SERVICE = "SERVICE";

	/**
	 * Constant to use to tag a bean with a qualifier of 'DAO'.
	 */
	public static final String DAO = "DAO";

	/**
	 * Constant to use to tag a bean with a qualifier of 'REST'.
	 */
	public static final String REST = "REST";

	/**
	 * Constant to use to tag a bean with a qualifier of 'SOAP'.
	 */
	public static final String SOAP = "SOAP";

	/**
	 * Constant to use to tag a bean with a qualifier of 'TRANSACTIONAL'.
	 */
	public static final String TRANSACTIONAL = "TRANSACTIONAL";

	/**
	 * Constant to use to tag a bean with a qualifier of 'NONTRANSACTIONAL'.
	 */
	public static final String NONTRANSACTIONAL = "NONTRANSACTIONAL";
}
