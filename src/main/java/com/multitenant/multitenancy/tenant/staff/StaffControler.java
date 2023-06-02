package com.multitenant.multitenancy.tenant.staff;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.multitenant.multitenancy.helpers.UrlMapping.ENTITY;
import static com.multitenant.multitenancy.helpers.UrlMapping.STAFF;

@RestController
@RequestMapping(STAFF)
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8091",  allowCredentials = "true")
public class StaffControler {
    private final StaffService staffService;

    @GetMapping()
    public List<StaffDTO> findAll() {
        return staffService.findAll();
    }

    @PostMapping()
    public StaffDTO create(@RequestBody StaffDTO staff) {
        return staffService.create(staff);
    }

    @PutMapping(ENTITY)
    public StaffDTO edit(@PathVariable Integer id, @RequestBody StaffDTO staffDTO) {
        return staffService.update(id, staffDTO);
    }

    @DeleteMapping()
    public void deleteAll() {
        staffService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Integer id) {
        staffService.delete(id);
    }
}
