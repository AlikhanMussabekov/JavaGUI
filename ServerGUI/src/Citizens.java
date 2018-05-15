import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Citizens implements Serializable{
    private String name;
    private int age;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Date date = new Date();
    private String city = "Санкт-Петербург";
    private String err = "";

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public Citizens(){

    }

    public Citizens(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Citizens(String name, String age) {
        this.name = name;
        this.age = Integer.parseInt(age);
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }

    public String getCity(){
        return city;
    }

    public void printInfo(){
        if(err!="")
            System.out.printf("%s, %d, %s, %s \n", getName(), getAge(), getCity(), getDate());
        else
            System.out.println(err);
    }
}
