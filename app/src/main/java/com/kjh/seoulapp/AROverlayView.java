package com.kjh.seoulapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.opengl.Matrix;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.kjh.seoulapp.helper.LocationHelper;
import com.kjh.seoulapp.model.ARPoint;

/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints;


    public AROverlayView(Context context) {
        super(context);

        this.context = context;

        //Demo points
        arPoints = new ArrayList<ARPoint>() {{
            add(new ARPoint("Sun Wheel", 16.0404856, 108.2262447, 0));
            add(new ARPoint("Linh Ung Pagoda", 16.1072989, 108.2343984, 0));
        }};
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentLocation == null) {
            return;
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        for (int i = 0; i < arPoints.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();

//                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.stamp_color, null);
//                d.setBounds(5,5,5,5);
//                d.draw(canvas);

                BitmapFactory.Options opt = new BitmapFactory.Options();
//                opt.outWidth = 1;
//                opt.outHeight = 1;
                opt.inSampleSize = 3;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stamp_color, opt);
                canvas.drawBitmap(bitmap, x - opt.outWidth/2, y - opt.outHeight/2, paint);

//                canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2), y - 80, paint);
            }
        }
    }
}
