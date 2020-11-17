package com.example.lib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyMainClass {

    public static void main(String[] args) {

        Date date = new Date();
        List<Note> list = new ArrayList<>();

        try {

            Note a = new Note("obaveza", "narediti nekaj", date);
            Note b = new Note("obaveza 2", null, date);
            Note d = new Note("obaveza 2", null, date);
            Note c = new Note("obaveza 3", null, date);

            list.add(a);
            //list.add(b);


            //System.out.println(list.size());
            Notes notes1 = new Notes(date, null);
            Notes notes = new Notes(date, list);
            //notes.addNote(a);
            notes.addNote(b);
            notes.sort2();
            System.out.println(notes.toString());
        } catch (NullPointerException e) {
            System.out.println("Vpisite ime obveznosti");
        } catch (IllegalArgumentException exception) {
            System.out.println("Prevec obveznosti"); // Catch expected IllegalArgumentExceptions.

        } catch (NegativeArraySizeException a) {
            System.out.println("negativan argument");
        } catch (IllegalNameException m) {
            System.out.println("napaka");
        }


    }
}
