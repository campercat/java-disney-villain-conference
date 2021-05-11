import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) throws IOException {
    ClassLoader classLoader = Main.class.getClassLoader();
    File attendeesFile = new File(classLoader.getResource("attendees.json").getFile());
    File lateArrivalsFile = new File(classLoader.getResource("lateArrivals.json").getFile());
    File alignmentTestResultsFile = new File(
        classLoader.getResource("alignmentTestResults.json").getFile());

    List<HashMap<String, String>> attendees = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    attendees = objectMapper.readValue(attendeesFile, ArrayList.class);

    List<HashMap<String, String>> lateArrivalsList = new ArrayList<>();
    lateArrivalsList = objectMapper.readValue(lateArrivalsFile, ArrayList.class);

    for (HashMap<String, String> lateArrival : lateArrivalsList) {
      attendees.add(lateArrival);
    }

    List<String> herosToLookFor = new ArrayList<>();
    herosToLookFor.add("Hercules");
    herosToLookFor.add("Merida");
    herosToLookFor.add("Maui");

    List<HashMap<String, String>> attendeesCopy = List.copyOf(attendees);
    for (HashMap<String, String> attendee : attendeesCopy) {
      if (herosToLookFor.contains(attendee.get("name"))) {
//        System.out.println(attendee);
        attendees.remove(attendee);
      }
    }

    Map<String, String> alignmentTestResults = new HashMap<>();
    alignmentTestResults = objectMapper.readValue(alignmentTestResultsFile, HashMap.class);

    for (HashMap<String, String> attendee : attendees) {
      String attendeeName = attendee.get("name");
      String alignmentValue = alignmentTestResults.get(attendeeName);
      attendee.put("alignment", alignmentValue);
    }

    List<HashMap<String, String>> updatedAttendeesCopy = List.copyOf(attendees);
    for (HashMap<String, String> attendee : updatedAttendeesCopy) {
      if (attendee.get("alignment").equals("Good")) {
        attendees.remove(attendee);
      }
    }

    ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    objectWriter.writeValue(new File("verifiedAttendees.json"), attendees);

//    System.out.println(objectMapper.writeValueAsString(attendees));
//    System.out.println(objectMapper.writeValueAsString(attendees.size()));
  }
}
