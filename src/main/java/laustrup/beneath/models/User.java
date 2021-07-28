package laustrup.beneath.models;

import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.services.Print;
import laustrup.beneath.utilities.Liszt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;

public class User {

    private String name,password,email,dateOfBirth,description,education,work;

    private int repoId = -1;

    private Liszt<String> music, movies;
    private Liszt<Gender> gendersOfInterest;
    private Liszt<ChatRoom> chatRooms;

    private Gender gender;

    private String coverUrl;
    private BufferedImage[] images;

    // Constructor for an user from db with all attributes
    public User(String name, String password, String email, Gender gender, Liszt gendersOfInterest,
                String dateOfBirth, String description, String education, String work,
                String coverUrl,BufferedImage[] images, Liszt<String> music,Liszt<String> movies,
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
        this.coverUrl = coverUrl;
        this.images = images;
        this.music = music;
        this.movies = movies;
        this.chatRooms = chatRooms;
    }

    public User(String name, String password, String email, Gender gender, Liszt gendersOfInterest,
                String dateOfBirth, String description, String education, String work, String coverUrl) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.gendersOfInterest = gendersOfInterest;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.education = education;
        this.work = work;
        this.coverUrl = coverUrl;
        this.images = new BufferedImage[5];
        this.music = new Liszt();
        this.movies = new Liszt();
        this.chatRooms = new Liszt();
    }

    public User(String name, String password, String email, Gender gender, Liszt<Gender> gendersOfInterest,
                String dateOfBirth, String description, String coverUrl) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.gendersOfInterest = gendersOfInterest;
        this.dateOfBirth = dateOfBirth;
        this.description = description;
        this.coverUrl = coverUrl;
        this.education = new String();
        this.work = new String();
        this.images = new BufferedImage[5];
        this.music = new Liszt();
        this.movies = new Liszt();
        this.chatRooms = new Liszt();
    }

    public int getAge() {

        String[] dateSplit = dateOfBirth.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[2]);
        int day = Integer.parseInt(dateSplit[1]);

        LocalDate now = LocalDate.now();

        int amountOfYears = now.getYear() - year;
        int amountOfMonths = now.getMonthValue() - month;
        int amountOfDays = now.getDayOfMonth() - day;

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BufferedImage getCover() {
        try {
            return ImageIO.read(new File(coverUrl));
        }
        catch (Exception e) {
            new Print().writeErr("Couldn't read cover's url in user's model...");
            return null;
        }
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCover(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Liszt<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public int getRepoId() {
        return repoId;
    }
}
