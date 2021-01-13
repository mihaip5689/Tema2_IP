package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ro.mta.se.lab.model.MeteoModel;
import org.json.simple.*;

import javafx.event.ActionEvent;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MeteoController {

    private MeteoModel meteoModel;
    private Map<String,String> locations;

    @FXML
    private ChoiceBox tari;

    @FXML
    private ChoiceBox orase;

    @FXML
    private Button cauta;

    @FXML
    private Label temperature;

    @FXML
    private Label wind;

    @FXML
    private Label humidity;

    @FXML
    private void initialize(){
        JSONParser parser = new JSONParser();
        ObservableList<String> t = FXCollections.observableArrayList();

        try {
            JSONArray loc = (JSONArray) parser.parse(
                    new FileReader("./src/main/resources/locations.json"));

            File f = new File("./src/main/resources/log.txt");

            if(!f.exists())
                f.createNewFile();

            for(Object l : loc){
                JSONObject location = (JSONObject) l;

                String ID = (String) location.get("ID");
                String nm = (String) location.get("nm");
                String lat = (String) location.get("lat");
                String lon = (String) location.get("lon");
                String countryCode = (String) location.get("countryCode");

                if(!locations.containsValue(countryCode))
                    t.add(countryCode);

                locations.put(nm,countryCode);
            }

            tari.setItems(t);

            temperature.setVisible(false);
            wind.setVisible(false);
            humidity.setVisible(false);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void LoadCities(ActionEvent e){
        ObservableList<String> o = FXCollections.observableArrayList();

        if(tari.getValue()!=null){
            for(Map.Entry<String,String> entry : locations.entrySet()){
                if(tari.getValue().toString().equals(entry.getValue())) {
                    o.add(entry.getKey());
                }
            }
        }

        orase.getItems().clear();
        orase.setItems(o);
    }

    @FXML
    public void LoadWeatherData(ActionEvent e){
        meteoModel = new MeteoModel(orase.getValue().toString(), tari.getValue().toString());

        this.temperature.setText(meteoModel.getTemperature()+"\u00B0C");
        this.wind.setText(meteoModel.getWind()+"km/h");
        this.humidity.setText(meteoModel.getHumidity()+"%");

        wind.setVisible(true);
        humidity.setVisible(true);
        temperature.setVisible(true);

        LogSearch(orase.getValue().toString(), tari.getValue().toString(), temperature.getText(), wind.getText(), humidity.getText());
    }

    private void LogSearch(String city, String country, String temp, String wind, String hum){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            FileWriter fw = new FileWriter("./src/main/resources/log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(city+" "+country+" "+temp+" "+wind+" "+hum+" "+dtf.format(now));
            bw.newLine();

            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MeteoController() {
        locations = new HashMap<>();
    }

}
