import spock.lang.Specification

class FloatingPointArithmeticTests extends Specification {

    class FloatingPointHelper {
        static boolean compareNaN(double value) {
            return Double.isNaN(value);
        }
    }

    class FloatingPointOperations {
        double add(double a, double b) { return a + b; }
        double subtract(double a, double b) { return a - b; }
        double multiply(double a, double b) { return a * b; }
        double divide(double a, double b) { return a / b; }
    }

    def floatingPointOperations = new FloatingPointOperations()

    def "Addition: 0.1 + 0.2 should not strictly equal 0.3 due to precision errors"() {
        expect:
        floatingPointOperations.add(0.1, 0.2) != 0.3
    }

    def "Addition: (0.1 + 0.2) - 0.3 should be approximately 0.0"() {
        expect:
        Math.abs(floatingPointOperations.add(0.1, 0.2) - 0.3) < 1e-15
    }

    def "Subtraction: 1e16 + 1 - 1e16 should return 0.0 due to loss of precision"() {
        expect:
        floatingPointOperations.subtract(floatingPointOperations.add(1e16, 1), 1e16) == 0.0
    }

    def "Multiplication: Double.MAX_VALUE * 2 should return positive infinity"() {
        expect:
        floatingPointOperations.multiply(Double.MAX_VALUE, 2) == Double.POSITIVE_INFINITY
    }

    def "Division: 1.0 / Double.MIN_VALUE should return positive infinity"() {
        expect:
        floatingPointOperations.divide(1.0, Double.MIN_VALUE) == Double.POSITIVE_INFINITY
    }

    def "Division: Double.MIN_VALUE / 2 should return 0.0 (subnormal behavior)"() {
        expect:
        floatingPointOperations.divide(Double.MIN_VALUE, 2) == 0.0
    }

    def "Division: 0.0 / 0.0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(0.0, 0.0))
    }

    def "NaN behavior: NaN + 1.0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.add(Double.NaN, 1.0))
    }

    def "NaN behavior: NaN * 0.0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.multiply(Double.NaN, 0.0))
    }

    def "Infinity behavior: Positive INF - Positive INF should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.subtract(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
    }

    def "Infinity behavior: Negative INF + Positive INF should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.add(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
    }

    def "0 / 0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(0.0, 0.0))
    }

    def "INF / INF should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
    }          //SHOULD IT RETURN NaN?????

    def "INF / +0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(Double.POSITIVE_INFINITY, +0.0) == Double.POSITIVE_INFINITY
    }

    def "INF / -0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(Double.POSITIVE_INFINITY, -0.0) == Double.NEGATIVE_INFINITY
    }

    def "0 / INF should return 0"() {
        expect:
        floatingPointOperations.divide(0.0, Double.POSITIVE_INFINITY) == 0.0
    }

    def "1 / +0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(1.0, +0.0) == Double.POSITIVE_INFINITY
    }

    def "1 / -0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(1.0, -0.0) == Double.NEGATIVE_INFINITY
    }

    def "0+ == 0-"() {
        expect:
        (0.0 + 0.0) == (0.0 - 0.0)
    }

    def "0+ > 0-"() {
        expect:
        (0.0 + 0.0) > (0.0 - 0.0) == false
    }

    def "NaN == NaN should return false"() {
        expect:
        Double.NaN == Double.NaN == false
    }

    def "(MAX - 1) * 2 should return Positive Infinity"() {
        expect:
        floatingPointOperations.multiply(Double.MAX_VALUE - 1, 2) == Double.POSITIVE_INFINITY
    }

    def "(MAX - 1) / MAX should return 1"() {
        expect:
        floatingPointOperations.divide(Double.MAX_VALUE - 1, Double.MAX_VALUE) == 1.0
    }

    def "MAX / (MAX - 1) should return Infinity"() {
        expect:
        floatingPointOperations.divide(Double.MAX_VALUE, Double.MAX_VALUE - 1) == Double.POSITIVE_INFINITY
    }

    def "1 / MIN should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(1.0, Double.MIN_VALUE) == Double.POSITIVE_INFINITY
    }

    def "MAX / +0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(Double.MAX_VALUE, +0.0) == Double.POSITIVE_INFINITY
    }

    def "MAX / -0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(Double.MAX_VALUE, -0.0) == Double.NEGATIVE_INFINITY
    }

    def "MIN / 0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(Double.MIN_VALUE, 0.0) == Double.NEGATIVE_INFINITY
    }

    def "1 + MIN - 1 should be approximately 0"() {
        expect:
        Math.abs(floatingPointOperations.add(Double.MIN_VALUE, -1) + 1) < 1e-15
    }

    def "MAX + 1 - MAX should return 1 due to precision"() {
        expect:
        floatingPointOperations.add(Double.MAX_VALUE, 1) - Double.MAX_VALUE == 1.0
    }

    def "NaN / NaN should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(Double.NaN, Double.NaN))
    }

    def "NaN / 0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(Double.NaN, 0.0))
    }

    def "NaN + Value should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.add(Double.NaN, 100.0))
    }
}
