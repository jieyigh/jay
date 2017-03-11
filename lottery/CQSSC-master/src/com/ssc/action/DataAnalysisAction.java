package com.ssc.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.ssc.DataAnalyzerMgr;
import com.ssc.filter.Filter;
import com.ssc.filter.digit.DigitFilter;
import com.ssc.filter.digit.impl.HundredsFilter;
import com.ssc.filter.digit.impl.TenThousandsFilter;
import com.ssc.filter.digit.impl.TensFilter;
import com.ssc.filter.digit.impl.ThousandsFilter;
import com.ssc.filter.digit.impl.UnitsFilter;
import com.ssc.filter.impl.BigSmallFilter;
import com.ssc.filter.impl.SingleDoubleFilter;

public class DataAnalysisAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lower = req.getParameter("lower");
		String upper = req.getParameter("upper");
		String digit = req.getParameter("digit");
		String checktype = req.getParameter("check");
		if (isEmpty(digit) || isEmpty(checktype) || isEmpty(lower) || isEmpty(upper)) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return;
		}
		int lowerLimit = Integer.parseInt(lower);
		int upperLimit = Integer.parseInt(upper);
		
		Filter filter = null;
		if ("bigsmall".equals(checktype)) {
			DigitFilter digitfilter = initDigitFilter(digit);
			filter = new BigSmallFilter(lowerLimit, upperLimit, digitfilter);
		} else if ("singledouble".equals(checktype)) {
			DigitFilter digitfilter = initDigitFilter(digit);
			filter = new SingleDoubleFilter(lowerLimit, upperLimit, digitfilter);
		}
		DataAnalyzerMgr mgr = new DataAnalyzerMgr(filter);
		List datalist = null;
		try {
			datalist = mgr.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray dataarray = JSONArray.fromObject(datalist);
		req.setAttribute("dataarray", dataarray);
		req.setAttribute("size", dataarray.size());
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
	private DigitFilter initDigitFilter(String digit) {
		/*设置位过滤器*/
		if (DigitFilter.DIGIT_UNITS.equals(digit)) {
			return new UnitsFilter();
		}
		if (DigitFilter.DIGIT_TENS.equals(digit)) {
			return new TensFilter();
		}
		if (DigitFilter.DIGIT_HUNDREDS.equals(digit)) {
			return new HundredsFilter();
		}
		if (DigitFilter.DIGIT_THOUSANDS.equals(digit)) {
			return new ThousandsFilter();
		}
		if (DigitFilter.DIGIT_TENTHOUSANDS.equals(digit)) {
			return new TenThousandsFilter();
		}
		return null;
		
	}

	
	public boolean isEmpty(String value) {
		if (value == null || value.length() <= 0) {
			return true;
		}
		return false;
	}
}
