package tj.david.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.david.springboot.entity.Role;
import tj.david.springboot.mapper.RoleMapper;

import java.util.List;

/**
 * Created by David on 2016/7/26.
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> getRolesByUrlAndUsername(String url) {
        return roleMapper.selectByUserResourceUrl(url);
    }

    public List<Role> selectAllRoleAndResourceUrl() {
        return roleMapper.selectAllRoleAndResourceUrl();
    }

}
