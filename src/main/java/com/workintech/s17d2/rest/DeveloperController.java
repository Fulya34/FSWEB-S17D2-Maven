package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private Map<Integer, Developer> developers;
    private final Taxable taxable;

    // Dependency Injection (Constructor Injection)
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    // Map initialization
    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    // GET ALL
    @GetMapping
    public List<Developer> getAllDevelopers() {
        return new ArrayList<>(developers.values());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable Integer id) {
        return developers.get(id);
    }

    // POST
    @PostMapping
    public Developer addDeveloper(@RequestBody Developer dev) {

        Developer newDev;
        double salary = dev.getSalary();

        if (dev.getExperience() == Experience.JUNIOR) {
            salary -= salary * taxable.getSimpleTaxRate();
            newDev = new JuniorDeveloper(dev.getId(), dev.getName(), salary);

        } else if (dev.getExperience() == Experience.MID) {
            salary -= salary * taxable.getMiddleTaxRate();
            newDev = new MidDeveloper(dev.getId(), dev.getName(), salary);

        } else {
            salary -= salary * taxable.getUpperTaxRate();
            newDev = new SeniorDeveloper(dev.getId(), dev.getName(), salary);
        }

        developers.put(dev.getId(), newDev);
        return newDev;
    }

    // PUT
    @PutMapping("/{id}")
    public Developer updateDeveloper(
            @PathVariable Integer id,
            @RequestBody Developer dev) {

        developers.put(id, dev);
        return dev;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Developer deleteDeveloper(@PathVariable Integer id) {
        return developers.remove(id);
    }
}