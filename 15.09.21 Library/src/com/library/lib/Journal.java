package com.library.lib;

import org.json.JSONObject;

public class Journal extends Literature {
    private String number ;  // Year, No (2021,1)

    public Journal( String title, String number ) {
        super.setTitle( title ) ;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void print() {
        System.out.printf(
                "Journal: %s (%s)",
                super.getTitle(),
                number
        );
    }

    /**
     *
     * @param lit - Literature object
     * @return JSON string if lit is instanceof Journal or null
     */
    @Override
    public String toJsonString (Literature lit) {
        if (lit instanceof Journal) {
            try {
                Journal journal = (Journal) lit;

                JSONObject obj = new JSONObject();
                obj.put("title", journal.getTitle());
                obj.put("author", journal.getNumber());

                return obj.toString();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.getTitle() + "_" + this.getNumber();
    }
}
