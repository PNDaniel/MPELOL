
public class Slot {

	private Matchup match_assigned = null;
	
	private boolean primetime;
	private int day;
	private int week;
	private double value=0;
	
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
		CalculateValue();
	}
	
	public void UnAssignMatchup(){
		match_assigned = null;
		value = 0;
	}
	
	public void CalculateValue(){
		
		// not taking into account the teams playing
		double time_val;
		
		if(primetime)
			time_val = 0.5*(day+week);
		else
			time_val = (day+week);
		
		value = time_val*match_assigned.GetValue();
		
	}
	
}
