package com.multitenant.multitenancy.tenant.staff;

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

        Staff staff = staffMapper.fromDTO(staffDTO);

        staff.setRoles(new HashSet<>(staffDTO.getRoles()));
        staff.setAddress(staffDTO.getAddress());
        staff.setName(staffDTO.getName());

        return staffMapper.toDTO(staffRepository.save(staff));
    }

    public StaffDTO update(Integer id, StaffDTO staffDTO) {

        Staff staff = findById(id);

        staff.setAddress(staffDTO.getAddress());
        staff.setRoles(staffDTO.getRoles().stream().collect(Collectors.toSet()));

        return staffMapper.toDTO(
                staffRepository.save(staff)
        );
    }
}
