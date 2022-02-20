package bd.edu.seu;

public class Project {
    private String projectId;
    private char status;
    private String ownedBy;
    private String description;
    private int downloaded;
    private int numberOfBugReport;

    public Project() {}

    public Project(String projectId, char status, String ownedBy, String description, int downloaded, int numberOfBugReport) {
        this.projectId = projectId;
        this.status = status;
        this.ownedBy = ownedBy;
        this.description = description;
        this.downloaded = downloaded;
        this.numberOfBugReport = numberOfBugReport;
    }

    public String getProjectId() {
        return projectId;
    }

    public char getStatus() {
        return status;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public int getNumberOfBugReport() {
        return numberOfBugReport;
    }


    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", status=" + status +
                ", ownedBy='" + ownedBy + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
