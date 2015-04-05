package myusick.model.DTO;

import myusick.persistence.VO.Publicacion;
import myusick.persistence.VO.Publicante;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class ProfileDTO implements Serializable {
    private String name;
    private String description;
    private String avatar;
    private String[] skills;
    private String[] tags;
    private Publisher[] members;
    private Publisher[] groups;
    private Publications[] publications;

    public ProfileDTO(){}
    public ProfileDTO(String name, String description, String avatar, String[] skills,
                      String[] tags, Publisher[] members, Publisher[] groups, Publications[] publications) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.skills = skills;
        this.tags = tags;
        this.members = members;
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

    public Publisher[] getMembers() {
        return members;
    }

    public void setMembers(Publisher[] members) {
        this.members = members;
    }

    public Publisher[] getGroups() {
        return groups;
    }

    public void setGroups(Publisher[] groups) {
        this.groups = groups;
    }

    public Publications[] getPublications() {
        return publications;
    }

    public void setPublications(Publications[] publications) {
        this.publications = publications;
    }

    public boolean isAGroup(){
        return groups.length!=0;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", avatar='" + avatar + '\'' +
                ", skills=" + Arrays.toString(skills) +
                ", tags=" + Arrays.toString(tags) +
                ", members=" + Arrays.toString(members) +
                ", groups=" + Arrays.toString(groups) +
                ", publications=" + Arrays.toString(publications) +
                '}';
    }
}
