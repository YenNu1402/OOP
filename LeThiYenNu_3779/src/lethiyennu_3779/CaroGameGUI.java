package lethiyennu_3779;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaroGameGUI extends JFrame {
    private JButton[][] buttons = new JButton[15][15];  // Mảng chứa các ô cờ
    private boolean isPlayerOneTurn = true;  // Xác định lượt của người chơi: True là X, False là O
    private JLabel statusLabel;  // Hiển thị trạng thái của trò chơi

    public CaroGameGUI() {
        setTitle("Cờ Caro");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo panel cho bàn cờ
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(15, 15));  // Tạo bảng 30x30

        // Khởi tạo các nút đại diện cho ô cờ
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button = new JButton();
                buttons[i][j] = button;
                button.setFont(new Font("Arial", Font.BOLD, 40));  // Font chữ lớn để dễ nhìn
                button.setFocusPainted(false);  // Loại bỏ viền khi chọn
                button.addActionListener(new ButtonClickListener(i, j));  // Sự kiện nhấn nút
                boardPanel.add(button);
            }
        }

        // Tạo label để hiển thị trạng thái
        statusLabel = new JLabel("Người chơi 1 (X) đi trước", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Tạo nút reset
        JButton resetButton = new JButton("Đặt lại");
        resetButton.addActionListener(e -> resetBoard());

        // Panel chứa label trạng thái và nút reset
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(statusLabel, BorderLayout.CENTER);
        controlPanel.add(resetButton, BorderLayout.EAST);

        // Thêm các panel vào frame chính
        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Lớp xử lý sự kiện khi người chơi nhấn vào button
    private class ButtonClickListener implements ActionListener {
        private int x, y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = buttons[x][y];
            // Kiểm tra ô đã được chọn chưa, nếu đã chọn thì không cho chọn lại
            if (!button.getText().equals("")) {
                return;
            }

            // Đặt quân X hoặc O dựa trên lượt của người chơi
            if (isPlayerOneTurn) {
                button.setText("X");  // Hiển thị X cho người chơi 1
                button.setForeground(Color.RED);  // Đặt màu đỏ cho X
                statusLabel.setText("Người chơi 2 (O) đang chơi");
            } else {
                button.setText("O");  // Hiển thị O cho người chơi 2
                button.setForeground(Color.BLUE);  // Đặt màu xanh cho O
                statusLabel.setText("Người chơi 1 (X) đang chơi");
            }

            // Kiểm tra xem có ai thắng không
            if (checkWin(x, y)) {
                String winner = isPlayerOneTurn ? "Người chơi 1 (X)" : "Người chơi 2 (O)";
                JOptionPane.showMessageDialog(null, winner + " đã chiến thắng!");
                resetBoard();
            }

            // Đổi lượt người chơi
            isPlayerOneTurn = !isPlayerOneTurn;
        }
    }

    // Kiểm tra chiến thắng sau mỗi lượt đi
    private boolean checkWin(int x, int y) {
        String currentSymbol = buttons[x][y].getText();

        // Kiểm tra hàng ngang
        if (countConsecutive(x, y, 0, 1, currentSymbol) + countConsecutive(x, y, 0, -1, currentSymbol) - 1 >= 5) {
            return true;
        }

        // Kiểm tra cột dọc
        if (countConsecutive(x, y, 1, 0, currentSymbol) + countConsecutive(x, y, -1, 0, currentSymbol) - 1 >= 5) {
            return true;
        }

        // Kiểm tra đường chéo chính (\)
        if (countConsecutive(x, y, 1, 1, currentSymbol) + countConsecutive(x, y, -1, -1, currentSymbol) - 1 >= 5) {
            return true;
        }

        // Kiểm tra đường chéo phụ (/)
        if (countConsecutive(x, y, 1, -1, currentSymbol) + countConsecutive(x, y, -1, 1, currentSymbol) - 1 >= 5) {
            return true;
        }

        return false;
    }

    // Đếm số quân liên tiếp theo một hướng
    private int countConsecutive(int x, int y, int dx, int dy, String symbol) {
        int count = 0;
        int i = x, j = y;
        while (i >= 0 && i < 20 && j >= 0 && j < 20 && buttons[i][j].getText().equals(symbol)) {
            count++;
            i += dx;
            j += dy;
        }
        return count;
    }

    // Đặt lại bàn cờ sau khi trò chơi kết thúc hoặc người chơi muốn đặt lại
    private void resetBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                buttons[i][j].setText("");  // Xóa hết ký tự trong ô
                buttons[i][j].setBackground(null);  // Đặt lại nền
            }
        }
        isPlayerOneTurn = true;  // Reset lượt chơi
        statusLabel.setText("Người chơi 1 (X) đi trước");
    }

    public static void main(String[] args) {
        new CaroGameGUI();  // Chạy game
    }
}
