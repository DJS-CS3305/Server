package web;

/**
 * Class for translating from the pseudo-HTML used by the admin client
 * to regular HTML for the website.
 * 
 * @author Stephen Fahy
 */
public class HTMLTransformer {
    private static final String LINK_START = "<linkstart";
    private static final String LINK_END = "linkend>";
    private static final String IMAGE_START = "<imagestart";
    private static final String IMAGE_END = "imageend>";
    private static final String VIDEO_START = "<videostart";
    private static final String VIDEO_END = "videoend>";
    private static final String TEXT_ESCAPE = "#@#";
    
    /**
     * Translates pseudo-HTML to normal HTML.
     * @param pseudo Pseudo-HTML.
     * @return Translated normal HTML.
     */
    public static String toHTML(String pseudo) {
        String output = pseudo;
        
        output = replaceLinks(output);
        output = replaceImages(output);
        output = replaceVideos(output);
        output = sanitize(output);
        
        return output;
    }
    
    /**
     * Sanitizes a string to be HTML compatible by replacing certain characters
     * with their escape equivalents and replacing double line breaks with p
     * tags.
     * 
     * @param pseudo The string to sanitize.
     * @return A HTML-compatible string.
     */
    private static String sanitize(String pseudo) {
        String output = pseudo;
        
        output = output.replaceAll("&", "&amp;");
        output = output.replaceAll("\n\n", "</p><p>");
        output = output.replaceAll("\n", " ");
        //finish encapsulating with p tags
        output = "<p>" + output + "</p>";
        
        return output;
    }
    
    /**
     * Replaces all pseudoHTML hyperlinks with HTML a tags.
     * 
     * @param pseudo PseudoHTML.
     * @return PseudoHTML with HTML hyperlinks.
     */
    private static String replaceLinks(String pseudo) {
        String output = pseudo;
        
        while(output.contains(LINK_START)) {
            //while there are still links to be replaced.
            String tagContents = getSubstring(output, LINK_START, LINK_END);
            String url = getSubstring(tagContents, "to=", " text=");
            String text = getSubstring(tagContents, "text=", TEXT_ESCAPE);
            
            String html = "<a href=\"" + url + "\">" + text + "</a>";
            
            output = output.replace(LINK_START + tagContents + LINK_END, html);
        }
        
        return output;
    }
    
    /**
     * Replaces all pseudoHTML images with HTML img tags.
     * 
     * @param pseudo PseudoHTML.
     * @return PseudoHTML with HTML images.
     */
    private static String replaceImages(String pseudo) {
        String output = pseudo;
        
        while(output.contains(IMAGE_START)) {
            //while there are still links to be replaced.
            String tagContents = getSubstring(output, IMAGE_START, IMAGE_END);
            String url = getSubstring(tagContents, "link=", " height=");
            int y = Integer.parseInt(getSubstring(tagContents, "height=", " width="));
            int x = Integer.parseInt(getSubstring(tagContents, "width=", " "));
            
            String html = "<img src=\"" + url + "\" height=\"" + y + 
                    "\" width=\"" + x + "\"/>";
            
            output = output.replace(IMAGE_START + tagContents + IMAGE_END, html);
        }
        
        return output;
    }
    
    /**
     * Replaces all pseudoHTML videos with HTML5 video tags. If the file type
     * linked to in the pseudoHTML is not supported by HTML5 video tags, it will
     * be replaced by a link to the source video.
     * 
     * @param pseudo PseudoHTML.
     * @return PseudoHTML with HTML videos.
     */
    private static String replaceVideos(String pseudo) {
        String output = pseudo;
        
        while(output.contains(VIDEO_START)) {
            //while there are still links to be replaced.
            String tagContents = getSubstring(output, VIDEO_START, VIDEO_END);
            String url = getSubstring(tagContents, "link=", " height=");
            int y = Integer.parseInt(getSubstring(tagContents, "height=", " width="));
            int x = Integer.parseInt(getSubstring(tagContents, "width=", " "));
            String fileType = "";
            String html;
            
            if(url.endsWith(".mp4")) {
                fileType = "mp4";
            }
            else if(url.endsWith(".ogg")) {
                fileType = "ogg";
            }
            else if(url.endsWith(".webm")) {
                fileType = "webm";
            }
            
            if(!fileType.equals("")) {
                //valid file type, make video tag
                html = "<video width=\"" + x + "\" height=\"" + y + 
                        "\" controls><source src=\"" + url + "\" " +
                        "type=\"video/" + fileType + "\"></video>";
            }
            else {
                //invalid file type, replace with link to video
                html = "<a src=\"" + url + "\">View the video here.</a>";
            }
            
            output = output.replace(VIDEO_START + tagContents + VIDEO_END, html);
        }
        
        return output;
    }
    
    /**
     * Gets the substring of a string between two given regular expressions in
     * the string.
     * 
     * @param s The string to extract from.
     * @param startPoint The start of the extraction (not included in output)
     * @param endPoint The end of the extraction (not included in output)
     * @return The substring between the points.
     */
    private static String getSubstring(String s, String startPoint, 
            String endPoint) {
        String output;
        
        int tagStartIndex = s.indexOf(startPoint) + startPoint.length();
        int tagEndIndex = s.indexOf(endPoint, tagStartIndex);
        output = s.substring(tagStartIndex, tagEndIndex);
        
        return output;
    }
}
