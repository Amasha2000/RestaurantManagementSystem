package view.tm;

import javafx.scene.control.Button;

public class TableTM {
      private int table_number;
      private String table_code;
      private int numberOfSeats;
      private Button btn;

    public TableTM() {
    }

    public TableTM(int table_number, String table_code, int numberOfSeats, Button btn) {
        this.setTable_number(table_number);
        this.setTable_code(table_code);
        this.setNumberOfSeats(numberOfSeats);
        this.setBtn(btn);
    }

    public int getTable_number() {
        return table_number;
    }

    public void setTable_number(int table_number) {
        this.table_number = table_number;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
