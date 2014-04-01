/**John Weir
 * DequeResizing.java
 * Recitation 207
 */

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testConstructor() {
		boolean hasException = false;
		try {
			Person me = new Person(-1);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testsetIderror() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.setId(-1);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testsetNameerror() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.setName(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testcantbefriendswithyouself() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		assertEquals(me.getFollowers(), everyone);
	}
	
	@Test
	public void testgetNamegetIDandsetNamesetID() {
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		me.setName("Jack");
		friend1.setName("Karl");
		friend2.setName("Estaban");
		assertEquals(me.getName(), "Jack");
		assertEquals(me.getId(), 1);
		assertEquals(friend1.getName(), "Karl");
		assertEquals(friend1.getId(), 2);
		assertEquals(friend2.getName(), "Estaban");
		assertEquals(friend2.getId(), 3);
		friend2.setId(100);
		friend2.setName("Est");
		assertEquals(friend2.getId(), 100);
		assertEquals(friend2.getName(), "Est");
	}
	
	@Test
	public void testgetFollowersandAdd() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend1);
		everyone.add(friend2);
		me.addFollower(friend1);
		me.addFollower(friend2);
		assertEquals(me.getFollowers(), everyone);
		everyone.clear();
		everyone.add(me);
		assertEquals(friend1.getFriends(), everyone);
	}
	
	@Test
	public void testgetFriendsandAdd() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend1);
		everyone.add(friend2);
		me.addFriend(friend1);
		me.addFriend(friend2);
		assertEquals(me.getFriends(), everyone);
		everyone.clear();
		everyone.add(me);
		assertEquals(friend1.getFollowers(), everyone);
	}
	
	@Test
	public void testBadAddFriend() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.addFriend(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testBadAddFollower() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.addFollower(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testAddYourselfasFriend() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend1);
		everyone.add(friend2);
		me.addFriend(friend1);
		me.addFriend(friend2);
		me.addFriend(me);
		assertEquals(me.getFriends(), everyone);
	}
	
	@Test
	public void testAddYourselfasFollower() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend1);
		everyone.add(friend2);
		me.addFollower(friend1);
		me.addFollower(friend2);
		me.addFollower(me);
		assertEquals(me.getFollowers(), everyone);
	}
	
	@Test
	public void testremoveFriends() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend2);
		me.addFriend(friend1);
		me.addFriend(friend2);
		me.removeFriend(friend1);
		assertEquals(me.getFriends(), everyone);
		everyone.remove(friend2);
		me.removeFriend(friend2);
		assertEquals(me.getFriends(), everyone);
	}
	
	@Test
	public void testremoveFollowers() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend2);
		me.addFollower(friend1);
		me.addFollower(friend2);
		me.removeFollower(friend1);
		assertEquals(me.getFollowers(), everyone);
		everyone.remove(friend2);
		me.removeFollower(friend2);
		assertEquals(me.getFollowers(), everyone);
	}
	
	@Test
	public void testBadRemoveFollower() {
		Set<PersonI> empty = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend = new Person(2);
		me.addFriend(friend);
		me.removeFollower(friend);
		assertTrue(me.getFollowers().toString()== empty.toString());
	}
	
	@Test
	public void testBadRemoveFriend() {
		Set<PersonI> empty = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend = new Person(2);
		me.addFollower(friend);
		me.removeFriend(friend);
		assertTrue(me.getFriends().toString() == empty.toString());

	}
	
	@Test
	public void testBadRemoveFollowerNull() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.removeFollower(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testBadRemoveFriendNull() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.removeFriend(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testSetFollowersNull() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.setFollowers(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testSetFriendsNull() {
		boolean hasException = false;
		Person me = new Person(1);
		try {
			me.setFriends(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testSetFriendsNormal() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend1);
		everyone.add(friend2);
		me.setFriends(everyone);
		assertEquals(me.getFriends(), everyone);
		everyone.clear();
		everyone.add(me);
		assertEquals(friend1.getFollowers(), everyone);
		assertEquals(friend2.getFollowers(), everyone);
	}

	@Test
	public void testSetFollowersNormal() {
		Set<PersonI> everyone = new HashSet<PersonI>();
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		everyone.add(friend1);
		everyone.add(friend2);
		me.setFollowers(everyone);
		assertEquals(me.getFollowers(), everyone);
		everyone.clear();
		everyone.add(me);
		assertEquals(friend1.getFriends(), everyone);
		assertEquals(friend2.getFriends(), everyone);
	}
	
	@Test
	public void testEqualsandHashSet() {
		Person me = new Person(1);
		Person you = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		me.addFollower(friend1);
		me.addFollower(friend2); //additions to friend/follower list dont matter
		assertTrue(me.equals(me));
		assertTrue(me.equals(you));
		assertEquals(me.hashCode(), me.hashCode());
		assertEquals(me.hashCode(), you.hashCode());
	}
	
	@Test
	public void testNotEqualsandHashset() {
		Person me = new Person(1);
		Person friend1 = new Person(2);
		Person friend2 = new Person(3);
		me.addFollower(friend1);
		me.addFollower(friend2); //additions to friend/follower list dont matter
		assertFalse(me.equals(friend1));
		assertFalse(me.hashCode() == friend1.hashCode());
	}
	
}
