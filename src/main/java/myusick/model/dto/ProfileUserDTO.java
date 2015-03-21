package myusick.model.dto;

import java.util.List;

/**
 * Created by david on 20/03/2015.
 */
public class ProfileUserDTO {
    
    private String name;
    private String description;
    private String avatar;
    private String[] skills;
    private String[] tags;
    private List<ShortGroupDTO> groups;
    private List<PublicationDTO> publications;

    public ProfileUserDTO(String name, String description, String avatar, String[] skills, String[] tags, List<ShortGroupDTO> groups, List<PublicationDTO> publications) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.skills = skills;
        this.tags = tags;
        this.groups = groups;
        this.publications = publications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<ShortGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<ShortGroupDTO> groups) {
        this.groups = groups;
    }

    public List<PublicationDTO> getPublications() {
        return publications;
    }

    public void setPublications(List<PublicationDTO> publications) {
        this.publications = publications;
    }
}
