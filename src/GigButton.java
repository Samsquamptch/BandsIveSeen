package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GigButton implements ActionListener {
    private final int value;
    GigButton(int givenValue){
    this.value = givenValue;
    }
    public void actionPerformed (ActionEvent e) {
        //This switch statement checks which button is being pressed, very cool!
        switch (this.value) {
            case 1 -> {
                GigWindow addGig = new GigWindow("Add Gig");
            }
            case 2 -> {
                GigWindow editGig = new GigWindow("Edit Gig");
            }
            case 3 -> {
                GigWindow delGig = new GigWindow("Delete Gig");
            }
        }
    }
}
