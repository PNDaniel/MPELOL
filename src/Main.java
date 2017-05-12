import java.util.ArrayList;

public class Main {
	
	private static ArrayList<Slot> slots = new ArrayList<Slot>();
	private static ArrayList<Matchup> matchups = new ArrayList<Matchup>();
	private static ArrayList<Team> teams = new ArrayList<Team>();
	
	public static void main (String[] args){
		StartGroupA();
	}
	
	
	
	//Consult GroupANotes.txt
	public static void StartGroupA(){
		FillUpTeamsArrayA();
		FillUpMatchupsA();
		FillUpSlotsA();
	}
	
	public static void FillUpTeamsArrayA(){
		teams.add(new Team("G2ESPORTS"));
		teams.add(new Team("MISFITS"));
		teams.add(new Team("ROCCAT"));
		teams.add(new Team("FNATIC"));
		teams.add(new Team("GIANTS"));
	}
	
	public static void FillUpMatchupsA(){
		matchups.add(new Matchup(teams.get(0), teams.get(1)));
		matchups.add(new Matchup(teams.get(0), teams.get(2)));
		matchups.add(new Matchup(teams.get(0), teams.get(3)));
		matchups.add(new Matchup(teams.get(0), teams.get(4)));
		matchups.add(new Matchup(teams.get(1), teams.get(2)));
		matchups.add(new Matchup(teams.get(1), teams.get(3)));
		matchups.add(new Matchup(teams.get(1), teams.get(4)));
		matchups.add(new Matchup(teams.get(2), teams.get(3)));
		matchups.add(new Matchup(teams.get(2), teams.get(4)));
		matchups.add(new Matchup(teams.get(3), teams.get(4)));
	}

	public static void FillUpSlotsA(){
		//Slot(primetime, day, week)
		slots.add(new Slot(true, 1, 1)); 
		slots.add(new Slot(false, 2, 1)); 
		slots.add(new Slot(false, 3, 1));
		slots.add(new Slot(true, 1, 2));
		slots.add(new Slot(true, 2, 2));
		slots.add(new Slot(true, 3, 2));
		slots.add(new Slot(true, 4, 2));
		slots.add(new Slot(true, 1, 3));
		slots.add(new Slot(true, 2, 3));
		slots.add(new Slot(true, 3, 3));
	}
	
	
}
