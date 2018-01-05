package com.howtographql.hackernews;

public class User {
    private final int id;
    private final String name;
    private final String email;
    private final String password;

    public User(String name, String email, String password) {
        this(-1, name, email, password);
    }
    
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
}
