package dao;

import dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO fill this out
public class UserDao implements BaseDao<UserDto> {

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    // TODO fill this out
    @Override
    public void put(UserDto messageDto) {
    }

    // TODO fill this out
    @Override
    public UserDto get(String uniqueId) {
        return null;
    }

    // TODO fill this out
    @Override
    public List<UserDto> getAll() {
        return null;
    }

    // only for testing, do not call this method
    public static void reset() {
        instance = new UserDao();
    }
}
