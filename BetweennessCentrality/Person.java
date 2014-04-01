import java.util.HashSet;
import java.util.Set;

/**
 * PersonI is an interface representing a node in a graph of a social network.
 * Your implementation should include a constructor that takes in an int and
 * sets the id of the PersonI to that int.
 * 
 */ 

public class Person implements PersonI {
	
    private int id;
    private String name;
    private Set<PersonI> friends;
    private Set<PersonI> followers;
    private Set<TweetI> tweets;
	
	
	public Person(int input) {
		if (input < 0) 
			{throw new IllegalArgumentException();}
		this.id = input;
		this.friends = new HashSet<PersonI>();
	    this.followers = new HashSet<PersonI>();
	    this.tweets = new HashSet<TweetI>();
	}
	
	// adds all tweets
	public void setTweets(Set<TweetI> setTweets) {
		if (setTweets == null) {
			throw new IllegalArgumentException();
		}
		this.tweets = setTweets;
	}
	
	public Set<TweetI> getTweetSet() {
		return this.tweets;
	}
	
	/**
	 * Returns a set of PersonIs to which this PersonI is connected.
	 */
	public Set<PersonI> getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            friends is the set of PersonIs given as friends to this
	 *            PersonI. Previously held friends should no longer belong to
	 *            this PersonI. If friends is null, then throw
	 *            IllegalArgumentException.
	 */
	public void setFriends(Set<PersonI> setfriends) {
		if (setfriends == null) 
			{throw new IllegalArgumentException();}
		for(PersonI current: setfriends) {
			this.addFriend(current);
		}
	}

	/**
	 *
	 * @param friend
	 *            If friend is null, then throw an IllegalArgumentException.
	 */
	public void addFriend(PersonI friend) {
		if (friend == null) 
			{throw new IllegalArgumentException();}
		if (friend == this) 
			{return;} //people cannot be friends with themselves but no error
		if (friends.contains(friend)) {
			return;
		}
		friends.add(friend);
		friend.addFollower(this);
	}

	/**
	 *
	 * Removes a PersonI from this PersonI's friends
	 *
	 * @param friend
	 *            If friend is null or is not a friend of this PersonI, then
	 *            throw an IllegalArgumentException.
	 */
	public void removeFriend(PersonI friend) {
		if (friend == null) {
			throw new IllegalArgumentException();
		}
		if (friends.contains(friend)){
			friends.remove(friend);
		}
		if (friend.getFollowers().contains(this)) {
			friend.removeFollower(this);
		}
	}

	/**
	 * Returns a set of PersonIs that follow this PersonI.
	 */
	public Set<PersonI> getFollowers(){
		return followers;
	}

	/**
	 * @param followers
	 *            the set of PersonIs given as inbound connections to this
	 *            PersonI. Previously held followers should no longer belong to
	 *            this PersonI. If followers is null, then throw an
	 *            IllegalArgumentException.
	 */
	public void setFollowers(Set<PersonI> setFollower) {
		if (setFollower == null) 
		{throw new IllegalArgumentException();}
		for (PersonI current : setFollower) {
			this.addFollower(current);
		}
	}

	/**
	 * @param follower
	 *            If follower is null, then throw an IllegalArgumentException.
	 */
	public void addFollower(PersonI follower){
		if (follower == null)
			{throw new IllegalArgumentException();}
		if (follower == this) 
			{return;} // people cant follow themselves, no error though
		if (followers.contains(follower)) {
			return;
		}
		followers.add(follower);
		follower.addFriend(this);
	}

	/**
	 * Removes a PersonI from this PersonI's friends
	 *
	 * @param friend
	 *            If friend is null or does not follow this PersonI, then throw
	 *            an IllegalArgumentException.
	 */
	public void removeFollower(PersonI follower) {
		if (follower == null) 
			{throw new IllegalArgumentException();}
		if (followers.contains(follower))
			{followers.remove(follower);}
		if(follower.getFriends().contains(this)) {
			follower.removeFriend(this);
		}
	}

	/**
	 * Returns the name of this PersonI.
	 */
	public String getName(){
		return name;
	}

	/**
	 * @param name
	 *            the name that will be given to this PersonI
	 */
	public void setName(String newName){
		if (newName == null) 
			{throw new IllegalArgumentException();}
		this.name = newName;
	}

	/**
	 * Returns the id of this PersonI. Each PersonI must have a unique id.
	 */
	public int getId(){
		return id;
	}

	/**
	 * @param id
	 *            the id that will be given to this PersonI
	 */
	public void setId(int newId){
		if (newId < 0) 
			{throw new IllegalArgumentException();}
		this.id = newId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
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
}
