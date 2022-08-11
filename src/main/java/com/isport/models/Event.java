package com.isport.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="Event Name is required!")
    @Size(min=4, max=30, message="Event Name must be between 4 and 30 characters")
    private String eventName;

    @NotEmpty(message="Location is required!")
    @Size(min=3, max=255, message="Location must be between more than 3 characters")
    private String location;

    @NotNull(message = "Latitude is required!")
    private Double latitude;

    @NotNull(message = "Latitude is required!")
    private Double longitude;

    @NotNull(message="The Number of Attendees is required!")
    @Min(value=2, message="There must be at least 2 attendees")
    private Long attendees;

    @NotNull(message = "Event Date is required!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    @NotEmpty(message = "Event Time is required!")
    private String time;

    @NotEmpty(message="Description is required!")
    @Size(min=3, message="Description must be at least 3 characters long")
    private String description;

    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "users_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User creator;

    @Column(updatable=false)
    @OneToMany(mappedBy="event", fetch=FetchType.LAZY)
    private List<Message> messages;

    public Event(){

    }

    public Event(String eventName, String location, Double latitude, Double longitude, Long attendees, Date date,
                 String time, String description) {
        this.eventName = eventName;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attendees = attendees;
        this.date = date;
        this.time = time;
        this.description = description;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getNumberOfAttendees(){
        return users.size();
    }

    public String fullDateFormatter(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy");
        String eventDate = simpleDateFormat.format(date);
        return eventDate + " " + time;
    }

    public boolean containsUser(Long id){
        for(User user : users){
            if(user.getId().equals(id)) return true;
        }
        return false;
    }
}