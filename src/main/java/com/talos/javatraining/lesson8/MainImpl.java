package com.talos.javatraining.lesson8;



import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;


public class MainImpl implements Main
{

	@Override
	public Instant getInstant(String dateTime)
	{
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		ZonedDateTime newDateTime = ZonedDateTime.of(localDateTime.plusSeconds(1).minusMinutes(10),ZoneId.of("GMT-5"));
		Instant moment = newDateTime.toInstant();
		return moment;
	}

	@Override
	public Duration getDuration(Instant a, Instant b)
	{
		Duration duration = Duration.between(a,b);
		Duration newDuration = duration.plusDays(1).minusHours(4);
		return newDuration;
	}

	@Override
	public String getHumanReadableDate(LocalDateTime localDateTime)
	{
		LocalDateTime newDateTime = localDateTime.plusHours(3).withMonth(7);
		int year = newDateTime.getYear();
		if(year%2 == 0)
			year++;
		LocalDateTime result = newDateTime.withYear(year);
		return result.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Override
	public LocalDateTime getLocalDateTime(String dateTime)
	{
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("ssmmHHddMMyyyy"));
		LocalDateTime newLocalDateTime;
		if(localDateTime.getMonth().getValue()%2 != 0)
			newLocalDateTime = localDateTime.plusMonths(1);
		else
			newLocalDateTime = localDateTime;

		LocalDateTime addedSeconds;
		int seconds = localDateTime.getSecond() * 2;
		if (seconds >= 60)
		{
			seconds -= 60;
			addedSeconds = newLocalDateTime.plusMinutes(1).withSecond(seconds);
		}
		else
			addedSeconds = newLocalDateTime.withSecond(seconds);
		return addedSeconds;
	}

	@Override
	public Period calculateNewPeriod(Period period)
	{
		Period result = period.plusMonths(5).plusDays(6).minusDays(14);
		return result;
	}

	@Override
	public LocalDate toLocalDate(Year year, MonthDay monthDay)
	{
		LocalDate localDate = monthDay.atYear(year.getValue()).plusYears(3);
		LocalDate result;
		int day = localDate.getDayOfMonth();
		if(day < 5)
			result = localDate.withDayOfMonth(1);
		else
		{
			while(day%5 != 0)
				day--;
			result = localDate.withDayOfMonth(day);
		}
		return result;
	}

	@Override
	public LocalDateTime toLocalDateTime(YearMonth yearMonth, int dayOfMonth, LocalTime time)
	{
		LocalDate localDate = yearMonth.atDay(dayOfMonth);
		LocalDateTime localDateTime = LocalDateTime.of(localDate,time).withSecond(0).minusMinutes(37).plusDays(3);
		return localDateTime;
	}

	@Override
	public TemporalAdjuster createTemporalAdjusterNextMonday()
	{
		TemporalAdjuster adjuster = x -> {
			LocalDate result = (LocalDate) x;
			do{
				result = result.plusDays(1);
			} while(!result.getDayOfWeek().equals(DayOfWeek.MONDAY));
			return result;
		};
		return adjuster;
	}

	@Override
	public TemporalAdjuster createTemporalAdjusterNextFebruaryFirst()
	{
		TemporalAdjuster adjuster = x -> {
			LocalDate result = (LocalDate) x;
			do{
				result = result.plusMonths(1);
			} while(!result.getMonth().equals(Month.FEBRUARY));
			result = result.withDayOfMonth(1);
			return result;
		};
		return adjuster;
	}

	@Override
	public String adjustDateTime(String localDateTime, TemporalAdjuster adjuster)
	{
		LocalDateTime date = LocalDateTime.parse(localDateTime,DateTimeFormatter.ISO_LOCAL_DATE_TIME).with(adjuster);
		String result = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return result;
	}

	@Override
	public String processZonedDateTime(String zonedDateTime)
	{
		ZonedDateTime dateTime = ZonedDateTime.parse(zonedDateTime,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		//LocalDateTime localDate = dateTime.toLocalDateTime().plusHours(1);
		ZonedDateTime localDate = dateTime.withZoneSameInstant(ZoneId.of("UTC")).plusHours(1);
		int mins = localDate.getMinute();
		if(mins < 15)
			mins = 0;
		else
			while(mins%15 != 0)
				mins--;

		localDate = localDate.withMinute(mins);

		String result = localDate.format(DateTimeFormatter.RFC_1123_DATE_TIME);
		return result;
	}
}
