import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kazurayam.ks.testobject.RollCaller

class TL1 {
	
	/**
	 * modifies the behaviror of the constructor of TestObject
	 */
	static RollCaller rollCaller = new RollCaller()
	
	/**
	 * We will modify behavior of the TestObject class constructor.
	 * When `new TestObject(objectId)` is invoked, the objectId is
	 * recorded.
	 * See https://docs.groovy-lang.org/latest/html/documentation/core-metaprogramming.html#_constructors
	 * 
	 * @param testSuiteContext
	 * @return
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		println "Before ${testSuiteContext.getTestSuiteId()}"
	}
	
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		println "Before ${testCaseContext.getTestCaseId()}"
	}
	
	
	/**
	 * We will print the set of objectIds recorded at every
	 * `new TestObject(objecctId)` invokations.
	 * 
	 * @param testSuiteContext
	 * @return
	 */
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		println "After ${testSuiteContext.getTestSuiteId()}"
		rollCaller.print()
	}
	
}