package tj.david.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tj.david.springboot.entity.AuthResource;
import tj.david.springboot.mapper.AuthResourceMapper;

import java.util.List;

/**
 * Created by David on 2016/7/29.
 */
@Service
public class AuthResourceService {

    @Autowired
    AuthResourceMapper authResourceMapper;

    public List<AuthResource> getURLResourceMapping() {
        return authResourceMapper.getURLResourceMapping();
    }

    public List<AuthResource> getURLResourceMappingByUrl(String url) {
        return authResourceMapper.getURLResourceMappingByUrl(url);
    }

}
