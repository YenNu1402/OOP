package lethiyennu_3779;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthTrackerUI extends JFrame implements ActionListener {
    private JTextField nameTextField, weightTextField, heightTextField;
    private JLabel resultLabel, statusLabel;
    private JButton calculateButton;

    public HealthTrackerUI() {
        // Thiết lập cửa sổ chính
        setTitle("Health Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Tạo các thành phần giao diện
        JLabel nameLabel = new JLabel("Tên người dùng:");
        nameTextField = new JTextField();

        JLabel weightLabel = new JLabel("Cân nặng (kg):");
        weightTextField = new JTextField();

        JLabel heightLabel = new JLabel("Chiều cao (cm):");
        heightTextField = new JTextField();

        calculateButton = new JButton("Tính BMI");
        calculateButton.addActionListener(this);

        resultLabel = new JLabel("Chỉ số BMI của bạn:");
        statusLabel = new JLabel("Trạng thái sức khỏe của bạn:");

        // Thêm các thành phần vào giao diện
        add(nameLabel);
        add(nameTextField);
        add(weightLabel);
        add(weightTextField);
        add(heightLabel);
        add(heightTextField);
        add(new JLabel()); // Để tạo khoảng trống
        add(calculateButton);
        add(resultLabel);
        add(statusLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            String name = nameTextField.getText();
            String weightText = weightTextField.getText();
            String heightText = heightTextField.getText();

            try {
                double weight = Double.parseDouble(weightText);
                double height = Double.parseDouble(heightText) / 100; // Chuyển đổi chiều cao sang mét

                // Tính chỉ số BMI
                double bmi = weight / (height * height);

                // Hiển thị kết quả BMI
                resultLabel.setText(name + ", chỉ số BMI của bạn là: " + String.format("%.2f", bmi));

                // Xác định trạng thái sức khỏe dựa trên BMI
                String healthStatus;
                if (bmi < 18.5) {
                    healthStatus = "Thiếu cân";
                } else if (bmi < 25) {
                    healthStatus = "Bình thường";
                } else if (bmi < 30) {
                    healthStatus = "Thừa cân";
                } else {
                    healthStatus = "Béo phì";
                }

                // Hiển thị trạng thái sức khỏe
                statusLabel.setText("Trạng thái sức khỏe của bạn: " + healthStatus);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập cân nặng và chiều cao hợp lệ.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HealthTrackerUI healthTrackerUI = new HealthTrackerUI();
            healthTrackerUI.setVisible(true);
        });
    }
}
