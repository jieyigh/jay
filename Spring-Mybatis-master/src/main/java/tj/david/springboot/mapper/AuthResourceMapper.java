package tj.david.springboot.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tj.david.springboot.common.mybatis.basemapper.BaseMapper;
import tj.david.springboot.entity.AuthResource;

import java.util.List;

/**
 * Created by David on 2016/7/29.
 */
public interface AuthResourceMapper extends BaseMapper<AuthResource> {

    @Results(id = "authResourceResult", value = {
            @Result(property = "url", column = "url", id = true),
            @Result(property = "authCode", column = "auth_code", id = true)
    })
    @Select("SELECT " +
            "r.url, " +
            "a.auth_code " +
            "FROM " +
            "auth_resource ar " +
            "LEFT JOIN auth a ON a.id = ar.auth_id " +
            "LEFT JOIN resources r ON r.id = ar.resource_id")
    List<AuthResource> getURLResourceMapping();

    @ResultMap("authResourceResult")
    @Select("SELECT " +
            "r.url, " +
            "a.auth_code " +
            "FROM " +
            "auth_resource ar " +
            "LEFT JOIN auth a ON a.id = ar.auth_id " +
            "LEFT JOIN resources r ON r.id = ar.resource_id " +
            "WHERE r.url = #{url}")
    List<AuthResource> getURLResourceMappingByUrl(String url);

}
