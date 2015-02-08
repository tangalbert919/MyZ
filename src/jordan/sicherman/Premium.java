/**
 * 
 */
package jordan.sicherman;

/**
 * @author Jordan
 * 
 */
public class Premium {

	protected static final boolean isPremium = intern(false);

	protected static final boolean isPatched = intern(false);

	private static boolean intern(boolean b) {
		return b;
	}
}
