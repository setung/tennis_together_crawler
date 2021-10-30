package kr.couchcoding.tennis_together_crawler.crawler.seoultennis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeoulTennisCourt {

    private String name;
    private String address;
    private String tel;
    private String fee;
    private String instructions;
    private String url;
    private double lat;
    private double lon;
}
