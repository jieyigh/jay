package tj.david.springboot.mapper;

import org.apache.ibatis.annotations.*;
import tj.david.springboot.common.mybatis.basemapper.BaseMapper;
import tj.david.springboot.entity.User;

import java.util.List;

/**
 * Created by David on 2016/7/16.
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 批量插入
     *
     * @param cities
     * @return
     */
//    @Insert("<script>" +
//            "insert into city (id, name, state) values " +
//            "<foreach collection=\"list\" item=\"city\" separator=\",\" >" +
//            "(#{city.id}, #{city.cityName}, #{city.cityState})" +
//            "</foreach>" +
//            "</script>")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    int insertCities(List<User> cities);

    /**
     * 根据主键查询一个
     *
     * @param id
     * @return
     */
    @Results(id = "userResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username", id = true),
            @Result(property = "password", column = "password", id = true),
            @Result(property = "sex", column = "sex", id = true),
            @Result(property = "brithday", column = "brithday", id = true)
    })
    @Select("select id, username, password, sex, brithday from user where id = #{id}")
    User selectByUserId(Integer id);

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    /**
     * 查询全部，引用上面的Results
     *
     * @return
     */
    @ResultMap("userResult")
    @Select("select id, username, password, sex, brithday from user")
    List<User> selectAll();

}
