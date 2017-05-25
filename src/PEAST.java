import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class PEAST {

    public PEAST(){  }

    public Schedule run(int iteration_limit, int cloning_interval, int shuffling_interval, ArrayList<Schedule> population){
        int update_interval = 0; // ADAGEN update interval
        int population_size = population.size();
        Schedule best_solution = new Schedule();
        int round = 1;
        double current_temperature = 1/Math.log(Math.pow(0.75,-1)); // T0, initial temperature
        int maximum_sequence_length = iteration_limit;  // maximum sequence length (m) is equal to the maximum number of iterations (t)
        while(round <= iteration_limit){
            int index = 1;
            while(index++ <= population_size){
               //TODO Apply GHCM to schedule Sindex to get a new schedule
                best_solution = GHCM(population.get(index), 10, round, current_temperature);
                if (population.get(index).getScheduleValue() < best_solution.getScheduleValue()){
                    best_solution = population.get(index);
                }
            }
            current_temperature = getTemperature(current_temperature,maximum_sequence_length);
            //TODO Update simulated annealing framework

            if (round % update_interval == 0) {
                //TODO Update ADAGEN Framework
            }
            if (round % shuffling_interval == 0) {
                //TODO Apply shuffling operators
            }
            if (round % cloning_interval == 0) {
                //TODO Replace the worst schedule with the best one
            }
            round++;
        }

        return best_solution;
    }

    public Schedule GHCM(Schedule split, int maximum_sequence_length, int current_round, double current_temperature){
        ArrayList<Double> everyCost = new ArrayList<Double>();
        int k = current_round/2;
        Matchup object_to_move = randomObject(split); // Set objectToMove = RandomObject(S)
        int target_cell_index = 0;
        Slot target_cell = new Slot();
        int index = 0;
        double last_increment = Double.POSITIVE_INFINITY;
        double costDiff = 0.0;
        Schedule copy = new Schedule(split.getSlots());
        Schedule last_optimal = new Schedule(split.getSlots());
        double optimal_cost = 0.0;

        System.out.println("Initial Schedule Value: " + split.getScheduleValue() +"\n");

        while(index < maximum_sequence_length){
            double originalIterationCost = copy.getScheduleValue(); // currentCost of the iteration is the full cost(value) of a split
            System.out.println("Cost of iteration " + index + " is: " + originalIterationCost);
         //   everyCost.add(originalIterationCost);
            if (index > 0){
                // Inner annealing #1
                if(Math.random() < Math.exp(-1/current_temperature)) {
                    object_to_move = randomObject(copy, target_cell_index); // Set targetCell = RandomCell(S)
                }
                else{
                    object_to_move = leastFitObject(copy,target_cell_index); // Set objectToMove = LeastFitObject(S,targetCell)
                }
                if (object_to_move != null) {
                }
            }

            // Inner annealing #2
            if(Math.random() < current_temperature) {
                target_cell_index = randomCell(copy); // Set targetCell = RandomCell(S)
                target_cell = copy.getSlots().get(target_cell_index);
            }
            else {
                target_cell_index = fittestCell(copy, object_to_move); // Set targetCell = FittestCell(S,objectToMove)
                target_cell = copy.getSlots().get(target_cell_index);
            }
            Matchup tempMatchup = target_cell.getMatch_assigned();
            for (int i = 0; i < copy.getSlots().size();i++)
            {
                if(object_to_move.equals(copy.getSlots().get(i).getMatch_assigned()))
                {
                    copy.getSlots().get(i).AssignMatchup(tempMatchup);
                    break;
                }
            }
            System.out.println("Slot Match BEFORE RECEIVING Move: "+ object_to_move.PrintMatchup());
            System.out.println("Slot Cost BEFORE RECEIVING Move: "+ target_cell.getValue() );
            target_cell.AssignMatchup(object_to_move); // Move objectToMove to targetCell
            System.out.println("Match Moved: "+ object_to_move.PrintMatchup());
            System.out.println("Slot Cost After RECEIVING Move: "+ target_cell.getValue() );
            double current_cost = copy.getScheduleValue();
            if(current_cost < optimal_cost)
            {
                optimal_cost = current_cost;
                last_optimal = new Schedule(copy.getSlots());
            }
            if (current_cost > originalIterationCost)
            {
                costDiff = current_cost - originalIterationCost;
                if (costDiff > last_increment){
                    index = Integer.MAX_VALUE;
                    break;
                }
                else{
                    last_increment = costDiff;
                }
            }
            index++;
            for (int i = 0; i < split.getSlots().size();i++)
            {
                if(i != 0)
                {
                    if (split.getSlots().get(i).getWeek() != split.getSlots().get(i-1).getWeek())
                    {
                        System.out.println("----------  ----------  ----------  ----------  ----------  ----------  ----------  ----------  ----------");
                    }
                }
                System.out.println(split.getSlots().get(i).PrintSlot());
            }
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        Schedule returnValue = new Schedule();
        // Outer annealing
        if( index == Integer.MAX_VALUE) {
            if (Math.random() < Math.exp(-costDiff / current_temperature)) {
                returnValue =  copy;
            }
        }
        else{
            returnValue = last_optimal; // Roll S back to the optimal point in the move sequence.
        }

        System.out.println("\nCost of returnValue: " +  returnValue.getScheduleValue());
        SaveToFile(current_round,everyCost,split, returnValue);
        return returnValue;
    }

    public Matchup leastFitObject(Schedule split, int target_cell_index){
        double least_fit_value = 1/split.getScheduleValue();
        Slot target_cell = split.getSlots().get(target_cell_index);
        Matchup target_cell_matchup = target_cell.getMatch_assigned();
        Matchup least_fit = target_cell.getMatch_assigned();

        for(int i = 0; i < split.getSlots().size() ;i++){
            Schedule copy = new Schedule(split.getSlots());

            if(i == target_cell_index)
                continue;

            Matchup candidate = copy.getSlots().get(i).getMatch_assigned();
            copy.getSlots().get(i).setMatch_assigned(target_cell_matchup);
            copy.getSlots().get(target_cell_index).setMatch_assigned(candidate);

            double schedule_cost = copy.getScheduleValue();

            if(schedule_cost > least_fit_value) {
                least_fit_value = schedule_cost;
                least_fit = candidate;
            }
        }

        return least_fit;
    }

    public int fittestCell(Schedule split, Matchup matchup_to_move){
        double fittest_cell_value = split.getScheduleValue();
        int original_cell_index = 0;
        for (int i = 0; i < split.getSlots().size(); i++){
            if (split.getSlots().get(i).getMatch_assigned().equals(matchup_to_move))
            {
                original_cell_index = i;
                break;
            }
        }

        int fittest_cell_index = original_cell_index;

        for(int i = 0; i < split.getSlots().size() ;i++){
            Schedule copy = new Schedule(split.getSlots());

            if(i == original_cell_index)
                continue;

            Slot candidate = copy.getSlots().get(i);

            copy.getSlots().get(original_cell_index).setMatch_assigned(candidate.getMatch_assigned());
            candidate.setMatch_assigned(matchup_to_move);

            double schedule_cost = copy.getScheduleValue();

            if(schedule_cost < fittest_cell_value) {
                fittest_cell_value = schedule_cost;
                fittest_cell_index = i;
            }
        }

        return fittest_cell_index;
    }

    /**
     * Support function for getting a random Matchup from a Schedule
     * @param schedule
     * @return Matchup currently selected from the split, it will become the matchup
     */
    private Matchup randomObject(Schedule schedule)
    {
        boolean checkForNull = false;
        Matchup matchupToReturn = new Matchup();
        while (!checkForNull){
            int index = new Random().nextInt(schedule.getSlots().size());
            matchupToReturn = schedule.getSlots().get(index).getMatch_assigned();
            if (matchupToReturn != null){
                checkForNull = true;
            }
        }

        return matchupToReturn;
    }

    private Matchup randomObject(Schedule schedule, int target_cell_index)
    {
        //TODO target_cell is used to assert constraints for the object movement.
        return randomObject(schedule);
    }

    private int randomCell(Schedule schedule)
    {
        return new Random().nextInt(schedule.getSlots().size());
    }

    public double getTemperature(double T0, int m){
        double p = 0.0015;
        return Math.pow((-1/(T0 *Math.log(p))), (1/m));
    }

    public void SaveToFile(int index, ArrayList<Double> everyCost, Schedule originalSplit, Schedule returnValue) {
        try{

            BufferedWriter output = null;
            File file = new File("C:\\Users\\Utilizador\\Documents\\Faculdade\\2016-2017\\2Semestre\\MPES\\Projeto\\SavedRuns\\" + "iteration" + index + ".txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("===================================================================");
            output.write("First Schedule");
            output = PrintSplit(originalSplit,output);
            output.write("===================================================================");
            output.write("===================================================================");
            for (int i = 0; i < everyCost.size(); i++) {
                output.write("Cost of " + i + " is: " + everyCost.get(i));
                output.write("Cost of returnValue:" + returnValue.getScheduleValue());
            }
            output.write("===================================================================");
            output.write("GHCM Schedule");
            output = PrintSplit(returnValue,output);
            output.write("===================================================================");
            output.write("===================================================================");
        }catch (IOException e) {
        }
    }

    public BufferedWriter PrintSplit(Schedule split, BufferedWriter output){
        int currentWeek = 1;
        try{
            for(Slot slot : split.getSlots()) {
                if(currentWeek != slot.getWeek()) {
                    output.write("-------------------------------------------------------------------");
                    currentWeek++;
                }
                output.write("\t\tWeek "+ slot.getWeek() +" Game Slots");
                output.write(slot.PrintSlot());
            }
        }
        catch (IOException e){

        }
        return output;
    }
}
