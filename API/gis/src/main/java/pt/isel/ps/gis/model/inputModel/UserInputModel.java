package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInputModel {

    @JsonProperty("email")
    private String email;

    @JsonProperty("age")
    private Short age;

    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public Short getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
