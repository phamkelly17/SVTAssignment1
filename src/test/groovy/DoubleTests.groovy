import spock.lang.Specification

class DoubleTests extends Specification{

    class DoubleIEEE754Helper {
        static boolean compareNaN(double value) {
            return Double.isNaN(value)
        }
    }

    class DoubleOperations {
        double Div(double a = (double) 0.0, double b = (double) 0.0) {
            return a / b
        }
    }

    def doubleOperations = new DoubleOperations()

    def "0.0/0.0 should return NaN"() {
        def result = doubleOperations.Div((double) 0.0,(double) 0.0)
        expect:
        DoubleIEEE754Helper.compareNaN(result)
    }

    def "Positive INF / Positive INF should return NaN"() {
        def result = doubleOperations.Div(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY)
        expect:
        DoubleIEEE754Helper.compareNaN(result)
    }

    def "Positive INF / positive signed 0 should equal positive INF"() {
        def result = doubleOperations.Div(Double.POSITIVE_INFINITY, (double) +0.0)
        expect:
        result == Double.POSITIVE_INFINITY
    }

    def "Positive INF / negative signed 0 should equal negative INF"() {
        def result = doubleOperations.Div(Double.POSITIVE_INFINITY, (double) -0.0)
        expect:
        result == Double.NEGATIVE_INFINITY
    }

    def "0.0 / positive INF should equal 0.0"() {
        def result = doubleOperations.Div((double) 0.0, Double.POSITIVE_INFINITY)
        expect:
        result == (double) 0.0
    }

    def "1.0 / +0.0 should equal positive INF"() {
        def result = doubleOperations.Div((double) 1.0, (double) +0.0)
        expect:
        result == Double.POSITIVE_INFINITY
    }

    def "1.0 / -0.0 should equal negative INF"() {
        def result = doubleOperations.Div((double) 1.0, (double) -0.0)
        expect:
        result == Double.NEGATIVE_INFINITY
    }

    def "+0.0 == -0.0 should be true"() {
        def result = (double) +0.0 == (double) -0.0
        expect:
        result == true
    }

    def "+0.0 > -0.0 should be false"() {
        def result = (double) +0.0 > (double) -0.0
        expect:
        result == false
    }

    // Adding in Bottom tests here
    def "Double.NaN == Double.NaN should return false"() {
        def result = Double.NaN == Double.NaN
        expect:
        result == false
    }

    def "(Double.MAX_VALUE - 1) * 2.0 should return positive infinity"() {
        def result = (Double.MAX_VALUE - 1) * 2.0
        expect:
        result == Double.POSITIVE_INFINITY
    }

    def "(Double.MAX_VALUE - 1) / Double.MAX_VALUE should be exactly 1.0"() {
        def result = (Double.MAX_VALUE - 1) / Double.MAX_VALUE
        expect:
        result == 1.0
    }

    def "Double.MAX_VALUE / (Double.MAX_VALUE - 1) should be approximately 1.0"() {
        def result = Double.MAX_VALUE / (Double.MAX_VALUE - 1)
        expect:
        result == 1.0
    }

    def "1.0 / Double.MIN_VALUE should return positive infinity"() {
        def result = 1.0 / Double.MIN_VALUE
        expect:
        result == Double.POSITIVE_INFINITY
    }

    def "Double.MAX_VALUE / +0.0 should return positive infinity"() {
        def result = Double.MAX_VALUE / +0.0
        expect:
        result == Double.POSITIVE_INFINITY
    }

    def "Double.MAX_VALUE / -0.0 should return negative infinity"() {
        def result = Double.MAX_VALUE / -0.0
        expect:
        result == Double.NEGATIVE_INFINITY
    }

    def "Double.MIN_VALUE / 0.0 should return positive infinity"() {
        println(Double.MAX_VALUE)
        def result = Double.MIN_VALUE / 0.0
        expect:
        result == Double.POSITIVE_INFINITY
    }

    def "1.0 + Double.MIN_VALUE - 1.0 should return 0.0"() {
        def result = (1.0 + Double.MIN_VALUE) - 1.0
        expect:
        result == 0.0
    }

    //UNSURE ABOUT THIS TEST TO CHECK
    def "Double.MAX_VALUE + 1.0 - Double.MAX_VALUE should return 0.0"() {
        def result = (Double.MAX_VALUE + 1.0) - Double.MAX_VALUE
        expect:
        result == 0.0
    }

    def "Double.NaN / Double.NaN should return NaN"() {
        def result = Double.NaN / Double.NaN
        expect:
        DoubleIEEE754Helper.compareNaN(result)
    }

    def "Double.NaN / 0.0 should return NaN"() {
        def result = Double.NaN / 0.0
        expect:
        DoubleIEEE754Helper.compareNaN(result)
    }

    def "Double.NaN + 1.0 should return NaN"() {
        def result = Double.NaN + 1.0
        expect:
        DoubleIEEE754Helper.compareNaN(result)
    }
}
