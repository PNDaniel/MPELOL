
public class Slot {

	private Matchup match_assigned = null;
	
	private boolean primetime;
	private int day;
	private int week;
	
	public Slot(){
		
	}
	
	
	// Set primetime to true if second slot of the day;
	// day [1,2,3,4] = [TH,FR,SA,SU]
	public Slot(boolean primetime, int day, int week){
		this.day = day;
		this.week = week;
		this.primetime = primetime;
	}
	public void AssignMatchup(Matchup m){
		match_assigned = m;
	}
	
	public void UnAssignMatchup(){
		match_assigned = null;
	}
	
}
