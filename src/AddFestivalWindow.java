package src;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import src.database.ReadFromDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AddFestivalWindow implements ActionListener, DateChangeListener {
    DatePicker festivalDate;
    JTextField festivalNameBox;
    JTextField festivalLocationBox;
    JPanel sidePanel;
    JPanel editPanel;
    JComboBox<String> headlineSelect;
    JComboBox<String> headlineRating;
    JComboBox<String> selectFestivalDay;
    JComboBox<String> removeFriend;
    JComboBox<String> addFriend;
    JButton addDayButton;
    JButton removeDayButton;
    JButton saveButton;
    CreateWindow addWindow;
    Festival selectedFestival;
    private final Connection jdbcConnection;

    public AddFestivalWindow(Connection conn){
        this.jdbcConnection = conn;
        this.selectedFestival = new Festival();
    }

    public void newWindow() {
        //Save changes button
        this.saveButton = new JButton("Save");
        this.saveButton.setPreferredSize(new Dimension(200,30));
        this.saveButton.addActionListener(this);
        JPanel saveButtonPanel = CreateWindow.createPanel("Save Changes");
        saveButtonPanel.add(this.saveButton, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.add(this.saveButton);

        this.sidePanel = new JPanel();
        setSidePanel();
        this.editPanel = new JPanel();
        setEditPanel();

        this.addWindow = new CreateWindow("Add Festival", 1000, 600);
        this.addWindow.add(new JPanel(), BorderLayout.NORTH);
        this.addWindow.add(this.sidePanel, BorderLayout.WEST);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
    }

    public String setFestivalName() {
        if (this.selectedFestival.getFestivalDays().isEmpty()) {
            return "";
        } else {
            int selectedIndex = this.selectFestivalDay.getSelectedIndex();
            return this.selectedFestival.getFestivalName() + " " + this.selectedFestival.getEventYear() +
                    ": " + this.selectedFestival.getFestivalDays().get(selectedIndex).getDay();
        }
    }

    public void setEditPanel() {
        String[] bandData = ReadFromDatabase.selectBands(this.jdbcConnection, true);
        String[] rating = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        this.editPanel.removeAll();
        this.editPanel.setLayout(new GridLayout(2,3));

        //Festival name label
        JLabel festivalDayName = new JLabel(setFestivalName());
        JPanel dayNamePanel = new JPanel();
        dayNamePanel.add(festivalDayName);

        //Headline select panel
        this.headlineSelect = new JComboBox<>(bandData);
        this.headlineSelect.removeItem("Remove Band");
        this.headlineSelect.addActionListener(this);
        JPanel headlineSelectPanel = CreateWindow.createPanel("Gig Headline");
        headlineSelectPanel.add(this.headlineSelect, BorderLayout.CENTER);

        //Headline rating select panel
        this.headlineRating = new JComboBox<>(rating);
        this.headlineRating.addActionListener(this);
        JPanel headlineRatingPanel = CreateWindow.createPanel("Set Rating");
        headlineRatingPanel.add(this.headlineRating, BorderLayout.CENTER);


        this.editPanel.add(dayNamePanel);
        this.editPanel.add(new JPanel());
        this.editPanel.add(new JPanel());
        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void setSidePanel() {
        sidePanel.removeAll();
        sidePanel.setPreferredSize(new Dimension(220,150));
        sidePanel.setLayout(new GridLayout(8,1));

        //Name field
        this.festivalNameBox = new JTextField();
        JPanel namePanel = CreateWindow.createPanel("   Festival Name");
        this.festivalNameBox.setPreferredSize(new Dimension(200, 40));
        namePanel.add(this.festivalNameBox, BorderLayout.CENTER);
        namePanel.add(new JPanel(), BorderLayout.WEST);

        //Genre field
        this.festivalLocationBox = new JTextField();
        JPanel locationPanel = CreateWindow.createPanel("   Festival Location");
        this.festivalLocationBox.setPreferredSize(new Dimension(200, 40));
        locationPanel.add(this.festivalLocationBox, BorderLayout.CENTER);
        locationPanel.add(new JPanel(), BorderLayout.WEST);

        //Date picker panel
        this.festivalDate = new DatePicker();
        this.festivalDate.enableInputMethods(false);
        if (this.selectedFestival.getEventDay().isEmpty()) {
            this.festivalDate.setDateToToday();
            this.selectedFestival.setEventDay(this.festivalDate.toString());
        } else {
            festivalDate.setDate(this.selectedFestival.getLocalDate());
        }
        this.festivalDate.addDateChangeListener(this::dateChanged);
        JPanel datePickerPanel = CreateWindow.createPanel("   Start Date");
        datePickerPanel.add(this.festivalDate, BorderLayout.CENTER);
        datePickerPanel.add(new JPanel(), BorderLayout.WEST);

        //Add or remove day Buttons
        this.addDayButton = new JButton("Add");
        this.removeDayButton = new JButton("Remove");
        this.addDayButton.addActionListener(this);
        this.removeDayButton.addActionListener(this);
        JPanel setDaysPanel = CreateWindow.createPanel("   Add/Remove Days");
        JPanel buttonSectionPanel = new JPanel();
        buttonSectionPanel.setLayout(new GridLayout(1,2));
        buttonSectionPanel.add(this.addDayButton);
        buttonSectionPanel.add(this.removeDayButton);
        setDaysPanel.add(buttonSectionPanel, BorderLayout.CENTER);
        setDaysPanel.add(new JPanel(), BorderLayout.WEST);

        //Festival day selector
        this.selectFestivalDay = new JComboBox<>();
        this.selectFestivalDay.addActionListener(this);
        JPanel selectDayPanel = CreateWindow.createPanel("   Select Festival Day");
        selectDayPanel.add(this.selectFestivalDay);
        selectDayPanel.add(new JPanel(), BorderLayout.WEST);

        //Add Friends panel
        String[] friendArray = ReadFromDatabase.selectFriends(this.jdbcConnection,
                this.selectedFestival.getWentWith().toArray(new String[0]), true);
        this.addFriend = new JComboBox<>(friendArray);
        this.addFriend.addActionListener(this);
        JPanel addFriendPanel = CreateWindow.createPanel("Add Friend");
        addFriendPanel.add(this.addFriend, BorderLayout.CENTER);

        //Remove Friends panel
        String[] removeArray = new String[this.selectedFestival.getWentWith().size()+1];
        removeArray[0] = "Remove a Friend";
        for (int i = 1; i <= this.selectedFestival.getWentWith().size(); i++) {
            removeArray[i] = this.selectedFestival.getWentWith().get(i-1);
        }
        this.removeFriend = new JComboBox<>(removeArray);
        this.removeFriend.addActionListener(this);
        JPanel removeFriendPanel = CreateWindow.createPanel("Remove Friend");
        removeFriendPanel.add(this.removeFriend, BorderLayout.CENTER);

        this.sidePanel.add(namePanel);
        this.sidePanel.add(locationPanel);
        this.sidePanel.add(datePickerPanel);
        this.sidePanel.add(setDaysPanel);
        this.sidePanel.add(selectDayPanel);
        this.sidePanel.add(new JPanel());
        this.sidePanel.add(new JPanel());
        this.sidePanel.add(new JPanel());
    }

    public void saveChanges() {
        this.selectedFestival.setLocation(new Venue(this.selectedFestival.getFestivalName(),
                this.festivalLocationBox.getText(), true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addDayButton) {
            if (this.selectedFestival.getNumberOfDays() >= 7) {
                return;
            }
            this.selectedFestival.addDays();
            this.selectFestivalDay.addItem("Day " + this.selectedFestival.getNumberOfDays());
            this.selectFestivalDay.revalidate();
            this.selectFestivalDay.repaint();
        }
        else if (e.getSource() == this.removeDayButton) {
            if (this.selectedFestival.getNumberOfDays() <= 0) {
                return;
            }
            this.selectFestivalDay.removeItem("Day " + this.selectedFestival.getNumberOfDays());
            this.selectedFestival.removeDays();
            this.selectFestivalDay.revalidate();
            this.selectFestivalDay.repaint();
        }
        else if (e.getSource() == this.selectFestivalDay) {
            this.selectedFestival.setFestivalName(this.festivalNameBox.getText());
            setEditPanel();
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        this.selectedFestival.setEventDay(this.festivalDate.toString());
        this.selectedFestival.updateDaysDate();
    }
}
