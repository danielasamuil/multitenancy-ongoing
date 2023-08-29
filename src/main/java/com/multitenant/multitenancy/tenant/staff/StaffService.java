package com.multitenant.multitenancy.tenant.staff;

import com.multitenant.multitenancy.meta.user.ERole;
import com.multitenant.multitenancy.meta.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    private final StaffRoleRepository staffRoleRepository;

    public Staff findById(Integer id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found: " + id));
    }

    public List<StaffDTO> findAll() {

        List<StaffDTO> staff = staffRepository.findAll().stream().map(
                staffMapper::toDTO).toList();

        return staffRepository.findAll().stream().map(
                        staffMapper::toDTO)
                .collect(toList());
    }

    public void delete(int id) {

        staffRepository.deleteById(id);
    }

    public void deleteAll() {

        staffRepository.deleteAll();
    }

    public StaffDTO create(StaffDTO staffDTO) {
        Set<StaffRole> roles = new HashSet<>();

        StaffRole defaultRole = new StaffRole();

        if(staffDTO.getRoles() == null || staffDTO.getRoles().get(0).equals("TRAINER")) {
            defaultRole = staffRoleRepository.findByName(StaffERole.TRAINER)
                    .orElseThrow(() -> new RuntimeException("Cannot find TRAINER role"));
        } else if(staffDTO.getRoles().get(0).equals("NUTRITIONIST")){
            defaultRole = staffRoleRepository.findByName(StaffERole.NUTRITIONIST)
                    .orElseThrow(() -> new RuntimeException("Cannot find NUTRITIONIST role"));
        } else if(staffDTO.getRoles().get(0).equals("MASSAGE_THERAPIST")){
            defaultRole = staffRoleRepository.findByName(StaffERole.MASSAGE_THERAPIST)
                    .orElseThrow(() -> new RuntimeException("Cannot find MASSAGE_THERAPIST role"));
        }

        roles.add(defaultRole);

        Staff staff = staffMapper.fromDTO(staffDTO);

        staff.setAddress(staffDTO.getAddress());
        staff.setName(staffDTO.getName());
        staff.setRoles(roles);

        return staffMapper.toDTO(staffRepository.save(staff));
    }

    public StaffDTO update(Integer id, StaffDTO staffDTO) {

        Staff staff = findById(id);

        staff.setAddress(staffDTO.getAddress());
        staff.setName(staffDTO.getName());

        return staffMapper.toDTO(
                staffRepository.save(staff)
        );
    }
}
