package services;

import data.Flight;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FlightViewAllService extends AbstractService {
    private List<Flight> allFlights;
    private Map<Long, Flight> allFlightsMap;
    private JSONObject jsonRequest;

    public FlightViewAllService(List<Flight> allFlights, JSONObject jsonRequest) {
        super(allFlights, jsonRequest);
    }

    public JSONObject getAllFlights() {
        JSONObject data = jsonRequest.getJSONObject("data");

        JSONObject json = new JSONObject();

        if (allFlights.isEmpty()) {
            return this.createErrorJSONObject("No flight found");
        } else {
            json.put("code", "SUCCESS");
            JSONObject flightJson = new JSONObject();
            flightJson.put("all_flights", allFlights.stream().map(Flight::getId));
            return json;
        }
    }
}
