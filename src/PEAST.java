import java.util.ArrayList;

public class PEAST {

    public PEAST(){  }

    public Schedule run(int iteration_limit, int cloning_interval, int shuffling_interval, ArrayList<Schedule> population){

        int update_interval = 0; // ADAGEN update interval

        int population_size = population.size();
        Schedule best_solution = null;
        int round = 1;
        while(round <= iteration_limit){
            int index = 1;
            while(index++ <= population_size){
               //TODO Apply GHCM to schedule Sindex to get a new schedule

                if (population.get(index).getScheduleValue() < best_solution.getScheduleValue()){
                    best_solution = population.get(index);
                }
            }
            //TODO Update simulated annealing framework

            if (round % update_interval == 0 % update_interval) {
                //TODO Update ADAGEN Framework
            }
            if (round % shuffling_interval == 0 % shuffling_interval) {
                //TODO Apply shuffling operators
            }
            if (round % cloning_interval == 0 % cloning_interval) {
                //TODO Replace the worst schedule with the best one
            }
            round++;
        }

        return best_solution;
    }

    public Schedule GHCM(Schedule split, int maximum_sequence_length, int current_round){

        double current_temperature = 1/Math.log(Math.pow(0.75,-1)); // T0, initial temperature
        int k = current_round/2;
        Matchup object_to_move = randomObject(split); // Set objectToMove = RandomObject(S)
        Slot target_cell = randomCell(split);
        int index = 0;
        double last_increment = Double.POSITIVE_INFINITY;

        double costDiff = 0.0;

        //TODO eu acho que o algoritmo ainda não faz nada, pois acho que não está a alterar custos
        while(index < maximum_sequence_length){
            double currentCost = split.getScheduleValue(); // currentCost of the iteration is the full cost(value) of a split
            if (index > 0){
                // Inner annealing #1
                if(Math.random() < Math.exp(-1/current_temperature)) {
                    object_to_move = moveToCell(object_to_move, target_cell); // Set targetCell = RandomCell(S)
                }
                else{
                    //TODO Set objectToMove = LeastFitObject(S,targetCell)
                }
            }

            // Inner annealing #2
            if(Math.random() < current_temperature) {
                target_cell = randomCell(split); // Set targetCell = RandomObject(S,targetCell)
            }
            else {
                //TODO Set targetCell = FittestCell(S,objectToMove)
            }
            target_cell.AssignMatchup(object_to_move); // Move objectToMove to targetCell
            if (split.getScheduleValue() > currentCost)
            {
                costDiff = split.getScheduleValue() - currentCost;
                if (costDiff > last_increment){
                    index = Integer.MAX_VALUE;
                }
                else{
                    last_increment = costDiff;
                }
            }
            current_temperature = getTemperature(current_temperature,maximum_sequence_length);
            index++;
        }
        // Outer annealing
        if( index == Integer.MAX_VALUE) {
            if (Math.random() < Math.exp(-costDiff / current_temperature)) {
                return split;
            }
        }
        else{
            //TODO Roll S back to the optimal point in the move sequence.
        }
        return split;
    }

    public Matchup moveToCell(Matchup matchup, Slot slot){

        slot.AssignMatchup(matchup);
        return slot.getMatch_assigned();
    }

    public double getTemperature(double T0, int m){
        double p = 0.0015;
        return Math.pow((-1/(T0 *Math.log(p))), (1/m));
    }

    /**
     * Support function for getting a random Matchup from a Schedule
     * @param schedule
     * @return Matchup currently selected from the split, it will become the matchup
     */
    private Matchup randomObject(Schedule schedule)
    {
        int index = (int)(Math.random() * schedule.getSlots().size());
        return schedule.getSlots().get(index).getMatch_assigned();
    }

    private Slot randomCell(Schedule schedule)
    {
        int index = (int)(Math.random() * schedule.getSlots().size());
        return schedule.getSlots().get(index);
    }
}
