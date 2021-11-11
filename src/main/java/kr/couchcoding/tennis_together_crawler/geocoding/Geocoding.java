package kr.couchcoding.tennis_together_crawler.geocoding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Geocoding {

    @Value("${geo.api.id}")
    private String API_KEY_ID;
    @Value("${geo.api.key}")
    private String API_KEY;
    @Value("${geo.api.url}")
    private String API_URL;

    public GeoData getGeoDataByAddress(String completeAddress) {
        String url = API_URL + completeAddress;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", API_KEY);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GeoData> response = restTemplate.exchange(url, HttpMethod.GET, request, GeoData.class);

        return response.getBody();
    }

    public LatLonData getLatLon(String completeAddress) {
        GeoData geoData = getGeoDataByAddress(completeAddress);
        GeoData.Address[] addresses = geoData.getAddresses();

        if (addresses.length != 0) {
            return new LatLonData(addresses[0].getY(), addresses[0].getX());
        }
        return new LatLonData(0, 0);
    }

}
