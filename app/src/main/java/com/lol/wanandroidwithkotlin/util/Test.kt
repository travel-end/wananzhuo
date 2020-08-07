package com.lol.wanandroidwithkotlin.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.os.Environment
import java.io.*


/**
 * Create by Jingui on 2020/6/23
 * Describe:
 * email: m15008497107@163.com
 */
object Test {

    fun compressBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size(), options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight,null)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size(), options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int,
        path: String?
    ): Int {
        var height = options.outHeight
        var width = options.outWidth
        var samplesize = 1

        if (height == -1 || width == -1) {
            try {
                if (path != null) {
                    val ex = ExifInterface(path)
                    val tempHeight = ex.getAttributeInt(
                        ExifInterface.TAG_IMAGE_LENGTH
                        , ExifInterface.ORIENTATION_NORMAL
                    )
                    val tempWidth = ex.getAttributeInt(
                        ExifInterface.TAG_IMAGE_WIDTH
                        , ExifInterface.ORIENTATION_NORMAL
                    )
                    width = tempWidth
                    height = tempHeight
                }


            } catch (e: Exception) {
                samplesize = 4
            }
        }


        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while ((halfHeight / samplesize) >= reqHeight
                && (halfWidth / samplesize) >= reqWidth
            ) {
                samplesize *= 2
            }
        }

        return samplesize
    }

    @Throws(IOException::class)
    fun saveFile(bm: Bitmap) {
        val SAVE_PIC_PATH = Environment.getExternalStorageDirectory().absolutePath+ "/test"

//        val subForder = SAVE_REAL_PATH + path
        val foder = File(SAVE_PIC_PATH)
        if (!foder.exists()) {
            foder.mkdirs()
        }
        val myCaptureFile = File(SAVE_PIC_PATH, "jg.jpg")
        if (!myCaptureFile.exists()) {


            myCaptureFile.createNewFile()
        }
        val bos = BufferedOutputStream(FileOutputStream(myCaptureFile))
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        bos.flush()
        bos.close()
    }
}