import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.io.IOException;
import java.util.Scanner;

public class JsonCommandParser {
    public void nextCommand(String cmd, ConcurrentSkipListSetCollection tree, String jsonStringIN) throws ParseException, IOException, InputException, CommandException {

        Scanner in = new Scanner(System.in);
        //String jsonStringIN = in.nextLine();

        JSONParser parser = new JSONParser();
        JSONObject jsonCommand = null;

        System.out.println(cmd + " - " + jsonStringIN);

        try {
             jsonCommand = (JSONObject) parser.parse(String.valueOf(jsonStringIN));
        }catch (ParseException e){
            //e.printStackTrace();
            //System.out.println("Incorrect JSON format...");
            throw new InputException("Incorrect JSON format...");
        }

            //{"name": "vasyap", "age": 10}
            //{"name":"бабушки", "age": 50}
            //{"name":"Alikhan", "age": 322222}
        switch(cmd){
            case "remove_greater":
                tree.remove_greater(jsonCommand);
                //tree.save();
                System.out.println("Elements successfully removed...");
                break;
            case "add_if_max":
                tree.add_if_max(jsonCommand);
                //tree.save();
                break;
            case "add_if_min":
                tree.add_if_min(jsonCommand);
                //tree.save();

                break;
            case "add":
                tree.add_element(jsonCommand);
                //tree.save();
                break;
            default:
                System.out.println("Invalid command...");
                throw new CommandException();
        }
    }
}
