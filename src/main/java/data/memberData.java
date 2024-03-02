package data;

import java.util.Date;

public class memberData {
    private Integer id;
    private String memberId;
    private String name;
    private String address;
    private Integer phoneNum;
    private String gender;
    private String schedule;
    private Date startDate;
    private Date endDate;
    private Double price;
    private String status;

    public memberData(Integer id, String memberId, String name, String address, Integer phoneNum, String gender, String schedule, Date startDate, Date endDate, Double price, String status) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public String getSchedule() {
        return schedule;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
