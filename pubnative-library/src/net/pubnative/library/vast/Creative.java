/**
 * Copyright 2014 PubNative GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.pubnative.library.vast;

import java.util.ArrayList;

import org.droidparts.annotation.serialize.XML;
import org.droidparts.model.Model;

public class Creative extends Model
{
    private static final long                serialVersionUID = 1L;
    @XML(tag = "Linear" + XML.SUB + "Duration")
    public String                            duration;
    //
    @XML(tag = "Linear" + XML.SUB + "TrackingEvents", attribute = "Tracking")
    public ArrayList<Creative.TrackingEvent> trackingEvents;
    @XML(tag = "Linear" + XML.SUB + "MediaFiles", attribute = "MediaFile")
    public ArrayList<Creative.MediaFile>     mediaFiles;

    //
    public static class TrackingEvent extends Model
    {
        private static final long serialVersionUID = 1L;
        @XML(attribute = "event")
        public String             event;
        @XML
        public String             url;
    }
    public static class MediaFile extends Model
    {
        private static final long serialVersionUID = 1L;
        @XML(attribute = "delivery")
        public String             delivery;
        @XML(attribute = "height")
        public int                height;
        @XML(attribute = "scalable")
        public boolean            scalable;
        @XML(attribute = "type")
        public String             type;
        @XML(attribute = "width")
        public int                width;
        @XML
        public String             url;
    }
}