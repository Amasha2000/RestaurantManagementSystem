package model;

public class Table {
    private int table_number;
    private String table_code;
    private int numberOfSeats;

    public Table(int table_number,String table_code, int numberOfSeats) {
        this.setTable_number(table_number);
        this.setTable_code(table_code);
        this.setNumberOfSeats(numberOfSeats);
    }

    public Table() {
    }

    public int getTable_number(){
        return table_number;
    }

    public void setTable_number(int table_number){
        this.table_number=table_number;
    }

    public String getTable_code() {
        return table_code;
    }

    public void setTable_code(String table_code) {
        this.table_code = table_code;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
