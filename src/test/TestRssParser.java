package test;

import java.io.IOException;
import java.text.ParseException;

import mob.assignment.rss.domain.model.Channel;
import mob.assignment.rss.util.xml.RssParser;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import junit.framework.TestCase;

public class TestRssParser extends TestCase {

	/**
	 * Testing that the expected data is returned. Test should be update if
	 * content of rss feed changes.
	 * 
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @throws ParseException
	 */
	@Test
	public void testParser() throws IOException, XmlPullParserException,
			ParseException {
		Channel channel = RssParser
				.parse("http://podcast.dr.dk/p1/rssfeed/arabiske_stemmer.xml");

		assertEquals("Arabiske stemmer", channel.getName());
		assertEquals(
				"Programmet Arabiske Stemmer fortæller om begivenhederne og hvad der ellers sker i regionen, som araberne selv oplever det. Hver uge tager værterne, Helen Hajjaj og Naser Khader, fat på et tema, der ikke nødvendigvis når det danske mediebillede, men som fylder meget i regionen.",
				channel.getDescription());
		assertEquals(114, channel.getEpisodeList().size());
	}
}
