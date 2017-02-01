package vote.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote {
    private final int id;
    private String link;
    public String question;
    private VoteStatus status;
    private Map<String, Integer> answers;

    public Vote(int id, String question, List<String> answers) {
        this.question = question;
        this.id = id;
        this.status = VoteStatus.CREATED;
        this.answers = new HashMap<>();
        answers.stream().forEach(v -> this.answers.put(v, 0));
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Integer> answers) {
        this.answers = answers;
    }

    public VoteStatus getStatus() {
        return status;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }
}
