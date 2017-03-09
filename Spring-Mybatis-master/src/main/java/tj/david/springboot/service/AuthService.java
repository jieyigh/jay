package tj.david.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.david.springboot.entity.Auth;
import tj.david.springboot.mapper.AuthMapper;

import java.util.List;

/**
 * Created by David on 2016/7/29.
 */
@Service
public class AuthService {

    @Autowired
    AuthMapper authMapper;

    public List<Auth> getAuthsByUserid(int userid) {
        return authMapper.getByUserid(userid);
    }

}
