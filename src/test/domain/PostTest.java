package domain;

import static org.junit.Assert.*;

public class PostTest {

    @org.junit.Test
    public void testGetPostDateTimeInMs() throws Exception {
        long beforePostTime = System.currentTimeMillis();
        Thread.sleep(100);
        Post p = new Post("arun", "message");
        Thread.sleep(100);
        long afterPostTime = System.currentTimeMillis();
        assertTrue(beforePostTime < p.getPostDateTimeInMs());
        assertTrue(afterPostTime > p.getPostDateTimeInMs());
    }

    @org.junit.Test
    public void testGetMessage() throws Exception {
        Post p = new Post("arun", "message2");
        assertTrue("message2".equals(p.getMessage()));
    }

    @org.junit.Test
    public void testGetUser() throws Exception {
        Post p = new Post("arun", "message3");
        assertTrue("arun".equals(p.getUser()));
    }

    @org.junit.Test
    public void testToString() throws Exception {
        Post p = new Post("arun", "message4");
        assertTrue(p.toString().startsWith("arun - message4 ("));
        assertTrue(p.toString().endsWith(" ago.)"));
    }

    @org.junit.Test
    public void testCompareTo() throws Exception {
        Post p1 = new Post("arun", "message5");
        Thread.sleep(100);
        Post p2 = new Post("arun", "message6");

        assertTrue(p1.compareTo(p2) > 0);
        assertTrue(p2.compareTo(p1) < 0);
        assertTrue(p1.compareTo(null) > 0);
    }
}