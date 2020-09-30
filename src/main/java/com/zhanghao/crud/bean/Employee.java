package com.zhanghao.crud.bean;
//
//import javax.validation.constraints.Pattern;
//
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.Length;


public class Employee {
    private Integer empId;

//    @Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})"
//    		,message="鐢ㄦ埛鍚嶅繀椤绘槸2-5浣嶄腑鏂囨垨鑰�6-16浣嶈嫳鏂囧拰鏁板瓧鐨勭粍鍚�")
    private String empName;

    private String gender;

    //@Email
//    @Pattern(regexp="^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",
//    		message="閭鏍煎紡涓嶆纭�")
    private String email;

    private Integer dId;
    
    //甯屾湜鏌ヨ鍛樺伐鐨勫悓鏃堕儴闂ㄤ俊鎭篃鏄煡璇㈠ソ鐨�
    private Department department;
    

    
    @Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName
				+ ", gender=" + gender + ", email=" + email + ", dId=" + dId
				+ "]";
	}

	public Employee() {
		super();
	}

	public Employee(Integer empId, String empName, String gender, String email,
			Integer dId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.gender = gender;
		this.email = email;
		this.dId = dId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}