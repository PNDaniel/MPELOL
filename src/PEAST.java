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

             //   IF Cost(Sindex) < Cost(best_sol) THEN Set best_sol = Sindex
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

        int k = current_round/2;
        //TODO Set objectToMove = RandomObject(S)
        // ^Deverá ser um matchup? pois os objects são matchups
        int index = 0;
        int last_increment = Integer.MAX_VALUE; // supostamente devia ser infinito, mas infinito só existe com double, usa-se o MAX_VALUE para simular o infinito

        while(index < maximum_sequence_length){
            //TODO Set currentCost = Cost(S)
            if (index > 0){

            }
            index++;
        }

        return split;
    }
}
