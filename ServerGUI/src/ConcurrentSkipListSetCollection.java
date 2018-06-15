import org.json.simple.JSONObject;

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;


class ConcurrentSkipListSetCollection implements Serializable {

    private Comparator<Citizens> citizensComparator = new CitizenNameComporator();
    private ConcurrentSkipListSet<Citizens> types = new ConcurrentSkipListSet<>(citizensComparator);

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "1234";

    JDBCPostgre database = new JDBCPostgre(Citizens.class);

    private Scanner in = null;
    private String path = "";

    void setPath(String path) throws FileNotFoundException {

        this.path = path;

        in = new Scanner(new File(path));
    }

    boolean lower(Citizens addedCitizen){
        if (types.lower(addedCitizen) == null){
            types.add(addedCitizen);
            return true;
        }else {
            return false;
        }
    }

    boolean higher(Citizens addedCitizen){
        if (types.higher(addedCitizen) == null){
            types.add(addedCitizen);
            return true;
        }else {
            return false;
        }
    }

    boolean contains(Citizens addedCitizen){
        return types.contains(addedCitizen);
    }

    ArrayList<Citizens> removeGreater(Citizens addedCitizen){

        ArrayList<Citizens> curArray = new ArrayList<>();

        while(types.higher(addedCitizen)!= null){
            curArray.add(types.higher(addedCitizen));
            types.remove(types.higher(addedCitizen));
        }

        return curArray;
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
            database.insert(curCitizen);
            //System.out.println(in.next() + " " + in.next());
        }

        writeElements();
    }

    void writeElements(){

        System.out.println("----------------------" +
                "\n" +
                "----------------------");

        //types.forEach(Citizens -> System.out.println(Citizens.getName() + " " + Citizens.getAge()));

        try {
            ResultSet resultSet = DriverManager.getConnection(DB_URL,USER,PASS).createStatement().executeQuery( "SELECT * FROM CITIZENS;" );

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " " +
                        resultSet.getString("age") + " " +
                        resultSet.getString("localDateTime")

                        );
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        database.commit();
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
            database.insert(curElement);
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
            database.insert(curElement);
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
            Citizens citizens = new Citizens(jsonCommand.get("name").toString(), String.valueOf(jsonCommand.get("age")));
            types.add(citizens);
            database.insert(citizens);
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
        database.insert(citizen);
        writeElements();
    }

    ArrayList<Citizens> returnObjects(){
        ArrayList<Citizens> curSet = new ArrayList<>();

        curSet.addAll(types);

        return curSet;
    }

    void removeElement(Citizens selectedCitizen){
        System.out.println(types.remove(selectedCitizen));
        database.delete(selectedCitizen);
        System.out.println("removed");
    }

}
