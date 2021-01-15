package ro.mta.se.lab.model;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class MeteoModelTestMockito {

    private MeteoModel meteoModel;

    private String temperature;
    private String wind;
    private String humidity;

    /**
     * Valori mockito test:
     *      - temperature: 55
     *      - wind: 66
     *      - humidity: 77
     */

    @Before
    public void setUp() throws Exception {

        String API_key = "d41a3b43ac8cf4ceaa48bf6d6c1f78f4";
        String API_url = "http://api.openweathermap.org/data/2.5/weather?q="+"London"+","+"GB"+"&units=metric&appid="+API_key;

        try{

            Map m = mock(Map.class);

            when(m.get("temp")).thenReturn("55");
            when(m.get("speed")).thenReturn("66");
            when(m.get("humidity")).thenReturn("77");

            URL url = new URL(API_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer content = new StringBuffer();

            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();

            //Map respMap = (Map) parser.parse(content.toString());
            //Map mainMap = (Map) parser.parse(respMap.get("main").toString());
            //Map windMap = (Map) parser.parse(respMap.get("wind").toString());

            //this.temperature = mainMap.get("temp").toString();
            //this.humidity = mainMap.get("humidity").toString();
            //this.wind = windMap.get("speed").toString();

            this.temperature = (String) m.get("temp");
            this.humidity = (String) m.get("humidity");
            this.wind = (String) m.get("speed");



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //catch (ParseException e) {
          //  e.printStackTrace();
        //}
    }

    @Test
    public void getTemperature() {
        assertEquals(this.temperature,"55");
    }

    @Test
    public void getWind() {
        assertEquals(this.wind,"66");
    }

    @Test
    public void getHumidity() {
        assertEquals(this.humidity,"77");
    }
}