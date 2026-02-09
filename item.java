package itemapi;
public class item {

    public int id;
    public String name;
    public String description;
    public double price;

    public item(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Convert Item object to JSON string
    public String toJson() {
        return "{"
                + "\"id\":" + id + ","
                + "\"name\":\"" + name + "\","
                + "\"description\":\"" + description + "\","
                + "\"price\":" + price
                + "}";
    }
}
