package vote.config;

import vote.dao.VoteDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@Configuration
@EnableAutoConfiguration
public class Config {
    @Bean
    public VoteDao voteDaoBean() {
        return new VoteDao();
    }

}
