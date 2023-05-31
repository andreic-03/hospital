package org.patients.models;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "credentials")
public class Credential {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    public Credential() {}

    private Credential(Builder source) {
    	this.id = source.id;
    	this.userId = source.userId;
    	this.username = source.username;
    	this.password = source.password;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder {
    	protected Long id = null;
        protected Long userId;
        protected String username;
        protected String password;

        /**
         * Constructor with all required (not nullable) fields from {@link Credential}
         * 
         * @param userId the id of the user whose crdentials are stored
         * @param username the user name
         * @param password the password
         */
        public Builder(Long userId, String username, String password) {
			this.userId = userId;
			this.username = username;
			this.password = password;
		}

        public Credential build() {
        	return new Credential(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(id, that.id) &&
                userId.equals(that.userId) &&
                username.equals(that.username) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, username, password);
    }
}
