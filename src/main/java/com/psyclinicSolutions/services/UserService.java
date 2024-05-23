package com.psyclinicSolutions.services;

import com.psyclinicSolutions.domain.Role;
import com.psyclinicSolutions.domain.User;
import com.psyclinicSolutions.dto.UserDTO;
import com.psyclinicSolutions.dto.UserInsertDTO;
import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import com.psyclinicSolutions.infra.exceptions.DatabaseException;
import com.psyclinicSolutions.infra.helpers.FetchObjects;
import com.psyclinicSolutions.repositories.RoleRepository;
import com.psyclinicSolutions.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private FetchObjects fo;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> list = repository.findAll();
        return list.stream().map(UserDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> pagedList = repository.findAll(pageable);
        return pagedList.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new DataNotFoundException("Usuário não encontrado."));

        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO data) {
        User entity = new User();
        dataInsertToUser(data, entity);
        entity = repository.save(entity);

        return new UserDTO(entity);
    }
    @Transactional
    public UserDTO update(UUID id, UserDTO data) {
        try{
            User obj = repository.getReferenceById(id);
            dataToUser(data, obj);
            obj = repository.save(obj);
            return new UserDTO(obj);
        } catch (EntityNotFoundException exception){
            throw new DataNotFoundException("Usuário não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Usuário não encontrado.");
        }
        try{
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void dataToUser(UserDTO data, User entity) {
        Role role = fo.fetchObject(data.roleId(), roleRepository);

        entity.setName(data.name());;
        entity.setEmail(data.email());
        entity.setRole(role);
    }

    private void dataInsertToUser(UserInsertDTO data, User entity) {
        Role role = fo.fetchObject(data.roleId(), roleRepository);

        entity.setName(data.name());;
        entity.setEmail(data.email());
        entity.setPassword(passwordEncoder.encode(data.password()));
        entity.setRole(role);
    }
}
