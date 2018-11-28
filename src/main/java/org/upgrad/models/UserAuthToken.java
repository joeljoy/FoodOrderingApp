package org.upgrad.models;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/*
 * UserAuthToken model class contain all the attributes to be mapped to all the fields in the user_auth_token table in the database.
 * Annotations are used to specify all the constraints to the table and table-columns in the database.
 * Here getter, setter and constructor are defined for this model class.
 */
@Entity
@Table(name="user_auth_token")
public class UserAuthToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "access_token")
    @NotNull
    private String accessToken;

    @Column(name = "login_at")
    @NotNull
    private Date loginAt;

    @Column(name = "logout_at")
    private Date logoutAt;

    public UserAuthToken() {
    }

    public UserAuthToken(User user, @NotNull String accessToken, @NotNull Date loginAt) {
        this.user = user;
        this.accessToken = accessToken;
        this.loginAt = loginAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    public Date getLogoutAt() {
        return logoutAt;
    }

    public void setLogoutAt(Date logoutAt) {
        this.logoutAt = logoutAt;
    }

}
