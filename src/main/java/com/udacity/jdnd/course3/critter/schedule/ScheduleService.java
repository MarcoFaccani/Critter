package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ScheduleDTO createSchedule(Schedule schedule) {
        return convertToScheduleDTO(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> getAllSChedules() {
        List<ScheduleDTO> schedules = new ArrayList<>();
        scheduleRepository.findAll().forEach(s -> schedules.add(convertToScheduleDTO(s)));
        return schedules;
    }

    public List<ScheduleDTO> getScheduleForPetById(long petId) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        scheduleRepository.findAllByPetIds(petId).forEach(s -> schedules.add(convertToScheduleDTO(s)));
        return schedules;
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        scheduleRepository.findAllByEmployeeIds(employeeId).forEach(s -> schedules.add(convertToScheduleDTO(s)));
        return schedules;
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
        customer.getPets().stream().map(Pet::getId).map(this::getScheduleForPetById).collect(Collectors.toList()).forEach(schedules::addAll);
        //schedules.addAll(customer.getPets().stream().map(Pet::getId).map(this::getScheduleForPetById).collect(Collectors.toList()));
        //customer.getPets().forEach(petId -> schedules.addAll(this.getScheduleForPetById(petId)));
        return schedules;
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        copyProperties(schedule, scheduleDTO);
        //schedule.getPets().forEach(c -> scheduleDTO.getPetIds().add(c.getId()) );
        //schedule.getEmployees().forEach(c -> scheduleDTO.getEmployeeIds().add(c.getId()));
        return scheduleDTO;
    }
}
