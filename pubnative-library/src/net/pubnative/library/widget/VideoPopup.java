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
package net.pubnative.library.widget;

import static org.droidparts.util.ui.ViewUtils.findViewById;
import static org.droidparts.util.ui.ViewUtils.setInvisible;
import net.pubnative.library.R;
import net.pubnative.library.inner.WorkerItem;
import net.pubnative.library.model.holder.VideoAdHolder;
import net.pubnative.library.util.MiscUtils;
import net.pubnative.library.util.ViewUtil;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoPopup extends ViewPopup
{
    public interface Listener
    {
        void didVideoPopupClose(VideoPopup vp, WorkerItem<?> wi);

        void didVideoPopupSkip(VideoPopup vp, WorkerItem<?> wi);

        void didVideoPopupDismiss(WorkerItem<?> wi, TextureView parentTv);
    }

    private final WorkerItem<VideoAdHolder> wi;
    private final TextureView               parentTv;
    private final Listener                  l;
    private final TextureView               tv;
    private final ImageView                 muteView;
    private final TextView                  skipButtonView;
    private final CountDownView             countDownView;
    private Handler                         handler;

    public VideoPopup(WorkerItem<VideoAdHolder> wi, TextureView parentTv, Listener l)
    {
        super(LayoutInflater.from(wi.getContext()).inflate(R.layout.pn_view_video, null));
        this.wi = wi;
        this.parentTv = parentTv;
        this.l = l;
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        handler = new Handler();
        tv = findViewById(getContentView(), R.id.view_video);
        muteView = findViewById(getContentView(), R.id.view_mute);
        skipButtonView = findViewById(getContentView(), R.id.view_skip);
        countDownView = findViewById(getContentView(), R.id.view_count_down);
        //
        tv.setOnClickListener(this);
        muteView.setOnClickListener(this);
        setMuted(false);
        skipButtonView.setOnClickListener(this);
    }

    private void initSkip()
    {
        skipButtonView.setOnClickListener(this);
        skipButtonView.setText(wi.holder.ad.getVideoSkipButton());
        setInvisible(true, skipButtonView);
        int skipAvailableIn = wi.holder.ad.getVideoSkipTime() * 1000 - wi.mp.getCurrentPosition();
        handler.postDelayed(showSkipRunnable, skipAvailableIn);
    }

    @Override
    public void show(View parent)
    {
        super.show(parent);
        ViewUtil.setSurface(wi.mp, tv);
        //
        initSkip();
        updateProgressRunnable.run();
        updateVideoSizeRunnable.run();
    }

    @Override
    public void dismiss()
    {
        handler.removeMessages(0);
        l.didVideoPopupDismiss(wi, parentTv);
        super.dismiss();
    }

    @Override
    public void onClick(View v)
    {
        if (v == muteView)
        {
            setMuted(!wi.isMuted());
        }
        else
            if (v == tv || v == skipButtonView)
            {
                l.didVideoPopupSkip(this, wi);
            }
            else
            {
                super.onClick(v);
            }
    }

    @Override
    protected void didClickClose()
    {
        l.didVideoPopupClose(this, wi);
    }

    private void setMuted(boolean muted)
    {
        MiscUtils.setMuted(wi, muteView, muted);
    }

    private final Runnable showSkipRunnable        = new Runnable()
                                                   {
                                                       @Override
                                                       public void run()
                                                       {
                                                           setInvisible(false, skipButtonView);
                                                       }
                                                   };
    private final Runnable updateProgressRunnable  = new Runnable()
                                                   {
                                                       @Override
                                                       public void run()
                                                       {
                                                           countDownView.setProgress(wi.mp.getCurrentPosition(), wi.mp.getDuration());
                                                           handler.postDelayed(this, 100);
                                                       }
                                                   };
    private final Runnable updateVideoSizeRunnable = new Runnable()
                                                   {
                                                       private int lastW;

                                                       @Override
                                                       public void run()
                                                       {
                                                           int w = getContentView().getWidth();
                                                           if (lastW != w)
                                                           {
                                                               Point p = ViewUtil.getFullSceeenSize(tv.getContext(), wi.mp);
                                                               ViewUtil.setSize(tv, p.x, p.y);
                                                               w = lastW;
                                                           }
                                                           handler.postDelayed(this, 100);
                                                       }
                                                   };
}
