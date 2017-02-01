package vote.dao;

import vote.model.Vote;

import java.util.List;

public interface IVoteDao {
    Vote getById(int id);
    Vote add(String name, List<String> answers);
    Vote findByLink(String link);
    List<Vote> getAll();
}
