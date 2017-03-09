package tj.david.springboot.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tj.david.springboot.common.pager.utils.AssembleGridQuery;
import tj.david.springboot.entity.User;
import tj.david.springboot.mapper.UserMapper;
import tj.david.springboot.mapper.UserRoleMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by David on 2016/7/24.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthService authService;

    public List<User> getAll() {
        return userMapper.selectAll();
    }

    public int countAll() {
        return userMapper.selectCount(new User());
    }

    public List<User> findByPage(int rows, int page, Map<String, Object> queryMap) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(null != queryMap){
        	AssembleGridQuery.assembleGridQuery(criteria, queryMap);
        }
        //分页查询
        PageHelper.startPage(page, rows);
        return userMapper.selectByExample(example);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        User user = userMapper.selectByUsername(username);
        Set<GrantedAuthority> authorities = new HashSet<>();

        userRoleService.getRoles(user.getId()).forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRoleCode())));
        System.out.println("UserService authorities = " + authorities);
        return new org.springframework.security.core.userdetails.User(
                username, user.getPassword(),
                true,//是否可用
                true,//是否过期
                true,//证书不过期为true
                true,//账户未锁定为true
                authorities);
    }

    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
