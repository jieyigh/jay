package tj.david.springboot.common.pager.utils;

import tj.david.springboot.common.pager.bean.Pager;
import tj.david.springboot.entity.User;

import java.util.List;

/**
 * Created by David on 2016/7/25.
 */
public class AssembleGrid<T> {

    public Pager<T> assembleGridPager(Pager<T> pager, int total, List<T> dataList) {

        int pageSize = pager.getPageSize();
        int startRecord = pager.getStartRecord();
        int recordCount = total;
        int pageCount = pager.getPageCount();
        int nowPage = pager.getNowPage();

        pager.setRecordCount(recordCount);
        pageCount = recordCount / pageSize + (recordCount % pageSize > 0 ? 1 : 0);
        pager.setPageCount(pageCount);
        if(nowPage>pageCount){
            nowPage = pageCount;
            startRecord = pageSize * (nowPage - 1);
            pager.setNowPage(nowPage);
            pager.setStartRecord(startRecord);
        }

        pager.setExhibitDatas(dataList);
        pager.setIsSuccess(true);

        return pager;
    }

}
