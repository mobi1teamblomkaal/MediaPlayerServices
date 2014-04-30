package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import mob.assignment.rss.domain.model.Channel;
import mob.assignment.rss.util.xml.RssParser;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

public class TestRssParser {

	@Test
	public void testParser() throws IOException, XmlPullParserException, ParseException {
		Channel channel = RssParser.parse("arabiske_stemmer.xml");
		
		assertEquals("Arabiske stemmer", channel.getName());
	}

}
