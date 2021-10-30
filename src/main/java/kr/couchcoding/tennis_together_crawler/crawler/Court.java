package kr.couchcoding.tennis_together_crawler.crawler;

import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCourt;
import kr.couchcoding.tennis_together_crawler.geocoding.Geocoding;
import kr.couchcoding.tennis_together_crawler.geocoding.LatLonData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@ToString
@Entity
public class Court {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String loc_sd;
    private String loc_skk;
    private String road_addr;
    private String court_contact;
    private String fee;
    private double lat;
    private double lon;
    private String org_url;
    private String adt_info;
    private String time;

    public Court(GoTennisCourt goTennisCourt) {

        if (goTennisCourt.getAddress() != null) {
            String[] splitAddress = goTennisCourt.getAddress().split(" ");
            loc_sd = splitAddress[0];
            loc_skk = splitAddress[1];
        }
        name = goTennisCourt.getTitle();
        road_addr = goTennisCourt.getAddress();
        org_url = goTennisCourt.getUrl();
        court_contact = goTennisCourt.getTel();
        adt_info = goTennisCourt.getInstructions();
        fee = getGoTennisCourtFee(goTennisCourt.getFee_out(), goTennisCourt.getFee_in());
        time = getGoTennisCourtTime(goTennisCourt.getOperatingTime_in(), goTennisCourt.getOperatingTime_out());
        lat = goTennisCourt.getLat();
        lon = goTennisCourt.getLon();
    }

    private String getGoTennisCourtTime(String operatingTime_in, String operatingTime_out) {
        String time = "";
        if (!(operatingTime_out == null || operatingTime_out.equals("없음") || operatingTime_out.equals("해당사항없음") || operatingTime_out.equals("해당사항 없음")))
            time += "[실외] : " + operatingTime_out + " ";

        if (!(operatingTime_in == null || operatingTime_in.equals("없음") || operatingTime_in.equals("해당사항없음") || operatingTime_in.equals("해당사항 없음")))
            time += "[실내] : " + operatingTime_in;

        return time;
    }

    private String getGoTennisCourtFee(String fee_out, String fee_in) {
        String fee = "";
        if (!(fee_out == null || fee_out.equals("없음") || fee_out.equals("해당사항없음") || fee_out.equals("해당사항 없음")))
            fee += "[실외] : " + fee_out + " ";

        if (!(fee_in == null || fee_in.equals("없음") || fee_in.equals("해당사항없음") || fee_in.equals("해당사항 없음")))
            fee += "[실내] : " + fee_in;

        return fee;
    }

    public Court(SeoulTennisCourt seoulTennisCourt) {
        if (seoulTennisCourt.getAddress() != null) {
            String[] splitAddress = seoulTennisCourt.getAddress().split(" ");
            loc_sd = splitAddress[0];
            loc_skk = splitAddress[1];
        }
        name = seoulTennisCourt.getName();
        fee = seoulTennisCourt.getFee();
        road_addr = seoulTennisCourt.getAddress();
        org_url = seoulTennisCourt.getUrl();
        court_contact = seoulTennisCourt.getTel();
        adt_info = seoulTennisCourt.getInstructions();
        lat = seoulTennisCourt.getLat();
        lon = seoulTennisCourt.getLon();
    }

    private void setLatLon(LatLonData latLon) {
        lat = latLon.getLat();
        lon = latLon.getLon();
    }
}
