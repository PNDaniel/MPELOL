import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	
	//private static ArrayList<Slot> slots = new ArrayList<Slot>();
	//private static ArrayList<Schedule> population = new ArrayList<Schedule>();
    private static ArrayList<Schedule> populationA_DR1 = new ArrayList<Schedule>();
    private static ArrayList<Schedule> populationA_DR2 = new ArrayList<Schedule>();
    private static ArrayList<Schedule> populationB_DR1 = new ArrayList<Schedule>();
    private static ArrayList<Schedule> populationB_DR2 = new ArrayList<Schedule>();
    private static ArrayList<Schedule> population_SR = new ArrayList<Schedule>();

	//private static ArrayList<Matchup> matchups = new ArrayList<Matchup>();
    //private static ArrayList<Matchup> matchups2 = new ArrayList<Matchup>();
	private static ArrayList<Team> teamsA = new ArrayList<Team>();
    private static ArrayList<Team> teamsB = new ArrayList<Team>();

    public static ArrayList<Matchup> matchupsA_DR1 = new ArrayList<>();
    public static ArrayList<Matchup> matchupsA_DR2 = new ArrayList<>();
    public static ArrayList<Matchup> matchupsB_DR1 = new ArrayList<>();
    public static ArrayList<Matchup> matchupsB_DR2 = new ArrayList<>();
    public static ArrayList<Matchup> matchups_SR = new ArrayList<>();

    private static ArrayList<Slot> slotsA_DR1 = new ArrayList<Slot>();
    private static ArrayList<Slot> slotsA_DR2 = new ArrayList<Slot>();
    private static ArrayList<Slot> slotsB_DR1 = new ArrayList<Slot>();
    private static ArrayList<Slot> slotsB_DR2 = new ArrayList<Slot>();
    private static ArrayList<Slot> slots_SR = new ArrayList<Slot>();
	
	public static void main (String[] args){

        StartGroups(); // Starts every Round-Robin for both Groups
        long totalExecutionTimeA_DR1 = runPEAST(populationA_DR1);
        long totalExecutionTimeA_DR2 = runPEAST(populationB_DR1);
        long totalExecutionTimeB_DR1 = runPEAST(populationA_DR2);
        long totalExecutionTimeB_DR2 = runPEAST(populationB_DR2);
        //long totalExecutionTimeSR = 0;
        System.out.println("Time of Execution of PEAST Algorithm for Group A First  Round-Robin " + totalExecutionTimeA_DR1 + "ns");
        System.out.println("Time of Execution of PEAST Algorithm for Group B First  Round-Robin " + totalExecutionTimeB_DR1 + "ns");
        System.out.println("Time of Execution of PEAST Algorithm for Group A Second Round-Robin " + totalExecutionTimeA_DR2 + "ns");
        System.out.println("Time of Execution of PEAST Algorithm for Group B Second Round-Robin " + totalExecutionTimeB_DR2 + "ns");
	}

    public static long runPEAST(ArrayList<Schedule> population){
        long totalExecutionTime = 0;
        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\tInitial Schedule for Group A First Round");
        Schedule initial_Split = population.get(0);
        initial_Split.PrintSchedule();
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");

        long startTime = System.nanoTime();
        PEAST peastAlg = new PEAST();
        long endTime = System.nanoTime();
        Schedule resultSchedule = peastAlg.run(20, 5, 5,population); // (iteration_limit, cloning_interval, shuffling_interval, population)

        System.out.println("=========================================================================================");
        System.out.println("\t\t\t\t\t\tGHCM Final Schedule");
        resultSchedule.PrintSchedule();
        System.out.println("=========================================================================================");
        System.out.println("=========================================================================================");
        totalExecutionTime += (endTime - startTime);
        return totalExecutionTime;
    }

    public static void StartGroups(){
        // Prepare Teams
        FillUpTeamsArrayA();
        FillUpTeamsArrayB();

        // Prepare Matchups
        FillUpMatchups_DR_Main(teamsA, matchupsA_DR1);
        Collections.reverse(teamsA);
        FillUpMatchups_DR_Main(teamsA, matchupsA_DR2);
        FillUpMatchups_DR_Main(teamsB, matchupsB_DR1);
        Collections.reverse(teamsB);
        FillUpMatchups_DR_Main(teamsB, matchupsB_DR2);
//        FillUpMatchups_DR_Main(teamsA, matchups_SR);

        // Prepare Slots
        FillUpSlotsA_DR1();
        FillUpSlotsA_DR2();
        FillUpSlotsB_DR1();
        FillUpSlotsB_DR2();
//        FillUpSlots_SR();

        // Fill Population with Slots
        FillUpPopulation(populationA_DR1, slotsA_DR1);
        FillUpPopulation(populationA_DR2, slotsA_DR2);
        FillUpPopulation(populationB_DR1, slotsB_DR1);
        FillUpPopulation(populationB_DR2, slotsB_DR2);
//        FillUpPopulation(population_SR, slots_SR);

        // Fill Population slots with Matchups
        AssignRandomMatchupsToSlots(populationA_DR1, matchupsA_DR1);
        AssignRandomMatchupsToSlots(populationA_DR2, matchupsA_DR2);
        AssignRandomMatchupsToSlots(populationB_DR1, matchupsB_DR1);
        AssignRandomMatchupsToSlots(populationB_DR2, matchupsB_DR2);
//        AssignRandomMatchupsToSlots(population_SR, matchups_SR);

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

    public static void FillUpMatchups_DR_Main(ArrayList<Team> teams, ArrayList<Matchup> matchups){
        int size = 4;
        int sizeOfJ = 5;
        for(int i = 0; i < size; i++){
            for (int j = 1; j < sizeOfJ; j++){
                matchups.add(new Matchup(teams.get(i), teams.get(j+i)));
            }
            sizeOfJ--;
        }
    }

	public static void FillUpSlotsA_DR1(){
		//Slot(primetime, day, week)
        slotsA_DR1.add(new Slot(true, 1, 1));
        slotsA_DR1.add(new Slot(false, 2, 1));
        slotsA_DR1.add(new Slot(false, 3, 1));
        slotsA_DR1.add(new Slot(true, 1, 2));
        slotsA_DR1.add(new Slot(true, 2, 2));
        slotsA_DR1.add(new Slot(true, 3, 2));
        slotsA_DR1.add(new Slot(true, 4, 2));
        slotsA_DR1.add(new Slot(true, 1, 3));
        slotsA_DR1.add(new Slot(true, 2, 3));
        slotsA_DR1.add(new Slot(true, 3, 3));
	}

    public static void FillUpSlotsA_DR2(){
        //Slot(primetime, day, week)
        slotsA_DR2.add(new Slot(true, 1, 8));
        slotsA_DR2.add(new Slot(true, 2, 8));
        slotsA_DR2.add(new Slot(false, 3, 8));
        slotsA_DR2.add(new Slot(true, 1, 9));
        slotsA_DR2.add(new Slot(false, 2, 9));
        slotsA_DR2.add(new Slot(true, 3, 9));
        slotsA_DR2.add(new Slot(true, 1, 10));
        slotsA_DR2.add(new Slot(true, 2, 10));
        slotsA_DR2.add(new Slot(true, 3, 10));
        slotsA_DR2.add(new Slot(true, 4, 10));
    }

    public static void FillUpSlotsB_DR1(){
        //Slot(primetime, day, week)
        slotsB_DR1.add(new Slot(false, 1, 1));
        slotsB_DR1.add(new Slot(true, 2, 1));
        slotsB_DR1.add(new Slot(true, 3, 1));
        slotsB_DR1.add(new Slot(false, 1, 2));
        slotsB_DR1.add(new Slot(false, 2, 2));
        slotsB_DR1.add(new Slot(false, 3, 2));
        slotsB_DR1.add(new Slot(false, 4, 2));
        slotsB_DR1.add(new Slot(false, 1, 3));
        slotsB_DR1.add(new Slot(false, 2, 3));
        slotsB_DR1.add(new Slot(false, 3, 3));
    }

    public static void FillUpSlotsB_DR2(){
        //Slot(primetime, day, week)
        slotsB_DR2.add(new Slot(false, 1, 8));
        slotsB_DR2.add(new Slot(false, 2, 8));
        slotsB_DR2.add(new Slot(true, 3, 8));
        slotsB_DR2.add(new Slot(false, 1, 9));
        slotsB_DR2.add(new Slot(true, 2, 9));
        slotsB_DR2.add(new Slot(false, 3, 9));
        slotsB_DR2.add(new Slot(false, 1, 10));
        slotsB_DR2.add(new Slot(false, 2, 10));
        slotsB_DR2.add(new Slot(false, 3, 10));
        slotsB_DR2.add(new Slot(false, 4, 10));
    }

	public static void FillUpPopulation(ArrayList<Schedule> _population, ArrayList<Slot> slots){
        /*int daysPerWeek = 4;
        int weeks = 3;
        int slotsPerDay = 2;
        int sizeOfPopulation = daysPerWeek * weeks * slotsPerDay;*/
        int sizeOfPopulation = 5;
		for(int i = 0; i < sizeOfPopulation ; i++)
            _population.add(new Schedule(slots));
	}

	public static void AssignRandomMatchupsToSlots(ArrayList<Schedule> _population, ArrayList<Matchup> matchups){
        /*int daysPerWeek = 4;
        int weeks = 3;
        int slotsPerDay = 2;
        int sizeOfPopulation = daysPerWeek * weeks * slotsPerDay;*/
        int sizeOfPopulation = 5;
		for(int i = 0; i < sizeOfPopulation ; i++)
            _population.get(i).AssignRandomMatchups(matchups);
	}
}
