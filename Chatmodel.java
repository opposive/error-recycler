package com.thiha.hswagata.Messaging;

public class Chatmodel {
    String message,sender,reciever,timestamp;

    public Chatmodel() {
    }

    public Chatmodel(String message, String sender, String reciever, String timestamp) {
        this.message = message;
        this.sender = sender;
        this.reciever = reciever;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
