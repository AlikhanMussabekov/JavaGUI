public class InputException extends Exception{

    private String err;

    InputException(String err){
        this.err = err;
    }

    public String info(){
        return err;
    }
}
