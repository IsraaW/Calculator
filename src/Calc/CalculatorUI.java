package Calc;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CalculatorUI extends JFrame {
    private final Calculator calculator;
    
    // Display components
    private JTextField currentDisplay;
    private JTextField previousDisplay;
    
    // Window dragging support
    private int mouseX, mouseY;
    
    // Colors
    private final Color BG_DARK = new Color(13, 12, 20);
    private final Color BG_NUMBER = new Color(21, 20, 22);
    private final Color BG_OPERATOR = new Color(41, 39, 44);
    private final Color BG_HOVER = new Color(73, 69, 78);
    private final Color FG_WHITE = Color.WHITE;
    private final Color FG_GRAY = new Color(203, 198, 213);
    
    public CalculatorUI(Calculator calculator) {
        this.calculator = calculator;
        calculator.setUI(this);
        initializeUI();
    }
    
    private void initializeUI() {
        // Frame setup
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(320, 530);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());
        
        // Add components
        add(createTitleBar(), BorderLayout.NORTH);
        add(createDisplayPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(BG_NUMBER);
        titleBar.setPreferredSize(new Dimension(320, 30));
        
        // Title label
        JLabel title = new JLabel(" Calculator");
        title.setFont(new Font("Century Gothic", Font.BOLD, 17));
        title.setForeground(FG_WHITE);
        titleBar.add(title, BorderLayout.WEST);
        
        // Window controls panel
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controls.setOpaque(false);
        
        // Minimize button
        JButton btnMin = createWindowButton("-", e -> setState(JFrame.ICONIFIED));
        controls.add(btnMin);
        
        // Close button
        JButton btnClose = createWindowButton("×", e -> setVisible(false));
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnClose.setBackground(new Color(255, 75, 75));
                btnClose.setForeground(new Color(31, 30, 33));
            }
            public void mouseExited(MouseEvent e) {
                btnClose.setBackground(BG_NUMBER);
                btnClose.setForeground(FG_WHITE);
            }
        });
        controls.add(btnClose);
        
        titleBar.add(controls, BorderLayout.EAST);
        
        // Make window draggable
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY);
            }
        });
        
        return titleBar;
    }
    
    private JButton createWindowButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(BG_NUMBER);
        button.setForeground(FG_WHITE);
        button.setFont(new Font("Century Gothic", Font.BOLD, 24));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(30, 30));
        button.addActionListener(action);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BG_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BG_NUMBER);
            }
        });
        
        return button;
    }
    
    private JPanel createDisplayPanel() {
        JPanel displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.setBackground(new Color(34, 34, 34));
        displayPanel.setPreferredSize(new Dimension(320, 110));
        
        // Previous operation display
        previousDisplay = createDisplayField(BG_NUMBER, FG_GRAY, 18);
        displayPanel.add(previousDisplay);
        
        // Current number display
        currentDisplay = createDisplayField(BG_OPERATOR, FG_WHITE, 24);
        displayPanel.add(currentDisplay);
        
        return displayPanel;
    }
    
    private JTextField createDisplayField(Color bg, Color fg, int fontSize) {
        JTextField field = new JTextField();
        field.setEditable(false);
        field.setBackground(bg);
        field.setForeground(fg);
        field.setFont(new Font("Century Gothic", Font.BOLD, fontSize));
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return field;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(BG_NUMBER);
        buttonPanel.setPreferredSize(new Dimension(320, 390));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Row 1: Del, Clear, ÷, ×
        addButton(buttonPanel, "←", BG_OPERATOR, e -> calculator.deleteLastDigit(), 0, 0, 1, 1, gbc);
        addButton(buttonPanel, "C", BG_OPERATOR, e -> calculator.clear(), 1, 0, 1, 1, gbc);
        addButton(buttonPanel, "÷", BG_OPERATOR, e -> calculator.setOperation("÷"), 2, 0, 1, 1, gbc);
        addButton(buttonPanel, "×", BG_OPERATOR, e -> calculator.setOperation("×"), 3, 0, 1, 1, gbc);
        
        // Row 2: 7, 8, 9, -
        addButton(buttonPanel, "7", BG_NUMBER, e -> calculator.appendNumber("7"), 0, 1, 1, 1, gbc);
        addButton(buttonPanel, "8", BG_NUMBER, e -> calculator.appendNumber("8"), 1, 1, 1, 1, gbc);
        addButton(buttonPanel, "9", BG_NUMBER, e -> calculator.appendNumber("9"), 2, 1, 1, 1, gbc);
        addButton(buttonPanel, "-", BG_OPERATOR, e -> calculator.setOperation("-"), 3, 1, 1, 1, gbc);
        
        // Row 3: 4, 5, 6, + (spans 2 rows)
        addButton(buttonPanel, "4", BG_NUMBER, e -> calculator.appendNumber("4"), 0, 2, 1, 1, gbc);
        addButton(buttonPanel, "5", BG_NUMBER, e -> calculator.appendNumber("5"), 1, 2, 1, 1, gbc);
        addButton(buttonPanel, "6", BG_NUMBER, e -> calculator.appendNumber("6"), 2, 2, 1, 1, gbc);
        addButton(buttonPanel, "+", BG_OPERATOR, e -> calculator.setOperation("+"), 3, 2, 1, 2, gbc);
        
        // Row 4: 1, 2, 3
        addButton(buttonPanel, "1", BG_NUMBER, e -> calculator.appendNumber("1"), 0, 3, 1, 1, gbc);
        addButton(buttonPanel, "2", BG_NUMBER, e -> calculator.appendNumber("2"), 1, 3, 1, 1, gbc);
        addButton(buttonPanel, "3", BG_NUMBER, e -> calculator.appendNumber("3"), 2, 3, 1, 1, gbc);
        
        // Row 5: +/-, 0, ., =
        addButton(buttonPanel, "+/-", BG_NUMBER, e -> calculator.toggleSign(), 0, 4, 1, 1, gbc);
        addButton(buttonPanel, "0", BG_NUMBER, e -> calculator.appendNumber("0"), 1, 4, 1, 1, gbc);
        addButton(buttonPanel, ".", BG_NUMBER, e -> calculator.addDecimalPoint(), 2, 4, 1, 1, gbc);
        addButton(buttonPanel, "=", BG_OPERATOR, e -> calculator.compute(), 3, 4, 1, 1, gbc);
        
        return buttonPanel;
    }
    
    private void addButton(JPanel panel, String text, Color bg, ActionListener action, 
                          int x, int y, int width, int height, GridBagConstraints gbc) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(FG_WHITE);
        button.setFont(new Font("Century Gothic", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(BG_OPERATOR));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(70, 70));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BG_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
        
        button.addActionListener(action);
        
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        panel.add(button, gbc);
    }
    
    // Called by Calculator to update display
    public void updateDisplay(String current, String previous) {
        SwingUtilities.invokeLater(() -> {
            currentDisplay.setText(current);
            previousDisplay.setText(previous);
        });
    }
    
    // Show the calculator window
    public void showCalculator() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            toFront();
            requestFocus();
        });
    }
}

