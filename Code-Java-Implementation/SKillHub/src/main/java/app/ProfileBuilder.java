package app;

import java.util.List;

public class ProfileBuilder {
    private Profile profile;

    public ProfileBuilder() {
        this.profile = new Profile();
    }

    public ProfileBuilder setSkills(List<String> skills) {
        profile.setSkills(skills);
        return this; // allows chaining
    }

    public ProfileBuilder setExperience(String experience) {
        profile.setExperience(experience);
        return this; // allows chaining
    }

    public Profile build() {
        System.out.println("[ProfileBuilder] Built profile: " + profile);
        return profile;
    }
}
