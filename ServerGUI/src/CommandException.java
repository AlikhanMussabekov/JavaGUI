public class CommandException extends Exception{
    CommandException(){}

    public String info(){
        return "Incorrect command...";
    }
}
