/**John Weir
 * DequeResizing.java
 * Recitation 207
 */

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class GraphMakerTest {

	@Test
	public void testConstructor() {
		boolean hasException = false;
		try {
			GraphMaker graph = new GraphMaker(null);
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}
	
	@Test
	public void testConstructorMissingFile() {
		boolean hasException = false;
		try {
			GraphMaker graph = new GraphMaker("poop.json");
		}
		catch(IllegalArgumentException x) {
			hasException = true;
		}
		finally {
			assertTrue(hasException);
		}
	}

	@Test
	public void results() {
		GraphMaker graph = new GraphMaker("small.json");
		Set<PersonI> output = new HashSet<PersonI>();
		try {
			output.addAll(graph.parse());
		} catch (IOException e) {
			System.out.println("Exception");
		}
		finally {
			for (PersonI current: output) {
				System.out.println("Name is : " + current.getName());
				System.out.println("ID is : " +current.getId());
				System.out.println("HashSet of friends is : "
						+ current.getFriends().toString());
				System.out.println("HashSet of followers is : " + 
						current.getFollowers().toString());
				System.out.println();
			}
		}
	}
}
