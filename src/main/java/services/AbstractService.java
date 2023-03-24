package services;

import data.Flight;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AbstractService {

    private List<Flight> allFlights;
    private Map<Long, Flight> allFlightsMap;
    private JSONObject jsonRequest;
    private EventManagerSingleton eventManager = EventManagerSingleton.getInstance();


    public AbstractService(List<Flight> allFlights, JSONObject jsonRequest) {
        this.allFlights = allFlights;
        this.jsonRequest = jsonRequest;
        this.allFlightsMap = allFlights
                .stream()
                .collect(Collectors.toMap(Flight::getId, Function.identity()));
    }

    EventManagerSingleton getEventManager() {
        return eventManager;
    }

    public JSONObject createErrorJSONObject(String errorMessage){
        JSONObject json = new JSONObject();
        json.put("code", "FAILURE");
        json.put("message", errorMessage);
        return json;
    }
}
