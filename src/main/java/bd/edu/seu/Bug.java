package bd.edu.seu;

import java.time.LocalDate;

public class Bug {
    private String bugId;
    private String description;
    private LocalDate date;
    private String projectId;
    private String userId;


    public Bug(String bugId, String description, LocalDate date, String projectId, String userId) {
        this.bugId = bugId;
        this.description = description;
        this.date = date;
        this.projectId = projectId;
        this.userId = userId;
    }

    public String getBugId() {
        return bugId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getProjectId() {
        return projectId;
    }



    public String getUserId() {
        return userId;
    }

}