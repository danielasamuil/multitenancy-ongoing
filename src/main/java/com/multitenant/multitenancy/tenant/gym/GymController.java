package com.multitenant.multitenancy.tenant.gym;

import com.multitenant.multitenancy.tenant.staff.StaffDTO;
import com.multitenant.multitenancy.tenant.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.multitenant.multitenancy.helpers.UrlMapping.ENTITY;
import static com.multitenant.multitenancy.helpers.UrlMapping.GYM;


@RestController
@RequestMapping(GYM)
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8091",  allowCredentials = "true")
public class GymController {
    private final GymService gymService;

    @GetMapping()
    public List<GymDTO> findAll() {
        return gymService.findAll();
    }

    @PostMapping()
    public GymDTO create(@RequestBody GymDTO gym) {
        return gymService.create(gym);
    }

    @PutMapping(ENTITY)
    public GymDTO edit(@PathVariable Integer id, @RequestBody GymDTO gymDTO) {
        return gymService.update(id, gymDTO);
    }

    @DeleteMapping()
    public void deleteAll() {
        gymService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Integer id) {
        gymService.delete(id);
    }
}
