package com.isport.model;

import com.isport.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 8, max = 128)
    private String password;

    @Transient
    private String confirm;

    @Past
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Column
    private String profilePhoto;

    @Transient
    public String getProfilePhotoPath() {
        if (!(StringUtils.hasText(profilePhoto) || StringUtils.hasText(String.valueOf(id)))) return null;
        return "/profile-photos/" + id + "/" + profilePhoto;
    }

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    private Date lastLogin;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;

    @Column(updatable = false)
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> eventsCreated;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFormattedBirthdate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM yyyy");
        return simpleDateFormat.format(birthdate);
    }

    public boolean isAdmin() {
        return roles.stream()
                .map(Role::getName)
                .anyMatch(role -> role.equals(UserRole.ADMIN.getRole()));
    }
}
