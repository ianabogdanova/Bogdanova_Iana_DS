public class User {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    public User(String name, String email, String password, String confirmPassword){
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
