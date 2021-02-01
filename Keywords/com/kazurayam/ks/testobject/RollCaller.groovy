package com.kazurayam.ks.testobject

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.testobject.ObjectRepository
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.configuration.RunConfiguration
import java.nio.file.Files
import java.nio.file.Path
import java.lang.reflect.Constructor
import java.nio.file.Paths

import internal.GlobalVariable

public class RollCaller {

	SortedSet usedTestObjects = new TreeSet()

	RollCaller() {
		this.decorate()
	}

	private def decorate() {
		def oneArgConstructor = findOneArgConstructor()
		TestObject.metaClass.constructor = { String objectId ->
			println "constructed with ${objectId}"
			usedTestObjects.add(objectId)
			def obj = oneArgConstructor.newInstance(objectId)
			return obj
		}
		ObjectRepository.metaClass.'static'.invokeMethod = { String name, args ->
			def result = null
			switch (name) {
				case 'findTestObject':
				//println "ObjectRepository.findTestObject(${args}) was called"
					usedTestObjects.add(args[0])
					break
			}
			if (result == null) {
				result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
			}
			return result
		}
	}

	private Constructor<TestObject> findOneArgConstructor() {
		Constructor cnstr = null
		Constructor[] arr = TestObject.constructors
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getParameterCount() == 1) {
				cnstr = arr[i]
			}
		}
		return cnstr
	}

	SortedSet<String> getObjectIds() {
		return new TreeSet(usedTestObjects)
	}
}
