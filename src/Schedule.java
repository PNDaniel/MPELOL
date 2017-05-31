import java.util.ArrayList;
import java.util.Collections;

public class Schedule {

	private ArrayList<Slot> slots = new ArrayList<Slot>();
	
	public Schedule() {

	}
	
	public Schedule(ArrayList<Slot> slots){
		this.slots = (ArrayList<Slot>) slots.clone();
	}

    public Schedule (Schedule s){
        for (int i = 0; i < s.getSlots().size(); i++){
            this.slots.add(new Slot(s.getSlots().get(i)));
        }
    }

	public void AssignRandomMatchups(ArrayList<Matchup> matchups) {
		Collections.shuffle(matchups);

		int index = 0;
		for (Matchup m : matchups) {
			this.slots.get(index).AssignMatchup(m);
			index++;
		}
	}

	public double getScheduleValue(){
		double scheduleValue = 0.0;
		for (int i = 0; i< slots.size();i++){
			scheduleValue += slots.get(i).getValue();
		}
		return 1/scheduleValue;
	}

	public ArrayList<Slot> getSlots(){
		return slots;
	}

    public void setSlots(ArrayList<Slot> slots)
	{
        //this.slots = (ArrayList<Slot>) slots.clone();
		for (int i = 0; i < slots.size(); i++){
			this.slots.add(new Slot(slots.get(i)));
		}
    }
    public void addSlot(Slot slot){

        slots.add(new Slot (slot));
    }
}
