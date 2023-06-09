package services;

import data.Flight;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class FlightReservationService extends AbstractService {

    private List<Flight> allFlights;
    private Map<Long, Flight> allFlightsMap;
    private JSONObject jsonRequest;

    public FlightReservationService(List<Flight> allFlights, JSONObject jsonRequest) {
        super(allFlights, jsonRequest);
    }

    public JSONObject reserveFlight() {
        JSONObject data = jsonRequest.getJSONObject("data");
        String user_id = jsonRequest.getString("userid");
        Long f_id = Long.valueOf(data.getString("flightid"));
        int numSeats = Integer.parseInt(data.getString("numseats"));

        Flight flight = allFlightsMap.get(f_id);
        if (flight != null) {
            int seatsAvail = flight.reserveSeats(user_id, numSeats);
            if (seatsAvail == -1) {
                return this.createErrorJSONObject("You're trying to reserve too many seats");
            }
            JSONObject json = new JSONObject();
            json.put("code", "SUCCESS");
            JSONObject flightJson = new JSONObject();
            flightJson.put("seats_available", seatsAvail);

            this.getEventManager().notifySubscribers(f_id, seatsAvail);

            return json;
        }
        return this.createErrorJSONObject("No flight found");

    }
}
