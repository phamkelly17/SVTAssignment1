import spock.lang.Specification

class FloatingPointArithmeticTests extends Specification {

    class FloatingPointHelper {
        static boolean compareNaN(float value) {
            return Float.isNaN(value);
        }
    }

    class FloatingPointOperations {
        float add(float a, float b) { return a + b; }
        float subtract(float a, float b) { return a - b; }
        float multiply(float a, float b) { return a * b; }
        float divide(float a, float b) { return a / b; }
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

    def "Multiplication: Float.MAX_VALUE * 2 should return positive infinity"() {
        expect:
        floatingPointOperations.multiply(Float.MAX_VALUE, 2) == Float.POSITIVE_INFINITY
    }

    def "Division: 1.0 / Float.MIN_VALUE should return positive infinity"() {
        expect:
        floatingPointOperations.divide(1.0, Float.MIN_VALUE) == Float.POSITIVE_INFINITY
    }

    def "Division: Float.MIN_VALUE / 2 should return 0.0 (subnormal behavior)"() {
        expect:
        floatingPointOperations.divide(Float.MIN_VALUE, 2) == 0.0
    }

    def "Division: 0.0 / 0.0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(0.0, 0.0))
    }

    def "NaN behavior: NaN + 1.0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.add(Float.NaN, 1.0))
    }

    def "NaN behavior: NaN * 0.0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.multiply(Float.NaN, 0.0))
    }

    def "Infinity behavior: Positive INF - Positive INF should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.subtract(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY))
    }

    def "Infinity behavior: Negative INF + Positive INF should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.add(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY))
    }

    def "0 / 0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(0.0, 0.0))
    }

    def "INF / INF should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY))
    }          //SHOULD IT RETURN NaN?????

    def "INF / +0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(Float.POSITIVE_INFINITY, +0.0) == Float.POSITIVE_INFINITY
    }

    def "INF / -0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(Float.POSITIVE_INFINITY, -0.0) == Float.NEGATIVE_INFINITY
    }

    def "0 / INF should return 0"() {
        expect:
        floatingPointOperations.divide(0.0, Float.POSITIVE_INFINITY) == 0.0
    }

    def "1 / +0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(1.0, +0.0) == Float.POSITIVE_INFINITY
    }

    def "1 / -0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(1.0, -0.0) == Float.NEGATIVE_INFINITY
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
        Float.NaN == Float.NaN == false
    }

    def "(MAX - 1) * 2 should return Positive Infinity"() {
        expect:
        floatingPointOperations.multiply((float) (Float.MAX_VALUE - 2f), 2f) == Float.POSITIVE_INFINITY
    }

    def "(MAX - 1) / MAX should return 1"() {
        expect:
        floatingPointOperations.divide((float) (Float.MAX_VALUE - 1f), Float.MAX_VALUE) == 1f
    }

    def "MAX / (MAX - 1) should return 1"() {
        expect:
        floatingPointOperations.divide(Float.MAX_VALUE, (float) (Float.MAX_VALUE - 1f)) == 1f
    }

    def "1 / MIN should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(1.0, Float.MIN_VALUE) == Float.POSITIVE_INFINITY
    }

    def "MAX / +0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(Float.MAX_VALUE, +0.0) == Float.POSITIVE_INFINITY
    }

    def "MAX / -0 should return Negative Infinity"() {
        expect:
        floatingPointOperations.divide(Float.MAX_VALUE, -0.0) == Float.NEGATIVE_INFINITY
    }

    def "MIN / 0 should return Positive Infinity"() {
        expect:
        floatingPointOperations.divide(Float.MIN_VALUE, 0.0) == Float.POSITIVE_INFINITY
    }

    def "1 + MIN - 1 should be approximately 0"() {
        expect:
        Math.abs(floatingPointOperations.add(Float.MIN_VALUE, -1) + 1) < 1e-15
    }

    def "MAX + 1 - MAX should return 0f"() {
        expect:
        floatingPointOperations.add(Float.MAX_VALUE, 1f) - Float.MAX_VALUE == 0f
    }

    def "NaN / NaN should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(Float.NaN, Float.NaN))
    }

    def "NaN / 0 should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.divide(Float.NaN, 0.0))
    }

    def "NaN + Value should return NaN"() {
        expect:
        FloatingPointHelper.compareNaN(floatingPointOperations.add(Float.NaN, 100.0))
    }
}
