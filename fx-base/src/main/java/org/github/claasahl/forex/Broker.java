package org.github.claasahl.forex;

import java.time.*;
import io.reactivex.*;

public interface Broker {
	/**
	 * Returns a <i>hot</i> {@link Observable} that emits (closed) candles with the
	 * specified symbol and duration. Upon subscription, the {@link Observable}
	 * emits <i>live</i> candles that close after the subscription. Candles are
	 * emitted just after they closed (i.e. after their duration elapsed). An empty
	 * {@link Observable} may also be returned if this function is not supported.
	 * <p>
	 * If you intend to be working on <i>live</i> (i.e. as in <i>live television
	 * transmission</i>) data, use this function. If you intent to be working on
	 * <i>historical</i> (i.e. as in <i>recorded television transmission</i>) data,
	 * use {@link #candles(String, Duration, OffsetDateTime, OffsetDateTime)}.
	 * <dl>
	 * <dt><b>Subscription:</b></dt>
	 * <dd>???</dd>
	 * <dt><b>Emission:</b></dt>
	 * <dd>Observable emits <i>live</i> (i.e. it is <i>hot</i>) candles that close
	 * after the subscription. Candles are emitted just after they closed (i.e.
	 * after their duration elapsed).</dd>
	 * <dt><b>Completion:</b></dt>
	 * <dd>Observable does not complete.</dd>
	 * <dt><b>Termination (error):</b></dt>
	 * <dd>Observable experienced an error condition. Such conditions might be, but
	 * are not limited to: invalid symbol (e.g. too long or short) or invalid
	 * duration (e.g. negative duration).</dd>
	 * <dt><b>Scheduler:</b></dt>
	 * <dd>Observable does not operate by default on a particular
	 * {@link Scheduler}.</dd>
	 * </dl>
	 * 
	 * @param symbol
	 *            symbol for which candles are emitted
	 * @param duration
	 *            duration of the emitted candles
	 * @return a <i>hot</i> {@link Observable} that emits (closed) candles with the
	 *         specified symbol and duration
	 */
	Observable<Candle> candles(String symbol, Duration duration);

	/**
	 * Returns a <i>cold</i> {@link Observable} that emits (closed) candles with the
	 * specified symbol and duration. Upon subscription, the {@link Observable}
	 * emits <i>historical</i> candles that have closed within the specified period
	 * of time. An empty {@link Observable} may also be returned if this function is
	 * not supported.
	 * <p>
	 * If you intend to be working on <i>live</i> (i.e. as in <i>live television
	 * transmission</i>) data, use {@link #candles(String, Duration)}. If you intent
	 * to be working on <i>historical</i> (i.e. as in <i>recorded television
	 * transmission</i>) data, use this function.
	 * <dl>
	 * <dt><b>Subscription:</b></dt>
	 * <dd>???</dd>
	 * <dt><b>Emission:</b></dt>
	 * <dd>Observable emits <i>historical</i> (i.e. it is <i>cold</i>) candles that
	 * have closed within the specified period of time.</dd>
	 * <dt><b>Completion:</b></dt>
	 * <dd>Observable completes after the last candle, that closed within the
	 * specified period of time, has been emitted.</dd>
	 * <dt><b>Termination (error):</b></dt>
	 * <dd>Observable experienced an error condition. Such conditions might be, but
	 * are not limited to: invalid symbol (e.g. too long or short), invalid duration
	 * (e.g. negative duration) or invalid time period (e.g. future date and
	 * time).</dd>
	 * <dt><b>Scheduler:</b></dt>
	 * <dd>Observable does not operate by default on a particular
	 * {@link Scheduler}.</dd>
	 * </dl>
	 * 
	 * @param symbol
	 *            symbol for which candles are emitted
	 * @param duration
	 *            duration of the emitted candles
	 * @param from
	 *            lower boundary of the time period (inclusive)
	 * @param to
	 *            upper boundary of the time period (exclusive)
	 * @return a <i>cold</i> {@link Observable} that emits (closed) candles with the
	 *         specified symbol and duration
	 */
	Observable<Candle> candles(String symbol, Duration duration, OffsetDateTime from, OffsetDateTime to);

