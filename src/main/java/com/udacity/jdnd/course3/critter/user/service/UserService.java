package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;


@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPetId(long petId) {
        return petRepository.getOne(petId).getOwner();
    }

    public long saveEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployeesForService(Set<EmployeeSkill> employeeSkills, LocalDate date) {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findDistinctAllBySkillsInAndDaysAvailable(employeeSkills, date.getDayOfWeek())
                .forEach(e -> { if (e.getSkills().containsAll(employeeSkills)) employees.add(e); });

        return  employees;
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }
}
