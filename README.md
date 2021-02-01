# Finding Used Test Objects in Katalon Studio

This is a small Katalon Stduio project for demonstration purpose. You can download the zip file at the [Rleases] page, unzip it, open it in your local Katalon Studio.

This project is developed to propose a solution raised in the Katalon User forum at ["No way to know which object in repository is being used in scripts"](https://forum.katalon.com/t/no-way-to-know-which-object-in-repository-is-being-used-in-scripts/51669/7).

This project was developed using Katalon Studio 7.6.6, but will work any version above 7.0.

## Problem to solve

The Object Repository in Katalon Studio tends to get messy. You create tens or hundreds of Test Objects, most of which are used by test scripts, but some of which are not used. The problem is that it is not easy to distinguish which Test Object is used, which is not.

Katalon Studio provides a feature named "Tools > Test Object > Show unused test object". User can identify which entries in the "Object Repository" are used in test scripts.

However, there is a difficult case. You can create instances of `com.kms.katalon.core.testobject.TestObject` class using the constructor in a test case script.

```
TestObject tObj1 = new TestObject("myTestObject#1")
```
The "Show unused test object" tool does not support this case.

## Solution

Using Groovy's Metaprogramming technique, I could write a set of code that enables me to list all ObjectIDs using in test cases.

## Description

### How to run the demo

Run `Test Suites/TS1`. It will indirectly runs [`Test Cases/TC1`](Scripts/TC1/Script1612146977725.groovy).

Here I show the fragment of TC1:
 
```
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

TestObject tObj1 = new TestObject("myTestObject#1")
...
WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment'))
...
```

You will see the following output in the console:

```
Object Repository/Page_CURA Healthcare Service/a_Go to Homepage
Object Repository/Page_CURA Healthcare Service/a_Make Appointment
Object Repository/Page_CURA Healthcare Service/button_Book Appointment
Object Repository/Page_CURA Healthcare Service/button_Login
Object Repository/Page_CURA Healthcare Service/input_Medicaid_programs
Object Repository/Page_CURA Healthcare Service/input_Password_password
Object Repository/Page_CURA Healthcare Service/input_Username_username
Object Repository/Page_CURA Healthcare Service/select_Tokyo CURA Healthcare Center        _5b4107
Object Repository/Page_CURA Healthcare Service/span_Visit Date (Required)_glyphicon glyphi_cada34
Object Repository/Page_CURA Healthcare Service/td_22
Object Repository/Page_CURA Healthcare Service/textarea_Comment_comment
myTestObject#1
myTestObject#2
myTestObject#3

```

This list shows you the list of identifiers of TestObject instances used by TL1. 

### Technology

This source code of 
[`com.kazurayam.ks.testobject.RollCaller`](Keywords/com/kazurayam/ks/testobject/RollCaller.groovy) would be interesting. This employs Groovy's Metaprogramming technique.


