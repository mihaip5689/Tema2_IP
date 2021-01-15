package ro.mta.se.lab.model;

import junit.framework.TestCase;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class MeteoModelTest extends TestCase {

    private MeteoModel meteoModel;

    private String temperature;
    private String wind;
    private String humidity;

    public void setUp() throws Exception {
        //super.setUp();
        meteoModel = new MeteoModel("London","GB");

        String API_key = "d41a3b43ac8cf4ceaa48bf6d6c1f78f4";
        String API_url = "http://api.openweathermap.org/data/2.5/weather?q="+"London"+","+"GB"+"&units=metric&appid="+API_key;

        try{
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

            Map respMap = (Map) parser.parse(content.toString()); //jsonToMap (content.toString());
            Map mainMap = (Map) parser.parse(respMap.get("main").toString());
            Map windMap = (Map) parser.parse(respMap.get("wind").toString());

            this.temperature = mainMap.get("temp").toString();
            this.humidity = mainMap.get("humidity").toString();
            this.wind = windMap.get("speed").toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void testGetTemperature() {
        assertEquals(meteoModel.getTemperature(), this.temperature);
    }

    public void testGetWind() {
        assertEquals(meteoModel.getWind(), this.wind);
    }

    public void testGetHumidity() {
        assertEquals(meteoModel.getHumidity(), this.humidity);
    }
}