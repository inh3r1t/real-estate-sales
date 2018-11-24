package com.zx.base.common; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* ValidateUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 27, 2018</pre> 
* @version 1.0 
*/ 
public class ValidateUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: integer(String value) 
* 
*/ 
@Test
public void testInteger() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: number(String value) 
* 
*/ 
@Test
public void testNumber() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: aSCII(String value) 
* 
*/ 
@Test
public void testASCII() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: chinese(String value) 
* 
*/ 
@Test
public void testChinese() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: date(String value) 
* 
*/ 
@Test
public void testDate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: email(String value) 
* 
*/ 
@Test
public void testEmail() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: idcard(String value) 
* 
*/ 
@Test
public void testIdcard() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: ip4(String value) 
* 
*/ 
@Test
public void testIp4() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: mobile(String value) 
* 
*/ 
@Test
public void testMobile() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: notEmpty(String value) 
* 
*/ 
@Test
public void testNotEmpty() throws Exception { 
    System.out.println(ValidateUtil.notBlank("dasfda"));
    System.out.println(ValidateUtil.notBlank(""));
    System.out.println(ValidateUtil.notBlank("    "));
    System.out.println(ValidateUtil.notBlank("" +
            ""));
    System.out.println(ValidateUtil.notBlank(null));
}

/** 
* 
* Method: picture(String value) 
* 
*/ 
@Test
public void testPicture() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: qq(String value) 
* 
*/ 
@Test
public void testQq() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: rar(String value) 
* 
*/ 
@Test
public void testRar() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: tel(String value) 
* 
*/ 
@Test
public void testTel() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: url(String value) 
* 
*/ 
@Test
public void testUrl() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: zipCode(String value) 
* 
*/ 
@Test
public void testZipCode() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: match(String regex, String str) 
* 
*/ 
@Test
public void testMatch() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ValidateUtil.getClass().getMethod("match", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
