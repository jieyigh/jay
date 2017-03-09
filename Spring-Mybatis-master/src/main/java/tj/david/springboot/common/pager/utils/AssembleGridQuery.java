package tj.david.springboot.common.pager.utils;

import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * 大连首闻DataGrid专用查询条件封装类
 * Created by David on 2016/7/25.
 */
public class AssembleGridQuery {


    /**
     * 大连首闻DataGrid专用查询条件
     * @param criteria
     * @param queryMap
     * @return
     */
    public static Example.Criteria assembleGridQuery(Example.Criteria criteria, Map<String, Object> queryMap) {

        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!StringUtils.isEmpty(value)) {
                String[] arr = key.split("_");
                if (key.startsWith("eq_")) {
                    criteria.andEqualTo(arr[1], value);
                }
                if (key.startsWith("ne_")) {
                    criteria.andNotEqualTo(arr[1], value);
                }
                if (key.startsWith("lk_")) {
                    criteria.andLike(arr[1], "%" + value + "%");
                }
//				right like
                if(key.startsWith("rl_")){
                    criteria.andLike(arr[1], "%" + value);
                }
//				left like
                if(key.startsWith("ll_")){
                    criteria.andLike(arr[1], value + "%" );
                }
//				is null
                if(key.startsWith("in_")){
                    criteria.andIsNull(arr[1]);
                }
//				is not null
                if(key.startsWith("inn_")){
                    criteria.andIsNull(arr[1]);
                }
//				great then 大于
                if(key.startsWith("gt_")){
                    criteria.andGreaterThan(arr[1], value);
                }
//				great then and equal 大于等于
                if(key.startsWith("ge_")){
                    criteria.andGreaterThanOrEqualTo(arr[1], value);
                }
//				less then
                if(key.startsWith("lt_")){
                    criteria.andLessThan(arr[1], value);
                }
//				less then and equal
                if(key.startsWith("le_")){
                    criteria.andLessThanOrEqualTo(arr[1], value);
                }
            }
        }
        return criteria;
    }

}
