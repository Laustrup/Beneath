package laustrup.beneath.models;

import laustrup.beneath.utilities.Liszt;

public class ChatRoom {

    private boolean viewRequested;
    private boolean viewAccepted;

    private Liszt<Message> inbox;
    private Liszt<User> users;

    private int repoId = -1;

    public ChatRoom(User firstParticipant, User secondParticipant) {
        Object[] participants = {firstParticipant, secondParticipant};
        users = new Liszt(true,participants);

        viewRequested = false;
        viewAccepted = false;

        inbox = new Liszt();
    }

    public boolean isViewRequested() {
        return viewRequested;
    }

    public void changeViewRequest() {
        if (viewRequested) {
            viewRequested = false;
        }
        else {
            viewRequested = true;
        }
    }

    public boolean isViewAccepted() {
        return viewAccepted;
    }

    public void acceptView() {
        viewAccepted = true;
    }

    // Puts an message into the inbox Liszt, and returns false if the author's email doesn't exist.
    public void addMessageToInbox(String content, String authorEmail) {
        inbox.add(new Message(content, users.get(authorEmail)));
    }

    public boolean isUserAParticipantOfChatroom(User participant) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) == participant) {
                return true;
            }
        }
        return false;
    }

    public User getParticipantOfChatroom(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (((User) users.get(i)).getEmail().equals(email)) {
                return (User) users.get(i);
            }
        }
        return null;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public int getRepoId() {
        return repoId;
    }

    public int getAmountOfMessages() {
        return inbox.length();
    }
}
