package com.udacity.jdnd.course3.critter.user.controller;

import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        long id = userService.saveCustomer(customerDTO);
         customerDTO.setId(id);
         return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> output = new ArrayList<>();
        userService.getAllCustomers().forEach( c -> output.add(convertToCustomerDTO(c)));
        return output;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
       return convertToCustomerDTO(userService.getOwnerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        long id = userService.saveEmployee(employeeDTO);
        employeeDTO.setId(id);
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertToEmployeeDTO(userService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        List<EmployeeDTO> output = new ArrayList<>();
        userService.getAllEmployeesForService(employeeRequestDTO)
                .forEach(e -> output.add(convertToEmployeeDTO(e)));
        return output;
    }

    private CustomerDTO convertToCustomerDTO(Customer pet) {
        CustomerDTO customerDTO = new CustomerDTO();
        copyProperties(pet, customerDTO);
        return customerDTO;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee pet) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        copyProperties(pet, employeeDTO);
        return employeeDTO;
    }

}
