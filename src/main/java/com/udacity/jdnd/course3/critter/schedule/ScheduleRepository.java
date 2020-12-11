package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    List<Schedule> findAllByPetIds(long petId);
    List<Schedule> findAllByEmployeeIds(long employeeId);

}
