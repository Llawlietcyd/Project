import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 *
 * @author yidian chen
 *
 */

public final class RSSAggregator {

    private RSSAggregator() {
    }

    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        int titleIndex = getChildElement(channel, "title");
        int linkIndex = getChildElement(channel, "link");
        int descriptionIndex = getChildElement(channel, "description");

        String title = (titleIndex != -1
                && channel.child(titleIndex).numberOfChildren() > 0)
                        ? channel.child(titleIndex).child(0).label()
                        : "Empty Title";
        String link = (linkIndex != -1
                && channel.child(linkIndex).numberOfChildren() > 0)
                        ? channel.child(linkIndex).child(0).label()
                        : "#";
        String description = (descriptionIndex != -1
                && channel.child(descriptionIndex).numberOfChildren() > 0)
                        ? channel.child(descriptionIndex).child(0).label()
                        : "No description";
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1><a href=\"" + link + "\">" + title + "</a></h1>");
        out.println("<p>" + description + "</p>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>News</th>");
        out.println("</tr>");
    }

    /**
     * Outputs the footer for the HTML page
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Returns the index of a child with a specific tag
     */
    private static int getChildElement(XMLTree xml, String tag) {
        int result = -1;
        boolean found = false;
        int i = 0;
        while (i < xml.numberOfChildren() && !found) {
            if (xml.child(i).label().equals(tag)) {
                result = i;
                found = true;
            }
            i++;
        }
        return result;
    }

    /**
     * Processes an RSS item and outputs it in HTML
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        String date = "No date available";
        String source = "No source available";
        String news = "No title available";
        String newsLink = "";

        int dateIndex = getChildElement(item, "pubDate");
        if (dateIndex != -1 && item.child(dateIndex).numberOfChildren() > 0) {
            date = item.child(dateIndex).child(0).label();
        }

        int sourceIndex = getChildElement(item, "source");
        if (sourceIndex != -1
                && item.child(sourceIndex).numberOfChildren() > 0) {
            source = "<a href=\""
                    + item.child(sourceIndex).attributeValue("url") + "\">"
                    + item.child(sourceIndex).child(0).label() + "</a>";
        }

        int titleIndex = getChildElement(item, "title");
        if (titleIndex != -1 && item.child(titleIndex).numberOfChildren() > 0) {
            news = item.child(titleIndex).child(0).label();
        }
        int descriptionIndex = getChildElement(item, "description");
        if (news.equals("No title available") && descriptionIndex != -1
                && item.child(descriptionIndex).numberOfChildren() > 0) {
            news = item.child(descriptionIndex).child(0).label();
        }
        int linkIndex = getChildElement(item, "link");
        if (linkIndex != -1 && item.child(linkIndex).numberOfChildren() > 0) {
            newsLink = item.child(linkIndex).child(0).label();
        }
        out.println("<tr>");
        out.println("<td>" + date + "</td>");
        out.println("<td>" + source + "</td>");
        if (!newsLink.isEmpty()) {
            out.println(
                    "<td><a href=\"" + newsLink + "\">" + news + "</a></td>");
        } else {
            out.println("<td>" + news + "</td>");
        }
        out.println("</tr>");
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        XMLTree xml = new XMLTree1(url);
        if (xml.label().equals("rss") && xml.hasAttribute("version")
                && xml.attributeValue("version").equals("2.0")) {
            XMLTree channel = xml.child(0);
            SimpleWriter fileOut = new SimpleWriter1L(file);
            outputHeader(channel, fileOut);

            for (int i = 0; i < channel.numberOfChildren(); i++) {
                XMLTree child = channel.child(i);
                if (child.label().equals("item")) {
                    processItem(child, fileOut);
                }
            }

            outputFooter(fileOut);
            fileOut.close();
        } else {
            out.println("The provided URL " + url + " is not an RSS 2.0 file.");
        }
    }

    /**
     * reads RSS feeds and generates HTML pages
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.println(
                "Please enter the name of the XML file containing the list of RSS feeds:");
        String feedsFile = in.nextLine();
        out.println("Please enter the name of the output index HTML file:");
        String indexFile = in.nextLine();

        XMLTree feedsXml = new XMLTree1(feedsFile);
        SimpleWriter indexOut = new SimpleWriter1L(indexFile);
        indexOut.println("<html><head><title>"
                + feedsXml.attributeValue("title") + "</title></head><body>");
        indexOut.println("<h1>" + feedsXml.attributeValue("title") + "</h1>");
        indexOut.println("<ul>");

        for (int i = 0; i < feedsXml.numberOfChildren(); i++) {
            XMLTree feed = feedsXml.child(i);
            String feedUrl = feed.attributeValue("url");
            String feedName = feed.attributeValue("name");
            String feedFile = feed.attributeValue("file");
            processFeed(feedUrl, feedFile, out);
            indexOut.println("<li><a href=\"" + feedFile + "\">" + feedName
                    + "</a></li>");
        }

        indexOut.println("</ul>");
        indexOut.println("</body></html>");
        indexOut.close();

        in.close();
        out.close();
    }
}
