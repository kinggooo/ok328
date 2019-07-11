package club.ok328.www.srv.config;

public class MyConfig {
    private String password;

    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        System.out.println("setter username");
        this.username = username;
    }

    public void init() {
        System.out.println("call init");
        System.out.println(username);
    }
}
