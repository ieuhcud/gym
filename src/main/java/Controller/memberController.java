package Controller;

import data.database;
import data.memberData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class memberController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private TextField id_member;

    @FXML
    private Button logout;

    @FXML
    private TableColumn<memberData, String> memberId;

    @FXML
    private TableColumn<memberData, String> memberEnd;

    @FXML
    private TableColumn<memberData, String> memberName;

    @FXML
    private TableColumn<memberData, String> memberStart;

    @FXML
    private TableView<memberData> memberTable;

    @FXML
    private Button search;

    @FXML
    private Label username;


    //Tương tác CSDL
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void displayUsername(){
        String user = database.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);
    }

    public ObservableList<memberData> membersDataList(){
        ObservableList<memberData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM member ";

        connect = database.connecDb();

        try{

            prepare = connect.  prepareStatement(sql);
            result = prepare.executeQuery();
            memberData md;

            while (result.next()){
                md = new memberData(result.getInt("id"),
                        result.getString("memberId"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getInt("phoneNum"),
                        result.getString("gender"),
                        result.getString("schedule"),
                        result.getDate("startDate"),
                        result.getDate("endDate"),
                        result.getDouble("price"),
                        result.getString("status"));

                listData.add(md);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<memberData> membersListData;
    public void showMember(){
        membersListData = membersDataList();

        memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        memberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        memberEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        memberTable.setItems(membersListData);
    }

    public void searchMember() {
        String searchText = id_member.getText().toLowerCase(); // Lấy giá trị ID từ trường nhập liệu và chuyển thành chữ thường

        ObservableList<memberData> searchList = FXCollections.observableArrayList();

        for (memberData member : membersListData) {
            String memberId = member.getMemberId().toLowerCase();
            String memberName = member.getName().toLowerCase();

            if (memberId.contains(searchText) || memberName.contains(searchText)) {
                searchList.add(member);
            }
        }

        memberTable.setItems(searchList);
    }

    public void logout() {
        // Hiển thị hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        // Nếu người dùng chọn OK
        if ( result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gymm/login.fxml"));
                Parent root = loader.load();

                // Tạo một Stage mới
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.initStyle(StageStyle.UNDECORATED);

                // Đóng Stage hiện tại
                Stage currentStage = (Stage) close.getScene().getWindow();
                currentStage.close();

                // Hiển thị Stage mới
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void close(){
        javafx.application.Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUsername();
        showMember();

    }
}
