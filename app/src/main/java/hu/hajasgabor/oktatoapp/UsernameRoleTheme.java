package hu.hajasgabor.oktatoapp;

public class UsernameRoleTheme {
    private String username;
    private String role;
    private boolean darktheme;

    public UsernameRoleTheme(String u, String r, boolean dt) {
        username = u;
        role = r;
        darktheme = dt;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isDarktheme() {
        return darktheme;
    }
}
