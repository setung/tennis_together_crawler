package kr.couchcoding.tennis_together_crawler.repository;

import kr.couchcoding.tennis_together_crawler.crawler.LocCd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocCdRepository extends JpaRepository<LocCd, Long> {

    LocCd findByLocSdNameStartsWithAndLocSkkNameStartsWith(String locSdName, String locSkkName);
}
