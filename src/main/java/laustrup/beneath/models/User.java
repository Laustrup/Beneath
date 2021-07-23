package laustrup.beneath.models;

import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.utilities.Liszt;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class User {

    private String name,password,email,description,education,work;

    private Liszt<String> music, movies;
    private Liszt<Gender> gendersOfInterest;
    private Liszt<ChatRoom> chatRooms;

    private Gender gender;

    private Date dateOfBirth;

    private BufferedImage profilePicture;
    private BufferedImage[] images;

    // Constructor for an user from db with all attributes
    public User(String name, String password, String email, Gender gender, Liszt gendersOfInterest,
                Date dateOfBirth, String description, String education, String work,
                BufferedImage profilePicture,BufferedImage[] images, Liszt<String> music,Liszt<String> movies,
                Liszt<ChatRoom> chatRooms) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.gendersOfInterest = gendersOfInterest;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.education = education;
        this.work = work;
        this.profilePicture = profilePicture;
        this.images = images;
        this.music = music;
        this.movies = movies;
        this.chatRooms = chatRooms;
    }

    public User(String name, String password, String email, Gender gender, Liszt gendersOfInterest,
                Date dateOfBirth, String description, String education, String work, BufferedImage profilePicture) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.gendersOfInterest = gendersOfInterest;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.education = education;
        this.work = work;
        this.profilePicture = profilePicture;
        this.images = new BufferedImage[5];
        this.music = new Liszt();
        this.movies = new Liszt();
        this.chatRooms = new Liszt();
    }

    public User(String name, String password, String email, Gender gender, Liszt<Gender> gendersOfInterest,
                Date dateOfBirth, String description, BufferedImage profilePicture) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.gendersOfInterest = gendersOfInterest;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.profilePicture = profilePicture;
        this.education = new String();
        this.work = new String();
        this.images = new BufferedImage[5];
        this.music = new Liszt();
        this.movies = new Liszt();
        this.chatRooms = new Liszt();
    }

    public int getAge() {
        LocalDate now = LocalDate.now();

        int amountOfYears = now.getYear() - dateOfBirth.getYear();
        int amountOfMonths = now.getMonthValue() - dateOfBirth.getMonth();
        int amountOfDays = now.getDayOfMonth() - dateOfBirth.getDay();

        if (amountOfMonths > 0 && amountOfDays > 0) {
        return amountOfYears;
        }
        return -1;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public void addImage(BufferedImage newImage) {
        for (int i = 0; i < 5; i++) {
            if (images[i] == null) {
                images[i] = newImage;
                break;
            }
        }
    }

    public void removeImage(int index) {
        for (int i = 0; i < 5; i++) {
            if (i == index) {
                images[i] = null;
                break;
            }
        }
    }

    public Liszt getMusic() {
        return music;
    }

    public void addMusicTrack(String track) {
        music.add(track);
    }

    public void removeMusicTrack(String track) {
        music.remove(track);
    }

    public Liszt getMovies() {
        return movies;
    }

    public void addMovie(String movie) {
        movies.add(movie);
    }

    public void removeMovie(String movie) {
        movies.remove(movie);
    }

    public Liszt<Gender> getGendersOfInterest() {
        return gendersOfInterest;
    }

    public void addGenderOfInterest(Gender gender) {
        gendersOfInterest.add(gender);
    }

    public void removeGenderOfInterest(Gender gender) {
        gendersOfInterest.remove(gender);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BufferedImage getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(BufferedImage profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Liszt<ChatRoom> getChatRooms() {
        return chatRooms;
    }
}
