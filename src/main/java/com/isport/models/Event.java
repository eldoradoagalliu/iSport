package com.isport.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String eventName;

    @NotEmpty
    @Size(min = 3, max = 255)
    private String location;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Min(value = 2)
    private Long attendees;

    private Date eventDateTime;

    @NotEmpty
    @Size(min = 3)
    private String description;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    public Event() {

    }

    public Event(String eventName, String location, Double latitude, Double longitude, Long attendees, Date eventDateTime,
                 String description, User creator) {
        this.eventName = eventName;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attendees = attendees;
        this.eventDateTime = eventDateTime;
        this.description = description;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getAttendees() {
        return attendees;
    }

    public void setAttendees(Long attendees) {
        this.attendees = attendees;
    }

    public Date getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getNumberOfAttenders() {
        return users.size();
    }

    public String fullDateFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy HH:mm");
        String eventDate = simpleDateFormat.format(eventDateTime);
        return eventDate;
    }

    public String fullTimeFormatter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String eventTime = simpleDateFormat.format(eventDateTime);
        return eventTime;
    }

    public boolean containsUser(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) return true;
        }
        return false;
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(eventDateTime);
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(eventDateTime);
    }
}