	/**
	 * Returns a <i>hot</i> {@link Observable} that emits rates with the specified
	 * symbol. Upon subscription, the {@link Observable} emits <i>live</i> rates
	 * that are observed after the subscription. Rates are emitted just after they
	 * changed (i.e. whenever the bid or ask price changed). An empty
	 * {@link Observable} may also be returned if this function is not supported.
	 * <p>
	 * If you intend to be working on <i>live</i> (i.e. as in <i>live television
	 * transmission</i>) data, use this function. If you intent to be working on
	 * <i>historical</i> (i.e. as in <i>recorded television transmission</i>) data,
	 * use {@link #rates(String, OffsetDateTime, OffsetDateTime)}.
	 * <dl>
	 * <dt><b>Subscription:</b></dt>
	 * <dd>???</dd>
	 * <dt><b>Emission:</b></dt>
	 * <dd>Observable emits <i>live</i> (i.e. it is <i>hot</i>) rates that are
	 * observed after the subscription. Rates are emitted just after they changed
	 * (i.e. whenever the bid or ask price changed).</dd>
	 * <dt><b>Completion:</b></dt>
	 * <dd>Observable does not complete.</dd>
	 * <dt><b>Termination (error):</b></dt>
	 * <dd>Observable experienced an error condition. Such conditions might be, but
	 * are not limited to: invalid symbol (e.g. too long or short).</dd>
	 * <dt><b>Scheduler:</b></dt>
	 * <dd>Observable does not operate by default on a particular
	 * {@link Scheduler}.</dd>
	 * </dl>
	 * 
	 * @param symbol
	 *            symbol for which rates are emitted
	 * @return a <i>hot</i> {@link Observable} that emits rates with the specified
	 *         symbol
	 */
	Observable<Rate> rates(String symbol);

	/**
	 * Returns a <i>cold</i> {@link Observable} that emits rates with the specified
	 * symbol. Upon subscription, the {@link Observable} emits <i>historical</i>
	 * rates that have been observed within the specified period of time. An empty
	 * {@link Observable} may also be returned if this function is not supported.
	 * <p>
	 * If you intend to be working on <i>live</i> (i.e. as in <i>live television
	 * transmission</i>) data, use {@link #rates(String)}. If you intent to be
	 * working on <i>historical</i> (i.e. as in <i>recorded television
	 * transmission</i>) data, use this function.
	 * <dl>
	 * <dt><b>Subscription:</b></dt>
	 * <dd>???</dd>
	 * <dt><b>Emission:</b></dt>
	 * <dd>Observable emits <i>historical</i> (i.e. it is <i>cold</i>) rates that
	 * have been observed within the specified period of time.</dd>
	 * <dt><b>Completion:</b></dt>
	 * <dd>Observable completes after the last rate, that was observed within the
	 * specified period of time, has been emitted.</dd>
	 * <dt><b>Termination (error):</b></dt>
	 * <dd>Observable experienced an error condition. Such conditions might be, but
	 * are not limited to: invalid symbol (e.g. too long or short) or invalid time
	 * period (e.g. future date and time).</dd>
	 * <dt><b>Scheduler:</b></dt>
	 * <dd>Observable does not operate by default on a particular
	 * {@link Scheduler}.</dd>
	 * </dl>
	 * 
	 * @param symbol
	 *            symbol for which rates are emitted
	 * @param from
	 *            lower boundary of the time period (inclusive)
	 * @param to
	 *            upper boundary of the time period (exclusive)
	 * @return a <i>cold</i> {@link Observable} that emits rates with the specified
	 *         symbol
	 */
	Observable<Rate> rates(String symbol, OffsetDateTime from, OffsetDateTime to);

	// TODO create quality monitoring module that records live data and compares
	// into with historical data
}
