package tj.david.springboot.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tj.david.springboot.common.pager.bean.Pager;
import tj.david.springboot.common.pager.utils.AssembleGrid;
import tj.david.springboot.entity.User;
import tj.david.springboot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 2016/7/23.
 */
@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "success";
    }

    @RequestMapping("/403")
    void error403() {}

    @RequestMapping("/index")
    void index() {}

    @RequestMapping("/home")
    void home() {}

    @RequestMapping(value="/login", method = RequestMethod.GET)
    void loginGet() {}

    @RequestMapping("/form-validator")
    void formValidator() {}

    @RequestMapping("/datagrid/test-datagrid")
    void testDatagrid() {}

    @RequestMapping("/datagrid/get-datagrid-data")
    @ResponseBody
    Pager getDatagridData(String gridPager) {

        Pager<User> pager = null;
        try {
            pager = JSON.parseObject(gridPager, Pager.class);
            AssembleGrid<User> assembleGrid = new AssembleGrid<User>();
            int recordCount = userService.getAll().size();
            Map<String, Object> fastQueryMap = pager.getFastQueryParameters();
            List<User> dataList = userService.findByPage(pager.getPageSize(), pager.getNowPage(), fastQueryMap);
            pager = assembleGrid.assembleGridPager(pager, recordCount, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pager;
    }

    @RequestMapping("/sessionTest")
    void sessionTest() {}

    @RequestMapping("/setSession")
    @ResponseBody
    public String setSession(HttpServletRequest request) {

        //测试一、向session中放入实现序列化的实体类（我这儿是TestEntity）
        User user = new User();
        user.setUsername("david_zh");
        user.setPassword("123456");
        request.getSession().setAttribute("user", user);

        //测试二、向session中放入String字符串
        request.getSession().setAttribute("testString", "i am a string value!");

        return "success";
    }

    @RequestMapping("/testMybatis")
    @ResponseBody
    public List<User> testMybatis() {
        return userService.getAll();
    }

}
