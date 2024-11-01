package com.nmt.groceryfinder.common.bases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IBaseService<ID, D> {
    Iterable<D> getAll();
    Optional<D> getOneById(ID id);
    D createOne(D data);
    D updateOneById(ID id, D data);
    void deleteOneById(ID id);
    Page<D> getAllPaginated(Pageable pageable);
}