import java.util.ArrayList;
import java.util.Collections;

public class Main {
	
	private static ArrayList<Slot> slots = new ArrayList<Slot>();
	private static ArrayList<Schedule> population = new ArrayList<Schedule>();
    private static ArrayList<Schedule> populationDR2 = new ArrayList<Schedule>();
	private static ArrayList<Matchup> matchups = new ArrayList<Matchup>();
	private static ArrayList<Team> teamsA = new ArrayList<Team>();
    private static ArrayList<Team> teamsB = new ArrayList<Team>();
	
	public static void main (String[] args){
    /*    FillUpTeamsArrayA();
        FillUpTeamsArrayB();
        FillUpMatchups();*/
        StartGroupA_DR_1();
      //  StartGroupB();
        Schedule initial_Split = population.get(0);
        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\tInitial Schedule");
        PrintResult(initial_Split,1);
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        
        /* testing PEAST */
        long startTime = System.nanoTime();
		PEAST peastAlg = new PEAST();
        long endTime = System.nanoTime();
        double current_temperature = 1/Math.log(Math.pow(0.75,-1));
        Schedule newSchedule = peastAlg.GHCM(initial_Split,20,0,current_temperature);
        /* END of test */

        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\tGHCM Schedule");
        PrintResult(newSchedule,1);
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        System.out.println("Time of Execution of PEAST Algorithm first half of the Double Round-Robin: " + (endTime - startTime) + "ns");

   /*     for (int i= 0; i < teamsA.size(); i++){
            System.out.println("Games of " + teamsA.get(i).getName() + ": " + teamsA.get(i).getGamesAssigned());
        }*/

     /*   System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\t\t\t\tWEEK 8-10");
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        StartGroupA_DR_2();
        Schedule second_half = populationDR2.get(0);
        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\tInitial Schedule");
        PrintResult(second_half,3);
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        startTime = System.nanoTime();
        peastAlg = new PEAST();
        endTime = System.nanoTime();
        current_temperature = 1/Math.log(Math.pow(0.75,-1));
        newSchedule = peastAlg.GHCM(second_half,20,0,current_temperature);

        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\tGHCM Schedule");
        PrintResult(newSchedule,3);
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        System.out.println("Time of Execution of PEAST Algorithm DR2" + (endTime - startTime) + "ns");*/
	}

	public static void StartGroupA_DR_1(){
		FillUpTeamsArrayA();
		FillUpMatchupsA();
		FillUpSlotsA();
        FillUpPopulation(population);
        System.out.println("Size of current ["+ 1 +"] population sample: " + population.size());
        AssignRandomMatchupsToSlots(population);
	}

    public static void StartGroupA_DR_2(){
        FillUpMatchupsA_DR2();
        FillUpSlotsA_DR2();
        FillUpPopulation(populationDR2);
        System.out.println("Size of current ["+ 1 +"] population sample: " + populationDR2.size());
        AssignRandomMatchupsToSlots(populationDR2);
    }

    public static void StartGroupB(){
        FillUpTeamsArrayB();
    /*    FillUpMatchupsB();
        FillUpSlotsB();
        AssignRandomMatchupsToSlots();*/
    }
	
	public static void FillUpTeamsArrayA(){
        teamsA.add(new Team("G2ESPORTS", 50));   // Team 0 - teams.get(0)
        teamsA.add(new Team("MISFITS", 30));     // Team 1 - teams.get(1)
        teamsA.add(new Team("ROCCAT", 20));      // Team 2 - teams.get(2)
        teamsA.add(new Team("FNATIC", 50));      // Team 3 - teams.get(3)
        teamsA.add(new Team("GIANTS", 10));      // Team 4 - teams.get(4)
	}

