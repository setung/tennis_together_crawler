package kr.couchcoding.tennis_together_crawler.crawler;

import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCourt;
import kr.couchcoding.tennis_together_crawler.geocoding.LatLonData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
public class CourtInfo {

    @Id
    @GeneratedValue
    private Long courtNo;
    private String name;
    private String roadAddr;
    private String fee;
    private String orgUrl;
    private Double lat;
    private Double lon;
    private String time;
    private String courtContact;
    @Lob
    private String adtInfo;
    private LocalDateTime fstRegDtm;
    private LocalDateTime lstUpdDtm;
    private String locSd;
    private String locSkk;

    public CourtInfo(GoTennisCourt goTennisCourt) {
        if (goTennisCourt.getAddress() != null) {
            String[] splitAddress = goTennisCourt.getAddress().split(" ");
            locSd = splitAddress[0];
            locSkk = splitAddress[1];
        }
        name = goTennisCourt.getTitle();
        roadAddr = goTennisCourt.getAddress();
        orgUrl = goTennisCourt.getUrl();
        courtContact = goTennisCourt.getTel();
        adtInfo = goTennisCourt.getInstructions();
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

    public CourtInfo(SeoulTennisCourt seoulTennisCourt) {
        if (seoulTennisCourt.getAddress() != null) {
            String[] splitAddress = seoulTennisCourt.getAddress().split(" ");
            locSd = splitAddress[0];
            locSkk = splitAddress[1];
        }
        name = seoulTennisCourt.getName();
        fee = seoulTennisCourt.getFee();
        roadAddr = seoulTennisCourt.getAddress();
        orgUrl = seoulTennisCourt.getUrl();
        courtContact = seoulTennisCourt.getTel();
        adtInfo = seoulTennisCourt.getInstructions();
        lat = seoulTennisCourt.getLat();
        lon = seoulTennisCourt.getLon();
    }

    private void setLatLon(LatLonData latLon) {
        lat = latLon.getLat();
        lon = latLon.getLon();
    }

    /*
    보류
    @ManyToOne
    @JoinColumn(name = "loc_sd")
    private LocCd locSd;
    @ManyToOne
    @JoinColumn(name = "loc_skk")
    private LocCd locSkk;*/

}
