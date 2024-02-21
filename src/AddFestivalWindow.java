package src;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

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
    JComboBox<String> selectFestivalDay;
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
//        setEditPanel();

        this.addWindow = new CreateWindow("Add Festival", 1000, 600);
        this.addWindow.add(new JPanel(), BorderLayout.NORTH);
        this.addWindow.add(this.sidePanel, BorderLayout.WEST);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
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
        if (!this.selectedFestival.getEventDay().isEmpty()) {
            festivalDate.setDate(this.selectedFestival.getLocalDate());
        }
        this.festivalDate.addDateChangeListener(this::dateChanged);
        JPanel datePickerPanel = CreateWindow.createPanel("   Start Date");
        datePickerPanel.add(this.festivalDate, BorderLayout.CENTER);
        datePickerPanel.add(new JPanel(), BorderLayout.WEST);

        //Add or remove day Buttons
        this.addDayButton = new JButton("+");
        this.removeDayButton = new JButton("-");
        JPanel setDaysPanel = CreateWindow.createPanel("   Add/Remove Days");
        JPanel buttonSectionPanel = new JPanel();
        buttonSectionPanel.setLayout(new GridLayout(1,2));
        buttonSectionPanel.add(this.addDayButton);
        buttonSectionPanel.add(this.removeDayButton);
        setDaysPanel.add(buttonSectionPanel, BorderLayout.CENTER);
        setDaysPanel.add(new JPanel(), BorderLayout.WEST);

        //Festival day selector
        this.selectFestivalDay = new JComboBox<>();
        JPanel selectDayPanel = CreateWindow.createPanel("   Select Festival Day");
        selectDayPanel.add(this.selectFestivalDay);
        selectDayPanel.add(new JPanel(), BorderLayout.WEST);

        this.sidePanel.add(namePanel);
        this.sidePanel.add(locationPanel);
        this.sidePanel.add(datePickerPanel);
        this.sidePanel.add(setDaysPanel);
        this.sidePanel.add(selectDayPanel);
        this.sidePanel.add(new JPanel());
        this.sidePanel.add(new JPanel());
        this.sidePanel.add(new JPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        this.selectedFestival.setEventDay(this.festivalDate.toString());
    }
}
