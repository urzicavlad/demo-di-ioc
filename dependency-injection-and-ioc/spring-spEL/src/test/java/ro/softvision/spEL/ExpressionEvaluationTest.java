package ro.softvision.spEL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.spEL.entity.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ExpressionEvaluationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionEvaluationTest.class);

    private ExpressionParser parser = new SpelExpressionParser();

    // The constructor arguments are name, birthday, and nationality.
    private static final Inventor NIKOLA_TESLA = new Inventor("Nikola Tesla",
            LocalDate.of(1856, 7, 9), "Serbian",
            new PlaceOfBirth("Smiljan", "Croatia")
    );

    private static final Society SOCIETY = new Society();

    @Autowired
    private ApplicationContext context;

    @Before
    public void setUp() {
        String[] inventions = {
                "Wireless Energy Transmission",
                "Supersonic Airships Powered",
                "Remote Controlled Navies",
                "The Thought Camera",
                "The Earthquake Machine",
                "Artificial Tidal Waves",
                "The Death Ray"
        };
        NIKOLA_TESLA.setInventions(inventions);
        SOCIETY.addInventor(NIKOLA_TESLA);
    }

    @After
    public void tearDown() {
        ((ConfigurableApplicationContext) context).registerShutdownHook();
    }

    @Test
    public void simpleTestParser() {
        Expression expression = parser.parseExpression("'Hello World!'");
        String message = (String) expression.getValue();
        LOGGER.debug("MESSAGE : {}", message);
        assertEquals("Hello World!", message);
    }

    @Test
    public void simpleTestParserWithConcatenation() {
        Expression expression = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) expression.getValue();
        LOGGER.debug("MESSAGE : {}", message);
        assertEquals("Hello World!", message);
    }

    @Test
    public void simpleTestParserGetBytesFromString() {
        // you can call bytes or getBytes() form String, the result will be the same.
        Expression expression = parser.parseExpression("'Hello World!'.bytes");
        byte[] bytes = (byte[]) expression.getValue();
        LOGGER.debug("MESSAGE : {}", Arrays.toString(bytes));
        assertArrayEquals("Hello World!".getBytes(), bytes);
    }

    @Test
    public void simpleTestParserGetLengthFromString() {
        Expression expression = parser.parseExpression("'Hello World!'.length()");
        int length = (int) expression.getValue();
        LOGGER.debug("MESSAGE : {}", length);
        assertEquals(12, length);
    }

    @Test
    public void simpleTestParserModifyStringUsingConstructorAlso() {
        Expression expression = parser.parseExpression("new String('Hello World!').toUpperCase()");
        String toUpperCase = expression.getValue(String.class);
        LOGGER.debug("MESSAGE : {}", toUpperCase);
        assertEquals("HELLO WORLD!", toUpperCase);
    }

    @Test
    public void testParserAgainstASpecificObjectInstanceWithEvaluationContext() {
        // Try to get name from the object.
        Expression expression = parser.parseExpression("name");
        // Create EvaluationContext and add "NIKOLA_TESLA" object in context.
        EvaluationContext context = new StandardEvaluationContext(NIKOLA_TESLA);
        // Get "name" from context.
        String name = (String) expression.getValue(context);
        LOGGER.debug("NAME : {}", name);
        assertEquals("Nikola Tesla", name);
    }

    @Test
    public void testParserAgainstASpecificObjectInstance() {
        Expression expression = parser.parseExpression("name");
        String name = (String) expression.getValue(NIKOLA_TESLA);
        LOGGER.debug("NAME : {}", name);
        assertEquals("Nikola Tesla", name);
    }

    @Test
    public void testParserWithBooleanExpression() {
        Expression expression = parser.parseExpression("name == 'Nikola Tesla'");
        EvaluationContext context = new StandardEvaluationContext(NIKOLA_TESLA);
        boolean result = (boolean) expression.getValue(context);
        LOGGER.debug("RESULT OF THE EVALUATION : {}", result);
        assertTrue(result);
    }

    @Test
    public void testParserWithElementsFromAList() {

        class Simple {

            private List<Boolean> booleanList = new ArrayList<>();

            // getters an setter will be used by parser.
            public List<Boolean> getBooleanList() {
                return booleanList;
            }

            public Simple setBooleanList(List<Boolean> booleanList) {
                this.booleanList = booleanList;
                return this;
            }

        }

        Simple simple = new Simple();
        simple.booleanList.add(true);
        simple.booleanList.add(false);
        simple.booleanList.add(true);

        EvaluationContext context = new StandardEvaluationContext(simple);

        // Take the first element form the booleanList
        Expression expression = parser.parseExpression("booleanList[0]");

        /* Set the first element from the booleanList with another value (from true to false), the String value of "false"
        will be converted to Boolean.*/
        expression.setValue(context, "false");
        boolean nowIsFalse = simple.booleanList.get(0);
        LOGGER.debug("ACTUAL VALUE OF THE FIRST INDEX : {}", nowIsFalse);
        assertFalse(nowIsFalse);
    }


    @Test
    public void testParserConfiguration() {

        class Demo {
            private List<String> list;

            public List<String> getList() {
                return list;
            }

            public Demo setList(List<String> list) {
                this.list = list;
                return this;
            }
        }

        /*
         * This will turn on auto null reference initialization and auto collection growing.
         * That means if we have null the collection will auto-grow.
         * */
        SpelParserConfiguration configuration = new SpelParserConfiguration(true, true);

        parser = new SpelExpressionParser(configuration);

        Expression expression = parser.parseExpression("list[3]");

        Demo demo = new Demo();
        Object object = expression.getValue(demo);
        assertNotNull(object);

        LOGGER.debug("DEMO LIST : {}", demo.getList());
        assertEquals(4, demo.getList().size());
    }


    @Test
    public void testCompilerConfiguration() {
        /*
         * There are few modes the compiler can operate:
         * The modes are captured in a enum: org.springframework.expression.spel.SpeCompilerMode
         *   We have the following modes:
         *       - OFF : the default compiler mode this means the compiler is switched off.
         *       - IMMEDIATE : in immediate mode the expressions are compiled as soon as possible.
         *                     This is typically after the first interpreted evaluation.
         *       - MIXED: in this mode the expressions are switched between interpreted and compiled mode over time.
         * */

        class Demo {

            private String payload;

            public String getPayload() {
                return payload;
            }

            public Demo setPayload(String payload) {
                this.payload = payload;
                return this;
            }
        }
        Demo demo = new Demo();
        demo.setPayload("Important Payload!");

        // establish the context with the root object
        EvaluationContext context = new StandardEvaluationContext(demo);
        // create configuration with Compiler Mode IMMEDIATE
        SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, this.getClass().getClassLoader());
        // instruct the parser to use the above configuration
        parser = new SpelExpressionParser(configuration);
        // create the expression
        Expression expression = parser.parseExpression("payload");

        String payload = expression.getValue(context, String.class);
        LOGGER.debug("PAYLOAD : {}", payload);
        assertEquals("Important Payload!", payload);

    }


    @Test
    public void testAnnotationBasedConfigurationOnField() {
        ValueBean fieldValueTestBean = context.getBean(ValueBean.class);
        String defaultLocale = fieldValueTestBean.getDefaultLocale();
        LOGGER.debug("DEFAULT LOCALE : {}", defaultLocale);
        assertEquals("US", defaultLocale);
    }

    @Test
    public void testAnnotationBasedConfigurationOnMethod() {
        ValueBean fieldValueTestBean = context.getBean(ValueBean.class);
        String operationSystem = fieldValueTestBean.getOperationSystem();
        LOGGER.debug("OS Name : {}", operationSystem);
        assertEquals(System.getProperties().get("os.name"), operationSystem);
    }

    @Test
    public void testAnnotationBasedConfigurationOnParameter() {
        ValueBean fieldValueTestBean = context.getBean(ValueBean.class);
        String importantDependency = fieldValueTestBean.getImportantDependency();
        LOGGER.debug("Important dependency : {}", importantDependency);
        assertEquals(System.getProperties().get("java.vm.specification.vendor"), importantDependency);
    }

    @Test
    public void testLiteralExpressions() {
        Double doubleValue = (Double) parser.parseExpression("6.022").getValue();
        Integer maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();
        Boolean booleanValue = (Boolean) parser.parseExpression("true").getValue();
        Object nullValue = parser.parseExpression("null").getValue();

        LOGGER.debug("DOUBLE_VALUE : {}", doubleValue);
        LOGGER.debug("INTEGER_VALUE : {}", maxValue);
        LOGGER.debug("BOOLEAN_VALUE : {}", booleanValue);
        LOGGER.debug("NULL_VALUE : {}", nullValue);

        assertEquals(Double.valueOf(6.022), doubleValue);
        assertEquals(Integer.valueOf(2147483647), maxValue);
        assertEquals(true, booleanValue);
        assertNull(nullValue);
    }

    @Test
    public void testPropertiesReference() {
        EvaluationContext context = new StandardEvaluationContext(NIKOLA_TESLA);
        /*
         * We will get only the year: 1856 (Tesla year of birth) and we will add 1900.
         * Result will be 3756.
         * b from Birthdate can be also b instead of B (first letter will be .toLowercase()) :
         * Case insensitivity is allowed for the first letter of property names.
         * The contents of arrays and lists are obtained using square bracket notation.
         * */
        Expression expressionForBirthYear = parser.parseExpression("Birthdate.Year + 1900");
        Expression expressionForBirthCity = parser.parseExpression("PlaceOfBirth.City");
        Integer birthYear = (Integer) expressionForBirthYear.getValue(context);
        String birthCity = (String) expressionForBirthCity.getValue(context);
        LOGGER.debug("BIRTH_YEAR : {}", birthYear);
        LOGGER.debug("BIRTH_CITY: {}", birthCity);
        assertEquals(Integer.valueOf(3756), birthYear);
        assertEquals("Smiljan", birthCity);
    }

    @Test
    public void testPropertiesWhenIHaveListsOrArrays() {

        EvaluationContext teslaContext = new StandardEvaluationContext(NIKOLA_TESLA);
        Expression expressionForInvention = parser.parseExpression("inventions[1]");
        String secondInvention = expressionForInvention.getValue(teslaContext, String.class);

        LOGGER.debug("SECOND INVENTION OF NIKOLA TESLA : {}", secondInvention);
        assertEquals("Supersonic Airships Powered", secondInvention);

        EvaluationContext societyContext = new StandardEvaluationContext(SOCIETY);
        Expression expressionForFirstInventionsFormFirstInventor = parser.parseExpression("members[0].inventions[0]");
        String firstInvention = expressionForFirstInventionsFormFirstInventor.getValue(societyContext, String.class);

        LOGGER.debug("FIRST INVENTION OF NIKOLA TESLA : {}", firstInvention);
        assertEquals("Wireless Energy Transmission", firstInvention);

        Expression expressionThePlaceOfBirthCountryForFisrtAdvisor =
                parser.parseExpression("officers['advisors'][0].placeOfBirth.country");

        String countryOfBirthForFirstAdvisor = expressionThePlaceOfBirthCountryForFisrtAdvisor.getValue(societyContext, String.class);

        LOGGER.debug("test {}", countryOfBirthForFirstAdvisor);


    }

    @Test
    public void testInLineLists() {
        List numbers = (List) parser.parseExpression("{1,2,3,4,5}").getValue();
        LOGGER.debug("LIST : {}", numbers);
        assertNotNull(numbers);
        List listOfListOfNumbers = (List) parser.parseExpression("{{1,2,3},{4,5}}").getValue();
        LOGGER.debug("LIST OF LISTS : {}", listOfListOfNumbers);
        assertNotNull(listOfListOfNumbers);
    }

    @Test
    public void testInLineMaps() {
        StandardEvaluationContext context = new StandardEvaluationContext(NIKOLA_TESLA);
        Map inventorInfo = (Map) parser.parseExpression("{name:'Nikola', dob:'10-July-1856'}")
                .getValue(context);
        LOGGER.debug("MAP : {}", inventorInfo);
        assertNotNull(inventorInfo);
        Map mapOfMaps = (Map) parser.parseExpression("{name: {firstName:'Nikola', last: 'Tesla'}, dob:{day:10, month:'July', year:1856}}")
                .getValue(context);
        LOGGER.debug("MAP OF MAPS {}", mapOfMaps);
        assertNotNull(mapOfMaps);
    }

    @Test
    public void testArrayConstruction() {
        // Array declaration
        int[] numbers1 = (int[]) parser.parseExpression("new int[3]").getValue();
        LOGGER.debug("NUMBERS_1 {}", Arrays.toString(numbers1));

        // Array with initializer
        int[] numbers2 = (int[]) parser.parseExpression("new int[] {1,2,3}").getValue();
        LOGGER.debug("NUMBERS_2 {}", Arrays.toString(numbers2));

        // Multi dimensional array --- ONLY DECLARATION! Using an initializer to build a multi-dimensional array is not currently supported
        int[][] numbers3 = (int[][]) parser.parseExpression("new int[2][2]").getValue();
        LOGGER.debug("NUMBERS_3 {}", Arrays.deepToString(numbers3));
    }

    @Test
    public void testMethods() {
        String bc = parser.parseExpression("'abc'.substring(1,3)").getValue(String.class);
        LOGGER.debug("BC : {}", bc);
        assertEquals("bc", bc);
        Boolean isMember = parser.parseExpression("isMember('Nikola Tesla')")
                .getValue(new StandardEvaluationContext(SOCIETY), Boolean.class);
        LOGGER.debug("IS_MEMBER : {}", isMember);
        assertNotNull(isMember);
        assertTrue(isMember);
    }

    @Test
    public void testOperators() {
        // evaluates to true
        Boolean trueValue = parser.parseExpression("2 == 2").getValue(Boolean.class);
        LOGGER.debug("2 == 2 : {}", trueValue);
        assertNotNull(trueValue);
        assertTrue(trueValue);

        // evaluates to false
        Boolean falseValue = parser.parseExpression("2 < -5.0").getValue(Boolean.class);
        LOGGER.debug("2 < -5.0 : {}", falseValue);
        assertNotNull(falseValue);
        assertFalse(falseValue);

        // evaluates to true
        Boolean trueValueSmart = parser.parseExpression("'black' < 'block'").getValue(Boolean.class);
        LOGGER.debug("black < block : {}", trueValueSmart);
        assertNotNull(trueValueSmart);
        assertTrue(trueValueSmart);
    }

    @Test
    public void testLogicalOperators() {
        Boolean falseValue1 = parser.parseExpression("isMember('Nikola Tesla') and  isMember('Vlad Urzica')")
                .getValue(new StandardEvaluationContext(SOCIETY), Boolean.class);
        LOGGER.debug("TESLA [AND] VLAD ARE MEMBERS OF SOCIETY: {}", falseValue1);
        assertNotNull(falseValue1);
        assertFalse(falseValue1);

        Boolean trueValue1 = parser.parseExpression("isMember('Nikola Tesla') and  1==1")
                .getValue(new StandardEvaluationContext(SOCIETY), Boolean.class);
        LOGGER.debug("TESLA IS A MEMBER OF SOCIETY [AND] 1==1: {}", trueValue1);
        assertNotNull(trueValue1);
        assertTrue(trueValue1);

        Boolean trueValue2 = parser.parseExpression("isMember('Nikola Tesla') OR isMember('Vlad Urzica')")
                .getValue(new StandardEvaluationContext(SOCIETY), Boolean.class);
        LOGGER.debug("TESLA [OR] VLAD ARE MEMBERS OF SOCIETY: {}", trueValue2);
        assertNotNull(trueValue2);
        assertTrue(trueValue2);

        Boolean falseValue2 = parser.parseExpression("isMember('Vlad Urzica') OR 1 != 1")
                .getValue(new StandardEvaluationContext(SOCIETY), Boolean.class);
        LOGGER.debug("VLAD ARE MEMBERS OF SOCIETY AND 1 IS NOT EQUAL WITH 1: {}", falseValue2);
        assertNotNull(falseValue2);
        assertFalse(falseValue2);

        Boolean trueValue3 = parser.parseExpression("isMember('Nikola Tesla') and !isMember('Vlad Urizca')")
                .getValue(new StandardEvaluationContext(SOCIETY), Boolean.class);
        LOGGER.debug("TESLA IS A MEMBER OF SOCIETY [AND] VLAD NOT: {}", trueValue3);
        assertNotNull(trueValue3);
        assertTrue(trueValue3);
    }


    @Test
    public void testMathematicalOperators() {

        // ADDITION
        Integer two = parser.parseExpression("1 + 1").getValue(Integer.class);
        LOGGER.debug("TWO : {}", two);
        assertEquals(Integer.valueOf(2), two);

        // CONCATENATION
        String testSpring = parser.parseExpression("'test' + 'Spring'").getValue(String.class);
        LOGGER.debug("testSpring : {}", testSpring);
        assertEquals("testSpring", testSpring);

        // SUBTRACTION
        Integer zero = parser.parseExpression("1 - 1").getValue(Integer.class);
        Double zeroDouble = parser.parseExpression("1.0 - 1.0").getValue(Double.class);
        LOGGER.debug("ZERO : {}", zero);
        LOGGER.debug("ZERO_DOUBLE : {}", zeroDouble);
        assertEquals(Integer.valueOf(0), zero);
        assertEquals(Double.valueOf(0), zeroDouble);

        // MULTIPLICATION
        Integer ten = parser.parseExpression("5 * 2").getValue(Integer.class);
        LOGGER.debug("TEN : {}", ten);
        assertEquals(Integer.valueOf(10), ten);

        // DIVISION
        Integer one = parser.parseExpression("10 / 10").getValue(Integer.class);
        LOGGER.debug("ONE : {}", one);
        assertEquals(Integer.valueOf(1), one);

        // MODULUS
        Integer three = parser.parseExpression("7 % 4").getValue(Integer.class);
        LOGGER.debug("THREE : {}", three);
        assertEquals(Integer.valueOf(3), three);

        // OPERATOR PRECEDENCE
        Integer minusTwentyOne = parser.parseExpression("1 + 2 - 3 * 8").getValue(Integer.class);
        LOGGER.debug("MINUS TWENTYONE : {}", minusTwentyOne);
        assertEquals(Integer.valueOf(-21), minusTwentyOne);
    }

    @Test
    public void testAssigment() {
        Inventor inventor = new Inventor();
        EvaluationContext context = new StandardEvaluationContext(inventor);

        parser.parseExpression("name").setValue(context, "Vlad Urzica");
        LOGGER.debug("INVENTOR NAME : {}", inventor.getName());
        assertEquals("Vlad Urzica", inventor.getName());

        // ALTERNATIVELY
        String value = parser.parseExpression("Name = 'Nikolas Tesla'")
                .getValue(context, String.class);
        LOGGER.debug("INVENTOR NAME : {}", inventor.getName());
        assertEquals("Nikolas Tesla", value);
    }

    @Test
    public void testTypes() {
        Class dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);
        LOGGER.debug("DATE CLASS : {}", dateClass);
        assertEquals(Date.class, dateClass);

        Boolean trueValue = parser.parseExpression("T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR").getValue(Boolean.class);
        LOGGER.debug("TRUE : {}", trueValue);
        assertNotNull(trueValue);
        assertTrue(trueValue);
    }


    @Test
    public void testConstructors() {
        Inventor einstein = parser.parseExpression("new ro.softvision.spEL.entity.Inventor('Albert Einstein', 'German')").getValue(Inventor.class);
        LOGGER.debug("EINSTEIN OBJECT : {}", einstein);
        assertNotNull(einstein);
    }

    @Test
    public void testVariables() {
        EvaluationContext context = new StandardEvaluationContext(NIKOLA_TESLA);
        context.setVariable("newName", "Mike Tesla");

        parser.parseExpression("name = #newName").getValue(context);

        LOGGER.debug("NEW NAME : {}", NIKOLA_TESLA.getName());
        assertEquals("Mike Tesla", NIKOLA_TESLA.getName());

        List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17);
        context.setVariable("primes", primes);

        // all prime numbers > 10 from the list (using selection ?{...})
        // evaluates to [11, 13, 17]
        List primesGreaterThanTen = (List) parser.parseExpression("#primes.?[#this > 10]").getValue(context);
        LOGGER.debug("PRIMES GREATER THAN TEN : {}" , primesGreaterThanTen);
        assertNotNull(primesGreaterThanTen);
        assertEquals(3, primesGreaterThanTen.size());
    }

    @Test
    public void testFunctions() throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("reverse", StringUtils.class.getDeclaredMethod("reverseString", String.class));

        String helloWordReversed = parser.parseExpression("#reverse('Hello World!')").getValue(context, String.class);

        LOGGER.debug("HELLO WORLD! REVERSED : {}", helloWordReversed);
        assertEquals("!dlroW olleH", helloWordReversed);
    }

}
