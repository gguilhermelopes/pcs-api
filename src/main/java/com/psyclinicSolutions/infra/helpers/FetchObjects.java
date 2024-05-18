package com.psyclinicSolutions.infra.helpers;

import com.psyclinicSolutions.infra.exceptions.DataNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FetchObjects {
    public <T> T fetchObject(UUID id, JpaRepository<T, UUID> repository){
        return repository.findById(id).
                orElseThrow(() -> new DataNotFoundException("Entidade " + repository.getClass().getName() + " não encontrada." ));
    }
}
