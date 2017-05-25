
public class Team {

	private String name;
	private int popularity;

	private int gamesAssigned;
	
	public Team(){
		
	}
	
	public Team(String name, int pop){
		this.name = name;
		this.popularity = pop;
	}
	
	public int getPopularity(){
		return this.popularity;
	}
	
	public String getName(){
		return this.name;
	}

	public void addGameAssigned(){
		this.gamesAssigned++;
	}

	public void removeGameAssigned(){
		this.gamesAssigned--;
	}

	public int getGamesAssigned() {
		return gamesAssigned;
	}

	public void setGamesAssigned(int gamesAssigned) {
		this.gamesAssigned = gamesAssigned;
	}
}
