import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Centrality {

	/**
	 * Returns a mapping from each person in the graph to a double representing
	 * his or her betweenness centrality.
	 *
	 * @param graph
	 *            if graph is null, then throw an IllegalArgumentException
	 */
	public static Map<PersonI, Double> betweennessCentrality(Set<PersonI> graph) {
		if (graph == null) {
			throw new IllegalArgumentException();
		}
		Map<PersonI, Double> answer = new HashMap<PersonI, Double>();
		for(PersonI temp : graph) {
			answer.put(temp, 0.0);
		}
		
		for (PersonI current : graph) {
	
			//initialize data structures needed
			Queue<PersonI> queue = new LinkedList<PersonI>();
			queue.offer(current);
			Stack<PersonI> stack = new Stack<PersonI>();
			Map<PersonI, List<PersonI>> list = 
					new HashMap<PersonI, List<PersonI>>();
			Map<PersonI, Double> number = new HashMap<PersonI, Double>();
			Map<PersonI, Double> distance = new HashMap<PersonI, Double>();
			Map<PersonI, Double> phi = new HashMap<PersonI, Double>();
			for(PersonI tempCurrent : graph) {
				list.put(tempCurrent, new ArrayList <PersonI>());
				phi.put(tempCurrent, 0.0);
				if (tempCurrent == current) {
					number.put(tempCurrent, 1.0);
					distance.put(tempCurrent, 0.0);
				}
				else {
					number.put(tempCurrent, 0.0);
					distance.put(tempCurrent, -1.0);
				}
			}
			while (!queue.isEmpty()) {
				PersonI check = queue.remove();
				stack.push(check);
				for(PersonI w : check.getFriends()) {
					if (distance.get(w) < 0.0) {
						queue.offer(w);
						double newInt = 1 + distance.get(check);
						distance.remove(w);
						distance.put(w, newInt);
					}
					if (distance.get(w) == distance.get(check) + 1) {
						double newInt = number.get(check) + number.remove(w);
						number.put(w, newInt);
						List<PersonI> tempList = list.remove(w);
						tempList.add(check);
						list.put(w, tempList);
					}
				}
			}
			
			while(!stack.isEmpty()) {
				PersonI w = stack.pop();
				List<PersonI> tempList = list.get(w);
				for (PersonI v : tempList) {
					double tempInt = phi.remove(v) + 
							(number.get(v)/number.get(w)) * (1 + phi.get(w));
					phi.put(v, tempInt);
				}
				if (w != current) {
					double tempDouble = answer.remove(w) + phi.get(w);
					answer.put(w, tempDouble);
				}
			}
		}
		return answer;
	}

	/**
	 * Returns a mapping from each person in the graph to his or her PageRank
	 * after the specified number of iterations.
	 *
	 * @param graph
	 *            if graph is null, then throw an IllegalArgumentException
	 */
	public static Map<PersonI, Double> pageRank(Set<PersonI> graph,
			int iterations) {
		
		if (graph == null) {
			throw new IllegalArgumentException();
		}
		Map<PersonI, Double> prev = new HashMap<PersonI, Double>();
		Map<PersonI, Double> answer = new HashMap<PersonI, Double>();
		
		//set starting conditions of previous, with initial 1 value PR
		for(PersonI toAdd: graph) {
			prev.put(toAdd, 1.0);
		}
		
		
		for (int i = 0; i < iterations; i++) {

			for (PersonI current : graph) {
				double itSum = 0;

				for(PersonI toCurrent: current.getFollowers()) {
					double tempPR = prev.get(toCurrent);
					itSum = itSum + tempPR/toCurrent.getFriends().size();
				}
				double pr = .15 + (.85*(itSum));
				answer.put(current, pr);
			}
			prev = answer;
		}
		return answer;
	}
}
