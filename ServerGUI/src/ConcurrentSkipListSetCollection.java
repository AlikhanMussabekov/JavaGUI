import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;


class ConcurrentSkipListSetCollection implements Serializable {

    Comparator<Citizens> citizensComparator = new CitizenNameComporator();
    ConcurrentSkipListSet<Citizens> types = new ConcurrentSkipListSet<>(citizensComparator);


    Scanner in = null;
    static FileOutputStream out = null;
    Scanner dataScanner = null;
    String path = "";

    public void setPath(String path) throws FileNotFoundException {

        this.path = path;

        in = new Scanner(new File(path));
    }

    public void readElements(){

        int index = 0;
        while(in.hasNextLine()){

            dataScanner = new Scanner(in.nextLine());
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
        in.close();
    }

    public void writeElements(){

        System.out.println("----------------------" +
                "\n" +
                "----------------------");

        types.stream().forEach(Citizens -> System.out.println(Citizens.getName() + " " + Citizens.getAge()));

        /*for(Citizens type: types){
            System.out.println(type.getName() + " " + type.getAge());
        }*/

        System.out.println("----------------------" +
                "\n" +
                "----------------------");

    }

    public void save() throws IOException {

        out = new FileOutputStream(path);

        byte[] buffer;
        String curStr = "";
        int i = 0;
        for(Citizens type: types)
        {
            if(i!=types.size()) {
                curStr += type.getName() + "," + type.getAge() + "\n";
            } else
                curStr += type.getName() + "," + type.getAge();

            i++;

            //System.out.println(curStr + "cur))))-------------------");
        }

        //System.out.println(buffer);

        buffer = curStr.getBytes();
        out.write(buffer, 0, buffer.length);

        out.close();

    }

    public void remove_greater(JSONObject jsonCommand) {
        //System.out.println(1);
        System.out.println(jsonCommand.get("name") + " " + jsonCommand.get("age"));

        Citizens curElement = new Citizens(jsonCommand.get("name").toString(), String.valueOf(jsonCommand.get("age")));

        //SortedSet<Citizens> setGreate = types.tailSet(curElement,);

        for (Iterator<Citizens> iterator = types.iterator(); iterator.hasNext();){
            Citizens curCitizen = iterator.next();

            if (citizensComparator.compare(curCitizen, curElement) > 0){
                iterator.remove();
            }
        }

        //System.out.println(types.ceiling(curElement).getName() + " " + types.ceiling(curElement).getAge());

        writeElements();
    }

    public void add_if_max(JSONObject jsonCommand) {
        //System.out.println(2);
        System.out.println(jsonCommand.get("name") + " " + jsonCommand.get("age"));

        Citizens maxElement = types.last();
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

    public void add_if_min(JSONObject jsonCommand) {
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

    public void add_element(JSONObject jsonCommand) {
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

    public ArrayList<Citizens> returnObjects(){
        ArrayList<Citizens> curSet = new ArrayList<>();

        for(Citizens type: types){
            curSet.add(type);
        }

        return curSet;
    }
}
