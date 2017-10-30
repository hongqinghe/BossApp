package com.android.app.buystoreapp.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理拍照及图片的工具类
 * <p/>
 * Created by 尚帅波 on 2016/9/29.
 */

public class ImageUtil {
    private static final String IMAGE_TYPE = "image/*";

    /**
     * 将拍照得来的照片放在SD卡的Picture目录中的 "Boss"文件夹中
     * 照片命名规则为：IMG_20160929_121045.jpg
     *
     * @return 照片的存放文件夹
     */
    public static File createImageFile() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time = format.format(new Date());
        String imageName = "IMG_" + time + ".jpg";

        File imageFile = new File(setAlbumDir(), imageName);
        return imageFile;
    }

    /**
     * 获取系统的外部路径，创建"Boss"文件夹
     *
     * @return
     */
    public static File setAlbumDir() {
        File file = new File(Environment.getExternalStorageDirectory(), "Boss");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 将图片添加到图库，并且发送广播提醒图库更新
     *
     * @param context
     */
    public static void galleryAddPic(Context context, String path) {
        //扫描指定文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 根据文件路径删除图片临时文件
     */
    public static void deleteImage() {
        if (createImageFile().exists()) {
            createImageFile().delete();
        }
    }

    /**
     * 根据路径获得图片并按要求进行压缩返回bitmap用于显示
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static Bitmap loadBitmap(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //是否仅仅加载图片的边界属性,如果将其设为true的话，在decode时将会
        // 返回null,通过此设置可以去查询一个bitmap的属性，比如bitmap的长与
        // 宽，而不占用内存大小
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //获取图片的原始宽度，以及计算其与想要图片宽高的比
        int widthRatio = options.outWidth / width;
        int heightRatio = options.outHeight / height;
        //选择宽高压缩比其中的最小值为Bitmap的压缩比例
        int inSampleSize = widthRatio > heightRatio ? widthRatio : heightRatio;
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 把bitmap转换成String
     *
     * @param path
     * @return
     */
    public static String bitMapToString(String path) {
        Bitmap bitmap = loadBitmap(path, 800, 480);
        ByteArrayOutputStream strem = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, strem);
        byte[] bytes = strem.toByteArray();
        String data = Base64.encodeToString(bytes, Base64.DEFAULT);
        return data;
    }
    /**
     * 把bitmap转换成String
     *
     * @param bitmap
     * @return
     */
    public static String bitMapToString(Bitmap bitmap) {
        if (bitmap == null){
            return "";
        }
        String string = null;
        ByteArrayOutputStream strem = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, strem);
        byte[] bytes = strem.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 打开本地相册
     *
     * @param context
     * @param requestCode
     */
    public static void openPhoto(Activity context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取从本地图库返回来的时候的URI解析出来的文件路径
     *
     * @param context
     * @param data
     * @return
     */
    public static String getPhotoPathByLocalUri(Context context, Intent data) {
        Uri uri = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }
}
