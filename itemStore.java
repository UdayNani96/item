package itemapi;

import java.util.ArrayList;
import java.util.List;

public class itemStore {

    // Acts like a database
    public static List<item> items = new ArrayList<>();

    // Find item by ID
    public static item findById(int id) {
        for (item item : items) {
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }
}
