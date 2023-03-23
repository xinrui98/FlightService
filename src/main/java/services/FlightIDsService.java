package services;

import data.Flight;
import org.json.JSONObject;
import java.util.List;
import java.util.stream.Collectors;

public class FlightIDsService {
    private List<Flight> allFlights;
    private JSONObject jsonRequest;

    public FlightIDsService(List<Flight> allFlights, JSONObject jsonRequest) {
        this.allFlights = allFlights;
        this.jsonRequest = jsonRequest;
    }

    public JSONObject getFlightIds() {
        JSONObject data = jsonRequest.getJSONObject("data");
        String source = data.getString("source");
        String destination = data.getString("destination");

        List<Long> flights = allFlights
                .stream()
                .filter(f -> (f.getSource().equalsIgnoreCase(source) && f.getDestination().equalsIgnoreCase(destination)))
                .map(Flight::getId)
                .collect(Collectors.toList());

        JSONObject json = new JSONObject();

        if (flights.isEmpty()) {
            json.put("code", "FAILURE");
            json.put("message", "No flights found");
            return json;
        } else {
            json.put("code", "SUCCESS");
            JSONObject flightJson = new JSONObject();
            flightJson.put("flightids", flights);
            json.put("data", flightJson);
            return json;
        }
    }
}
