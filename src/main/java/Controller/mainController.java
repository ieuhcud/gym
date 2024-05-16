package Controller;

import data.coachData;
import data.database;
import data.memberData;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class mainController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Button coach_btn;

    @FXML
    private Button coaches_addBtn;

    @FXML
    private TextField coaches_address;

    @FXML
    private TextField coaches_coachID;

    @FXML
    private TableColumn<coachData, String> coaches_col_address;

    @FXML
    private TableColumn<coachData, String> coaches_col_coachID;

    @FXML
    private TableColumn<coachData, String> coaches_col_gender;

    @FXML
    private TableColumn<coachData, String> coaches_col_name;

    @FXML
    private TableColumn<coachData, String> coaches_col_phoneNum;

    @FXML
    private TableColumn<coachData, String> coaches_col_status;

    @FXML
    private Button coaches_deleteBtn;

    @FXML
    private AnchorPane coaches_form;

    @FXML
    private ComboBox<String> coaches_gender;

    @FXML
    private TextField coaches_name;

    @FXML
    private TextField coaches_phoneNum;

    @FXML
    private Button coaches_resetBtn;

    @FXML
    private ComboBox<String> coaches_status;

    @FXML
    private TableView< coachData > coaches_tableView;

    @FXML
    private Button coaches_updateBtn;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NM;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private BarChart<?, ?> dashboard_incomeChart;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button members_addBtn;

    @FXML
    private TextField members_address;

    @FXML
    private Button members_btn;

    @FXML
    private Button members_clearBtn;

    @FXML
    private TableColumn<memberData, String> members_col_Status;

    @FXML
    private TableColumn<memberData, String> members_col_address;

    @FXML
    private TableColumn<memberData, String> members_col_customerID;

    @FXML
    private TableColumn<memberData, String> members_col_endDay;

    @FXML
    private TableColumn<memberData, String> members_col_gender;

    @FXML
    private TableColumn<memberData, String> members_col_name;

    @FXML
    private TableColumn<memberData, String> members_col_phoneNum;

    @FXML
    private TableColumn<memberData, String> members_col_schedule;

    @FXML
    private TableColumn<memberData, String> members_col_startDay;

    @FXML
    private TextField members_customerid;

    @FXML
    private Button members_deleteBtn;

    @FXML
    private DatePicker members_endDate;

    @FXML
    private AnchorPane members_form;

    @FXML
    private ComboBox<String> members_gender;

    @FXML
    private TextField members_name;

    @FXML
    private TextField members_phoneNum;

    @FXML
    private ComboBox<String> members_schedule;

    @FXML
    private DatePicker members_startDate;

    @FXML
    private ComboBox<String> members_status;

    @FXML
    private TableView<memberData> members_tableView;

    @FXML
    private Button members_updateBtn;
    @FXML
    private Button minimize;

    @FXML
    private AnchorPane payment_Form;

    @FXML
    private TextField payment_amount;

    @FXML
    private Button payment_btn;

    @FXML
    private Label payment_change;

    @FXML
    private TableColumn<memberData, String> payment_col_customerID;

    @FXML
    private TableColumn<memberData, String> payment_col_endDate;

    @FXML
    private TableColumn<memberData, String> payment_col_name;

    @FXML
    private TableColumn<memberData, String> payment_col_startDate;

    @FXML
    private TableColumn<memberData, String> payment_col_status;

    @FXML
    private ComboBox<?> payment_customerID;

    @FXML
    private ComboBox<?> payment_name;

    @FXML
    private Button payment_payBtn;

    @FXML
    private TableView<memberData> payment_tableView;

    @FXML
    private Label payment_total;
    @FXML
    private Label username;

    //Tương tác CSDL
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void displayUsername(){
        String user = database.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);
    }

    public void emptyFields(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Vui lòng nhập hết vào các ô trống!");
        alert.showAndWait();
    }
    public void dashBoardNM(){
        String sql = "SELECT COUNT(id) FROM member WHERE status = 'Paid'";
        connect = database.connecDb();
        int nm = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()){
                nm = result.getInt("COUNT(id)");
            }
            dashboard_NM.setText(String.valueOf(nm));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dashBoardTC(){
        String sql = "SELECT COUNT(id) AS coachCount FROM coach WHERE status = 'Active'";

        connect = database.connecDb();
        int tc = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()){
                tc = result.getInt("coachCount");
            }
            dashboard_NC.setText(String.valueOf(tc));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Hàm chuyển đổi giá trị thành tên viết tắt
    public static String formatCurrency(double amount) {
        if (amount >= 1_000_000_000) {
            return String.format("%.2fB", amount / 1_000_000_000);
        } else if (amount >= 1_000_000) {
            return String.format("%.2fM", amount / 1_000_000);
        } else if (amount >= 1_000) {
            return String.format("%.2fK", amount / 1_000);
        } else {
            return String.format("%.2f", amount);
        }
    }
    public void dashboardTI(){
        String sql = "SELECT SUM(price) FROM member WHERE status = 'Paid'";
        connect = database.connecDb();

        double ti = 0;
        try{

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()){
                BigDecimal tiBigDecimal = result.getBigDecimal("SUM(price)");
                ti = tiBigDecimal.doubleValue();
                dashboard_TI.setText(formatCurrency(ti) + " VND");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dashboardChart() {
        // Xóa dữ liệu hiện tại của biểu đồ
        dashboard_incomeChart.getData().clear();
        // Chuỗi truy vấn SQL để lấy thông tin thu nhập
        String sql = "SELECT startDate, SUM(price) FROM member WHERE status = 'Paid' GROUP BY startDate ORDER BY TIMESTAMP (startDate) ASC LIMIT 10 ";
        // Kết nối đến cơ sở dữ liệu
        connect = database.connecDb();
        // Tạo đối tượng Series cho biểu đồ cột
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Thu Nhập"); // Đặt tên cho loạt dữ liệu
        try {
            // Chuẩn bị câu lệnh SQL
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            // Duyệt qua kết quả truy vấn
            while (result.next()) {
                // Lấy thời điểm bắt đầu (startDate) từ cột 1
                String startDate = result.getString(1);
                // Lấy tổng giá trị (SUM(price)) từ cột 2
                Double totalIncome = result.getDouble(2);
                // Thêm dữ liệu vào đối tượng series
                series.getData().add(new XYChart.Data<>(startDate, totalIncome));
            }
            // Thêm đối tượng series vào biểu đồ cột
            dashboard_incomeChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //phương thức của coach---------------------------------
    private ObservableList<coachData>coachesListData;
    public void coachesShowData(){
        coachesListData = coachesDataList();

        coaches_col_coachID.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        coaches_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coaches_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        coaches_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        coaches_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        coaches_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        coaches_tableView.setItems(coachesListData);
    }
    public void coachesAddBtn(){

        String sql = "INSERT INTO coach(coachId, name, address, gender, phoneNum, status) " + "VALUES(?,?,?,?,?,?)";
        connect = database.connecDb();

        try{
            Alert alert;

            if (coaches_coachID.getText().isEmpty() || coaches_name.getText().isEmpty()
                    || coaches_address.getText().isEmpty()
                    || coaches_gender.getSelectionModel().getSelectedItem() == null
                    || coaches_phoneNum.getText().isEmpty()
                    || coaches_status.getSelectionModel().getSelectedItem() == null){
                emptyFields();
            }else {
                String checkData = "SELECT coachId FROM coach WHERE coachId = '" +coaches_coachID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Coach ID: " + coaches_coachID.getText() + "đã tồn tại!");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, coaches_coachID.getText());
                    prepare.setString(2, coaches_name.getText());
                    prepare.setString(3, coaches_address.getText());
                    prepare.setString(4, (String)coaches_gender.getSelectionModel().getSelectedItem());
                    prepare.setInt(5, Integer.parseInt(coaches_phoneNum.getText()));
                    prepare.setString(6, (String)coaches_status.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm thành công");
                    alert.showAndWait();

                    // Cập nhật TableView hiển thị dữ liệu
                    coachesShowData();
                    // Xóa nội dung trong tất cả các trường nhập liệu
                    coachesClearBtn();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void coachesUpdateBtn(){

        String sql = "UPDATE coach SET name = '"
                + coaches_name.getText() +"', address = '"
                + coaches_address.getText() + "', gender = '"
                + coaches_gender.getSelectionModel().getSelectedItem() + "', phoneNum = '"
                + coaches_phoneNum.getText() + "', status = '"
                + coaches_status.getSelectionModel().getSelectedItem() + "'WHERE coachId = '"
                + coaches_coachID.getText()+"'";

        connect = database.connecDb();
        try {
            Alert alert;
            if (coaches_coachID.getText().isEmpty() || coaches_name.getText().isEmpty()
                    || coaches_address.getText().isEmpty()
                    || coaches_gender.getSelectionModel().getSelectedItem() == null
                    || coaches_phoneNum.getText().isEmpty()
                    || coaches_status.getSelectionModel().getSelectedItem() == null){
                emptyFields();
            }else {
                prepare = connect.prepareStatement(sql);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Thay đổi thành công");
                alert.showAndWait();

                coachesShowData();
                coachesClearBtn();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void coachesDeleteBtn(){

        String sql = "DELETE FROM coach WHERE coachId = '" + coaches_coachID.getText() + "'";

        connect = database.connecDb();
        try {
            Alert alert;

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn XÓA HLV có ID: " + coaches_coachID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa thành công");
                    alert.showAndWait();

                    //Update tableView
                    coachesShowData();
                    //Clear all field
                    coachesClearBtn();
                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy bỏ xóa");
                    alert.showAndWait();
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void coachesClearBtn(){
        coaches_coachID.setText("");
        coaches_name.setText("");
        coaches_address.setText("");
        coaches_gender.getSelectionModel().clearSelection();
        coaches_phoneNum.setText("");
        coaches_status.getSelectionModel().clearSelection();
    }
    private String gender[] = {"Male", "Female", "Others"};
    public void coachGenderList(){
        List<String> genderL = new ArrayList<>();

        for(String data : gender){
            genderL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(genderL);
        coaches_gender.setItems(listData);

    }
    private String coachStatus[] = {"Active", "Inactive"};
    public void coachStatusList(){
        List<String> coachList = new ArrayList<>();

        for(String data : coachStatus){
            coachList.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(coachStatus);
        coaches_status.setItems(listData);
    }
    public ObservableList<coachData> coachesDataList(){
        ObservableList<coachData> listData = FXCollections.observableArrayList();

        String sql = " SELECT * FROM coach";

        connect = database.connecDb();
        try{

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            coachData cd;

            while (result.next()){
                cd = new coachData(result.getInt("id"),
                        result.getString("coachId"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getString("gender"),
                        result.getInt("phoneNum"),
                        result.getString("status"));
                listData.add(cd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    public void coachesSelect(){
        coachData cd = coaches_tableView.getSelectionModel().getSelectedItem();
        int num = coaches_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1)return;

        coaches_coachID.setText(cd.getCoachId());
        coaches_name.setText(cd.getName());
        coaches_address.setText((cd.getAddress()));
        coaches_phoneNum.setText(String.valueOf(cd.getPhoneNum()));
    }

    //Phương thức của member-------------------------------------------
    private ObservableList<memberData> membersListData;
    public void membersShowData(){
        membersListData = membersDataList();

        members_col_customerID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        members_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        members_col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        members_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        members_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        members_col_schedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        members_col_startDay.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        members_col_endDay.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        members_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

        members_tableView.setItems(membersListData);

    }
    private int totalDay;
    private double price;
    public void membersAddBtn(){
        String sql = "INSERT INTO member (memberId, name, address, phoneNum, gender, schedule, startDate, endDate, price, status)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?)";
        connect = database.connecDb();

        try {
            Alert alert;

            if (members_customerid.getText().isEmpty() || members_name.getText().isEmpty()
                    || members_phoneNum.getText().isEmpty() || members_address.getText().isEmpty()
                    || members_gender.getSelectionModel().getSelectedItem() == null
                    || members_schedule.getSelectionModel().getSelectedItem() == null
                    || members_startDate.getValue() == null
                    || members_endDate.getValue() == null) {
                emptyFields();
            } else {
                if (members_phoneNum.getText().length() != 10) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Số điện thoại phải có 10 số.");
                    alert.showAndWait();
                } else {
                    String checkData = "SELECT memberId FROM member WHERE memberId = '" + members_customerid.getText() + "'";
                    statement = connect.createStatement();
                    result = statement.executeQuery(checkData);

                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Member ID: " + members_customerid.getText() + " đã tồn tại!");
                        alert.showAndWait();
                    } else {
                        // Tiếp tục xử lý khi phoneNum hợp lệ
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, members_customerid.getText());
                        prepare.setString(2, members_name.getText());
                        prepare.setString(3, members_address.getText());
                        prepare.setInt(4, Integer.parseInt(members_phoneNum.getText()));
                        prepare.setString(5, (String) members_gender.getSelectionModel().getSelectedItem());
                        prepare.setString(6, (String) members_schedule.getSelectionModel().getSelectedItem());
                        prepare.setString(7, String.valueOf(members_startDate.getValue()));
                        prepare.setString(8, String.valueOf(members_endDate.getValue()));

                        // Tính toán số ngày giữa startDate và endDate
                        totalDay = (int) ChronoUnit.DAYS.between(members_startDate.getValue(), members_endDate.getValue());

                        // Tính giá trị price dựa trên số ngày và mức giá 10,000 VND/ngày
                        price = (totalDay * 10000);

                        prepare.setString(9, String.valueOf(price));
                        prepare.setString(10, (String) members_status.getSelectionModel().getSelectedItem());

                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Đã thêm thành công.");
                        alert.showAndWait();

                        // Cập nhật TableView hiển thị dữ liệu
                        membersShowData();
                        membersClear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void memberUpdate(){
        // Tính số ngày giữa members_startDate và members_endDate
        totalDay = (int)ChronoUnit.DAYS.between(members_startDate.getValue(), members_endDate.getValue());
        price = (totalDay * 10000);

        String sql = "UPDATE member SET name = '"
                + members_name.getText() + "', address = '"
                + members_address.getText() + "', phoneNum = '"
                + members_phoneNum.getText() +"', gender = '"
                + members_gender.getSelectionModel().getSelectedItem() + "', schedule = '"
                + members_schedule.getSelectionModel().getSelectedItem() + "', startDate = '"
                + members_startDate.getValue() + "', endDate = '"
                + members_endDate.getValue() + "', price = '"
                + String.valueOf(price) + "', status = '"
                + members_status.getSelectionModel().getSelectedItem() + "' WHERE memberId = '"
                + members_customerid.getText() + "'";
        connect = database.connecDb();

        try {
            Alert alert;

                prepare = connect.prepareStatement(sql);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Thay đổi thành công");
                alert.showAndWait();

                membersShowData();
                membersClear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void membersDelete(){

        String sql = "DELETE FROM member WHERE memberId = '"
                + members_customerid.getText() + "'";
        connect = database.connecDb();

        try {

            Alert alert;

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn xóa thành viên có ID: " + members_customerid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Xóa thành công!");
                    alert.showAndWait();

                    membersShowData();
                    membersClear();
                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy bỏ xóa!");
                    alert.showAndWait();
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void membersClear(){
        members_customerid.setText("");
        members_name.setText("");
        members_address.setText("");
        members_phoneNum.setText("");
        members_gender.getSelectionModel().clearSelection();
        members_schedule.getSelectionModel().clearSelection();
        members_startDate.setValue(null);
        members_endDate.setValue(null);
        members_status.getSelectionModel().clearSelection();

    }
    public void membersGender(){
        List<String> genderList = new ArrayList<>();

        for(String data : gender){
            genderList.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(genderList);
        members_gender.setItems(listData);
    }
    private String[] scheduleList = {"9AM-11AM", "11AM-1PM", "1PM-3PM", "3PM-5PM", "5PM-7PM"};
    public void membersSchedule(){
        List<String> s1 = new ArrayList<>();

        for (String data : scheduleList){
            s1.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(s1);
        members_schedule.setItems(listData);
    }
    private String memberStatus[] = {"Paid", "Unpaid"};
    public void membersStatus(){
        List<String> ms = new ArrayList<>();

        for (String data : memberStatus){
            ms.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(memberStatus);
        members_status.setItems(listData);
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
    public void membersSelect(){
        memberData md = members_tableView.getSelectionModel().getSelectedItem();
        int num = members_tableView.getSelectionModel().getSelectedIndex();

        if ((num-1) < -1)return;

        members_customerid.setText(md.getMemberId());
        members_name.setText(md.getName());
        members_address.setText(md.getAddress());
        members_phoneNum.setText(String.valueOf(md.getPhoneNum()));
        members_startDate.setValue(LocalDate.parse(String.valueOf(md.getStartDate())));
        members_endDate.setValue(LocalDate.parse(String.valueOf(md.getEndDate())));

        members_gender.setValue(String.valueOf(md.getGender()));
        members_schedule.setValue(String.valueOf(md.getSchedule()));
        members_status.setValue(String.valueOf(md.getStatus()));
    }

    //Phương thức thanh toán--------------------------------------------------
    public void paymentMemberId(){
        String sql = "SELECT memberId FROM member WHERE status = 'Unpaid' ";

        connect = database.connecDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                listData.add(result.getString("memberId"));
            }
            payment_customerID.setItems(listData);

            paymentName();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void paymentName(){
        String sql = "SELECT name FROM member WHERE memberId = '"
                + payment_customerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connecDb();

        try {
            ObservableList listData =  FXCollections.observableArrayList();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()){
                listData.add(result.getString("name"));
            }
            payment_name.setItems(listData);

            paymentDisplayTotal();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private double totalP;
    public void displayTotal(){
        String sql = "SELECT price FROM member WHERE name = '"
                + payment_name.getSelectionModel().getSelectedItem() + "' and memberId = '"
                + payment_customerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connecDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()){
                totalP = result.getDouble("price");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void paymentDisplayTotal(){
        displayTotal();
        payment_total.setText(String.valueOf(totalP) + " vnd");
    }
    private double amount;
    private double change;
    public void paymentAmount() {
        displayTotal();

        Alert alert;

        if (payment_amount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Dữ liệu không hợp lệ.");
            alert.showAndWait();

            payment_amount.setText("");
        } else {
            try {
                amount = Double.parseDouble(payment_amount.getText());

                if (amount >= totalP) {
                    change = amount - totalP;
                    payment_change.setText(String.format("%.2f vnd", change));
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Số tiền không đủ");
                    alert.showAndWait();

                    payment_amount.setText("");
                    change = 0;
                    amount = 0;
                }
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Dữ liệu không hợp lệ");
                alert.showAndWait();

                payment_amount.setText("");
            }
        }
    }
    public void paymentPayBtn() {
        String sql = "UPDATE member SET status = 'Paid' WHERE memberId = '"
                + payment_customerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.connecDb();

        try {
            Alert alert;
            if (totalP == 0 || payment_amount.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Có lỗi xảy ra. Vui lòng kiểm tra lại.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn muốn thanh toán?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.orElse(null) == ButtonType.OK) {
                    prepare = connect.prepareStatement(sql);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thanh toán thành công!");
                    alert.showAndWait();

                    paymentShowData();
                    paymentClear();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã hủy bỏ");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void paymentClear(){
        payment_customerID.getSelectionModel().getSelectedItem();
        payment_name.getSelectionModel().getSelectedItem();
        payment_total.setText("0 vnd");
        payment_amount.setText("");
        payment_change.setText("0 vnd");

        amount = 0;
        change = 0;
        totalP = 0;
    }
    public ObservableList<memberData> paymentDataList(){

        ObservableList<memberData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM member";

        connect = database.connecDb();

        try {
            memberData md;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()){
                md = new memberData(result.getInt("id"),
                        result.getString("memberId"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getInt("phoneNum"),
                        result.getString("schedule"),
                        result.getString("gender"),
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
    private ObservableList<memberData> paymentListData;
    public void paymentShowData(){
        paymentListData = paymentDataList();

        payment_col_customerID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        payment_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        payment_col_startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        payment_col_endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        payment_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        payment_tableView.setItems(paymentListData);
    }
    public void paymentSelect(){
        memberData md = payment_tableView.getSelectionModel().getSelectedItem();
        int num = payment_tableView.getSelectionModel().getSelectedIndex();

        if ((num-1) < -1)return;

        payment_col_customerID.setText(md.getMemberId());
        payment_col_name.setText(md.getName());


    }

    //----------------------------------------------------------
    public void switchForm(ActionEvent event){

        if (event.getSource() == dashboard_btn){

            dashboard_form.setVisible(true);
            coaches_form.setVisible(false);
            members_form.setVisible(false);
            payment_Form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#2d8cff, #4694bb)");
            coach_btn.setStyle(".nav-btn");
            members_btn.setStyle(".nav-btn");
            payment_btn.setStyle(".nav-btn");

            dashBoardNM();
            dashBoardTC();
            dashboardTI();
            dashboardChart();

        } else if (event.getSource() == coach_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(true);
            members_form.setVisible(false);
            payment_Form.setVisible(false);

            coach_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#2d8cff, #4694bb)");
            members_btn.setStyle(".nav-btn");
            dashboard_btn.setStyle(".nav-btn");
            payment_btn.setStyle(".nav-btn");

            coachGenderList();
            coachStatusList();
            coachesShowData();

        }else if (event.getSource() == members_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(false);
            members_form.setVisible(true);
            payment_Form.setVisible(false);

            members_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#2d8cff, #4694bb)");
            coach_btn.setStyle(".nav-btn");
            dashboard_btn.setStyle(".nav-btn");
            payment_btn.setStyle(".nav-btn");

            membersShowData();
            membersStatus();
            membersGender();
            membersSchedule();

        }else if (event.getSource() == payment_btn) {

            dashboard_form.setVisible(false);
            coaches_form.setVisible(false);
            members_form.setVisible(false);
            payment_Form.setVisible(true);

            payment_btn.setStyle("-fx-background-color: linear-gradient(to bottom right,#2d8cff, #4694bb)");
            coach_btn.setStyle(".nav-btn");
            dashboard_btn.setStyle(".nav-btn");
            members_btn.setStyle(".nav-btn");

            paymentMemberId();
            paymentName();
            paymentShowData();
        }
    }
//    private double x = 0;
//    private double y = 0;
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
    public void minimize(){;
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    public void close(){
        javafx.application.Platform.exit();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUsername();

        dashBoardNM();
        dashBoardTC();
        dashboardTI();
        dashboardChart();

        coachGenderList();
        coachStatusList();
        coachesShowData();

        membersShowData();
        membersStatus();
        membersGender();
        membersSchedule();

        paymentMemberId();
        paymentName();
        paymentShowData();
    }
}
