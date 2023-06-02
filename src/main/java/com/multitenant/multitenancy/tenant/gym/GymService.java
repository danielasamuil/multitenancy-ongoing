package com.multitenant.multitenancy.tenant.gym;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    private final GymMapper gymMapper;

    public Gym findById(Integer id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gym not found: " + id));
    }

    public List<GymDTO> findAll() {

        List<GymDTO> gym = gymRepository.findAll().stream().map(
                        gymMapper::toDTO)
                .collect(toList());

        return gymRepository.findAll().stream().map(
                        gymMapper::toDTO)
                .collect(toList());
    }

    public void delete(int id) {

        gymRepository.deleteById(id);
    }

    public void deleteAll() {

        gymRepository.deleteAll();
    }

    public GymDTO create(GymDTO gymDTO) {

        Gym gym = gymMapper.fromDTO(gymDTO);

        gym.setAddress(gymDTO.getAddress());
        gym.setManager(gymDTO.getManager());

        return gymMapper.toDTO(gymRepository.save(gym));
    }

    public GymDTO update(Integer id, GymDTO gymDTO) {

        Gym gym = findById(id);

        gym.setAddress(gymDTO.getAddress());
        gym.setManager(gymDTO.getManager());

        return gymMapper.toDTO(
                gymRepository.save(gym)
        );
    }
}
