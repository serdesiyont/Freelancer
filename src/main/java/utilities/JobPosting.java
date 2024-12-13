package utilities;

import java.util.List;

public class JobPosting {
    private int id;
    private String title;
    private String company;
    private String type;
    private String salary;
    private String description;
    private List<String> requirements;


    public JobPosting(int id, String title, String company, String type, String salary, String description, List<String> requirements) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.type = type;
        this.salary = salary;
        this.description = description;
        this.requirements = requirements;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getType() {
        return type;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRequirements() {
        return requirements;
    }
}