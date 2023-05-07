package net.javaguides.springboot.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "shifts", uniqueConstraints = @UniqueConstraint(columnNames={"email", "year", "month"}))
public class Shifts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String email;
    private int month;
    private int year;
   
    private String shifts;
    
    public Shifts()
    {
    }
    
    public Shifts (String email, int year, int month, String shifts)
    {
    	this.email = email;
    	this.month = month;
    	this.year = year;
    	this.shifts = shifts;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getShifts() {
		return shifts;
	}

	public void setShifts(String shifts) {
		this.shifts = shifts;
	}
	
	@Override
	public String toString() {
		return "Shifts [id=" + id + ", email=" + email + ", month=" + month + ", year=" + year + ", shifts=" + shifts
				+ "]";
	}    
}
