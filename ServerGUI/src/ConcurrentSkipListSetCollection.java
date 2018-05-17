import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;


class ConcurrentSkipListSetCollection implements Serializable {

    private Comparator<Citizens> citizensComparator = new CitizenNameComporator();
    private ConcurrentSkipListSet<Citizens> types = new ConcurrentSkipListSet<>(citizensComparator);


    private Scanner in = null;
    private String path = "";

    void setPath(String path) throws FileNotFoundException {

        this.path = path;

        in = new Scanner(new File(path));
    }

    void readElements(){
        int index = 0;
        while(in.hasNextLine()){

            Scanner dataScanner = new Scanner(in.nextLine());
            dataScanner.useDelimiter(",");
            Citizens curCitizen = new Citizens();

            while(dataScanner.hasNext()){
                String data = dataScanner.next();
                if (index == 0){
                    curCitizen.setName(data);
                }
                else if (index == 1) {
                    curCitizen.setAge(data);
                }
                index++;
            }

            index = 0;

            types.add(curCitizen);
            //System.out.println(in.next() + " " + in.next());
        }

        writeElements();
    }

    void writeElements(){

        System.out.println("----------------------" +
                "\n" +
                "----------------------");

        types.forEach(Citizens -> System.out.println(Citizens.getName() + " " + Citizens.getAge()));

        /*for(Citizens type: types){
            System.out.println(type.getName() + " " + type.getAge());
        }*/

        System.out.println("----------------------" +
                "\n" +
                "----------------------");

    }

    void save() throws IOException {

        FileOutputStream out = new FileOutputStream(path);

        byte[] buffer;
        StringBuilder curStr = new StringBuilder();
        int i = 0;
        for(Citizens type: types)
        {
            if(i!=types.size()) {
                curStr.append(type.getName()).append(",").append(type.getAge()).append("\n");
            } else
                curStr.append(type.getName()).append(",").append(type.getAge());

            i++;

            //System.out.println(curStr + "cur))))-------------------");
        }

        //System.out.println(buffer);

        buffer = curStr.toString().getBytes();
        out.write(buffer, 0, buffer.length);

        out.close();
        //in.close();

    }

    void remove_greater(JSONObject jsonCommand) {
        System.out.println(jsonCommand.get("name") + " " + jsonCommand.get("age"));

        Citizens curElement = new Citizens(jsonCommand.get("name").toString(), String.valueOf(jsonCommand.get("age")));


        types.removeIf(curCitizen -> citizensComparator.compare(curCitizen, curElement) > 0);


        writeElements();
    }

    void add_if_max(JSONObject jsonCommand) {
        //System.out.println(2);
        System.out.println(jsonCommand.get("name") + " " + jsonCommand.get("age"));

        Citizens curElement = new Citizens(jsonCommand.get("name").toString(), String.valueOf(jsonCommand.get("age")));

        if (types.higher(curElement)== null){
            types.add(curElement);
            System.out.println("Element successfully added...");
        }
        else
            System.out.println("Element is not max...");


        //System.out.println(types.higher(curElement));

        writeElements();
    }

    void add_if_min(JSONObject jsonCommand) {
        //System.out.println(3);
        System.out.println(jsonCommand.get("name") + " " + jsonCommand.get("age"));

        Citizens curElement = new Citizens(jsonCommand.get("name").toString(), String.valueOf(jsonCommand.get("age")));

        if (types.lower(curElement)== null){
            types.add(curElement);
            System.out.println("Element successfully added...");
        }
        else
            System.out.println("Element is not min...");

        writeElements();
    }

    void add_element(JSONObject jsonCommand) {
        //System.out.println(4);
        //System.out.println(jsonCommand.get("name") + " " + jsonCommand.get("age"));

        try {
            types.add(new Citizens(jsonCommand.get("name").toString(), String.valueOf(jsonCommand.get("age"))));
            System.out.println("Element successfully added...");
            writeElements();
        }catch (ClassCastException e){
            System.out.println("Incorrect element types...");
        }catch (NullPointerException e){
            System.out.println("Error...");
        }
    }

    void add_element(Citizens citizen){
        //System.out.println(citizen.getName());
        types.add(citizen);
        writeElements();
    }

    ArrayList<Citizens> returnObjects(){
        ArrayList<Citizens> curSet = new ArrayList<>();

        curSet.addAll(types);

        return curSet;
    }

    void removeElement(Citizens selectedCitizen){
        System.out.println(types.remove(selectedCitizen));
        System.out.println("removed");
    }

}
