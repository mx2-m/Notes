package com.example.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Notes implements Sizable {
    private Date date;
    private List<Note> list;
    private int MAX_NOTES = 200;
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Notes(String idAPP) {
        setUserID(idAPP);
        list = new ArrayList<>();
    }

    public Notes(Date date, List<Note> list) {
        this.date = date;
        this.list = new ArrayList<>();;
    }



    public Notes() {
        list = new ArrayList<>();
    }


    public Date getDate() {
        return date;
    }

    public List<Note> getList() {
        return list;
    }


    public Note get(int i) {
        return  list.get(i);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setList(List<Note> list) throws IllegalArgumentException {
        if (list.size() == MAX_NOTES)
            throw new IllegalArgumentException();
        this.list = list;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "date=" + date +
                ", list=" + list +
                '}';
    }

    @Override
    public int size() {
        if (list.size() < 0)
            throw new NegativeArraySizeException();
        return list.size();
    }

    public void addNote(Note a) throws IllegalArgumentException, IllegalNameException {
       if (list.contains(a) && list.size()>0)
            throw new IllegalNameException("obveznost ze obstaja", a.getName());

       /* if (list.size() == MAX_NOTES)
            throw new IllegalArgumentException();*/
        else
            list.add(a);
    }


    public void sort1() {
        Collections.sort(list, new Comparator<Note>() {
            public int compare(Note note, Note note1) {
                return note.getName().compareTo(note1.getName());
            }
        });
    }

    public void sort2() {
        Collections.sort(list, new Comparator<Note>() {
            public int compare(Note note, Note note1) {
                return note.getDueDate().compareTo(note1.getDueDate());
            }
        });
    }

    public void remove(int position) {
        list.remove(position);
    }

    public void edit(int position,Note note)  {
        list.set(position,note);
    }


}
