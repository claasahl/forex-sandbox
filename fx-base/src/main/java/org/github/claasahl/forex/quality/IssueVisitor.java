package org.github.claasahl.forex.quality;

public interface IssueVisitor {
	void visitCandleDurationIssue(CandleDurationIssue issue);

	void visitCandleSymbolIssue(CandleSymbolIssue issue);

	void visitCandleStagnatingDateTimeIssue(CandleStagnatingDateTimeIssue issue);

	void visitCandleLiveDateTimeIssue(CandleLiveDateTimeIssue issue);

	void visitCandleDateTimeTooEarlyIssue(CandleDateTimeTooEarlyIssue issue);

	void visitCandleDateTimeTooLateIssue(CandleDateTimeTooLateIssue issue);

	void visitCandleInconsistentHighPriceIssue(CandleInconsistentHighPriceIssue issue);
	
	void visitCandleInconsistentLowPriceIssue(CandleInconsistentLowPriceIssue issue);
	
	void visitRateSymbolIssue(RateSymbolIssue issue);

	void visitRateStagnatingDateTimeIssue(RateStagnatingDateTimeIssue issue);

	void visitRateLiveDateTimeIssue(RateLiveDateTimeIssue issue);

	void visitRateDateTimeTooEarlyIssue(RateDateTimeTooEarlyIssue issue);

	void visitRateDateTimeTooLateIssue(RateDateTimeTooLateIssue issue);
}
