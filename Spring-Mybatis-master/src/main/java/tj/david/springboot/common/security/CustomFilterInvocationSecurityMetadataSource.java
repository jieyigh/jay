package tj.david.springboot.common.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import tj.david.springboot.entity.Role;
import tj.david.springboot.service.RoleService;

import java.util.*;

/**
 * @author chenzhenjia
 * @since 16/6/17
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    private boolean expire = false;  //当数据库权限发生变化的时候需要刷新内存中的权限

    private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();

    private Map<String, Collection<ConfigAttribute>> requestMap;

    private AntPathMatcher urlMatcher = new AntPathMatcher();

    @Autowired
    private RoleService roleService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        //刷新资源
        if(expire){
            this.requestMap.clear();
            expire = false;
        }
        //若map为空，则重新加载
        if(this.requestMap==null || this.requestMap.isEmpty()){
            this.requestMap = bindRequestMap();
        }

        String URL = ((FilterInvocation) o).getRequestUrl();
        if (URL.contains("&")) {
            URL = URL.substring(0, URL.indexOf("&"));
        }
        Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (urlMatcher.match(URL, entry.getKey())) {
                attrs = entry.getValue();
                break;
            }
        }
        return attrs;
    }

    /**
     * 获取所有权限的集合
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> set = new HashSet<>();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            set.addAll(entry.getValue());
        }
        return set;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.requestMap = this.bindRequestMap();
    }

    /**
     *
     * @return
     */
    private Map<String, Collection<ConfigAttribute>> bindRequestMap() {
        Map<String, Collection<ConfigAttribute>> map = new LinkedHashMap<>();

        Map<String, String> sourceMap = this.loadResouece();

        for (Map.Entry<String, String> entry : sourceMap.entrySet()) {
            String key = entry.getKey();
            Collection<ConfigAttribute> attr = new ArrayList<>();
            attr = SecurityConfig.createListFromCommaDelimitedString(entry.getValue());
            map.put(key, attr);
        }

        return map;
    }

    /**
     * 从数据库中加载权限和资源的对应列表
     *
     * @return
     */
    private Map<String, String> loadResouece() {
        Map<String, String> map = new LinkedHashMap<>();
        List<Role> list = roleService.selectAllRoleAndResourceUrl();
        if (list != null && list.size() > 0) {
            for (Role role : list) {
                String path = role.getUrl();
                String code = role.getRoleCode();
                if (map.containsKey(path)) {
                    String existCode = map.get(path);
                    map.put(path, existCode + "," + code);
                } else {
                    map.put(path, code);
                }
            }
        }
        return map;
    }

    /**
     * 设置是否重新获取数据
     */
    public void expireNow(){
        this.expire = true;
    }
}
