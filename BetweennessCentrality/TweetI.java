import java.util.Set;

/**
 * PersonI is an interface representing a node in a graph of a social network.
 * Your implementation should include a constructor that takes in an int and
 * sets the id of the PersonI to that int.
 *
 */
public interface TweetI {

	/**
	 * Returns a set of PersonIs to which this PersonI is connected.
	 */
	public Integer getTweetId();
	
	public String getTweetText();
	
	public void addHash(String string);
	
	public Set<String> getHash();
	
	@Override
	public boolean equals(Object o);

	@Override
	public int hashCode();

}
