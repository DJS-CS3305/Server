package test;

import web.HTMLTransformer;

/**
 * Tests web.HTMLTransform 
 * 
 * @author Stephen Fahy
 */
public class HTMLTest {
    public static void test() {
        String s1 = "A test of paragraphing.\n\nThis should see if the "
                + "paragraphing features work right.\n\nThe last two "
                + "sentences should be their own paragraphs.\nBut this one "
                + "should be two lines seperated by a space.";
        String s2 = "A test of linking: <linkstart to=http://google.ie "
                + "text=Cover text here#@# linkend>";
        String s3 = "A test of imaging: <imagestart link=panda.png "
                + "height=50 width=100 imageend>";
        String s4 = "A test of videos: <videostart link=empeefour.mp4 "
                + "height=500 width=1000 videoend>";
        String s5 = "A test of unsupported videos: <videostart link=unsupported.avi "
                + "height=750 width=400 videoend>";
        
        System.out.println(s1);
        System.out.println("------");
        System.out.println(HTMLTransformer.toHTML(s1));
        System.out.println("#########################");
        System.out.println(s2);
        System.out.println("------");
        System.out.println(HTMLTransformer.toHTML(s2));
        System.out.println("#########################");
        System.out.println(s3);
        System.out.println("------");
        System.out.println(HTMLTransformer.toHTML(s3));
        System.out.println("#########################");
        System.out.println(s4);
        System.out.println("------");
        System.out.println(HTMLTransformer.toHTML(s4));
        System.out.println("#########################");
        System.out.println(s5);
        System.out.println("------");
        System.out.println(HTMLTransformer.toHTML(s5));
        System.out.println("#########################");
    }
}
