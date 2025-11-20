package app;

import java.util.List;

public class Profile {
    private List<String> skills;
    private String experience;

    // package-private constructor to force builder usage
    Profile() {}

    public List<String> getSkills() { return skills; }
    public String getExperience() { return experience; }

    // setters package-private, used by builder only
    void setSkills(List<String> skills) { this.skills = skills; }
    void setExperience(String experience) { this.experience = experience; }

    @Override
    public String toString() {
        return "Profile{" +
                "skills=" + skills +
                ", experience='" + experience + '\'' +
                '}';
    }
}

