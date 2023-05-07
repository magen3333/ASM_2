package net.javaguides.springboot.springsecurity.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.springsecurity.model.GeneratedShifts;
import net.javaguides.springboot.springsecurity.model.Shifts;

@Repository
public interface GeneratedShiftsRepository extends JpaRepository<GeneratedShifts, Long> {

	GeneratedShifts findByYearAndMonth(int year, int month);
		
}
