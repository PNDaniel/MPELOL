import java.util.ArrayList;
import java.util.Collections;

public class Schedule {

	private ArrayList<Slot> slots = new ArrayList<Slot>();
	
	public Schedule() {

	}
	
	public Schedule(ArrayList<Slot> slots){
		this.slots = (ArrayList<Slot>) slots.clone();
	}

	public void AssignRandomMatchups(ArrayList<Matchup> matchups) {
		Collections.shuffle(matchups);

		int index = 0;
		for (Matchup m : matchups) {
			this.slots.get(index).AssignMatchup(m);
			index++;
		}
	}
}
