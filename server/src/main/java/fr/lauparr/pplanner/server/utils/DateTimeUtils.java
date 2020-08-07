package fr.lauparr.pplanner.server.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public abstract class DateTimeUtils {

	public static final String PATTERN_DATE_TIME = "dd/MM/yyyy HH:mm";
	public static final String PATTERN_DATE = "dd/MM/yyyy";

	private DateTimeUtils() {}

	public static LocalDate convertToLocalDate(String date) {
		return convertToLocalDate(date, PATTERN_DATE);
	}

	public static LocalDate convertToLocalDate(String date, String pattern) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		try {
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static LocalDateTime convertToLocalDateTime(String date) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		boolean simpleDate = date.matches("\\d{2}/\\d{2}/\\d{4}");
		if (simpleDate) {
			date = date.concat(" 00:00:00");
		}
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
	}

	public static LocalDate convertToLocalDate(Date date) {
		LocalDateTime dateTime = convertToLocalDateTime(date);
		if (dateTime == null) {
			return null;
		}
		return dateTime.toLocalDate();
	}

	public static LocalDateTime convertToLocalDateTime(Date date) {
		if (date != null) {
			Instant instant = Instant.ofEpochMilli(date.getTime());
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		}
		return null;
	}

	public static Date convertLocalDateToDate(LocalDate date) {
		return convertLocalDateTimeToDate(date.atTime(0, 0, 0));
	}

	public static Date convertLocalDateTimeToDate(LocalDateTime date) {
		if (date != null) {
			return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}

	public static String formatDate(Temporal temporal) {
		if (temporal != null) {
			if (temporal instanceof LocalDate) {
				return ((LocalDate) temporal).format(DateTimeFormatter.ofPattern(PATTERN_DATE));
			}
			if (temporal instanceof LocalDateTime) {
				return ((LocalDateTime) temporal).format(DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
			}
		}
		return null;
	}

	public static String formattedLocalDateTimeDuration(LocalDateTime time) {
		return formatLocalDateTimeDuration(time);
	}

	private static String formatLocalDateTimeDuration(LocalDateTime time) {
		if (time != null) {
			long heures = ChronoUnit.HOURS.between(LocalDateTime.now(), time);
			long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), time) % 60;
			long secondes = ChronoUnit.SECONDS.between(LocalDateTime.now(), time) % 60;

			return String.format("%s %s %s %s %s %s", heures, getPlurialString(heures, " heure"), minutes, getPlurialString(minutes, " minute"), secondes, getPlurialString(secondes, " seconde"));
		}
		return null;
	}

	public static String formattedEllapsedTimeDuration(long time) {
		Duration duration = Duration.ofMillis(time);
		return formatEllapsedTimeDuration(duration);
	}

	public static int getWeekNumber(Temporal date) {
		TemporalField field = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
		return date.get(field);
	}

	public static String getDayOfWeekLiteral(Temporal date, boolean abrege) {
		if (abrege) {
			return DateTimeFormatter.ofPattern("EEE").format(date);
		}
		return DateTimeFormatter.ofPattern("EEEE").format(date);
	}

	private static String formatEllapsedTimeDuration(Duration duration) {
		if (duration != null) {
			long heures = duration.toHours();
			long minutes = duration.toMinutes();
			long secondes = duration.getSeconds() % 60;

			String formattedHeures = heures > 0 ? heures + getPlurialString(heures, " heure") : "";
			String formattedMinutes = minutes > 0 ? minutes + getPlurialString(minutes, " minute") : "";
			String formattedSecondes = secondes > 0 || heures <= 0 ? secondes + getPlurialString(secondes, " seconde") : "";

			return String.format("%s %s %s", formattedHeures, formattedMinutes, formattedSecondes);
		}
		return null;
	}

	private static String getPlurialString(long number, String word) {
		return number > 1 ? word + "s" : word;
	}

}
