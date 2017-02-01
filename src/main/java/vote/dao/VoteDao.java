package vote.dao;

import vote.model.Vote;
import vote.model.VoteArchive;
import java.util.List;
import java.util.Optional;

public class VoteDao implements IVoteDao {
    public VoteDao() {}
    @Override
    public Vote getById(int id) {
        Vote vote = null;
        Optional<Vote> first = VoteArchive.getVotes().stream().filter(v -> v.getId() == id).findFirst();
        if (first.isPresent())
            vote = first.get();

        return vote;
    }

    @Override
    public Vote add(String name, List<String> answers) {
        return VoteArchive.createVote(name, answers);
    }

    @Override
    public Vote findByLink(String link) {
        Vote vote = null;
        Optional<Vote> first = VoteArchive.getVotes().stream().filter(v -> link.equals(v.getLink())).findFirst();
        if (first.isPresent())
            vote = first.get();

        return vote;
    }

    @Override
    public List<Vote> getAll() {
        return VoteArchive.getVotes();
    }
}
