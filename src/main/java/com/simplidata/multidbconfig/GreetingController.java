package com.simplidata.multidbconfig;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplidata.multidbconfig.dbservice.CompanyDBService;
import com.simplidata.multidbconfig.dbservice.EmployeeDBService;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    CompanyDBService companyDbservice;
    
    @Autowired
    EmployeeDBService employeeDbservice;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        
        companyDbservice.getData();
        
        employeeDbservice.getData();
        
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    

    
    
}