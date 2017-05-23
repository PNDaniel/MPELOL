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
        Matchup object_to_move = randomElement(split); // Set objectToMove = RandomObject(S)
        int index = 0;
        int last_increment = Integer.MAX_VALUE; // Supposedly it should be an infinite value, but "infinite" only exists
                                                // with double types, so we used Integer.MAX_VALUE to simulate it with an int
        while(index < maximum_sequence_length){
            double currentCost = split.getScheduleValue(); // currentCost of the iteration is the full cost(value) of a split
            if (index > 0){
                // Inner annealing #1
                current_temperature = getTemperature(current_temperature,maximum_sequence_length);
                if(Math.random() < Math.exp(-1/current_temperature)) {
                    //object_to_move =
                    // TODO Set objectToMove = RandomObject(S,targetCell)
                }
                else{

                }
            }
            current_temperature = getTemperature(current_temperature,maximum_sequence_length);
            // Inner annealing #2
            if(Math.random() < current_temperature) {

            }
            else {

            }
            index++;
        }
        // Outer annealing
        if( index == Integer.MAX_VALUE)
        {
            current_temperature = getTemperature(current_temperature,maximum_sequence_length);
          //  if(Math.random() < Math.exp(-costDiff/current_temperature)) {
                return split;
        }
        return split;
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
    private Matchup randomElement(Schedule schedule)
    {
        int index = (int)(Math.random() * schedule.getSlots().size());
        return schedule.getSlots().get(index).getMatch_assigned();
    }
}
