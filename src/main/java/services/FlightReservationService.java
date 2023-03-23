package services;

import data.Flight;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FlightReservationService {
    private Map<Long, Flight> allFlightsMap;
    private JSONObject jsonRequest;

    public FlightReservationService(List<Flight> allFlights, JSONObject jsonRequest) {
        this.allFlightsMap = allFlights
                .stream()
                .collect(Collectors.toMap(Flight::getId, Function.identity()));
        this.jsonRequest = jsonRequest;
    }

    public JSONObject reserveFlight() {
        JSONObject data = jsonRequest.getJSONObject("data");
        String user_id = jsonRequest.getString("userid");
        Long f_id = Long.valueOf(data.getString("flightid"));
        int numSeats = Integer.parseInt(data.getString("numseats"));

        Flight flight = allFlightsMap.get(f_id);
        JSONObject json = new JSONObject();
        if (flight != null) {
            int seatsAvail = flight.reserveSeats(user_id, numSeats);
            if (seatsAvail == -1) {
                json.put("code", "FAILURE");
                json.put("message", "You are trying to reserve too many seats");
                return json;
            }
            json.put("code", "SUCCESS");
            JSONObject flightJson = new JSONObject();
            flightJson.put("seats_available", seatsAvail);
            return json;
        }
        json.put("code", "FAILURE");
        json.put("message", "flight not found");
        return json;
    }
}
