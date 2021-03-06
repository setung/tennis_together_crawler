package kr.couchcoding.tennis_together_crawler.crawler.gotennis;

import kr.couchcoding.tennis_together_crawler.crawler.LocCd;
import kr.couchcoding.tennis_together_crawler.geocoding.Geocoding;
import kr.couchcoding.tennis_together_crawler.geocoding.LatLonData;
import kr.couchcoding.tennis_together_crawler.repository.LocCdRepository;
import kr.couchcoding.tennis_together_crawler.service.LocCdService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class GoTennisCrawler {

    private static final String BASIC_URL = "http://gotennis.kr/category/seoul,gyeonggi,incheon,gangwon,chungnam,busan,ulsan,daegu,gyeongbuk,uncategorized,youtube_skykim/?tag=indoor-court-aturf,indoor-lesson,indoor-court,indoor-lesson-hard,indoor-court-hard,outdoor-court-aturf,outdoor-court-clay,outdoor-court,outdoor-court-hard";

    private final Geocoding geocoding;
    private final LocCdService locCdService;

    public Map<String, GoTennisCourt> crawling() {
        List<String> detailUrls = getCourtDetailUrls();
        Map<String, GoTennisCourt> courts = new HashMap<>();

        for (String url : detailUrls) {
            GoTennisCourt court = getCourt(url);
            courts.put(court.getTitle(), court);
        }

        return courts;
    }

    protected GoTennisCourt getCourt(String detailUrl) {
        GoTennisCourt court = new GoTennisCourt();

        try {
            Document document = Jsoup.connect(detailUrl).get();
            String title = document.select("header.entry-header").text();
            Elements elements = document.select("td");

            court.setTitle(title);

            for (int i = 0; i < elements.size(); i++) {
                String text = elements.get(i).text();
                if (text.equals("??????") || text.equals("???"))
                    court.setAddress(elements.get(++i).text());
                else if (text.equals("??????"))
                    court.setTel(elements.get(++i).text());
                else if (text.equals("????????????(??????)"))
                    court.setOperatingTime_in(elements.get(++i).text());
                else if (text.equals("????????????(??????)"))
                    court.setOperatingTime_out(elements.get(++i).text());
                else if (text.equals("?????????(??????)"))
                    court.setFee_in(elements.get(++i).text());
                else if (text.equals("?????????(??????)"))
                    court.setFee_out(elements.get(++i).text());
                else if (text.equals("????????????")) {
                    if (elements.get(++i).childNodeSize() != 0)
                        court.setUrl(elements.get(i).childNode(0).attr("href"));
                    else
                        court.setUrl(elements.get(i).text());
                } else if (text.equals("????????????"))
                    court.setInstructions(elements.get(++i).text());

                if (court.getAddress() != null) {
                    LatLonData latLon = geocoding.getLatLon(court.getAddress());
                    court.setLat(latLon.getLat());
                    court.setLon(latLon.getLon());

                    court.setLocCd(locCdService.getLocCd(court.getAddress()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(detailUrl + " " + e.getMessage());
        }
        return court;
    }

    protected List<String> getCourtDetailUrls() {
        List<String> detailUrls = new ArrayList<>();

        try {
            Document document = Jsoup.connect(BASIC_URL).get();
            Elements elements = document.select("h1.entry-title a");

            for (Element element : elements) {
                detailUrls.add(element.attr("href"));
            }

        } catch (Exception e) {
            throw new RuntimeException("GoTennis ????????? ?????? " + e.getMessage());
        }

        return detailUrls;
    }
}

