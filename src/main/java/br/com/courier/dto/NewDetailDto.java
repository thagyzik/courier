package br.com.courier.dto;

import br.com.courier.model.Detail;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class NewDetailDto {

    private Long id;

    @Size(min = 3, max = 30, message = "Invalid First Name")
    private String firstName;

    @Size(min = 3, max = 30, message = "Invalid Last Name")
    private String lastName;

    @Size(max = 30)
    private String address;

    @Size(min = 1, max = 30, message = "Invalid City")
    private String city;

    @Size(min = 1, max = 30, message = "Invalid State")
    private String state;

    @Size(min = 6, max = 6, message = "Invalid Zip Code")
    private String zip;

    @Min(value = 18, message = "Age must be 18 or more")
    private Integer age;

    @Size(min = 10, max = 10, message = "Invalid mobile number")
    private String mobile;

    public NewDetailDto(){

    }

    public NewDetailDto(Detail detail) {
        this.firstName = detail.getFirstName();
        this.lastName = detail.getLastName();
        this.address = detail.getAddress();
        this.city = detail.getCity();
        this.state = detail.getState();
        this.zip = String.valueOf(detail.getZip());
        this.age = detail.getAge();
        this.mobile = String.valueOf(detail.getMobile());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Detail toDetail() {
        Long mobileLong = null;
        Integer zipInt = null;

        if (mobile != null){

            mobileLong = Long.valueOf(mobile);

        }

        if (zip != null){

            zipInt = Integer.parseInt(zip);

        }

        Detail detail = new Detail(firstName, lastName, address, city, state, zipInt, age, mobileLong);
        detail.setId(id);
        return detail;
    }

}
