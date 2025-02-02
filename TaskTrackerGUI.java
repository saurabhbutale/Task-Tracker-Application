import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Task {
    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    @Override
    public String toString() {
        return description + (completed ? " - Completed" : "");
    }
}

class TaskTracker {
    private ArrayList<Task> tasks;

    public TaskTracker() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        Task newTask = new Task(description);
        tasks.add(newTask);
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markCompleted();
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}

public class TaskTrackerGUI extends JFrame {
    private TaskTracker taskTracker;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;

    public TaskTrackerGUI() {
        taskTracker = new TaskTracker();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);

        // Set up the JFrame
        setTitle("Task Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JTextField taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark as Completed");
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Add components to the JFrame
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(completeButton, BorderLayout.SOUTH);

        // Add event listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = taskInput.getText().trim();
                if (!description.isEmpty()) {
                    taskTracker.addTask(description);
                    listModel.addElement(description);
                    taskInput.setText("");
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskTracker.markTaskCompleted(selectedIndex);
                    listModel.set(selectedIndex, taskTracker.getTasks().get(selectedIndex).toString());
                }
            }
        });
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskTrackerGUI().setVisible(true);
            }
        });
    }
}