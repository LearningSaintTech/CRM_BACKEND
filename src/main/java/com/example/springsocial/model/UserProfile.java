package com.example.springsocial.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "age")
    private String age;

    @Column(name = "name")
    private String name;
    
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "pronouns")
    private String pronouns;

    @Column(name = "about_you", length = 500)
    private String aboutYou;
    
    @Column(name = "current_latitude")
    private Double currentLatitude;

    @Column(name = "current_longitude")
    private Double currentLongitude;
    
    @ElementCollection
    @CollectionTable(name = "profile_relationship_goals", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "relationship_goal")
    private List<RelationshipGoal> relationshipGoals;

    @ElementCollection
    @CollectionTable(name = "profile_interests", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "interest")
    private List<String> interests;

    @Lob // Use this if storing the image data directly
    private byte[] imageData;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getAboutYou() {
        return aboutYou;
    }

    public void setAboutYou(String aboutYou) {
        this.aboutYou = aboutYou;
    }

    public List<RelationshipGoal> getRelationshipGoals() {
        return relationshipGoals;
    }

    public void setRelationshipGoals(List<RelationshipGoal> relationshipGoals) {
        this.relationshipGoals = relationshipGoals;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
    public enum Gender {
        MALE,
        FEMALE,
        BISEXUAL,
        TRANSGENDER,
        QUEER,
        GAY,
        LESBIAN,
        OTHERS

    }

    public enum RelationshipGoal {
        SHORT_TERM,
        LONG_TERM,
        NEW_FRIENDS,
        DATING,
        FIGURING_OUT,
        HANGOUT
    }

	public Double getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(Double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

	public Double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(Double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

}


