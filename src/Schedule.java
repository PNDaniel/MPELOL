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
		
		double prevsched = scheduleValue;
		//Testar Constraints
		double penalty = 0;		
		penalty = Constraints.AllConstraints(this);
		
		scheduleValue = scheduleValue - penalty*scheduleValue;
		
		
		
		System.out.println("Penalty: "+ penalty);
		System.out.println("Valor sem penalty: " + prevsched);
		System.out.println("Valor com penalty: " + scheduleValue);
		
		return 1/scheduleValue;
	}
	
	public ArrayList<Slot> getSlots(){
		return slots;
	}
	
	public ArrayList<Team> getInvolvedTeams(){
		ArrayList<Team> involvedteams = new ArrayList<Team>();
		System.out.println("size: " + this.slots.size());
		for(int i = 0 ;  i < this.slots.size(); i++){
			if(this.slots.get(i).getMatch_assigned() == null)
				continue;
			if(i == 0){
				involvedteams.add(this.slots.get(i).getMatch_assigned().getTeam1());
				involvedteams.add(this.slots.get(i).getMatch_assigned().getTeam2());
				continue;
			}
			if(!involvedteams.contains(this.slots.get(i).getMatch_assigned().getTeam1()))
				involvedteams.add(this.slots.get(i).getMatch_assigned().getTeam1());
			if(!involvedteams.contains(this.slots.get(i).getMatch_assigned().getTeam2()))
				involvedteams.add(this.slots.get(i).getMatch_assigned().getTeam2());
		}
		return involvedteams;
	}
	

    public void setSlots(ArrayList<Slot> slots)
	{
			this.slots = (ArrayList<Slot>) slots.clone();
    }
    public void PrintSchedule() {
        int currentWeek = 1;
        if ((slots.get(0).getWeek() >= 8) && (slots.get(0).getWeek() <= 10))
            currentWeek = 8;
        else if ((slots.get(0).getWeek() >= 4) && (slots.get(0).getWeek() <= 7) )
            currentWeek = 4;
        else if ((slots.get(0).getWeek() >= 1) && (slots.get(0).getWeek() <= 3) )
            currentWeek = 1;

        System.out.println("\t\t\t\t\t  Week "+ currentWeek +" Game Slots");
        for(Slot slot : slots) {
            if(currentWeek != slot.getWeek()) {
               System.out.println("\t\t\t\t\t  Week "+ slot.getWeek() +" Game Slots");
              System.out.println("------------------------------------------------------------------------------------");
                currentWeek++;
          }
            System.out.println(slot.PrintSlot());
        }
	}
}
