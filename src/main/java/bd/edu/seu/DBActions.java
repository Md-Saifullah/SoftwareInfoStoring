package bd.edu.seu;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBActions {
    public DBActions() {
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/softwareProject", "root", "Incorrect");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public boolean createUser(User user, String password, Boolean isDeveloper, Boolean isGuest) {
        System.out.println("createUser");
        String query1, query2, query3;
        query1 = String.format("insert into userId values('%s','%s','%s',%d);",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNumberOfDownloads());
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query1);
            if (isGuest) {
                query2 = String.format("insert into guest_user values('%s');", user.getId());
                statement.executeUpdate(query2);
            } else {
                query2 = String.format("insert into subscribed_user values('%s','%s','%s');",
                        user.getId(),
                        LocalDate.now(),
                        password);
                statement.executeUpdate(query2);
                if (isDeveloper) {
                    query3 = String.format("insert into developer values('%s');", user.getId());
                } else {
                    query3 = String.format("insert into non_developer values('%s');", user.getId());
                }
                statement.executeUpdate(query3);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public String getMax(String tableName, String columnName, String condition) {
        System.out.println("getMax");
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = String.format("SELECT MAX(%s) AS %s from %s;", columnName, condition, tableName);
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("getMax finish");
            resultSet.next();
            if (resultSet.getString(columnName) == null) {
                System.out.println("if");
                return "";
            } else {
                System.out.println("else");
                return resultSet.getString(columnName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("getMax Failed");
        return null;
    }


    public String createId(String tableName, String columnName, String condition) {
        System.out.println("createId start");
        String id = getMax(tableName, columnName, condition);
        System.out.println(id);
        if (id.equals("")) {
            id = "00000001";
        } else {
            id = String.valueOf(Integer.parseInt(id) + 1);
            int i = 0;
            StringBuilder prefix = new StringBuilder();
            while (i < 8 - id.length()) {
                prefix.append("0");
                i++;
            }
            id = prefix + id;
        }
        System.out.println("createId finish");
        System.out.println(id);
        return id;
    }


    public Boolean isSubscriber(String id, String password) {
        System.out.println("isSubscriber");
        Connection connection = getConnection();
        String query = String.format("select * from subscribed_user where user_id='%s';", id);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public Boolean isGuest(String id) {
        System.out.println("isGuest");
        return getBoolean(id, "guest_user");
    }

    public Boolean isDeveloper(String id) {
        System.out.println("isDeveloper");
        return getBoolean(id, "developer");
    }

    private Boolean getBoolean(String id, String tableName) {
        Connection connection = getConnection();
        String query = String.format("select * from %s where user_id='%s';", tableName, id);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Project> getAllProject() {
        System.out.println("getAllProject");
        List<Project> projectList = new ArrayList<>();
        String query = "select * from project;";
        return getProjects(projectList, query);
    }

    public List<Project> getProjectByUserId(String userId) {
        System.out.println("getProjectByUserId");
        List<Project> projectList = new ArrayList<>();
        String query = String.format("select * from project where owned_by='%s';", userId);
        return getProjects(projectList, query);
    }

    private List<Project> getProjects(List<Project> projectList, String query) {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("project_id");
                char status = resultSet.getString("status").charAt(0);
                String ownedBy = resultSet.getString("owned_by");
                String description = resultSet.getString("description");
                int downloaded = resultSet.getInt("downloaded");
                int numberOfBugReport = resultSet.getInt("no_of_bug_report");

                projectList.add(new Project(id, status, ownedBy, description, downloaded, numberOfBugReport));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return projectList;
    }

    public Project getProjectByProjectId(String projectId) {
        System.out.println("getProjectByProjectId");
        String query = String.format("select * from project where project_id='%s';", projectId);
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String Id = resultSet.getString("project_id");
                char status = resultSet.getString("status").charAt(0);
                String ownedBy = resultSet.getString("owned_by");
                String description = resultSet.getString("description");
                int downloaded = resultSet.getInt("downloaded");
                int numberOfBugReport = resultSet.getInt("no_of_bug_report");


                return new Project(Id, status, ownedBy, description, downloaded, numberOfBugReport);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return new Project();
    }

    public List<User> getAllUser() {
        System.out.println("getAllUser");
        List<User> userList = new ArrayList<>();
        String query = "select * from user;";
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("user_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int noOfDownloads = resultSet.getInt("no_of_downloads");
                userList.add(new User(name, id, email, noOfDownloads));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public List<String> getAllProjectNames() {
        List<String> nameList = new ArrayList<>();
        List<Project> projectList = getAllProject();
        for (Project project : projectList) {
            nameList.add(project.getProjectId());
        }
        return nameList;
    }


    public User getUserByUserId(String userId) {
        System.out.println("getUserByUserId");
        String query = String.format("select * from user where user_id='%s';", userId);
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String id = resultSet.getString("user_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int noOfDownloads = resultSet.getInt("no_of_downloads");
                return new User(name, id, email, noOfDownloads);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateTable(String tableName, String columnName, int value, String id, String condition) {
        System.out.println("updateTable");
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = String.format("update %s set %s=%d where %s='%s';",
                    tableName,
                    columnName,
                    value,
                    condition,
                    id);
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateDownload(String userId, String projectId) {
        System.out.println("updateDownload");
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String query1 = String.format("insert into download values('%s','%s');", projectId, userId);
            statement.executeUpdate(query1);
            User user = getUserByUserId(userId);
            Project project = getProjectByProjectId(projectId);
            updateTable("userId",
                    "no_of_downloads",
                    user.getNumberOfDownloads() + 1,
                    userId,
                    "user_id");
            updateTable("project",
                    "downloaded",
                    project.getDownloaded() + 1,
                    projectId,
                    "project_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Boolean createBugReport(Bug bug) {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = String.format("insert into bug values('%s','%s','%s','%s','%s');",
                    bug.getProjectId(),
                    bug.getBugId(),
                    bug.getDescription(),
                    bug.getDate(),
                    bug.getUserId());
            statement.executeUpdate(query);
            Project project = getProjectByProjectId(bug.getProjectId());
            updateTable("project",
                    "no_of_bug_report",
                    project.getNumberOfBugReport() + 1,
                    bug.getProjectId(),
                    "project_id");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean uploadProject(Project project, String category, String depends) {
        System.out.println("uploadProject");
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String query1 = String.format("insert into project values('%s','%s','%s','%s',%d,%d);",
                    project.getProjectId(),
                    project.getStatus(),
                    project.getDescription(),
                    project.getOwnedBy(),
                    project.getDownloaded(),
                    project.getNumberOfBugReport());
            String query2 = String.format("insert into transaction values('%s','%s','%s','Upload');",
                    project.getProjectId(),
                    createId("transaction", "transaction_id", "transaction_id"),
                    LocalDate.now());
            String query4 = String.format("insert into category values('%s','%s');",
                    project.getProjectId(),
                    category);
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            if (depends != null) {
                String query3 = String.format("insert into depends values('%s','%s');",
                        project.getProjectId(),
                        depends);
                statement.executeUpdate(query3);
            }
            statement.executeUpdate(query4);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean createUpdate(Patch patch, String projectId) {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String transactionId = createId("transaction", "transaction_id", "transaction_id");
            String query1 = String.format("insert into transaction values('%s','%s','%s','Update');",
                    projectId,
                    transactionId,
                    LocalDate.now());
            String query2 = String.format("insert into patch values('%s','%s','%s','%s','%s','%s',%d);",
                    projectId,
                    transactionId,
                    patch.getId(),
                    patch.getName(),
                    patch.getStatus(),
                    patch.getDescription(),
                    patch.getType());
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Bug getBugByBugId(String bugId) {
        System.out.println("getBugByBugId");
        String query = String.format("select * from bug where bug_id='%s';", bugId);
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String projectId = resultSet.getString("project_id");
                String description = resultSet.getString("description");
                String userId = resultSet.getString("user_id");
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                return new Bug(bugId, description, date, projectId, userId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Bug> getAllBugs() {
        System.out.println("getAllBugs");
        List<Bug> bugList = new ArrayList<>();
        String query = "select * from bug;";
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String bugId=resultSet.getString("bug_id");
                String userId = resultSet.getString("user_id");
                String projectId = resultSet.getString("project_id");
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                String description = resultSet.getString("description");
                bugList.add(new Bug(bugId,description, date,projectId,userId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bugList;
    }
    public List<Transaction> getAllTransactions() {
        System.out.println("getAllTransactions");
        List<Transaction> transactionList = new ArrayList<>();
        String query = "select * from transaction;";
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String transactionId = resultSet.getString("transaction_id");
                String projectId = resultSet.getString("project_id");
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                String transactionType = resultSet.getString("transaction_type");
                transactionList.add(new Transaction(transactionId,projectId, transactionType, date));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactionList;
    }
}

