package services;

import data.DateTimeString;
import data.Flight;
import data.UserMonitoring;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MonitorUpdatesService extends AbstractService {
    private List<Flight> allFlights;
    private Map<Long, Flight> allFlightsMap;
    private JSONObject jsonRequest;

    public MonitorUpdatesService(List<Flight> allFlights, JSONObject jsonRequest) {
        super(allFlights, jsonRequest);
    }

    public JSONObject addToMonitor() {
        JSONObject data = jsonRequest.getJSONObject("data");
        String user_id = jsonRequest.getString("userid");
        Long f_id = Long.valueOf(data.getString("flightid"));

        if (allFlightsMap.containsKey(f_id)) {
            LocalDateTime monitorEndDateTime = new DateTimeString(data.getString("numseats")).getLocalDateTime();
            UserMonitoring userMonitoring = new UserMonitoring();
            userMonitoring.setU_id(user_id);
            userMonitoring.setDatetime(monitorEndDateTime);
            this.getEventManager().addSubscriber(f_id, userMonitoring);

            JSONObject json = new JSONObject();
            json.put("code", "SUCCESS");
            return json;
        }
        return this.createErrorJSONObject("No flight found");


    }
}