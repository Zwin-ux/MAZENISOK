package dao;

import dto.UserDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDao implements BaseDao<UserDto> {
    private static UserDao instance;
    private final Map<String, UserDto> users = new HashMap<>();

    private UserDao() {}

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public void put(UserDto entity) {
        users.put(entity.getUniqueId(), entity);
    }

    @Override
    public Optional<UserDto> get(String uniqueId) {
        return Optional.ofNullable(users.get(uniqueId));
    }

    @Override
    public List<UserDto> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean exists(String uniqueId) {
        return users.containsKey(uniqueId);
    }

    @Override
    public void delete(String uniqueId) {
        users.remove(uniqueId);
    }

    @Override
    public void update(UserDto entity) {
        users.put(entity.getUniqueId(), entity);
    }

    public static void reset() {
        instance = new UserDao();
    }
}
