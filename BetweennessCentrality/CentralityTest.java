/**John Weir
 * DequeResizing.java
 * Recitation 207
 */

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CentralityTest {

	@Test
	public void testillegalbetweeness() {
		boolean hasException = false;
		try {
			Centrality.betweennessCentrality(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testlegalbetweeness() {
		GraphMaker graph = new GraphMaker("users.json");
		Set<PersonI> output = new HashSet<PersonI>();
		try {
			output.addAll(graph.parse());
		}
		catch(IOException x) {
			System.out.println("IOError");
			assertTrue(false);
		}
		finally {
			Map<PersonI, Double> map = Centrality.betweennessCentrality(output);
			for(PersonI person: output) {
				System.out.print("User ID " + person.getId() + " ");
				System.out.println(map.get(person));
			}
		}
	}
	
	@Test
	public void testillegalpagerank() {
		boolean hasException = false;
		try {
			Centrality.pageRank(null, 1);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	// this graph and the output values are based on the information given on by  
	// the website http://www.sirgroane.net/google-page-rank/
	public void testSimpleGraphPageRank () {
		PersonI a = new Person(1);
		PersonI b = new Person(2);
		PersonI c = new Person(3);
		PersonI d = new Person(4);
		
		Set<PersonI> aFriend = new HashSet<PersonI>();
		Set<PersonI> bFriend = new HashSet<PersonI>();
		Set<PersonI> cFriend = new HashSet<PersonI>();
		Set<PersonI> dFriend = new HashSet<PersonI>();
		
		Set<PersonI> aFollower = new HashSet<PersonI>();
		Set<PersonI> bFollower = new HashSet<PersonI>();
		Set<PersonI> cFollower = new HashSet<PersonI>();
		Set<PersonI> dFollower = new HashSet<PersonI>();
		
		aFriend.add(c);
		aFriend.add(b);
		aFollower.add(c);
		
		bFriend.add(c);
		bFollower.add(a);
		
		cFriend.add(a);
		cFollower.add(a);
		cFollower.add(b);
		cFollower.add(d);
		
		dFriend.add(c);
		
		a.setFriends(aFriend);
		a.setFollowers(aFollower);
		
		b.setFriends(bFriend);
		b.setFollowers(bFollower);
		
		c.setFriends(cFriend);
		c.setFollowers(cFollower);
		
		d.setFriends(dFriend);
		d.setFollowers(dFollower);
		
		Set<PersonI> graph = new HashSet<PersonI>();
		graph.add(a);
		graph.add(b);
		graph.add(c);
		graph.add(d);
		
		Map<PersonI, Double> answer = Centrality.pageRank(graph, 2);
		
		for (PersonI current : graph) {
			System.out.print(current.getId() + ": ");
			System.out.println(answer.get(current));
		}
	}
	
}