
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
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    ClassLoader classLoader = Main.class.getClassLoader();
    File attendeesFile = new File(classLoader.getResource("attendees.json").getFile());
    File lateArrivalsFile = new File(classLoader.getResource("lateArrivals.json").getFile());
    File alignmentTestFile = new File(classLoader.getResource("alignmentTestResults.json").getFile());

    try {
      List<HashMap<String, String>> attendees = new ArrayList<HashMap<String, String>>();
      ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

      //Set attendees list
      attendees = objectMapper.readValue(attendeesFile, ArrayList.class);
      // Print out formatted json
      System.out.println("Attendees: \n\n" + attendees);
      System.out.println(objectMapper.writeValueAsString(attendees));

      //Add late arrivals to attendees
      List<HashMap<String, String>> lateArrivals = new ArrayList<HashMap<String, String>>();
      lateArrivals = objectMapper.readValue(lateArrivalsFile, ArrayList.class);
      for (HashMap<String, String> character : lateArrivals) {
        attendees.add(character);
      }

      //Remove heroes by name
      List<String> heroNames = new ArrayList<>();
      heroNames.add("Maui");
      heroNames.add("Hercules");
      heroNames.add("Merida");

      List<HashMap<String, String>> attendeesCopy = List.copyOf(attendees);
      for (HashMap<String, String> character : attendeesCopy) {
        if (heroNames.contains(character.get("name"))) {
          attendees.remove(character);
        }
      }

      // Update each attendee with their alignment test result
      Map<String, String> testResults = new HashMap<>();
      testResults = objectMapper.readValue(alignmentTestFile, HashMap.class);

      Set<String> characterNames = testResults.keySet();
      for (String characterName : characterNames) {
        for (HashMap<String, String> attendee : attendees) {
          if (characterName.equals(attendee.get("name"))) {
            attendee.put("alignment", testResults.get(characterName));
          }
        }
      }

      // Remove any attendees with an alignment of Good
      List<HashMap<String, String>> updatedAttendeesCopy = List.copyOf(attendees);
      for (HashMap<String, String> attendee : updatedAttendeesCopy) {
        if (attendee.get("alignment").equals("Good")) {
          attendees.remove(attendee);
        }
      }

      //Create a verifiedAttendees.json file with the new attendees list
      ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
      objectWriter.writeValue(new File("verifiedAttendees.json"), attendees);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
