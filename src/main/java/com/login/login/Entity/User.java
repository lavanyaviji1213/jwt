package com.login.login.Entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Sign_Up_Login")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="user_Id")
    private Long userId;
    @Column(name="company_No")
    private String companyNo;
    @Column(name="role_No")
    private String roleNo;
    @Column(name="responsiblity_No")
    private String responsiblityNo;
    @Column(name="resource_No")
    private String resourceNo;

    @Column(name="Boolean")
    private Boolean isDeleted=false;
    @Column(name="Email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="createdAt")
    private DateTime createdAt=new DateTime();
    @Column(name="updatedAt")
    private DateTime updatedAt=new DateTime();

    @PrePersist
    public void onSave(){
        //create at and update at
        DateTime currentDateTime= new DateTime();

            this.createdAt=currentDateTime;
            this.updatedAt=currentDateTime;
    }
    @PostPersist
    public void onUpdate(){
        DateTime currentDateTime= new DateTime();

        this.updatedAt=currentDateTime;

    }

}

