package tj.david.springboot.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tj.david.springboot.common.mybatis.basemapper.BaseMapper;
import tj.david.springboot.entity.Auth;

import java.util.List;

/**
 * Created by David on 2016/7/29.
 */
public interface AuthMapper extends BaseMapper<Auth> {


    @Results(id = "authResourceResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "authCode", column = "auth_code", id = true),
            @Result(property = "authName", column = "auth_name", id = true),
            @Result(property = "status", column = "status", id = true)
    })
    @Select("SELECT " +
            "a.* " +
            "FROM " +
            "auth a,role_auth ra,role r,user_role ur,`user` u " +
            "WHERE ra.auth_id = a.id AND r.id = ra.role_id AND ur.role_id = r.id AND u.id = ur.user_id AND u.id = #{userid}")
    List<Auth> getByUserid(int userid);


}
