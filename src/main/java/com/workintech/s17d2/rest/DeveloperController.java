package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    // ❗ private DEĞİL → testler erişebilsin diye
    public Map<Integer, Developer> developers;


    private final Taxable taxable;

    // Constructor Injection
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
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public Developer addDeveloper(@RequestBody Developer dev) {

        Developer newDev;
        double salary = dev.getSalary();

        if (dev.getExperience() == Experience.JUNIOR) {
            salary -= salary * taxable.getSimpleTaxRate() / 100;
            newDev = new JuniorDeveloper(dev.getId(), dev.getName(), salary);

        } else if (dev.getExperience() == Experience.MID) {
            salary -= salary * taxable.getMiddleTaxRate() / 100;
            newDev = new MidDeveloper(dev.getId(), dev.getName(), salary);

        } else {
            salary -= salary * taxable.getUpperTaxRate() / 100;
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

        developers.remove(id);
        dev.setId(id);
        return addDeveloper(dev);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Developer deleteDeveloper(@PathVariable Integer id) {
        return developers.remove(id);
    }
}
