/*上午，开奖周期更改的期数，对应时间点为2点或10点*/
var SEPARATE_ISSUE_AM = 24;

/* 上午，开奖周期更改的时间 */
var SEPARATE_HOUR_AM = 10;

/*下午，开奖周期更改的期数，对应时间点为22点*/
var SEPARATE_ISSUE_PM = 96;

/*下午，开奖周期更改的时间*/
var SEPARATE_HOUR_PM = 22;

/* 十分钟周期*/
var PERIOD_TEN = 10;

/*五分钟周期*/
var PERIOD_FIVE = 5;

function getTime(databean) {
	var time = databean.date;
	var issue = databean.issue;
	var calendar = new Date();
	var year = time.substring(0,4);
	var month = time.substring(4,6);
	var date = time.substring(6,8);
	calendar.setFullYear(year, month, date);
	if (isTenPeriod(databean)) {
		var totalminute = SEPARATE_HOUR_AM * 60 + (issue - SEPARATE_ISSUE_AM) * PERIOD_TEN;
		calendar.setHours(totalminute/60, totalminute%60);
	}
	if (isFivePeriod(databean)) {
		var totalminute;
		if (issue <= SEPARATE_ISSUE_AM) {
			totalminute = issue * PERIOD_FIVE;
		} else {
			totalminute = SEPARATE_HOUR_PM * 60 + (issue - SEPARATE_ISSUE_PM) * PERIOD_FIVE;
		}
		calendar.setHours(totalminute/60, totalminute%60, 0);
	}
	return calendar;
}

function isTenPeriod(databean) {
	var issue = parseInt(databean.issue);
	if(issue > SEPARATE_ISSUE_AM && issue <= SEPARATE_ISSUE_PM) {
		return true;
	}
	return false;
}

function isFivePeriod(databean) {
	var issue = parseInt(databean.issue);
	if(issue <= SEPARATE_ISSUE_AM || issue > SEPARATE_ISSUE_PM) {
		return true;
	}
	return false;
}