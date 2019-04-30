package top.yzlin.homework.dao;

import top.yzlin.homework.database.annotation.QueryParam;
import top.yzlin.homework.database.annotation.Save;
import top.yzlin.homework.database.annotation.SaveOrUpdate;
import top.yzlin.homework.database.annotation.Select;
import top.yzlin.homework.entity.User;

public interface UserDAO {

    @Select("from User as l where l.userName =:userName and l.password=:password")
    User userLogin(@QueryParam("userName") String userName,
                   @QueryParam("password")String password);

    @Select("select count(*)>0 from User as l where l.userName = :userName")
    boolean isExistUser(@QueryParam("userName")String userName);

    @Save
    boolean registerUser(User user);

    @SaveOrUpdate
    void updatePassword(User user);

}
