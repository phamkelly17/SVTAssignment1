import spock.lang.Specification

class IntegerTests extends Specification {

    def "Integer 0/0 should result in exception using Math.divideExact"() {

        when: "Zero is divided by zero as an integer"
        Math.divideExact(0,0)

        then: "An ArithmeticException is thrown"
        thrown(ArithmeticException)
    }

    def "Integer 1/0 should result in exception"(){

        when: "One is divided by 0 as an integer"
        Math.divideExact(1,0)

        then: "An ArithmeticException is thrown"
        thrown(ArithmeticException)

    }

    def "Integer -0 and 0 should be considered equal"() {
        expect: "Negative zero and positive zero are equal"
        -0 == 0
    }

    def "Integer 0 > -0 should be false"(){
        when: "Comparing if 0 is greater than -0"
        def result = 0 > -0

        then: "Result should be false since -0 = 0"
        result == false
    }

    def "Integer overflow on (MAX - 1) * 2 should result in infinity"() {

        when: "Integer.MAX_VALUE - 1 is multiplied by 2"
        def result = (Integer.MAX_VALUE - 1) * 2

        then: "The result should be infinity due to overflow" //but it should fail since integer infinity doesn't exist
        result > Integer.MAX_VALUE
    }

    def "(Integer.MAX_VALUE - 1) / Integer.MAX_VALUE should be below 1"(){

        when:"(Integer.MAX_VALUE - 1) is divided by Integer.MAX_VALUE"
        def result = (Integer.MAX_VALUE - 1) / Integer.MAX_VALUE

        then: "Result should be an integer less than 1"
        result < 1
    }

}