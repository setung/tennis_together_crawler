package kr.couchcoding.tennis_together_crawler.crawler;

import kr.couchcoding.tennis_together_crawler.crawler.gotennis.GoTennisCourt;
import kr.couchcoding.tennis_together_crawler.crawler.seoultennis.SeoulTennisCourt;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CourtInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courtNo;

    @ManyToOne
    @JoinColumn(name = "loc_cd_no")
    private LocCd locCd;

    @Column(name = "court_name", length = 100)
    private String name;

    @Column(name = "road_adr", length = 100)
    private String roadAdr;

    private String price;

    @Column(name = "org_url")
    private String orgUrl;

    private Double lat;
    private Double lon;

    @Column(name = "court_contact", length = 200)
    private String courtContact;

    @Lob
    @Column(name = "adt_info")
    private String adtInfo;

    @Column(name = "operate_time")
    private String operateTime;

    @Column(name = "reg_dtm")
    @CreatedDate
    private LocalDateTime regDtm;

    @Column(name = "upd_dtm")
    @LastModifiedDate
    private LocalDateTime updDtm;

    @Column(name = "act_dv_cd")
    private Character actDvCd = '1';

    public CourtInfo(GoTennisCourt goTennisCourt) {
        name = goTennisCourt.getTitle();
        roadAdr = goTennisCourt.getAddress();
        orgUrl = goTennisCourt.getUrl();
        courtContact = goTennisCourt.getTel();
        adtInfo = goTennisCourt.getInstructions();
        price = getGoTennisCourtFee(goTennisCourt.getFee_out(), goTennisCourt.getFee_in());
        operateTime = getGoTennisCourtTime(goTennisCourt.getOperatingTime_in(), goTennisCourt.getOperatingTime_out());
        lat = goTennisCourt.getLat();
        lon = goTennisCourt.getLon();
        locCd = goTennisCourt.getLocCd();
    }

    private String getGoTennisCourtTime(String operatingTime_in, String operatingTime_out) {
        String time = "";
        if (!(operatingTime_out == null || operatingTime_out.equals("없음") || operatingTime_out.equals("해당사항없음") || operatingTime_out.equals("해당사항 없음")))
            time += "[실외] " + operatingTime_out + " ";

        if (!(operatingTime_in == null || operatingTime_in.equals("없음") || operatingTime_in.equals("해당사항없음") || operatingTime_in.equals("해당사항 없음")))
            time += "[실내] " + operatingTime_in;

        return time;
    }

    private String getGoTennisCourtFee(String fee_out, String fee_in) {
        String fee = "";
        if (!(fee_out == null || fee_out.equals("없음") || fee_out.equals("해당사항없음") || fee_out.equals("해당사항 없음")))
            fee += "[실외] " + fee_out + " ";

        if (!(fee_in == null || fee_in.equals("없음") || fee_in.equals("해당사항없음") || fee_in.equals("해당사항 없음")))
            fee += "[실내] " + fee_in;

        return fee;
    }

    public CourtInfo(SeoulTennisCourt seoulTennisCourt) {
        name = seoulTennisCourt.getName();
        price = seoulTennisCourt.getFee();
        roadAdr = seoulTennisCourt.getAddress();
        orgUrl = seoulTennisCourt.getUrl();
        courtContact = seoulTennisCourt.getTel();
        adtInfo = seoulTennisCourt.getInstructions();
        lat = seoulTennisCourt.getLat();
        lon = seoulTennisCourt.getLon();
        locCd = seoulTennisCourt.getLocCd();
    }
}
