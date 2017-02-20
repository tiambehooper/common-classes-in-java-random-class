package com.theironyard;

import net.doughughes.testifier.exception.*;
import net.doughughes.testifier.matcher.RegexMatcher;
import net.doughughes.testifier.output.OutputStreamInterceptor;
import net.doughughes.testifier.test.TestifierTest;
import net.doughughes.testifier.util.Invoker;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LuckyNumbersTest extends TestifierTest{

    @Test
    public void testFiveLuckyNumbersWithNoArgumentConstructor() {
        /* arrange */

        /* act */
        try {
            LuckyNumbers lucky = (LuckyNumbers) Invoker.instantiate(LuckyNumbers.class);
            Invoker.invoke(lucky, "fiveLuckyNumbers");
        } catch (CannotInvokeMethodException | CannotFindMethodException | CannotFindConstructorException | CannotInstantiateClassException | CannotAccessMethodException e) {
            fail(e.getMessage());
        }

        /* assert */
        OutputStreamInterceptor out = (OutputStreamInterceptor) System.out;
        assertThat("Output didn't match expected pattern of 'Your five lucky numbers are: 1, 2, 3, 4, 5'",
                out.getLines().get(0), RegexMatcher.matches("Your five lucky numbers are: -{0,1}[0-9]+, -{0,1}[0-9]+, -{0,1}[0-9]+, -{0,1}[0-9]+, -{0,1}[0-9]+"));
    }

    @Test
    public void testFiveLuckyNumbersWithLongConstructor() {
        /* arrange */

        /* act */
        try {
            LuckyNumbers lucky = (LuckyNumbers) Invoker.instantiate(LuckyNumbers.class, 250505100001L);
            Invoker.invoke(lucky, "fiveLuckyNumbers");
        } catch (CannotInvokeMethodException | CannotFindMethodException | CannotFindConstructorException | CannotInstantiateClassException | CannotAccessMethodException e) {
            fail(e.getMessage());
        }

        /* assert */
        OutputStreamInterceptor out = (OutputStreamInterceptor) System.out;
        assertThat(out.getLines().get(0), equalTo("Your five lucky numbers are: 96, 64, 53, 20, 70"));
    }

    @Test
    public void testFiveLuckyNumbersUsesRandProperty(){
        /* arrange */

        /* act */
        String source = null;
        try {
            source = codeWatcher.getMainSourceCodeService().getDescriptionOfMethod("fiveLuckyNumbers");
        } catch (CannotFindMethodException e) {
            fail(e.getMessage());
        }

        /* assert */
        assertThat("fiveLuckyNumbers should use a reference to the rand property",
                source, RegexMatcher.matches("^.*?MethodCallExpr ((NameExpr\\[rand\\]|(ThisExpr FieldAccessExpr\\[rand\\])) MethodName\\[nextInt\\]).*?$"));
        assertThat("fiveLuckyNumbers should not instantiate Random",
                source, not(RegexMatcher.matches("^.*?ObjectCreationExpr ClassOrInterfaceType\\[Random\\].*?$")));


    }

}