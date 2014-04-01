import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.gson.stream.JsonReader;

/**
 * A class that parses a data file in JSON form into a graph representation.
 *
 * @author wangrace
 *
 */
public class GraphMaker {

	JsonReader reader;
	
	public GraphMaker(String filename) {
		if (filename == null) 
			{throw new IllegalArgumentException();}
		
		try {
			reader = new JsonReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns a set of all PersonIs defined in the input file.
	 * @throws IOException 
	 */
	public Set<PersonI> parse() throws IOException {
		try {
			//instantiate maps to create people
			Map<Integer, Set<Integer>> toPerson =
						new HashMap<Integer, Set<Integer>>();
			Map<Integer, String> toName = new HashMap <Integer, String>();
			Map<Integer, PersonI> preAnswer = new HashMap<Integer, PersonI>();
			Set<PersonI> answer = new HashSet<PersonI>();
			
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				reader.skipValue(); //skip "friends"
				Set<Integer> friendsSet = new HashSet<Integer>();
				
				reader.beginArray();

				while (reader.hasNext()){
					int temp = reader.nextInt();
					friendsSet.add(temp);
				}
				
				reader.endArray();
				reader.skipValue(); //skip "id"
				int current = reader.nextInt();
				toPerson.put(current, friendsSet);
				reader.skipValue(); //skip "name"
				toName.put(current, reader.nextString());
				reader.endObject();
			}
			Set<Integer> currentPerson = toPerson.keySet();
			for(Integer i : currentPerson) {
				String name = toName.remove(i);
				Person current = new Person(i);
				current.setName(name);
				answer.add(current);
				preAnswer.put(current.getId(), current);
			}
			for(PersonI p : answer) {
				Set<Integer> toAdd = toPerson.get(p.getId());
				for(Integer i : toAdd) {
					p.addFriend(preAnswer.get(i));
					preAnswer.get(i).addFollower(p);
				}
			}
			return answer;
		}
		catch(IOException x) {
			System.out.println("Invalid Input Stream");
			throw new IOException();
		}
	}
}
