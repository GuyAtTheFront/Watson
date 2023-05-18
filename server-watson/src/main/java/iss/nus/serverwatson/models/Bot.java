package iss.nus.serverwatson.models;

public class Bot {
    private Long id;
    private String username;
    private String imageUrl;
    private String token;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return "Bot [id=" + id + ", username=" + username + ", imageUrl=" + imageUrl + ", token=" + token + "]";
    }

}
