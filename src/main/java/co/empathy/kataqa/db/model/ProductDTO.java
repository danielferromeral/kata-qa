package co.empathy.kataqa.db.model;

public class ProductDTO {

    private int id;
    private String name;
    private String type;
    private String color;
    private String size;

    public ProductDTO(int id, String name, String type, String color, String size) {
        setId(id);
        setName(name);
        setType(type);
        setColor(color);
        setSize(size);
    }

    public String toString() {
        return getId() + " | " + getName() + " | " + getType() + " | " + getColor() + " | " + getSize();
    }

    public String toCSV() {
        return getName() + "," + getType() + "," + getColor() + "," + getSize();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
