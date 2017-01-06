package com.example.yeisongomez.gestordetareas;

/**
 * Created by YeisonGomez on 5/01/17.
 */

public class taskObject {
    private int id;
    private String subject;
    private int important;

    public taskObject(String subject, int important, int id) {
        this.subject = subject;
        this.important = important;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

}
