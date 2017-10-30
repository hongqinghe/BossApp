package com.android.app.buystore.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtils {

    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // 原始图片的宽高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Utility method for downsampling images.
     * 
     * @param path
     *            the file path
     * @param data
     *            if file path is null, provide the image data directly
     * @param target
     *            the target dimension
     * @param isWidth
     *            use width as target, otherwise use the higher value of height
     *            or width
     * @param round
     *            corner radius
     * @return the resized image
     */
    public static Bitmap getResizedImage(String path, byte[] data, int target,
            boolean isWidth, int round) {
        Options options = null;
        if (target > 0) {
            Options info = new Options();
            info.inJustDecodeBounds = true;
            // 设置这两个属性可以减少内存损耗
            info.inInputShareable = true;
            info.inPurgeable = true;

            decode(path, data, info);

            int dim = info.outWidth;
            if (!isWidth)
                dim = Math.max(dim, info.outHeight);
            int ssize = sampleSize(dim, target);
            options = new Options();
            options.inSampleSize = ssize;
        }

        Bitmap bm = null;
        try {
            bm = decode(path, data, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        if (round > 0) {
            bm = getRoundedCornerBitmap(bm, round);
        }

        return bm;

    }

    private static Bitmap decode(String path, byte[] data,
            BitmapFactory.Options options) {

        Bitmap result = null;

        if (path != null) {

            result = decodeFile(path, options);

        } else if (data != null) {
            result = BitmapFactory.decodeByteArray(data, 0, data.length,
                    options);

        }

        if (result == null && options != null && !options.inJustDecodeBounds) {
        }

        return result;
    }

    private static Bitmap decodeFile(String path, BitmapFactory.Options options) {

        Bitmap result = null;

        if (options == null) {
            options = new Options();
        }

        options.inInputShareable = true;
        options.inPurgeable = true;

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(path);

            FileDescriptor fd = fis.getFD();

            result = BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (IOException e) {
            Log.e("TAG", e.toString());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    private static int sampleSize(int width, int target) {

        int result = 1;

        for (int i = 0; i < 10; i++) {

            if (width < target * 2) {
                break;
            }
            width = width / 2;
            result = result * 2;
        }

        return result;
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        r = Math.min(width, height);
        Bitmap backgroundBmp = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        canvas.drawRoundRect(rect, r / 2, r / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 取相交的区域
        canvas.drawBitmap(bitmap, null, rect, paint);
        return backgroundBmp;
    }

    /**
     * 获取圆角的bitmap
     * 
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * auto fix the imageOrientation
     * 
     * @param bm
     *            source
     * @param iv
     *            imageView if set invloke it's setImageBitmap() otherwise do
     *            nothing
     * @param uri
     *            image Uri if null user path
     * @param path
     *            image path if null use uri
     */
    public static Bitmap autoFixOrientation(Bitmap bm, ImageView iv, Uri uri,
            String path) {
        int deg = 0;
        try {
            ExifInterface exif = null;
            if (uri == null) {
                exif = new ExifInterface(path);
            } else if (path == null) {
                exif = new ExifInterface(uri.getPath());
            }

            if (exif == null) {
                Log.e("TAG", "exif is null check your uri or path");
                return bm;
            }

            String rotate = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int rotateValue = Integer.parseInt(rotate);
            System.out.println("orientetion : " + rotateValue);
            switch (rotateValue) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                deg = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                deg = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                deg = 270;
                break;
            default:
                deg = 0;
                break;
            }
        } catch (Exception ee) {
            Log.d("catch img error", "return");
            if (iv != null)
                iv.setImageBitmap(bm);
            return bm;
        }
        Matrix m = new Matrix();
        m.preRotate(deg);
        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m,
                true);

        if (iv != null)
            iv.setImageBitmap(bm);
        return bm;
    }
}
