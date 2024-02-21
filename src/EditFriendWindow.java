package src;

import src.database.DeleteFromDatabase;
import src.database.EditDatabase;
import src.database.ReadFromDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class EditFriendWindow implements ActionListener {
    JComboBox<String> friendList;
    JTextField friendNameBox;
    JPanel editPanel;
    JButton deleteButton;
    JButton saveButton;
    String selectedFriend;
    int friendDatabaseId;
    CreateWindow addWindow;
    private final Connection jdbcConnection;

    public EditFriendWindow(Connection connection){
        this.jdbcConnection = connection;
        this.selectedFriend = "";
    }

    public void newWindow() {
        //Created the friend selector
        String[] friendData = ReadFromDatabase.selectFriends(this.jdbcConnection, null, false);
        this.friendList = new JComboBox<>(friendData);
        this.friendList.setPreferredSize(new Dimension(400,30));
        this.friendList.addActionListener(this);
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.friendList);

        //Delete band button
        this.deleteButton = new JButton("Delete");
        this.deleteButton.setPreferredSize(new Dimension(200,30));
        this.deleteButton.addActionListener(this);
        JPanel deleteButtonPanel = CreateWindow.createPanel("Delete Friend");
        deleteButtonPanel.add(this.deleteButton, BorderLayout.CENTER);

        //Save changes button
        this.saveButton = new JButton("Save");
        this.saveButton.setPreferredSize(new Dimension(200,30));
        this.saveButton.addActionListener(this);
        JPanel saveButtonPanel = CreateWindow.createPanel("Save Changes");
        saveButtonPanel.add(this.saveButton, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.add(this.deleteButton);
        optionPanel.add(new JPanel());
        optionPanel.add(this.saveButton);

        this.editPanel = new JPanel();
        setEditPanel();

        this.addWindow = new CreateWindow("Edit Band", 500, 350);
        this.addWindow.add(searchPanel, BorderLayout.NORTH);
        this.addWindow.add(this.editPanel, BorderLayout.CENTER);
        this.addWindow.add(optionPanel, BorderLayout.SOUTH);
    }

    public void setEditPanel() {
        this.editPanel.removeAll();
        //Name field
        this.friendNameBox = new JTextField(this.selectedFriend);
        JPanel namePanel = CreateWindow.createPanel("Name");
        this.friendNameBox.setPreferredSize(new Dimension(250, 40));
        namePanel.add(this.friendNameBox);

        revalidateEditPanel();

        this.editPanel.add(namePanel);
        this.editPanel.revalidate();
        this.editPanel.repaint();
    }

    public void revalidateEditPanel() {
        if (this.selectedFriend.isEmpty()) {
            this.friendNameBox.setEnabled(false);
        } else {
            this.friendNameBox.setEnabled(true);
        }
    }

    public void deleteFriend() throws SQLException {
        int deleteResponse = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this friend permanently?",
                "Confirm delete friend", JOptionPane.YES_NO_OPTION);
        if (deleteResponse!=0) {
            return;
        }
        DeleteFromDatabase.deleteFriend(this.jdbcConnection, this.friendDatabaseId);
        JOptionPane.showMessageDialog(null, "Friend has been deleted");
        this.addWindow.dispose();
    }

    public void saveFriendChanges() throws SQLException {
        if (this.friendNameBox.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please ensure the name field is not empty",
                    "Field is empty!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        EditDatabase.editFriend(this.jdbcConnection, this.friendNameBox.getText(), this.friendDatabaseId);
        JOptionPane.showMessageDialog(null, "Changes have been saved");
        this.addWindow.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == this.deleteButton || e.getSource() == this.saveButton) && this.selectedFriend.isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please select a friend before choosing this option",
                    "No Friend Selected!", JOptionPane.WARNING_MESSAGE);
        }
        else if (e.getSource() == this.deleteButton) {
            try { deleteFriend();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.saveButton) {
            try { saveFriendChanges();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == this.friendList && this.friendList.getSelectedIndex()!=0) {
            try {
                this.selectedFriend = this.friendList.getSelectedItem().toString();
                this.friendDatabaseId = ReadFromDatabase.getFriendId(this.jdbcConnection, this.selectedFriend);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setEditPanel();
            }
        else if (e.getSource() == this.friendList) {
            this.selectedFriend = "";
            setEditPanel();
        }
        }
}
