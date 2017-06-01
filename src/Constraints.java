import java.util.ArrayList;

public class Constraints {

	public static double AllConstraints(Schedule split) {
		double penalty = 0;

		// Hard Constraints
		penalty += HardConstraint1(split);
		penalty += HardConstraint2(split);

		// Soft Constraints
		penalty += SoftConstraint1(split);
		penalty += SoftConstraint2(split);

		return penalty;
	}

	public static double HardConstraint1(Schedule split) {
		ArrayList<Team> involvedteams = split.getInvolvedTeams();
		int occurencecounter = 0;
		int ongoingweek = -1;
		for (int i = 0; i < involvedteams.size(); i++) {
			occurencecounter = 0;
			ongoingweek = -1;
			for (int x = 0; x < split.getSlots().size(); x++) {
				
				Slot aux = split.getSlots().get(x);
				if (aux.getMatch_assigned() == null)
					continue;
				if (x == 0) {
					ongoingweek = aux.getWeek();
					occurencecounter = 0;
				} else if (aux.getWeek() != ongoingweek) { // new week begins
					if (occurencecounter < 1 || occurencecounter > 2)
						return 0.4;
					ongoingweek = aux.getWeek();
					occurencecounter = 0;
				}

				// If team plays
				if (aux.getMatch_assigned().getTeam1() == involvedteams.get(i)
						|| aux.getMatch_assigned().getTeam2() == involvedteams.get(i))
					occurencecounter++;

			}

		}
		return 0;
	}

	public static double HardConstraint2(Schedule split) {
		for (int x = 0; x < split.getSlots().size(); x++) {
			Slot aux = split.getSlots().get(x);
			if (aux.getValue() < 260)
				return 0.3;
		}
		return 0;
	}

	public static double SoftConstraint1(Schedule split) {
		String t1 = null;
		String t2 = null;
		boolean flag = false;
		for (int x = 0; x < split.getSlots().size(); x++) {
			if(x == 0)
				flag = false;
			Slot aux = split.getSlots().get(x);
			if(aux.getMatch_assigned() == null)
				continue;
			if (!flag) {
				t1 = aux.getMatch_assigned().getTeam1().getName();
				t2 = aux.getMatch_assigned().getTeam2().getName();
				flag = true;
				continue;
			}
			if (aux.getMatch_assigned().getTeam1().getName().equals(t1)
					|| aux.getMatch_assigned().getTeam1().getName().equals(t2)
					|| aux.getMatch_assigned().getTeam2().getName().equals(t1)
					|| aux.getMatch_assigned().getTeam2().getName().equals(t2)) {
				return 0.20;
			} else {
				t1 = aux.getMatch_assigned().getTeam1().getName();
				t2 = aux.getMatch_assigned().getTeam2().getName();
			}

		}
		return 0;
	}

	public static double SoftConstraint2(Schedule split) {
		for (int x = 0; x < split.getSlots().size(); x++) {
			Slot aux = split.getSlots().get(x);
			if (aux.getValue() < 300)
				return 0.1;
		}
		return 0;
	}

}
