package pt.isel.ps.gis.model.inputModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterUserInputModel {

    @JsonProperty("user-username")
    private String username;

    @JsonProperty("user-email")
    private String email;

    @JsonProperty("user-age")
    private Short age;

    @JsonProperty("user-name")
    private String name;

    @JsonProperty("user-password")
    private String password;

    public String getUsername() {
        return username;
    }

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
