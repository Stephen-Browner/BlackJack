package ca.nscc.blackjack.ui.game;

public class Card {

    private int value;
    private String name;
    //private String image;

    public Card(int value, String name) {
        this.value = value;
        this.name = name;

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
