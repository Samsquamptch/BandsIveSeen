package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GigButton implements ActionListener {
    private int value;
    GigButton(int givenValue){
    this.value = givenValue;
    }
    public void actionPerformed (ActionEvent e) {
        switch (this.value){
            case 1:
                GigWindow addGig = new GigWindow("Add Gig");
                break;
            case 2:
                GigWindow editGig = new GigWindow("Edit Gig");
                break;
            case 3:
                GigWindow delGig = new GigWindow("Delete Gig");
                break;
        }
    }
}
