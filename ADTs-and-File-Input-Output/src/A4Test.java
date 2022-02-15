import java.io.IOException;
import java.util.*;
public class A4Test {

    public static ArrayList<String> thing = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        thing.add("HSJFNSKJFNSFN");
//        thing.add("Testing 123");
//        System.out.println(thing);

        ElectronicStore store = ElectronicStore.loadFromFile("store.dat");

        assert store != null;
        System.out.println(store.getCustomers());
        }
    }

