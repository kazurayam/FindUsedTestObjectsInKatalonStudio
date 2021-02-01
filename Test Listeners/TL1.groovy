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
	 * We will print the set of objectIds recorded at every
	 * `new TestObject(objecctId)` invokations.
	 * 
	 * @param testSuiteContext
	 * @return
	 */
	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		SortedSet objectIds = rollCaller.getObjectIds()
		//
		StringBuilder sb = new StringBuilder()
		objectIds.each {
			sb.append(it)
			sb.append("\n")
		}
		print sb.toString()
		//
		File out = new File("used_testobject.txt")
		out.text = sb.toString()
	}

}