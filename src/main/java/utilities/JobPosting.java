package utilities;

import java.util.List;

public class JobPosting  {
    private final int id;
    private final String title;
    private final String company;
    private final String type;
    private final String salary;
    private final String description;
    private final String posted_by;
    private final List<String> requirements;


    public JobPosting(int id, String title, String company, String type, String salary, String description, String posted_by,List<String> requirements) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.type = type;
        this.salary = salary;
        this.description = description;
        this.posted_by = posted_by;
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

    public String getPosted_by(){
        return  posted_by;
    }
    public List<String> getRequirements() {
        return requirements;
    }

}