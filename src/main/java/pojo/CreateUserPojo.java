package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserPojo {

    @JsonProperty("name")
    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }
    String name;
    @JsonProperty("email")
    public String getEmail() {
        return this.email; }
    public void setEmail(String email) {
        this.email = email; }
    String email;
    @JsonProperty("gender")
    public String getGender() {
        return this.gender; }
    public void setGender(String gender) {
        this.gender = gender; }
    String gender;
    @JsonProperty("status")
    public String getStatus() {
        return this.status; }
    public void setStatus(String status) {
        this.status = status; }
    String status;
}
