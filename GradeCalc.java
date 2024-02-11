import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
public class GradeCalc extends JFrame {
    private JTextField classField, gradeField;
    private JButton calcButton, calcUnButton, addButton, fillerButton;
    private ArrayList<Double> grades;
    private ArrayList<String> classes, rigor;
    private JTextArea displayArea;
    private JComboBox RigorList = new JComboBox();
    private Font headerFont;
    private JLabel courseLbl, gradeLbl, rigorLbl;
   
    public GradeCalc() {
        super("GPA Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 450));
        
        headerFont = new Font("EB Garamond", Font.BOLD, 14);
        Color fontClr = new Color(0, 0, 0);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        mainPanel.setBackground(new Color(235, 255, 255));
        setResizable(false);
        
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255, 255, 70), 2),"Enter Class and Grade"));
        inputPanel.setBackground(Color.white);
        
        courseLbl = new JLabel("Class: ");
            courseLbl.setFont(headerFont);
            courseLbl.setForeground(fontClr);
        gradeLbl = new JLabel("Grade(0-100): ");
            gradeLbl.setFont(headerFont);
            gradeLbl.setForeground(fontClr);
        rigorLbl = new JLabel("Rigor: ");
            rigorLbl.setFont(headerFont);
            rigorLbl.setForeground(fontClr);
        
        grades = new ArrayList<Double>();
        classes = new ArrayList<String>();
        rigor = new ArrayList<String>();
        
        gradeField = new JTextField();
        classField = new JTextField();

        inputPanel.add(courseLbl);
        inputPanel.add(classField);
        inputPanel.add(gradeLbl);
        inputPanel.add(gradeField);
        inputPanel.add(rigorLbl);
        
        RigorList.setBackground(Color.white);
        RigorList.addItem("AP");
        RigorList.addItem("College");
        RigorList.addItem("Grade-Level");
        RigorList.addItem("Honors");
        inputPanel.add(RigorList);
        JPanel buttonPanel = new JPanel(); // new FlowLayout(FlowLayout.CENTER)
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(235, 255, 255));
        
        addButton = new JButton("Add");
            addButton.addActionListener(new addButtonListener());
            addButton.setBackground(Color.white);
        fillerButton = new JButton(" ");
            fillerButton.setVisible(false);
        calcButton = new JButton(" Calculate Weighted GPA ");
            calcButton.addActionListener(new calcButtonListener());
            calcButton.setBackground(Color.white);
        calcUnButton = new JButton("Calculate Unweighted GPA");
            calcUnButton.addActionListener(new calcUnButtonListener());
            calcUnButton.setBackground(Color.white);
        
        inputPanel.add(fillerButton);
        inputPanel.add(addButton);
        buttonPanel.add(calcButton);
        buttonPanel.add(calcUnButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel displayPanel = new JPanel(new BorderLayout());
            displayPanel.setPreferredSize(new Dimension(250, 175));
            displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255, 255, 70), 2),"Classes"));
            displayPanel.setBackground(Color.white);
            displayArea = new JTextArea();
            displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.white));
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(displayPanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        GradeCalc calc = new GradeCalc();
        calc.setVisible(true);
    }
    
    private class addButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String getclass = classField.getText();
            double getgrades = Double.parseDouble(gradeField.getText());
            String getrigor = RigorList.getSelectedItem().toString();
            if(getgrades>100||getgrades<0)
            {
                
                try{
                    Thread.sleep(001);
                    gradeField.setText("grade not possible");
                }
                catch(InterruptedException x)
                {
                    gradeField.setText("Re-enter");
                }
            }
            else{
                grades.add(getgrades);
                classes.add(getclass);
                rigor.add(getrigor);
                displayArea.append(getclass + "     " + getgrades + "     " + getrigor + "\n");
                classField.setText("");
                gradeField.setText("");
                }
        }
    }

    private class calcButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double total = 0;
            int count = 0;
            for (int i = 0; i < grades.size(); i++) {
                if (rigor.get(i).equalsIgnoreCase("AP") || rigor.get(i).equalsIgnoreCase("College")) {
                    total += (grades.get(i) * 1.1);
                } else if (rigor.get(i).equalsIgnoreCase("Honors") || rigor.get(i).equalsIgnoreCase("Honor")) {
                    total += (grades.get(i) * 1.05);
                } else {
                    total += (grades.get(i) * 1.0);
                }
                count++;
            }
            double avg = total / count;
            JOptionPane.showMessageDialog(GradeCalc.this, "Your weighted GPA is " + avg);
        }
    }

    private class calcUnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double total = 0;
            int count = 0;
            for (int i = 0; i < grades.size(); i++) {
                total += (grades.get(i) * 1.0);
                count++;
            }
            double avg = total / count;
            JOptionPane.showMessageDialog(GradeCalc.this, "Your unweighted GPA is " + avg);
        }
    }
}
