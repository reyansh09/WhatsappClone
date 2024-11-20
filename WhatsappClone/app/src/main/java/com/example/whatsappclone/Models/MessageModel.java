package com.example.whatsappclone.Models;

public class  MessageModel {

    String uId, Message, MessageId;
    Long timeStamp;

    public MessageModel(String uId, String message, String messageId, Long timeStamp) {
        this.uId = uId;
        Message = message;
        this.timeStamp = timeStamp;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public MessageModel(String uId, String message) {
        this.uId = uId;
        Message = message;
    }

    public MessageModel() {
    }
}

