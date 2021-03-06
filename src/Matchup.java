
public class Matchup {

    private Team team1;
	private Team team2;
	private double value;
	
	public Matchup(){
		
	}
	
	public Matchup(Team a, Team b){
		a.addGameAssigned();
		b.addGameAssigned();
		team1=a;
		team2=b;
		CalculateValue();
	}
	
	public Matchup(Matchup m){
        if( m != null){
            this.team1 = new Team (m.getTeam1());
            this.team2 = new Team (m.getTeam2());
            this.value = m.GetValue();
        }
	}
	
	public void CalculateValue(){
		int total_popularity = team1.getPopularity() + team2.getPopularity();
		int popularity_difference = Math.abs(team1.getPopularity() - team2.getPopularity());
		int popularity_similarity = total_popularity - popularity_difference;

		// The number of followers of each team have an increasing collision chance
		if(popularity_similarity > 15)
			value = total_popularity - (((Math.log(popularity_similarity) / Math.log(15)) - 1) * popularity_similarity);
		else
			value = total_popularity;

		// Twitch/Special popularity spike 
		if(total_popularity >= 80){
			value += Math.log(total_popularity-30);
		}
	}
	
	public double GetValue(){
		return this.value;
	}
	
	public Team getTeam1(){
		return team1;
	}
	
	public Team getTeam2(){
		return team2;
	}

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public String PrintMatchup() {
        return team1.getName() + " VS " + team2.getName();
    }
    
   
}
