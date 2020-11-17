package com.example.lib;

import java.time.LocalDateTime;
import java.util.Date;

public class Note implements java.lang.Comparable<Note> {
    public String name;
    public String content;
    private Date dueDate;

    public Note(String name, String content, Date dueDate) throws NullPointerException {
        if (name == null)
            throw new NullPointerException();
        this.name = name;
        this.content = content;
        this.dueDate = dueDate;
    }

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", finish=" + dueDate +
                '}';
    }

    @Override
    public int compareTo(Note note) {
        if (dueDate.compareTo(note.getDueDate()) == 0)
            return 0;
        else if (dueDate.compareTo(note.getDueDate()) > 0)
            return 1;
        return -1;
    }
}
