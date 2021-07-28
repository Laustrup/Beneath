package laustrup.beneath.models;

import java.time.LocalDateTime;

public class Message {

    private LocalDateTime date;
    private String content;
    private User author;

    private int repoId = -1;

    public Message(String content, User author) {
        this.date = LocalDateTime.now();
        this.content = content;
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAmountOfTimeSinceSent(Message message) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = message.getDate();

        int amountOfYears = now.getYear() - then.getYear();
        int amountOfMonths = now.getMonthValue() - then.getMonthValue();
        int amountOfDays = now.getDayOfMonth() - then.getDayOfMonth();
        int amountOfHours = now.getHour() - then.getHour();
        int amountOfMinutes = now.getMinute() - then.getMinute();
        int amountOfSeconds = now.getSecond() - then.getSecond();

        if (amountOfYears > 0 && amountOfMonths > 0) {
            return amountOfYears + " years ago";
        }
        if (amountOfMonths > 0 && amountOfDays > 0) {
            return amountOfMonths + " months ago";
        }
        if (amountOfDays > 0 && amountOfHours > 0) {
            return amountOfDays + " days ago";
        }
        if (amountOfHours > 0 && amountOfMinutes > 0) {
            return amountOfHours + " hours ago";
        }
        if (amountOfMinutes > 0 && amountOfSeconds > 0) {
            return amountOfMinutes + " minutes ago";
        }
        return amountOfSeconds + " seconds ago";
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }
}
