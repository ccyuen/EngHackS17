package com.google.sample.cloudvision;

import java.util.*;
import java.io.*;
import java.text.*;

class Event implements java.io.Serializable
{
    public Date datetime;
    public int moneySpent;
    public String location;
    public Event(String location, Date datetime, int moneySpent) {
        this.location = location;
        this.datetime = datetime;
        this.moneySpent = moneySpent;
    }
    @Override
    public String toString() {
        return String.format("- at %s, spent %.2f on %s\n", this.location, this.moneySpent / 100., new SimpleDateFormat("yyyy-MM-dd").format(this.datetime));
    }
}
public class ReceiptDatabase
{
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<Event> events = new ArrayList<Event>();
    String path;

    public ReceiptDatabase(String path) throws IOException {
        this.path = path;
        ObjectInputStream inp = null;
        try {
            try {
                inp = new ObjectInputStream(new FileInputStream(path));
            }
            catch (IOException ex) {
                new PrintWriter(path).close();
                inp = new ObjectInputStream(new FileInputStream(path));
            }
            events = (ArrayList<Event>)inp.readObject();
        }
        catch (IOException | ClassCastException | ClassNotFoundException ex) {
            if (inp != null)
                inp.close();
        }
    }

    private void add(String location, Date datetime, int moneySpent) {
        Event newEvent = new Event(location, datetime, moneySpent);
        events.add(newEvent);
        try {
            ObjectOutputStream databaseFile = new ObjectOutputStream(new FileOutputStream(path));
            databaseFile.writeObject(events);
            databaseFile.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void add(String location, int price) {
        this.add(location, new Date(), price);
    }
    public void add(String location, Date datetime, double price) {
        this.add(location, datetime, (int)Math.round(price * 100));
    }
    public void add(String location, double price) {
        this.add(location, (int)Math.round(price * 100));
    }

    public static interface Predicate {
        boolean test(Event e);
    }
    public ArrayList<Event> query(Predicate pred) {
        ArrayList<Event> yld = new ArrayList<Event>();
        for (Event e: events)
            if (pred.test(e))
                yld.add(e);
        return yld;
    }
    public ArrayList<Event> query() {
        return query(new Predicate() {
            public boolean test(Event e) {
                return true;
            }
        });
    }
    public ArrayList<Event> query(final Date datetime) {
        return query(new Predicate() {
            public boolean test(Event e) {
                return fmt.format(e.datetime).equals(fmt.format(datetime));
            }
        });
    }
    public ArrayList<Event> query(final String location) {
        return query(new Predicate() {
            public boolean test(Event e) {
                return e.location.equals(location);
            }
        });
    }

    public void drop() {
        events = new ArrayList<Event>();
        try {
            ObjectOutputStream databaseFile = new ObjectOutputStream(new FileOutputStream(path));
            databaseFile.writeObject(events);
            databaseFile.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
