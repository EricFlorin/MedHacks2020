package com.ericflorin.medhacks2020;

public class Message
{
    private String message;
    private User sender;
    private long createdAt;

    public Message(String message, User sender, long createdAt)
    {
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    // Get the message
    public String getMessage() { return message; }

    // Get the User object of the sender
    public User getSender() { return sender; }

    // Get the timestamp message was created
    public long getCreatedAt() { return createdAt; }

}
