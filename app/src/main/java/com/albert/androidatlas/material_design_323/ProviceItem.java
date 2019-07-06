package com.albert.androidatlas.material_design_323;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Filename : ProviceItem.java
 *
 * @author : Mihawk
 * @since : 2019-07-01
 */

public class ProviceItem {
    private Path path;

    private int drawColor;

    public ProviceItem(Path path) {
        this.path = path;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }

    public void drawItem(Canvas canvas, Paint paint, boolean isSelected) {
        if (isSelected) {
//            绘制内部的颜色
            RectF rect = new RectF();
            path.computeBounds(rect, true);
            canvas.scale(1.5f, 1.5f, rect.centerX(), rect.centerY());
            paint.clearShadowLayer();
//            paint.setShadowLayer(10, 0, 0, 0xFF4f4f4f);
            paint.setStrokeWidth(2);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(drawColor);
            canvas.drawPath(path, paint);

//            绘制边界
            paint.setStyle(Paint.Style.STROKE);
            int strokeColor = 0xFFD0E8F4;
            paint.setShadowLayer(10, 0, 0, 0xFF4f4f4f);
            paint.setColor(strokeColor);
            canvas.drawPath(path, paint);
            canvas.scale(1 / 1.5f, 1 / 1.5f, rect.centerX(), rect.centerY());
        } else {

            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(8, 0, 0, 0xffffff);
            canvas.drawPath(path, paint);

//            绘制边界
            paint.clearShadowLayer();
            paint.setColor(drawColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(2);
            canvas.drawPath(path, paint);
        }

    }

    public boolean isTouch(float x, float y) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return region.contains((int) x, (int) y);
    }
}
