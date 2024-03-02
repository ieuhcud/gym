package Controller;

import data.database;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Label edit_label;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button si_loginBtn;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button su_signupBtn;

    @FXML
    private TextField su_username;

    @FXML
    private AnchorPane sub_form;

    @FXML
    private Button sub_loginBtn;

    @FXML
    private Button sub_signupBtn;
    @FXML
    private FontAwesomeIconView close_icon;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Kiểm tra tính hợp lệ của địa chỉ email
    private boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra định dạng địa chỉ email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        // Kiểm tra xem địa chỉ email có đúng định dạng không
        return matcher.matches() && email.endsWith("@gmail.com");
    }
    public void login() {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        Alert alert;

        try (Connection connect = database.connecDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, si_username.getText());
            prepare.setString(2, si_password.getText());
            ResultSet result = prepare.executeQuery();

            if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập hết vào các ô trống!");
                alert.showAndWait();
            } else if (result.next()) {
                database.username = si_username.getText();
                openNewScene();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Tên người dùng/mật khẩu không đúng");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void openNewScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gymm/dashboard.fxml"));
            Parent root = loader.load();
            // Tạo một Stage mới
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.initStyle(StageStyle.UNDECORATED);
            // Đóng Stage hiện tại
            Stage currentStage = (Stage) si_loginBtn.getScene().getWindow();
            currentStage.close();
            // Hiển thị Stage mới
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void signup() {
        String sql = "INSERT INTO admin (email, username, password) VALUES(?, ?, ?)";
        connect = database.connecDb();

        try {
            Alert alert;

            if (su_email.getText().isEmpty() || su_username.getText().isEmpty() || su_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập hết vào các ô trống!");
                alert.showAndWait();
            } else {
                if (!isValidEmail(su_email.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Địa chỉ email không hợp lệ. Vui lòng sử dụng địa chỉ Gmail hợp lệ.");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password từ 8 kí tự trở lên --");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, su_email.getText());
                    prepare.setString(2, su_username.getText());
                    prepare.setString(3, su_password.getText());
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Tạo tài khoản thành công!");
                    alert.showAndWait();

                    prepare.executeUpdate();
                    su_email.setText("");
                    su_username.setText("");
                    su_password.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void signupSlider(){

        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(300);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished(event -> {
            edit_label.setText("  Login Account");

            sub_signupBtn.setVisible(false);
            sub_loginBtn.setVisible(true)   ;

            close_icon.setFill(Color.valueOf("#fff"));
        });
    }
    public void loginSlider(){
        TranslateTransition slider1 = new TranslateTransition();
        slider1.setNode(sub_form);
        slider1.setToX(0);
        slider1.setDuration(Duration.seconds(.5));
        slider1.play();

        slider1.setOnFinished(envent ->{
            edit_label.setText(" Create Account  ");
            sub_signupBtn.setVisible(true);
            sub_loginBtn.setVisible(false)   ;
            close_icon.setFill(Color.valueOf("#000"));
        });

    }
    public void close(){
        javafx.application.Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}