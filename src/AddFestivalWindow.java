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
    JPanel wentWithPanel;
    JComboBox<String> headlineSelect;
    JComboBox<String> headlineRating;
    JComboBox<String> bandSelect;
    JComboBox<String> bandRating;
    JComboBox<String> editSelect;
    JComboBox<String> editRating;
    JComboBox<String> selectFestivalDay;
    JComboBox<String> removeFriend;
    JComboBox<String> addFriend;
    JButton addDayButton;
    JButton removeDayButton;
    JButton addBandButton;
    JButton editBandButton;
    JButton removeBandButton;
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

        this.addWindow = new CreateWindow("Add Festival", 1000, 650);
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
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4,3,10,0));
        JPanel bottomPanel = new JPanel();

        this.editPanel.setLayout(new GridLayout(2,1));
        this.editPanel.add(topPanel);
        this.editPanel.add(bottomPanel);

        //Headline select panel
        this.headlineSelect = new JComboBox<>(bandData);
        this.headlineSelect.removeItem("Remove Band");
        this.headlineSelect.addActionListener(this);
        JPanel headlineSelectPanel = CreateWindow.createPanel("Festival Headline");
        headlineSelectPanel.add(this.headlineSelect, BorderLayout.CENTER);

        //Headline rating select panel
        this.headlineRating = new JComboBox<>(rating);
        this.headlineRating.addActionListener(this);
        JPanel headlineRatingPanel = CreateWindow.createPanel("Set Rating");
        headlineRatingPanel.add(this.headlineRating, BorderLayout.CENTER);

        //Band select panel
        this.bandSelect = new JComboBox<>(bandData);
        JPanel bandSelectPanel = CreateWindow.createPanel("Add Band");
        bandSelectPanel.add(this.bandSelect, BorderLayout.CENTER);

        //Band rating select panel
        this.bandRating = new JComboBox<>(rating);
        JPanel bandRatingPanel = CreateWindow.createPanel("Set Rating");
        bandRatingPanel.add(this.bandRating, BorderLayout.CENTER);

        //Add band button
        this.addBandButton = new JButton("Add");
        JPanel addBandPanel = CreateWindow.createPanel("Add Band");
        addBandPanel.add(this.addBandButton, BorderLayout.CENTER);

        //Band select panel
        this.editSelect = new JComboBox<>(bandData);
        JPanel editSelectPanel = CreateWindow.createPanel("Edit/Remove Band");
        editSelectPanel.add(this.editSelect, BorderLayout.CENTER);

        //Band rating select panel
        this.editRating = new JComboBox<>(rating);
        JPanel editRatingPanel = CreateWindow.createPanel("Edit Rating");
        editRatingPanel.add(this.editRating, BorderLayout.CENTER);

        //Edit or delete panel
        this.editBandButton = new JButton("Edit");
        this.removeBandButton = new JButton("Remove");
        this.editBandButton.addActionListener(this);
        this.removeBandButton.addActionListener(this);
        JPanel setDaysPanel = CreateWindow.createPanel("Edit/Remove Band");
        JPanel buttonSectionPanel = new JPanel();
        buttonSectionPanel.setLayout(new GridLayout(1,2));
        buttonSectionPanel.add(this.editBandButton);
        buttonSectionPanel.add(this.removeBandButton);
        setDaysPanel.add(buttonSectionPanel, BorderLayout.CENTER);

        topPanel.add(headlineSelectPanel);
        topPanel.add(headlineRatingPanel);
        topPanel.add(new JPanel());
        topPanel.add(bandSelectPanel);
        topPanel.add(bandRatingPanel);
        topPanel.add(addBandPanel);
        topPanel.add(editSelectPanel);
        topPanel.add(editRatingPanel);
        topPanel.add(setDaysPanel);
        topPanel.add(new JPanel());
        topPanel.add(new JPanel());
        topPanel.add(new JPanel());
        topPanel.revalidate();
        topPanel.repaint();
    }

    public void setSidePanel() {
        sidePanel.removeAll();
        sidePanel.setPreferredSize(new Dimension(220,150));
        sidePanel.setLayout(new GridLayout(9,1));

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
        JPanel addFriendPanel = CreateWindow.createPanel("   Add Friend");
        addFriendPanel.add(this.addFriend, BorderLayout.CENTER);
        addFriendPanel.add(new JPanel(), BorderLayout.WEST);

        //Remove Friends panel
        String[] removeArray = new String[this.selectedFestival.getWentWith().size()+1];
        removeArray[0] = "Remove a Friend";
        for (int i = 1; i <= this.selectedFestival.getWentWith().size(); i++) {
            removeArray[i] = this.selectedFestival.getWentWith().get(i-1);
        }
        this.removeFriend = new JComboBox<>(removeArray);
        this.removeFriend.addActionListener(this);
        JPanel removeFriendPanel = CreateWindow.createPanel("   Remove Friend");
        removeFriendPanel.add(this.removeFriend, BorderLayout.CENTER);
        removeFriendPanel.add(new JPanel(), BorderLayout.WEST);

        //Went with panel
        JPanel friendPanel = CreateWindow.createPanel("   Went with:");
        this.wentWithPanel = new JPanel();
        friendPanel.add(wentWithPanel, BorderLayout.CENTER);
        this.wentWithPanel.add(new JPanel(), BorderLayout.WEST);
        refreshFriendPanel();

        this.sidePanel.add(namePanel);
        this.sidePanel.add(locationPanel);
        this.sidePanel.add(datePickerPanel);
        this.sidePanel.add(setDaysPanel);
        this.sidePanel.add(selectDayPanel);
        this.sidePanel.add(new JPanel());
        this.sidePanel.add(addFriendPanel);
        this.sidePanel.add(removeFriendPanel);
        this.sidePanel.add(friendPanel);
    }

    public void refreshFriendPanel() {
        this.wentWithPanel.removeAll();
        this.wentWithPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel friendLabel = new JLabel(this.selectedFestival.getFriendsString());
        this.wentWithPanel.add(friendLabel);
        this.wentWithPanel.revalidate();
        this.wentWithPanel.repaint();
    }

    public void friendSelectSettings(JComboBox<String> selectorItem) {
        if (selectorItem.getSelectedItem().equals("Add New Friend")) {
            String friendName = JOptionFriend.addFriend(this.jdbcConnection);
            if (friendName.isEmpty()) {
                selectorItem.setSelectedIndex(0);
                return;
            }
            addFriend.removeItem("Add New Friend");
            addFriend.addItem(friendName);
            addFriend.addItem("Add New Friend");
            this.addFriend.setSelectedItem(friendName);
            friendSelectSettings(this.addFriend);
        }
        else if (selectorItem.getSelectedIndex() != 0) {
            if (selectorItem == this.addFriend) {
                this.selectedFestival.addWentWith(this.addFriend.getSelectedItem().toString());
                this.removeFriend.addItem(this.addFriend.getSelectedItem().toString());
                this.addFriend.removeItem(this.addFriend.getSelectedItem());
                this.addFriend.setSelectedIndex(0);
            } else {
                this.selectedFestival.removeWentWith(this.removeFriend.getSelectedItem().toString());
                this.addFriend.removeItem("Add New Friend");
                this.addFriend.addItem(this.removeFriend.getSelectedItem().toString());
                this.addFriend.addItem("Add New Friend");
                this.removeFriend.removeItem(this.removeFriend.getSelectedItem());
                this.removeFriend.setSelectedIndex(0);
            }
        }
        this.addFriend.revalidate();
        this.addFriend.repaint();
        this.removeFriend.revalidate();
        this.removeFriend.repaint();
        refreshFriendPanel();
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
            if (this.selectedFestival.getNumberOfDays() <= 1) {
                JOptionPane.showMessageDialog(null,
                        "You can't remove the only day of a festival!",
                        "Can't remove day!",JOptionPane.WARNING_MESSAGE);
                return;
            }
            this.selectFestivalDay.removeItem("Day " + this.selectedFestival.getNumberOfDays());
            this.selectedFestival.removeDays();
            this.selectFestivalDay.revalidate();
            this.selectFestivalDay.repaint();
        }
        else if (e.getSource() == this.selectFestivalDay) {
            this.selectedFestival.setFestivalName(this.festivalNameBox.getText());
            this.addWindow.setTitle("Add Festival | " + setFestivalName());
            setEditPanel();
        } else if (e.getSource() == this.addFriend) {
            friendSelectSettings(this.addFriend);
        } else if (e.getSource() == this.removeFriend) {
            friendSelectSettings(this.removeFriend);
        }
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        this.selectedFestival.setEventDay(this.festivalDate.toString());
        this.selectedFestival.updateDaysDate();
    }
}
