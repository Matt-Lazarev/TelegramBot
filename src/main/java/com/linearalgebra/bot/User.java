package com.linearalgebra.bot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    private Long id;

    private Long chatId;
    private Integer stateId;
    private String name;
    private boolean isAdmin;
    private boolean isNew;
    private List<Integer> options = new ArrayList<>();

    public User() {
    }

    public User(Long chatId, Integer stateId) {
        this.chatId = chatId;
        this.stateId = stateId;
    }

    public User(Long chatId, Integer stateId, boolean isAdmin) {
        this.chatId = chatId;
        this.stateId = stateId;
        this.isAdmin = isAdmin;
    }

    public void addOption(int option){
        options.add(option);
    }

    public int getLastOption(){
        return options.get(options.size()-1);
    }

    public List<Integer> getAllOptions(){
        return options;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
