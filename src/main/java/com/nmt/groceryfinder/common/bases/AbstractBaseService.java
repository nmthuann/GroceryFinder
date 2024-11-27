package com.nmt.groceryfinder.common.bases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractBaseService<T, ID, D> implements IBaseService <ID, D> {
    private final JpaRepository<T, ID> baseRepository;
    private final AbstractModelMapper<T,ID, D> modelMapper;

    public AbstractBaseService(
            JpaRepository<T, ID> baseRepository,
            AbstractModelMapper<T,ID, D> modelMapper
    ) {
        this.baseRepository = baseRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable <D> getAll() {
        if(baseRepository.count() > 30){
            throw new RuntimeException("Number of records exceeds 20. Cannot retrieve the list.");
        }
        return baseRepository.findAll().stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<D> getOneById(ID id)  {
        return baseRepository.findById(id)
                .map(modelMapper::toDto)
                .or(() -> {
                    throw new EntityNotFoundException("Entity with id " + id + " not found");
                });
    }

    public D createOne(D data) {
        T entity = modelMapper.toEntity(data);
        return modelMapper.toDto(baseRepository.save(entity));
    }

    public D updateOneById(ID id, D data) {
        T checkEntityExist = baseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));

        T updateEntity = modelMapper.toEntity(data);

        modelMapper.mapId(id,checkEntityExist, updateEntity);

        D dtoUpdated = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode originalNode = objectMapper.convertValue(checkEntityExist, JsonNode.class);
            JsonNode updateNode = objectMapper.convertValue(updateEntity, JsonNode.class);
            ((ObjectNode) originalNode).setAll((ObjectNode) updateNode);

            T entityUpdated = objectMapper.treeToValue(originalNode, (Class<T>) checkEntityExist.getClass());

            dtoUpdated = modelMapper.toDto(baseRepository.save(entityUpdated));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return dtoUpdated;
    }


    public void deleteOneById(ID id) {
        if (baseRepository.existsById(id)) {
            baseRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found");
        }
    }

    public Page<D> getAllPaginated(Pageable pageable) {
        Page<T> entitiesPage = baseRepository.findAll(pageable);
        return entitiesPage.map(modelMapper::toDto);
    }
}