package com.food.cpg.models;
import javax.persistence.*;
@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    public Long getId() {
        return userid;
    }

    public void setId(Long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
