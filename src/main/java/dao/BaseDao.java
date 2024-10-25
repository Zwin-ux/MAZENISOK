package dao;

import dto.BaseDto;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends BaseDto> {

    void put(T entity);

    Optional<T> get(String uniqueId);

    List<T> getAll();

    // Additional useful methods
    boolean exists(String uniqueId);

    void delete(String uniqueId);

    void update(T entity);
}
