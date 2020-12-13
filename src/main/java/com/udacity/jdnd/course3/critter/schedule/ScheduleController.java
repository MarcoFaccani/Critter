package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;


/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertToScheduleDTO(scheduleService.createSchedule(convertToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertListToScheduleDTO(scheduleService.getAllSChedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertListToScheduleDTO(scheduleService.getScheduleForPetById(petId));

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertListToScheduleDTO(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        scheduleService.getScheduleForCustomer(customerId)
                .forEach(s -> scheduleDTOS.addAll(convertListToScheduleDTO(s)));
        return scheduleDTOS;
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        copyProperties(scheduleDTO, schedule);

        scheduleDTO.getPetIds().stream().map(petRepository::findById).collect(Collectors.toList())
                .forEach(optionalPet -> schedule.getPets().add(optionalPet.orElseThrow(PetNotFoundException::new)));

        scheduleDTO.getEmployeeIds().stream().map(employeeRepository::findById).collect(Collectors.toList())
                .forEach(optionalEmployee -> schedule.getEmployees().add(optionalEmployee.orElseThrow(EmployeeNotFoundException::new)));

        return schedule;
    }

    private List<ScheduleDTO> convertListToScheduleDTO(List<Schedule> schedules) {
        return schedules.stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        copyProperties(deepClone(schedule), scheduleDTO);
        schedule.getEmployees().forEach(employee -> scheduleDTO.getEmployeeIds().add(employee.getId()));
        schedule.getPets().forEach(pet -> scheduleDTO.getPetIds().add(pet.getId()));
        return scheduleDTO;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T object) {
        return (T) SerializationUtils.deserialize(SerializationUtils.serialize(object));
    }
}
