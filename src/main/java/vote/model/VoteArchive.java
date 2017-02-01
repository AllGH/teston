package vote.model;

import java.util.ArrayList;
import java.util.List;

public class VoteArchive {
    private static Vote vote;
    private static List<Vote> votes;
    private static int id;

    static {
        votes = new ArrayList<>();
        id=0;
    }

    public static Vote getInstanceVote() {
        return vote;
    }

    public static Vote createVote(String name, List<String> answers) {
        vote = new Vote(id++, name, answers);
        votes.add(vote);
        return vote;
    }

    public static List<Vote> getVotes() {
        return votes;
    }

}
