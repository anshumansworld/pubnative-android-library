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

import net.pubnative.library.R;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class ViewPopup extends PopupWindow implements View.OnClickListener
{
    private View closeView;

    public ViewPopup(View view)
    {
        super(wrap(view), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        init(true);
    }

    public ViewPopup(View view, boolean showCloseButton)
    {
        super(wrap(view), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        init(showCloseButton);
    }

    private void init(boolean showCloseButton)
    {
        setBackgroundDrawable(new ColorDrawable());
        closeView = getContentView().findViewById(R.id.view_close);
        if (showCloseButton)
        {
            closeView.setOnClickListener(this);
        }
        else
        {
            closeView.setVisibility(View.INVISIBLE);
        }
    }

    public void show(View parent)
    {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClick(View v)
    {
        if (v == closeView)
        {
            didClickClose();
        }
    }

    protected void didClickClose()
    {
        dismiss();
    }

    private static View wrap(View view)
    {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(view.getContext()).inflate(R.layout.pn_view_popup, null);
        rl.addView(view, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return rl;
    }
}
