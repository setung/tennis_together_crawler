package kr.couchcoding.tennis_together_crawler.crawler;

import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCrawler;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CourtTest {

    @Autowired
    EntityManager em;
    @Autowired
    SeoulTennisCrawler seoulTennisCrawler;
    @Autowired
    GoTennisCrawler goTennisCrawler;

    @Test
    public void seoulTennisToCourt() {
        Map<String, SeoulTennisCourt> seoulTennis = seoulTennisCrawler.crawling();

        for (String key : seoulTennis.keySet()) {
            CourtInfo court = new CourtInfo(seoulTennis.get(key));
            em.persist(court);
        }
    }

    @Test
    public void goTennisToCourt() {
        Map<String, GoTennisCourt> goTennis = goTennisCrawler.crawling();

        for (String key : goTennis.keySet()) {
            CourtInfo court = new CourtInfo(goTennis.get(key));
            em.persist(court);
        }
    }

}
