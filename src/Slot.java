
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
	
	public Slot(Slot s){
		this.day = s.getDay();
		this.week = s.getWeek();
		this.primetime = s.isPrimetime();
	}

	public void CalculateValue(){
		
		// not taking into account the teams playing
		double time_val;
		
		if(primetime)
			time_val = 0.5*(day+week);
		else
			time_val = (day+week);

        if(match_assigned != null)
        {
            value = time_val*match_assigned.GetValue();
        }
        else{
            value = 0.0;
        }
	}

    public String PrintSlot()
    {
        String printDay = null;
        String timeDay = "Afternoon";
        switch (day) {
            case 1 : printDay = "Thursday";
                break;
            case 2 : printDay = "Friday  ";
                break;
            case 3 : printDay = "Saturday";
                break;
            case 4 : printDay = "Sunday  ";
                break;
            default: printDay = "UNKNOWN ";
                break;
        }
        if(primetime) {
            timeDay = "Evening  ";
        }
        if (match_assigned != null){
            return "Day " + day + " -  " + printDay + " : "+ timeDay + " -> Game: " + match_assigned.PrintMatchup() + "\t\t Valor do Slot:  " + getValue();
        }
        else {
            return "Day " + day + " -  " + printDay + " : "+ timeDay + " -> No match assigned";
            //return "No match assigned";
        }
    }

/**
 * Getters & Setters for Class variables
 */
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Matchup getMatch_assigned() {
        return match_assigned;
    }
    
    public void AssignMatchup(Matchup m){
		match_assigned = m;
		CalculateValue();
	}
	
	public void UnAssignMatchup(){
      /*  match_assigned.getTeam1().removeGameAssigned();
        match_assigned.getTeam2().removeGameAssigned();*/
		match_assigned = null;
		value = 0;
	}
	
	public Matchup UnAssignMatchupWithReturn(){
		Matchup mat = new Matchup(match_assigned);
		match_assigned = null;
		value = 0;
		return mat;
	}

    /* DEPRECATED
     public void setMatch_assigned(Matchup match_assigned) {
     
        this.match_assigned = match_assigned;
    }*/

    public boolean isPrimetime() {
        return primetime;
    }

    public void setPrimetime(boolean primetime) {
        this.primetime = primetime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
    
}
