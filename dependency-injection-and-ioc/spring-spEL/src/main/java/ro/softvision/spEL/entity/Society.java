package ro.softvision.spEL.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Society {

    private String name;

    public static final String ADVISORS = "advisors";
    public static final String PRESIDENT = "president";

    private List<Inventor> members = new ArrayList<>();

    private Map<String, List<Inventor>> officers = new HashMap<>();

    public List getMembers() {
        return members;
    }

    public Map getOfficers() {
        return officers;
    }

    public String getName() {
        return name;
    }

    public void addInventor(Inventor inventor) {
        members.add(inventor);
        officers.put(ADVISORS,List.of(inventor));
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMember(String name) {
        for (Inventor inventor : members) {
            if (inventor.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}