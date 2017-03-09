package tj.david.springboot.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tj.david.springboot.common.mybatis.basemapper.BaseMapper;
import tj.david.springboot.entity.UserRole;

import java.util.List;

/**
 * Created by David on 2016/7/26.
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {


    @Results(id = "userRoleResult", value = {
            @Result(property = "roleCode", column = "role_code", id = true)
    })
    @Select("SELECT r.role_code FROM role r, user_role ur WHERE r.id = ur.role_id AND ur.user_id = #{id}")
    List<UserRole> selectByUserId(Integer id);
}
