package myusick.model.dto;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class ProfileDTO implements Serializable {
    private boolean type;
    private String name;
    private String description;
    private long year;
    private String avatar;
    private ArrayList<String> skills;
    private ArrayList<String> tags;
    private ArrayList<PublisherDTO> members;
    private ArrayList<PublisherDTO> groups;
    private ArrayList<PublicationsDTO> publications;

    public ProfileDTO(){}
    
    public ProfileDTO(String name, String description, String avatar, ArrayList<String> skills,
                      ArrayList<String> tags, ArrayList<PublisherDTO> members, ArrayList<PublisherDTO> groups,
                      ArrayList<PublicationsDTO> publications) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
        this.skills = skills;
        this.tags = tags;
        this.members = members;
        this.groups = groups;
        this.publications = publications;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
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

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
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

    public ArrayList<PublicationsDTO> getPublications() {
        return publications;
    }

    public void setPublications(ArrayList<PublicationsDTO> publications) {
        this.publications = publications;
    }

    public boolean isAGroup(){
        return members!=null && groups == null;
    }

    @Override
    public String toString() {
        String provMembers;
        if(members == null || members.isEmpty()) provMembers="";
        else provMembers = members.toString();
        String provGroups;
        if(groups == null || groups.isEmpty()) provGroups="";
        else provGroups = groups.toString();
        String resultado = "ProfileDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", avatar='" + avatar + '\'' +
                ", skills=" + skills.toString() +
                ", tags=" + tags.toString() +
                ", members=" + provMembers +
                ", groups=" + provGroups +
                ", publications=" + publications.toString() +
                '}';
        return resultado;
    }
}
