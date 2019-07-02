package com.albert.androidatlas.material_design_323;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.albert.androidatlas.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Filename : MapView.java
 *
 * @author : Mihawk
 * @since : 2019-07-01
 */

public class MapView extends View {

    private int[] colors = new int[]{0xFF992255, 0xFFf482f4, 0xFF9253d8, 0xFFc92b11};

    private List<ProviceItem> proviceItemList;
    private ProviceItem itemSelected;
    private Context mCtx;

    private Paint paint;
    private ProviceItem getItemSelected;
    private RectF totalRect;
    private float scale = 1.0f;
    private Thread loadThread = new Thread() {
        @Override
        public void run() {
            try {
                InputStream inputStram = mCtx.getResources().openRawResource(R.raw.china);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;

                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStram);
                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                float left = 999999;
                float top = 999999;
                float right = -999999;
                float bottom = -999999;

                List<ProviceItem> list = new ArrayList<>();
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    Path path = PathParser.createPathFromPathData(pathData);
                    ProviceItem proviceItem = new ProviceItem(path);
                    proviceItem.setDrawColor(colors[i % 4]);

                    RectF rect = new RectF();
                    path.computeBounds(rect, true);

                    left = Math.min(left, rect.left);
                    top = Math.min(top, rect.top);
                    right = Math.max(right, rect.right);
                    bottom = Math.max(bottom, rect.bottom);

                    list.add(proviceItem);
                }
                proviceItemList = list;
                totalRect = new RectF(left, top, right, bottom);


                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestLayout();
                        invalidate();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    private void initView(Context context) {
        this.mCtx = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        proviceItemList = new ArrayList<>();
        loadThread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (totalRect != null) {
            double mapWidth = totalRect.width();
            scale = (float) (width / mapWidth);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX() / scale, event.getY() / scale);
        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (proviceItemList == null) {
            return;
        }
        ProviceItem selectItem = null;
        for (ProviceItem proviceItem : proviceItemList) {
            if (proviceItem.isTouch(x, y)) {
                selectItem = proviceItem;
            }
        }
        if (selectItem != null) {
            itemSelected = selectItem;
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (proviceItemList != null && !proviceItemList.isEmpty()) {
            canvas.save();
            canvas.scale(scale, scale);

            for (ProviceItem item : proviceItemList) {
                if (item != itemSelected) {
                    item.drawItem(canvas, paint, false);
                }
            }
            if (itemSelected != null) {
                itemSelected.drawItem(canvas, paint, true);
            }
        }
    }
}