    public static void FillUpTeamsArrayB(){
        teamsB.add(new Team("UNICORNS OF LOVE", 50));   // Team 0 - teams.get(0)
        teamsB.add(new Team("H2K-GAMING", 40));         // Team 1 - teams.get(1)
        teamsB.add(new Team("SPLYCE", 30));             // Team 2 - teams.get(2)
        teamsB.add(new Team("VITALIY", 30));            // Team 3 - teams.get(3)
        teamsB.add(new Team("ORIGEN", 30));             // Team 4 - teams.get(4)
    }

	public static void FillUpMatchupsA(){
		matchups.add(new Matchup(teamsA.get(0), teamsA.get(1)));
		matchups.add(new Matchup(teamsA.get(0), teamsA.get(2)));
		matchups.add(new Matchup(teamsA.get(0), teamsA.get(3)));
		matchups.add(new Matchup(teamsA.get(0), teamsA.get(4)));
		matchups.add(new Matchup(teamsA.get(1), teamsA.get(2)));
		matchups.add(new Matchup(teamsA.get(1), teamsA.get(3)));
		matchups.add(new Matchup(teamsA.get(1), teamsA.get(4)));
		matchups.add(new Matchup(teamsA.get(2), teamsA.get(3)));
		matchups.add(new Matchup(teamsA.get(2), teamsA.get(4)));
		matchups.add(new Matchup(teamsA.get(3), teamsA.get(4)));
	}

    public static void FillUpMatchupsA_DR2(){
        matchups.add(new Matchup(teamsA.get(1), teamsA.get(0)));
        matchups.add(new Matchup(teamsA.get(2), teamsA.get(0)));
        matchups.add(new Matchup(teamsA.get(3), teamsA.get(0)));
        matchups.add(new Matchup(teamsA.get(4), teamsA.get(0)));
        matchups.add(new Matchup(teamsA.get(2), teamsA.get(1)));
        matchups.add(new Matchup(teamsA.get(3), teamsA.get(1)));
        matchups.add(new Matchup(teamsA.get(4), teamsA.get(1)));
        matchups.add(new Matchup(teamsA.get(3), teamsA.get(2)));
        matchups.add(new Matchup(teamsA.get(4), teamsA.get(2)));
        matchups.add(new Matchup(teamsA.get(4), teamsA.get(3)));
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

    public static void FillUpSlotsA_DR2(){
        //Slot(primetime, day, week)
        slots.add(new Slot(true, 1, 8));
        slots.add(new Slot(true, 2, 8));
        slots.add(new Slot(false, 3, 8));
        slots.add(new Slot(true, 1, 9));
        slots.add(new Slot(false, 2, 9));
        slots.add(new Slot(true, 3, 9));
        slots.add(new Slot(true, 1, 10));
        slots.add(new Slot(true, 2, 10));
        slots.add(new Slot(true, 3, 10));
        slots.add(new Slot(true, 4, 10));
    }

	public static void FillUpPopulation(ArrayList<Schedule> _population){
        int daysPerWeek = 4;
        int weeks = 3;
        int slotsPerDay = 2;
        int sizeOfPopulation = daysPerWeek * weeks * slotsPerDay;
		for(int i = 0; i < sizeOfPopulation ; i++)
            _population.add(new Schedule(slots));
	}

	public static void AssignRandomMatchupsToSlots(ArrayList<Schedule> _population){
        int daysPerWeek = 4;
        int weeks = 3;
        int slotsPerDay = 2;
        int sizeOfPopulation = daysPerWeek * weeks * slotsPerDay;
		for(int i = 0; i < sizeOfPopulation ; i++)
            _population.get(i).AssignRandomMatchups(matchups);
	}

    public static void PrintResult(Schedule split, int round_robin) {
        int currentWeek = 1;

        System.out.println("\t\t\t\t\t  Week "+ 1 +" Game Slots");
        for(Slot slot : split.getSlots()) {
                if(currentWeek != slot.getWeek()) {
                    System.out.println("\t\t\t\t\t  Week "+ slot.getWeek() +" Game Slots");
                    System.out.println("-------------------------------------------------------------------");
                    currentWeek++;
                }
            System.out.println(slot.PrintSlot());
        }
    }
}
