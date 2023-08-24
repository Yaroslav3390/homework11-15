package dto;

public class CredentialsDto {

    String username;
    String password;

    public CredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CredentialsDto() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



}
