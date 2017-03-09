package tj.david.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.david.springboot.entity.UserRole;
import tj.david.springboot.mapper.UserRoleMapper;

import java.util.List;

/**
 * Created by David on 2016/7/26.
 */
@Service
public class UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    public List<UserRole> getRoles(int userid) {
        return userRoleMapper.selectByUserId(userid);
    }

    public boolean authorized(int id, String a, String b) {

        return true;
    }

}
