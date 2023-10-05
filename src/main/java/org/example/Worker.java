package org.example;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Worker {
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    private Long ID;
    private String fullName;

    private Date birthDate;

    private String gender;

    public String getFullName() {
        return fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Worker(String fullName, Date birthDate, String gender) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Worker() {
    }

    public void saveWorker(DataBaseConnect connect){
        connect.saveItem(this);
    }

    public int fullYears(){
        return Period.between(birthDate.toLocalDate(),LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return
                "fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", full age='" + fullYears() ;
    }
}
