package net.javaguides.springboot.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.springsecurity.model.Shifts;

@Repository
public interface ShiftsRepository extends JpaRepository<Shifts, Long> {

	Shifts findByEmailAndYearAndMonth(String email, int year, int month);
	
	List<Shifts> findByYearAndMonth(int year, int month);
	
	void deleteByYearAndMonth(int year, int month);
	
}
