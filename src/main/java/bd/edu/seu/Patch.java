package bd.edu.seu;

public class Patch {
    private String id;
    private String name;
    private String status;
    private String description;
    private int type;

    public Patch() {
    }

    public Patch(String id, String name, String status, String description, int type) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }
}
