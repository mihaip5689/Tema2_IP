package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ro.mta.se.lab.model.MeteoModel;
import org.json.simple.*;

import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MeteoController {

    private MeteoModel meteoModel;
    private Map<String,String> locations = new HashMap<>();

    @FXML
    private ChoiceBox tari;

    @FXML
    private ChoiceBox orase;

    @FXML
    private void initialize(){
        JSONParser parser = new JSONParser();
        ObservableList<String> t = FXCollections.observableArrayList();

        try {
            JSONArray loc = (JSONArray) parser.parse(
                    new FileReader("./src/main/resources/locations.json"));

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

    public MeteoController() {

    }

}
