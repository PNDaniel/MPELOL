
public class Team {

	private String name;
	private int popularity;
	
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
}
