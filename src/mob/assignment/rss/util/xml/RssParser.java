package mob.assignment.rss.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import mob.assignment.rss.domain.model.Channel;
import mob.assignment.rss.domain.model.Episode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class RssParser {

	public static Channel parse(String urlString) throws IOException,
			XmlPullParserException, ParseException {

		InputStream stream = connect(urlString);
		Channel channel;
		try {
			channel = parseXml(stream);
		} finally  {
			stream.close();
		}
		return channel;
	}

	private static Channel parseXml(InputStream stream)
			throws XmlPullParserException, IOException, ParseException {

		Channel channel = new Channel();

		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(stream, null);
		parser.nextTag();
		parser.nextTag();

		parser.require(XmlPullParser.START_TAG, null, "channel");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
			String name = parser.getName();
			

			if (name.equalsIgnoreCase(Channel.TITLE)) {
				channel.setName(readText(parser));
			} else if (name.equalsIgnoreCase(Channel.DESCRIPTION)) {
				channel.setDescription(readText(parser));
			} else if (name.equalsIgnoreCase(Channel.ITUNES_IMAGE)) {
				try {
					URL coverart = new URL(parser.getAttributeValue(null,
							"href"));
					channel.setCoverart(coverart);
				} finally {
					parser.next();
					parser.nextTag();
				}
			} else if (name.equalsIgnoreCase(Channel.ITEM)) {
				Episode episode = new Episode();
				parser.require(XmlPullParser.START_TAG, null, "item");
				while (parser.next() != XmlPullParser.END_TAG) {
					if (parser.getEventType() != XmlPullParser.START_TAG) {
		                continue;
		            }
					name = parser.getName();
					if (name.equalsIgnoreCase(Episode.TITLE)) {
						episode.setTitle(readText(parser));
					} else if (name.equalsIgnoreCase(Episode.DESCRIPTION)) {
						episode.setDescription(readText(parser));
					} else if (name.equalsIgnoreCase(Episode.DURATION)) {
						episode.setDuration(parseDuration(readText(parser)));
					} else if (name.equalsIgnoreCase(Episode.LOCATION)) {
						try {
							episode.setLocation(parser.getAttributeValue(
								null, "url"));
						} finally {
							parser.next();
							parser.nextTag();
						}
					} else {
						skip(parser);
					}
				}
				channel.addEpisode(episode);
				parser.next();
				parser.nextTag();
			} else {
				skip(parser);
			}
		}
		return channel;
	}

	private static String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String text = "";
		if (parser.next() == XmlPullParser.TEXT) {
			text = parser.getText();
			parser.nextTag();
        }
		return text;
	}

	private static InputStream connect(String urlString)
			throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		return conn.getInputStream();
	}

	// Skips tags the parser isn't interested in. Uses depth to handle nested
	// tags. i.e.,
	// if the next tag after a START_TAG isn't a matching END_TAG, it keeps
	// going until it
	// finds the matching END_TAG (as indicated by the value of "depth" being
	// 0).
	private static void skip(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

	private static int parseDuration(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			return (int) (sdf.parse(time).getTime() + 3600000);
		} catch (Exception e) {
			return 0;
		}
	}
}
