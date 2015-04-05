package myusick.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
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
    private ArrayList<PublisherDTO> members;
    private ArrayList<PublisherDTO> groups;
    private ArrayList<PostDTO> publications;

    public ProfileDTO(){}
    
    public ProfileDTO(String name, String description, String avatar, String[] skills,
                      String[] tags, ArrayList<PublisherDTO> members, ArrayList<PublisherDTO> groups,
                      ArrayList<PostDTO> publications) {
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

    public ArrayList<PublisherDTO> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<PublisherDTO> members) {
        this.members = members;
    }

    public ArrayList<PublisherDTO> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<PublisherDTO> groups) {
        this.groups = groups;
    }

    public ArrayList<PostDTO> getPublications() {
        return publications;
    }

    public void setPublications(ArrayList<PostDTO> publications) {
        this.publications = publications;
    }

    public boolean isAGroup(){
        return members!=null && members == null;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", avatar='" + avatar + '\'' +
                ", skills=" + Arrays.toString(skills) +
                ", tags=" + Arrays.toString(tags) +
                ", members=" + members.toString() +
                ", groups=" + groups.toString() +
                ", publications=" + publications.toString() +
                '}';
    }
}
