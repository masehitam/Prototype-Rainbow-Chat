package me.masehitam.prototypechatrainbow.model;

/**
 * Created by Wiwid Widyanto on 9/8/2017.
 */

public class ChatFeed {
    private String name, lastMessage, time;

    public ChatFeed(String name, String lastMessage, String time) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
