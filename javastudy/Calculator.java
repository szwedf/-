package javastudy;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//電卓アプリケーションのメインクラス
public class Calculator extends JFrame {
    
    private static final long serialVersionUID = 1L;
    // コンテンツパネルとレイアウトの定義
    JPanel contentPane = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JTextField resultText = new JTextField("");
 // 計算に関連する変数の初期化
    String strTemp = "";
    String strResult = "0";
    int operator = 0;
    
    final static int KEYADD = 1;
    final static int KEYSUB = 2;
    final static int KEYMUL = 3;
    final static int KEYDIV = 4;
    
    // メインメソッド
    public static void main(String args[]) {
        Calculator frame = new Calculator("Calculator");
        frame.setVisible(true);
    }
    
    // コンストラクタ
    Calculator(String title) {
        setTitle(title);
        setSize(300, 300);
        setLocationRelativeTo(null);
        contentPane.setLayout(borderLayout1);
        this.setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultText = new JTextField("0");
        
        // アイコンの設定
        String imageFileName = "resources/calculator.png";
        URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
        Image icon = (imageUrl != null) ? new ImageIcon(imageUrl).getImage() : new ImageIcon(imageFileName).getImage();
        setIconImage(icon);
        
        resultText.setEditable(false);
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(resultText, BorderLayout.NORTH);
        
      
        // キーパネルの設定
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(5, 4));
        contentPane.add(keyPanel, BorderLayout.CENTER);
        
        // キーパネルにボタンを追加
        keyPanel.add(new JButton(""), 0);
        keyPanel.add(new JButton(""), 1);
        keyPanel.add(new ClearButton("del"), 2);
        keyPanel.add(new ClearButton("C"), 3);
        keyPanel.add(new NumberButton("7"), 4);
        keyPanel.add(new NumberButton("8"), 5);
        keyPanel.add(new NumberButton("9"), 6);
        keyPanel.add(new CalcButton("÷"), 7);
        keyPanel.add(new NumberButton("4"), 8);
        keyPanel.add(new NumberButton("5"), 9);
        keyPanel.add(new NumberButton("6"), 10);
        keyPanel.add(new CalcButton("×"), 11);
        keyPanel.add(new NumberButton("1"), 12);
        keyPanel.add(new NumberButton("2"), 13);
        keyPanel.add(new NumberButton("3"), 14);
        keyPanel.add(new CalcButton("-"), 15);
        keyPanel.add(new NumberButton("0"), 16);
        keyPanel.add(new NumberButton("."), 17);
        keyPanel.add(new CalcButton("+"), 18);
        keyPanel.add(new CalcButton("="), 19);
    }
    // 計算を実行するメソッド
    private String doCalc() {
        BigDecimal bd1 = new BigDecimal(strResult);
        BigDecimal bd2 = new BigDecimal(strTemp);
        BigDecimal result = BigDecimal.ZERO;
        
        switch(operator) {
        case KEYADD:
            result = bd1.add(bd2);
            break;
        case KEYSUB:
            result = bd1.subtract(bd2);
            break;
        case KEYMUL:
            result = bd1.multiply(bd2);
            break;
        case KEYDIV:
            if(!bd2.equals(BigDecimal.ZERO)) {
                result = bd1.divide(bd2, 12, 3);
            } else {
                JOptionPane.showMessageDialog(this, "error! (0 div)");
                return strResult;
            }
            
            break;
        }
        
        if(result.toString().indexOf(".") >= 0) {
            return result.toString().replaceAll("\\.0+$|0+$", "");
        } else {
            return result.toString();
        }
    }
    // テキストフィールドに数値を表示するメソッド
    private void showNumber(String strNum) {
        DecimalFormat form = new DecimalFormat("#,##0");
        String strDecimal = "";
        String strInt = "";
        String fText = "";
        
        if(strNum.length() > 0) {
            int decimalPoint = strNum.indexOf(".");
            
            if(decimalPoint > -1) {
                strDecimal = strNum.substring(decimalPoint);
                strInt = strNum.substring(0, decimalPoint);
            } else {
                strInt = strNum;
            }
            
            fText = form.format(Double.parseDouble(strInt)) + strDecimal;
        } else {
            fText = "0";
        }
        
        resultText.setText(fText);
    }
    
    // 数字ボタンのアクションを処理するクラス
    public class NumberButton extends JButton implements ActionListener {
        
        private static final long serialVersionUID = 1L;
        
        public NumberButton(String str) {
            super(str);
            this.addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent evt) {
            String keyNumber = this.getText();
            
            if(keyNumber.equals(".")) {
                if(strTemp.length() == 0) {
                	strTemp = "0.";
                } else {
                    if(strTemp.indexOf(".") == -1) {
                    	strTemp = strTemp + ".";
                    }
                }
            } else {
                strTemp = strTemp + keyNumber;
            }
            
            showNumber(strTemp);
        }
    }
    
 // 計算ボタンのアクションを処理するクラス
    public class CalcButton extends JButton implements ActionListener {
        
        private static final long serialVersionUID = 1L;
        
        public CalcButton(String str) {
            super(str);
            this.addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e) {
            if(operator != 0) {
                if(strTemp.length() > 0) {
                    strResult = doCalc();
                    showNumber(strResult);
                } else {
                    
                }
            } else {
                if(strTemp.length() > 0) {
                    strResult = strTemp;
                }
            }
            
            strTemp = "";
            
            if(this.getText().equals("+")) {
                operator = KEYADD;
            } else if(this.getText().equals("-")) {
                operator = KEYSUB;
            } else if(this.getText().equals("×")) {
                operator = KEYMUL;
            } else if(this.getText().equals("÷")) {
                operator = KEYDIV;
            } else {
                operator = 0;
            }
        }
    }
    
    // クリアボタンのアクションを処理するクラス
    public class ClearButton extends JButton implements ActionListener {
        
        private static final long serialVersionUID = 1L;
        
        public ClearButton(String str) {
            super(str);
            this.addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent evt) {
            if(this.getText().equals("C")) {
                strTemp = "";// テンポラリをクリア
                strResult = "0";// 結果を0に戻す
                operator = 0; // 演算子をクリア
            } else if(this.getText().equals("del")) {
                if(strTemp.length() == 0) {
                    return;
                } else {
                    strTemp = strTemp.substring(0, strTemp.length() - 1); // 最後の文字を削除
                }
            }
            
            showNumber(strTemp);
        }
    }
    
}