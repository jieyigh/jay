package tj.david.springboot.common.mybatis.basemapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.select.SelectCountMapper;

/**
 * Created by David on 2016/7/16.
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
