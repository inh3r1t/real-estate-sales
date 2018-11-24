package com.zx.base.common;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * AuthUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 23, 2018</pre>
 */
public class AuthUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: isAdmin(List<SysRole> roles)
     */
    @Test
    public void test() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(74);
        list.add(14);
        list.add(34);
        list.add(94);
        list.add(44);
        list.add(54);
        list.add(84);
        list.add(24);
        list.forEach((t) ->
                System.out.println(t)
        );
        System.out.println(list.get(0));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        TreeSet<Integer> list2 = new TreeSet<>();
        list2.add(74);
        list2.add(14);
        list2.add(34);
        list2.add(94);
        list2.add(44);
        list2.add(54);
        list2.add(84);
        list2.add(24);
        list2.forEach((t) ->
                System.out.println(t)
        );
        System.out.println(list2.first());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");

        HashMap<Integer,String> list3 = new HashMap<>();
        list3.put(74,"3423dde2w");
        list3.put(14,"d2342d32ew");
        list3.put(34,"d242343432d256ew");
        list3.put(94,"2342dd42ew");
        list3.put(44,"d23432d2456ew");
        list3.put(54,"dd23432234ew");
        list3.put(84,"dd234325442ew");
        list3.put(24,"dd56462ew");
        list3.forEach((k,v) ->
                System.out.println("index : " + k + " value : " + v)
        );
        System.out.println(list3.get(0));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        list3.put(64,"dd56462ew");
        list3.put(164,"dd56462ew");
        list3.put(124,"dd56462ew");
        list3.forEach((k,v) ->
                System.out.println("index : " + k + " value : " + v)
        );

    }


} 
