package tj.david.springboot.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tj.david.springboot.common.mybatis.basemapper.BaseMapper;
import tj.david.springboot.entity.Role;

import java.util.List;

/**
 * Created by David on 2016/7/26.
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Results(id = "roleCodeResult", value = {
            @Result(property = "roleCode", column = "role_code", id = true)
    })
    @Select("SELECT role.role_code " +
            "FROM role, auth, resources, role_auth, auth_resource, user, user_role " +
            "WHERE " +
            "role.id = role_auth.role_id " +
            "AND role_auth.auth_id = auth.id " +
            "AND auth.id = auth_resource.auth_id " +
            "AND auth_resource.resource_id = resources.id " +
            "AND user.id = user_role.user_id " +
            "AND user_role.id = role.id " +
            "AND resources.url = #{url} ")
    List<Role> selectByUserResourceUrl(String url);


    @Results(id = "roleCodeAndUrlResult", value = {
            @Result(property = "roleCode", column = "role_code", id = true),
            @Result(property = "url", column = "url", id = true)
    })
    @Select("SELECT " +
            "res.url, " +
            "r.role_code " +
            "FROM " +
            "resources res " +
            "LEFT JOIN auth_resource ar ON ar.resource_id = res.id " +
            "LEFT JOIN auth a ON a.id = ar.auth_id " +
            "LEFT JOIN role_auth ra ON ra.auth_id = a.id " +
            "LEFT JOIN role r ON r.id = ra.role_id")
    List<Role> selectAllRoleAndResourceUrl();


}
