package bd.edu.seu;

public class User {
    private String name;
    private String id;
    private String email;
    private int numberOfDownloads;

    public User() {
    }

    public User(String name, String id, String email, int numberOfDownloads) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.numberOfDownloads = numberOfDownloads;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }
}
