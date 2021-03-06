package kr.couchcoding.tennis_together_crawler.repository;

import kr.couchcoding.tennis_together_crawler.crawler.CourtInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<CourtInfo, Long> {
}
