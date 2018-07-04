package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(repeatTimeInterval: RepeatTimeInterval): MyDate {
    return addTimeIntervals(repeatTimeInterval.timeInterval, repeatTimeInterval.times)
}

operator fun TimeInterval.times(times: Int): RepeatTimeInterval {
    return RepeatTimeInterval(this, times)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatTimeInterval(val timeInterval: TimeInterval, val times: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterator<MyDate> {
    var current = start

    override fun hasNext(): Boolean {
        return current <= this.endInclusive
    }

    override fun next(): MyDate {
        val nextDay = current
        current = current.nextDay()
        return nextDay
    }

    operator fun contains(d: MyDate) = (d >= start && d <= endInclusive)
}
