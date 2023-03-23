package services;

import data.Flight;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightDetailsService {
    private List<Flight> allFlights;
    private JSONObject jsonRequest;

    public FlightDetailsService(List<Flight> allFlights, JSONObject jsonRequest) {
        this.allFlights = allFlights;
        this.jsonRequest = jsonRequest;
    }

    public JSONObject getFlightDetails() {
        JSONObject data = jsonRequest.getJSONObject("data");
        Long f_id = Long.valueOf(data.getString("flightid"));

        Optional<Flight> flight = allFlights
                .stream()
                .filter(f -> (f.getId().equals(f_id))).findFirst();


        JSONObject json = new JSONObject();

        if (!flight.isPresent()) {
            json.put("code", "FAILURE");
            json.put("message", "No flight found");
            return json;
        } else {
            json.put("code", "SUCCESS");
            JSONObject flightJson = new JSONObject();
            flightJson.put("departure_time", flight.get().getDepartureTime());
            flightJson.put("air_fare", flight.get().getAirfare());
            json.put("seat_availability", flight.get().getTotalSeats());
            return json;
        }
    }
}
