import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conference {

  public static void main(String[] args) throws IOException {
    ClassLoader classLoader = Conference.class.getClassLoader();
    File attendeeFile = new File(classLoader.getResource("attendees.json").getFile());
    File lateArrivalFile = new File(classLoader.getResource("lateArrivals.json").getFile());
    File alignmentFile = new File(classLoader.getResource("alignmentTestResults.json").getFile());
    ObjectMapper objectMapper = new ObjectMapper();

    List<HashMap<String, String>> attendeeList = null;
    List<HashMap<String, String>> lateArrivalList = null;
    Map<String, String> alignmentMap = null;
    try {
      attendeeList = objectMapper.readValue(attendeeFile, ArrayList.class);
      lateArrivalList = objectMapper.readValue(lateArrivalFile, ArrayList.class);
      alignmentMap = objectMapper.readValue(alignmentFile, HashMap.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for(HashMap<String, String> lateArrival : lateArrivalList) {
      attendeeList.add(lateArrival);
    }

    List<String> heroesToRemove = new ArrayList<>();
    heroesToRemove.add("Hercules");
    heroesToRemove.add("Merida");
    heroesToRemove.add("Maui");

    List<HashMap<String, String>> attendeeListCopy = List.copyOf(attendeeList);
    for(HashMap<String, String> attendee : attendeeListCopy) {
      if(heroesToRemove.contains(attendee.get("name"))) {
        attendeeList.remove(attendee);
      } else {
        attendee.put("alignment", alignmentMap.get(attendee.get("name")));
      }
    }

    attendeeListCopy = List.copyOf(attendeeList);
    for(HashMap<String, String> attendee : attendeeListCopy) {
      if(attendee.get("alignment").equals("Good")) {
        attendeeList.remove(attendee);
      }
    }

    ObjectWriter objectWriter = objectMapper.writer();
    objectWriter.writeValue(new File("verfiedAttendees.json"), attendeeList);
  }
}
