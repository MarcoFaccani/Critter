package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSChedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPetById(long petId) {
        return scheduleRepository.findAllByPetsContaining(petRepository.findById(petId).orElseThrow(PetNotFoundException::new));
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployeesContaining(employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new));
    }

    public List<List<Schedule>> getScheduleForCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        return customer.getPets().stream().map(Pet::getId).map(this::getScheduleForPetById).collect(Collectors.toList());
    }


}
