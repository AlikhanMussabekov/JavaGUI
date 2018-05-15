import java.io.Serializable;
import java.util.Comparator;

class CitizenNameComporator implements Comparator<Citizens>, Serializable{
    @Override
    public int compare(Citizens o1, Citizens o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
