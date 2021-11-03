package kr.couchcoding.tennis_together_crawler.service;

import kr.couchcoding.tennis_together_crawler.crawler.LocCd;
import kr.couchcoding.tennis_together_crawler.repository.LocCdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocCdService {

    private final LocCdRepository locCdRepository;

    public LocCd getLocCd(String roadAdr) {
        if (roadAdr == null || roadAdr.isEmpty())
            return null;

        String[] split = roadAdr.split(" ");
        String locSdName = split[0].substring(0, 2);
        String locSkkName = split[1].substring(0, 2);

        return locCdRepository.findByLocSdNameStartsWithAndLocSkkNameStartsWith(locSdName, locSkkName);
    }
}
