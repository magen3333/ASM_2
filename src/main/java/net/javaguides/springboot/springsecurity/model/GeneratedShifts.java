package net.javaguides.springboot.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "generated_shifts", uniqueConstraints = @UniqueConstraint(columnNames={"year", "month"}))
public class GeneratedShifts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int month;
    private int year;
    private int numberOfShiftsPerDay;

   
    private String shifts;
    
    public GeneratedShifts()
    {
    }
    
    public GeneratedShifts (int year, int month, int numberOfShiftsPerDay, String shifts)
    {
    	this.month = month;
    	this.year = year;
    	this.shifts = shifts;
    	this.numberOfShiftsPerDay = numberOfShiftsPerDay;
    }

	public int getNumberOfShiftsPerDay() {
		return numberOfShiftsPerDay;
	}

	public void setNumberOfShiftsPerDay(int numberOfShiftsPerDay) {
		this.numberOfShiftsPerDay = numberOfShiftsPerDay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "Shifts [id=" + id + ", month=" + month + ", year=" + year + ", shifts=" + shifts
				+ "]";
	}    
}
