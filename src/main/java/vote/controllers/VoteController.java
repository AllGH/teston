package vote.controllers;

import vote.config.Config;
import vote.dao.VoteDao;
import vote.dto.QuestionDto;
import vote.model.Vote;
import vote.model.VoteStatus;
import vote.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteDao voteDao;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(new Class<?>[] {VoteController.class, Config.class}, args);
    }

    //    curl -G http://127.0.0.1:8080/votes
    @RequestMapping(method = RequestMethod.GET)
    public List<Vote> getAllVote() {
        return voteDao.getAll();
    }

    //    curl -H "Content-Type: application/json" -X POST -d "{\"question\":\"Say please...\",\"answers\":[\"Answer1\",\"Answer2\",\"Answer3\"]}"  http://127.0.0.1:8080/votes/create
    @RequestMapping(path = "/create",
            method = RequestMethod.POST)
    public Vote createVote(@RequestBody QuestionDto pvote) {
        if (pvote != null && pvote.getQuestion() != null && !pvote.getQuestion().isEmpty()
                && pvote.getAnswers() != null && !pvote.getAnswers().isEmpty()) {
            return voteDao.add(pvote.getQuestion(), pvote.getAnswers());
        }
        return null;
    }

    //    curl -X POST -d id=0 http://127.0.0.1:8080/votes/start
    @RequestMapping(path = "/start",
            method = RequestMethod.POST)
    public String startVote(@RequestParam("id") Integer id, HttpServletRequest request) {
        String link = "Bad input parameters";

        try {
            Vote vote = voteDao.getById(id);

            if (vote != null && vote.getStatus() == VoteStatus.CREATED) {
                vote.setStatus(VoteStatus.ACTIVE);
                vote.setLink(RandomString.getRandomString(10));
                link = String.format("%s/%s",
                        request.getRequestURL().substring(0, request.getRequestURL().indexOf("/start")),
                        vote.getLink());
                System.out.println(String.format("Link: %s", link));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Internal error";
        }

        return link;
    }

    //    curl -G http://127.0.0.1:8080/votes/jpjXl1oBCT
    @RequestMapping(path = "/{link}",
            method = RequestMethod.GET)
    public Vote getVote(@PathVariable("link") String link) {
        Vote vote = voteDao.findByLink(link);

        if (vote != null) {
            return vote;
        }

        return null;
    }

    //curl -X POST -d "answer=Answer2" http://127.0.0.1:8080/votes/iNI6PxIHUP
    @RequestMapping(path = "/{link}",
            method = RequestMethod.POST)
    public String registerVoice(@PathVariable("link") String link, @RequestParam("answer") String answer) {
        Vote vote = voteDao.findByLink(link);

        if (vote != null &&
                answer != null && !answer.isEmpty() &&
                vote.getStatus() == VoteStatus.ACTIVE) {
            int val = vote.getAnswers().get(answer);
            vote.getAnswers().replace(answer, val+1);
            System.out.println(String.format("Answer count: %s", vote.getAnswers().get(answer)));

            return "Answer registered";
        }

        return "Unknown answer";
    }

    //    curl -X POST  http://127.0.0.1:8080/votes/iNI6PxIHUP/stop
    @RequestMapping(path = "/{link}/stop",
            method = RequestMethod.POST)
    public String stopVote(@PathVariable("link") String link) {
        Vote vote = voteDao.findByLink(link);

        if (vote != null &&
                vote.getStatus() == VoteStatus.ACTIVE) {
            vote.setStatus(VoteStatus.STOPPED);

            return "Vote stopped";
        }

        return "Vote is not active";
    }

    //        curl -G http://127.0.0.1:8080/votes/jpjXl1oBCT/stat
    @RequestMapping(path = "/{link}/stat",
            method = RequestMethod.GET)
    public Map<String, Integer> getVoteStatistic(@PathVariable("link") String link) {
        Vote vote = voteDao.findByLink(link);

        if (vote != null) {
            return vote.getAnswers();
        }

        return null;
    }
}