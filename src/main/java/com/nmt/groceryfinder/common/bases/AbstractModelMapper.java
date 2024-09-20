package com.nmt.groceryfinder.common.bases;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;


/**
 * Abstract class to handle mapping between entities and DTOs using ModelMapper.
 *
 * @param <T> The type of the entity.
 * @param <ID> The type of the entity's ID.
 * @param <D> The type of the DTO.
 */
public abstract class AbstractModelMapper<T,ID, D> {

    protected final ModelMapper modelMapper;
    private final Class <T> entityClass;
    private final Class<D> dtoClass;

    /**
     * Constructor to initialize the mapper with required classes.
     *
     * @param modelMapper the ModelMapper instance.
     * @param entityClass the class of the entity.
     * @param dtoClass the class of the DTO.
     */
    public AbstractModelMapper(ModelMapper modelMapper, Class<T> entityClass, Class<D> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    /**
     * Maps an entity to a DTO.
     * @param entity the entity to be mapped.
     * @return the mapped DTO.
     */
    public D toDto(T entity) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Maps a DTO to an entity.
     * @param dto the DTO to be mapped.
     * @return the mapped entity.
     */
    public T toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    /**
     * Sets the ID field of the target entity using the given ID.
     * @param id the ID to be set.
     * @param entitySource the source entity (not used here but could be for context).
     * @param entityTarget the target entity where the ID will be set.
     */
    public void mapId(ID id, T entitySource, T entityTarget) {
        try {
            // Check if the field exists in the class hierarchy
            Field idField = findFieldInHierarchy(entitySource.getClass(), "id");
            if (idField == null) {
                throw new RuntimeException("Field 'id' not found in class hierarchy.");
            }
            idField.setAccessible(true);
            idField.set(entityTarget, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error setting ID field", e);
        }
    }

    private Field findFieldInHierarchy(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}