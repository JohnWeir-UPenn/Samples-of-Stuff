import java.util.HashSet;
import java.util.Set;


/**
 * PersonI is an interface representing a node in a graph of a social network.
 * Your implementation should include a constructor that takes in an int and
 * sets the id of the PersonI to that int.
 *
 */
public class Tweet implements TweetI {
	
    private int id;
    private String text;
    private Set<String> hashtag;
	
	
	public Tweet(int input, String text) {
		if (input < 0) 
			{throw new IllegalArgumentException();}
		this.text = text;
		this.id = input;
		hashtag = new HashSet<String>();
	}
	
	public Set<String> getHash() {
		return hashtag;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public Integer getTweetId() {
		return id;
	}
	
	public void addHash(String string) {
		hashtag.add(string);
	}


	@Override
	public String getTweetText() {
		return text;
	}

}
