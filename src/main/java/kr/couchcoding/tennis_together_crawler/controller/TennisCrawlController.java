package kr.couchcoding.tennis_together_crawler.controller;

import kr.couchcoding.tennis_together_crawler.CourtRepository;
import kr.couchcoding.tennis_together_crawler.crawler.Court;
import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCrawler;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/crawler")
@RequiredArgsConstructor
public class TennisCrawlController {

    private final CourtRepository repository;
    private final SeoulTennisCrawler seoulTennisCrawler;
    private final GoTennisCrawler goTennisCrawler;

    @PostMapping("/seoultennis")
    public void seoulTennis() {
        Map<String, SeoulTennisCourt> crawling = seoulTennisCrawler.crawling();
        for (String key : crawling.keySet()) {
            SeoulTennisCourt seoulTennisCourt = crawling.get(key);
            repository.save(new Court(seoulTennisCourt));
        }
    }

    @PostMapping("/gotennis")
    public void goTennis() {
        Map<String, GoTennisCourt> crawling = goTennisCrawler.crawling();
        for (String key : crawling.keySet()) {
            GoTennisCourt goTennisCourt = crawling.get(key);
            repository.save(new Court(goTennisCourt));
        }
    }
}
